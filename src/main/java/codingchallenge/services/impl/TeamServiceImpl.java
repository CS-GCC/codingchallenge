package codingchallenge.services.impl;

import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Team;
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


}
