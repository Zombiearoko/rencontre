package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Subscription;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

}
