package codingchallenge.jobs;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.ContestantList;
import codingchallenge.domain.GitDTO;
import codingchallenge.services.ServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsernameRunner {

    private final RestTemplate restTemplate;
    private final ContestantRepository contestantRepository;
    private final ServiceProperties serviceProperties;

    private final Logger logger = LoggerFactory.getLogger(UsernameRunner.class);

    @Autowired
    public UsernameRunner(RestTemplate restTemplate,
                          ContestantRepository contestantRepository,
                          ServiceProperties serviceProperties) {
        this.restTemplate = restTemplate;

        this.contestantRepository = contestantRepository;
        this.serviceProperties = serviceProperties;
    }

    @Scheduled(initialDelay = 100, fixedDelay = 12500)
    public void obtainUsernames() {
        logger.info("Starting username run");
        List<Contestant> contestants = contestantRepository.findAll();
        List<String> ids =
                contestants.stream().filter(c -> c.getGitUsername() == null || c.getGitUsername().isEmpty()).map(Contestant::getGlobalId).collect(Collectors.toList());
        logger.info("There are " + ids.size() + " usernames left.");
        ids = ids.stream().limit(100).collect(Collectors.toList());
        ResponseEntity<ContestantList> responseEntity =
                restTemplate.postForEntity(serviceProperties.getGlobal() + "contestants" +
                        "/gitdto", ids, ContestantList.class);
        List<GitDTO> contestantList =
                responseEntity.getBody().getContestants();
        for (GitDTO contestant : contestantList) {
            Optional<Contestant> localContestantOptional =
                    contestantRepository.findContestantByGlobalId(contestant.getGlobalId());
            if (localContestantOptional.isPresent()) {
                Contestant localContestant = localContestantOptional.get();
                localContestant.setGitUsername(contestant.getUsername());
                localContestant.setGitAvatar(contestant.getAvatar());
                contestantRepository.save(localContestant);
            }
        }
        logger.info("Completed username run");

    }

}
