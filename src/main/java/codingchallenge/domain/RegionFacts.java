package codingchallenge.domain;

import java.util.List;

public class RegionFacts {

    private List<Contestant> individualLeaderboard;
    private List<Contestant> universityLeaderboard;
    private List<Headline> headlines;

    public RegionFacts(List<Contestant> contestants, List<Contestant> universityContestants, List<Headline> headlines) {
        this.individualLeaderboard = contestants;
        this.universityLeaderboard = universityContestants;
        this.headlines = headlines;
    }

    public List<Contestant> getIndividualLeaderboard() {
        return individualLeaderboard;
    }

    public void setIndividualLeaderboard(List<Contestant> individualLeaderboard) {
        this.individualLeaderboard = individualLeaderboard;
    }

    public List<Contestant> getUniversityLeaderboard() {
        return universityLeaderboard;
    }

    public void setUniversityLeaderboard(List<Contestant> universityLeaderboard) {
        this.universityLeaderboard = universityLeaderboard;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }
}
