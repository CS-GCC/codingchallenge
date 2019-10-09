package codingchallenge.domain;

public class PersonalProperties {

    private int positionsGained;
    private int rank;
    private String university;
    private int universityGained;
    private int uniRank;
    private int highestRank;
    private String contestantId;
    private String gitUsername;

    public PersonalProperties() {
    }

    public int getPositionsGained() {
        return positionsGained;
    }

    public void setPositionsGained(int positionsGained) {
        this.positionsGained = positionsGained;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getUniversityGained() {
        return universityGained;
    }

    public void setUniversityGained(int universityGained) {
        this.universityGained = universityGained;
    }

    public int getUniRank() {
        return uniRank;
    }

    public void setUniRank(int uniRank) {
        this.uniRank = uniRank;
    }

    public int getHighestRank() {
        return highestRank;
    }

    public void setHighestRank(int highestRank) {
        this.highestRank = highestRank;
    }

    public String getContestantId() {
        return contestantId;
    }

    public void setContestantId(String contestantId) {
        this.contestantId = contestantId;
    }

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }
}
