package codingchallenge.domain;

import java.util.List;

public class HeadlineDTO {

    private List<Headline> headlines;
    private int total;

    public HeadlineDTO() {
    }

    public HeadlineDTO(List<Headline> headlines, int total) {
        this.headlines = headlines;
        this.total = total;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public int getTotal() {
        return total;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
