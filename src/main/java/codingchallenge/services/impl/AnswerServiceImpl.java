package codingchallenge.services.impl;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Answer;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Status;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.services.interfaces.AnswerService;
import codingchallenge.services.interfaces.ChallengeInBounds;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ContestantRepository contestantRepository;
    private final ChallengeInBounds challengeInBounds;

    private final Logger logger =
            LoggerFactory.getLogger(AnswerServiceImpl.class);

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ContestantRepository contestantRepository,
                             ChallengeInBounds challengeInBounds) {
        this.answerRepository = answerRepository;
        this.contestantRepository = contestantRepository;
        this.challengeInBounds = challengeInBounds;
    }

    @Override
    public void updateAnswersForUUID(String uuid, List<Answer> answers,
                                     int questionNumber) {
        if (challengeInBounds.challengeInBounds() == Status.IN_PROGRESS) {
            List<Contestant> contestants =
                    contestantRepository.findAllByGitUsername(uuid);

            List<String> blacklist = Lists.newArrayList("mjslee0921", "userJY",
                    "theriley106", "underscoreanuj");

            if (contestants.size() > 0 && !blacklist.contains(uuid)) {
                String contestant = contestants.get(0).getId();
                for (Answer answer : answers) {
                    answer.setContestant(contestant);
                }
                answerRetry(answers, questionNumber, contestant, 5);
                return;
            }
            logger.info("UUID given is invalid. No action taken.");
        } else {
            logger.info("Answers blocked as challenge not in progress");
        }
    }

    private void answerRetry(List<Answer> answers, int questionNumber,
                             String contestant, int retry) {
        try {
            logger.info("Trying the retry for " + contestant + ". Retry count" +
                    " is at " + retry);
            answers = answerRepository.insert(answers);
            List<String> ids =
                    answers.stream().map(Answer::getId).collect(Collectors.toList());
            logger.info("Got " + ids.size() + " for " + contestant);
            if (ids.size() > 0) {
                answerRepository.deleteAnswersByContestantAndQuestionNumberAndIdNotIn(contestant, questionNumber, ids);
            } else {
                logger.info("Failed on attempt. Have " + retry + " retries " +
                        "remaining");
                answerRetry(answers, questionNumber, contestant, retry-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Failed on attempt. Have " + retry + " retries " +
                    "remaining");
            if (retry > 0) {
                answerRetry(answers, questionNumber, contestant, retry-1);
            }
        }
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
