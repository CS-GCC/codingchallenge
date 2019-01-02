package codingchallenge.collections;

import codingchallenge.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    int deleteAnswersByContestantAndQuestionNumber(String contestant, int questionNumber);
}
