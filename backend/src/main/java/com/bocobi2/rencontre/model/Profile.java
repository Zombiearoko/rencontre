package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="profile")
public class Profile {

	@DBRef
	private Locality locality;
	
	public Profile() {
		// TODO Auto-generated constructor stub
	}

}
