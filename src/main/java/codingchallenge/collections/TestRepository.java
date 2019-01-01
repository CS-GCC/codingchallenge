package codingchallenge.collections;

import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<TestCase, String> {

    List<TestCase> findAllByCategoryAndQuestionNumber(Category category, int questionNumber);

    List<TestCase> findAllByQuestionNumber(int questionNumber);

}
