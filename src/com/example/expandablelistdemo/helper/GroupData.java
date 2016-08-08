package com.example.expandablelistdemo.helper;

import java.util.List;
/**
 * 封装这  这个组的名字  跟 这个 组的  子 元素
 * @author sht
 *
 */

public class GroupData {
	
	private String groupName,Goods_id,money;
	
	private boolean groupSelected ,setindClicked;
	
	private List<ChildData> items;
	
	public String getGoods_id() {
		return Goods_id;
	}

	public void setGoods_id(String goods_id) {
		Goods_id = goods_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isGroupSelected() {
		return groupSelected;
	}

	public void setGroupSelected(boolean groupSelected) {
		this.groupSelected = groupSelected;
	}

	public boolean isSetingCkicked() {
		return setindClicked;
	}

	public void setSetingClicked(boolean setindClicked) {
		this.setindClicked = setindClicked;
	}
	public List<ChildData> getItems() {
		return items;
	}

	public void setItems(List<ChildData> items) {
		this.items = items;
	}
	
	
}
