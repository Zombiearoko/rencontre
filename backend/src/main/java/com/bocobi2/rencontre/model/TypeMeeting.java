package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;

public class TypeMeeting {
	
	@Id
	private String id;
	
	private String meetingName;

	public TypeMeeting() {}

	public TypeMeeting(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
	
	
	
	

}
