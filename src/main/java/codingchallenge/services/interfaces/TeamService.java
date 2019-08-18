package codingchallenge.services.interfaces;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Team;
import codingchallenge.domain.subdomain.Region;
import codingchallenge.exceptions.ContestantNotFoundException;

import java.util.List;

public interface TeamService {

    Team getTeamById(String id) throws ContestantNotFoundException;

    String getTeamNameById(String id) throws ContestantNotFoundException;

    void generateTimeStampedPositions(Leaderboard teamLeaderboard);

    void addToTeams(List<Contestant> contestants);

    List<Team> findByIdNotIn(List<String> ids);

    int getTeamPosition(String teamId);

    List<Team> addTeams(List<Team> teams);

}
