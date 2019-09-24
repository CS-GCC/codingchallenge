package codingchallenge.collections;

import codingchallenge.domain.GitRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GitRepository extends MongoRepository<GitRepo, String> {
    Optional<GitRepo> findGitRepoByRepoName(String repoName);
    List<GitRepo> findGitRepoByUsername(String username);
    int countAllByIdExists();
}
