package com.example.graphqlplayground.lec07.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class AllTypesDto {
	String id;
	int height;
	float temperature;
	String city;
	boolean isValid;

	Long distance;
	Byte ageInYears;
	Short ageInMonths;
	BigInteger bigInteger;
	BigDecimal bigDecimal;
	LocalDate date;
	LocalTime time;
	OffsetDateTime dateTime;
	Car car;
	// ExtendedScalars
}

/*
type AllTypes {
    # default scalar types
    id: ID
    height: Int
    temperature: Float
    city: String
    isValid: Boolean
    # extended scalar types
    distance: Long
    ageInYears: Byte
    ageInMonths: Short
    bigDecimal: BigDecimal
    date: Date
    time: LocalTime
    dateTime: DateTime
    car: Car
}

enum Car {
    BMW
    MERCEDES
}
* */
