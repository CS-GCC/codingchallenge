package codingchallenge.controllers;

import codingchallenge.domain.LeaderFacts;
import codingchallenge.domain.QuickFacts;
import codingchallenge.domain.RegionFacts;
import codingchallenge.services.interfaces.FactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FactsController {

    private final FactsService factsService;

    @Autowired
    public FactsController(FactsService factsService) {
        this.factsService = factsService;
    }

    @CrossOrigin
    @RequestMapping(path = "/facts/quick", method = RequestMethod.GET)
    public QuickFacts getQuickFacts() {
        return factsService.getQuickFacts();
    }

    @CrossOrigin
    @RequestMapping(path = "/facts/region", method = RequestMethod.GET)
    public RegionFacts getRegionFacts() {
        return factsService.getRegionFacts();
    }

    @CrossOrigin
    @RequestMapping(path = "/facts/leader", method = RequestMethod.GET)
    public LeaderFacts getLeaderFacts() {
        return factsService.getLeaderFacts();
    }


}
