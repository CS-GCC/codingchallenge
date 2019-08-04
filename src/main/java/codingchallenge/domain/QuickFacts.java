package codingchallenge.domain;

public class QuickFacts {

    private long numberOfContestants;
    private String leadingIndividual;
    private String leadingTeam;

    public QuickFacts(long numberOfContestants, String leadingIndividual,
                      String leadingTeam) {
        this.numberOfContestants = numberOfContestants;
        this.leadingIndividual = leadingIndividual;
        this.leadingTeam = leadingTeam;
    }

    public long getNumberOfContestants() {
        return numberOfContestants;
    }

    public void setNumberOfContestants(long numberOfContestants) {
        this.numberOfContestants = numberOfContestants;
    }

    public String getLeadingIndividual() {
        return leadingIndividual;
    }

    public void setLeadingIndividual(String leadingIndividual) {
        this.leadingIndividual = leadingIndividual;
    }

    public String getLeadingTeam() {
        return leadingTeam;
    }

    public void setLeadingTeam(String leadingTeam) {
        this.leadingTeam = leadingTeam;
    }
}
