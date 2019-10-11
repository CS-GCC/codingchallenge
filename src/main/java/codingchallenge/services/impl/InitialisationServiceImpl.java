package codingchallenge.services.impl;

import codingchallenge.api.interfaces.Git;
import codingchallenge.api.interfaces.Travis;
import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.GitRepo;
import codingchallenge.domain.Status;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.ChallengeInBounds;
import codingchallenge.services.interfaces.GitHubService;
import codingchallenge.services.interfaces.InitialisationService;
import org.kohsuke.github.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
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

//    @Async
    public void completeInitialisation(Contestant contestant, String travisUUID) {
        if (!contestant.isRepoCreated()) {
            contestant.setRepoCreated(
                    createGitRepositories(contestant, UUID.randomUUID()));
            contestantRepository.save(contestant);
        }
    }

    private boolean createGitRepositories(Contestant contestant, UUID uuid) {
        if (!challengeInBounds.isStubbed() && challengeInBounds.isGitEnabled()) {
            logger.info("Beginning git creation");
            String username = contestant.getGitUsername();
            List<GitRepo> repositories = gitHubService.addRepositories(username);
            updateContestantRepo(contestant, repositories);
            logger.info("Added repos to service");
            for (GitRepo repo : repositories) {
                try {
                    logger.info("About to create repo");
                    git.createRepository(repo);
                    if (challengeInBounds.challengeInBounds() == Status.IN_PROGRESS) {
                        git.addCollaborator(repo, username);
                        logger.info("Collaborator added");
                        travis.activateTravis(repo.getRepoName());
                        travis.setEnvVariable(repo.getRepoName(),
                                contestant.getGitUsername());
                    }
                } catch (HttpException e) {
                    logger.error("Repo already exists. Setting to true", e);
                    return true;
                } catch (Exception e) {
                    logger.error("Creation of Git Repository didn't work", e);
                    return false;
                }
            }
        } else {
            return false;
        }
        logger.info("Repos created");
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
