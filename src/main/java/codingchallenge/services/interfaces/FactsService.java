package codingchallenge.services.interfaces;

import codingchallenge.domain.*;
import codingchallenge.exceptions.ContestantNotFoundException;

public interface FactsService {

    RegionFacts getRegionFacts();
    QuickFacts getQuickFacts();
    LeaderFacts getLeaderFacts();
    TeamStats getStatsForTeam(String teamId) throws ContestantNotFoundException;
    ContestantStats getStatsForContestant(String id) throws ContestantNotFoundException;
    void getStatsForNewsletter(String oldInd, String oldUni,
                                                String newInd, String newUni,
                               String email, String newsletter);

}
