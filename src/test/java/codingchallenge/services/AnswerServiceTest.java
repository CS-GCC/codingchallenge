package codingchallenge.services;

import codingchallenge.collections.AnswerRepository;
import codingchallenge.domain.Answer;
import codingchallenge.services.impl.AnswerServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    @Test
    public void shouldUpdateAnswersAsExpected() {
        when(answerRepository.deleteAnswersByContestantAndQuestionNumber("123", 1)).thenReturn(0);
        List<Answer> answers = Lists.newArrayList(
              new Answer()
        );

        answerService.updateAnswersForContestantAndQuestion("123", 1, answers);

        verify(answerRepository, times(1)).deleteAnswersByContestantAndQuestionNumber("123", 1);
        verify(answerRepository, times(1)).insert(answers);
    }
}
