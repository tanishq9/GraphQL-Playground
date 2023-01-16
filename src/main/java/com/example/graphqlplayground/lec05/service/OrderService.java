package com.example.graphqlplayground.lec05.service;

import com.example.graphqlplayground.lec05.data.CustomerOrderDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OrderService {
	private final Map<String, List<CustomerOrderDto>> map =
			Map.of(
					"sam", List.of(
							new CustomerOrderDto(UUID.randomUUID(), "sam-product-1"),
							new CustomerOrderDto(UUID.randomUUID(), "sam-product-2")
					),
					"jake", List.of(
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-1"),
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-2")
					)
			);

	public Flux<CustomerOrderDto> ordersByCustomerName(String name) {
		return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()))
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(o -> print("Order for: " + name));
	}

	private void print(String msg) {
		System.out.println(LocalDateTime.now() + " : " + msg);
	}


	public Flux<List<CustomerOrderDto>> ordersByCustomerNames(List<String> names) {
		return Flux.fromIterable(names)
				.map(name -> map.getOrDefault(name, Collections.emptyList()));
	}
}
