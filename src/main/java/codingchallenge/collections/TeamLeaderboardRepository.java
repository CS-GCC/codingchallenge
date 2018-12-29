package codingchallenge.collections;

import codingchallenge.domain.Leaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TeamLeaderboardRepository extends MongoRepository<Leaderboard, String> {
//    Optional<Leaderboard> findTopByOrderByCreatedDesc();
}
