package codingchallenge.api.implementations;

import codingchallenge.api.interfaces.Git;
import codingchallenge.domain.FileData;
import codingchallenge.domain.GitRepo;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.FileService;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class GitImpl implements Git {

    private final GitHub gitHub;
    private final FileService fileService;
    private final ServiceProperties serviceProperties;

    @Autowired
    public GitImpl(FileService fileService, ServiceProperties serviceProperties) throws IOException {
        this.fileService = fileService;
        this.serviceProperties = serviceProperties;
        this.gitHub = GitHub.connect(serviceProperties.getGitUsername(),
                serviceProperties.getGitToken());
    }

    @Override
    public void createRepository(GitRepo repo) throws Exception {
            GHCreateRepositoryBuilder builder =
                    gitHub.createRepository(repo.getRepoName());
            GHRepository repository = builder
                    .private_(true)
                    .create();
            System.out.println(repository);
    }

    @Override
    public void initialCommit(GitRepo repo) throws IOException {
        List<FileData> fileData = fileService.getFolderContent(repo.getLanguage());
        GHRepository repository =
                gitHub.getRepository(serviceProperties.getGitUsername() + "/" +
                repo.getRepoName());
        for (FileData file : fileData) {
            if (file.getBytes() != null) {
                repository
                        .createContent()
                        .path(file.getPath())
                        .content(file.getBytes())
                        .message(file.getName())
                        .commit();
            }
        }
    }

    @Override
    public void addCollaborator(GitRepo repo, String username) throws IOException {
        GHRepository repository =
                gitHub.getRepository(serviceProperties.getGitUsername() +
                "/" +
                repo.getRepoName());
        GHUser user = gitHub.getUser(username);
        repository.addCollaborators(user);
    }

}
