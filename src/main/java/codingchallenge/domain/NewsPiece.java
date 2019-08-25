package codingchallenge.domain;

import java.util.List;

public class NewsPiece {

    private Article article;
    private List<Headline> headlines;

    public NewsPiece(Article article, List<Headline> headlines) {
        this.article = article;
        this.headlines = headlines;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Headline> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<Headline> headlines) {
        this.headlines = headlines;
    }
}
