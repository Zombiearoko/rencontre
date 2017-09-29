package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;

public class AcademicDatingInformation {
	
	
	
	//@Id 
	//private String id;
	
	private String firstName;
	private String lastName;
	private String schoolName;
	private String levelStudy;
	
	
	public AcademicDatingInformation() {
		
	}


	public AcademicDatingInformation(String firstName, String lastName, String schoolName, String levelStudy) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolName = schoolName;
		this.levelStudy = levelStudy;
	}


	


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	public String getLevelStudy() {
		return levelStudy;
	}


	public void setLevelStudy(String levelStudy) {
		this.levelStudy = levelStudy;
	}
	
	
	
	
	
	

}
