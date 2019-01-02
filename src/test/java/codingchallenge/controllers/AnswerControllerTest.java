package codingchallenge.controllers;

import codingchallenge.services.interfaces.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void shouldUpdateQuestionsForContestant() throws Exception {

        mockMvc.perform(post("/answer/question/1/contestant/123")
                .content("[\n" +
                        "\t{\n" +
                        "\t\t\"questionNumber\": 1,\n" +
                        "\t\t\"testNumber\": 1,\n" +
                        "\t\t\"correct\": true,\n" +
                        "\t\t\"contestant\": \"123\",\n" +
                        "\t\t\"speed\": 0.00162 \n" +
                        "\t}\n" +
                        "]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(answerService, times(1)).updateAnswersForContestantAndQuestion(eq("123"), eq(1), any());
    }

}
