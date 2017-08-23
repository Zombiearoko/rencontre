package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.bocobi2.rencontre.model.MemberBuffer;

public interface MemberBufferRepository extends MongoRepository<MemberBuffer, String> {
	public MemberBuffer findByPseudonym(String pseudonym);
	public List<MemberBuffer> findByEmailAdress(String emailAdress);

	
}
