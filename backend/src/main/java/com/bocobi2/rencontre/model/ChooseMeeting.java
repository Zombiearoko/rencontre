package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="chooseMeeting")
public class ChooseMeeting {
	
	
	@Id
	private String idChooseMeeting;

	public ChooseMeeting() {
		
	}

	public ChooseMeeting(String idChooseMeeting) {
		
		this.idChooseMeeting = idChooseMeeting;
	}

	public String getIdChooseMeeting() {
		return idChooseMeeting;
	}

	public void setIdChooseMeeting(String idChooseMeeting) {
		this.idChooseMeeting = idChooseMeeting;
	}
	
	
	
	
	

}
