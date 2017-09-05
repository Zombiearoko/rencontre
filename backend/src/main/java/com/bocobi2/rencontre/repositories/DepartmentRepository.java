package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {

}
