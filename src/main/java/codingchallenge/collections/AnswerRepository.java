package codingchallenge.collections;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Correctness;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    int deleteAnswersByContestantAndQuestionNumber(String contestant, int questionNumber);
    List<Answer> findAllByQuestionNumberAndTestNumberAndCorrect(int questionNumber, int testNumber, Correctness correct);
    List<Answer> findAllByQuestionNumberAndTestNumberAndCorrectOrderBySpeedAsc(int questionNumber, int testNumber,
                                                                               Correctness correct);
}
