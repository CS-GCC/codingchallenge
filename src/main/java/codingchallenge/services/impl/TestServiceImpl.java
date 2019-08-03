package codingchallenge.services.impl;

import codingchallenge.collections.TestRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.TravisUUID;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.exceptions.NotEnoughTestsException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TravisRepository travisRepository;
    private final ContestantService contestantService;

    @Autowired
    public TestServiceImpl(TestRepository testRepository,
                           TravisRepository travisRepository,
                           ContestantService contestantService) {
        this.testRepository = testRepository;
        this.travisRepository = travisRepository;
        this.contestantService = contestantService;
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

    @Override
    public Contestant registerTravis(String id, String uuid) {
        TravisUUID travisUUID = new TravisUUID(id, UUID.fromString(uuid));
        travisRepository.insert(travisUUID);
        try {
            return contestantService.getContestantById(id);
        } catch (ContestantNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
