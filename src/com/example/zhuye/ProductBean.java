package com.example.zhuye;

//{"id":"6","goods_id":"11","shop_id":"1","attr1_id":"4","attr2_id":"2","attr3_id":"0","price":"100.00","in
//
//ventory":"200","create_date":"2016-07-30 11:08:56","discount":"0","groom":"n","sale_num":"0","title":"智能
//
//手机","img":"14696801086828.jpg","attr2_name":"电信3G(CDMA2000)","attr2":"2","attr1_name":"苹
//
//果","attr1":"1"}


public class ProductBean {
	
	private String goodsName;
	private int goodsImage;
	private String price;
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(int goodsImage) {
		this.goodsImage = goodsImage;
	}
}
