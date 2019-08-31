package codingchallenge.collections;

import codingchallenge.domain.TravisUUID;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TravisRepository extends MongoRepository<TravisUUID, String> {
    Optional<TravisUUID> findByUuid(UUID uuid);
}
