package com.example.graphqlplayground.lec04.service;

import com.example.graphqlplayground.lec04.data.Customer;
import com.example.graphqlplayground.lec04.data.CustomerOrderDto;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

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
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-2"),
							new CustomerOrderDto(UUID.randomUUID(), "jake-product-3")
					)
			);

	public Flux<CustomerOrderDto> ordersByCustomerName(String name) {
		return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()));
	}

	public Flux<List<CustomerOrderDto>> ordersByCustomerNames(List<String> names) {
		return Flux.fromIterable(names)
				.map(name -> map.getOrDefault(name, Collections.emptyList()));
	}

	public Flux<List<CustomerOrderDto>> ordersByCustomerNamesFromDataSource(List<String> names) {
		//  The size of the promised values MUST be the same size as the key list - this error comes if for n items in batch we return <n items
		return Flux.fromIterable(names)
				// flatMapSequential - Transform the elements emitted by this Flux asynchronously into Publishers, then flatten these inner publishers into a single Flux, but merge them in the order of their source element.
				.flatMapSequential(name -> getOrder(name).defaultIfEmpty(Collections.emptyList()));
	}

	// assume some data source which returns a PUBLISHER (Mono of list of all orders) for given customer using name
	private Mono<List<CustomerOrderDto>> getOrder(String name) {
		return Mono.justOrEmpty(map.get(name));
	}

	public Mono<Map<Customer, List<CustomerOrderDto>>> ordersByCustomerNamesFromDataSourceAsMap(List<Customer> customers) {
		//  The size of the promised values MUST be the same size as the key list - this error comes if for n items in batch we return <n items
		return Flux.fromIterable(customers)
				.map(customer -> Tuples.of(customer, map.getOrDefault(customer.getName(), Collections.emptyList())))
				.collectMap(
						tuple2 -> tuple2.getT1(),
						tuple2 -> tuple2.getT2()
				);
	}
}
