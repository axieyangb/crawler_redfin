package com.jerryxie.redfin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jerryxie.redfin.domain.Home;

@Repository
public interface HomeRepository extends MongoRepository<Home, String>{


}
