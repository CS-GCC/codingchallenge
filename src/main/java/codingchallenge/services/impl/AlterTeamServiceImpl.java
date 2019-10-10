package codingchallenge.services.impl;

import codingchallenge.collections.*;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Team;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.services.interfaces.AlterTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlterTeamServiceImpl implements AlterTeamService  {

    private final IndividualPositionRepository individualPositionRepository;
    private final TeamPositionRepository teamPositionRepository;
    private final ContestantRepository contestantRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public AlterTeamServiceImpl(IndividualPositionRepository individualPositionRepository, TeamPositionRepository teamPositionRepository, ContestantRepository contestantRepository, TeamRepository teamRepository) {
        this.individualPositionRepository = individualPositionRepository;
        this.teamPositionRepository = teamPositionRepository;
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public Team alterTeam(String id, String name) {
        Team team = teamRepository.findById(id).get();
        List<IndividualPosition> individualPositions =
                individualPositionRepository.findAllByTeamId(id);
        List<TeamPosition> teamPositions =
                teamPositionRepository.findAllByTeamId(id);
        List<Contestant> contestants =
                contestantRepository.findContestantsByTeamId(id);
        for (IndividualPosition individualPosition : individualPositions) {
            individualPosition.setTeamName(name);
        }
        for (TeamPosition teamPosition : teamPositions) {
            teamPosition.setTeamName(name);
        }
        for (Contestant contestant : contestants) {
            contestant.setTeam(name);
        }
        team.setName(name);
        individualPositionRepository.saveAll(individualPositions);
        teamPositionRepository.saveAll(teamPositions);
        contestantRepository.saveAll(contestants);
        return teamRepository.save(team);
    }
}
