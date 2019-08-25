package codingchallenge.services.interfaces;

import codingchallenge.domain.Article;
import codingchallenge.domain.Headline;
import codingchallenge.domain.NewsPiece;

import java.util.List;

public interface ArticleService {

    List<Headline> getLatestArticles(int from, int limit);
    NewsPiece getArticle(String id);
    void addArticle(Article article);

}
