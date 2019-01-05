package codingchallenge.services.impl;

import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.collections.TeamLeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
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
    private final TeamLeaderboardRepository teamLeaderboardRepository;
    private final ContestantService contestantService;

    private final Logger logger =
            LoggerFactory.getLogger(LeaderboardServiceImpl.class);

    @Autowired
    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository, TeamLeaderboardRepository
            teamLeaderboardRepository, ContestantService contestantService) {
        this.leaderboardRepository = leaderboardRepository;
        this.teamLeaderboardRepository = teamLeaderboardRepository;
        this.contestantService = contestantService;
    }

    @Override
    public Leaderboard getLatestIndividualLeaderboard() {
        Optional<Leaderboard> leaderboard = leaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(new Date());
        return leaderboard.orElseGet(this::generatePlainLeaderboard);
    }

    @Override
    public Leaderboard getLatestTeamLeaderboard() {
        Optional<Leaderboard> leaderboard = teamLeaderboardRepository.findTopByTimestampBeforeOrderByTimestampDesc(new Date());
        return leaderboard.orElseGet(this::generatePlainTeamLeaderboard);
    }

    @Override
    public Leaderboard getFilteredIndividualLeaderboard(String searchTerm) {
        Leaderboard leaderboard = getLatestIndividualLeaderboard();

        leaderboard.setPositions(
                leaderboard.getPositions()
                        .stream()
                        .filter(pos -> searchPredicate(searchTerm, (IndividualPosition) pos))
                        .collect(Collectors.toList())
        );

        return leaderboard;
    }

    @Override
    public Leaderboard getFilteredTeamLeaderboard(String searchTerm) {
        Leaderboard leaderboard = getLatestTeamLeaderboard();

        leaderboard.setPositions(
                leaderboard.getPositions()
                        .stream()
                        .filter(pos -> teamSearchPredicate(searchTerm, (TeamPosition) pos))
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
            Position position = new IndividualPosition(-1, contestantId,
                    contestant.getName(), contestantId);
            position.setScores(Lists.newArrayList(scores));
            position.setTotal(scores.stream().mapToDouble(Score::getTotal).sum());
            positions.add(position);
        }
        logger.debug("Sorting individual positions");
        sortPositions(positions);
        Leaderboard leaderboard = new Leaderboard(new Date());
        leaderboard.setPositions(positions);
        leaderboardRepository.insert(leaderboard);
        logger.info("New individual leaderboard generated and inserted");
        return leaderboard;
    }

    @Override
    public void generateTeamLeaderboard(Leaderboard individualLeaderboard,
                                        int numberOfQuestions) {
        List<Position> positions = Lists.newArrayList();
        List<Position> individualPositions =
                individualLeaderboard.getPositions();
        Leaderboard leaderboard = new Leaderboard(individualLeaderboard.getTimestamp());
        Multimap<String, Contestant> teams = getAllTeams();
        for (String team : teams.keySet()) {
            logger.debug("Generating team position for " + team);
            Collection<Contestant> contestants = teams.get(team);
            List<String> contestantIds =
                    contestants.stream().map(Contestant::getId).collect(Collectors.toList());
            TeamPosition teamPosition = new TeamPosition(
                    -1,
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
            for (int i=1; i<=numberOfQuestions; i++) {
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
            List<String> contestantNames =
                    contestantService.getContestantNames(filteredContestantIds);
            teamPosition.setContestants(contestantNames);
            positions.add(teamPosition);
        }
        sortPositions(positions);
        leaderboard.setPositions(positions);
        teamLeaderboardRepository.insert(leaderboard);
        logger.info("Generated and inserted new team leaderboard");
    }

    private boolean searchPredicate(String searchTerm, IndividualPosition individualPosition) {

        searchTerm = searchTerm.toLowerCase();

        return individualPosition.getName().toLowerCase().contains(searchTerm)
                || individualPosition.getContestant().toLowerCase().contains(searchTerm)
                || String.valueOf(individualPosition.getPosition()).contains(searchTerm);

    }

    private boolean teamSearchPredicate(String searchTerm, TeamPosition teamPosition) {

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
                    new IndividualPosition(i, contestant.getId(), contestant.getName(), contestant.getId());
            newLeaderboard.getPositions().add(individualPosition);
            i++;
        }
        logger.info("Created blank individual leaderboard");
        return newLeaderboard;
    }

    private Leaderboard generatePlainTeamLeaderboard() {
        Leaderboard newLeaderboard = new Leaderboard();
        Multimap<String, Contestant> teamMap =  getAllTeams();
        int i = 1;
        for (String team : teamMap.keySet()) {
            List<String> contestantStrings = teamMap.get(team).stream().map(Contestant::getName).collect(Collectors.toList());
            TeamPosition teamPosition = new TeamPosition(i, team, contestantStrings);
            newLeaderboard.getPositions().add(teamPosition);
            i++;
        }
        logger.info("Created blank team leaderboard");
        return newLeaderboard;
    }

    private Multimap<String, Contestant> getAllTeams() {
        List<Contestant> contestants = contestantService.getAllContestants();
        return Multimaps.index(contestants, Contestant::getTeam);
    }

    private void sortPositions(List<Position> positions) {
        positions.sort(Comparator.comparingDouble(Position::getTotal).reversed());
        for (int i=0; i<positions.size(); i++) {
            positions.get(i).setPosition(i+1);
        }
    }

}
