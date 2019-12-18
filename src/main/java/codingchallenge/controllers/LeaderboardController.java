package codingchallenge.controllers;

import codingchallenge.domain.LeaderboardDTO;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.jobs.Runner;
import codingchallenge.services.interfaces.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final Runner runner;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService,
                                 Runner runner) {
        this.leaderboardService = leaderboardService;
//        this.git = git;
        this.runner = runner;
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard", method = RequestMethod.GET)
    public LeaderboardDTO latestLeaderboard(@RequestParam("from") int from,
                                            @RequestParam("limit") int limit) {
        return leaderboardService.getLatestIndividualLeaderboard(from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/getById/{id}", method = RequestMethod.GET)
    public LeaderboardDTO leaderboardById(@RequestParam("from") int from,
                                          @RequestParam("limit") int limit,
                                          @PathVariable String id) {
        return leaderboardService.getLeaderboardById(id, from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public LeaderboardDTO filteredLeaderboard(@PathVariable String searchTerm,
                                 @RequestParam("from") int from,
                                           @RequestParam("limit") int limit) {
        return leaderboardService.getFilteredIndividualLeaderboard(searchTerm
                , from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/save", method =
            RequestMethod.GET)
    public List<LeaderboardDTO> saveLeaderboard() {
        return leaderboardService.save();
    }


    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard", method = RequestMethod.GET)
    public LeaderboardDTO latestTeamLeaderboard(@RequestParam("from") int from,
                                             @RequestParam("limit") int limit) {
        return leaderboardService.getLatestTeamLeaderboard(from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public LeaderboardDTO filteredTeamLeaderboard(@PathVariable String searchTerm,
                                     @RequestParam("from") int from,
                                               @RequestParam("limit") int limit) {
        return leaderboardService.getFilteredTeamLeaderboard(searchTerm,
                from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/oneTimeRun/{id}", method =
            RequestMethod.GET)
    public void filteredTeamLeaderboard(@PathVariable String id) throws ContestantNotFoundException {
        if (id.equals("28193201")) {
            runner.oneTimeRun();
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/shortfall/{id}", method =
            RequestMethod.GET)
    public List<String> findShortfall(@PathVariable String id) throws ContestantNotFoundException {
        return leaderboardService.findShortfall(id);
    }

}
