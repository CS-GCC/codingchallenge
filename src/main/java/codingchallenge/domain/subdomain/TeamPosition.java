package codingchallenge.domain.subdomain;

import com.google.common.base.Objects;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamPosition extends Position {

    private List<String> contestants;
    private Map<Integer, Double> questionTotals;
    private String teamName;

    public TeamPosition(int position, String teamName) {
        super(position);
        this.teamName = teamName;
    }

    public TeamPosition(int position, String teamName, List<String> contestants) {
        super(position);
        this.teamName = teamName;
        this.contestants = contestants;
        this.questionTotals = new HashMap<>();
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

    public Map<Integer, Double> getQuestionTotals() {
        return questionTotals;
    }

    public void setQuestionTotals(Map<Integer, Double> questionTotals) {
        this.questionTotals = questionTotals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPosition that = (TeamPosition) o;
        return getTeamName().equals(that.getTeamName()) &&
                CollectionUtils.isEqualCollection(getContestants(),
                        that.getContestants()) &&
                getTotal() == that.getTotal() &&
                getPosition() == that.getPosition() &&
                getQuestionTotals().equals(that.getQuestionTotals())
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTeamName(), getContestants());
    }

}
