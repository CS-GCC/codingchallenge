package codingchallenge.jobs;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Status;
import codingchallenge.engines.interfaces.RunAll;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ChallengeInBounds;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    private final RunAll runAll;
    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final TeamService teamService;
    private final ChallengeInBounds challengeInBounds;

    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    public Runner(RunAll runAll, LeaderboardService leaderboardService,
                  ContestantService contestantService, TeamService teamService,
                  ChallengeInBounds challengeInBounds) {
        this.runAll = runAll;
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.teamService = teamService;
        this.challengeInBounds = challengeInBounds;
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 600000)
    public void runner() throws ContestantNotFoundException {
        logger.info("Beginning new run");
        if (challengeInBounds.challengeInBounds() == Status.IN_PROGRESS) {
            Leaderboard leaderboard = runAll.runAll();
            Leaderboard universityLeaderboard =
                    leaderboardService.generateTeamLeaderboard(leaderboard, 6);
            logger.info("Generated leaderboards");
            leaderboardService.saveLeaderboard(leaderboard);
            leaderboardService.saveTeamLeaderboard(universityLeaderboard);
            logger.info("Saved leaderboards");
            contestantService.generateTimeStampedPositions(leaderboard);
            teamService.generateTimeStampedPositions(universityLeaderboard);
            logger.info("Generated time stamped positions");
        } else {
            logger.info("Run not needed. Challenge status is not in bounds");
        }
        logger.info("Run completed successfully");

    }

}
