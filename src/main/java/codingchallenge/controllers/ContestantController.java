package codingchallenge.controllers;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.ContestantStats;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.FactsService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContestantController {

    private final ContestantService contestantService;
    private final FactsService factsService;

    private final Logger logger =
            LoggerFactory.getLogger(ContestantController.class);

    @Autowired
    public ContestantController(ContestantService contestantService,
                                FactsService factsService) {
        this.contestantService = contestantService;
        this.factsService = factsService;
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/addnames", method = RequestMethod.POST)
    public List<Contestant> addContestants(@RequestBody List<Contestant> contestants) {
        logger.info("Adding " + contestants.size() + " contestants");
        return contestantService.addContestants(contestants, true);
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/addname", method = RequestMethod.POST)
    public Contestant addContestant(@RequestBody Contestant contestant) {
        logger.info("Adding 1 contestants");
        return contestantService.addContestants(Lists.newArrayList(contestant),
                true).get(0);
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/participants", method =
            RequestMethod.GET)
    public List<Contestant> getAllContestants() {
        return contestantService.getAllContestants();
    }

    @CrossOrigin
    @RequestMapping(path = "/contestant/{id}", method = RequestMethod.GET)
    public Contestant getContestant(@PathVariable String id) throws ContestantNotFoundException {
        logger.info("Received request for contestant with id " + id);
        return contestantService.getContestantById(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/contestants", method = RequestMethod.GET)
    public List<Contestant> getContestants(@RequestParam("ids") List<String> ids) {
        logger.info("Received request for contestant with ids " + ids);
        return contestantService.getContestantsById(ids);
    }

    @CrossOrigin
    @RequestMapping(path = "/contestant/stats/{id}", method = RequestMethod.GET)
    public ContestantStats getContestantStats(@PathVariable String id) throws ContestantNotFoundException {
        logger.info("Received request for stats for contestant with id " + id);
        return factsService.getStatsForContestant(id);
    }

}
