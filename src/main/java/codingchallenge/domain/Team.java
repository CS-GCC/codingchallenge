package codingchallenge.domain;

import codingchallenge.domain.subdomain.Registration;

import java.util.List;

public class Team extends Contestant {

    private List<Registration> registeredContestants;

    public Team() {
        super();
    }

    public Team(String id, String name, String team,
                List<Registration> registeredContestants) {
        super(id, name, team);
        this.registeredContestants = registeredContestants;
    }

    public List<Registration> getRegisteredContestants() {
        return registeredContestants;
    }

    public void setRegisteredContestants(List<Registration> registeredContestants) {
        this.registeredContestants = registeredContestants;
    }
}
