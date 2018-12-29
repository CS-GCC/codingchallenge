package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.collections.TeamLeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.TeamPosition;
import codingchallenge.services.interfaces.LeaderboardService;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final TeamLeaderboardRepository teamLeaderboardRepository;
    private final ContestantRepository contestantRepository;

    @Autowired
    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository, TeamLeaderboardRepository
            teamLeaderboardRepository, ContestantRepository contestantRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.teamLeaderboardRepository = teamLeaderboardRepository;
        this.contestantRepository = contestantRepository;
    }

    @Override
    public Leaderboard getLatestIndividualLeaderboard() {
        Optional<Leaderboard> leaderboard = leaderboardRepository.findById("123");
        return leaderboard.orElseGet(this::generatePlainLeaderboard);
//        return leaderboardRepository.findTopByOrderByCreatedDesc();
    }

    @Override
    public Leaderboard getLatestTeamLeaderboard() {
        Optional<Leaderboard> leaderboard = teamLeaderboardRepository.findById("123");
        return leaderboard.orElseGet(this::generatePlainTeamLeaderboard);
//        return teamLeaderboardRepository.findTopByOrderByCreatedDesc();
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
        List<Contestant> contestants = contestantRepository.findAllOrderedByName();
        int i = 1;
        Leaderboard newLeaderboard = new Leaderboard();
        for (Contestant contestant : contestants) {
            IndividualPosition individualPosition =
                    new IndividualPosition(i, contestant.getId(), contestant.getName(), contestant.getId());
            newLeaderboard.getPositions().add(individualPosition);
            i++;
        }
        return newLeaderboard;
    }

    private Leaderboard generatePlainTeamLeaderboard() {
        List<Contestant> contestants = contestantRepository.findAllOrderedByName();
        Leaderboard newLeaderboard = new Leaderboard();
        Multimap<String, Contestant> teamMap =  Multimaps.index(contestants, Contestant::getTeam);
        int i = 1;
        for (String team : teamMap.keySet()) {
            List<String> contestantStrings = teamMap.get(team).stream().map(Contestant::getName).collect(Collectors.toList());
            TeamPosition teamPosition = new TeamPosition(i, team, contestantStrings);
            newLeaderboard.getPositions().add(teamPosition);
        }
        return newLeaderboard;
    }

}
