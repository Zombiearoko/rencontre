package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="friendly")
public class Friendly {
	
	@Id
	private String idFriendly;
	
	@Indexed
	private String friendlyStatut;

	public Friendly() {	}

	public Friendly(String friendlyStatut) {
		this.friendlyStatut = friendlyStatut;
	}

	public String getIdFriendly() {
		return idFriendly;
	}

	public void setIdFriendly(String idFriendly) {
		this.idFriendly = idFriendly;
	}

	public String getFriendlyStatut() {
		return friendlyStatut;
	}

	public void setFriendlyStatut(String friendlyStatut) {
		this.friendlyStatut = friendlyStatut;
	}
	
	
	
	
	
	
	

}
