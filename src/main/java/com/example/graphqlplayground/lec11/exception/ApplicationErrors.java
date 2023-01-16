package com.example.graphqlplayground.lec11.exception;

import com.example.graphqlplayground.lec11.dto.CustomerDto;
import java.util.Map;
import org.springframework.graphql.execution.ErrorType;
import reactor.core.publisher.Mono;

public class ApplicationErrors {
	public static <T> Mono<T> noSuchCustomer(int id) {
		return Mono.error(
				new ApplicationException(
						"Customer not found",
						ErrorType.NOT_FOUND,
						Map.of(
								"customerId", id
						)
				)
		);
	}

	public static <T> Mono<T> ageErrorCustomer(CustomerDto customerDto) {
		return Mono.error(
				new ApplicationException(
						"Customer should be >=18 years",
						ErrorType.BAD_REQUEST,
						Map.of(
								"Customer", customerDto
						)
				)
		);
	}
}
