package codingchallenge.jobs;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.InitialisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GitGenerationRunner {

    private final ContestantRepository contestantRepository;
    private final RestTemplate restTemplate;
    private final InitialisationService initialisationService;
    private final ServiceProperties serviceProperties;

    private final Logger logger =
            LoggerFactory.getLogger(GitGenerationRunner.class);

    @Autowired
    public GitGenerationRunner(ContestantRepository contestantRepository,
                               RestTemplate restTemplate,
                               InitialisationService initialisationService,
                               ServiceProperties serviceProperties) {
        this.contestantRepository = contestantRepository;
        this.restTemplate = restTemplate;
        this.initialisationService = initialisationService;
        this.serviceProperties = serviceProperties;
    }

    @Scheduled(initialDelay = 100, fixedDelay = 120000)
    public void generateRepos() {
        logger.info("Starting generation of repos");
        List<Contestant> contestants = contestantRepository.findAll();
        contestants =
                contestants.stream().filter(c -> c.getGitUsername() != null && !c.getGitUsername().isEmpty() && !c.isRepoCreated() && !c.isSentForInitialisation()).collect(Collectors.toList());
        logger.info("There are currently " + contestants.size() + " left");
        contestants =
                contestants.stream().limit(100).collect(Collectors.toList());
        for (Contestant contestant : contestants) {
            ResponseEntity<UUID> uuidResponseEntity =
                    restTemplate.getForEntity(serviceProperties.getGlobal() +
                                    "contestants/uuid/travis/" + contestant.getGlobalId(),
                            UUID.class);
            UUID uuid = uuidResponseEntity.getBody();
            if (uuid != null) {
                contestant.setSentForInitialisation(true);
                contestantRepository.save(contestant);
                initialisationService.completeInitialisation(contestant,
                        uuid.toString());
                logger.info("Sent someone to the initialisation function");
            }
        }
        logger.info("Completed a git generation run");
    }

}
