package com.example.graphqlplayground.lec05.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerWithOrder {
	private Integer id;
	private String name;
	private Integer age;
	private String city;
	private List<CustomerOrderDto> orders;
}

