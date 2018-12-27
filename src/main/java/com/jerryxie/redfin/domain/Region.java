package com.jerryxie.redfin.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Region")
public class Region {
	@Id
	@Field("region_id")
	private String regionId;
	private String name;

	public Date getLastFetchedTime() {
		return lastFetchedTime;
	}

	public void setLastFetchedTime(Date lastFetchedTime) {
		this.lastFetchedTime = lastFetchedTime;
	}

	private Date lastFetchedTime;

	@Field("sub_name")
	private String subName;

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
