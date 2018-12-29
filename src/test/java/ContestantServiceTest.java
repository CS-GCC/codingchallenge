import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

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
        when(contestantRepository.findAllOrderedByName()).thenReturn(contestants);

        List<Contestant> actualContestants = contestantService.getAllContestants();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(contestants, actualContestants));
    }

    @Test
    public void shouldGetCorrectContestant() {
        Contestant contestant = new Contestant("1", "Kunal Wagle", "Imperial College London", "an@email.com");
        when(contestantRepository.findById("1")).thenReturn(Optional.of(contestant));

        Contestant actualContestant = contestantService.getContestantById("1");

        Assert.assertEquals(contestant, actualContestant);
    }

}
