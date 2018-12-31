package codingchallenge.controllers;

import codingchallenge.domain.Contestant;
import codingchallenge.services.interfaces.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContestantController {

    private final ContestantService contestantService;

    @Autowired
    public ContestantController(ContestantService contestantService) {
        this.contestantService = contestantService;
    }

    @CrossOrigin
    @RequestMapping(path = "challenge/addnames", method = RequestMethod.POST)
    public List<Contestant> addContestants(@RequestBody List<Contestant> contestants) {
        return contestantService.addContestants(contestants);
    }

    @CrossOrigin
    @RequestMapping(path = "challenge/participants", method = RequestMethod.GET)
    public List<Contestant> getAllContestants() {
        return contestantService.getAllContestants();
    }

    @CrossOrigin
    @RequestMapping(path = "/contestant/{id}", method = RequestMethod.GET)
    public Contestant getContestant(@PathVariable String id) {
        return contestantService.getContestantById(id);
    }

}
