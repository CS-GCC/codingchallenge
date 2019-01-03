package codingchallenge.services;

import codingchallenge.collections.TestRepository;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.NotEnoughTestsException;
import codingchallenge.services.impl.TestServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void shouldObtainCorrectNumberRandomTests() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 100);
        List<TestCase> medium = blankTestCases(Category.MEDIUM, 100);
        List<TestCase> large = blankTestCases(Category.LARGE, 100);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.MEDIUM, 1))
                .thenReturn(medium);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.LARGE, 1))
                .thenReturn(large);

        List<TestCase> actual = testService.obtainRandomisedTests(1);

        Assert.assertEquals(66, actual.size());
        Assert.assertEquals(50,
                actual.stream().filter(t -> t.getCategory().equals(Category.SMALL)).collect(Collectors.toList()).size());
        Assert.assertEquals(10,
                actual.stream().filter(t -> t.getCategory().equals(Category.MEDIUM)).collect(Collectors.toList()).size());
        Assert.assertEquals(6,
                actual.stream().filter(t -> t.getCategory().equals(Category.LARGE)).collect(Collectors.toList()).size());
    }

    @Test
    public void shouldNotHaveAnyRepeatTestCases() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 100);
        List<TestCase> medium = blankTestCases(Category.MEDIUM, 100);
        List<TestCase> large = blankTestCases(Category.LARGE, 100);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.MEDIUM, 1))
                .thenReturn(medium);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.LARGE, 1))
                .thenReturn(large);

        List<TestCase> actual = testService.obtainRandomisedTests(1);

        List<String> distinctSmall = actual.stream()
                .filter(t -> t.getCategory().equals(Category.SMALL))
                .map(TestCase::getId)
                .distinct()
                .collect(Collectors.toList());
        List<String> distinctMedium = actual.stream()
                .filter(t -> t.getCategory().equals(Category.MEDIUM))
                .map(TestCase::getId)
                .distinct()
                .collect(Collectors.toList());
        List<String> distinctLarge = actual.stream()
                .filter(t -> t.getCategory().equals(Category.LARGE))
                .map(TestCase::getId)
                .distinct()
                .collect(Collectors.toList());

        Assert.assertEquals(50, distinctSmall.size());
        Assert.assertEquals(10, distinctMedium.size());
        Assert.assertEquals(6, distinctLarge.size());
    }

    @Test(expected = NotEnoughTestsException.class)
    public void shouldThrowErrorIfNotEnoughSmallCases() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 10);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);

        testService.obtainRandomisedTests(1);

    }

    @Test(expected = NotEnoughTestsException.class)
    public void shouldThrowErrorIfNotEnoughMediumCases() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 100);
        List<TestCase> medium = blankTestCases(Category.MEDIUM, 10);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.MEDIUM, 1))
                .thenReturn(medium);

        testService.obtainRandomisedTests(1);

    }

    @Test(expected = NotEnoughTestsException.class)
    public void shouldThrowErrorIfNotEnoughLargeCases() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 100);
        List<TestCase> medium = blankTestCases(Category.MEDIUM, 100);
        List<TestCase> large = blankTestCases(Category.LARGE, 5);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.MEDIUM, 1))
                .thenReturn(medium);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.LARGE, 1))
                .thenReturn(large);

        testService.obtainRandomisedTests(1);

    }

    @Test
    public void shouldHaveCorrectlyNumberedTests() throws NotEnoughTestsException {
        List<TestCase> small = blankTestCases(Category.SMALL, 100);
        List<TestCase> medium = blankTestCases(Category.MEDIUM, 100);
        List<TestCase> large = blankTestCases(Category.LARGE, 100);

        when(testRepository.findAllByCategoryAndQuestionNumber(Category.SMALL, 1))
                .thenReturn(small);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.MEDIUM, 1))
                .thenReturn(medium);
        when(testRepository.findAllByCategoryAndQuestionNumber(Category.LARGE, 1))
                .thenReturn(large);

        List<TestCase> actual = testService.obtainRandomisedTests(1);

        for (int i=1; i<66; i++) {
            Assert.assertEquals(i, actual.get(i-1).getTestNumber());
        }
    }

    private List<TestCase> blankTestCases(Category category, int total) {
        List<TestCase> testCases = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            testCases.add(blankTestCase(category, i));
        }
        return testCases;
    }

    private TestCase blankTestCase(Category category, int number) {
        TestCase testCase = new TestCase(1, number, category, "", 0);
        testCase.setId(String.valueOf(number));
        return testCase;
    }


}
