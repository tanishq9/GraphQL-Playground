package com.example.graphqlplayground.lec11.controller;

import com.example.graphqlplayground.lec11.exception.ApplicationErrors;
import com.example.graphqlplayground.lec11.dto.CustomerDto;
import com.example.graphqlplayground.lec11.dto.DeleteResponseDto;
import com.example.graphqlplayground.lec11.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@QueryMapping
	public Flux<CustomerDto> customers() {
		return customerService.getCustomers();
	}

	@QueryMapping
	public Mono<CustomerDto> customerById(@Argument int id) {
		// throw new RuntimeException("some issue");
		return customerService
				.getCustomer(id)
				.switchIfEmpty(ApplicationErrors.noSuchCustomer(id));
	}

	// @MutationMapping
	@QueryMapping
	public Mono<CustomerDto> createCustomer(@Argument CustomerDto customer) {
		return customerService
				.createCustomer(customer)
				.switchIfEmpty(ApplicationErrors.ageErrorCustomer(customer));
	}

	@MutationMapping
	public Mono<CustomerDto> updateCustomer(@Argument int id, @Argument CustomerDto customer) {
		return customerService.updateCustomer(id, customer);
	}

	@MutationMapping
	public Mono<DeleteResponseDto> deleteCustomer(@Argument int id) {
		return customerService.deleteCustomer(id);
	}
}
