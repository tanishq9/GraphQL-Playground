package com.example.graphqlplayground.lec02.service;

import com.example.graphqlplayground.lec02.data.AgeRangeFilter;
import com.example.graphqlplayground.lec02.data.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	public Mono<Customer> customerById(Integer id) {
		return flux
				.filter(customer -> customer.getId().equals(id))
				.next();
	}

	public Flux<Customer> nameContains(String name) {
		return flux.filter(customer -> customer.getName().contains(name));
	}

	public Flux<Customer> ageRangeFilter(AgeRangeFilter filter) {
		return flux.filter(customer -> (customer.getAge() >= filter.getMin() && customer.getAge() <= filter.getMax()));
	}
}
