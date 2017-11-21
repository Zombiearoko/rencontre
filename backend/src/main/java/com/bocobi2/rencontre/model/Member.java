package com.bocobi2.rencontre.model;

import java.util.Collection;
//import java.util.Collection;
import java.util.List;
import java.util.Set;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document(collection="member")
public class Member extends InternetSurfer implements UserDetails  {
 

	@Id
	@Indexed
	private String pseudonym;
	private String password;
	private String registrationDate;
	private String phoneNumber;
	private String numberPicture;
	@Indexed
	private String birthDate;
	@Indexed
	private String age;
	@Indexed
	private String meetingNameConnexion;
	@Indexed
	private String gender;
	private String picture;
	
	@DBRef
	private Set<Role> roles;
	
	private AcademicDatingInformation academicDatingInformation;
	private FriendlyDatingInformation friendlyDatingInformatio;
	private ProfessionalMeetingInformation professionalMeetingInformation;
	private DatingInformation datingInformation;
	
	
	
	
	private Subscription subscription;
	
	@DBRef
	private Profile profile;
	
	//@DBRef
	//private List<TypeMeeting> typeMeeting;


	@DBRef
	private List<Message> messages;
	
	@DBRef
	private List<Conversation> conversations;
	
	@DBRef
	private List<Testimony> testimonies;
	
	@DBRef
	private Status status;
	
	
    
	
	
	public Member() {
		
	}



	


	public Member(String pseudonym, String password, String registrationDate, String phoneNumber, String birthDate,
			String gender, String picture, AcademicDatingInformation academicDatingInformation,
			FriendlyDatingInformation friendlyDatingInformatio,
			ProfessionalMeetingInformation professionalMeetingInformation, DatingInformation datingInformation,
			Subscription subscription, Profile profile, List<TypeMeeting> typeMeeting, List<Message> messages,
			List<Conversation> conversations, List<Testimony> testimonies, Status status, 
			String meetingNameConnexion, String numberPicture , String age,  Set<Role> roles) {
		super();
		this.pseudonym = pseudonym;
		this.password = password;
		this.registrationDate = registrationDate;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.picture = picture;
		this.academicDatingInformation = academicDatingInformation;
		this.friendlyDatingInformatio = friendlyDatingInformatio;
		this.professionalMeetingInformation = professionalMeetingInformation;
		this.datingInformation = datingInformation;
		this.subscription = subscription;
		this.profile = profile;
		//this.typeMeeting = typeMeeting;
		this.messages = messages;
		this.conversations = conversations;
		this.testimonies = testimonies;
		this.status = status;
		this.meetingNameConnexion=meetingNameConnexion;
		this.age=age;
		this.roles=roles;
		this.setNumberPicture(numberPicture);
		
	}






	public String getAge() {
		return age;
	}






	public void setAge(String age) {
		this.age = age;
	}






	public Set<Role> getRoles() {
		return roles;
	}






	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}






	/**
	 * @param pseudonym
	 * @param password
	 * @param phoneNumber
	 * @param birthDate
	 * @param gender
	 * @param picture
	 * @param subscription
	 * @param profile
	 * @param messages
	 * @param testimonies
	 * @param status
	 */
	
	



	//public List<TypeMeeting> getTypeMeeting() {
	//	return typeMeeting;
	//}



	public String getMeetingNameConnexion() {
		return meetingNameConnexion;
	}






	public void setMeetingNameConnexion(String meetingNameConnexion) {
		this.meetingNameConnexion = meetingNameConnexion;
	}






	//public void setTypeMeeting(List<TypeMeeting> list) {
	//	this.typeMeeting = list;
	//}

	

	public List<Conversation> getConversations() {
		return conversations;
	}



	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
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
	
	


	public String getRegistrationDate() {
		return registrationDate;
	}






	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}






	public AcademicDatingInformation getAcademicDatingInformation() {
		return academicDatingInformation;
	}






	public void setAcademicDatingInformation(AcademicDatingInformation academicDatingInformation) {
		this.academicDatingInformation = academicDatingInformation;
	}






	public FriendlyDatingInformation getFriendlyDatingInformatio() {
		return friendlyDatingInformatio;
	}






	public void setFriendlyDatingInformatio(FriendlyDatingInformation friendlyDatingInformatio) {
		this.friendlyDatingInformatio = friendlyDatingInformatio;
	}






	public ProfessionalMeetingInformation getProfessionalMeetingInformation() {
		return professionalMeetingInformation;
	}






	public void setProfessionalMeetingInformation(ProfessionalMeetingInformation professionalMeetingInformation) {
		this.professionalMeetingInformation = professionalMeetingInformation;
	}






	public DatingInformation getDatingInformation() {
		return datingInformation;
	}






	public void setDatingInformation(DatingInformation datingInformation) {
		this.datingInformation = datingInformation;
	}






	public int computeAge(){
		return 0;
	}






	public String getNumberPicture() {
		return numberPicture;
	}






	public void setNumberPicture(String numberPicture) {
		this.numberPicture = numberPicture;
	}












	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}





	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	
	@Override
	public String toString() {
		String messagesList = "";
		for(Message m:messages){
			messagesList += m.toString()+",\n";
		}
		//String testimoniesList = "";
		//for(Testimony t:testimonies){
			//testimoniesList += t.toString()+",\n";
		} 
		return "Member: {\npseudonym : " + pseudonym + ",\n password : " + password + ",\n phoneNumber : " + phoneNumber
				+ ",\n birthDate : " + birthDate + ",\n gender : " + gender + ",\n picture : " + picture + ",\n subscription : "
				+ subscription.toString() + ",\n profile:" + profile.toString() + ",\n messages : [" + messagesList + "],\n testimonies : [" + testimoniesList
				+ "],\n status : " + status.toString() + "\n}";
	}*/

	
/*	public static void main(String args[]){
		// Get the input stream
		MongoClient mongoClient = new MongoClient();
		MongoDatabase myDatabase = mongoClient.getDatabase("mydb");

		// Create a gridFSBucket using the default bucket name "fs"
		GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase);
		try {
		    InputStream streamToUploadFrom = new FileInputStream(new File("/tmp/mongodb-tutorial.pdf"));
		    // Create some custom options
		    GridFSUploadOptions options = new GridFSUploadOptions()
		                                        .chunkSizeBytes(358400)
		                                        .metadata(new org.bson.Document("type", "presentation"));

		    ObjectId fileId = gridFSBucket.uploadFromStream("mongodb-tutorial", streamToUploadFrom, options);
		} catch (FileNotFoundException e){
		   // handle exception
		}

	}*/
}
