package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="locality")
public class Locality {

	@Id
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
		this.setPostalCode(postalCode);
		this.country = country;
		this.town = town;
		this.department = department;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the town
	 */
	public Town getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(Town town) {
		this.town = town;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the idLocalite
	 */
	public String getIdLocalite() {
		return idLocalite;
	}

	
}
