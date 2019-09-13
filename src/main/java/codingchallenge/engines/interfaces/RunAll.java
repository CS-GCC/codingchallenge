package codingchallenge.engines.interfaces;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.exceptions.ContestantNotFoundException;

import java.util.List;

public interface RunAll {

    String runAll() throws ContestantNotFoundException;
    String runContestants(List<Contestant> contestants) throws ContestantNotFoundException;

}
