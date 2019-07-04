package codingchallenge.api.implementations;

import codingchallenge.api.interfaces.Git;
import codingchallenge.domain.GitRepo;
import codingchallenge.services.interfaces.FileService;
import codingchallenge.services.interfaces.GitHubService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.List;

@Service
public class GitImpl implements Git {

    private final GitHub gitHub = GitHub.connect("kunalwagle",
            "3a5ac6fdeb5bf827903c2d64add2fd01e74ef7d7");
    private final FileService fileService;

    @Autowired
    public GitImpl(FileService fileService) throws IOException {
        this.fileService = fileService;
    }

    @Override
    public void createRepository(GitRepo repo) throws IOException {
        GHCreateRepositoryBuilder builder =
                gitHub.createRepository(repo.getRepoName());
        builder
                .private_(true)
                .gitignoreTemplate(repo.getLanguage().toString())
                .create();
    }

    @Override
    public void initialCommit(GitRepo repo) throws IOException {
        byte[] fileData = fileService.getFolderContent(repo.getLanguage());
        GHRepository repository = gitHub.getRepository(repo.getRepoName());
        repository
                .createContent()
                .content(fileData)
                .message("Initial Commit")
                .commit();
    }

    @Override
    public void addCollaborator(GitRepo repo, String username) throws IOException {
        GHRepository repository = gitHub.getRepository(repo.getRepoName());
        GHUser user = gitHub.getUser(username);
        repository.addCollaborators(user);
    }
}
