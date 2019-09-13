package codingchallenge.domain;

import org.springframework.data.annotation.Id;

public class QuickFind {

    @Id
    private String id;
    private String individualLeaderboard;
    private String teamLeaderboard;

    public QuickFind() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndividualLeaderboard() {
        return individualLeaderboard;
    }

    public void setIndividualLeaderboard(String individualLeaderboard) {
        this.individualLeaderboard = individualLeaderboard;
    }

    public String getTeamLeaderboard() {
        return teamLeaderboard;
    }

    public void setTeamLeaderboard(String teamLeaderboard) {
        this.teamLeaderboard = teamLeaderboard;
    }
}
