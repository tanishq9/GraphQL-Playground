package com.example.graphqlplayground.lec03.controller;

import com.example.graphqlplayground.lec03.data.Customer;
import com.example.graphqlplayground.lec03.data.CustomerOrderDto;
import com.example.graphqlplayground.lec03.service.CustomerService;
import com.example.graphqlplayground.lec03.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderService orderService;

	// @QueryMapping -> Alias for @SchemaMapping
	@SchemaMapping(typeName = "Query")
	// this means that there is customers field (in schema) which belongs to type Query, Query is the root object
	public Flux<Customer> customers() {
		return customerService.allCustomers();
	}

	@SchemaMapping(typeName = "Customer")
	// this means that there is orders field (in schema) which belongs to type Customer, Customer is the root object
	// we can access parent object i.e. Customer in the method parameters
	// this method would be executed only if orders information is requested for the Customer
	public Flux<CustomerOrderDto> orders(Customer customer, @Argument int limit) {
		System.out.println("orders method invoked for: " + customer.getName());
		return this.orderService.ordersByCustomerName(customer.getName()).take(limit);
	}
}
