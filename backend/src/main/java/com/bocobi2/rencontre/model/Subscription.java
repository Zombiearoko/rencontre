package com.bocobi2.rencontre.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Subscription {

	@Id
	private String idSubscription;
	private String paymentMethod;
	private String paymentMethodName;
	
	@Indexed
	private Date subscriptionDate;
	
	@DBRef
	private Contrat contrat;
	
	public Subscription() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param paymentMethod
	 * @param paymentMethodName
	 * @param subscriptionDate
	 * @param contrat
	 */
	public Subscription(String paymentMethod, String paymentMethodName, Date subscriptionDate, Contrat contrat) {
		super();
		this.paymentMethod = paymentMethod;
		this.paymentMethodName = paymentMethodName;
		this.subscriptionDate = subscriptionDate;
		this.contrat = contrat;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the paymentMethodName
	 */
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * @param paymentMethodName the paymentMethodName to set
	 */
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * @return the subscriptionDate
	 */
	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	/**
	 * @param subscriptionDate the subscriptionDate to set
	 */
	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	/**
	 * @return the contrat
	 */
	public Contrat getContrat() {
		return contrat;
	}

	/**
	 * @param contrat the contrat to set
	 */
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	/**
	 * @return the idSubscription
	 */
	public String getIdSubscription() {
		return idSubscription;
	}

	/**
	 * @param idSubscription the idSubscription to set
	 */
	public void setIdSubscription(String idSubscription) {
		this.idSubscription = idSubscription;
	}
 
}
