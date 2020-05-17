package com.anjali.xebia;

import com.anjali.xebia.Model.Article;
import com.anjali.xebia.Repository.ArticleRepository;
import com.anjali.xebia.Service.ArticleServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class XebiaApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

	@Autowired
	private ArticleRepository articleRepository;

	@Before
	public void before() {
		this.articleRepository.deleteAll();
		final Article article1 = new Article("First Title","First Description","First Article Body", Arrays.asList("JaVa","spring boot"));
		final Article article2 = new Article("Second Ttl","Second Desc","Second Atl Body", Arrays.asList("JaVa","spring boot","hibernate"));
        final Article article3 = new Article("First Title","First Description","First Article Body", Arrays.asList("Microservices","spring boot"));
        final Article article4 = new Article("First Title","First Description","First Article Body", Arrays.asList("JaVa","Microservices","HiberNate"));
        List<Article> list = Arrays.asList(article1, article2, article3, article4);
		this.articleRepository.saveAll(list);
		/*this.articleRepository.insert(article2);
		this.articleRepository.insert(article3);
		this.articleRepository.insert(article4);*/
	}

  	@Test
    public void getAllArticles() {
		List<Article> articleList = this. articleRepository.findAll();
		Assertions.assertNotNull(articleList);
		Assertions.assertEquals(4,articleList.size());
        Assertions.assertEquals("First Title",articleList.get(0).getTitle());
        Assertions.assertEquals("First Article Body",articleList.get(0).getBody());
        Assertions.assertEquals("First Description",articleList.get(0).getDescription());

	}

	@Test
    public void get_an_article_by_Id() {
		final Article article1 = new Article("First Title","First Description","First Article Body", Arrays.asList("JaVa","spring boot"));
        String id = this.articleRepository.findAll().get(0).getId();
		Article article = this.articleRepository.findByIdEquals(id);
		Assertions.assertNotNull(article);
		Assertions.assertEquals(article.getTitle(),article1.getTitle());
		Assertions.assertEquals(article.getDescription(),article1.getDescription());
		Assertions.assertEquals(article.getBody(),article1.getBody());
    }

    @Test
	public void successfully_delete_an_article() {
		Article article = this.articleRepository.findAll().get(0);
		this.articleRepository.delete(article);
		List<Article> articleList = this.articleRepository.findAll();
		Assertions.assertEquals(3,articleList.size());
	}

}
