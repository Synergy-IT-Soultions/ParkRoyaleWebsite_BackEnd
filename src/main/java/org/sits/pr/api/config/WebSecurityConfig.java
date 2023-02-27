package org.sits.pr.api.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.sits.pr.api.config.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.swagger.v3.oas.models.PathItem.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.addFilterBefore(corsFilter(), SessionManagementFilter.class)
				
				.csrf(AbstractHttpConfigurer::disable)
				
				.authorizeHttpRequests((requests) -> requests
				.requestMatchers(new AntPathRequestMatcher("/content/save/**", HttpMethod.OPTIONS.name())).permitAll()	
				.requestMatchers(new AntPathRequestMatcher("/image/upload/**", HttpMethod.OPTIONS.name())).permitAll()
				.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.exceptionHandling((ex) -> ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
				.build();
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.addFilterBefore(corsFilter(), SessionManagementFilter.class)
				.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests
				.requestMatchers(new AntPathRequestMatcher("/user/authenticate")).permitAll()
			//	.requestMatchers(new AntPathRequestMatcher("/image/download/**")).permitAll()
			//	.requestMatchers(new AntPathRequestMatcher("/content/get/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/image/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/content/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/configuration/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/v2/api-docs/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).exceptionHandling(ex -> {
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).httpBasic(withDefaults()).build();
	}
	
	@Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }
	
}
