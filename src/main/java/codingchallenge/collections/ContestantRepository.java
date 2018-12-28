package codingchallenge.collections;

import codingchallenge.domain.Contestant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContestantRepository extends MongoRepository<Contestant, String> {

    List<Contestant> findAllOrderedByName();

}
