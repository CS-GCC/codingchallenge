package codingchallenge.engines.implementations;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.interfaces.RunAll;
import codingchallenge.engines.interfaces.ScoreCalculation;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.TeamService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class RunAllImpl implements RunAll {

    private final ScoreCalculation scoreCalculation;
    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;

    private final ServiceProperties serviceProperties;

    @Autowired
    public RunAllImpl(ScoreCalculation scoreCalculation,
                      LeaderboardService leaderboardService,
                      ContestantService contestantService,
                      ServiceProperties serviceProperties) {
        this.scoreCalculation = scoreCalculation;
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.serviceProperties = serviceProperties;
    }


    @Override
    public Leaderboard runAll() throws ContestantNotFoundException {
        return runContestants(contestantService.getAllContestants());
    }

    @Override
    public Leaderboard runContestants(List<Contestant> contestants) throws ContestantNotFoundException {
        Multimap<String, Score> scoreMultimap = ArrayListMultimap.create();
        int numberOfQuestions = serviceProperties.getNumberOfQuestions();
        for (int i=1; i<=numberOfQuestions; i++) {
            Map<String, Score> scores =
                    scoreCalculation.calculateScores(
                            contestants.stream().map(Contestant::getId).collect(Collectors.toList()),
                            i);
            scores.keySet().forEach(s -> scoreMultimap.put(s,
                    scores.get(s)));
        }
        return leaderboardService.generateLeaderboard(scoreMultimap);
    }
}
