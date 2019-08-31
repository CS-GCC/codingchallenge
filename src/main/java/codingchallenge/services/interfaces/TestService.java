package codingchallenge.services.interfaces;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.NotEnoughTestsException;

import java.util.List;
import java.util.UUID;

public interface TestService {

    List<TestCase> testsForCategoryAndQuestion(Category category, int questionNumber);

    List<TestCase> testsForQuestion(int questionNumber);

    List<TestCase> addMultipleTests(List<TestCase> testCases);

    List<TestCase> obtainRandomisedTests(int questionNumber) throws NotEnoughTestsException;

    Contestant registerTravis(String id, String uuid);

    List<UUID> getUUIDs();
}
