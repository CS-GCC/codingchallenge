package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.Region;
import codingchallenge.domain.subdomain.Score;

import java.util.List;

public class ContestantStats {

    private String id;
    private String name;
    private String team;
    private int position;
    private int positionWithinTeam;
    private int teamPosition;
    private List<Score> scores;
    private double total;
    private Region region;

    public ContestantStats(String id, String name, String team,
                           Region region) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.region = region;
    }

    public ContestantStats(Contestant contestant, Region region) {
        this(
                contestant.getId(),
                contestant.getName(),
                contestant.getTeam(),
                region
        );
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPositionWithinTeam() {
        return positionWithinTeam;
    }

    public void setPositionWithinTeam(int positionWithinTeam) {
        this.positionWithinTeam = positionWithinTeam;
    }

    public int getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(int teamPosition) {
        this.teamPosition = teamPosition;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
