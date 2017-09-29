package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.Region;

public interface DepartmentRepository extends MongoRepository<Department, String> {
		
	public Department findByDepartmentName(String departmentName);
	
	public List<Department> findByRegion(Region region);
		
}
