package com.example.graphqlplayground.lec12.util;

import com.example.graphqlplayground.lec12.dto.CustomerDto;
import com.example.graphqlplayground.lec12.entity.Customer;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

	public static Customer toEntity(CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}

	public static Customer toEntity(int id, CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		customer.setId(id);
		return customer;
	}

	public static CustomerDto toDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}
}
