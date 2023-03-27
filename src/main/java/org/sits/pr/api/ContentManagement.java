package org.sits.pr.api;

import org.sits.pr.api.config.properties.RsaKeysProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(value= { RsaKeysProperties.class })
@EnableTransactionManagement
public class ContentManagement  {
	public static void main(String[] args) {
		SpringApplication.run(ContentManagement.class, args);	
	}
}

