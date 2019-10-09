package codingchallenge.collections;

import codingchallenge.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findArticlesByTimestampBeforeOrderByTimestampDesc(Date timestamp);
    List<Article> findArticlesByIdIsNotOrderByTimestampDesc(String id);
}
