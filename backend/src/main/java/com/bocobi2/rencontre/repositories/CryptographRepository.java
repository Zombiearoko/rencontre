package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.bocobi2.rencontre.model.Cryptograph;

public interface CryptographRepository extends MongoRepository<Cryptograph, String>{
	

	public Cryptograph findById(String id);
}
