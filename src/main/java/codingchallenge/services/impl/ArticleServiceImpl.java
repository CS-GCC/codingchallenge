package codingchallenge.services.impl;

import codingchallenge.collections.ArticleRepository;
import codingchallenge.domain.Article;
import codingchallenge.domain.Headline;
import codingchallenge.domain.NewsPiece;
import codingchallenge.services.ServiceProperties;
import codingchallenge.services.interfaces.ArticleService;
import com.google.common.collect.Lists;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final RestTemplate restTemplate;
    private final ServiceProperties serviceProperties;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              RestTemplate restTemplate,
                              ServiceProperties serviceProperties) {
        this.articleRepository = articleRepository;
        this.restTemplate = restTemplate;
        this.serviceProperties = serviceProperties;
    }

    @Override
    public List<Headline> getLatestArticles(int from, int limit) {
        ResponseEntity<List> articlesEntity =
                restTemplate.getForEntity(serviceProperties.getGlobal() +
                        "/news/headlines/" + serviceProperties.getRegion() +
                                "?from=" + from + "&limit=" + limit,
                        List.class);
        try {
            return
                    (List<Headline>) articlesEntity.getBody();
        } catch (Exception e) {
            return Lists.newArrayList();
        }
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
