package codingchallenge.controllers;

import codingchallenge.domain.Leaderboard;
import codingchallenge.services.interfaces.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService
                                 ) {
        this.leaderboardService = leaderboardService;
//        this.git = git;
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard", method = RequestMethod.GET)
    public Leaderboard latestLeaderboard(@RequestParam("from") int from,
                                         @RequestParam("limit") int limit) {
        return leaderboardService.getLatestIndividualLeaderboard(from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public Leaderboard filteredLeaderboard(@PathVariable String searchTerm, @RequestParam("from") int from,
                                           @RequestParam("limit") int limit) {
        return leaderboardService.getFilteredIndividualLeaderboard(searchTerm
                , from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard", method = RequestMethod.GET)
    public Leaderboard latestTeamLeaderboard(@RequestParam("from") int from,
                                             @RequestParam("limit") int limit) {
        return leaderboardService.getLatestTeamLeaderboard(from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public Leaderboard filteredTeamLeaderboard(@PathVariable String searchTerm, @RequestParam("from") int from,
                                               @RequestParam("limit") int limit) {
        return leaderboardService.getFilteredTeamLeaderboard(searchTerm,
                from, limit);
    }

}
