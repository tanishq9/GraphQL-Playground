package com.example.graphqlplayground.lec10.config;

import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationCachingConfig {

	@Bean
	public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer(PreparsedDocumentProvider provider) {
		return c -> c.configureGraphQl(builder -> builder.preparsedDocumentProvider(provider));
	}

	/*
	 * request body
	 * parse
	 * validation
	 * executing doc
	 * */

	@Bean
	public PreparsedDocumentProvider provider() {

		// treat this as cache
		// ideally use cache with eviction strategy like caffeine/lru cache
		Map<String, PreparsedDocumentEntry> map = new ConcurrentHashMap<>();

		return new PreparsedDocumentProvider() {

			// this function contains the parsing and validation logic
			// if we want to improve the performance then we would to do the caching ourselves
			@Override
			public PreparsedDocumentEntry getDocument(ExecutionInput executionInput, Function<ExecutionInput, PreparsedDocumentEntry> parseAndValidateFunction) {
				if (map.containsKey(executionInput.getQuery())) {
					return map.get(executionInput.getQuery());
				}
				PreparsedDocumentEntry preparsedDocumentEntry = parseAndValidateFunction.apply(executionInput);
				map.put(executionInput.getQuery(), preparsedDocumentEntry);
				return preparsedDocumentEntry;
			}
		};
	}
}
