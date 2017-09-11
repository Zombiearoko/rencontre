package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Impression;

public interface ImpressionRepository extends MongoRepository<Impression, String> {

}
