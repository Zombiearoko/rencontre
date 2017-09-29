package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;

public class FriendlyDatingInformation {
	
	//@Id
	//private String id;
	
	private String name;
	
	
	

	public FriendlyDatingInformation() {
	
	}




	public FriendlyDatingInformation(String name) {
	
		this.name = name;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
