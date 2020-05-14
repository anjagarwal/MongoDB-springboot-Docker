package com.anjali.xebia.Service;

import com.anjali.xebia.Model.Article;
import com.anjali.xebia.Model.TagMetrics;
import com.anjali.xebia.Model.TimeToRead;
import com.anjali.xebia.Model.TimeToReadArticle;
import com.anjali.xebia.Repository.ArticleRepository;
import net.ricecode.similarity.StringSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    StringSimilarityService stringSimilarityService;

    @Value("${speed.of.average.human}")
    private int averageSpeed; //time taken in seconds

    @Override
    public ResponseEntity createArticle(Article article) {
        boolean stringSimilarity = validateArticleBody(article.getBody());
        if (stringSimilarity) {
            article.setCreatedAt(new Date());
            article.setUpdatedAt(new Date());
            String s = article.getTitle();
            s = s.replaceAll(" ", "-").toLowerCase();
            article.setSlug(s);
            return new ResponseEntity<>(mongoTemplate.save(article), HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Article with almost same body is already present",HttpStatus.BAD_REQUEST);
    }

    private boolean validateArticleBody(String body) {
        List<Article> bodyList = articleRepository.findAll();
        List<String> strings =  bodyList.stream().map(Article::getBody).collect(Collectors.toList());
        List<String> similarBodyList = strings.stream().filter(body1 -> stringSimilarityService.score(body,body1) > 0.7).collect(Collectors.toList());
        if (similarBodyList.size() > 0)
            return false;
        else
            return true;
    }

    @Override
    public ResponseEntity updateArticle(String id, Article article) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Article updatedArticle =  mongoTemplate.findOne(query, Article.class);
        if (null == updatedArticle || stringSimilarityService.score(article.getTitle(), updatedArticle.getTitle()) > 0.70)
            return new ResponseEntity<>("Title is almost similar",HttpStatus.BAD_REQUEST);
        else {
            String title = article.getTitle().replaceAll(" ","-").toLowerCase();
            updatedArticle.setSlug(title);
            updatedArticle.setUpdatedAt(new Date());
            mongoTemplate.save(updatedArticle);
            return new ResponseEntity<>(updatedArticle, HttpStatus.FOUND);
        }
    }

    @Override
    public Article getAnArticle(String id) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Article article = mongoTemplate.findOne(query, Article.class);
        if (null == article)
            throw new Exception("Article with given id is not found");
        else
            return article;
    }

    @Override
    public Map<String,Object> getAllArticles(int pageNo, int pageSize, String sortBy) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();
        Sort sort = Sort.by(sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
        Page<Article> page = articleRepository.findAll(pageRequest);
        if (null == page)
            throw new Exception("No articles found");
        else {
            responseMap.put("data",page);
            responseMap.put("Total no. of elements",page.getTotalElements());
            responseMap.put("Total no. of pages", page.getTotalPages());
            responseMap.put("Current page no.",page.getNumber());
        }
        return responseMap;
    }

    @Override
    public Article deleteArticle(String id) {
        Article article = articleRepository.findByIdEquals(id);
        //articleRepository.deleteById(id);
        articleRepository.delete(article);
        return article;
    }

    @Override
    public TimeToReadArticle getTimeToRead(String id) {
        TimeToReadArticle timeToReadArticle = new TimeToReadArticle();
        timeToReadArticle.setArticleId(id);
        Article article = articleRepository.findByIdEquals(id);
        int totalNoOfWords = article.getBody().split(" ").length;
        int time = totalNoOfWords/averageSpeed;
        int minutes = time / 60;
        int seconds = time % 60;
        timeToReadArticle.setTimeToRead(new TimeToRead(minutes,seconds));
        return timeToReadArticle;
    }

    @Override
    public List<TagMetrics> getAllTags() {
        List<TagMetrics> tagMetricsList = new ArrayList<>();
        TagMetrics tagMetrics;
        List<Article> articleList = articleRepository.findAll();
        List<String> strings =  articleList.stream().flatMap(article -> article.getTags().stream()).collect(Collectors.toList());
        Map<String, Long> tags =  strings.stream().collect(Collectors.groupingBy(e -> e.toLowerCase(), Collectors.counting()));
        for (Map.Entry<String, Long> entry : tags.entrySet()) {
            tagMetrics = new TagMetrics();
            tagMetrics.setTag(entry.getKey());
            tagMetrics.setOccurence(String.valueOf(entry.getValue()));
            tagMetricsList.add(tagMetrics);

        }
        return tagMetricsList;
    }
}
