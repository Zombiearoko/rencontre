package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Locality;

public interface LocalityRepository extends MongoRepository<Locality, String> {

}
