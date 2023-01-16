package com.example.graphqlplayground.lec01;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class GraphQLController {

	/*@QueryMapping(name = "sayHello")
	public Mono<String> sayHello() {
		return Mono.just("Hello World");
	}
*/
	// if the controller method name does not match with method name in graphql query type then we have to mention it explicitly inside @QueryMapping
	@QueryMapping(name = "sayHello")
	public Mono<String> helloWorld() {
		return Mono.just("Hello World");
	}

	/*@QueryMapping
	public Mono<String> sayHelloTo(@Argument String name) {
		return Mono.fromSupplier(() -> "Hello " + name);
	}*/

	// if the arg name mentioned in method (value in this case) does not match arg name in graphql query then we have to explicitly mention inside @Argument
	@QueryMapping(name = "sayHelloTo")
	public Mono<String> sayHelloToExplicitArgName(@Argument("name") String value) {
		System.out.println("Arg name value: " + value);
		return Mono.fromSupplier(() -> "Hello " + value);
	}
}
