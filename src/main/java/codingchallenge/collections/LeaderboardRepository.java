package codingchallenge.collections;

import codingchallenge.domain.Leaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeaderboardRepository extends MongoRepository<Leaderboard, String> {
}
