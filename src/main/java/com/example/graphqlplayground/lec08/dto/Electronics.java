package com.example.graphqlplayground.lec08.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class Electronics {

	int id;
	String description;
	int price;
	String brand; // could be enum, made string intentionally
}
