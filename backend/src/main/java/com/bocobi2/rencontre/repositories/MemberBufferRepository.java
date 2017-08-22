package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Member;

public interface MemberBufferRepository extends MongoRepository<Member, String> {
	public Member findByPseudonym(String pseudonym);
	public List<Member> findByEmailAdress(String emailAdress);

	
}
