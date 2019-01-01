package codingchallenge.services;

import codingchallenge.collections.TestRepository;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.services.impl.TestServiceImpl;
import com.google.common.collect.Lists;
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
public class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestServiceImpl testService;

    private List<TestCase> testCases = Lists.newArrayList(
            new TestCase(1, 1, Category.SMALL, "input", 1)
    );

    @Test
    public void shouldFetchTestsForCategoryAndQuestion() {
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(testCases);

        List<TestCase> actual = testService.testsForCategoryAndQuestion(Category.SMALL,     1);

        Assert.assertEquals(testCases, actual);
    }

    @Test
    public void shouldFetchTestsForQuestion() {
        when(testRepository.findAllByQuestionNumber(1))
                .thenReturn(testCases);

        List<TestCase> actual = testService.testsForQuestion(1);

        Assert.assertEquals(testCases, actual);
    }

    @Test
    public void shouldAddMultipleTests() {
        when(testRepository.insert(testCases))
                .thenReturn(testCases);

        List<TestCase> actual = testService.addMultipleTests(testCases);

        Assert.assertEquals(testCases, actual);
    }


}
