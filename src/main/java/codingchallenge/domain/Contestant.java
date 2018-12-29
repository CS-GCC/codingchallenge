package codingchallenge.domain;

import codingchallenge.domain.subdomain.TimeStampPosition;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Contestant {

    @Id
    private String id;
    private String name;
    private String team;
    private String email;
    private String gitRepository;
    private String herokuServer;
    private List<TimeStampPosition> positions;
    private boolean runPending;

    public Contestant() {
    }

    public Contestant(String id, String name, String team, String email) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.email = email;
    }

    public Contestant(String name, String team, String email) {
        this.name = name;
        this.team = team;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGitRepository() {
        return gitRepository;
    }

    public void setGitRepository(String gitRepository) {
        this.gitRepository = gitRepository;
    }

    public String getHerokuServer() {
        return herokuServer;
    }

    public void setHerokuServer(String herokuServer) {
        this.herokuServer = herokuServer;
    }
}
