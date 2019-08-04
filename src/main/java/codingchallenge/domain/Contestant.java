package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TimeStampPosition;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

public class Contestant {

    @Id
    private String id;
    private String globalId;
    private String name;
    private String team;
    private String teamId;
    private List<TimeStampPosition> positions;

    public Contestant() {
    }

    public Contestant(String id, String name, String team, String teamId) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.teamId = teamId;
    }

    public Contestant(String name, String team, String teamId) {
        this.name = name;
        this.team = team;
        this.teamId = teamId;
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

    public List<TimeStampPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<TimeStampPosition> positions) {
        this.positions = positions;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contestant that = (Contestant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(team, that.team) &&
                CollectionUtils.isEqualCollection(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, team, positions);
    }

    public Position latestPosition() {
        return positions.get(positions.size()-1);
    }
}
