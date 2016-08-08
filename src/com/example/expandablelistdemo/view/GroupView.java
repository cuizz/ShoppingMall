package com.example.expandablelistdemo.view;


import com.example.shoppingmall.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("NewApi")
public class GroupView extends LinearLayout {

	private int groupPosition;

	private OnGroupClickListeners listener;


	private CheckBox selectGroup;

	private TextView groupName, groupSeting;
	
	public GroupView(GroupView.OnGroupClickListeners activity, Context context) {
		this(activity, context,null);
	}

	public GroupView(OnGroupClickListeners listener, Context context,
			AttributeSet attrs) {
		this(listener, context, attrs, 0);
	}

	public GroupView(OnGroupClickListeners listener, Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.listener = listener;
		intViews();
	}

	public void intViews() {
		final LayoutInflater mLayoutInflater = LayoutInflater
				.from(getContext());
		View v = mLayoutInflater.inflate(R.layout.group, GroupView.this, false);
		addView(v);

		selectGroup = (CheckBox) v.findViewById(R.id.checkbox_select_group);
		groupName = (TextView) v.findViewById(R.id.textview_group_name);
		groupSeting= (TextView) v.findViewById(R.id.seting) ;
		selectGroup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectGroup.isChecked()) {
					//这是被点击时候的状态。。。
					listener.onGroupChecked(groupPosition);
				} else {
					listener.onGroupUnChecked(groupPosition);
				}
			}
		});
		
		groupSeting.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(groupSeting.getText().equals("编辑")){
					listener.onSetingClicked(groupPosition) ;
					groupSeting.setText("确定") ;
				}else{
					//System.out.println("我不是一个傻比") ;
					listener.onSetingUnChecked(groupPosition) ;
					groupSeting.setText("编辑");
				}
			}
		}) ;
		
	}
	/**
	 * 这里面 都是  有关组 控件的  回调 方法
	 * @author sht
	 *
	 */
	public interface OnGroupClickListeners {
		//这个组 被选中的方法
		public void onGroupChecked(int groupPosition);
		//这个组 没有被选中的方法
		public void onGroupUnChecked(int groupPosition);
		//
		public void onSetingClicked(int groupPosition);
		//
		public void onSetingUnChecked(int groupPosition);
	}


	public int getGroupPosition() {
		return groupPosition;
	}

	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}

	public CheckBox getSelectGroup() {
		return selectGroup;
	}

	public void setSelectGroup(CheckBox selectGroup) {
		this.selectGroup = selectGroup;
	}

	public TextView getGroupName() {
		return groupName;
	}

	public void setGroupName(TextView groupName) {
		this.groupName = groupName;
	}
	public TextView getGroupSeting() {
		return groupSeting;
	}

	public void setGroupSeting(TextView groupSeting) {
		this.groupSeting = groupSeting;
	}

}
