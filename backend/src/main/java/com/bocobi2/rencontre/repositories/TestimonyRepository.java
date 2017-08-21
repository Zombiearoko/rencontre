package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Testimony;

public interface TestimonyRepository extends MongoRepository<Testimony, String> {
	public List<Testimony> findByTestimonyType(String testimonyType);
	
}
