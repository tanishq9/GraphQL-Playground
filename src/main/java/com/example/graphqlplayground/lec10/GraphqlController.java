package com.example.graphqlplayground.lec10;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class GraphqlController {

	@QueryMapping
	public Mono<String> sayHello(@Argument String name) {
		return Mono.fromSupplier(() -> "Hello " + name);
	}
}
