package com.cts.task.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {
	private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private final MessageSource messageSource;
    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
   
    
    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiError> handleRestExceptions(RestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getApiError().getMessage(), null, locale);
        ex.printStackTrace();
        
		ApiError apiError = ex.getApiError();
		apiError.setMessage(errorMessage);
		System.out.println("Throwable error for test case "+ ex.getMessage());    
		
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
        ex.printStackTrace();
        System.out.println("Throwable error for test case "+ ex.getMessage());
		ApiError apiError = 
			      new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
