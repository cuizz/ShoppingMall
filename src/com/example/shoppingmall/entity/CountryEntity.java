package com.example.shoppingmall.entity;

public class CountryEntity {
//{"id":"1348","countyid":"370101","county":"市辖区","fatherid":"370100"},
	String id;
	String countyid;
	String county;
	String fatherid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountyid() {
		return countyid;
	}
	public void setCountyid(String countyid) {
		this.countyid = countyid;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	
}
