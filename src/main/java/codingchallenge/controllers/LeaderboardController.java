package codingchallenge.controllers;

import codingchallenge.domain.Leaderboard;
import codingchallenge.services.interfaces.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard", method = RequestMethod.GET)
    public Leaderboard latestLeaderboard() {
        return leaderboardService.getLatestIndividualLeaderboard();
    }

    @CrossOrigin
    @RequestMapping(path = "/leaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public Leaderboard filteredLeaderboard(@PathVariable String searchTerm) {
        return leaderboardService.getFilteredIndividualLeaderboard(searchTerm);
    }

    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard", method = RequestMethod.GET)
    public Leaderboard latestTeamLeaderboard() {
        return leaderboardService.getLatestTeamLeaderboard();
    }

    @CrossOrigin
    @RequestMapping(path = "/teamleaderboard/search/{searchTerm}", method = RequestMethod.GET)
    public Leaderboard filteredTeamLeaderboard(@PathVariable String searchTerm) {
        return leaderboardService.getFilteredTeamLeaderboard(searchTerm);
    }

}
