package codingchallenge.services.impl;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Answer;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.services.interfaces.AnswerService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ContestantRepository contestantRepository;

    private final Logger logger =
            LoggerFactory.getLogger(AnswerServiceImpl.class);

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ContestantRepository contestantRepository) {
        this.answerRepository = answerRepository;
        this.contestantRepository = contestantRepository;
    }

    @Override
    public void updateAnswersForUUID(String uuid, List<Answer> answers,
                                     int questionNumber) {
        List<Contestant> contestants =
                contestantRepository.findAllByGitUsername(uuid);

        List<String> blacklist = Lists.newArrayList("mjslee0921", "userJY");

        if (contestants.size() > 0 && !blacklist.contains(uuid)) {
            String contestant = contestants.get(0).getId();
            for (Answer answer : answers) {
                answer.setContestant(contestant);
            }
            answerRepository.deleteAnswersByContestantAndQuestionNumber(contestant,
                    questionNumber);
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
