package com.example.shoppingmall.entity;

public class CityEntity {
//{"id":"1","cityid":"110100","city":"东城区","fatherid":"110000"}
	String id;
	String cityid;
	String city;
	String fatherid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	
}
