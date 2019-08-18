package codingchallenge.controllers;

import codingchallenge.domain.Team;
import codingchallenge.domain.TeamStats;
import codingchallenge.domain.subdomain.Region;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.FactsService;
import codingchallenge.services.interfaces.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;
    private final FactsService factsService;

    private Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    public TeamController(TeamService teamService, FactsService factsService) {
        this.teamService = teamService;
        this.factsService = factsService;
    }

    @CrossOrigin
    @RequestMapping(path = "/team/{id}", method = RequestMethod.GET)
    public Team getContestant(@PathVariable String id) throws ContestantNotFoundException {
        logger.info("Received request for team with id " + id);
        return teamService.getTeamById(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/team/stats/{id}", method = RequestMethod.GET)
    public TeamStats getContestantStats(@PathVariable String id) throws ContestantNotFoundException {
        logger.info("Received request for stats for contestant with id " + id);
        return factsService.getStatsForTeam(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/team/add", method = RequestMethod.POST)
    public List<Team> addTeams(@RequestBody List<Team> teams) {
        return teamService.addTeams(teams);

    }

}
