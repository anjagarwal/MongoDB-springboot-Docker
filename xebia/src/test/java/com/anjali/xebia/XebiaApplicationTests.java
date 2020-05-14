package com.anjali.xebia;

import com.anjali.xebia.Controller.ArticleController;
import com.anjali.xebia.Model.Article;
import com.anjali.xebia.Repository.ArticleRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class XebiaApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;

	@Before
	void contextLoads() {
		articleRepository.deleteAll();
		articleRepository.saveAll(list);
	}

	private final Article article1 = new Article("First Title","First Description","First Article Body", Arrays.asList("JaVa","spring boot"));
	private final Article article2 = new Article("Second Ttl","Second Desc","Second Atl Body", Arrays.asList("JaVa","spring boot","hibernate"));
	private final Article article3 = new Article("First Title","First Description","First Article Body", Arrays.asList("Microservices","spring boot"));
	private final Article article4 = new Article("First Title","First Description","First Article Body", Arrays.asList("JaVa","Microservices","HiberNate"));
	List<Article> list = Arrays.asList(article1, article2, article3, article4);

  	@Test
	public void getAllArticles() {
		List<Article> articleList = articleRepository.findAll();
		Assertions.assertNotNull(articleList);
		int i = 0;
		for (Article article:articleList) {
			Assertions.assertEquals(article.getTitle(),list.get(i).getTitle());
			Assertions.assertEquals(article.getBody(),list.get(i).getBody());
			Assertions.assertEquals(article.getDescription(),list.get(i++).getDescription());
		}
	}

}
