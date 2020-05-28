package com.anjali.xebia.Controller;

import com.anjali.xebia.Model.Article;
import com.anjali.xebia.Service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/articles")
    @PostMapping
    public ResponseEntity createArticle(@RequestBody Article article)
    {
        LOGGER.info("Create an article with body ", article);
        try {
            if (article.getTitle().isEmpty() || null==article.getTitle() || article.getDescription().isEmpty() || null==article.getDescription() || article.getBody().isEmpty() || null==article.getBody() )
                return  new ResponseEntity<>("Request validation failed", HttpStatus.BAD_REQUEST);
            else
                return articleService.createArticle(article);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles/{slug-uuid}", method = RequestMethod.PATCH)
    @PatchMapping
    public ResponseEntity updateArticle(@RequestBody Article article, @PathVariable("slug-uuid") String id)
    {
        article.setId(id);
        try {
            System.out.println("Id of article to be updated is: " + id);
            if (article.getTitle().isEmpty() || null==article.getTitle())
                return  new ResponseEntity<>("Request validation failed", HttpStatus.BAD_REQUEST);
            else
                return articleService.updateArticle(id,article);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles/{slug-uuid}", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity getAnArticle(@PathVariable("slug-uuid") String id)
    {
        try {
            return new ResponseEntity<>(articleService.getAnArticle(id), HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    @GetMapping
    public ResponseEntity getAllArticles(@RequestBody Map<String, Object> params)
    {
        try {
            return new ResponseEntity<>(articleService.getAllArticles((int)params.getOrDefault("pageNo", 0),
                    (int)params.getOrDefault("pageSize", 2), (String)params.getOrDefault("sortBy", "body")), HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles/{slug-uuid}", method = RequestMethod.DELETE)
    @DeleteMapping
    public ResponseEntity deleteArticle(@PathVariable("slug-uuid") String id)
    {
        try {
            return new ResponseEntity<>(articleService.deleteArticle(id), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles/{slug-uuid}/timetoread", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity getTimeToRead(@PathVariable("slug-uuid") String id)
    {
        try {
            return new ResponseEntity<>(articleService.getTimeToRead(id), HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/articles/tags", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity getTags()
    {
        try {
            return new ResponseEntity<>(articleService.getAllTags(), HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
