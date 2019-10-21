package codingchallenge.engines.implementations;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.interfaces.ScoreCalculation;
import codingchallenge.services.interfaces.AnswerService;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreCalculationImpl implements ScoreCalculation {

    private final AnswerService answerService;

    private final Logger logger =
            LoggerFactory.getLogger(ScoreCalculation.class);

    public ScoreCalculationImpl(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public Map<String, Score> calculateScores(List<String> contestants, int
                                              questionNumber) {
        Map<String, Score> scoreMap = initialiseMap(contestants, questionNumber);
        int numberOfTests = getNumberOfTestsForQuestion();
        for (int i=0; i<=numberOfTests; i++) {
            Multimap<Correctness, Answer> answers = getAllAnswersForTest(questionNumber, i);
            Category category = getTestCategory(i);
            double highScore = category.getTestValue();
            updateCorrectAnswers(scoreMap, answers.get(Correctness.CORRECT), highScore);
            updateIncorrectAnswers(scoreMap, answers.get(Correctness.INCORRECT));
            updateTimedOutAnswers(scoreMap, answers.get(Correctness.TIMED_OUT));
            logger.info("Completed for question " + questionNumber + ", test " +
                    "number " + i);
        }
        return scoreMap;
    }

    private void updateTimedOutAnswers(Map<String, Score> scoreMap, Collection<Answer> answers) {
        for (Answer answer : answers) {
            String contestant = answer.getContestant();
            Score score = scoreMap.get(contestant);
            score.incrementTimedOut();
        }
    }

    private void updateIncorrectAnswers(Map<String, Score> scoreMap, Collection<Answer> answers) {
        for (Answer answer : answers) {
            String contestant = answer.getContestant();
            Score score = scoreMap.get(contestant);
            score.incrementIncorrect();
        }
    }

    private void updateCorrectAnswers(Map<String, Score> scoreMap, Collection<Answer> answers, double highScore) {
        Optional<Answer> ans =
                answers.stream().min(Comparator.comparingDouble(Answer::getSpeed));
        if (!ans.isPresent()) {
            return;
        }
        double fastest = ans.get().getSpeed();
        for (Answer answer : answers) {
            String contestant = answer.getContestant();
            Score score = scoreMap.get(contestant);
            score.incrementCorrect();
            double scoreToAdd = (fastest / answer.getSpeed()) * highScore;
            score.increaseTotal(scoreToAdd);
        }
    }

    private Map<String, Score> initialiseMap(List<String> contestants, int questionNumber) {
        Map<String, Score> map = new HashMap<>();
        for (String contestant : contestants) {
            map.put(contestant, new Score(questionNumber));
        }
        return map;
    }

    private int getNumberOfTestsForQuestion() {
        int numberOfTests = 0;
        Category[] categories = Category.values();
        for (Category category : categories) {
            numberOfTests += category.getNumberOfTests();
        }
        return numberOfTests;
    }

    private Category getTestCategory(int testNumber) {
        int cumulative = 0;
        Category[] categories = Category.values();
        for (Category category : categories) {
            cumulative += category.getNumberOfTests();
            if (testNumber <= cumulative) {
                return category;
            }
        }
        return categories[categories.length - 1];
    }

    private Multimap<Correctness, Answer> getAllAnswersForTest(int questionNumber, int test) {
        return answerService.getAnswersForQuestionAndTest(questionNumber, test);
    }

}
