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
	private List<Town> town;
	
	public Department() {
		// TODO Auto-generated constructor stub
	}

	public Department( String departmentName, List<Town> town) {
		
		this.departmentName = departmentName;
		this.town = town;
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

	public List<Town> getTown() {
		return town;
	}

	public void setTown(List<Town> town) {
		this.town = town;
	}

}
