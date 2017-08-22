package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="town")
public class Town {

	@Id
	private String idTown;
	

	@Indexed
	private String townName;
	
	public Town() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param townName
	 */
	public Town(String townName) {
		super();
		this.townName = townName;
	}

	/**
	 * @return the townName
	 */
	public String getTownName() {
		return townName;
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
}
