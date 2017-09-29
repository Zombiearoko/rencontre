package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="town")
public class Town {

	@Id
	private String idTown;
	

	@Indexed
	private String townName;
	
	@DBRef
	private Borough borough;
	
	public Town() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param townName
	 */
	

	/**
	 * @return the townName
	 */
	public String getTownName() {
		return townName;
	}

	public Town(String townName, Borough borough) {
		super();
		this.townName = townName;
		this.borough = borough;
	}

	/**
	 * @param townName the townName to set
	 */
	public void setTownName(String townName) {
		this.townName = townName;
	}

	/**
	 * @return the idTown
	 */
	public String getIdTown() {
		return idTown;
	}

	public Borough getBorough() {
		return borough;
	}

	public void setBorough(Borough borough) {
		this.borough = borough;
	}

	public void setIdTown(String idTown) {
		this.idTown = idTown;
	}
	
	
}
