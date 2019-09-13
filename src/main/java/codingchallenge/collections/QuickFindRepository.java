package codingchallenge.collections;

import codingchallenge.domain.QuickFind;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuickFindRepository extends MongoRepository<QuickFind,
        String> {
}
