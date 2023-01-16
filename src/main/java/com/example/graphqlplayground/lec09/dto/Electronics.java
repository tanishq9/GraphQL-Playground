package com.example.graphqlplayground.lec09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class Electronics {

	int id;
	String description;
	int price;
	String brand;
}
