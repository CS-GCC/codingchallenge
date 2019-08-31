package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;

import java.util.List;

public class RegionFacts {

    private List<Position> individualLeaderboard;
    private List<Position> universityLeaderboard;
    private List<Headline> headlines;

    public RegionFacts(List<Position> contestants,
                       List<Position> universityContestants,
                       List<Headline> headlines) {
        this.individualLeaderboard = contestants;
        this.universityLeaderboard = universityContestants;
        this.headlines = headlines;
    }

    public List<Position> getIndividualLeaderboard() {
        return individualLeaderboard;
    }

    public void setIndividualLeaderboard(List<Position> individualLeaderboard) {
        this.individualLeaderboard = individualLeaderboard;
    }

    public List<Position> getUniversityLeaderboard() {
        return universityLeaderboard;
    }

    public void setUniversityLeaderboard(List<Position> universityLeaderboard) {
        this.universityLeaderboard = universityLeaderboard;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }
}
