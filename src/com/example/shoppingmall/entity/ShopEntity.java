package com.example.shoppingmall.entity;

import java.util.List;

import com.example.zhuye.ProductBean;

public class ShopEntity {
	//{"id":"3","name":"安踏专卖","tell":"13456789023","province_id":"370000","province":"山东","city_id":"370100","city":"济南",
	//"county_id":"370104","country":"槐荫","business_hours":"08:00-21:00","reg_date":"0000-00-00","img":"","level":"4"}
	private String id;
	private String name;
	private String tell;
	private String province_id;
	private String province;
	private String city_id;
	private String city;
	private String county_id;
	private String country;
	private String business_hours;
	private String reg_date;
	private String img;
	private String address; 
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private List<ProductBean> list;
	
	public List<ProductBean> getList() {
		return list;
	}
	public void setList(List<ProductBean> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBusiness_hours() {
		return business_hours;
	}
	public void setBusiness_hours(String business_hours) {
		this.business_hours = business_hours;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
