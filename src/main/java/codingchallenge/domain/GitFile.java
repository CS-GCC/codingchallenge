package codingchallenge.domain;

import codingchallenge.domain.subdomain.Language;
import org.springframework.data.annotation.Id;

public class GitFile {

    @Id
    private String id;
    private Language language;
    private String name;
    private String data;

    public GitFile() {
    }

    public GitFile(Language language, String name, String data) {
        this.language = language;
        this.name = name;
        this.data = data;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
