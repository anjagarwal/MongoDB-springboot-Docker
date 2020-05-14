package com.anjali.xebia.Repository;

import com.anjali.xebia.Model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article,String> {

    Article findByIdEquals(String id);

}
