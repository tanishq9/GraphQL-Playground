package com.example.graphqlplayground.lec09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class Fruit {

	String description;
	String expiryDate;
}
