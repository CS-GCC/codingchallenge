package codingchallenge.services.impl;

import codingchallenge.domain.Contestant;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TimeStampPosition;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class TimestampPositionGenerator {

    public static void addTimeStampPosition(Contestant contestant,
                                            Position position,
                                            Date timestamp) {
        List<TimeStampPosition> timeStampPositions = contestant.getPositions();
        if (timeStampPositions == null) {
            timeStampPositions = Lists.newArrayList();
            contestant.setPositions(timeStampPositions);
            timeStampPositions.add(new TimeStampPosition(position, timestamp));
        } else {
            TimeStampPosition lastPosition =
                    timeStampPositions.get(timeStampPositions.size() - 1);
            if (lastPosition.getPosition() != position.getPosition()) {
                timeStampPositions.add(new TimeStampPosition(position
                        , timestamp));
            }
        }
    }

}
