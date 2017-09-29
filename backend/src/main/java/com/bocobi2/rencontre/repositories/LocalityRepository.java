package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.Locality;
import com.bocobi2.rencontre.model.Town;

public interface LocalityRepository extends MongoRepository<Locality, String> {
	public Locality findByCountry(Country country);
	public Town findByTown(Town town);
	public Department findByDepartment(Department department);
	
	public Locality findByIdLocalite(String idLocalite);
}
