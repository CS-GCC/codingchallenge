package codingchallenge.services.impl;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.services.interfaces.AnswerService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
    public void updateAnswersForContestantAndQuestion(String contestant,
                                                      int questionNumber,
                                                      List<Answer> answers) {
        answerRepository.deleteAnswersByContestantAndQuestionNumber(contestant, questionNumber);
        answerRepository.insert(answers);
    }

    @Override
    public Multimap<Correctness, Answer> getAnswersForQuestionAndTest(int questionNumber, int testNumber) {
        Multimap<Correctness, Answer> multimap = ArrayListMultimap.create();
        multimap.putAll(Correctness.INCORRECT,
                answerRepository.findAllByQuestionNumberAndTestNumberAndCorrect(questionNumber, testNumber, Correctness.INCORRECT));
        multimap.putAll(Correctness.TIMED_OUT,
                answerRepository.findAllByQuestionNumberAndTestNumberAndCorrect(questionNumber, testNumber, Correctness.TIMED_OUT));
        multimap.putAll(Correctness.CORRECT,
                answerRepository.findAllByQuestionNumberAndTestNumberAndCorrectOrderBySpeedAsc(questionNumber, testNumber, Correctness.CORRECT));
        return multimap;
    }
}
