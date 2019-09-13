package codingchallenge.domain;

import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TeamPosition;

import java.util.List;

public class RegionFacts {

    private List<IndividualPosition> individualLeaderboard;
    private List<TeamPosition> universityLeaderboard;
    private List<Headline> headlines;

    public RegionFacts(List<IndividualPosition> contestants,
                       List<TeamPosition> universityContestants,
                       List<Headline> headlines) {
        this.individualLeaderboard = contestants;
        this.universityLeaderboard = universityContestants;
        this.headlines = headlines;
    }

    public List<IndividualPosition> getIndividualLeaderboard() {
        return individualLeaderboard;
    }

    public void setIndividualLeaderboard(List<IndividualPosition> individualLeaderboard) {
        this.individualLeaderboard = individualLeaderboard;
    }

    public List<TeamPosition> getUniversityLeaderboard() {
        return universityLeaderboard;
    }

    public void setUniversityLeaderboard(List<TeamPosition> universityLeaderboard) {
        this.universityLeaderboard = universityLeaderboard;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }
}
