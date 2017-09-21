package com.bocobi2.rencontre.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="memberBuffer")
public class MemberBuffer {

	@Id
	@Indexed
	private String pseudonym;
	
	private String firstName;
	private String profession;
	private String schoolName;
	private String password;
	
	private String phoneNumber;
	private String birthDate;
	@Indexed
	private String gender;
	private String picture;
	
	private String emailAdress;
	
	private Subscription subscription;
	
	@DBRef
	private List<TypeMeeting> typeMeeting;

	
	@DBRef
	private Profile profile;
	
	@DBRef
	private List<Message> messages;
	
	@DBRef
	private List<Testimony> testimonies;
	
	@DBRef
	private Status status;
	
	
	
	public MemberBuffer() {
		// TODO Auto-generated constructor stub
	}
	
	






	
	


	public MemberBuffer(String pseudonym, String firstName, String profession, String schoolName, String password,
			String phoneNumber, String birthDate, String gender, String picture, String emailAdress,
			Subscription subscription, List<TypeMeeting> typeMeeting, Profile profile, List<Message> messages,
			List<Testimony> testimonies, Status status) {
		super();
		this.pseudonym = pseudonym;
		this.firstName = firstName;
		this.profession = profession;
		this.schoolName = schoolName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.picture = picture;
		this.emailAdress = emailAdress;
		this.subscription = subscription;
		this.typeMeeting = typeMeeting;
		this.profile = profile;
		this.messages = messages;
		this.testimonies = testimonies;
		this.status = status;
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
	
	/**
	 * @return the emailAdress
	 */
	public String getEmailAdress() {
		return emailAdress;
	}




	public List<TypeMeeting> getTypeMeeting() {
		return typeMeeting;
	}




	public void setTypeMeeting(List<TypeMeeting> typeMeeting) {
		this.typeMeeting = typeMeeting;
	}




	/**
	 * @param emailAdress the emailAdress to set
	 */
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getProfession() {
		return profession;
	}




	public void setProfession(String profession) {
		this.profession = profession;
	}




	public String getSchoolName() {
		return schoolName;
	}




	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}




	public int computeAge(){
		return 0;
	}
	

	
	
	
}
