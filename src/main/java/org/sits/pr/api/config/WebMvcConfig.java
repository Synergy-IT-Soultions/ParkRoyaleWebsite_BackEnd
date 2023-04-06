package org.sits.pr.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	WebRequestInterceptor webRequestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bearerTokenInterceptor());
	}

	@Bean
	public BearerTokenInterceptor bearerTokenInterceptor() {
		BearerTokenInterceptor bearerTokenInterceptor = new BearerTokenInterceptor(webRequestInterceptor);
		bearerTokenInterceptor.setTokenWrapper(bearerTokenWrapper());
		return bearerTokenInterceptor;
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public BearerTokenWrapper bearerTokenWrapper() {
		return new BearerTokenWrapper();
	}

}