package codingchallenge.domain;

import codingchallenge.domain.subdomain.Position;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Leaderboard {

    @Id
    private String id;
    private Date timestamp;
    private List<Position> positions;
}
