package com.bocobi2.rencontre.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="department")
public class Department {
	
	@Id
	private String idDepartment;
	@Indexed
	private String departmentName;
	
	@DBRef
	private Region region;
	
	public Department() {
		// TODO Auto-generated constructor stub
	}

	

	public Department(String departmentName, Region region) {
		super();
		this.departmentName = departmentName;
		this.region = region;
	}



	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getIdDepartment() {
		return idDepartment;
	}

	public void setIdDepartment(String idDepartment) {
		this.idDepartment = idDepartment;
	}



	public Region getRegion() {
		return region;
	}



	public void setRegion(Region region) {
		this.region = region;
	}

	

}
