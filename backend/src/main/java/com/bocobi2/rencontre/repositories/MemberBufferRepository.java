package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Member;

public interface MemberBufferRepository extends MongoRepository<Member, String> {

}
