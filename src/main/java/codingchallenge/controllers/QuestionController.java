package codingchallenge.controllers;

import codingchallenge.domain.Question;
import codingchallenge.services.interfaces.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    private final Logger logger =
            LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @CrossOrigin
    @RequestMapping(path = "/questions", method = RequestMethod.GET)
    public List<Question> activeQuestions() {
        return questionService.activeQuestions();
    }

    @CrossOrigin
    @RequestMapping(path = "/allquestions", method = RequestMethod.GET)
    public List<Question> allQuestions() {
        return questionService.allQuestions();
    }

    @CrossOrigin
    @RequestMapping(path = "/question", method = RequestMethod.POST)
    public Question addQuestion(@RequestBody Question question) {
        logger.info("Adding a new question");
        return questionService.addQuestion(question);
    }

}
