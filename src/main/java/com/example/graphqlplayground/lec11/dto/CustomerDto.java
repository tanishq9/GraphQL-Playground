package com.example.graphqlplayground.lec11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerDto {

	Integer id;
	String name;
	int age;
	String city;
}
