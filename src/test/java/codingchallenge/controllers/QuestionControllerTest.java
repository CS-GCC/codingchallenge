package codingchallenge.controllers;

import codingchallenge.domain.Question;
import codingchallenge.services.interfaces.QuestionService;
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
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    private List<Question> questions = Lists.newArrayList(
           new Question(1, "text", "name", true)
    );

    @Test
    public void shouldGetAllActiveQuestions() throws Exception {

        when(questionService.activeQuestions()).thenReturn(questions);

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"questionText\":\"text\"," +
                        "\"questionName\":\"name\",\"active\":true}]"));
    }

    @Test
    public void shouldGetAllQuestions() throws Exception {

        when(questionService.allQuestions()).thenReturn(questions);

        mockMvc.perform(get("/allquestions"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"questionText\":\"text\"," +
                        "\"questionName\":\"name\",\"active\":true}]"));
    }

    @Test
    public void shouldAddQuestion() throws Exception {

        when(questionService.addQuestion(any())).thenReturn(questions.get(0));

        mockMvc.perform(post("/question")
                .content("{\"questionNumber\":1,\"questionText\":\"text\",\"questionName\":\"name\"," +
                        "\"active\":true}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"questionNumber\":1,\"questionText\":\"text\"," +
                        "\"questionName\":\"name\",\"active\":true}"));
    }

}
