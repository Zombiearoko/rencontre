package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="concession")
public class Concession {
	
	@Id
	private String idConcession;
	

	@Indexed
	private String concessionName;
	
	@DBRef
	private Town town;

	public Concession() {
	
	}

	public Concession(String concessionName, Town town) {
		
		this.concessionName = concessionName;
		this.town = town;
	}

	public String getIdConcession() {
		return idConcession;
	}

	public void setIdConcession(String idConcession) {
		this.idConcession = idConcession;
	}

	public String getConcessionName() {
		return concessionName;
	}

	public void setConcessionName(String concessionName) {
		this.concessionName = concessionName;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
	
	
	
	
	

}
