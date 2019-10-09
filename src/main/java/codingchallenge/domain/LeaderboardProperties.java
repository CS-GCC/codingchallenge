package codingchallenge.domain;

import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.TeamPosition;

import java.util.List;
import java.util.Map;

public class LeaderboardProperties {

    private List<PersonalProperties> personalPropertiesList;
    private Map<Integer, Integer> posChange;
    private Map<Integer, Integer> uniPosChange;
    private List<IndividualPosition> individualPositions;
    private List<TeamPosition> teamPositions;


    public LeaderboardProperties() {
    }

    public LeaderboardProperties(List<PersonalProperties> personalPropertiesList, Map<Integer, Integer> posChange, Map<Integer, Integer> uniPosChange) {
        this.personalPropertiesList = personalPropertiesList;
        this.posChange = posChange;
        this.uniPosChange = uniPosChange;
    }

    public List<PersonalProperties> getPersonalPropertiesList() {
        return personalPropertiesList;
    }

    public void setPersonalPropertiesList(List<PersonalProperties> personalPropertiesList) {
        this.personalPropertiesList = personalPropertiesList;
    }

    public Map<Integer, Integer> getPosChange() {
        return posChange;
    }

    public void setPosChange(Map<Integer, Integer> posChange) {
        this.posChange = posChange;
    }

    public Map<Integer, Integer> getUniPosChange() {
        return uniPosChange;
    }

    public void setUniPosChange(Map<Integer, Integer> uniPosChange) {
        this.uniPosChange = uniPosChange;
    }

    public List<TeamPosition> getTeamPositions() {
        return teamPositions;
    }

    public void setTeamPositions(List<TeamPosition> teamPositions) {
        this.teamPositions = teamPositions;
    }

    public List<IndividualPosition> getIndividualPositions() {
        return individualPositions;
    }

    public void setIndividualPositions(List<IndividualPosition> individualPositions) {
        this.individualPositions = individualPositions;
    }
}
