package org.sits.pr.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
@Profile(value = { "test", "stage" })
public class SwaggerConfig {
	
	@Bean
	public OpenAPI baseOpenAPI() {
		return new OpenAPI()
				.info(new Info()
				.title("Park Royale API")
				.version("1.0.0")
				.description("APIs to fetch and save th content of the website is documented here"));
	}
}

