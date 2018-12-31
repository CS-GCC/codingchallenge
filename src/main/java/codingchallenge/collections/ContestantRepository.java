package codingchallenge.collections;

import codingchallenge.domain.Contestant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContestantRepository extends MongoRepository<Contestant, String> {

}
