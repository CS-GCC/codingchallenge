package codingchallenge.engines.implementations;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.interfaces.ScoreCalculation;
import codingchallenge.services.interfaces.AnswerService;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreCalculationImpl implements ScoreCalculation {

    private final ContestantService contestantService;
    private final AnswerService answerService;

    public ScoreCalculationImpl(ContestantService contestantService, AnswerService answerService) {
        this.contestantService = contestantService;
        this.answerService = answerService;
    }

    @Override
    public Map<String, Score> calculateScores(List<String> contestants, int
                                              questionNumber) {
        Map<String, Score> scoreMap = initialiseMap(contestants, questionNumber);
        int numberOfTests = getNumberOfTestsForQuestion();
        for (int i=1; i<=numberOfTests; i++) {
            Multimap<Correctness, Answer> answers = getAllAnswersForTest(questionNumber, i);
            Category category = getTestCategory(i);
            double highScore = category.getTestValue();
            double speedScoreFactor = calculateSpeedScoreFactor(answers.get(Correctness.CORRECT).size(), highScore);
            updateCorrectAnswers(scoreMap, answers.get(Correctness.CORRECT), highScore, speedScoreFactor);
            updateIncorrectAnswers(scoreMap, answers.get(Correctness.INCORRECT));
            updateTimedOutAnswers(scoreMap, answers.get(Correctness.TIMED_OUT));
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

    private void updateCorrectAnswers(Map<String, Score> scoreMap, Collection<Answer> answers, double highScore, double speedScoreFactor) {
        for (Answer answer : answers) {
            String contestant = answer.getContestant();
            Score score = scoreMap.get(contestant);
            score.incrementCorrect();
            score.increaseTotal(highScore);
            highScore -= speedScoreFactor;
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

    private double calculateSpeedScoreFactor(int numberCorrect, double highScore) {
        return highScore / numberCorrect;
    }

}
