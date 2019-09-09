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
    public String getTeamNameById(String id) throws ContestantNotFoundException {
        return getTeamById(id).getName();
    }

    @Override
    public void generateTimeStampedPositions(Leaderboard teamLeaderboard) {
        List<Position> positions = teamLeaderboard.getContestants();
        Date timestamp = teamLeaderboard.getTimestamp();
        for (Position pos : positions) {
            TeamPosition position = (TeamPosition) pos;
            try {
                Team team =
                        getTeamById(position.getTeamId());
                TimestampPositionGenerator.addTimeStampPosition(team,
                        position, timestamp);
                teamRepository.save(team);
            } catch (ContestantNotFoundException e) {
                logger.error("Contestant not found", e);
            }
        }
        logger.info("Generated time stamped positions");
    }

    @Override
    public void addToTeams(List<Contestant> contestants) {
        Date timestamp = new Date();
        for (Contestant contestant : contestants) {
            Optional<Team> teamOptional =
                    teamRepository.findById(contestant.getTeamId());
            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                List<Registration> registrations = team.getRegisteredContestants();
                if (registrations == null) {
                    registrations = Lists.newArrayList();
                    team.setRegisteredContestants(registrations);
                }
                registrations.add(new Registration(contestant.getId(),
                        timestamp));
                teamRepository.save(team);
            }
        }
    }

    @Override
    public List<Team> addTeams(List<Team> teams) {
        return teamRepository.insert(teams);
    }

    @Override
    public List<Team> findByIdNotIn(List<String> ids) {
        return teamRepository.findByIdNotIn(ids);
    }

    @Override
    public int getTeamPosition(String teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            return -1;
        }
        List<TimeStampPosition> positions = teamOptional.get().getPositions();
        return positions.get(positions.size()-1).getPosition();
    }

}
