package codingchallenge.services.interfaces;

import codingchallenge.domain.GitRepo;

import java.io.IOException;
import java.util.List;

public interface GitHubService {

    List<GitRepo> addRepositories(String username);
    void updateTravisEnvVar();

}
