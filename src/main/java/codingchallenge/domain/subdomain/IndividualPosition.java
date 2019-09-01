package codingchallenge.domain.subdomain;

import com.google.common.base.Objects;

public class IndividualPosition extends Position {

    private String contestant;
    private String name;
    private String contestantId;

    public IndividualPosition() {
    }

    public IndividualPosition(int position, String contestant, String name, String contestantId) {
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
}
