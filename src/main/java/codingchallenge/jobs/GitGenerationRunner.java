package codingchallenge.jobs;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.InitialisationService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    @Scheduled(initialDelay = 100, fixedDelay = 300000)
    public void generateRepos() {
        logger.info("Starting generation of repos");
        List<Contestant> contestants = contestantRepository.findAll();
        contestants =
                contestants.stream().filter(c -> c.getGitUsername() != null && !c.getGitUsername().isEmpty() && !c.isRepoCreated()).collect(Collectors.toList());
        logger.info("There are currently " + contestants.size() + " left");
        contestants =
                contestants.stream().limit(250).collect(Collectors.toList());
        ExecutorService es = Executors.newFixedThreadPool(5);
        List<Callable<Void>> runnables = Lists.newArrayList();
        for (int i=0; i<=4; i++) {
            runnables.add(new Runner(contestants.stream().skip(i*50).limit(50).collect(Collectors.toList())));
        }
        try {
            es.invokeAll(runnables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Completed a git generation run");
    }

    private class Runner implements Callable<Void> {

        private List<Contestant> contestants;

        private Runner(List<Contestant> contestants) {
            this.contestants = contestants;
        }

        @Override
        public Void call() throws Exception {
            int i=1;
            for (Contestant contestant : contestants) {
                try {
                    logger.info("Starting " + i + " of " + contestants.size());
                    ResponseEntity<UUID> uuidResponseEntity =
                            restTemplate.getForEntity(serviceProperties.getGlobal() +
                                            "contestants/uuid/travis/" + contestant.getGlobalId(),
                                    UUID.class);
                    UUID uuid = uuidResponseEntity.getBody();
                    if (uuid != null) {
//                contestant.setSentForInitialisation(true);
//                contestantRepository.save(contestant);
                        initialisationService.completeInitialisation(contestant,
                                uuid.toString());
                        logger.info("Sent someone to the initialisation function");
                    }
                    logger.info("Completed contestant " + i);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Not to worry. Continuing as usual");
                }
            }
            return null;
        }
    }

}
