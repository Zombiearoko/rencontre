package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="internetSurfer")
public class InternetSurfer {

	private String emailAdress;
	private Impression impression;
	public InternetSurfer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the emailAdress
	 */
	public String getEmailAdress() {
		return emailAdress;
	}
	/**
	 * @param emailAdress the emailAdress to set
	 */
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

}
