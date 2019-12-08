package codingchallenge.collections;

import codingchallenge.domain.Contestant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ContestantRepository extends MongoRepository<Contestant, String> {
    List<Contestant> findContestantsByTeamId(String teamId);
    Optional<Contestant> findContestantByGlobalId(String globalId);
    long countAllByRepoCreatedIsFalse();
    List<Contestant> findContestantsByGroupFalseAndName(String name);
    Optional<Contestant> findByGitUsername(String uuid);
    List<Contestant> findAllByGitUsername(String uuid);
    List<Contestant> findAllByGroupMemberFalse();
    long countAllByGroupMemberFalse();
    List<Contestant> findContestantsByGroupTrueAndName(String groupName);
}
