package com.example.graphqlplayground.lec07;

import com.example.graphqlplayground.lec07.data.AllTypesDto;
import com.example.graphqlplayground.lec07.data.Car;
import com.example.graphqlplayground.lec07.data.Product;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class AllScalarTypesController {

	private AllTypesDto allTypesDto = AllTypesDto.create(
			"1",
			170,
			10.12f,
			"LA",
			false,
			120L,
			Byte.valueOf("12"),
			Short.valueOf("100"),
			BigInteger.valueOf(123456789),
			BigDecimal.valueOf(123456789.123),
			LocalDate.now(),
			LocalTime.now(),
			OffsetDateTime.now(),
			Car.BMW
	);

	@QueryMapping
	public Mono<AllTypesDto> getAll() {
		return Mono.just(allTypesDto);
	}

	@QueryMapping
	public Flux<Product> products() {
		return Flux.just(
				Product.create("p1", Map.of(
						"color", "red", "when", "today"
				)),
				Product.create("p2", Map.of(
						"isAvailable", "true", "location", "Delhi"
				))
		);
	}
}
