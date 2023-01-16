package com.example.graphqlplayground.lec05.controller;

import com.example.graphqlplayground.lec05.data.CustomerOrderDto;
import com.example.graphqlplayground.lec05.data.CustomerWithOrder;
import com.example.graphqlplayground.lec05.service.CustomerService;
import com.example.graphqlplayground.lec05.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CustomerRestController {

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderService orderService;

	@GetMapping("customer/orders")
	public Flux<CustomerWithOrder> customerOrders() {
		return this.customerService.allCustomers()
				.flatMap(customer -> this.orderService.ordersByCustomerName(customer.getName())
						.collectList()
						.map(l -> CustomerWithOrder.create(customer.getId(), customer.getName(), customer.getAge(), customer.getCity(), l)));
	}
}
