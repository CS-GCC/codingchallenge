package codingchallenge.domain;

import java.util.UUID;

public class TravisUUID {

    private String contestantId;
    private UUID uuid;

    public TravisUUID(String contestantId, UUID uuid) {
        this.contestantId = contestantId;
        this.uuid = uuid;
    }

    public String getContestantId() {
        return contestantId;
    }

    public void setContestantId(String contestantId) {
        this.contestantId = contestantId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
