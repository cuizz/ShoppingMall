package com.example.expandablelistdemo.helper;

import android.graphics.Bitmap;
/**
 * 里面封装着  购物车 商品的所有元素    孩子的实体类
 * @author sht
 *
 */
public class ChildData {
	
	private String childNmu ;
	
	
	private String childName;
	
	
	private String childPrice ;
	
	private String cart_id ;
	

	private Bitmap bitMap   ; 
	
	private String id ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private boolean childSelected ,childSetingClicked;
	
	private String childImage;
	
	public String getChildNum() {
		return childNmu;
	}

	public void setChildNum(String childNum) {
		this.childNmu = childNum;
	}
	
	public String getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}
	
	public String getCartId() {
		return cart_id;
	}

	public void setCartId(String cart_id) {
		this.cart_id = cart_id;
	}
	

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public boolean isChildSelected() {
		return childSelected;
	}

	public void setChildSelected(boolean childSelected) {
		this.childSelected = childSelected;
	}
	public boolean isChildSetingClicked() {
		return childSetingClicked;
	}

	public void setChildSetingClicked(boolean childSetingClicked) {
		this.childSetingClicked = childSetingClicked;
	}

	public String getChildImage() {
		return childImage;
	}

	public void setChildImage(String childImage) {
		this.childImage = childImage;
	}
	public Bitmap getBitMap() {
		return bitMap;
	}

	public void setBitMap(Bitmap bitMap) {
		this.bitMap = bitMap;
	}
	
}