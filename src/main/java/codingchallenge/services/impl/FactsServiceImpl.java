package codingchallenge.services.impl;

import codingchallenge.ServiceProperties;
import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.*;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.FactsService;
import codingchallenge.services.interfaces.LeaderboardService;
import codingchallenge.services.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FactsServiceImpl implements FactsService {

    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final ServiceProperties serviceProperties;
    private final TeamService teamService;
    private final ContestantRepository contestantRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public FactsServiceImpl(LeaderboardService leaderboardService,
                            ContestantService contestantService,
                            ServiceProperties serviceProperties,
                            TeamService teamService, ContestantRepository contestantRepository, TeamRepository teamRepository) {
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.serviceProperties = serviceProperties;
        this.teamService = teamService;
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public RegionFacts getRegionFacts() {
        List<Contestant> individualContestants =
                getIndividualContestants(serviceProperties.getContestants());
        List<Contestant> universityContestants =
                getUniversityContestants(serviceProperties.getUniversities());
        return new RegionFacts(individualContestants, universityContestants);
    }

    @Override
    public QuickFacts getQuickFacts() {
        long numberOfContestants = contestantService.getNumberOfContestants();
        List<Contestant> contestants = getIndividualContestants(1);
        List<Position> universityPositions = getUniversityPositions(1);
        String contestant = contestants.isEmpty() ? null :
                contestants.get(0).getName();
        String universityPosition = universityPositions.isEmpty() ? null :
                ((TeamPosition) universityPositions.get(0)).getTeamName();

        return new QuickFacts(numberOfContestants, contestant,
                universityPosition);
    }

    @Override
    public TeamStats getStatsForTeam(String teamId) throws ContestantNotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ContestantNotFoundException(teamId);
        }
        Team team = teamOptional.get();
        TeamStats teamStats = new TeamStats(team,
                serviceProperties.getRegion());
        TeamPosition teamPosition =
                leaderboardService.getLatestPositionForTeam(teamId);
        List<IndividualPosition> individualPositions =
                teamPosition.getContestants().stream().map(leaderboardService::getLatestPositionForIndividual).collect(Collectors.toList());
        teamStats.setContestants(individualPositions);
        return teamStats;
    }

    @Override
    public ContestantStats getStatsForContestant(String id) throws ContestantNotFoundException {
        Optional<Contestant> contestantOptional = contestantRepository.findById(id);
        if (!contestantOptional.isPresent()) {
            throw new ContestantNotFoundException(id);
        }
        Contestant contestant = contestantOptional.get();
        ContestantStats contestantStats = new ContestantStats(contestant,
                serviceProperties.getRegion());
        contestantStats.setPositionWithinTeam(leaderboardService.positionWithinTeam(contestant.getTeamId(), contestant.getId()));
        contestantStats.setTeamPosition(teamService.getTeamPosition(contestant.getTeamId()));
        return contestantStats;
    }

    private List<Position> getUniversityPositions(int numberOfUniversities) {
        Leaderboard teamLeaderboard =
                leaderboardService.getLatestTeamLeaderboard(0, numberOfUniversities);
        return teamLeaderboard.getPositions();
    }

    private List<Contestant> getUniversityContestants(int numberOfUniversities) {
        List<Position> teamPositions =
                getUniversityPositions(numberOfUniversities);
        List<String> teamContestantIds = teamPositions.stream().map(
                pos -> ((TeamPosition) pos).getContestants()
        ).flatMap(List::stream).collect(Collectors.toList());
        return contestantService.getContestantsById(teamContestantIds);
    }

    private List<Contestant> getIndividualContestants(int numberOfIndividuals) {
        Leaderboard leaderboard =
                leaderboardService.getLatestIndividualLeaderboard(0, numberOfIndividuals);
        List<Position> individualPositions =
                leaderboard.getPositions();
        List<String> contestantIds = individualPositions.stream().map(
                pos -> ((IndividualPosition) pos).getContestantId()
        ).collect(Collectors.toList());
        return contestantService.getContestantsById(contestantIds);
    }

}
