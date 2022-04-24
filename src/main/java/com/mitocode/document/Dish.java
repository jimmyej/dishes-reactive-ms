package com.mitocode.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(value = "dishes")
public class Dish {
	@Id
	private String id;
	private String name;
	private double price;
	private boolean status;
}
