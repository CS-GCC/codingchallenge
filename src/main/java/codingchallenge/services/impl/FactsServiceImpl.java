package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.*;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class FactsServiceImpl implements FactsService {

    private final LeaderboardService leaderboardService;
    private final ContestantService contestantService;
    private final ServiceProperties serviceProperties;
    private final ArticleService articleService;
    private final ContestantRepository contestantRepository;
    private final TeamRepository teamRepository;
    private final AnswerService answerService;
    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(FactsService.class);

    @Autowired
    public FactsServiceImpl(LeaderboardService leaderboardService,
                            ContestantService contestantService,
                            ServiceProperties serviceProperties,
                            ArticleService articleService, ContestantRepository contestantRepository, TeamRepository teamRepository, AnswerService answerService, RestTemplate restTemplate) {
        this.leaderboardService = leaderboardService;
        this.contestantService = contestantService;
        this.serviceProperties = serviceProperties;
        this.articleService = articleService;
        this.contestantRepository = contestantRepository;
        this.teamRepository = teamRepository;
        this.answerService = answerService;
        this.restTemplate = restTemplate;
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
        ResponseEntity<Team> responseEntity =
                restTemplate.getForEntity(serviceProperties.getGlobal() +
                        "team/" + teamId, Team.class);
        teamStats.setLogo(responseEntity.getBody().getGitAvatar());
        teamStats.setTotalContestants(individualPositions.size());
        teamStats.setContestants(individualPositions.stream().limit(20).filter(p -> p.getTotal() > 0).collect(Collectors.toList()));
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

    @Override
    @Async
    public void getStatsForNewsletter(String oldInd,
                                                       String oldUni,
                                                       String newInd,
                                                       String newUni,
                                      String email, String newsletter) {
        logger.info("Getting old individuals");
        Map<String, IndividualPosition> oldIndividuals =
                leaderboardService.individualPositionsByLeaderboard(oldInd).stream().collect(Collectors.toMap(IndividualPosition::getContestantId, Function.identity()));
        List<IndividualPosition> newIndividuals =
                leaderboardService.individualPositionsByLeaderboard(newInd);
        logger.info("Getting old teams");
        Map<String, TeamPosition> oldTeams =
                leaderboardService.teamPositionsByLeaderboard(oldUni).stream().collect(Collectors.toMap(TeamPosition::getTeamId, Function.identity()));
        List<TeamPosition> newTeams =
                leaderboardService.teamPositionsByLeaderboard(newUni);
        Map<String, UniProperties> uniPropertiesMap = Maps.newHashMap();
        List<PersonalProperties> personalPropertiesList = Lists.newArrayList();
        for (TeamPosition team : newTeams) {
            TeamPosition old = oldTeams.get(team.getTeamId());
            if (old == null) {
                old = new TeamPosition(team.getPosition(), null, null);
            }
            uniPropertiesMap.put(team.getTeamId(), new UniProperties(
                    team.getTeamName(),
                    old.getPosition() - team.getPosition(),
                    team.getPosition()
            ));
        }
        logger.info("Generated uni property map");
        int i = 1;
        for (IndividualPosition position : newIndividuals) {
            logger.info("Doing position " + i + " of " + newIndividuals.size());
            i++;
            IndividualPosition old =
                    oldIndividuals.get(position.getContestantId());
            if (old == null) {
                old = new IndividualPosition(position.getPosition(), null,
                        null, null);
            }
            Contestant contestant =
                    contestantRepository.findContestantByGlobalId(position.getGlobalId()).get();
            PersonalProperties personalProperties = new PersonalProperties();
            personalProperties.setContestantId(position.getContestantId());
            personalProperties.setGitUsername(contestant.getGitUsername());
            personalProperties.setRank(position.getPosition());
            personalProperties.setHighestRank(leaderboardService.getPositionsForIndividual(position.getContestantId()).stream().min(Comparator.comparingInt(Position::getPosition)).get().getPosition());
            personalProperties.setPositionsGained(old.getPosition() - position.getPosition());
            UniProperties uniProperties =
                    uniPropertiesMap.get(position.getTeamId());
            personalProperties.setUniRank(uniProperties.rank);
            personalProperties.setUniversityGained(uniProperties.gained);
            personalProperties.setUniversity(uniProperties.name);
            personalPropertiesList.add(personalProperties);
        }
        logger.info("Generated individual property list");
        List<IndividualPosition> topTenInd =
                leaderboardService.getTopTenIndividuals(newInd);
        List<TeamPosition> topTenPos =
                leaderboardService.getTopTenTeams(newUni);
        Map<Integer, Integer> indChange = Maps.newHashMap();
        for (IndividualPosition pos : topTenInd) {
            IndividualPosition old = oldIndividuals.get(pos.getContestantId());
            indChange.put(
                    pos.getPosition(),
                    old != null ? old.getPos() - pos.getPosition() : 0
            );
        }
        Map<Integer, Integer> uniChange = Maps.newHashMap();
        for (TeamPosition pos : topTenPos) {
            TeamPosition old = oldTeams.get(pos.getTeamId());
            uniChange.put(
                    pos.getPosition(),
                    old != null ? old.getPos() - pos.getPosition() : 0
            );
        }
        logger.info("Generated change maps");
        LeaderboardProperties leaderboardProperties =
                new LeaderboardProperties(personalPropertiesList, indChange,
                uniChange);
        leaderboardProperties.setIndividualPositions(topTenInd);
        leaderboardProperties.setTeamPositions(topTenPos);
        restTemplate.postForEntity(serviceProperties.getGlobal() +
                "newsletter/test/" + newsletter + "/" + email,
                leaderboardProperties, Void.class);
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

    private class UniProperties {

        String name;
        int gained;
        int rank;

        public UniProperties(String name, int gained, int rank) {
            this.name = name;
            this.gained = gained;
            this.rank = rank;
        }
    }

}
