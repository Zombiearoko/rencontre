package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Administrator;

public interface AdministratorRepository extends MongoRepository<Administrator, String> {
	public Administrator findByLoginAdmin(String loginAdmin);

}
