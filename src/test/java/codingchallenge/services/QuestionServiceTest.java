package codingchallenge.services;

import codingchallenge.collections.QuestionRepository;
import codingchallenge.domain.Question;
import codingchallenge.services.impl.QuestionServiceImpl;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void shouldReturnAllActiveQuestions() {
        List<Question> questions = Lists.newArrayList(
                new Question(1, "Question Text", "Question 1", true),
                new Question(2, "Question Text", "Question 2", true)
        );
        when(questionRepository.findQuestionsByActive()).thenReturn(questions);

        List<Question> actual = questionService.activeQuestions();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(questions, actual));
    }

    @Test
    public void shouldReturnAllQuestions() {
        List<Question> questions = Lists.newArrayList(
                new Question(1, "Question Text", "Question 1", true),
                new Question(2, "Question Text", "Question 2", false),
                new Question(3, "Question Text", "Question 3", true)
        );
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> actual = questionService.allQuestions();

        Assert.assertEquals(true, CollectionUtils.isEqualCollection(questions, actual));
    }

    @Test
    public void shouldAddQuestionWithoutIssue() {
        Question question = new Question(1, "Question Text", "Question 1", true);
        Question question2 = new Question(1, "Question Text", "Question 1", true);
        question2.setId("123456");
        when(questionRepository.insert(question)).thenReturn(question2);

        Question response = questionService.addQuestion(question);

        Assert.assertEquals(question2, response);
    }


}
