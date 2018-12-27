package com.jerryxie.redfin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jerryxie.redfin.domain.Region;

@Repository
public interface RegionRepository extends MongoRepository<Region, String> {
	public Region findByRegionId(String regionId);
}
