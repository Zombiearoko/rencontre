package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="internetSurfer")
public class InternetSurfer {

	private String emailAdress;
	private Impression impression;
	public InternetSurfer() {
		
	}
	public String getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	public Impression getImpression() {
		return impression;
	}
	public void setImpression(Impression impression) {
		this.impression = impression;
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
