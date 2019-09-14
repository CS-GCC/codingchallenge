package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TeamPositionRepository;
import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.*;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FactsServiceImpl implements FactsService {

    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final ServiceProperties serviceProperties;
    private final ArticleService articleService;
    private final ContestantRepository contestantRepository;
    private final TeamRepository teamRepository;
    private final AnswerService answerService;

    @Autowired
    public FactsServiceImpl(LeaderboardService leaderboardService,
                            ContestantService contestantService,
                            ServiceProperties serviceProperties,
                            ArticleService articleService, ContestantRepository contestantRepository, TeamRepository teamRepository, AnswerService answerService) {
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.serviceProperties = serviceProperties;
        this.articleService = articleService;
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
        this.answerService = answerService;
    }

    @Override
    public RegionFacts getRegionFacts() {
        List<IndividualPosition> individualContestants =
                getIndividualPositions(5);
        List<TeamPosition> universityContestants =
                getUniversityPositions(5);
        List<Headline> headlines = articleService.getLatestArticles(0, 5);
        return new RegionFacts(individualContestants, universityContestants,
                headlines);
    }

    @Override
    public QuickFacts getQuickFacts() {
        long numberOfContestants = contestantService.getNumberOfContestants();
        return new QuickFacts(numberOfContestants, leaderboardService.leadingIndividual(),
                leaderboardService.leadingTeam(), serviceProperties.getRegion());
    }

    @Override
    public LeaderFacts getLeaderFacts() {
        List<Contestant> universityContestants =
                getUniversityContestants(serviceProperties.getUniversities());
        List<Contestant> individualContestants =
                getIndividualContestants(serviceProperties.getContestants());
        Set<String> contestants = Sets.newHashSet();
        contestants.addAll(individualContestants.stream().map(Contestant::getId).collect(Collectors.toSet()));
        contestants.addAll(universityContestants.stream().map(Contestant::getId).collect(Collectors.toSet()));
        List<Answer> answers =
                answerService.getAnswersForContestants(contestants);
        return new LeaderFacts(individualContestants,
                universityContestants, answers);
    }

    @Override
    public TeamStats getStatsForTeam(String teamId) throws ContestantNotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ContestantNotFoundException(teamId);
        }
        TeamPosition teamPosition =
                leaderboardService.getLatestPositionForTeam(teamId);
        TeamStats teamStats = new TeamStats(teamPosition,
                serviceProperties.getRegion());
        List<IndividualPosition> individualPositions =
                leaderboardService.getLatestIndividualPositionsForTeam(teamId);
        teamStats.setTotalContestants(individualPositions.size());
        teamStats.setContestants(individualPositions.stream().limit(20).collect(Collectors.toList()));
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
        IndividualPosition individualPosition =
                leaderboardService.getLatestPositionForIndividual(contestant.getId());
        contestantStats.setPosition(individualPosition.getPosition());
        contestantStats.setScores(individualPosition.getScores());
        contestantStats.setTotal(individualPosition.getTotal());
        contestantStats.setPositionWithinTeam(leaderboardService.positionWithinTeam(contestant.getTeamId(), contestant.getId()));
        contestantStats.setTeamPosition(leaderboardService.getLatestPositionForTeam(contestant.getTeamId()).getPosition());
        return contestantStats;
    }

    private List<TeamPosition> getUniversityPositions(int numberOfUniversities) {
        return leaderboardService.getTopTeams(numberOfUniversities);
    }

    private List<Contestant> getUniversityContestants(int numberOfUniversities) {
        List<TeamPosition> teamPositions =
                getUniversityPositions(numberOfUniversities);
        List<String> teamContestantIds = Lists.newArrayList();
        for (TeamPosition pos : teamPositions) {
            teamContestantIds.addAll(leaderboardService.getLatestIndividualPositionsForTeam(pos.getTeamId()).stream().limit(20).map(IndividualPosition::getContestantId).collect(Collectors.toList()));
        }
        return contestantService.getContestantsById(teamContestantIds);
    }

    private List<IndividualPosition> getIndividualPositions(int numberOfIndividuals) {
        return leaderboardService.getTopIndividuals(numberOfIndividuals);
    }

    private List<Contestant> getIndividualContestants(int numberOfIndividuals) {
        List<IndividualPosition> individualPositions =
                getIndividualPositions(numberOfIndividuals);
        List<String> contestantIds = individualPositions.stream().map(
                IndividualPosition::getContestantId
        ).collect(Collectors.toList());
        return contestantService.getContestantsById(contestantIds);
    }

}
