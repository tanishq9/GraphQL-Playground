package com.example.graphqlplayground.lec12.controller;

import com.example.graphqlplayground.lec12.dto.CustomerDto;
import com.example.graphqlplayground.lec12.dto.DeleteResponseDto;
import com.example.graphqlplayground.lec12.exception.ApplicationErrors;
import com.example.graphqlplayground.lec12.service.CustomerService;
import graphql.schema.DataFetchingEnvironment;
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
	public Flux<CustomerDto> customers(DataFetchingEnvironment environment) {
		var callerId = environment.getGraphQlContext().get("caller-id");
		System.out.println("caller-id: " + callerId);
		// We can perform some action on caller-id http request header
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
