package codingchallenge.collections;

import codingchallenge.domain.subdomain.TeamPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TeamPositionRepository extends MongoRepository<TeamPosition, String> {
    List<TeamPosition> findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(String leaderboardId, int position, int position2);
    Optional<TeamPosition> findByLeaderboardIdAndTeamId(String leaderboardId,
                                                     String teamId);
    List<TeamPosition> findAllByTeamId(String teamId);
}
