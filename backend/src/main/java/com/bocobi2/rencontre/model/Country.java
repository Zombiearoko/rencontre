package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="country")
public class Country {

	@Id
	private String idCountry;
	

	@Indexed
	private String countryName;
	
	public Country() {
		// TODO Auto-generated constructor stub
	}

}
