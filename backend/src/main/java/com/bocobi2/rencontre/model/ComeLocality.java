package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comeLocality")
public class ComeLocality {
	
	@Id
	private String id;

	public ComeLocality() {
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
