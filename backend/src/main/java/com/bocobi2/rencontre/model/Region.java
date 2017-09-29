package com.bocobi2.rencontre.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="region")
public class Region {
	
	@Id
	private String idRegion;
	
	@Indexed
	private String regionName;
	
	@DBRef
	private Country country;

	public Region() {
		
	}

	public Region(String regionName, Country country) {
		this.regionName = regionName;
		this.country = country;
	}



	public String getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	
	
	
	
	

}
