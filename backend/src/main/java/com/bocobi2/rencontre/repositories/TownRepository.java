package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Town;

public interface TownRepository extends MongoRepository<Town, String> {
	
}
