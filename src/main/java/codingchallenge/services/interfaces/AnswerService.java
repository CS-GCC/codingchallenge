package codingchallenge.services.interfaces;

import codingchallenge.domain.Answer;

import java.util.List;

public interface AnswerService {

    void updateAnswersForContestantAndQuestion(String contestant, int questionNumber, List<Answer> answers);

}
