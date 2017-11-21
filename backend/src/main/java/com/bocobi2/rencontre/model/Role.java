package com.bocobi2.rencontre.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="role")
public class Role {
	
	@Id
	private String id;
    private String name;
    private Set<Member> users;
    
	public Role() {

	}

	public Role(String name, Set<Member> users) {

		this.name = name;
		this.users = users;
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
    
    

}
