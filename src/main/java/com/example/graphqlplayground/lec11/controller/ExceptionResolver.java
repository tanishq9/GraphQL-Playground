package com.example.graphqlplayground.lec11.controller;

import com.example.graphqlplayground.lec11.exception.ApplicationException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExceptionResolver implements DataFetcherExceptionResolver {

	@Override
	public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

		System.out.println("Inside exception resolver");

		// change any exception to ApplicationException
		var ex = convertToApplicationException(exception);

		System.out.println("Application exception: " + ex.toString());
		return Mono.just(
				List.of(
						GraphqlErrorBuilder.newError()
								.message(ex.getMessage())
								.errorType(ex.getErrorType())
								.extensions(ex.getExtensions())
								/*.extensions(
										Map.of(
												"key1", "value1",
												"timestamp", LocalDateTime.now()
										)
								)*/
								.build()
				)
		);
	}

	private ApplicationException convertToApplicationException(Throwable exception) {
		if (!exception.getClass().equals(ApplicationException.class)) {
			return new ApplicationException(
					exception.getMessage(),
					ErrorType.INTERNAL_ERROR,
					Collections.emptyMap()
			);
		}
		return (ApplicationException) exception;
	}
}
