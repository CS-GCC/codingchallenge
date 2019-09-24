package codingchallenge.domain;

import codingchallenge.domain.subdomain.Language;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class GitRepo {

    @Id
    private String id;
    private String username;
    private String repoName;
    private Language language;

    public GitRepo() {
    }

    public GitRepo(String username, Language language, int counter) {
        this.username = username;
        this.repoName = "GCC-" + language.toString().toLowerCase() + "-" + counter;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitRepo gitRepo = (GitRepo) o;
        return Objects.equals(username, gitRepo.username) &&
                Objects.equals(repoName, gitRepo.repoName) &&
                language == gitRepo.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, repoName, language);
    }
}
