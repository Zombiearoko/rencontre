package com.bocobi2.rencontre.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Member;

public interface MemberRepository extends MongoRepository<Member, String> {

	public Member findByPseudonym(String pseudonym);
	public List<Member> findByEmailAdress(String emailAdress);
	public List<Member> findByGenderAndBirthDateBetween(String gender, String from, String to);
	
	
	
	public List<Member> findByGender(String gender);
	
	
}
