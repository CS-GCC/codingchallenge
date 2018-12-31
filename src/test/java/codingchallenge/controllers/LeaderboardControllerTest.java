package codingchallenge.controllers;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.services.interfaces.LeaderboardService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaderboardController.class)
public class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaderboardService leaderboardService;

    private final Date date = new Date(1546254762);

    @Test
    public void shouldGetLatestIndividualLeaderboard() throws Exception {

        when(leaderboardService.getLatestIndividualLeaderboard()).thenReturn(
                new Leaderboard(date)
        );

        this.mockMvc.perform(get("/leaderboard"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("1970-01-18T21:30:54.762+0000")));
    }

    @Test
    public void shouldGetLatestTeamLeaderboard() throws Exception {

        when(leaderboardService.getLatestTeamLeaderboard()).thenReturn(
                new Leaderboard(date)
        );

        this.mockMvc.perform(get("/teamleaderboard"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1970-01-18T21:30:54.762+0000")));
    }

    @Test
    public void shouldGetFilteredIndividualLeaderboard() throws Exception {
        Leaderboard leaderboard = new Leaderboard(date);
        leaderboard.setPositions(Lists.newArrayList(
                    new IndividualPosition(1, "1", "Kunal Wagle", "123")
                )
        );
        when(leaderboardService.getFilteredIndividualLeaderboard("123")).thenReturn(
                leaderboard
        );
        String expectedResult = "{\"id\":null,\"timestamp\":\"1970-01-18T21:30:54.762+0000\"," +
                "\"positions\":[{\"total\":0.0,\"scores\":null,\"position\":1,\"contestant\":\"1\",\"name\":\"Kunal " +
                "Wagle\",\"contestantId\":\"123\"}]}";

        this.mockMvc.perform(get("/leaderboard/search/123"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void shouldGetFilteredTeamLeaderboard() throws Exception {
        Leaderboard leaderboard = new Leaderboard(date);
        leaderboard.setPositions(Lists.newArrayList(
                new TeamPosition(1, "Imperial College London")
                )
        );
        when(leaderboardService.getFilteredTeamLeaderboard("123")).thenReturn(
                leaderboard
        );
        String expectedResult = "{\"id\":null,\"timestamp\":\"1970-01-18T21:30:54.762+0000\"," +
                "\"positions\":[{\"total\":0.0,\"scores\":null,\"position\":1,\"contestants\":null," +
                "\"teamName\":\"Imperial College London\"}]}";

        this.mockMvc.perform(get("/teamleaderboard/search/123"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

}
