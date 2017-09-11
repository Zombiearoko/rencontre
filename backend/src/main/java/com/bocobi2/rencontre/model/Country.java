package com.bocobi2.rencontre.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="country")
public class Country {

	@Id
	private String idCountry;
	

	@Indexed
	private String countryName;
	
	@DBRef
	private List<Region> region;
	
	

	public Country() {
		// TODO Auto-generated constructor stub
	}

	

	public Country(String countryName, List<Region> region) {
		super();
		this.countryName = countryName;
		this.region = region;
	}



	public String getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}



	public List<Region> getRegion() {
		return region;
	}



	public void setRegion(List<Region> region) {
		this.region = region;
	}

	
	
	
	

}
