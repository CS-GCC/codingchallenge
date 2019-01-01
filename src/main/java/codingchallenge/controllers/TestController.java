package codingchallenge.controllers;

import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.services.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
