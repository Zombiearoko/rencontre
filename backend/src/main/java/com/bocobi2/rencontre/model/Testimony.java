package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="testimony")
public class Testimony {

	@Id
	private String id;
	
	@Indexed
	private String testimonyType;
	private String testimonyContent;
	private String author;
	
	

	public Testimony(String testimonyType, String testimonyContent, String author) {
		
		this.testimonyType = testimonyType;
		this.testimonyContent = testimonyContent;
		this.author = author;
	}



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



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
