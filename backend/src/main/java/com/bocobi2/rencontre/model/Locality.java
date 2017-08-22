package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Locality {

	private String idLocalite;
	private String postalCode;
	
	@DBRef
	private Country country;
	
	@DBRef
	private Town town;
	
	@DBRef
	private Department department;

	public Locality() {
		
	}

	/**
	 * @param postalCode
	 * @param country
	 * @param town
	 * @param department
	 */
	public Locality(String postalCode, Country country, Town town, Department department) {
		super();
		this.postalCode = postalCode;
		this.country = country;
		this.town = town;
		this.department = department;
	}

	
}
