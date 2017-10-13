package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Friendly;

public interface FriendlyRepository extends MongoRepository<Friendly, String> {
	
	public Friendly findByFriendlyStatut(String friendlyStatut);

}
