package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cryptograph")
public class Cryptograph {

	 @Id
	 public String id;
	 
	 private String pseudonym;
	 private String meetingName;
	public Cryptograph() {
	
	}
	public Cryptograph(String pseudonym, String meetingName) {
		this.pseudonym = pseudonym;
		this.meetingName = meetingName;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPseudonym() {
		return pseudonym;
	}
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
	
	 
	
	 
}
