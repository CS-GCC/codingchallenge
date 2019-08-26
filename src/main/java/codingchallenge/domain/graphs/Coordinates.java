package codingchallenge.domain.graphs;

import codingchallenge.domain.subdomain.TimeStampPosition;

import java.text.SimpleDateFormat;

public class Coordinates {

    private String x;
    private int y;

    public Coordinates(TimeStampPosition position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        x = dateFormat.format(position.getTimestamp());
        y = position.getPosition();
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
