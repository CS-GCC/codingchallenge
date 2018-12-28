package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Leaderboard<T extends Position> {

    @Id
    private String id;
    private Date timestamp;
    private List<T> positions;

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

    public List<T> getPositions() {
        return positions;
    }

    public void setPositions(List<T> positions) {
        this.positions = positions;
    }
}
