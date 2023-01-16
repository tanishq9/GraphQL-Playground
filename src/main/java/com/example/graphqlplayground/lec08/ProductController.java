package com.example.graphqlplayground.lec08;

import com.example.graphqlplayground.lec08.dto.Book;
import com.example.graphqlplayground.lec08.dto.Electronics;
import com.example.graphqlplayground.lec08.dto.FruitDto;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@QueryMapping
	public Flux<Object> products() {
		return Flux.just(
				FruitDto.create(
						1,
						"apple",
						100,
						"tomorrow"
				),
				Electronics.create(
						2,
						"laptop",
						1000,
						"APPLE"
				),
				Book.create(
						3,
						"fiction",
						200,
						"Venkat"
				)
		);
	}
}
