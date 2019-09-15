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

import java.util.Date;

@Component
public class Runner {

    private final RunAll runAll;
    private final LeaderboardService leaderboardService;
    private final ChallengeInBounds challengeInBounds;

    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    public Runner(RunAll runAll, LeaderboardService leaderboardService,
                  ChallengeInBounds challengeInBounds) {
        this.runAll = runAll;
        this.leaderboardService = leaderboardService;
        this.challengeInBounds = challengeInBounds;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 600000)
    public void runner() throws ContestantNotFoundException {
        logger.info("Beginning new run. Current time: " + new Date());
        long startTime = System.currentTimeMillis();
        if (challengeInBounds.challengeInBounds() == Status.IN_PROGRESS) {
            String leaderboard = runAll.runAll();
            logger.info("Generated individual leaderboard with id " + leaderboard);
            String universityLeaderboard =
                    leaderboardService.generateTeamLeaderboard(leaderboard, 6);
            logger.info("Generated university leaderboard with id " + universityLeaderboard);
        } else {
            logger.info("Run not needed. Challenge status is not in bounds");
        }
        long endTime = System.currentTimeMillis();
        logger.info("Run completed successfully in " + ((endTime - startTime)/60000.0) + " minutes.");

    }

}
