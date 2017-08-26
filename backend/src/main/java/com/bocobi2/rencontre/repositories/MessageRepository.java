package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Messages;

public interface MessageRepository extends MongoRepository<Messages, String> {
	
	public List<Messages> findBySender(String sender);
	public List<Messages> findByReceiver(String receiver);
	
}
