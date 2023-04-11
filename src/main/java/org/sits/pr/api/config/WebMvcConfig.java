package org.sits.pr.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private List<String> pathPatternList;
	
	WebMvcConfig() {
		pathPatternList = new ArrayList<String>();
		pathPatternList.add("/content/save/**");
		pathPatternList.add("/image/upload/**");
		pathPatternList.add("/image/delete/**");
	}
	
	@Autowired
	WebRequestInterceptor webRequestInterceptor;
	
	AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bearerTokenInterceptor()).addPathPatterns(pathPatternList).pathMatcher(pathMatcher);
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
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

}