package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="contrat")
public class Contract {

	@Id
	private String idContrat;
	private Double price;
	private int contratDuration;
	private String contratName;
	
	public Contract() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param price
	 * @param contratDuration
	 * @param contratName
	 */
	public Contract(Double price, int contratDuration, String contratName) {
		super();
		this.price = price;
		this.contratDuration = contratDuration;
		this.contratName = contratName;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the contratDuration
	 */
	public int getContratDuration() {
		return contratDuration;
	}

	/**
	 * @param contratDuration the contratDuration to set
	 */
	public void setContratDuration(int contratDuration) {
		this.contratDuration = contratDuration;
	}

	/**
	 * @return the contratName
	 */
	public String getContratName() {
		return contratName;
	}

	/**
	 * @param contratName the contratName to set
	 */
	public void setContratName(String contratName) {
		this.contratName = contratName;
	}

	/**
	 * @return the idContrat
	 */
	public String getIdContrat() {
		return idContrat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\nidContrat:" + idContrat + ",\n price:" + price + ",\n contratDuration:" + contratDuration
				+ ",\n contratName:" + contratName + "\n}";
	}
	
}
