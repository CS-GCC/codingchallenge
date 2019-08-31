package codingchallenge.domain;

import java.util.List;

public class LeaderFacts {

    private List<Contestant> individualLeaderboard;
    private List<Contestant> universityLeaderboard;
    private List<Answer> answers;

    public LeaderFacts(List<Contestant> individualLeaderboard,
                       List<Contestant> universityLeaderboard,
                       List<Answer> answers) {
        this.individualLeaderboard = individualLeaderboard;
        this.universityLeaderboard = universityLeaderboard;
        this.answers = answers;
    }

    public List<Contestant> getIndividualLeaderboard() {
        return individualLeaderboard;
    }

    public void setIndividualLeaderboard(List<Contestant> individualLeaderboard) {
        this.individualLeaderboard = individualLeaderboard;
    }

    public List<Contestant> getUniversityLeaderboard() {
        return universityLeaderboard;
    }

    public void setUniversityLeaderboard(List<Contestant> universityLeaderboard) {
        this.universityLeaderboard = universityLeaderboard;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
