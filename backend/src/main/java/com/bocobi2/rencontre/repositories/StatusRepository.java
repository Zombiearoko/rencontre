package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Status;

public interface StatusRepository extends MongoRepository<Status, String> {
	public Status findByStatusName(String statusName);

}
