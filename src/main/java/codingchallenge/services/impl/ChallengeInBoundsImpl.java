package codingchallenge.services.impl;

import codingchallenge.domain.Status;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.ChallengeInBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class ChallengeInBoundsImpl implements ChallengeInBounds {

    private final ServiceProperties serviceProperties;

    @Autowired
    public ChallengeInBoundsImpl(ServiceProperties ServiceProperties) {
        this.serviceProperties = ServiceProperties;
    }

    @Override
    public Status challengeInBounds() {
        String start = serviceProperties.getStartDate();
        String end = serviceProperties.getEndDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy " +
                "HH:mm:ss");
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            Date currentDate = new Date();
            if (currentDate.after(startDate)) {
                if (currentDate.before(endDate)) {
                    return Status.IN_PROGRESS;
                }
                return Status.FINISHED;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Status.NOT_STARTED;
    }
}
