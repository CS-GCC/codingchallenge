package codingchallenge.services.impl;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.domain.Answer;
import codingchallenge.services.interfaces.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void updateAnswersForContestantAndQuestion(String contestant, int questionNumber, List<Answer> answers) {
        answerRepository.deleteAnswersByContestantAndQuestionNumber(contestant, questionNumber);
        answerRepository.insert(answers);
    }
}
