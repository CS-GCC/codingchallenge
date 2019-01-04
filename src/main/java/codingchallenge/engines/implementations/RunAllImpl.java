package codingchallenge.engines.implementations;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.interfaces.RunAll;
import codingchallenge.engines.interfaces.ScoreCalculation;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.QuestionService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RunAllImpl implements RunAll {

    private final ScoreCalculation scoreCalculation;
    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final QuestionService questionService;

    @Autowired
    public RunAllImpl(ScoreCalculation scoreCalculation,
                      LeaderboardService leaderboardService,
                      ContestantService contestantService, QuestionService questionService) {
        this.scoreCalculation = scoreCalculation;
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.questionService = questionService;
    }


    @Override
    public void runAll() throws ContestantNotFoundException {
        Multimap<String, Score> scoreMultimap = ArrayListMultimap.create();
        int numberOfQuestions = questionService.activeQuestionCount();
        for (int i=1; i<=numberOfQuestions; i++) {
            Map<String, Score> scores = scoreCalculation.calculateScores(i);
            scores.keySet().forEach(s -> scoreMultimap.put(s,
                    scores.get(s)));
        }
        Leaderboard leaderboard =
                leaderboardService.generateLeaderboard(scoreMultimap);
        leaderboardService.generateTeamLeaderboard(leaderboard, numberOfQuestions);
        contestantService.generateTimeStampedPositions(leaderboard);
    }
}
