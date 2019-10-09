package codingchallenge.domain;

public class Headline {

    private String id;
    private String blurb;
    private String title;
    private String author;
    private String imageUrl;

    public Headline() {
    }

    public Headline(Article article) {
        this.id = article.getId();
        this.blurb = article.getBlurb();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.imageUrl = article.getImageUrl();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
