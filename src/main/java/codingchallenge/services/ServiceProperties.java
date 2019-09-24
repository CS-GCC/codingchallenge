package codingchallenge.services;

import codingchallenge.domain.subdomain.Region;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConfigurationProperties("service")
public class ServiceProperties {

    private int numberOfQuestions;
    private int contestants;
    private int universities;
    private Region region;
    private String startDate;
    private String endDate;
    private String global;
    private boolean stubbed;
    private boolean gitEnabled;
    private String gitToken;
    private String gitUsername;
    private String travisApiKey;


    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getContestants() {
        return contestants;
    }

    public void setContestants(int contestants) {
        this.contestants = contestants;
    }

    public int getUniversities() {
        return universities;
    }

    public void setUniversities(int universities) {
        this.universities = universities;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isStubbed() {
        return stubbed;
    }

    public void setStubbed(boolean stubbed) {
        this.stubbed = stubbed;
    }

    public boolean isGitEnabled() {
        return gitEnabled;
    }

    public void setGitEnabled(boolean gitEnabled) {
        this.gitEnabled = gitEnabled;
    }

    public String getGitToken() {
        return gitToken;
    }

    public void setGitToken(String gitToken) {
        this.gitToken = gitToken;
    }

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }

    public String getTravisApiKey() {
        return travisApiKey;
    }

    public void setTravisApiKey(String travisApiKey) {
        this.travisApiKey = travisApiKey;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }
}
