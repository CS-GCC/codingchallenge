package codingchallenge.services.interfaces;

import codingchallenge.domain.Contestant;

import java.util.List;

/**
 * Created by kunalwagle on 29/12/2018.
 */
public interface ContestantService {

    List<Contestant> addContestants(List<Contestant> contestants);

    List<Contestant> getAllContestants();

    Contestant getContestantById(String id);
}
