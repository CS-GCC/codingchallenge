package codingchallenge.controllers;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.TestCase;
import codingchallenge.domain.subdomain.Category;
import codingchallenge.exceptions.NotEnoughTestsException;
import codingchallenge.services.interfaces.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class TestController {

    private final TestService testService;

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

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
    @RequestMapping(path = "/challenge/registerTravis/{id}/{uuid}", method =
            RequestMethod.GET)
    public Contestant testsForCategoryAndQuestion(@PathVariable String id,
                                                  @PathVariable String uuid) {
        return testService.registerTravis(id, uuid);
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/uuids", method = RequestMethod.GET)
    public List<UUID> getUuids() {
        return testService.getUUIDs();
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
            logger.error("Not enough tests were found.", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Not Enough Tests for Category: " + e.getCategory() + ", Question Number: " + e.getQuestionNumber(), e);
        }
    }

}
