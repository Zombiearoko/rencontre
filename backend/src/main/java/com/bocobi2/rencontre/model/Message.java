package com.bocobi2.rencontre.model;

import java.time.OffsetDateTime;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Message")
public class Message {

	@Id
	private String idMessage;
	
	private String messageContent;
	
	@Indexed
	private String sender;
	
	@Indexed
	private String receiver;
	
	@Indexed
	private String StatusMessage = "Non lu";
	
	@Indexed
	private DateTime sendingDate;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param messageContent
	 * @param sender
	 * @param receiver
	 * @param sendingDate
	 */
	public Message(String messageContent, String sender, String receiver, DateTime sendingDate) {
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
	public DateTime getSendingDate() {
		return sendingDate;
	}
	/**
	 * @param sendingDate the sendingDate to set
	 */
	public void setSendingDate(DateTime sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	/**
	 * @return the idMessage
	 */
	public String getIdMessage() {
		return idMessage;
	}

	/**
	 * @return the messageStatus
	 */
	public String getStatusMessage() {
		return StatusMessage;
	}

	/**
	 * @param messageStatus the messageStatus to set
	 */
	public void setStatusMessage(String messageStatus) {
		this.StatusMessage = messageStatus;
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
