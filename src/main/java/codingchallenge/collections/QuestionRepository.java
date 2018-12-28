package codingchallenge.collections;

import codingchallenge.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findQuestionsByActive();
}
