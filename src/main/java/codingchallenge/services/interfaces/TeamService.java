package codingchallenge.services.interfaces;

import codingchallenge.domain.Team;
import codingchallenge.domain.TeamImage;
import codingchallenge.exceptions.ContestantNotFoundException;

import java.util.List;

public interface TeamService {

    Team getTeamById(String id) throws ContestantNotFoundException;

    List<Team> addTeams(List<Team> teams);

    void addImageUrl(List<TeamImage> images);

    List<String> getImagelessTeams();

    int imagelessTeamCount();

}
