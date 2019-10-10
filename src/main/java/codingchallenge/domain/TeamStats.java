package codingchallenge.domain;

import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Region;
import codingchallenge.domain.subdomain.TeamPosition;

import java.util.List;

public class TeamStats {

    private String id;
    private String name;
    private int position;
    private double total;
    private List<IndividualPosition> contestants;
    private int totalContestants;
    private String logo;
    private Region region;

    public TeamStats(TeamPosition team, Region region) {
        this.id = team.getId();
        this.name = team.getTeamName();
        this.total = team.getTotal();
        this.position = team.getPosition();
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<IndividualPosition> getContestants() {
        return contestants;
    }

    public void setContestants(List<IndividualPosition> contestants) {
        this.contestants = contestants;
    }

    public int getTotalContestants() {
        return totalContestants;
    }

    public void setTotalContestants(int totalContestants) {
        this.totalContestants = totalContestants;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
