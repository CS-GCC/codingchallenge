package codingchallenge.services.interfaces;

import codingchallenge.domain.Question;

import java.util.List;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface QuestionService {
    List<Question> activeQuestions();

    List<Question> allQuestions();

    Question addQuestion(Question question);
}
