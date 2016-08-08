package com.example.expandablelistdemo.helper;

import com.example.expandablelistdemo.view.ChildView;
import com.example.expandablelistdemo.view.GroupView;

import android.util.SparseArray;
import android.view.View;


public class ViewHolder {
	// arraylist 一样 比他更好的 集合 这个 集合里面放的 是控件 这个放的父 控件
	private volatile SparseArray<GroupView> groupViewMap = new SparseArray<GroupView>();
	// 这个 放的 子控件
	private volatile SparseArray<SparseArray<ChildView>> childViewMap = new SparseArray<SparseArray<ChildView>>();

	public void deleteAllgroupMap() {
		groupViewMap.clear();
		childViewMap.clear();
	}

	/**
	 * 设置 组控件
	 * 
	 * @param groupPosition
	 * @param groupView
	 */
	public void setGroupView(int groupPosition, GroupView groupView) {
		groupViewMap.put(groupPosition, groupView);
	}

	/**
	 * 去 得到组控件
	 * 
	 * @param groupPosition
	 * @return
	 */
	public GroupView getGroupView(int groupPosition) {
		return groupViewMap.get(groupPosition);
	}

	/**
	 * 设置 子控件
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @param childView
	 */
	// 这个 填充view 的是向里面选项...
	public void setChildView(int groupPosition, int childPosition,
			ChildView childView) {
		// 子控件的 集合
		SparseArray<ChildView> childViews = childViewMap.get(groupPosition);
		// 如果 这个 子控件 没有元素 那么 我们 新创建这个 集合 往里面 在 添 东西
		if (childViews == null) {
			childViews = new SparseArray<ChildView>();
			childViewMap.put(groupPosition, childViews);
		}

		childViews.put(childPosition, childView);
	}

	/**
	 * 去 得到这个子控件
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 */
	public ChildView getChildView(int groupPosition, int childPosition) {
		SparseArray<ChildView> childViews = childViewMap.get(groupPosition);
		if (childViews == null) {
			return null;
		}
		return childViews.get(childPosition);
	}

	/**
	 * 得到子控件 的集合
	 * 
	 * @param groupPosition
	 * @return
	 */
	public SparseArray<ChildView> getChildViewMap(int groupPosition) {
		return childViewMap.get(groupPosition);
	}

	/**
	 * 这个是 组被选 中的 怎么就选中 了 呢/？ 选的那个 控件
	 * 
	 * @param groupPosition
	 * @return
	 */
	public boolean isGroupChecked(int groupPosition) {
		// TODO
		GroupView groupView = getGroupView(groupPosition);
		if (groupView == null) {
			return false;
		}
		// 返回的 是这个 点击的 那个控件的 boolean 值
		return groupView.getSelectGroup().isChecked();
	}

	/**
	 * 这个是 子的 选中
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 */
	public boolean isChildChecked(int groupPosition, int childPosition) {

		ChildView childView = getChildView(groupPosition, childPosition);

		if (childView == null) {
			return false;
		}
		
		return childView.getSelectChild().isChecked();
	}

	
	/**
	 * 
	 * @param groupPosition
	 */
	// 下面的这个代码 才是让checkbox 表示选中与未选中的情况的了...
	public void setGroupChecked(int groupPosition) {
		// 这个组 控件
		GroupView groupView = getGroupView(groupPosition);

		if (groupView == null) {
			return;
		}
		// getselectgroup 是 checkbox 的控件
		if (!groupView.getSelectGroup().isChecked()) {
			groupView.getSelectGroup().setChecked(true);
		}

		// 下面的这个东西就相当于一个list 表的啊...
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);

		// 这个 子控件 的集合 小于 0 或者 为 空 那么 我们 就 跳出
		if (childViews == null || childViews.size() <= 0) {
			return;
		}
		// 这个 是 子控件 的
		for (int i = 0; i < childViews.size(); i++) {
			// 得到 这个 子集合 的 对象
			ChildView childView = childViews.get(i);

			if (!childView.getSelectChild().isChecked()) {
				childView.getSelectChild().setChecked(true);
			}
		}
	}

	// 。。。
	public void setGroupSettingChecked(int groupPosition) {
		GroupView groupView = getGroupView(groupPosition);
		if (groupView == null) {
			return;
		}

		// 下面的这个东西就相当于一个list 表的啊...
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);
		if (childViews == null || childViews.size() <= 0) {
			return;
		}
		
		for (int i = 0; i < childViews.size(); i++) {

			ChildView childView = childViews.get(i);
			childView.getChildNamee().setVisibility(View.VISIBLE);
			childView.getChildNameee().setVisibility(View.GONE);
		}
	}

	// 下面的这个代码 才是让checkbox 表示选中与未选中的情况的了...
	public void setGroupSettingUnChecked(int groupPosition) {
		// 这个 是 组控件 的 对象
		GroupView groupView = getGroupView(groupPosition);
		if (groupView == null) {
			return;
		}

		// 下面的这个东西就相当于一个list 表的啊... 这个 是 子的 集合
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);

		if (childViews == null || childViews.size() <= 0) {
			return;
		}
		for (int i = 0; i < childViews.size(); i++) {
			// 得到 子 控件 对象
			ChildView childView = childViews.get(i);
			System.out.println("看看我要执行几次..." + childViews.size());
			// 设置 这个 layout 去隐藏 的 是 删除的 布局
			childView.getChildNamee().setVisibility(View.GONE);

			// 显示的 是 商品信息的 布局
			childView.getChildNameee().setVisibility(View.VISIBLE);
			
		}
	}

	/*
	 * 这个 是 设置 组 不选中
	 */
	public void setGroupUnChecked(int groupPosition) {
		GroupView groupView = getGroupView(groupPosition);

		if (groupView == null) {
			return;
		}
		if (groupView.getSelectGroup().isChecked()) {
			groupView.getSelectGroup().setChecked(false);
		}
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);
		if (childViews == null || childViews.size() <= 0) {
			return;
		}

		for (int i = 0; i < childViews.size(); i++) {
			ChildView childView = childViews.get(i);
			if (childView.getSelectChild().isChecked()) {
				childView.getSelectChild().setChecked(false);
			}
		}
	}

	// =====================================================================
	// 让没有选中的都给选上...
	public void setChildChecked(int groupPosition, int childPosition) {
		// 这个是 子 控件的 集合
		SparseArray<ChildView> childViews = getChildViewMap(groupPosition);

		if (childViews == null || childViews.size() <= 0) {
			return;
		}
		// 给他 一个自定义的
		boolean allChildChecked = true;

		for (int i = 0; i < childViews.size(); i++) {
			// 得到 了 子控件
			ChildView childView = childViews.get(i);

			if (childPosition == i) {

				if (!childView.getSelectChild().isChecked()) {

					childView.getSelectChild().setChecked(true);
				}
			}

			if (!childView.getSelectChild().isChecked()) {
				allChildChecked = false;
			}
		}
		
		// 下面的这行代码表示的是所有的孩子被选上之后 。。。父亲也要默认的被选上了...

		if (allChildChecked) {
			GroupView groupView = getGroupView(groupPosition);
			if (!groupView.getSelectGroup().isChecked()) {
				groupView.getSelectGroup().setChecked(true);
			}
		}
	}

	/**
	 * 设置子控件 没有被选的 方法
	 * @param groupPosition
	 * @param childPosition
	 */
	public void setChildUnChecked(int groupPosition, int childPosition) {
		ChildView childView = getChildView(groupPosition, childPosition);
		if (childView == null) {
			return;
		}
		
		if (childView.getSelectChild().isChecked()) {
			childView.getSelectChild().setChecked(false);
		}
		
		GroupView groupView = getGroupView(groupPosition);
		if (groupView.getSelectGroup().isChecked()) {
			groupView.getSelectGroup().setChecked(false);
		}
	}
	
	/**
	 * 全选
	 */
	// 下面的这个应该是全选或者是全不选的...   
	public void setAllGroupAndChildChecked() {
		if (groupViewMap == null || groupViewMap.size() <= 0) {
			return;
		}
		int groupCount = groupViewMap.size();
		for (int i = 0; i < groupCount; i++) {
			setGroupChecked(i);
		}
	}

	// 这个是全不选的一条说明 ...   ？？？
	
	public void setAllGroupAndChildUnChecked() {
		if (groupViewMap == null || groupViewMap.size() <= 0) {
			return;
		}
		int groupCount = groupViewMap.size();
		for (int i = 0; i < groupCount; i++) {
			setGroupUnChecked(i);
		}
	}

	public boolean isAllGroupandChildChecked() {
		if (groupViewMap == null || groupViewMap.size() <= 0) {
			return false;
		}
		int groupCount = groupViewMap.size();
		for (int i = 0; i < groupCount; i++) {
			if (!isGroupChecked(i)) {
				return false;
			}
		}
		return true;
	}
}
