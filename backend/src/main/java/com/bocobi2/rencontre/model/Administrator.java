package com.bocobi2.rencontre.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="administrator")
public class Administrator {

	@Id
	private String loginAdmin;

	private String passwordAdmin;
	
	public Administrator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param loginAdmin
	 * @param passwordAdmin
	 */
	public Administrator(String loginAdmin, String passwordAdmin) {
		super();
		this.loginAdmin = loginAdmin;
		this.passwordAdmin = passwordAdmin;
	}

	/**
	 * @return the loginAdmin
	 */
	public String getLoginAdmin() {
		return loginAdmin;
	}

	/**
	 * @param loginAdmin the loginAdmin to set
	 */
	public void setLoginAdmin(String loginAdmin) {
		this.loginAdmin = loginAdmin;
	}

	/**
	 * @return the passwordAdmin
	 */
	public String getPasswordAdmin() {
		return passwordAdmin;
	}

	/**
	 * @param passwordAdmin the passwordAdmin to set
	 */
	public void setPasswordAdmin(String passwordAdmin) {
		this.passwordAdmin = passwordAdmin;
	}

	
	
}
