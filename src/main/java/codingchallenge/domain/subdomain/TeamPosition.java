package codingchallenge.domain.subdomain;

import com.google.common.base.Objects;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class TeamPosition extends Position {

    private List<String> contestants;
    private String teamName;

    public TeamPosition(int position, String teamName) {
        super(position);
        this.teamName = teamName;
    }

    public TeamPosition(int position, String teamName, List<String> contestants) {
        super(position);
        this.teamName = teamName;
        this.contestants = contestants;
    }

    public List<String> getContestants() {
        return contestants;
    }

    public void setContestants(List<String> contestants) {
        this.contestants = contestants;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPosition that = (TeamPosition) o;
        return getTeamName().equals(that.getTeamName()) &&
                CollectionUtils.isEqualCollection(getContestants(), that.getContestants());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTeamName(), getContestants());
    }

}
