package codingchallenge.controllers;

import codingchallenge.domain.LeaderFacts;
import codingchallenge.domain.QuickFacts;
import codingchallenge.domain.RegionFacts;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.FactsService;
import codingchallenge.services.interfaces.FileService;
import codingchallenge.services.interfaces.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FactsController {

    private final FactsService factsService;

    private final InitialisationService fileService;

    @Autowired
    public FactsController(FactsService factsService, InitialisationService fileService) {
        this.factsService = factsService;
        this.fileService = fileService;
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

    @CrossOrigin
    @RequestMapping(path = "/file/addtogit", method = RequestMethod.GET)
    public void addToGit() throws IOException {
//        fileService.completeInitialisation(null, "123456");
//        for (Language language : Language.values()) {
//            fileService.getFolderContent(language);
//        }
    }


}
