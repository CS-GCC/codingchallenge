package codingchallenge.services.impl;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Answer;
import codingchallenge.domain.TravisUUID;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.services.interfaces.AnswerService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final TravisRepository travisRepository;

    private final Logger logger =
            LoggerFactory.getLogger(AnswerServiceImpl.class);

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository,
                             TravisRepository travisRepository) {
        this.answerRepository = answerRepository;
        this.travisRepository = travisRepository;
    }

    @Override
    public void updateAnswersForUUID(String uuid, List<Answer> answers) {
        UUID travisUUID = UUID.fromString(uuid);
        Optional<TravisUUID> contestantCode =
                travisRepository.findByUuid(travisUUID);
        if (contestantCode.isPresent()) {
            String contestant = contestantCode.get().getContestantId();
            for (Answer answer : answers) {
                answer.setContestant(contestant);
            }
            answerRepository.deleteAnswersByContestant(contestant);
            answerRepository.insert(answers);
            return;
        }
        logger.info("UUID given is invalid. No action taken.");
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

    @Override
    public List<Answer> getAnswersForContestants(Set<String> contestants) {
        return answerRepository.findAllByContestantIn(contestants);
    }
}
