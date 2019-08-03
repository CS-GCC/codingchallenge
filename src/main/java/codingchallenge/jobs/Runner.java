package codingchallenge.jobs;

import codingchallenge.domain.Leaderboard;
import codingchallenge.engines.interfaces.RunAll;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    private final RunAll runAll;
    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final TeamService teamService;

    @Autowired
    public Runner(RunAll runAll, LeaderboardService leaderboardService,
                  ContestantService contestantService, TeamService teamService) {
        this.runAll = runAll;
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.teamService = teamService;
    }

    public void runner() throws ContestantNotFoundException {
        Leaderboard leaderboard = runAll.runAll();
        Leaderboard universityLeaderboard =
                leaderboardService.generateTeamLeaderboard(leaderboard, 6);
        leaderboardService.saveLeaderboard(leaderboard);
        leaderboardService.saveTeamLeaderboard(universityLeaderboard);
        contestantService.generateTimeStampedPositions(leaderboard);
        teamService.generateTimeStampedPositions(universityLeaderboard);
    }

}
