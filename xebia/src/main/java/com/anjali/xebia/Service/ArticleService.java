package com.anjali.xebia.Service;

import com.anjali.xebia.Model.Article;
import com.anjali.xebia.Model.TagMetrics;
import com.anjali.xebia.Model.TimeToReadArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ArticleService {

    ResponseEntity createArticle(Article article);

    ResponseEntity updateArticle(String id, Article article) throws Exception;

     Article getAnArticle(String id) throws Exception;

     Map<String,Object> getAllArticles(int pageNo, int pageSize, String sortBy) throws Exception;

     Article deleteArticle(String id);

     TimeToReadArticle getTimeToRead(String id) throws Exception;

     List<TagMetrics> getAllTags();
}
