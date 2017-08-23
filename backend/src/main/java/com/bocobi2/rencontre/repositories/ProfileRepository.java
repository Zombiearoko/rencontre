package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {

	
}
