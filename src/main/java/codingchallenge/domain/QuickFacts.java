package codingchallenge.domain;

import codingchallenge.domain.subdomain.Region;

public class QuickFacts {

    private Region region;
    private long numberOfContestants;
    private String leadingIndividual;
    private String leadingTeam;

    public QuickFacts(long numberOfContestants, String leadingIndividual,
                      String leadingTeam, Region region) {
        this.numberOfContestants = numberOfContestants;
        this.leadingIndividual = leadingIndividual;
        this.leadingTeam = leadingTeam;
        this.region = region;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
