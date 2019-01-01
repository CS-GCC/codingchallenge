package codingchallenge.services.interfaces;

import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;

import java.util.List;

public interface TestService {

    List<TestCase> testsForCategoryAndQuestion(Category category, int questionNumber);

    List<TestCase> testsForQuestion(int questionNumber);

    List<TestCase> addMultipleTests(List<TestCase> testCases);


}
