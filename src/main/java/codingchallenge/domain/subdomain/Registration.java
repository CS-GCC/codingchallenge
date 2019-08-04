package codingchallenge.domain.subdomain;

import java.util.Date;

public class Registration {

    private String contestant;
    private Date timeStamp;

    public Registration(String contestant, Date timeStamp) {
        this.contestant = contestant;
        this.timeStamp = timeStamp;
    }

    public String getContestant() {
        return contestant;
    }

    public void setContestant(String contestant) {
        this.contestant = contestant;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
