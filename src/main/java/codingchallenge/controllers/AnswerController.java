package codingchallenge.controllers;

import codingchallenge.domain.Answer;
import codingchallenge.domain.Contestant;
import codingchallenge.services.interfaces.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class AnswerController {

    private final AnswerService answerService;

    private final Logger logger =
            LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @CrossOrigin
    @RequestMapping(path = "/answer/contestant/{uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateAnswersForContestant(@RequestBody List<Answer> answers,
                                           @PathVariable String uuid) {
        this.answerService.updateAnswersForUUID(uuid, answers);
        logger.info("Updated answers for UUID " + uuid);
    }

    @CrossOrigin
    @RequestMapping(path = "/answer/contestants")
    public void retrieveAnswersForContestants(@RequestBody Set<String> contestants) {
        answerService.getAnswersForContestants(contestants);
    }

}
