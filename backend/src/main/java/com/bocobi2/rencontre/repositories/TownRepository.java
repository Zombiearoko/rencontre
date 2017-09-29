package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.Town;

public interface TownRepository extends MongoRepository<Town, String> {
	
	public Town findByTownName(String townName);
	
	public List<Town> findByBorough(Borough borough);
	
}
