package com.bocobi2.rencontre.model;



import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="locality")
public class Locality {

	@Id
	private String idLocalite;
	private String postalCode;
	
	
	private Country country;
	
	
	private Town town;
	

	private Department department;
	
	private Region region;
	
	private Borough borough;
	
	private Concession concession;

	public Locality() {
		
	}
	
	
	public Locality(String postalCode, Country country, Town town, Department department, Region region,
			Borough borough, Concession concession) {

		this.postalCode = postalCode;
		this.country = country;
		this.town = town;
		this.department = department;
		this.region = region;
		this.borough = borough;
		this.concession = concession;
	}


	/**
	 * @param postalCode
	 * @param country
	 * @param town
	 * @param department
	 */
	

	public Region getRegion() {
		return region;
	}


	public void setRegion(Region region) {
		this.region = region;
	}


	public Borough getBorough() {
		return borough;
	}


	public void setBorough(Borough borough) {
		this.borough = borough;
	}


	public Concession getConcession() {
		return concession;
	}


	public void setConcession(Concession concession) {
		this.concession = concession;
	}


	public void setIdLocalite(String idLocalite) {
		this.idLocalite = idLocalite;
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
	

	/**
	 * @return the idLocalite
	 */
	public String getIdLocalite() {
		return idLocalite;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
}
