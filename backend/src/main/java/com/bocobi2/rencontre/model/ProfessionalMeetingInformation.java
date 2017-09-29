package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;

public class ProfessionalMeetingInformation {
	
	//@Id
	//private String id;
	
	private String firstName;
	private String lastName;
	private String profession;
	private String levelStudy;
	
	
	public ProfessionalMeetingInformation() {
		
	}


	public ProfessionalMeetingInformation(String firstName, String lastName, String profession, String levelStudy) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.profession = profession;
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


	public String getProfession() {
		return profession;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}


	public String getLevelStudy() {
		return levelStudy;
	}


	public void setLevelStudy(String levelStudy) {
		this.levelStudy = levelStudy;
	}
	
	
	
	
	
	

}
