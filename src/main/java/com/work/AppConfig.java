package com.work;

import com.work.service.DataParser;
import com.work.service.DataRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lilit on 7/21/16.
 */
@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public DataRetriever dataRetriever() {
		return new DataRetriever(restTemplate());
	}

	@Bean
	public DataParser dataParser() {
		return new DataParser();
	}
}
