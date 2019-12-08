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
        List<String> blacklist = Lists.newArrayList();

        List<Contestant> contestants =
                contestantRepository.findAllByGitUsername(uuid);
        if (contestants.size() > 0 && !blacklist.contains(uuid)) {
            Contestant contestant = contestants.size() == 1 ?
                    contestants.get(0) :
                    contestants.stream().filter(Contestant::isGroup).findFirst().get();
            List<String> ids = Lists.newArrayList();
            for (Answer answer : answers) {
                answer.setContestant(contestant.getId());
            }
            for (int i = 0; i < answers.size(); i = i + 10) {
                List<Answer> batchedAnswers =
                        answers.stream().skip(i).limit(10).collect(Collectors.toList());
                logger.info("Starting batch " + (i / 10) + " for " + contestant);
                ids.addAll(answerRetry(batchedAnswers, questionNumber,
                        contestant.getId(), 5));
            }
            answerRepository.deleteAnswersByContestantAndQuestionNumberAndIdNotIn(contestant.getId(), questionNumber, ids);
            return;
        }
        logger.info("UUID given is invalid. No action taken.");
    }

    private List<String> answerRetry(List<Answer> answers, int questionNumber,
                                     String contestant, int retry) {
        try {
            logger.info("Trying the retry for " + contestant + ". Retry count" +
                    " is at " + retry);
            answers = answerRepository.insert(answers);
            List<String> ids =
                    answers.stream().map(Answer::getId).collect(Collectors.toList());
            logger.info("Got " + ids.size() + " for " + contestant);
            if (ids.size() > 0) {
                return ids;
            } else {
                logger.info("Failed on attempt. Have " + retry + " retries " +
                        "remaining");
                if (retry > 0) {
                    return answerRetry(answers, questionNumber, contestant,
                            retry - 1);
                }
                return Lists.newArrayList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Failed on attempt. Have " + retry + " retries " +
                    "remaining");
            if (retry > 0) {
                return answerRetry(answers, questionNumber, contestant,
                        retry - 1);
            }
            return Lists.newArrayList();
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
