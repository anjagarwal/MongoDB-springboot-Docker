package com.anjali.xebia;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XebiaApplication {


	@Bean
	public SimilarityStrategy jaroWinklerStrategy() {
		return new JaroWinklerStrategy();
	}

	@Bean
	public StringSimilarityService stringSimilarityService() {
		return new StringSimilarityServiceImpl(new JaroWinklerStrategy());
	}

	public static void main(String[] args) {
		SpringApplication.run(XebiaApplication.class, args);
	}

}
