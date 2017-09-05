package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Administrator;

public interface AdministratorRepositoy extends MongoRepository<Administrator, String> {
	public Administrator findByAdminLogin(String adminLogin);

}
