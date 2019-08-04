package codingchallenge.domain;

import codingchallenge.domain.subdomain.BuildStatus;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Commit {

    @Id
    private String id;
    private String sha;
    private Date timestamp;
    private BuildStatus buildStatus;
    private String logFileId;
    private String guid;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getLogFileId() {
        return logFileId;
    }

    public void setLogFileId(String logFileId) {
        this.logFileId = logFileId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
