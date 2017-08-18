package com.bocobi2.rencontre.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;


import com.bocobi2.rencontre.model.Member;

public interface MemberRepository extends MongoRepository<Member, String> {
	
	 public Member findByPseudonym(String pseudonym);
	 public Member findByEmailAdress(String emailAdress);
	 public Member findByPhoneNumber(String phoneNumber);
	    

}
