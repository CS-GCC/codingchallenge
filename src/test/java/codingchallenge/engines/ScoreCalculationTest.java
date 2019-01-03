package codingchallenge.engines;

import codingchallenge.domain.Answer;
import codingchallenge.domain.subdomain.Correctness;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.engines.implementations.ScoreCalculationImpl;
import codingchallenge.services.interfaces.AnswerService;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
@SpringBootTest
public class ScoreCalculationTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private AnswerService answerService;

    @Mock
    private ContestantService contestantService;

    @InjectMocks
    private ScoreCalculationImpl scoreCalculation;

    @Parameters(name = "{2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {1, 1.0, "Small Test"},
                        {51, 2.0, "Medium Test"},
                        {65, 5.0, "Large Test"}
                }
        );
    }

    @Parameter(0)
    public int testNumber;

    @Parameter(1)
    public double maxScore;

    @Parameter(2)
    public String testName;

    @Test
    public void shouldCalculateScoresCorrectly() {
        List<String> contestantIds = Lists.newArrayList(
                "123",
                "456",
                "789",
                "1234",
                "5678",
                "9012"
        );

        Collection<Answer> correct = Lists.newArrayList(
                new Answer(1, testNumber, Correctness.CORRECT, "123", 1.00),
                new Answer(1, testNumber, Correctness.CORRECT, "1234", 1.50),
                new Answer(1, testNumber, Correctness.CORRECT, "789", 2.50),
                new Answer(1, testNumber, Correctness.CORRECT, "456", 3.50)
        );


        Collection<Answer> incorrect = Lists.newArrayList(
        new Answer(1, testNumber, Correctness.INCORRECT, "5678", 1.50)
        );

        Collection<Answer> timedOut = Lists.newArrayList(
                new Answer(1, testNumber, Correctness.TIMED_OUT, "9012", 1.50)
        );

        Multimap<Correctness, Answer> answerMultimap =
                ArrayListMultimap.create();
        answerMultimap.putAll(Correctness.CORRECT, correct);
        answerMultimap.putAll(Correctness.INCORRECT, incorrect);
        answerMultimap.putAll(Correctness.TIMED_OUT, timedOut);

        Multimap<Correctness, Answer> emptyMultimap =
                ArrayListMultimap.create();
        emptyMultimap.putAll(Correctness.CORRECT, new ArrayList<>());
        emptyMultimap.putAll(Correctness.INCORRECT, new ArrayList<>());
        emptyMultimap.putAll(Correctness.TIMED_OUT, new ArrayList<>());

        double speedScoreFactor = maxScore / 4;

        Map<String, Score> expectedScores = Maps.newHashMap();
        expectedScores.put("123", new Score(1, 1, 0, 0, maxScore));
        expectedScores.put("456", new Score(1, 1, 0, 0, maxScore - 3*speedScoreFactor));
        expectedScores.put("789", new Score(1, 1, 0, 0, maxScore - 2*speedScoreFactor));
        expectedScores.put("1234", new Score(1, 1, 0, 0, maxScore - speedScoreFactor));
        expectedScores.put("5678", new Score(1, 0, 1, 0, 0));
        expectedScores.put("9012", new Score(1, 0, 0, 1, 0));

        when(answerService.getAnswersForQuestionAndTest(1, testNumber)).thenReturn(answerMultimap);
        when(answerService.getAnswersForQuestionAndTest(eq(1),
                not(eq(testNumber))))
                .thenReturn(emptyMultimap);
        when(contestantService.getAllContestantIds()).thenReturn(contestantIds);

        Map<String, Score> actualScores = scoreCalculation.calculateScores(1);

        Assert.assertEquals(expectedScores.size(), actualScores.size());

        for (String contestant : expectedScores.keySet()) {
            Score expected = expectedScores.get(contestant);
            Score actual = actualScores.get(contestant);
            Assert.assertEquals(expected, actual);
        }

        verify(answerService, times(66)).getAnswersForQuestionAndTest(eq(1),
                anyInt());
        verify(contestantService, times(1)).getAllContestantIds();

    }

}
