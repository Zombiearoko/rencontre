package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Contrat;

public interface ContractRepository extends MongoRepository<Contrat, String> {

}
