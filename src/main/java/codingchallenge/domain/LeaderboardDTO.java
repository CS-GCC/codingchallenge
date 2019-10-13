package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

public class LeaderboardDTO {

    private Date timestamp;
    private int totalContestants;
    private List<? extends Position> contestants;
    private String prettyPrintedTime;

    public LeaderboardDTO(Leaderboard leaderboard,
                          List<? extends Position> positions) {
        this.contestants = positions;
        this.totalContestants = leaderboard.getTotalContestants();
        this.timestamp = leaderboard.getTimestamp();
        this.prettyPrintedTime =
                new PrettyTime().format(leaderboard.getTimestamp());
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalContestants() {
        return totalContestants;
    }

    public void setTotalContestants(int totalContestants) {
        this.totalContestants = totalContestants;
    }

    public List<? extends Position> getContestants() {
        return contestants;
    }

    public void setContestants(List<Position> contestants) {
        this.contestants = contestants;
    }

    public String getPrettyPrintedTime() {
        return prettyPrintedTime;
    }

    public void setPrettyPrintedTime(String prettyPrintedTime) {
        this.prettyPrintedTime = prettyPrintedTime;
    }
}
