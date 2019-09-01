package codingchallenge.domain.subdomain;

import java.util.Date;
import java.util.Objects;

public class TimeStampPosition extends Position {

    private Date timestamp;

    public TimeStampPosition() {
    }

    public TimeStampPosition(Position position, Date timestamp) {
        super(position.getTotal(), position.getScores(), position.getPosition());
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeStampPosition that = (TimeStampPosition) o;
        return Objects.equals(timestamp, that.timestamp)
                && getPosition() == that.getPosition()
                && getTotal() == that.getTotal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }
}
