package codingchallenge.controllers;

import codingchallenge.domain.Question;
import codingchallenge.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

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
    @RequestMapping(path = "question", method = RequestMethod.POST)
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

}
