package codingchallenge.services.impl;

import codingchallenge.collections.TestRepository;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.services.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<TestCase> obtainRandomisedTests(int questionNumber) {
        return null;
    }

}
