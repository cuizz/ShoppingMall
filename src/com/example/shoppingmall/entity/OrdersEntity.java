package com.example.shoppingmall.entity;

import java.util.List;

public class OrdersEntity {
//{"id":"1","user_id":"45","shop_id":"1","total_price":"2100.00","freight":"2.00","state":"send","pay_type":"wxpay",
	//"pay_state":"payed","consignee":"小李","phone":"132223334554","province":"山东","city":"济南","county":"槐荫区",
	//"address":"和谐广场","zip_code":"666666","remarks":"哈哈","create_date":"2016-07-28 20:50:30","is_comment":"y",
	//"username":"杨阳洋","shap_name":"海澜之家","state_name":"已发货","payState":"已支付","payType":"微信","lists":
	String id;
	String user_id;
	String shop_id;
	String total_price;
	String freight;
	String state;
	String pay_type;
	String pay_state;
	String consignee;
	String phone;
	String province;
	String city;
	String county;
	String address;
	String zip_code;
	String remarks;
	String create_date;
	String is_comment;
	String username;
	String shap_name;
	String state_name;
	String payState;
	String payType;
	List<OrdersEntity> lists;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_state() {
		return pay_state;
	}
	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(String is_comment) {
		this.is_comment = is_comment;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getShap_name() {
		return shap_name;
	}
	public void setShap_name(String shap_name) {
		this.shap_name = shap_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public List<OrdersEntity> getLists() {
		return lists;
	}
	public void setLists(List<OrdersEntity> lists) {
		this.lists = lists;
	}
	
}
