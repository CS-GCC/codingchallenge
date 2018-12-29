package codingchallenge.services.interfaces;

import codingchallenge.domain.Leaderboard;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface LeaderboardService {

    Leaderboard getLatestIndividualLeaderboard();

    Leaderboard getLatestTeamLeaderboard();

    Leaderboard getFilteredIndividualLeaderboard(String searchTerm);

    Leaderboard getFilteredTeamLeaderboard(String searchTerm);
}
