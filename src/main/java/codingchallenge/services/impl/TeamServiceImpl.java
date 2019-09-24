package codingchallenge.services.impl;

import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Team;
import codingchallenge.domain.TeamImage;
import codingchallenge.domain.subdomain.*;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.TeamService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final Logger logger =
            LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team getTeamById(String id) throws ContestantNotFoundException {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get();
        }
        throw new ContestantNotFoundException(id);
    }

    @Override
    public List<Team> addTeams(List<Team> teams) {
        return teamRepository.insert(teams);
    }

    @Override
    public void addImageUrl(List<TeamImage> images) {
        for (TeamImage teamImage : images) {
            Optional<Team> teamOptional =
                    teamRepository.findByName(teamImage.getName());
            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                team.setGitAvatar(teamImage.getImageUrl());
                teamRepository.save(team);
            }
        }
    }

    @Override
    public List<String> getImagelessTeams() {
        return teamRepository.findAll().stream().filter(team -> team.getGitAvatar() == null || team.getGitAvatar().isEmpty()).map(Team::getName).collect(Collectors.toList());
    }

    @Override
    public int imagelessTeamCount() {
        return getImagelessTeams().size();
    }

}
