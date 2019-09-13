package codingchallenge.domain.graphs;

import codingchallenge.domain.subdomain.Position;

import java.text.SimpleDateFormat;

public class Coordinates {

    private String x;
    private int y;

    public Coordinates(Position position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
