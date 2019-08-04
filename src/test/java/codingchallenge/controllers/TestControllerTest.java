//package codingchallenge.controllers;
//
//import codingchallenge.domain.TestCase;
//import codingchallenge.domain.subdomain.Category;
//import codingchallenge.exceptions.NotEnoughTestsException;
//import codingchallenge.services.interfaces.TestService;
//import com.google.common.collect.Lists;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(TestController.class)
//public class TestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TestService testService;
//
//    private List<TestCase> testCases = Lists.newArrayList(
//            new TestCase(1, 1, Category.SMALL, "input", 1)
//    );
//
//    @Test
//    public void shouldFetchTestsForCategoryAndQuestion() throws Exception {
//        when(testService.testsForCategoryAndQuestion(Category.SMALL, 1))
//                .thenReturn(testCases);
//
//        mockMvc.perform(get("/tests/category/SMALL/question/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"testNumber\":1," +
//                        "\"category\":\"SMALL\",\"input\":\"input\",\"output\":1}]"));
//    }
//
//    @Test
//    public void shouldFetchTestsForQuestion() throws Exception {
//        when(testService.testsForQuestion(1))
//                .thenReturn(testCases);
//
//        mockMvc.perform(get("/tests/question/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"testNumber\":1," +
//                        "\"category\":\"SMALL\",\"input\":\"input\",\"output\":1}]"));
//    }
//
//    @Test
//    public void shouldAddMultipleTests() throws Exception {
//        when(testService.addMultipleTests(anyList()))
//                .thenReturn(testCases);
//
//        mockMvc.perform(post("/tests")
//                .content("[{\"id\":null,\"questionNumber\":1,\"testNumber\":1," +
//                        "\"category\":\"SMALL\",\"input\":\"input\",\"output\":1}]")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"testNumber\":1," +
//                        "\"category\":\"SMALL\",\"input\":\"input\",\"output\":1}]"));
//    }
//
//    @Test
//    public void shouldObtainRandomisedTests() throws Exception {
//        when(testService.obtainRandomisedTests(1))
//                .thenReturn(testCases);
//
//        mockMvc.perform(get("/tests/run/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("[{\"id\":null,\"questionNumber\":1,\"testNumber\":1," +
//                        "\"category\":\"SMALL\",\"input\":\"input\",\"output\":1}]"));
//    }
//
//    @Test
//    public void shouldHandleErrorGracefully() throws Exception {
//        when(testService.obtainRandomisedTests(1))
//                .thenThrow(new NotEnoughTestsException(Category.SMALL, 1));
//
//        mockMvc.perform(get("/tests/run/1"))
//                .andExpect(status().isInternalServerError());
//    }
//
//
//}
