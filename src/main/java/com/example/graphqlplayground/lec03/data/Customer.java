package com.example.graphqlplayground.lec03.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class Customer {
	private Integer id;
	private String name;
	private Integer age;
	private String city;
	// We don't have to always add all fields as per what we have in GraphQL schema, our implementation might be different
	// However, the fields which we have in this POJO should match with what we have in GraphQL schema
}
