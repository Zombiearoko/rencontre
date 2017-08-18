package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Member")
public class Member {
	@Id
	private String idMember;
	private String pseudonym;
	private String emailAdress;
	private String phoneNumber;
	
	public Member() {}

	public Member(String idMember, String pseudonym, String emailAdress, String phoneNumber) {
		this.idMember = idMember;
		this.pseudonym = pseudonym;
		this.emailAdress = emailAdress;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return String.format(
                "Member[idMember=%s, pseudonym='%s', emailAdress='%s' , phoneNumber='%s']",
                idMember, pseudonym, emailAdress, phoneNumber );
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
}
