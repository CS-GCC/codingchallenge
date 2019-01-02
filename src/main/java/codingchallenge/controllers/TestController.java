package codingchallenge.controllers;

import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.NotEnoughTestsException;
import codingchallenge.services.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @CrossOrigin
    @RequestMapping(path = "/tests/category/{category}/question/{question}", method = RequestMethod.GET)
    public List<TestCase> testsForCategoryAndQuestion(@PathVariable Category category, @PathVariable int question) {
        return testService.testsForCategoryAndQuestion(category, question);
    }

    @CrossOrigin
    @RequestMapping(path = "/tests/question/{question}", method = RequestMethod.GET)
    public List<TestCase> testsForQuestion(@PathVariable int question) {
        return testService.testsForQuestion(question);
    }

    @CrossOrigin
    @RequestMapping(path = "/tests", method = RequestMethod.POST)
    public List<TestCase> addTests(@RequestBody List<TestCase> testCases) {
        return testService.addMultipleTests(testCases);
    }

    @CrossOrigin
    @RequestMapping(path = "/tests/run/{question}", method = RequestMethod.GET)
    public List<TestCase> getRandomTestCases(@PathVariable int question) {
        try {
            return testService.obtainRandomisedTests(question);
        } catch (NotEnoughTestsException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Not Enough Tests for Category: " + e.getCategory() + ", Question Number: " + e.getQuestionNumber(), e);
        }
    }

}
