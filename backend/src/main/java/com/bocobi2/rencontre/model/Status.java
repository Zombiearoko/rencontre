package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="status")
public class Status {

	private String id;
	private String statusName;
	
	public Status() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statusName
	 */
	public Status(String statusName) {
		super();
		this.statusName = statusName;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
