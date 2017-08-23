package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="testimony")
public class Testimony {

	@Id
	private String idTestimony;
	
	@Indexed
	private String testimonyType;
	private String testimonyName;
	private String testimonyContent;


	/**
	 * @param testimonyType
	 * @param testimonyContent
	 */
	public Testimony(String testimonyType, String testimonyContent) {
		super();
		this.testimonyType = testimonyType;
		this.testimonyContent = testimonyContent;
	}

	
	
	public Testimony() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the testimonyType
	 */
	public String getTestimonyType() {
		return testimonyType;
	}



	/**
	 * @param testimonyType the testimonyType to set
	 */
	public void setTestimonyType(String testimonyType) {
		this.testimonyType = testimonyType;
	}



	/**
	 * @return the testimonyContent
	 */
	public String getTestimonyContent() {
		return testimonyContent;
	}



	/**
	 * @param testimonyContent the testimonyContent to set
	 */
	public void setTestimonyContent(String testimonyContent) {
		this.testimonyContent = testimonyContent;
	}



	/**
	 * @return the testimonyName
	 */
	public String getTestimonyName() {
		return testimonyName;
	}



	/**
	 * @param testimonyName the testimonyName to set
	 */
	public void setTestimonyName(String testimonyName) {
		this.testimonyName = testimonyName;
	}



	/**
	 * @return the idTestimony
	 */
	public String getIdTestimony() {
		return idTestimony;
	}



	

}
