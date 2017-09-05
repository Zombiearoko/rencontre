package com.bocobi2.rencontre.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="conversation")
public class Conversation {

	@Id
	private String idConversation;
	
	@DBRef
	@Indexed
	private List<Message> messages;
	
	@Indexed
	private String statusConversation;
	
	@DBRef
	@Indexed
	private List<String> members;	
	
	public Conversation() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	
	/**
	 * @param idConversation
	 * @param messages
	 * @param statusConversation
	 */
	public Conversation(String idConversation, List<Message> messages, String statusConversation) {
		super();
		this.idConversation = idConversation;
		this.messages = messages;
		this.statusConversation = statusConversation;
	}



	/**
	 * @return the idConversation
	 */
	public String getIdConversation() {
		return idConversation;
	}
	/**
	 * @param idConversation the idConversation to set
	 */
	public void setIdConversation(String idConversation) {
		this.idConversation = idConversation;
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
	 * @return the statusConversation
	 */
	public String getStatusConversation() {
		return statusConversation;
	}
	/**
	 * @param statusConversation the statusConversation to set
	 */
	public void setStatusConversation(String statusConversation) {
		this.statusConversation = statusConversation;
	}



	/**
	 * @return the membres
	 */
	public List<String> getMembres() {
		return members ;
	}



	/**
	 * @param membres the membres to set
	 */
	public void setMembres(List<String> membres) {
		this.members = membres;
	}
	
	//Méthode pour ajouter un méssage à la conversation
	public void addMessage(Message message){
		
	}
	
	//Methode d'ajout d'un membre à la conversation
	public void addMember(String member){
		
	}
	
	public void isMember(String pseudonyme){
		members.contains(pseudonyme);
	}
}
