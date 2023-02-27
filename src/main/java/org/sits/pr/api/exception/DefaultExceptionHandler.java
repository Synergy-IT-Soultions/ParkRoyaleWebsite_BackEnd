package org.sits.pr.api.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.sits.pr.api.exception.dto.ErrorMessage;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Order(10)
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public final ResponseEntity<ErrorMessage> authenticationException(AuthenticationCredentialsNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorMessage(ex.getMessage());
		errorMessage.setErrorDesc("Authentication Exception");
		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> generalException(Exception ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorMessage(ex.getMessage());
		errorMessage.setErrorDesc("Something went wrong! Please try again later.");

		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public final ResponseEntity<ErrorMessage> getMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		String errorMsg = " Size of the attached file exceeds the limit of 1 MB, please reduce the size and upload again";
		errorFields.put("file", errorMsg);
		errorMessage.setErrorMessage(errorMsg);
		errorMessage.setErrorDesc(ex.getMostSpecificCause().getMessage());
		errorMessage.setErrorAttributes(errorFields);
		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	
	private String getExceptionStackTrace(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		return sStackTrace;
	}
}
