package com.bocobi2.rencontre.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {

	@Id
	private String id;
	private String name;
	@DBRef
	private Set<Member> users;
	@DBRef
	private Set<Administrator> admin;

	public Role() {

	}

	public Role(String name, Set<Member> users,  Set<Administrator> admin) {

		this.name = name;
		this.users = users;
		this.admin = admin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Member> getUsers() {
		return users;
	}

	public void setUsers(Set<Member> users) {
		this.users = users;
	}

	public Set<Administrator> getAdmin() {
		return admin;
	}

	public void setAdmin(Set<Administrator> admin) {
		this.admin = admin;
	}
	
	

}
