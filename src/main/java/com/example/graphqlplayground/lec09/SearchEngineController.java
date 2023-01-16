package com.example.graphqlplayground.lec09;

import com.example.graphqlplayground.lec09.dto.Book;
import com.example.graphqlplayground.lec09.dto.Electronics;
import com.example.graphqlplayground.lec09.dto.Fruit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class SearchEngineController {

	// Union in GraphQL schema = oneOf.
	// In case of GraphQL API which returns a union, we have to expect all type of fields in GraphQL query that can be returned by objects inside union.

	List<Object> list = List.of(Fruit.create(
			"apple",
			"Tomorrow"
			),
			Electronics.create(
					2,
					"laptop",
					1000,
					"APPLE"
			),
			Book.create(
					"fiction",
					"Venkat"
			));

	@QueryMapping
	public Flux<Object> search() {
		return Mono.fromSupplier(() -> new ArrayList<>(list))
				.doOnNext(Collections::shuffle) // create a new list from above in a random order
				.flatMapIterable(Function.identity()) // convert to flux
				.take(ThreadLocalRandom.current().nextInt(0, list.size()));
	}
}
