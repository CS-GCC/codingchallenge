package codingchallenge.services.interfaces;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.exceptions.ContestantNotFoundException;
import com.google.common.collect.Multimap;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface LeaderboardService {

    Leaderboard getLatestIndividualLeaderboard();

    Leaderboard getLatestTeamLeaderboard();

    Leaderboard getFilteredIndividualLeaderboard(String searchTerm);

    Leaderboard getFilteredTeamLeaderboard(String searchTerm);

    Leaderboard generateLeaderboard(Multimap<String, Score> scoreMultimap) throws ContestantNotFoundException;

    void generateTeamLeaderboard(Leaderboard leaderboard,
                                 int numberOfQuestions);
}
