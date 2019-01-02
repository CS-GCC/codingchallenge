package codingchallenge.controllers;

import codingchallenge.domain.Answer;
import codingchallenge.services.interfaces.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @CrossOrigin
    @RequestMapping(path = "/answer/question/{question}/contestant/{contestant}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateAnswersForContestant(@RequestBody List<Answer> answers, @PathVariable int question, @PathVariable String contestant) {
        this.answerService.updateAnswersForContestantAndQuestion(contestant, question, answers);
    }
}
