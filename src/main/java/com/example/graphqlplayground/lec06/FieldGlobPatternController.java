package com.example.graphqlplayground.lec06;

import graphql.scalars.ExtendedScalars;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FieldGlobPatternController {

	@QueryMapping
	// @SchemaMapping(typeName = "Query")
	public void level1(DataFetchingFieldSelectionSet selectionSet) {
		System.out.println(selectionSet.getFields());
		System.out.println(selectionSet.contains("level2")); // true
		System.out.println(selectionSet.contains("level3")); // false
		System.out.println(selectionSet.contains("level2/level3")); // true
		System.out.println(selectionSet.contains("*/level3")); // true
		System.out.println(selectionSet.contains("**/level3")); // true
		System.out.println(selectionSet.contains("level2/*/level5")); // false
		System.out.println(selectionSet.contains("level2/**/level5")); // true
		// return null;
	}
}
