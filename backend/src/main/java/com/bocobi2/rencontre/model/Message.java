package com.bocobi2.rencontre.model;

import java.util.Date;

public class Message {

	private String idMessage;
	private String messageContent;
	private String sender;
	private String receiver;
	private Date sendingDate;
	
	public Message() {
		// TODO Auto-generated constructor stub
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
	

}
