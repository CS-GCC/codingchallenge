package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Leaderboard {

    @Id
    private String id;
    private Date timestamp;
    private List<Position> positions;
    private Type type;

    public Leaderboard() {
        this.timestamp = new Date();
        this.positions = new ArrayList<>();
    }

    public Leaderboard(Date timestamp) {
        this.timestamp = timestamp;
        this.positions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leaderboard that = (Leaderboard) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp) &&
                CollectionUtils.isEqualCollection(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, positions);
    }
}
