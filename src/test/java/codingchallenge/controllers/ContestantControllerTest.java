package codingchallenge.controllers;

import codingchallenge.domain.Contestant;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContestantController.class)
public class ContestantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContestantService contestantService;

    private List<Contestant> contestants = Lists.newArrayList(
        new Contestant("Kunal Wagle", "Imperial College London", "test@email.com")
    );

    @Test
    public void shouldGetAllContestants() throws Exception {

        when(contestantService.getAllContestants()).thenReturn(contestants);

        mockMvc.perform(get("/challenge/participants"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"Kunal Wagle\",\"team\":\"Imperial College " +
                        "London\",\"email\":\"test@email.com\",\"gitRepository\":null,\"herokuServer\":null}]"));
    }

    @Test
    public void shouldGetAllQuestions() throws Exception {

        when(contestantService.getContestantById("123")).thenReturn(contestants.get(0));

        mockMvc.perform(get("/contestant/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"name\":\"Kunal Wagle\",\"team\":\"Imperial College " +
                        "London\",\"email\":\"test@email.com\",\"gitRepository\":null,\"herokuServer\":null}"));
    }

    @Test
    public void shouldAddQuestion() throws Exception {

        when(contestantService.addContestants(any())).thenReturn(contestants);

        mockMvc.perform(post("/challenge/addnames")
                .content("[{\"id\":null,\"name\":\"Kunal Wagle\",\"team\":\"Imperial College London\"," +
                        "\"email\":\"test@email.com\",\"gitRepository\":null,\"herokuServer\":null}]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"Kunal Wagle\",\"team\":\"Imperial College " +
                        "London\",\"email\":\"test@email.com\",\"gitRepository\":null,\"herokuServer\":null}]"));
    }
}
