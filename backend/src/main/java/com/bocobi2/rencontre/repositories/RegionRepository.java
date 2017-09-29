package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.Region;



public interface RegionRepository extends MongoRepository<Region, String>  {
	
	public Region findByRegionName(String regionName);
	
	public List<Region> findByCountry(Country country);
}
