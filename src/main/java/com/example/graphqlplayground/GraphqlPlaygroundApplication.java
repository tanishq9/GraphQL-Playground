package com.example.graphqlplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.example.graphqlplayground.lec12") // explicitly mentioning package due to same beans present across packages
@EnableR2dbcRepositories(basePackages = "com.example.graphqlplayground.lec12")
public class GraphqlPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlPlaygroundApplication.class, args);
	}
}
