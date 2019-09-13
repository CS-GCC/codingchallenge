package codingchallenge.services.interfaces;

import codingchallenge.domain.Team;
import codingchallenge.exceptions.ContestantNotFoundException;

import java.util.List;

public interface TeamService {

    Team getTeamById(String id) throws ContestantNotFoundException;

    List<Team> addTeams(List<Team> teams);

}
