package codingchallenge.services.interfaces;

import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.LeaderboardDTO;
import codingchallenge.domain.TotalMap;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import com.google.common.collect.Multimap;

import java.util.List;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface LeaderboardService {

    LeaderboardDTO getLatestIndividualLeaderboard(int from, int limit);

    LeaderboardDTO getLatestTeamLeaderboard(int from, int limit);

    LeaderboardDTO getFilteredIndividualLeaderboard(String searchTerm, int from,
                                                 int limit);

    LeaderboardDTO getFilteredTeamLeaderboard(String searchTerm, int from,
                                           int limit);

    String generateLeaderboard(Multimap<String, Score> scoreMultimap) throws ContestantNotFoundException;

    String generateTeamLeaderboard(String leaderboard,
                                        int numberOfQuestions) throws ContestantNotFoundException;

    int positionWithinTeam(String teamId, String contestantId);

    Leaderboard getLeaderboard();

    Leaderboard getTeamLeaderboard();

    TeamPosition getLatestPositionForTeam(String teamId);

    IndividualPosition getLatestPositionForIndividual(String id);

    List<IndividualPosition> getLatestIndividualPositionsForTeam(String id);

    TotalMap getContestantTotals(List<String> ids);

    String individualLeaderboardId();

    String teamLeaderboardId();

    String leadingIndividual();

    String leadingTeam();

    List<TeamPosition> getTopTeams(int numberOfUniversities);

    List<IndividualPosition> getTopIndividuals(int numberOfIndividuals);

    List<IndividualPosition> getPositionsForIndividual(String id);

    List<TeamPosition> getPositionsForTeam(String id);

    List<IndividualPosition> getTeamContestantsLatestPosition(List<String> ids);
}
