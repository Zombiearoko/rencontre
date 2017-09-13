package com.bocobi2.rencontre.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.bocobi2.rencontre.model.Region;



public interface RegionRepository extends MongoRepository<Region, String>  {
	public Region findByRegionName(String regionName);
}
