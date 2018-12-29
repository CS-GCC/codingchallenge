package codingchallenge.services.impl;

import codingchallenge.collections.QuestionRepository;
import codingchallenge.domain.Question;
import codingchallenge.services.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> activeQuestions() {
        return questionRepository.findQuestionsByActive();
    }

    @Override
    public List<Question> allQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.insert(question);
    }

}
