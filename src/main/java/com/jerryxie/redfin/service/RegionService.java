package com.jerryxie.redfin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerryxie.redfin.domain.Region;
import com.jerryxie.redfin.repository.RegionRepository;

@Service
public class RegionService {

	@Autowired
	RegionRepository regionRepository;

	public void insertRegion(Region region) {
		this.regionRepository.insert(region);
	}

	public void insertCountyData(String id, String name, String subName) {
		Region region = new Region();
		region.setName(name);
		region.setRegionId(id);
		region.setSubName(subName);
		region.setLastFetchedTime(null);
		insertRegion(region);
	}

	public void saveRegion(Region region) {
		this.regionRepository.save(region);
	}
}
