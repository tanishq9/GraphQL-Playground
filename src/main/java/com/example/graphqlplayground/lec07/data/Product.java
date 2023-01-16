package com.example.graphqlplayground.lec07.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {

	private String name;
	private Map<String, String> attributes;
}
