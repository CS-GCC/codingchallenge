package codingchallenge.services.interfaces;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.exceptions.ContestantNotFoundException;

import java.util.List;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface ContestantService {

    List<Contestant> addContestants(List<Contestant> contestants);

    List<Contestant> getAllContestants();

    List<String> getAllContestantIds();

    Contestant getContestantById(String id) throws ContestantNotFoundException;

    void generateTimeStampedPositions(Leaderboard leaderboard);

    List<String> getContestantNames(List<String> contestantIds);

}
