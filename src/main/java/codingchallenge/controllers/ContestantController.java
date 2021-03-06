package codingchallenge.controllers;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.ContestantStats;
import codingchallenge.domain.TotalMap;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.FactsService;
import codingchallenge.services.interfaces.GitHubService;
import codingchallenge.services.interfaces.LeaderboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContestantController {

    private final ContestantService contestantService;
    private final FactsService factsService;
    private final LeaderboardService leaderboardService;
    private final GitHubService gitHubService;

    private final Logger logger =
            LoggerFactory.getLogger(ContestantController.class);

    @Autowired
    public ContestantController(ContestantService contestantService,
                                FactsService factsService,
                                LeaderboardService leaderboardService,
                                GitHubService gitHubService) {
        this.contestantService = contestantService;
        this.factsService = factsService;
        this.leaderboardService = leaderboardService;
        this.gitHubService = gitHubService;
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/addnames", method = RequestMethod.POST)
    public List<Contestant> addContestants(@RequestBody List<Contestant> contestants) {
        logger.info("Adding " + contestants.size() + " contestants");
        return contestantService.addContestants(contestants, true);
    }

    @CrossOrigin
    @RequestMapping(path = "/challenge/addname/{travisUUID}", method =
            RequestMethod.POST)
    public Contestant addContestant(@RequestBody Contestant contestant,
                                    @PathVariable String travisUUID) {
        logger.info("Adding 1 contestants");
        return contestantService.addContestant(contestant,
                true, travisUUID);
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

    @CrossOrigin
    @RequestMapping(path = "/contestants/total", method = RequestMethod.POST)
    public TotalMap getContestantTotals(@RequestBody List<String> ids) {
        return leaderboardService.getContestantTotals(ids);
    }

    @CrossOrigin
    @RequestMapping(path = "/contestantswithoutgit", method = RequestMethod.GET)
    public long withoutGit() {
        return contestantService.getContestantsWithoutGit();
    }

    @CrossOrigin
    @RequestMapping(path = "/updateenvs", method = RequestMethod.GET)
    public void updateGit() {
        gitHubService.updateTravisEnvVar();
    }


}
