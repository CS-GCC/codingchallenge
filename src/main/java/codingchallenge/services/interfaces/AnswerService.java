package codingchallenge.services.interfaces;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Correctness;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Set;

public interface AnswerService {

    void updateAnswersForUUID(String contestant, List<Answer> answers,
                              int questionNumber);

    Multimap<Correctness, Answer> getAnswersForQuestionAndTest(int questionNumber, int testNumber);

    List<Answer> getAnswersForContestants(Set<String> contestants);
}
