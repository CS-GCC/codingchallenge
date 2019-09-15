package codingchallenge.domain.subdomain;

import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Position {

    @Id
    private String id;
    private String leaderboardId;
    private double total;
    private List<Score> scores;
    private int position;
    private int pos;
    private Date timestamp;

    public Position() {
    }

    public Position(int position) {
        this.total = 0.0;
        this.scores = null;
        this.position = position;
        this.pos = position;
    }

    public Position(double total, List<Score> scores, int position) {
        this.total = total;
        this.scores = scores;
        this.position = position;
    }

    public double getTotal() {
        return total;
    }

    public List<Score> getScores() {
        return scores;
    }

    public int getPosition() {
        return position;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void setPosition(int position) {
        this.position = position;
        this.pos = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeaderboardId() {
        return leaderboardId;
    }

    public void setLeaderboardId(String leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPos() {
        return pos;
    }

    public String createSimpleDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(timestamp);
    }
}
