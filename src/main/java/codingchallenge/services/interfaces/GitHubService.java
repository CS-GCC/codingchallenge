package codingchallenge.services.interfaces;

import codingchallenge.domain.GitRepo;

import java.util.List;

public interface GitHubService {

    List<GitRepo> addRepositories(String username);

}
