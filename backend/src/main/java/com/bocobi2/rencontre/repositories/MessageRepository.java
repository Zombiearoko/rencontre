package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

}
