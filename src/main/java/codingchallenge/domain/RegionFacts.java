package codingchallenge.domain;

import java.util.List;

public class RegionFacts {

    private List<Contestant> contestants;
    private List<Contestant> universityContestants;

    public RegionFacts(List<Contestant> contestants, List<Contestant> universityContestants) {
        this.contestants = contestants;
        this.universityContestants = universityContestants;
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
}
