package codingchallenge.services.impl;

import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.collections.TeamRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.Team;
import codingchallenge.domain.Type;
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

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final ContestantService contestantService;
    private final TeamRepository teamRepository;

    private final Logger logger =
            LoggerFactory.getLogger(LeaderboardServiceImpl.class);

    @Autowired
    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository
            ,
                                  ContestantService contestantService,
                                  TeamRepository teamRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.contestantService = contestantService;
        this.teamRepository = teamRepository;
    }

    @Override
    public Leaderboard getLatestIndividualLeaderboard(int from, int limit) {
        Leaderboard leaderboard = getLeaderboard();
        leaderboard.setTotalContestants(leaderboard.getContestants().size());
        leaderboard.setContestants(leaderboard
                .getContestants()
                .stream()
                .skip(from)
                .limit(limit)
                .collect(Collectors.toList()));
        return leaderboard;
    }

    @Override
    public Leaderboard getLatestTeamLeaderboard(int from, int limit) {
        Leaderboard leaderboard = getTeamLeaderboard();
        leaderboard.setTotalContestants(leaderboard.getContestants().size());
        leaderboard.setContestants(leaderboard
                .getContestants()
                .stream()
                .skip(from)
                .limit(limit)
                .collect(Collectors.toList()));
        return leaderboard;
    }

    @Override
    public Leaderboard getTeamLeaderboard() {
        Optional<Leaderboard> leaderboardOptional =
                leaderboardRepository.findTopByTimestampBeforeAndTypeOrderByTimestampDesc(new Date(), Type.TEAM);
        return leaderboardOptional.orElseGet(this::generatePlainTeamLeaderboard);
    }

    @Override
    public TeamPosition getLatestPositionForTeam(String teamId) {
        Leaderboard leaderboard = getTeamLeaderboard();
        List<Position> positions = leaderboard.getContestants();
        for (Position position : positions) {
            TeamPosition pos = (TeamPosition) position;
            if (pos.getTeamId().equals(teamId)) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public IndividualPosition getLatestPositionForIndividual(String id) {
        Leaderboard leaderboard = getLeaderboard();
        List<Position> positions = leaderboard.getContestants();
        for (Position position : positions) {
            IndividualPosition pos = (IndividualPosition) position;
            if (pos.getContestantId().equals(id)) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public Leaderboard getLeaderboard() {
        Optional<Leaderboard> leaderboardOptional =
                leaderboardRepository.findTopByTimestampBeforeAndTypeOrderByTimestampDesc(new Date(), Type.INDIVIDUAL);
        return leaderboardOptional.orElseGet(this::generatePlainLeaderboard);
    }

    @Override
    public Leaderboard getFilteredIndividualLeaderboard(String searchTerm,
                                                        int from, int limit) {
        Leaderboard leaderboard = getLatestIndividualLeaderboard(from, limit);

        leaderboard.setContestants(
                leaderboard.getContestants()
                        .stream()
                        .filter(pos -> searchPredicate(searchTerm,
                                (IndividualPosition) pos))
                        .skip(from)
                        .limit(limit)
                        .collect(Collectors.toList())
        );

        return leaderboard;
    }

    @Override
    public Leaderboard getFilteredTeamLeaderboard(String searchTerm, int from
            , int limit) {
        Leaderboard leaderboard = getLatestTeamLeaderboard(from, limit);

        leaderboard.setContestants(
                leaderboard.getContestants()
                        .stream()
                        .filter(pos -> teamSearchPredicate(searchTerm,
                                (TeamPosition) pos))
                        .skip(from)
                        .limit(limit)
                        .collect(Collectors.toList())
        );

        return leaderboard;
    }

    @Override
    public Leaderboard generateLeaderboard(Multimap<String, Score> scoreMultimap) throws ContestantNotFoundException {
        List<Position> positions = Lists.newArrayList();
        for (String contestantId : scoreMultimap.keySet()) {
            logger.debug("Generating position for " + contestantId);
            Collection<Score> scores = scoreMultimap.get(contestantId);
            Contestant contestant =
                    contestantService.getContestantById(contestantId);
            IndividualPosition position = new IndividualPosition(-1, contestantId,
                    contestant.getName(), contestantId);
            position.setScores(Lists.newArrayList(scores));
            position.setTotal(scores.stream().mapToDouble(Score::getTotal).sum());
            position.setTeamId(contestant.getTeamId());
            position.setTeamName(contestant.getTeam());
            positions.add(position);
        }
        logger.debug("Sorting individual positions");
        sortPositions(positions);
        Leaderboard leaderboard = new Leaderboard(new Date());
        leaderboard.setContestants(positions);
        leaderboard.setType(Type.INDIVIDUAL);
        logger.info("New individual leaderboard generated and inserted");
        return leaderboard;
    }

    @Override
    public Leaderboard generateTeamLeaderboard(Leaderboard individualLeaderboard,
                                               int numberOfQuestions) throws ContestantNotFoundException {
        List<Position> positions = Lists.newArrayList();
        List<Position> individualPositions =
                individualLeaderboard.getContestants();
        Leaderboard leaderboard =
                new Leaderboard(individualLeaderboard.getTimestamp());
        Multimap<String, Contestant> teams = getAllTeams();
        for (String team : teams.keySet()) {
            logger.debug("Generating team position for " + team);
            Collection<Contestant> contestants = teams.get(team);
            List<String> contestantIds =
                    contestants.stream().map(Contestant::getId).collect(Collectors.toList());
            TeamPosition teamPosition = new TeamPosition(
                    -1,
                    getTeamNameById(team),
                    team
            );
            List<Position> teamPositions =
                    individualPositions.stream().filter(p -> contestantIds.contains(((IndividualPosition) p).getContestantId())).sorted(Comparator.comparingDouble(Position::getTotal).reversed()).collect(Collectors.toList());
            teamPositions = teamPositions.subList(0,
                    Math.min(contestants.size(), 20));
            logger.debug("Limited number of positions in team to 20");
            double total =
                    teamPositions.stream().mapToDouble(Position::getTotal).sum();
            Map<Integer, Double> map = Maps.newHashMap();
            for (int i = 1; i <= numberOfQuestions; i++) {
                int index = i;
                map.put(
                        i,
                        teamPositions.stream().mapToDouble(pos ->
                                pos.getScores().stream().filter(s -> s.getQuestionNumber() == index).findFirst().get().getTotal()
                        ).sum()
                );
            }
            teamPosition.setTotal(total);
            teamPosition.setQuestionTotals(map);
            logger.debug("Finding names for team " + team + "contestants");
            List<String> filteredContestantIds =
                    teamPositions.stream().map(p -> ((IndividualPosition) p).getContestantId()).collect(Collectors.toList());
            teamPosition.setContestants(filteredContestantIds);
            positions.add(teamPosition);
        }
        leaderboard.setType(Type.TEAM);
        sortPositions(positions);
        leaderboard.setContestants(positions);
        logger.info("Generated new team leaderboard");
        return leaderboard;
    }

    @Override
    public void saveLeaderboard(Leaderboard leaderboard) {
        leaderboardRepository.insert(leaderboard);
    }

    @Override
    public void saveTeamLeaderboard(Leaderboard leaderboard) {
        leaderboardRepository.insert(leaderboard);
    }

    @Override
    public int positionWithinTeam(String teamId, String contestantId) {
        Leaderboard leaderboard = getTeamLeaderboard();
        List<Position> positions = leaderboard.getContestants();
        TeamPosition position = null;
        for (Position pos : positions) {
            if (((TeamPosition) pos).getTeamId().equals(teamId)) {
                position = (TeamPosition) pos;
                break;
            }
        }
        if (position == null) {
            return -1;
        }
        List<String> contestants = position.getContestants();
        return contestants.indexOf(contestantId) + 1;
    }

    @Override
    public double getContestantTotals(List<String> ids) {
        return ids.stream()
                .map(contestantService::getContestantIdForGlobalId)
                .filter(Objects::nonNull)
                .map(this::getLatestPositionForIndividual)
                .filter(Objects::nonNull)
                .mapToDouble(IndividualPosition::getTotal)
                .sum();
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
        List<Contestant> contestants = contestantService.getAllContestants();
        int i = 1;
        Leaderboard newLeaderboard = new Leaderboard();
        for (Contestant contestant : contestants) {
            IndividualPosition individualPosition =
                    new IndividualPosition(i, contestant.getId(),
                            contestant.getName(), contestant.getId());
            newLeaderboard.getContestants().add(individualPosition);
            i++;
        }
        newLeaderboard.setType(Type.INDIVIDUAL);
        logger.info("Created blank individual leaderboard");
        return newLeaderboard;
    }

    private Leaderboard generatePlainTeamLeaderboard() {
        Leaderboard newLeaderboard = new Leaderboard();
        Multimap<String, Contestant> teamMap = getAllTeams();
        int i = 1;
        for (String team : teamMap.keySet()) {
            List<String> contestantStrings =
                    teamMap.get(team).stream().map(Contestant::getName).collect(Collectors.toList());
            TeamPosition teamPosition = new TeamPosition(i,
                    getTeamNameById(team),
                    team,
                    contestantStrings);
            newLeaderboard.getContestants().add(teamPosition);
            i++;
        }
        newLeaderboard.setType(Type.TEAM);
        logger.info("Created blank team leaderboard");
        return newLeaderboard;
    }

    private Multimap<String, Contestant> getAllTeams() {
        List<Contestant> contestants = contestantService.getAllContestants();
        return Multimaps.index(contestants, Contestant::getTeamId);
    }

    private void sortPositions(List<Position> positions) {
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
