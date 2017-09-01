package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	
	public List<Message> findBySender(String sender);
	public List<Message> findByReceiver(String receiver);
	public List<Message> findByStatusMessage(String status);
	
}
