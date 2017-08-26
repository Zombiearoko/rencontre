package com.bocobi2.rencontre.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Message")
public class Messages {

	@Id
	private String idMessage;
	
	private String messageContent;
	
	@Indexed
	private String sender;
	
	@Indexed
	private String receiver;
	
	@Indexed
	private Date sendingDate;
	
	public Messages() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param messageContent
	 * @param sender
	 * @param receiver
	 * @param sendingDate
	 */
	public Messages(String messageContent, String sender, String receiver, Date sendingDate) {
		super();
		this.messageContent = messageContent;
		this.sender = sender;
		this.receiver = receiver;
		this.sendingDate = sendingDate;
	}

	/**
	 * @return the messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}
	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the sendingDate
	 */
	public Date getSendingDate() {
		return sendingDate;
	}
	/**
	 * @param sendingDate the sendingDate to set
	 */
	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	/**
	 * @return the idMessage
	 */
	public String getIdMessage() {
		return idMessage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\nidMessage:" + idMessage + ",\n messageContent:" + messageContent + ",\n sender:" + sender
				+ ",\n receiver:" + receiver + ",\n sendingDate:" + sendingDate + "\n}";
	}
	

}
