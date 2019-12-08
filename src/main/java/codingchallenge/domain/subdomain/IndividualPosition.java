package codingchallenge.domain.subdomain;

import com.google.common.base.Objects;

public class IndividualPosition extends Position {

    private String contestant;
    private String name;
    private String contestantId;
    private String globalId;
    private String teamName;
    private String teamId;

    public IndividualPosition() {
    }

    public IndividualPosition(int position, String contestant, String name,
                              String contestantId) {
        super(position);
        this.contestant = contestant;
        this.name = name;
        this.contestantId = contestantId;
    }

    public String getContestant() {
        return contestant;
    }

    public String getName() {
        return name;
    }

    public String getContestantId() {
        return contestantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualPosition that = (IndividualPosition) o;
        return getName().equals(that.getName()) &&
                getContestantId().equals(that.getContestantId()) &&
                getContestant().equals(that.getContestant()) &&
                getTotal() == that.getTotal() &&
                getPosition() == that.getPosition();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getContestant(), getName(), getContestantId());
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public void setContestant(String contestant) {
        this.contestant = contestant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContestantId(String contestantId) {
        this.contestantId = contestantId;
    }
}
