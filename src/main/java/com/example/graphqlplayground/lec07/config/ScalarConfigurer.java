package com.example.graphqlplayground.lec07.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ScalarConfigurer {
	// Configuring/Registering extended scalars so as to understand GraphQL queries containing those
	@Bean
	public RuntimeWiringConfigurer configurer(){
		return c -> c
				.scalar(ExtendedScalars.GraphQLLong)
				.scalar(ExtendedScalars.GraphQLByte)
				.scalar(ExtendedScalars.GraphQLShort)
				.scalar(ExtendedScalars.GraphQLBigInteger)
				.scalar(ExtendedScalars.GraphQLBigDecimal)
				.scalar(ExtendedScalars.Date)
				.scalar(ExtendedScalars.LocalTime)
				.scalar(ExtendedScalars.DateTime)
				.scalar(ExtendedScalars.Object);
	}
}
