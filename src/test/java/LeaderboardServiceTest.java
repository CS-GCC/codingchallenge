import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.collections.TeamLeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.services.impl.LeaderboardServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
    private ContestantRepository contestantRepository;

    @InjectMocks
    private LeaderboardServiceImpl leaderboardService;

    private List<Contestant> contestants = Lists.newArrayList(
            generateContestant("Kunal Wagle", "1", "Imperial College London"),
            generateContestant("Rahul Kothare", "2", "Kings College London"),
            generateContestant("Lux Mahendran", "3", "University College London"),
            generateContestant("Search Term", "4", "Imperial College London"),
            generateContestant("Search Keyword", "5", "Oxford University")
    );

    @Test
    public void shouldReturnLatestIndividualLeaderboard() {
        Leaderboard expected = new Leaderboard();
        when(leaderboardRepository.findById("123")).thenReturn(Optional.of(expected));

        Leaderboard actual = leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantRepository, times(0)).findAllOrderedByName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateBlankIndividualLeaderboardWhenNoneExist() {
        List<Position> expectedPositions = generateSearchablePositions();

        when(leaderboardRepository.findById("123")).thenReturn(Optional.empty());
        when(contestantRepository.findAllOrderedByName()).thenReturn(contestants);

        Leaderboard actual = leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantRepository, times(1)).findAllOrderedByName();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

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
        when(leaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredIndividualLeaderboard("Search");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

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
        when(leaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredIndividualLeaderboard("sEARCH");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldSearchByIndividualPosition() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredIndividualLeaderboard("4");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldWorkWithPartialIndividualSearchTerm() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredIndividualLeaderboard("ter");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldReturnLatestTeamLeaderboard() {
        Leaderboard expected = new Leaderboard();
        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.of(expected));

        Leaderboard actual = leaderboardService.getLatestTeamLeaderboard();

        verify(contestantRepository, times(0)).findAllOrderedByName();
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

        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.empty());
        when(contestantRepository.findAllOrderedByName()).thenReturn(contestants);

        Leaderboard actual = leaderboardService.getLatestTeamLeaderboard();

        verify(contestantRepository, times(1)).findAllOrderedByName();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

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
        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard("University");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

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
        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard("uNIVERSITY");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldSearchByTeamPosition() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(2)
        );
        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard("3");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldWorkWithPartialTeamSearchTerm() {
        Leaderboard returnedLeaderboard = new Leaderboard();
        List<Position> teamPositions = generateSearchableTeams();
        returnedLeaderboard.setPositions(teamPositions);
        List<Position> expectedPositions = Lists.newArrayList(
                teamPositions.get(3)
        );
        when(teamLeaderboardRepository.findById("123")).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard actual = leaderboardService.getFilteredTeamLeaderboard("ox");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    private List<Position> generateSearchablePositions() {
        return Lists.newArrayList(
                new IndividualPosition(1, contestants.get(0).getId(), contestants.get(0).getName(), contestants.get
                        (0).getId()),
                new IndividualPosition(2, contestants.get(1).getId(), contestants.get(1).getName(), contestants.get
                        (1).getId()),
                new IndividualPosition(3, contestants.get(2).getId(), contestants.get(2).getName(), contestants.get
                        (2).getId()),
                new IndividualPosition(4, contestants.get(3).getId(), contestants.get(3).getName(), contestants.get
                        (3).getId()),
                new IndividualPosition(5, contestants.get(4).getId(), contestants.get(4).getName(), contestants.get
                        (4).getId())
        );
    }

    private Contestant generateContestant(String name, String id, String teamName) {
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
