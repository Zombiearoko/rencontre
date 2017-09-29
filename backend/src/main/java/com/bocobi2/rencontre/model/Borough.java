package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="borough")
public class Borough {
	
	@Id
	private String idBorough;
	@Indexed
	private String boroughName;
	
	@DBRef
	private Department department;

	public Borough() {
		
	}

	public Borough(String boroughName, Department department) {
		this.boroughName = boroughName;
		this.department = department;
	}

	public String getIdBorough() {
		return idBorough;
	}

	public void setIdBorough(String idBorough) {
		this.idBorough = idBorough;
	}

	public String getBoroughName() {
		return boroughName;
	}

	public void setBoroughName(String boroughName) {
		this.boroughName = boroughName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
	
	

}
