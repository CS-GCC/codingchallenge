package codingchallenge.domain.subdomain;

import com.google.common.base.Objects;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamPosition extends Position {

    private Map<Integer, Double> questionTotals;
    private String teamName;
    private String teamId;

    public TeamPosition() {
    }

    public TeamPosition(int position, String teamName, String teamId) {
        super(position);
        this.teamName = teamName;
        this.teamId = teamId;
    }

    public TeamPosition(int position, String teamName,
                        String teamId, List<String> contestants) {
        super(position);
        this.teamName = teamName;
        this.teamId = teamId;
        this.questionTotals = new HashMap<>();
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPosition that = (TeamPosition) o;
        return getTeamName().equals(that.getTeamName()) &&
                getTotal() == that.getTotal() &&
                getPosition() == that.getPosition() &&
                getQuestionTotals().equals(that.getQuestionTotals())
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTeamName());
    }

}
