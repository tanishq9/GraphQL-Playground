package com.example.graphqlplayground.lec12.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Service
public class HeaderCheckFilter implements WebFilter {

	// not involving the graphql layer unlike request interceptor
	@Override
	public Mono<Void> filter(ServerWebExchange webExchange, WebFilterChain filterChain) {
		System.out.println("Inside WebFilter");
		var isEmpty = webExchange.getRequest().getHeaders().getOrEmpty("caller-id").isEmpty();
		return !isEmpty ? filterChain.filter(webExchange)
				: Mono.fromRunnable(() -> webExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST));
	}
}
