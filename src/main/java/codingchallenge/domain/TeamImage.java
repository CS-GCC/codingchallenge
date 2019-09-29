package codingchallenge.domain;

public class TeamImage {

    private String id;
    private String name;
    private String imageUrl;
    private String region;

    public TeamImage() {
    }

    public TeamImage(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.imageUrl = team.getGitAvatar();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
