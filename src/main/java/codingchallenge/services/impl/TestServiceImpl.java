package codingchallenge.services.impl;

import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.services.interfaces.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {


    @Override
    public List<TestCase> testsForCategoryAndQuestion(Category category, int questionNumber) {
        return null;
    }

    @Override
    public List<TestCase> testsForQuestion(int questionNumber) {
        return null;
    }

    @Override
    public List<TestCase> addMultipleTests(List<TestCase> testCases) {
        return null;
    }

}
