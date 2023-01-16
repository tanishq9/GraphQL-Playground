package com.example.graphqlplayground.lec12.service;

import com.example.graphqlplayground.lec12.dto.CustomerDto;
import com.example.graphqlplayground.lec12.dto.DeleteResponseDto;
import com.example.graphqlplayground.lec12.dto.Status;
import com.example.graphqlplayground.lec12.repository.CustomerRepository;
import com.example.graphqlplayground.lec12.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public Flux<CustomerDto> getCustomers() {
		return customerRepository
				.findAll()
				.doOnNext(customer -> System.out.println(customer))
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> getCustomer(int id) {
		return customerRepository
				.findById(id)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
		return Mono.just(customerDto)
				.filter(customerDtoInput -> customerDtoInput.getAge() >= 18)
				.map(EntityDtoUtil::toEntity)
				.flatMap(customerRepository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> updateCustomer(int id, CustomerDto customerDtoMono) {
		return customerRepository
				.findById(id)
				.map(customer -> EntityDtoUtil.toEntity(id, customerDtoMono))
				.flatMap(customerRepository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<DeleteResponseDto> deleteCustomer(int id) {
		return customerRepository
				.deleteById(id)
				.thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
				.onErrorReturn(DeleteResponseDto.create(1, Status.FAILURE));
	}
}
