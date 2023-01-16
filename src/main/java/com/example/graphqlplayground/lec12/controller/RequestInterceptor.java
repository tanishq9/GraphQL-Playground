package com.example.graphqlplayground.lec12.controller;

import java.util.Map;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RequestInterceptor implements WebGraphQlInterceptor {

	@Override // image client has to pass a header caller-id and we need to pass that info to controller
	public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

		System.out.println("Inside request interceptor");

		// RSocketGraphQlInterceptor --> depending upon the transport protocol, we would be using the apt interceptor

		// list of string with one value or empty list if client has not passed caller-id
		var headers = request.getHeaders().getOrEmpty("caller-id");

		var callerId = headers.isEmpty() ? "" : headers.get(0);

		// modify the execution input with caller-id info
		request.configureExecutionInput(
				((executionInput, builder) ->
						builder.graphQLContext(
								Map.of(
										"caller-id", callerId
								)
						).build()
				)
		);
		return chain.next(request);
	}
}
