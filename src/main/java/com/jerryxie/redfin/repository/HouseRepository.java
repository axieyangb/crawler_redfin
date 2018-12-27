package com.jerryxie.redfin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jerryxie.redfin.domain.House;

@Repository
public interface HouseRepository extends MongoRepository<House, String>{

}
