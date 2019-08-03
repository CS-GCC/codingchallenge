package codingchallenge.collections;

import codingchallenge.domain.TravisUUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravisRepository extends MongoRepository<TravisUUID, String> {
}
