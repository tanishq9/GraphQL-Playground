package com.example.graphqlplayground.lec04.controller;

import com.example.graphqlplayground.lec04.data.Customer;
import com.example.graphqlplayground.lec04.data.CustomerOrderDto;
import com.example.graphqlplayground.lec04.service.CustomerService;
import com.example.graphqlplayground.lec04.service.OrderService;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Flux<Customer> customers(DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet) {
		System.out.println("customer: " + dataFetchingFieldSelectionSet.getFields());
		return customerService.allCustomers();
	}

/*	@BatchMapping(typeName = "Customer")
	// @SchemaMapping(typeName = "Customer")
	// this method would be executed for all customers at once if orders information is requested for Customers
	public Flux<List<CustomerOrderDto>> orders(List<Customer> customers) {
		System.out.println("orders method invoked for: " + customers);
		return orderService.ordersByCustomerNames(
				customers.stream().map(Customer::getName).collect(Collectors.toList())
		);
	}*/

	/*@BatchMapping(typeName = "Customer")
	// @SchemaMapping(typeName = "Customer")
	// this method would be executed for all customers at once if orders information is requested for Customers
	public Flux<List<CustomerOrderDto>> orders(List<Customer> customers) {
		System.out.println("orders method invoked for: " + customers);
		return orderService.ordersByCustomerNamesFromDataSource(
				customers.stream().map(Customer::getName).collect(Collectors.toList())
		);
	}*/

	/*@BatchMapping(typeName = "Customer")
	// @SchemaMapping(typeName = "Customer")
	// this method would be executed for all customers at once if orders information is requested for Customers
	public Mono<Map<Customer, List<CustomerOrderDto>>> orders(List<Customer> customers) {
		System.out.println("orders method invoked for: " + customers);
		// System.out.println("order: " + dataFetchingFieldSelectionSet.getFields());
		return orderService.ordersByCustomerNamesFromDataSourceAsMap(customers);
	}*/

	/*@SchemaMapping(typeName = "Customer")
	public Flux<CustomerOrderDto> orders(Customer customer, DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet) {
		// System.out.println("orders method invoked for: " + customer);
		System.out.println("order: " + dataFetchingFieldSelectionSet.getFields());
		return orderService.ordersByCustomerName(
				customer.getName()
		);
	}*/

	@SchemaMapping(typeName = "Customer")
	public Flux<CustomerOrderDto> orders(Customer customer, DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet, DataFetchingEnvironment dataFetchingEnvironment) {
		// System.out.println("orders method invoked for: " + customer);
		System.out.println("order: " + dataFetchingFieldSelectionSet.getFields());
		System.out.println("operationName: " + dataFetchingEnvironment.getOperationDefinition());
		return orderService.ordersByCustomerName(
				customer.getName()
		);
	}

	// @SchemaMapping(typeName = "Customer") // --> this annotation is mandatory for age overriding to kick in for the Customer
	// overriding age field value
	public int age(Customer customer) {
		if (customer.getAge() > 21) {
			return 100;
		} else {
			return 0;
		}
	}
}
