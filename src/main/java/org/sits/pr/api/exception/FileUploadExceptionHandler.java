package org.sits.pr.api.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOException;

import org.sits.pr.api.controller.FileController;
import org.sits.pr.api.exception.custom.ContainerNotFoundException;
import org.sits.pr.api.exception.custom.ImageFormatNotSupportedException;
import org.sits.pr.api.exception.dto.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(assignableTypes = FileController.class)
@Slf4j
@Order(1)
public class FileUploadExceptionHandler extends ResponseEntityExceptionHandler {

	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxUploadFileSize;

	@ExceptionHandler(ContainerNotFoundException.class)
	public final ResponseEntity<ErrorMessage> getContainerNotFoundException(ContainerNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		errorFields.put("containerDivId", ex.getMessage());
		errorMessage.setErrorMessage(ex.getMessage());
		errorMessage.setErrorDesc("Container entered is not available in the master "
				+ "table T204_Container_Data. If this is a valid container, "
				+ "Please make an entry in the table for the container");
		errorMessage.setErrorAttributes(errorFields);

		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<ErrorMessage> dataIntegrityViolationException(DataIntegrityViolationException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		errorMessage.setErrorMessage(getAppropriateConstraintErrorMessage(ex.getMostSpecificCause().getMessage(), errorFields));
		errorMessage.setErrorDesc(ex.getMostSpecificCause().getMessage());
		errorMessage.setErrorAttributes(errorFields);
		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}

	private String getAppropriateConstraintErrorMessage(String exceptionMessage, Map<String, String> errorFields) {
		if (exceptionMessage.contains("UNIQUE KEY constraint")) {
			if (exceptionMessage.contains("T301_Image_Info")) {
				String errorMessage = "Image Name already exists, please enter a different name.";
				errorFields.put("imageName", errorMessage);
				return errorMessage;
			}
		} else if (exceptionMessage.contains("FOREIGN KEY constraint")) {
			if (exceptionMessage.contains("C101_User_Id")) {
				String errorMessage  =  "Updated By provided in the request does not exist, please try login again!";
				errorFields.put("updatedBy", errorMessage);
				return errorMessage;
			}
		}
		return "Data Integrity Error";
	}

	@ExceptionHandler(ImageFormatNotSupportedException.class)
	public final ResponseEntity<ErrorMessage> getImageFormatNotSupportedException(ImageFormatNotSupportedException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		String errorMsg = "Unable to upload the attached image, Please correct the following error and attach again, "
				+ ex.getMessage();
		errorFields.put("file", errorMsg);
		errorMessage.setErrorMessage(errorMsg);
		errorMessage.setErrorDesc(ex.getCause().getMessage());
		errorMessage.setErrorAttributes(errorFields);
		log.error("Error Occured : " + getExceptionStackTrace(ex));
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorMessage> getConstraintViolationException(ConstraintViolationException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		ex.getConstraintViolations().forEach(error -> {
			errorFields.put(error.getPropertyPath().toString(), error.getMessage());
		});
		errorMessage.setErrorMessage("Input Fields has errors");
		errorMessage.setErrorDesc("Input fields has errors check the map for all the exact fields");
		errorMessage.setErrorAttributes(errorFields);
		return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(IIOException.class)
	public final ResponseEntity<ErrorMessage> getIIOException(IIOException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		Map<String, String> errorFields = new HashMap<String, String>();
		String errorMsg = "Unable to upload the attached image, Please correct the following error and attach again, "
				+ ex.getMessage();
		errorFields.put("file", errorMsg);
		errorMessage.setErrorMessage(errorMsg);
		errorMessage.setErrorDesc(ex.getCause().getMessage());
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
