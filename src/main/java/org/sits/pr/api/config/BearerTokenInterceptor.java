package org.sits.pr.api.config;

import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BearerTokenInterceptor extends WebRequestHandlerInterceptorAdapter {

	  public BearerTokenInterceptor(WebRequestInterceptor requestInterceptor) {
		super(requestInterceptor);
	}

	private BearerTokenWrapper tokenWrapper;

	 
	  public void setTokenWrapper(BearerTokenWrapper tokenWrapper) {
		  this.tokenWrapper = tokenWrapper;
	  }

	  @Override
	  public boolean preHandle(HttpServletRequest request,
	          HttpServletResponse response, Object handler) throws Exception {
		  log.debug("Inside Prehandle: ");
	    final String authorizationHeaderValue = request.getHeader("Authorization");
	    log.debug("authorizationHeaderValue: "+authorizationHeaderValue);
	    if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
	      String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
	      tokenWrapper.setToken(token);
	      log.debug("token: "+token);
	    }
	    
	    return true;
	  }
	}