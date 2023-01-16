package com.example.graphqlplayground.lec08.config;

import com.example.graphqlplayground.lec08.dto.FruitDto;
import graphql.schema.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class TypeResolverConfig {

	@Bean
	public RuntimeWiringConfigurer configurer(TypeResolver typeResolver) {
		// Type name, adding resolver in builder
		return c -> c.type("Product", builder -> builder.typeResolver(typeResolver));
	}

	@Bean
	public TypeResolver typeResolver() {
		ClassNameTypeResolver classNameTypeResolver = new ClassNameTypeResolver();
		// mapping custom class to GraphQL type name
		classNameTypeResolver.addMapping(FruitDto.class, "Fruit");
		return classNameTypeResolver;
	}
}
