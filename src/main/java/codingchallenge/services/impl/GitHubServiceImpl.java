package codingchallenge.services.impl;

import codingchallenge.api.interfaces.Git;
import codingchallenge.api.interfaces.Travis;
import codingchallenge.collections.GitRepository;
import codingchallenge.domain.GitRepo;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.GitHubService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final GitRepository gitRepository;
    private final Git git;
    private final Travis travis;

    private final Logger logger =
            LoggerFactory.getLogger(GitHubServiceImpl.class);

    @Autowired
    public GitHubServiceImpl(GitRepository gitRepository, Git git, Travis travis) {
        this.gitRepository = gitRepository;
        this.git = git;
        this.travis = travis;
    }

    @Override
    public List<GitRepo> addRepositories(String username) {
        int count = 15000 + Math.toIntExact((gitRepository.count() / 4) + 1);
        logger.info("Count has been set as " + count);
        List<GitRepo> gitRepos = Lists.newArrayList();
        for (Language language : Language.values()) {
            gitRepos.add(new GitRepo(username, language, count));
        }
        gitRepos = gitRepository.insert(gitRepos);
        return gitRepos;
    }

    @Override
    public void updateTravisEnvVar() {
        List<GitRepo> gitRepos = gitRepository.findAll();
        int i=1;
        for (GitRepo repo : gitRepos) {
            logger.info("Updating travis env for " + repo.getRepoName() + " " +
                    "to " + repo.getUsername());
            travis.setEnvVariable(repo.getRepoName(), repo.getUsername());
            logger.info("Completed " + i + " of " + gitRepos.size());
            i++;
        }
    }


}
