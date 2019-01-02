package codingchallenge.services.impl;

import codingchallenge.collections.TestRepository;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.NotEnoughTestsException;
import codingchallenge.services.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }


    @Override
    public List<TestCase> testsForCategoryAndQuestion(Category category, int questionNumber) {
        return testRepository.findAllByCategoryAndQuestionNumber(category, questionNumber);
    }

    @Override
    public List<TestCase> testsForQuestion(int questionNumber) {
        return testRepository.findAllByQuestionNumber(questionNumber);
    }

    @Override
    public List<TestCase> addMultipleTests(List<TestCase> testCases) {
        return testRepository.insert(testCases);
    }

    @Override
    public List<TestCase> obtainRandomisedTests(int questionNumber) throws NotEnoughTestsException {
        List<TestCase> testCases = new ArrayList<>();
        for (Category category : Category.values()) {
            List<TestCase> tests = testsForCategoryAndQuestion(category, questionNumber);
            if (tests.size() < category.getNumberOfTests()) {
                throw new NotEnoughTestsException(category, questionNumber);
            }
            tests = getDistinctCases(category, tests);
            if (tests != null) {
                testCases.addAll(tests);
            } else {
                throw new NotEnoughTestsException(category, questionNumber);
            }
        }
        renumberTestCases(testCases);
        return testCases;
    }

    private void renumberTestCases(List<TestCase> testCases) {
        for (int i=0; i<testCases.size(); i++) {
            testCases.get(i).setTestNumber(i+1);
        }
    }

    private List<TestCase> getDistinctCases(Category category, List<TestCase> tests) {
        int[] ints = generateRandomIntArray(category.getNumberOfTests(), tests.size());
        List<TestCase> testCases = new ArrayList<>();
        for (int index : ints) {
            testCases.add(tests.get(index));
        }
        return testCases;
    }

    private int[] generateRandomIntArray(int numberOfTests, int listSize) {
        List<Integer> integers = new ArrayList<>();
        int[] result = new int[numberOfTests];
        for (int i=0; i<listSize; i++) {
            integers.add(i);
        }
        Random random = new Random();
        for (int i=0; i<numberOfTests; i++) {
            int next = random.nextInt(integers.size());
            result[i] = integers.get(next);
            integers.remove(next);
        }
        return result;
    }

}
