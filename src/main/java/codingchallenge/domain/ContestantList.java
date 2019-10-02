package codingchallenge.domain;

import java.util.List;

public class ContestantList {

    private List<GitDTO> contestants;

    public List<GitDTO> getContestants() {
        return contestants;
    }

    public void setContestants(List<GitDTO> contestants) {
        this.contestants = contestants;
    }
}
