package codingchallenge.collections;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LeaderboardRepository extends MongoRepository<Leaderboard<IndividualPosition>, String> {

    Optional<Leaderboard<IndividualPosition>> findTopByOrderByCreatedDesc();

}
