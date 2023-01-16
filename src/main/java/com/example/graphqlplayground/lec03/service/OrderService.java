package com.example.graphqlplayground.lec03.service;

import com.example.graphqlplayground.lec03.data.CustomerOrderDto;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OrderService {
	private Map<String, List<CustomerOrderDto>> map =
			Map.of(
					"sam", List.of(
							new CustomerOrderDto(UUID.randomUUID(), "sam-product-1"),
							new CustomerOrderDto(UUID.randomUUID(), "sam-product-2")
					),
					"jake", List.of(
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-1"),
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-2"),
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-3")
					)
			);

	public Flux<CustomerOrderDto> ordersByCustomerName(String name) {
		return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()));
	}
}
