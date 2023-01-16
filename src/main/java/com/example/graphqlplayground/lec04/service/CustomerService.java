package com.example.graphqlplayground.lec04.service;

import com.example.graphqlplayground.lec04.data.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	private final Flux<Customer> flux = Flux.just(
			Customer.create(1, "sam", 20, "la"),
			Customer.create(2, "jake", 21, "lv"),
			Customer.create(3, "mike", 22, "miami"),
			Customer.create(4, "john", 23, "ny")
	);

	public Flux<Customer> allCustomers() {
		return flux;
	}
}
