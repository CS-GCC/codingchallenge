package codingchallenge.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Objects;

public class Leaderboard {

    @Id
    private String id;
    private Date timestamp;
    private Type type;
    private int totalContestants;

    public Leaderboard() {
        this.timestamp = new Date();
    }

    public Leaderboard(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTotalContestants(int totalContestants) {
        this.totalContestants = totalContestants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leaderboard that = (Leaderboard) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp);
    }

    public int getTotalContestants() {
        return totalContestants;
    }
}
