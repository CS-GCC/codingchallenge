package codingchallenge.services.impl;

import codingchallenge.api.interfaces.Git;
import codingchallenge.collections.GitRepository;
import codingchallenge.domain.GitRepo;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.GitHubService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final GitRepository gitRepository;
    private final Git git;

    private final Logger logger =
            LoggerFactory.getLogger(GitHubServiceImpl.class);

    @Autowired
    public GitHubServiceImpl(GitRepository gitRepository, Git git) {
        this.gitRepository = gitRepository;
        this.git = git;
    }

    @Override
    public List<GitRepo> addRepositories(String username) {
        int count = Math.toIntExact((gitRepository.count() / 4) + 1);
        List<GitRepo> gitRepos = Lists.newArrayList();
        for (Language language : Language.values()) {
            gitRepos.add(new GitRepo(username, language, count));
        }
        gitRepos = gitRepository.insert(gitRepos);
        return gitRepos;
    }

}
