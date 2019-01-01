package codingchallenge.collections;

import codingchallenge.domain.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestCase, String> {
}
