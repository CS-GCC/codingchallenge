package codingchallenge.services.impl;

import codingchallenge.collections.GitRepository;
import codingchallenge.domain.GitRepo;
import codingchallenge.domain.Commit;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.exceptions.GitRepoNotFoundException;
import codingchallenge.services.interfaces.GitHubService;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final GitRepository gitRepository;

    private final Logger logger =
            LoggerFactory.getLogger(GitHubServiceImpl.class);

    @Autowired
    public GitHubServiceImpl(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    @Override
    public List<GitRepo> addRepositories(String username) {
        int count = gitRepository.countAllByIdExists();
        List<GitRepo> gitRepos = Lists.newArrayList();
        for (Language language : Language.values()) {
            gitRepos.add(new GitRepo(username, language, count));
        }
        gitRepos = gitRepository.insert(gitRepos);
        return gitRepos;
    }

}
