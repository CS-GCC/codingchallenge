package codingchallenge.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Article {

    @Id
    private String id;
    private String title;
    private String subHeading;
    private String blurb;
    private String author;
    private Date timestamp;
    private String imageUrl;
    private String body;
    private String prettyPrintedTime;

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrettyPrintedTime() {
        return prettyPrintedTime;
    }

    public void setPrettyPrintedTime(String prettyPrintedTime) {
        this.prettyPrintedTime = prettyPrintedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
