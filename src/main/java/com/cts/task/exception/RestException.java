package com.cts.task.exception;

public class RestException extends Exception {

	private static final long serialVersionUID = 100000146;
private ApiError error;
	
	public RestException(final ApiError error) {
		super(error.toString());
		this.error = error;
	}
	
	public ApiError getApiError(){
		return error;
	}
}
