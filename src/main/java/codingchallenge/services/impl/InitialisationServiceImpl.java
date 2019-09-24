package codingchallenge.services.impl;

import codingchallenge.api.interfaces.Git;
import codingchallenge.api.interfaces.Travis;
import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.GitRepo;
import codingchallenge.domain.Status;
import codingchallenge.domain.TravisUUID;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.ChallengeInBounds;
import codingchallenge.services.interfaces.GitHubService;
import codingchallenge.services.interfaces.InitialisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InitialisationServiceImpl implements InitialisationService {

    private final ContestantRepository contestantRepository;
    private final Git git;
    private final ChallengeInBounds challengeInBounds;
    private final GitHubService gitHubService;
    private final Travis travis;

    private final Logger logger =
            LoggerFactory.getLogger(InitialisationService.class);

    public InitialisationServiceImpl(ContestantRepository contestantRepository, Git git, ChallengeInBounds challengeInBounds, GitHubService gitHubService, ServiceProperties serviceProperties, RestTemplate restTemplate, TravisRepository travisRepository, Travis travis) {
        this.contestantRepository = contestantRepository;
        this.git = git;
        this.challengeInBounds = challengeInBounds;
        this.gitHubService = gitHubService;
        this.travis = travis;
    }

    @Async
    public void completeInitialisation(Contestant contestant, String code) {
        UUID uuid = UUID.randomUUID();
        contestant.setRepoCreated(
                createGitRepositories(contestant, uuid));
    }

    private boolean createGitRepositories(Contestant contestant, UUID uuid) {
        if (!challengeInBounds.isStubbed() && challengeInBounds.isGitEnabled()) {
            String username = contestant.getGitUsername();
            List<GitRepo> repositories = gitHubService.addRepositories(username);
            updateContestantRepo(contestant, repositories);
            for (GitRepo repo : repositories) {
                try {
                    git.createRepository(repo);
                    git.initialCommit(repo);
                    if (challengeInBounds.challengeInBounds() == Status.IN_PROGRESS) {
                        git.addCollaborator(repo, username);
                        travis.activateTravis(repo.getRepoName());
                        travis.setEnvVariable(repo.getRepoName(),
                                uuid.toString());
                    }
                } catch (IOException e) {
                    logger.error("Creation of Git Repository didn't work", e);
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private void updateContestantRepo(Contestant contestant,
                                      List<GitRepo> repositories) {
        GitRepo gitRepo = repositories.get(0);
        String[] repoParts = gitRepo.getRepoName().split("-");
        String repoCounter = repoParts[repoParts.length-1];
        contestant.setGitRepository(repoCounter);
        contestantRepository.save(contestant);
    }

}
