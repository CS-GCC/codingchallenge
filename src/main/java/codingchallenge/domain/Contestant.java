package codingchallenge.domain;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Contestant {

    @Id
    private String id;
    private String globalId;
    private int graduationYear;
    private String name;
    private String team;
    private String teamId;
    private boolean repoCreated;
    private String gitAvatar;
    private String gitRepository;
    private String gitUsername;

    public Contestant() {
    }

    public Contestant(String id, String name, String team, String teamId) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.teamId = teamId;
    }

    public Contestant(String name, String team, String teamId) {
        this.name = name;
        this.team = team;
        this.teamId = teamId;
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

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contestant that = (Contestant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, team);
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public boolean isRepoCreated() {
        return repoCreated;
    }

    public void setRepoCreated(boolean repoCreated) {
        this.repoCreated = repoCreated;
    }

    public String getGitRepository() {
        return gitRepository;
    }

    public void setGitRepository(String gitRepository) {
        this.gitRepository = gitRepository;
    }

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }

    public String getGitAvatar() {
        return gitAvatar;
    }

    public void setGitAvatar(String gitAvatar) {
        this.gitAvatar = gitAvatar;
    }
}
