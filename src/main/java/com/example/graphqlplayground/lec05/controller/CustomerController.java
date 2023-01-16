package com.example.graphqlplayground.lec05.controller;

import com.example.graphqlplayground.lec05.data.Customer;
import com.example.graphqlplayground.lec05.data.CustomerOrderDto;
import com.example.graphqlplayground.lec05.data.CustomerWithOrder;
import com.example.graphqlplayground.lec05.service.CustomerOrderDataFetcher;
import com.example.graphqlplayground.lec05.service.CustomerService;
import com.example.graphqlplayground.lec05.service.OrderService;
import graphql.schema.DataFetchingFieldSelectionSet;
import java.time.LocalDateTime;
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

	@Autowired
	CustomerOrderDataFetcher customerOrderDataFetcher;

	@SchemaMapping(typeName = "Query")
	// this means that there is customers field (in schema) which belongs to type Query, Query is the root object
	public Flux<CustomerWithOrder> customers(DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet) {
		System.out.println("customer: " + dataFetchingFieldSelectionSet.getFields());
		return customerOrderDataFetcher.customerOrders(dataFetchingFieldSelectionSet);
	}

	/*@SchemaMapping(typeName = "Query")
	// this means that there is customers field (in schema) which belongs to type Query, Query is the root object
	public Flux<Customer> customers() {
		System.out.println(" I am here: " + LocalDateTime.now());
		return customerService.allCustomers();
	}

	@SchemaMapping(typeName = "Customer")
	public Flux<CustomerOrderDto> orders(Customer customer) {
		System.out.println(" I am here: " + LocalDateTime.now());
		return orderService.ordersByCustomerName(customer.getName());
	}*/
}
