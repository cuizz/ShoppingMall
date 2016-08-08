package com.example.expandablelistdemo.helper;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
	/**
	 * 组的集合
	 */
	private volatile List<GroupData> contentData;
	
	//这个地方我要拿到    优惠券数据的集合    这个集合 应该 也是在子 里面 
	
	/**
	 * 得到 这个集合的方法
	 * 
	 * @return
	 */
	public List<GroupData> getContentData() {
		return contentData;
	}

	/**
	 * 设置 这个集合的方法
	 * 
	 * @param contentData
	 */
	public void setContentData(List<GroupData> contentData) {
		this.contentData = contentData;
	}

	/**
	 * 这个地方需要传 一个 组的 position 传进来的 时候 调用这个方法 就能 得到 group 的 集合
	 * 得到 这个   组的  对象
	 * @param groupPosition
	 * @return
	 */
	public GroupData getGroupData(int groupPosition) {
		if (contentData == null) {
			return null;
		}
		return contentData.get(groupPosition);
	}

	/**
	 * 组的长度的方法
	 * 
	 * @return
	 */
	public int getGroupCount() {
		if (contentData == null) {
			return 0;
		}
		return contentData.size();
	}

	/**
	 * 得到子的数据的方法
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 */
	public ChildData getChildData(int groupPosition, int childPosition) {

		List<ChildData> childDataList = getChildDataList(groupPosition);
		if (childDataList == null) {
			return null;
		}
		return childDataList.get(childPosition);
	}

	/**
	 * 删除子数据的方法
	 * 
	 * @param groupPosition
	 * @param childPosition
	 */
	public void deleteChildData(int groupPosition, int childPosition) {

		List<ChildData> childDataList = getChildDataList(groupPosition);

		if (childDataList.size() == 1) {
			deleteGroupData(groupPosition);
		} else {
			// 去 移除 这个 集合里的子 数据
			childDataList.remove(childPosition);
		}

	}

	/**
	 * 改变 子的购买商品的数量
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @param a
	 */
	public void onChangeChildData(int groupPosition, int childPosition, String a) {

		List<ChildData> childDataList = getChildDataList(groupPosition);

		ChildData childData = childDataList.get(childPosition);
		// 改变 数量 子 里面 改变的数量
		childData.setChildNum(a);

	}

	/**
	 * 删除这一组的方法
	 * 
	 * @param groupPosition
	 * @return
	 */
	public GroupData deleteGroupData(int groupPosition) {
		if (contentData == null) {
			return null;
		}
		// 去移除 这一个组
		return contentData.remove(groupPosition);
	}

	// TODO 去得到 组下边的 子的 数据源
	public List<ChildData> getChildDataList(int groupPosition) {
		GroupData groupData = getGroupData(groupPosition);
		if (groupData == null) {
			return null;
		}
		return groupData.getItems();
	}

	// 得到组的数据源 的长度
	public int getChildCount(int groupPosition) {
		List<ChildData> childDataList = getChildDataList(groupPosition);
		if (childDataList == null) {
			return 0;
		}
		return childDataList.size();
	}

	// 下面的是说每一组的父类都要被选上...
	public void setGroupChecked(int groupPosition) {
		// 这个是 那一组的 数据 源
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}

		// 下面的代码表示的是父类被点击了全选之后 ，，，子类的所有的checkbox 都要被选中...
		groupData.setGroupSelected(true);

		List<ChildData> childDataList = groupData.getItems();
		if (childDataList != null) {
			for (ChildData childData : childDataList) {
				childData.setChildSelected(true);
			}
		}
	}
	
	/**
	 * 这个方法跟上边的方法是一样的 没区别
	 * 
	 * @param groupPosition
	 */
	public void setGroupSetingChecked(int groupPosition) {
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}
		// 下面的代码表示的是父类被点击了全选之后 ，，，子类的所有的checkbox 都要被选中...
		groupData.setSetingClicked(true);
		List<ChildData> childDataList = groupData.getItems();
		if (childDataList != null) {

			for (ChildData childData : childDataList) {
				childData.setChildSetingClicked(true);
			}
		}
	}

	/**
	 * 这个
	 * 
	 * @param groupPosition
	 */
	public void setGroupSetingUnChecked(int groupPosition) {
		
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}
		// 下面的代码表示的是父类被点击了取消 ，，，子类的所有的checkbox 都要被取消...
		groupData.setSetingClicked(false);
		List<ChildData> childDataList = groupData.getItems();
		if (childDataList != null) {
			for (ChildData childData : childDataList) {
				childData.setChildSetingClicked(false);
			}
		}
	}

	// 下面的代码表示父类被点击取消选择了之后 ，子类的全部都要取消选择...
	public void setGroupUnChecked(int groupPosition) {
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}
		groupData.setGroupSelected(false);
		List<ChildData> childDataList = groupData.getItems();
		if (childDataList != null) {
			for (ChildData childData : childDataList) {
				childData.setChildSelected(false);
			}
		}

	}

	// 下面的表示单独的选择子类的item 会出现的一些的情况...
	public void setChildChecked(int groupPosition, int childPosition) {
		// 先是这个 组集合
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}
		// 然后 是这个 子 集合
		List<ChildData> childDataList = groupData.getItems();
		if (childDataList == null) {
			return;
		}

		// 先声明一个为true的事件...一有不符合的，，，立马设为false ...
		boolean allChecked = true;
		// TODO
		for (int i = 0; i < childDataList.size(); i++) {
			ChildData childData = childDataList.get(i);

			if (i == childPosition) {
				childData.setChildSelected(true);
			}
			// 下面的这个if 是判断chidlData里面的值是不是全部的选中了...
			if (!childData.isChildSelected()) {
				allChecked = false;
			}
		}
		if (allChecked) {
			groupData.setGroupSelected(true);
		}
	}

	/**
	 * 设置 子控件没有被选中
	 * @param groupPosition
	 * @param childPosition
	 */
	public void setChildUnChecked(int groupPosition, int childPosition) {
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return;
		}
		// 只在一点击取消，，，立马 那个父选项显示为   未被显示的状态...
		groupData.setGroupSelected(false);
		List<ChildData> childDataList = groupData.getItems();
		if (childDataList == null) {
			return;
		}
		
		ChildData childData = childDataList.get(childPosition);
		childData.setChildSelected(false);

	}

	/**
	 * 调用这个方法 去 判断这个 组 是不是 被选中了 返回的 是boolean
	 * 
	 * @param groupPosition
	 * @return
	 */
	public boolean isGroupSelected(int groupPosition) {
		GroupData groupData = contentData.get(groupPosition);
		if (groupData == null) {
			return false;
		}
		return groupData.isGroupSelected();
	}

	/**
	 * 这个是子 返回的 是 boolean
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 */
	public boolean isChildSelected(int groupPosition, int childPosition) {
		ChildData childData = getChildData(groupPosition, childPosition);
		if (childData == null) {
			return false;
		}
		return childData.isChildSelected();
	}

	/**
	 * 所有的组 子 被选中的 方法 返回的 boolean
	 * 
	 * @param groupPosition
	 * @return
	 */
	public boolean isAllChildSelected(int groupPosition) {
		List<ChildData> childDataList = getChildDataList(groupPosition);
		if (childDataList == null) {
			return false;
		}
		for (ChildData childData : childDataList) {
			if (childData == null) {
				return false;
			}
			if (!childData.isChildSelected()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 这些 工作 都是 为 全选 做准备
	 */
	// 下面的这个方法是所有的item 都要被 选 上的说...

	public void setAllGroupAndChildChecked() {
		// 得到这个组的 数量
		int groupCount = getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			setGroupChecked(i);
		}
	}

	// 这个是所有的item 全不被选上的说...

	public void setAllGroupAndChildUnChecked() {
		int groupCount = getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			setGroupUnChecked(i);
		}
	}

	// 这里的continue 是跳出本次循环接着向下执行...

	public List<GroupData> getCheckedDataList() {
		if (contentData == null) {
			return null;
		}
		// 声明出来 一个 集合 组的集合
		List<GroupData> checkedGroupList = new ArrayList<GroupData>();
		// 这个组对象：组的集合？ 不明白
		
		for (GroupData shopData : contentData) {
			List<ChildData> items = shopData.getItems();
			if (items == null) {
				continue;
			}

			List<ChildData> checkedChildList = null;

			for (ChildData item : items) {
				if (item.isChildSelected()) {

					if (checkedChildList == null) {
						checkedChildList = new ArrayList<ChildData>();
					}
					// 如果这一个 是 null 那么 我们就 吧 子对象 添加到 子 集合 中去
					checkedChildList.add(item);
				}
			}

			// 如果 这个 不是 null
			if (checkedChildList != null) {
				// 创建了 一个 groupdata

				GroupData checkedShopData = new GroupData();

				// 把这个 子 放到组 里面去
				checkedShopData.setItems(checkedChildList);
				checkedShopData.setGroupName(shopData.getGroupName());
				checkedShopData.setGroupSelected(shopData.isGroupSelected());
				checkedGroupList.add(checkedShopData);
			}
		}
		return checkedGroupList;
	}
	/**
	 * 优惠券 被选中的情况  --->我要获取相对应下的优惠券的 钱数    TODO     我有  购物车的集合 了    我也有   优惠券的集合了
	 */
	
	
	
	
	/**
	 * 得到总的 价格
	 * 
	 * @return
	 */
	public double getotalPrice() {

		double price = 0;
		if (contentData == null) {
			return 0;
		}
		// List<GroupData> checkedGroupList = new ArrayList<GroupData>();
		for (GroupData shopData : contentData) {
			List<ChildData> items = shopData.getItems();
			if (items == null) {
				continue;
			}
			// List<ChildData> checkedChildList = null;
			for (ChildData item : items) {
				// 如果这个 item 是选中的 为true 那么 我们 就 去 计算 价格
				if (item.isChildSelected()) {
					price = price + Double.parseDouble(item.getChildPrice())
							* Double.parseDouble(item.getChildNum());

				}
			}

		}
		return price;
	}
}
