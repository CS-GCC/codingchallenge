import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.services.LeaderboardService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private ContestantRepository contestantRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    private List<Contestant> contestants = Lists.newArrayList(
            generateContestant("Kunal Wagle", "1"),
            generateContestant("Rahul Kothare", "2"),
            generateContestant("Lux Mahendran", "3"),
            generateContestant("Search Term", "4"),
            generateContestant("Search Keyword", "5")
    );

    @Test
    public void shouldReturnLatestLeaderboard() {
        Leaderboard<IndividualPosition> expected = new Leaderboard<>();
        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.of(expected));

        Leaderboard<IndividualPosition> actual = leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantRepository, times(0)).findAllOrderedByName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateBlankLeaderboardWhenNoneExist() {
        List<IndividualPosition> expectedPositions = generateSearchablePositions();

        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.empty());
        when(contestantRepository.findAllOrderedByName()).thenReturn(contestants);

        Leaderboard<IndividualPosition> actual = leaderboardService.getLatestIndividualLeaderboard();

        verify(contestantRepository, times(1)).findAllOrderedByName();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

    }

    @Test
    public void shouldReturnCorrectlyFilteredLeaderboard() {
        Leaderboard<IndividualPosition> returnedLeaderboard = new Leaderboard<>();
        List<IndividualPosition> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<IndividualPosition> expectedPositions = Lists.newArrayList(
                individualPositions.get(3),
                individualPositions.get(4)
        );
        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard<IndividualPosition> actual = leaderboardService.getFilteredLeaderboard("Search");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));

    }

    @Test
    public void shouldHaveCaseInsensitiveSearch() {
        Leaderboard<IndividualPosition> returnedLeaderboard = new Leaderboard<>();
        List<IndividualPosition> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<IndividualPosition> expectedPositions = Lists.newArrayList(
                individualPositions.get(3),
                individualPositions.get(4)
        );
        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard<IndividualPosition> actual = leaderboardService.getFilteredLeaderboard("sEARCH");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldSearchByPosition() {
        Leaderboard<IndividualPosition> returnedLeaderboard = new Leaderboard<>();
        List<IndividualPosition> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<IndividualPosition> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard<IndividualPosition> actual = leaderboardService.getFilteredLeaderboard("4");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    @Test
    public void shouldWorkWithPartialSearchTerm() {
        Leaderboard<IndividualPosition> returnedLeaderboard = new Leaderboard<>();
        List<IndividualPosition> individualPositions = generateSearchablePositions();
        returnedLeaderboard.setPositions(individualPositions);
        List<IndividualPosition> expectedPositions = Lists.newArrayList(
                individualPositions.get(3)
        );
        when(leaderboardRepository.findTopByOrderByCreatedDesc()).thenReturn(Optional.of(returnedLeaderboard));

        Leaderboard<IndividualPosition> actual = leaderboardService.getFilteredLeaderboard("ter");

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(expectedPositions, actual.getPositions()));
    }

    private ArrayList<IndividualPosition> generateSearchablePositions() {
        return Lists.newArrayList(
                new IndividualPosition(1, contestants.get(0).getId(), contestants.get(0).getName(), contestants.get(0).getId()),
                new IndividualPosition(2, contestants.get(1).getId(), contestants.get(1).getName(), contestants.get(1).getId()),
                new IndividualPosition(3, contestants.get(2).getId(), contestants.get(2).getName(), contestants.get(2).getId()),
                new IndividualPosition(4, contestants.get(3).getId(), contestants.get(3).getName(), contestants.get(3).getId()),
                new IndividualPosition(5, contestants.get(4).getId(), contestants.get(4).getName(), contestants.get(4).getId())
        );
    }

    private Contestant generateContestant(String name, String id) {
        Contestant contestant = new Contestant();
        contestant.setName(name);
        contestant.setId(id);
        return contestant;
    }

}
