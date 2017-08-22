package com.bocobi2.rencontre.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="member")
public class Member extends InternetSurfer {

	@Id
	@Indexed
	private String pseudonym;
	private String password;
	
	private String phoneNumber;
	private String birthDate;
	@Indexed
	private String gender;
	private String picture;
	
	private Subscription subscription;
	
	@DBRef
	private Profile profile;
	
	@DBRef
	private List<Message> messages;
	
	@DBRef
	private List<Testimony> testimonies;
	
	@DBRef
	private Status status;
	
	
	
	public Member() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the pseudonym
	 */
	public String getPseudonym() {
		return pseudonym;
	}



	/**
	 * @param pseudonym the pseudonym to set
	 */
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}



	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}



	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}



	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}



	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}



	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}



	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}



	/**
	 * @return the subscription
	 */
	public Subscription getSubscription() {
		return subscription;
	}



	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}



	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}



	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}



	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}



	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}



	/**
	 * @return the testimonies
	 */
	public List<Testimony> getTestimonies() {
		return testimonies;
	}



	/**
	 * @param testimonies the testimonies to set
	 */
	public void setTestimonies(List<Testimony> testimonies) {
		this.testimonies = testimonies;
	}



	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public int computeAge(){
		return 0;
	}
	

}
