package codingchallenge.services.interfaces;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Correctness;
import com.google.common.collect.Multimap;

import java.util.List;

public interface AnswerService {

    void updateAnswersForContestantAndQuestion(String contestant, int questionNumber, List<Answer> answers);

    Multimap<Correctness, Answer> getAnswersForQuestionAndTest(int questionNumber, int testNumber);
}
