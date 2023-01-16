package com.example.graphqlplayground.lec05.data;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// The name of class could be different from what we have in the GraphQL schema
public class CustomerOrderDto {

	// But the field name HAS to match with what we have in GraphQL schema
	private UUID id;
	private String description;
}
