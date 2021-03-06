package com.cts.task.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class RestExceptionHandler  {
	private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private final MessageSource messageSource;
    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> invalidFormatException(HttpMessageNotReadableException ex, Locale locale) {
    	    	    
    	 String errorMessage = messageSource.getMessage("Exception.invalidFormat", null, locale);
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, errorMessage, ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)    
    public ResponseEntity<ApiError> methodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
    	String errorMessage = "";    	
    	try
    	{
    	 errorMessage = messageSource.getMessage(ex.getBindingResult().getFieldError().getCode()+"."+ex.getBindingResult().getFieldError().getField(), null, locale);
    	}
    	catch(Exception e)
    	{    		
    		errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);    		
    	}    	    	
    	ex.printStackTrace();    	
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, errorMessage, ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
   
    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiError> handleRestExceptions(RestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getApiError().getMessage(), null, locale);
        ex.printStackTrace();          
		ApiError apiError = ex.getApiError();
		apiError.setMessage(errorMessage);			
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> databaseException(DataIntegrityViolationException ex, Locale locale) {
    	    	    
    	 String errorMessage = messageSource.getMessage("Exception.DBError", null, locale);
         ex.printStackTrace();          		    	
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, errorMessage, ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);        
        ex.printStackTrace();       
		ApiError apiError = 
			      new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}
