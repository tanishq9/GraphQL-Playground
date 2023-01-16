package com.example.graphqlplayground.lec02.controller;

import com.example.graphqlplayground.lec02.data.AgeRangeFilter;
import com.example.graphqlplayground.lec02.data.Customer;
import com.example.graphqlplayground.lec02.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@QueryMapping
	public Flux<Customer> customers() {
		return customerService.allCustomers();
	}

	@QueryMapping
	public Mono<Customer> customerById(@Argument Integer id) {
		return customerService.customerById(id);
	}

	@QueryMapping
	public Flux<Customer> customerByName(@Argument String name) {
		return customerService.nameContains(name);
	}

	@QueryMapping
	public Flux<Customer> customerByAgeRange(@Argument AgeRangeFilter filter) {
		return customerService.ageRangeFilter(filter);
	}
}
