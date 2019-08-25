package codingchallenge.domain;

import java.util.List;

public class RegionFacts {

    private List<Contestant> contestants;
    private List<Contestant> universityContestants;
    private List<Headline> headlines;

    public RegionFacts(List<Contestant> contestants, List<Contestant> universityContestants, List<Headline> headlines) {
        this.contestants = contestants;
        this.universityContestants = universityContestants;
        this.headlines = headlines;
    }

    public List<Contestant> getContestants() {
        return contestants;
    }

    public void setContestants(List<Contestant> contestants) {
        this.contestants = contestants;
    }

    public List<Contestant> getUniversityContestants() {
        return universityContestants;
    }

    public void setUniversityContestants(List<Contestant> universityContestants) {
        this.universityContestants = universityContestants;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }
}
