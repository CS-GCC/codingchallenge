package codingchallenge.domain;

import java.util.List;

public class TeamImageList {

    private List<TeamImage> teamImages;

    public TeamImageList(List<TeamImage> teamImages) {
        this.teamImages = teamImages;
    }

    public TeamImageList() {
    }

    public List<TeamImage> getTeamImages() {
        return teamImages;
    }

    public void setTeamImages(List<TeamImage> teamImages) {
        this.teamImages = teamImages;
    }
}
