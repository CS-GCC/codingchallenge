package codingchallenge.api.interfaces;

import codingchallenge.domain.GitRepo;

import java.io.IOException;

public interface Git {

    void createRepository(GitRepo repo) throws IOException, Exception;
    void initialCommit(GitRepo repo) throws IOException;
    void addCollaborator(GitRepo repo, String username) throws IOException;
    void patchCommit(GitRepo repo) throws IOException;

}
