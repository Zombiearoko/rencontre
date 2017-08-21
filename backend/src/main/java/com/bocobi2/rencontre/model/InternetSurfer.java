package com.bocobi2.rencontre.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="internetSurfer")
public class InternetSurfer {

	private String emailAdress;
	private Impression impression;
	public InternetSurfer() {
		// TODO Auto-generated constructor stub
	}

}
