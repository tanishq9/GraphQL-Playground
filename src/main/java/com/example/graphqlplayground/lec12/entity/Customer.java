package com.example.graphqlplayground.lec12.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table("customers") // maps to customers table in customerdb, by default it would map to customer table
public class Customer {
	@Id // to denote primary key, required to add for using any jpa repository
	Integer id;
	String name;
	int age;
	String city;
}
