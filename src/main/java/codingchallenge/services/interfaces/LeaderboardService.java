package codingchallenge.services.interfaces;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import com.google.common.collect.Multimap;

import java.util.List;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface LeaderboardService {

    Leaderboard getLatestIndividualLeaderboard(int from, int limit);

    Leaderboard getLatestTeamLeaderboard(int from, int limit);

    Leaderboard getFilteredIndividualLeaderboard(String searchTerm, int from,
                                                 int limit);

    Leaderboard getFilteredTeamLeaderboard(String searchTerm, int from,
                                           int limit);

    Leaderboard generateLeaderboard(Multimap<String, Score> scoreMultimap) throws ContestantNotFoundException;

    Leaderboard generateTeamLeaderboard(Leaderboard leaderboard,
                                        int numberOfQuestions) throws ContestantNotFoundException;

    void saveLeaderboard(Leaderboard leaderboard);

    void saveTeamLeaderboard(Leaderboard leaderboard);

    int positionWithinTeam(String teamId, String contestantId);

    Leaderboard getLeaderboard();

    Leaderboard getTeamLeaderboard();

    TeamPosition getLatestPositionForTeam(String teamId);

    IndividualPosition getLatestPositionForIndividual(String id);

    double getContestantTotals(List<String> ids);
}
