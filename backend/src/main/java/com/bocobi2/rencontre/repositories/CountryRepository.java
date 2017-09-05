package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Country;

public interface CountryRepository extends MongoRepository<Country, String> {

}
