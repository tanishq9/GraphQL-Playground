package com.example.graphqlplayground.lec05.service;

import com.example.graphqlplayground.lec05.data.CustomerWithOrder;
import graphql.schema.DataFetchingFieldSelectionSet;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerOrderDataFetcher {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	public Flux<CustomerWithOrder> customerOrders(DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet) {
		var includeOrders = dataFetchingFieldSelectionSet.contains("orders");
		System.out.println(includeOrders);
		return customerService
				.allCustomers()
				.map(c -> CustomerWithOrder.create(c.getId(), c.getName(), c.getAge(), c.getCity(), Collections.emptyList()))
				//.flatMap(customerWithOrder -> {
				// to maintain order of merging use flatMapSequential
				.flatMapSequential(customerWithOrder -> {
					if (includeOrders) {
						return orderService
								.ordersByCustomerName(customerWithOrder.getName())
								.collectList()
								.doOnNext(customerOrderDtos -> customerWithOrder.setOrders(customerOrderDtos))
								.thenReturn(customerWithOrder);
					} else {
						return Flux.just(customerWithOrder);
					}
				});
	}

	/*public Flux<CustomerWithOrder> customerOrders(DataFetchingFieldSelectionSet dataFetchingFieldSelectionSet) {
		var includeOrders = dataFetchingFieldSelectionSet.contains("orders");
		System.out.println(includeOrders);
		return customerService
				.allCustomers()
				.map(c -> CustomerWithOrder.create(c.getId(), c.getName(), c.getAge(), c.getCity(), Collections.emptyList()))
				.transform(customerWithOrderFlux -> {
							if (includeOrders) {
								return customerWithOrderFlux
										.flatMap(customerWithOrder -> fetchOrders(customerWithOrder));
							} else {
								return customerWithOrderFlux;
							}
						}
				);
	}*/

	private Mono<CustomerWithOrder> fetchOrders(CustomerWithOrder customerWithOrder) {
		return orderService.ordersByCustomerName(customerWithOrder.getName())
				.collectList()
				.doOnNext(customerOrderDtos -> customerWithOrder.setOrders(customerOrderDtos))
				.thenReturn(customerWithOrder);
	}
}
