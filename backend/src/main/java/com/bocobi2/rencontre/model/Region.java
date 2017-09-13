package com.bocobi2.rencontre.model;

import java.util.List;

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
	private List<Department> department;

	public Region() {
		super();
		
	}

	public Region(String regionName, List<Department> department) {
		super();
		this.regionName = regionName;
		this.department = department;
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

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}
	
	
	
	

}
