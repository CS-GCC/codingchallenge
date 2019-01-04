package codingchallenge.services;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TimeStampPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.impl.ContestantServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ContestantServiceTest {

    @Mock
    private ContestantRepository contestantRepository;

    @InjectMocks
    private ContestantServiceImpl contestantService;

    @Test
    public void shouldAddAllContestants() {
        List<Contestant> inputContestants = Lists.newArrayList(
                new Contestant("Kunal Wagle", "Imperial College London", "an@email.com"),
                new Contestant("Rahul Kothare", "Kings College London", "another@email.com")
        );
        List<Contestant> outputContestants = Lists.newArrayList(
                new Contestant("1", "Kunal Wagle", "Imperial College London", "an@email.com"),
                new Contestant("2", "Rahul Kothare", "Kings College London", "another@email.com")
        );
        when(contestantRepository.insert(inputContestants)).thenReturn(outputContestants);

        List<Contestant> actualContestants = contestantService.addContestants(inputContestants);

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(outputContestants, actualContestants));
    }

    @Test
    public void shouldGetAllContestants() {
        List<Contestant> contestants = Lists.newArrayList(
                new Contestant("Kunal Wagle", "Imperial College London", "an@email.com"),
                new Contestant("Rahul Kothare", "Kings College London", "another@email.com")
        );
        when(contestantRepository.findAll()).thenReturn(contestants);

        List<Contestant> actualContestants = contestantService.getAllContestants();

        Assert.assertTrue(CollectionUtils.isEqualCollection(contestants,
                actualContestants));
    }

    @Test
    public void shouldGetCorrectContestant() throws ContestantNotFoundException {
        Contestant contestant = new Contestant("1", "Kunal Wagle", "Imperial College London", "an@email.com");
        when(contestantRepository.findById("1")).thenReturn(Optional.of(contestant));

        Contestant actualContestant = contestantService.getContestantById("1");

        Assert.assertEquals(contestant, actualContestant);
    }

    @Test
    public void shouldGetCorrectContestantIds() {
        List<Contestant> contestants = Lists.newArrayList(
                new Contestant("123", "Kunal Wagle", "Imperial College London",
                        "an@email.com"),
                new Contestant("456", "Rahul Kothare", "Kings College London",
                        "another@email.com")
        );
        when(contestantRepository.findAll()).thenReturn(contestants);

        List<String> actual = contestantService.getAllContestantIds();

        Assert.assertTrue(CollectionUtils.isEqualCollection(Lists.newArrayList("123", "456"), actual));
    }

    @Test
    public void shouldGetCorrectContestantNames() {
        List<Contestant> contestants = Lists.newArrayList(
                new Contestant("Kunal Wagle", "Imperial College London", "an@email.com"),
                new Contestant("Rahul Kothare", "Kings College London", "another@email.com")
        );
        when(contestantRepository.findAllById(
                any()
        )).thenReturn(contestants);

        List<String> contestantNames = contestantService.getContestantNames(
                Lists.newArrayList(
                        "123",
                        "456"
                )
        );

        Assert.assertTrue(CollectionUtils.isEqualCollection(
                Lists.newArrayList("Kunal Wagle", "Rahul Kothare"),
                contestantNames
        ));

    }

    @Test
    public void shouldGenerateTimeStampedPositions() {
        Date timeStamp = new Date();
        Leaderboard leaderboard = new Leaderboard(timeStamp);
        List<Position> positions = Lists.newArrayList(
            new IndividualPosition(1, "123", "Kunal Wagle", "123")
        );
        leaderboard.setPositions(positions);
        Contestant contestant = new Contestant("123", "Kunal Wagle",
                "Imperial College London", "test@email.com");
        Contestant endContestant = new Contestant("123", "Kunal Wagle",
                "Imperial College London", "test@email.com");
        endContestant.setPositions(
                Lists.newArrayList(
                        new TimeStampPosition(positions.get(0), timeStamp)
                )
        );

        when(contestantRepository.findById("123")).thenReturn(Optional.of(contestant));

        contestantService.generateTimeStampedPositions(leaderboard);

        verify(contestantRepository, times(1)).save(eq(endContestant));
    }

}
