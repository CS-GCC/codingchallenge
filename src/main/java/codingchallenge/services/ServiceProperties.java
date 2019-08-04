package codingchallenge.services;

import codingchallenge.domain.subdomain.Region;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public class ServiceProperties {

    private int numberOfQuestions;
    private int contestants;
    private int universities;
    private Region region;

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
}
