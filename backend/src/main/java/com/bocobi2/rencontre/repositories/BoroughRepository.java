package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.Department;


public interface BoroughRepository extends MongoRepository<Borough, String> {
	
	public Borough findByBoroughName(String boroughName);
	
	public List<Borough> findByDepartment(Department department);
}
