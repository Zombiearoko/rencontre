package com.bocobi2.rencontre.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="administrator")
public class Administrator {

	@Id
	private String loginAdmin;

	private String passwordAdmin;
	
	private String passwordSec;
	
	@DBRef
	private Set<Role> roles;
	
	public Administrator() {
		// TODO Auto-generated constructor stub
	}

	

	public Administrator(String loginAdmin, String passwordAdmin, String passwordSec, Set<Role> roles) {
		super();
		this.loginAdmin = loginAdmin;
		this.passwordAdmin = passwordAdmin;
		this.passwordSec = passwordSec;
		this.roles = roles;
	}



	public String getPasswordSec() {
		return passwordSec;
	}



	public void setPasswordSec(String passwordSec) {
		this.passwordSec = passwordSec;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
