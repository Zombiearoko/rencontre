package com.bocobi2.rencontre.repositories;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.Role;



public interface RoleRepository extends MongoRepository<Role, String>{
	
	public Role findByName(String name);
	public Role findByUsers(Set<Member> users);

}
