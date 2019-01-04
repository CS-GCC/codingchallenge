package codingchallenge.services;

import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.collections.TeamLeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.impl.LeaderboardServiceImpl;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private TeamLeaderboardRepository teamLeaderboardRepository;

    @Mock
    private ContestantService contestantService;

    @InjectMocks
    private LeaderboardServiceImpl leaderboardService;

    private List<Contestant> contestants = Lists.newArrayList(
            generateContestant("Kunal Wagle", "1", "Imperial College London"),
            generateContestant("Rahul Kothare", "2", "Kings College London"),
            generateContestant("Lux Mahendran", "3", "University College " +
                    "London"),
            generateContestant("Search Term", "4", "Imperial College London"),
            generateContestant("Search Keyword", "5", "Oxford University")
    );

    @Test
    public void shouldReturnLatestIndividualLeaderboard() {
        Leaderboard expected = new Leaderboard();
        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(expected));

        Leaderboard actual =
                leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantService, times(0)).getAllContestants();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateBlankIndividualLeaderboardWhenNoneExist() {
        List<Position> expectedPositions = generateSearchablePositions();

        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.empty());
        when(contestantService.getAllContestants()).thenReturn(contestants);

        Leaderboard actual =
                leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantService, times(1)).getAllContestants();

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));

    }

    @Test
    public void shouldReturnCorrectlyFilteredIndividualLeaderboard() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3),
                individualPositions.get(4)
        );
        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual =
                leaderboardService.getFilteredIndividualLeaderboard("Search");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));

    }

    @Test
    public void shouldHaveCaseInsensitiveIndividualSearch() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3),
                individualPositions.get(4)
        );
        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual =
                leaderboardService.getFilteredIndividualLeaderboard("sEARCH");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldSearchByIndividualPosition() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual =
                leaderboardService.getFilteredIndividualLeaderboard("4");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldWorkWithPartialIndividualSearchTerm() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual =
                leaderboardService.getFilteredIndividualLeaderboard("ter");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldReturnLatestTeamLeaderboard() {
        Leaderboard expected = new Leaderboard();
        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(expected));

        Leaderboard actual = leaderboardService.getLatestTeamLeaderboard();

        verify(contestantService, times(0)).getAllContestants();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateBlankTeamLeaderboardWhenNoneExist() {
        List<Position> expectedPositions = Lists.newArrayList(
                new TeamPosition(
                        1,
                        "Imperial College London",
                        Lists.newArrayList("Kunal Wagle", "Search Term")
                ),
                new TeamPosition(
                        2,
                        "Kings College London",
                        Lists.newArrayList("Rahul Kothare")
                ),
                new TeamPosition(
                        3,
                        "University College London",
                        Lists.newArrayList("Lux Mahendran")
                ),
                new TeamPosition(
                        4,
                        "Oxford University",
                        Lists.newArrayList("Search Keyword")
                )
        );

        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.empty());
        when(contestantService.getAllContestants()).thenReturn(contestants);

        Leaderboard actual = leaderboardService.getLatestTeamLeaderboard();

        verify(contestantService, times(1)).getAllContestants();

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));

    }

    @Test
    public void shouldReturnCorrectlyFilteredTeamLeaderboard() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(2),
                teamPositions.get(3)
        );
        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard(
                "University");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));

    }

    @Test
    public void shouldHaveCaseInsensitiveTeamSearch() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(2),
                teamPositions.get(3)
        );
        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard(
                "uNIVERSITY");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldSearchByTeamPosition() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(2)
        );
        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard("3");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldWorkWithPartialTeamSearchTerm() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(3)
        );
        when(teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(Mockito.any())).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard(
                "ox");

        Assert.assertTrue(CollectionUtils.isEqualCollection(expectedPositions
                , actual.getPositions()));
    }

    @Test
    public void shouldCreateLeaderboardAsExpected() throws ContestantNotFoundException {
        Multimap<String, Score> scoreMultimap = ArrayListMultimap.create();
        scoreMultimap.put("123", new Score(1, 50, 50, 0, 50.0));
        scoreMultimap.put("123", new Score(2, 30, 70, 0, 30.0));
        scoreMultimap.put("456", new Score(1, 30, 70, 0, 60.0));
        scoreMultimap.put("456", new Score(2, 30, 70, 0, 30.0));

        IndividualPosition first = new IndividualPosition(1, "456", "Rahul " +
                "Kothare", "456");
        first.setTotal(90.0);
        first.setScores(Lists.newArrayList(scoreMultimap.get("456")));

        IndividualPosition second = new IndividualPosition(2, "123", "Kunal " +
                "Wagle", "123");
        second.setTotal(80.0);
        second.setScores(Lists.newArrayList(scoreMultimap.get("123")));

        List<Position> positions = Lists.newArrayList(
                first, second
        );

        when(contestantService.getContestantById("123")).thenReturn(
                new Contestant("123", "Kunal Wagle", "ICL", "test@email.com")
        );

        when(contestantService.getContestantById("456")).thenReturn(
                new Contestant("456", "Rahul Kothare", "ICL", "test@email.com")
        );

        Leaderboard leaderboard =
                leaderboardService.generateLeaderboard(scoreMultimap);

        Assert.assertTrue(CollectionUtils.isEqualCollection(positions,
                leaderboard.getPositions()));

        verify(leaderboardRepository, times(1)).insert(leaderboard);


    }

    @Test
    public void shouldCreateTeamLeaderboardAsExpected() {
        Multimap<String, Score> scoreMultimap = ArrayListMultimap.create();
        scoreMultimap.put("123", new Score(1, 50, 50, 0, 50.0));
        scoreMultimap.put("123", new Score(2, 30, 70, 0, 30.0));
        scoreMultimap.put("456", new Score(1, 30, 70, 0, 60.0));
        scoreMultimap.put("456", new Score(2, 30, 70, 0, 30.0));
        scoreMultimap.put("789", new Score(1, 30, 70, 0, 60.0));
        scoreMultimap.put("789", new Score(2, 30, 70, 0, 40.0));

        IndividualPosition first = new IndividualPosition(1, "456", "Rahul " +
                "Kothare", "456");
        first.setTotal(90.0);
        first.setScores(Lists.newArrayList(scoreMultimap.get("456")));

        IndividualPosition second = new IndividualPosition(2, "123", "Kunal " +
                "Wagle", "123");
        second.setTotal(80.0);
        second.setScores(Lists.newArrayList(scoreMultimap.get("123")));

        IndividualPosition third = new IndividualPosition(3, "789", "Lux " +
                "Mahendran", "789");
        third.setTotal(100.0);
        third.setScores(Lists.newArrayList(scoreMultimap.get("789")));

        List<Position> positions = Lists.newArrayList(
                first, second, third
        );

        Date timestamp = new Date();

        Leaderboard individualLeaderboard = new Leaderboard(timestamp);
        individualLeaderboard.setPositions(positions);

        TeamPosition firstTP = new TeamPosition(1, "KCL", Lists.newArrayList(
                "Rahul Kothare", "Lux Mahendran"));
        Map<Integer, Double> map = Maps.newHashMap();
        map.put(1, 120.0);
        map.put(2, 70.0);
        firstTP.setTotal(190.0);
        firstTP.setQuestionTotals(map);

        TeamPosition secondTP = new TeamPosition(2, "ICL", Lists.newArrayList(
                "Kunal Wagle"));
        Map<Integer, Double> secondMap = Maps.newHashMap();
        secondMap.put(1, 50.0);
        secondMap.put(2, 30.0);
        secondTP.setTotal(80.0);
        secondTP.setQuestionTotals(secondMap);

        Leaderboard expected = new Leaderboard(timestamp);
        expected.setPositions(
                Lists.newArrayList(
                       firstTP, secondTP
                )
        );

        when(contestantService.getAllContestants()).thenReturn(
                Lists.newArrayList(
                        new Contestant("123", "Kunal Wagle", "ICL", "test" +
                                "@email.com"),
                        new Contestant("456", "Rahul Kothare", "KCL", "test" +
                                "@email.com"),
                        new Contestant("789", "Lux Mahendran", "KCL", "test" +
                                "@email.com")
                )
        );

        when(contestantService.getContestantNames(eq(Lists.newArrayList(
                "123"
        )))).thenReturn(
                Lists.newArrayList(
                        "Kunal Wagle"
                )
        );

        when(contestantService.getContestantNames(eq(Lists.newArrayList(
                "789", "456"
        )))).thenReturn(
                Lists.newArrayList(
                        "Rahul Kothare", "Lux Mahendran"
                )
        );

        leaderboardService.generateTeamLeaderboard(individualLeaderboard, 2);

        verify(teamLeaderboardRepository, times(1)).insert(eq(expected));

    }

    @Test
    public void shouldOnlyGenerateTeamBoardWithTop20() {
        List<Position> individualPositions = Lists.newArrayList();
        List<Contestant> contestants = Lists.newArrayList();
        List<String> names = Lists.newArrayList();
        for (int i=0; i<30; i++) {
            IndividualPosition individualPosition = new IndividualPosition(i, String.valueOf(i), "Name",
                    String.valueOf(i));
            individualPosition.setScores(Lists.newArrayList(
                    new Score(1, 100, 0, 0, 100.0)
            ));
            individualPosition.setTotal(100.0);
            individualPositions.add(
                    individualPosition
            );
            contestants.add(
                    new Contestant(String.valueOf(i), "Name", "Team", "Email")
            );
            names.add("Name");
        }
        Date timestamp = new Date();
        Leaderboard leaderboard = new Leaderboard(timestamp);
        leaderboard.setPositions(individualPositions);

        Leaderboard expected = new Leaderboard(timestamp);
        TeamPosition teamPosition = new TeamPosition(1, "Team",
                names.subList(0, 20));
        teamPosition.setTotal(2000.0);
        Map<Integer, Double> map = Maps.newHashMap();
        map.put(1, 2000.0);
        teamPosition.setQuestionTotals(map);
        expected.setPositions(Lists.newArrayList(teamPosition));

        when(contestantService.getAllContestants()).thenReturn(contestants);
        when(contestantService.getContestantNames(anyList())).thenReturn(names.subList(0, 20));

        leaderboardService.generateTeamLeaderboard(leaderboard, 1);

        verify(teamLeaderboardRepository, times(1)).insert(eq(expected));

    }

    private List<Position> generateSearchablePositions() {
        return Lists.newArrayList(
                new IndividualPosition(1, contestants.get(0).getId(),
                        contestants.get(0).getName(), contestants.get
                        (0).getId()),
                new IndividualPosition(2, contestants.get(1).getId(),
                        contestants.get(1).getName(), contestants.get
                        (1).getId()),
                new IndividualPosition(3, contestants.get(2).getId(),
                        contestants.get(2).getName(), contestants.get
                        (2).getId()),
                new IndividualPosition(4, contestants.get(3).getId(),
                        contestants.get(3).getName(), contestants.get
                        (3).getId()),
                new IndividualPosition(5, contestants.get(4).getId(),
                        contestants.get(4).getName(), contestants.get
                        (4).getId())
        );
    }

    private Contestant generateContestant(String name, String id,
                                          String teamName) {
        Contestant contestant = new Contestant();
        contestant.setName(name);
        contestant.setId(id);
        contestant.setTeam(teamName);
        return contestant;
    }

    private List<Position> generateSearchableTeams() {
        return Lists.newArrayList(
                new TeamPosition(1, "Imperial College London"),
                new TeamPosition(2, "Kings College London"),
                new TeamPosition(3, "University College London"),
                new TeamPosition(4, "Oxford University")
        );
    }

}
