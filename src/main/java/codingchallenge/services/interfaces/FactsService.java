package codingchallenge.services.interfaces;

import codingchallenge.domain.ContestantStats;
import codingchallenge.domain.QuickFacts;
import codingchallenge.domain.RegionFacts;
import codingchallenge.domain.TeamStats;
import codingchallenge.exceptions.ContestantNotFoundException;

public interface FactsService {

    RegionFacts getRegionFacts();
    QuickFacts getQuickFacts();
    TeamStats getStatsForTeam(String teamId) throws ContestantNotFoundException;
    ContestantStats getStatsForContestant(String id) throws ContestantNotFoundException;

}
