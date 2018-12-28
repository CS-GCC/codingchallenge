package codingchallenge.services;

import codingchallenge.collections.QuestionRepository;
import codingchallenge.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> activeQuestions() {
        return questionRepository.findQuestionsByActive();
    }

    public List<Question> allQuestions() {
        return questionRepository.findAll();
    }

    public Question addQuestion(Question question) {
        return questionRepository.insert(question);
    }

}
