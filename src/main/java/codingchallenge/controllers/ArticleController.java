package codingchallenge.controllers;

import codingchallenge.domain.Article;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Headline;
import codingchallenge.domain.NewsPiece;
import codingchallenge.services.interfaces.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @CrossOrigin
    @RequestMapping(path = "/news/headlines", method = RequestMethod.GET)
    public List<Headline> getHeadlines(@RequestParam("from") int from,
                                      @RequestParam("limit") int limit) {
        return articleService.getLatestArticles(from, limit);
    }

    @CrossOrigin
    @RequestMapping(path = "/news/{id}", method = RequestMethod.GET)
    public NewsPiece getArticle(@PathVariable String id) {
        return articleService.getArticle(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/news/addstory", method = RequestMethod.POST)
    public void addStory(@RequestBody Article article) {
        articleService.addArticle(article);
    }

}
