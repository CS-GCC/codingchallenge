package codingchallenge.domain.subdomain;

import java.util.List;

public class Position {

    private double total;
    private List<Score> scores;
    private int position;

    public Position() {
    }

    public Position(int position) {
        this.total = 0.0;
        this.scores = null;
        this.position = position;
    }

    public Position(double total, List<Score> scores, int position) {
        this.total = total;
        this.scores = scores;
        this.position = position;
    }

    public double getTotal() {
        return total;
    }

    public List<Score> getScores() {
        return scores;
    }

    public int getPosition() {
        return position;
    }
}
