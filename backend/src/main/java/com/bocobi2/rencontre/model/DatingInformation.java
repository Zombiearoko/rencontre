package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;

public class DatingInformation {
	
	//@Id
	//private String id;
	
	private String fatherName;
	private String motherName;
	
	private String fatherProfession;
	private String motherProfession;
	
	public DatingInformation() {
	
	}
	public DatingInformation(String fatherName, String motherName, String fatherProfession, String motherProfession) {
		super();
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.fatherProfession = fatherProfession;
		this.motherProfession = motherProfession;
	}
	
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getFatherProfession() {
		return fatherProfession;
	}
	public void setFatherProfession(String fatherProfession) {
		this.fatherProfession = fatherProfession;
	}
	public String getMotherProfession() {
		return motherProfession;
	}
	public void setMotherProfession(String motherProfession) {
		this.motherProfession = motherProfession;
	}
	
	
	
	
	

}
