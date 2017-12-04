package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Role;



public interface RoleRepository extends MongoRepository<Role, String>{
	
	public Role findByName(String name);

}
