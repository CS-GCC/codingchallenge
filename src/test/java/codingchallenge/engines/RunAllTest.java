package codingchallenge.engines;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.implementations.RunAllImpl;
import codingchallenge.engines.interfaces.ScoreCalculation;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.QuestionService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RunAllTest {

    @Mock
    private ScoreCalculation scoreCalculation;

    @Mock
    private LeaderboardService leaderboardService;

    @Mock
    private ContestantService contestantService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private RunAllImpl runAll;

    @Test
    public void shouldRunAllAsExpected() throws ContestantNotFoundException {
        Map<String, Score> stringScoreMap = Maps.newHashMap();
        stringScoreMap.put("123", new Score(1, 50, 50, 0, 50.0));
        stringScoreMap.put("456", new Score(1, 70, 30, 0, 70.0));

        Multimap<String, Score> stringScoreMultimap =
                ArrayListMultimap.create();
        stringScoreMultimap.put("123", stringScoreMap.get("123"));
        stringScoreMultimap.put("456", stringScoreMap.get("456"));

        Leaderboard leaderboard = new Leaderboard(new Date());

        when(questionService.activeQuestionCount()).thenReturn(1);
        when(scoreCalculation.calculateScores(1)).thenReturn(stringScoreMap);
        when(leaderboardService.generateLeaderboard(eq(stringScoreMultimap))).thenReturn(leaderboard);

        runAll.runAll();

        verify(leaderboardService, times(1)).generateTeamLeaderboard(leaderboard, 1);
        verify(contestantService, times(1)).generateTimeStampedPositions(leaderboard);
    }

}
