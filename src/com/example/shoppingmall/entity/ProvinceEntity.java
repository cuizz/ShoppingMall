package com.example.shoppingmall.entity;

public class ProvinceEntity {
//{"id":"1","provinceid":"110000","province":"北京","fatherid":""}
	String id;
	String provinceid;
	String province;
	String fatherid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	
}
