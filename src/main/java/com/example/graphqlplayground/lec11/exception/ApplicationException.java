package com.example.graphqlplayground.lec11.exception;

import java.util.Map;
import lombok.ToString;
import org.springframework.graphql.execution.ErrorType;

@ToString
public class ApplicationException extends RuntimeException {
	String message;
	ErrorType errorType;
	Map<String, Object> extensions;

	public ApplicationException(String message, ErrorType errorType, Map<String, Object> extensions) {
		super(message);
		this.message = message;
		this.errorType = errorType;
		this.extensions = extensions;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public Map<String, Object> getExtensions() {
		return extensions;
	}
}
