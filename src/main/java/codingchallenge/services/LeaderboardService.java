package codingchallenge.services;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.LeaderboardRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final ContestantRepository contestantRepository;

    @Autowired
    public LeaderboardService(LeaderboardRepository leaderboardRepository, ContestantRepository contestantRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.contestantRepository = contestantRepository;
    }

    public Leaderboard<IndividualPosition> getLatestIndividualLeaderboard() {
        Optional<Leaderboard<IndividualPosition>> leaderboard = leaderboardRepository.findTopByOrderByCreatedDesc();
        return leaderboard.orElseGet(this::generatePlainLeaderboard);
    }

    public Leaderboard<IndividualPosition> getFilteredLeaderboard(String searchTerm) {
        Leaderboard<IndividualPosition> leaderboard = getLatestIndividualLeaderboard();

        leaderboard.setPositions(
                leaderboard.getPositions()
                        .stream()
                        .filter(pos -> searchPredicate(searchTerm, pos))
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

    private Leaderboard<IndividualPosition> generatePlainLeaderboard() {
        List<Contestant> contestants = contestantRepository.findAllOrderedByName();
        int i = 1;
        Leaderboard<IndividualPosition> newLeaderboard = new Leaderboard<>();
        for (Contestant contestant : contestants) {
            IndividualPosition individualPosition =
                    new IndividualPosition(i, contestant.getId(), contestant.getName(), contestant.getId());
            newLeaderboard.getPositions().add(individualPosition);
            i++;
        }
        return newLeaderboard;
    }

}
