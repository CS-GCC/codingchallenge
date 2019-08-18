package codingchallenge.collections;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;

public interface LeaderboardRepository extends MongoRepository<Leaderboard, String> {

    Optional<Leaderboard> findTopByTimestampBeforeAndTypeOrderByTimestampDesc(Date timestamp, Type type);

}
