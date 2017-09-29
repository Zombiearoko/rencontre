package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.bocobi2.rencontre.model.ChooseMeeting;

public interface ChooseMeetingRepository extends MongoRepository<ChooseMeeting, String> {
	
	public ChooseMeeting findByIdChooseMeeting(String idChooseMeeting);

}
