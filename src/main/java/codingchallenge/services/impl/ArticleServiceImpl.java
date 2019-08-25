package codingchallenge.services.impl;

import codingchallenge.collections.ArticleRepository;
import codingchallenge.domain.Article;
import codingchallenge.domain.Headline;
import codingchallenge.domain.NewsPiece;
import codingchallenge.services.interfaces.ArticleService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Headline> getLatestArticles(int from, int limit) {
        return
                articleRepository.findArticlesByTimestampBeforeOrderByTimestamp(new Date())
                        .stream()
                        .skip(from)
                        .limit(limit)
                        .map(Headline::new)
                        .collect(Collectors.toList());
    }

    @Override
    public NewsPiece getArticle(String id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            addPrettyPrintedTimestamp(article);
            List<Headline> headlines =
                    articleRepository.findArticlesByIdIsNotOrderByTimestamp(id)
                        .stream()
                        .limit(5)
                        .map(Headline::new)
                        .collect(Collectors.toList());
            return new NewsPiece(article, headlines);
        }
        return null;
    }

    @Override
    public void addArticle(Article article) {
        articleRepository.insert(article);
    }

    private void addPrettyPrintedTimestamp(Article article) {
        PrettyTime prettyTime = new PrettyTime();
        article.setPrettyPrintedTime(prettyTime.format(article.getTimestamp()));
    }
}
