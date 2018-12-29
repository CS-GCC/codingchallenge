package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Leaderboard {

    @Id
    private String id;
    private Date timestamp;
    private List<Position> positions;

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
}
