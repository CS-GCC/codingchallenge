package codingchallenge.jobs;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.ContestantList;
import codingchallenge.services.ServiceProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsernameRunner {

    private final RestTemplate restTemplate;
    private final ContestantRepository contestantRepository;
    private final ServiceProperties serviceProperties;

    public UsernameRunner(RestTemplate restTemplate,
                          ContestantRepository contestantRepository,
                          ServiceProperties serviceProperties) {
        this.restTemplate = restTemplate;

        this.contestantRepository = contestantRepository;
        this.serviceProperties = serviceProperties;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 10000000)
    public void obtainUsernames() {
        List<Contestant> contestants = contestantRepository.findAll();
        List<String> ids =
                contestants.stream().filter(c -> c.getGitUsername() == null).map(Contestant::getGlobalId).collect(Collectors.toList());
        ResponseEntity<ContestantList> responseEntity =
                restTemplate.postForEntity(serviceProperties + "contestants" +
                        "/getall", ids, ContestantList.class);
        List<Contestant> contestantList =
                responseEntity.getBody().getContestants();
        for (Contestant contestant : contestantList) {
            Optional<Contestant> localContestantOptional =
                    contestantRepository.findContestantByGlobalId(contestant.getGlobalId());
            if (localContestantOptional.isPresent()) {
                Contestant localContestant = localContestantOptional.get();
                localContestant.setGitUsername(contestant.getGitUsername());
                localContestant.setGitAvatar(contestant.getGitAvatar());
                contestantRepository.save(localContestant);
            }
        }

    }

}
