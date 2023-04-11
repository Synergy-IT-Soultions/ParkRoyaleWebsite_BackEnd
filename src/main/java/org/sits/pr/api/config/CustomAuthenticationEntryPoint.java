package org.sits.pr.api.config;

import java.io.IOException;

import org.sits.pr.api.exception.dto.ErrorMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
    throws IOException, ServletException {
    	
    	log.error("Exception: " + authException.getMessage());
    	authException.printStackTrace();
    	
    	 if (authException != null) {
	    	ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setErrorMessage("Invalid Credentials");
			errorMessage.setErrorDesc("Authentication Exception");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(errorMessage);
	
	    	response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getOutputStream().println(json);
      }
     
    }
  }





