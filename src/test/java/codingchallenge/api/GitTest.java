//package codingchallenge.api;
//
//import codingchallenge.api.implementations.GitImpl;
//import codingchallenge.domain.GitRepo;
//import codingchallenge.domain.subdomain.Language;
//import codingchallenge.services.interfaces.FileService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.kohsuke.github.GHCreateRepositoryBuilder;
//import org.kohsuke.github.GitHub;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//public class GitTest {
//
//    @Mock
//    private FileService fileService;
//
//    @Mock
//    private GitHub gitHub;
//
//    @InjectMocks
//    private GitImpl git;
//
//    @Test
//    public void shouldCreateRepository() throws IOException {
//        GitRepo repo = new GitRepo("username", Language.JAVA, 1);
//        repo.setRepoName("repoName");
////        when(gitHub.createRepository("repoName")).thenReturn();
//
//        git.createRepository(repo);
//
//
//    }
//}
