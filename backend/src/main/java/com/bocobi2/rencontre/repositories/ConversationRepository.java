package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Conversation;


public interface ConversationRepository extends MongoRepository<Conversation, String> {

	public List<Conversation> findByStatusConversation(String status);
	
	
}
