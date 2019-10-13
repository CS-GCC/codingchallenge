package codingchallenge.services.impl;

import codingchallenge.collections.*;
import codingchallenge.domain.*;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.Score;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.LeaderboardService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final IndividualPositionRepository individualPositionRepository;
    private final TeamPositionRepository teamPositionRepository;
    private final QuickFindRepository quickFindRepository;
    private final ContestantService contestantService;
    private final TeamRepository teamRepository;

    private final Logger logger =
            LoggerFactory.getLogger(LeaderboardServiceImpl.class);

    @Autowired
    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository
            ,
                                  IndividualPositionRepository individualPositionRepository, TeamPositionRepository teamPositionRepository, QuickFindRepository quickFindRepository, ContestantService contestantService,
                                  TeamRepository teamRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.individualPositionRepository = individualPositionRepository;
        this.teamPositionRepository = teamPositionRepository;
        this.quickFindRepository = quickFindRepository;
        this.contestantService = contestantService;
        this.teamRepository = teamRepository;
    }

    @Override
    public LeaderboardDTO getLatestIndividualLeaderboard(int from, int limit) {
        Leaderboard leaderboard = getLeaderboard();
        String leaderboardId = leaderboard.getId();
        List<IndividualPosition> individualPositions = Lists.newArrayList();
        if (leaderboardId != null) {
            individualPositions = individualPositionRepository
                    .findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboardId, from, from+limit);
        }
        return new LeaderboardDTO(leaderboard, individualPositions);
    }

    @Override
    public LeaderboardDTO getLatestTeamLeaderboard(int from, int limit) {
        Leaderboard leaderboard = getTeamLeaderboard();
        String leaderboardId = leaderboard.getId();
        List<TeamPosition> teamPositions = Lists.newArrayList();
        if (leaderboardId != null) {
            teamPositions = teamPositionRepository
                    .findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboardId, from, from+limit);
        }
        return new LeaderboardDTO(leaderboard, teamPositions);
    }

    @Override
    public Leaderboard getTeamLeaderboard() {
        String leaderboardId = teamLeaderboardId();
        if (leaderboardId == null) {
            return generatePlainTeamLeaderboard();
        }
        return leaderboardRepository.findById(leaderboardId).get();
    }

    @Override
    public TeamPosition getLatestPositionForTeam(String teamId) {
        String leaderboardId = teamLeaderboardId();
        Optional<TeamPosition> teamPosition =
                teamPositionRepository.findByLeaderboardIdAndTeamId(leaderboardId, teamId);
        return teamPosition.orElse(null);
    }

    @Override
    public IndividualPosition getLatestPositionForIndividual(String id) {
        String leaderboardId = individualLeaderboardId();
        Optional<IndividualPosition> individualPosition =
                individualPositionRepository.findByLeaderboardIdAndContestantId(leaderboardId, id);
        return individualPosition.orElse(null);
    }

    @Override
    public List<IndividualPosition> getLatestIndividualPositionsForTeam(String id) {
        String leaderboardId = individualLeaderboardId();
        return individualPositionRepository.findAllByLeaderboardIdAndTeamIdOrderByTotalDesc(leaderboardId, id);
    }

    @Override
    public Leaderboard getLeaderboard() {
        String leaderboardId = individualLeaderboardId();
        if (leaderboardId == null) {
            return generatePlainLeaderboard();
        }
        return leaderboardRepository.findById(leaderboardId).get();
    }

    @Override
    public LeaderboardDTO getFilteredIndividualLeaderboard(String searchTerm,
                                                        int from, int limit) {
        Leaderboard leaderboard = getLeaderboard();
        List<IndividualPosition> individualPositions =
                individualPositionRepository
                        .findAllByLeaderboardId(leaderboard.getId())
                        .stream()
                        .filter(pos -> searchPredicate(searchTerm, pos))
                        .collect(Collectors.toList());
        int total = individualPositions.size();
        leaderboard.setTotalContestants(total);
        individualPositions = individualPositions
                .stream()
                .skip(from)
                .limit(limit)
                .collect(Collectors.toList());
        return new LeaderboardDTO(leaderboard, individualPositions);
    }

    @Override
    public LeaderboardDTO getFilteredTeamLeaderboard(String searchTerm, int from
            , int limit) {
        Leaderboard leaderboard = getTeamLeaderboard();
        List<TeamPosition> teamPositions =
                teamPositionRepository
                        .findAllByLeaderboardId(leaderboard.getId())
                        .stream()
                        .filter(pos -> teamSearchPredicate(searchTerm, pos))
                        .collect(Collectors.toList());
        int total = teamPositions.size();
        leaderboard.setTotalContestants(total);
        teamPositions = teamPositions
                .stream()
                .skip(from)
                .limit(limit)
                .collect(Collectors.toList());
        return new LeaderboardDTO(leaderboard, teamPositions);
    }

    @Override
    public List<IndividualPosition> individualPositionsByLeaderboard(String leaderboardId) {
        return individualPositionRepository.findAllByLeaderboardId(leaderboardId);
    }

    @Override
    public List<TeamPosition> teamPositionsByLeaderboard(String leaderboardId) {
        return teamPositionRepository.findAllByLeaderboardId(leaderboardId);
    }

    @Override
    public List<IndividualPosition> getTopTenIndividuals(String leaderboardId) {
        return individualPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboardId, 1, 11);
    }

    @Override
    public List<TeamPosition> getTopTenTeams(String leaderboardId) {
        return teamPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboardId, 1, 11);
    }

    @Override
    public String generateLeaderboard(Multimap<String, Score> scoreMultimap) throws ContestantNotFoundException {
        Date timestamp = new Date();
        Leaderboard leaderboard = new Leaderboard(timestamp);
        leaderboard.setType(Type.INDIVIDUAL);
        leaderboard.setTotalContestants(scoreMultimap.keySet().size());
        leaderboard = leaderboardRepository.insert(leaderboard);
        String id = leaderboard.getId();
        updateQuickFinds(id, Type.INDIVIDUAL);
        logger.info("New individual leaderboard generated and inserted with " +
                "id " + id);
        List<IndividualPosition> positions = Lists.newArrayList();
        for (String contestantId : scoreMultimap.keySet()) {
            logger.info("Generating position for " + contestantId);
            Collection<Score> scores = scoreMultimap.get(contestantId);
            Contestant contestant =
                    contestantService.getContestantById(contestantId);
            IndividualPosition position = new IndividualPosition(-1, contestantId,
                    contestant.getName(), contestantId);
            position.setLeaderboardId(id);
            position.setScores(Lists.newArrayList(scores));
            position.setTotal(scores.stream().mapToDouble(Score::getTotal).sum());
            position.setTeamId(contestant.getTeamId());
            position.setTeamName(contestant.getTeam());
            position.setTimestamp(timestamp);
            position.setGlobalId(contestant.getGlobalId());
            positions.add(position);
        }
        logger.info("Sorting individual positions");
        sortPositions(positions);
        individualPositionRepository.insert(positions);
        logger.info("Inserted individual positions");
        return id;
    }

    @Override
    public String generateTeamLeaderboard(String individualLeaderboard,
                                               int numberOfQuestions) throws ContestantNotFoundException {
        List<TeamPosition> positions = Lists.newArrayList();
        List<IndividualPosition> individualPositions =
                individualPositionRepository.findAllByLeaderboardId(individualLeaderboard);
        Date timestamp = new Date();
        Multimap<String, IndividualPosition> teams =
                Multimaps.index(individualPositions,
                        IndividualPosition::getTeamId);
        Leaderboard leaderboard = new Leaderboard(timestamp);
        leaderboard.setType(Type.TEAM);
        leaderboard.setTotalContestants(teams.keySet().size());
        leaderboard = leaderboardRepository.insert(leaderboard);
        String id = leaderboard.getId();
        updateQuickFinds(id, Type.TEAM);
        logger.info("New team leaderboard generated and inserted with " +
                "id " + id);
        for (String team : teams.keySet()) {
            logger.debug("Generating team position for " + team);
            List<IndividualPosition> contestants =
                    teams.get(team)
                            .stream()
                            .sorted(Comparator.comparingDouble(Position::getTotal).reversed())
                            .limit(20)
                            .collect(Collectors.toList());
            String teamName = contestants.get(0).getTeamName();
            TeamPosition teamPosition = new TeamPosition(
                    -1,
                    teamName,
                    team
            );
            logger.debug("Limited number of positions in team to 20");
            double total =
                    contestants.stream().mapToDouble(Position::getTotal).sum();
            Map<Integer, Double> map = Maps.newHashMap();
            for (int i = 1; i <= numberOfQuestions; i++) {
                int index = i;
                map.put(
                        i,
                        contestants.stream().mapToDouble(pos ->
                                getTotal(index, pos)
                        ).sum()
                );
            }
            teamPosition.setTotal(total);
            teamPosition.setQuestionTotals(map);
            teamPosition.setTimestamp(timestamp);
            teamPosition.setLeaderboardId(id);
            positions.add(teamPosition);
        }
        logger.info("Sorting team positions");
        sortPositions(positions);
        teamPositionRepository.insert(positions);
        logger.info("Generated team positions");
        return id;
    }

    private double getTotal(int index, IndividualPosition pos) {
        List<Score> scores =
                pos.getScores().stream().filter(s -> s.getQuestionNumber() == index).collect(Collectors.toList());
        if (scores.size() > 0) {
            return scores.get(0).getTotal();
        }
        return 0.0;
    }

    @Override
    public int positionWithinTeam(String teamId, String contestantId) {
        String leaderboard = individualLeaderboardId();
        List<IndividualPosition> positions =
                individualPositionRepository.findAllByLeaderboardIdAndTeamIdOrderByTotalDesc(leaderboard, teamId);
        for (int i=0; i<positions.size(); i++) {
            if (positions.get(i).getContestantId().equals(contestantId)) {
                return i+1;
            }
        }
        return -1;
    }

    @Override
    public TotalMap getContestantTotals(List<String> ids) {
        String leaderboard = individualLeaderboardId();
        List<IndividualPosition> positions =
                individualPositionRepository.findAllByLeaderboardIdAndGlobalIdIsIn(leaderboard, ids);
        Map<String, Double> map = Maps.newHashMap();
        for (IndividualPosition position : positions) {
            map.put(position.getGlobalId(), position.getTotal());
        }
        TotalMap totalMap = new TotalMap();
        totalMap.setTotals(map);
        return totalMap;
    }

    @Override
    public String individualLeaderboardId() {
        List<QuickFind> quickFinds = quickFindRepository.findAll();
        if (quickFinds.isEmpty()) {
            return null;
        }
        return quickFinds.get(0).getIndividualLeaderboard();
    }

    @Override
    public String teamLeaderboardId() {
        List<QuickFind> quickFinds = quickFindRepository.findAll();
        if (quickFinds.isEmpty()) {
            return null;
        }
        return quickFinds.get(0).getTeamLeaderboard();
    }

    @Override
    public String leadingIndividual() {
        String id = individualLeaderboardId();
        List<IndividualPosition> individualPositions =
                individualPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(id, 1, 2);
        if (individualPositions.isEmpty()) {
            return "N/A";
        }
        return individualPositions.get(0).getName();
    }

    @Override
    public String leadingTeam() {
        String id = teamLeaderboardId();
        List<TeamPosition> teamPositions =
                teamPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(id, 1, 2);
        if (teamPositions.isEmpty()) {
            return "N/A";
        }
        return teamPositions.get(0).getTeamName();
    }

    @Override
    public List<TeamPosition> getTopTeams(int numberOfUniversities) {
        String id = teamLeaderboardId();
        return teamPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(id, 1, numberOfUniversities + 1);
    }

    @Override
    public List<IndividualPosition> getTopIndividuals(int numberOfIndividuals) {
        String id = individualLeaderboardId();
        return individualPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(id, 1, numberOfIndividuals + 1);

    }

    @Override
    public List<IndividualPosition> getPositionsForIndividual(String id) {
        return individualPositionRepository.findAllByContestantId(id);
    }

    @Override
    public List<TeamPosition> getPositionsForTeam(String id) {
        return teamPositionRepository.findAllByTeamId(id);
    }

    @Override
    public List<IndividualPosition> getTeamContestantsLatestPosition(List<String> ids) {
        String id = individualLeaderboardId();
        return individualPositionRepository.findAllByLeaderboardIdAndContestantIdIn(id, ids);
    }

    @Override
    public List<LeaderboardDTO> save() {
        QuickFind quickFind = quickFindRepository.findAll().get(0);
        Iterable<Leaderboard> leaderboards =
                leaderboardRepository.findAllById(Lists.newArrayList(quickFind.getIndividualLeaderboard(), quickFind.getTeamLeaderboard()));
        List<LeaderboardDTO> leaderboardDTOS = Lists.newArrayList();
        for (Leaderboard leaderboard : leaderboards) {
            leaderboard.setSaved(true);
            leaderboardRepository.save(leaderboard);
            List<? extends Position> positions;
            if (leaderboard.getType().equals(Type.INDIVIDUAL)) {
                positions = individualPositionRepository
                        .findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboard.getId(), 1, 11);
            } else {
                positions =
                        teamPositionRepository.findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(leaderboard.getId(), 1, 11);
            }
            leaderboardDTOS.add(new LeaderboardDTO(leaderboard, positions));
        }
        return leaderboardDTOS;
    }

    private void updateQuickFinds(String id, Type type) {
        List<QuickFind> quickFinds = quickFindRepository.findAll();
        QuickFind quickFind = new QuickFind();
        if (!quickFinds.isEmpty()) {
            quickFind = quickFinds.get(0);
        }
        if (type == Type.INDIVIDUAL) {
            quickFind.setIndividualLeaderboard(id);
        } else if (type == Type.TEAM) {
            quickFind.setTeamLeaderboard(id);
        }
        if (quickFinds.isEmpty()) {
            quickFindRepository.insert(quickFind);
        } else {
            quickFindRepository.save(quickFind);
        }
    }

    private boolean searchPredicate(String searchTerm,
                                    IndividualPosition individualPosition) {

        searchTerm = searchTerm.toLowerCase();

        return individualPosition.getName().toLowerCase().contains(searchTerm)
                || individualPosition.getContestant().toLowerCase().contains(searchTerm)
                || String.valueOf(individualPosition.getPosition()).contains(searchTerm);

    }

    private boolean teamSearchPredicate(String searchTerm,
                                        TeamPosition teamPosition) {

        searchTerm = searchTerm.toLowerCase();

        return teamPosition.getTeamName().toLowerCase().contains(searchTerm)
                || String.valueOf(teamPosition.getPosition()).contains(searchTerm);

    }

    private Leaderboard generatePlainLeaderboard() {
        Leaderboard newLeaderboard = new Leaderboard(new Date());
        newLeaderboard.setType(Type.INDIVIDUAL);
        logger.info("Created blank individual leaderboard");
        return newLeaderboard;
    }

    private Leaderboard generatePlainTeamLeaderboard() {
        Leaderboard newLeaderboard = new Leaderboard(new Date());
        newLeaderboard.setType(Type.TEAM);
        logger.info("Created blank team leaderboard");
        return newLeaderboard;
    }

    private Multimap<String, Contestant> getAllTeams() {
        List<Contestant> contestants = contestantService.getAllContestants();
        return Multimaps.index(contestants, Contestant::getTeamId);
    }

    private void sortPositions(List<? extends Position> positions) {
        positions.sort(Comparator.comparingDouble(Position::getTotal).reversed());
        for (int i = 0; i < positions.size(); i++) {
            positions.get(i).setPosition(i + 1);
        }
    }

    private String getTeamNameById(String id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().getName();
        }
        return "";
    }


}
