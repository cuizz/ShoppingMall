package com.example.shoppingmall.orders;

import java.util.List;

import com.example.shoppingmall.R;
import com.example.shoppingmall.entity.OrdersEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ItemOrdersTop implements OrderContent{
	private List<OrdersEntity>list;
	private OrdersEntity entity;
	public ItemOrdersTop(List<OrdersEntity>list,OrdersEntity entity) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.entity=entity;
	}
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.ordercontent_top;
	}

	@Override
	public boolean isClickable() {
		// TODO Auto-generated method stub
		return true;
	}
	public OrdersEntity order(){
		return entity;
	}
	@Override
	public View getView(Context context, View convertView,
			LayoutInflater inflater) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(getLayout(), null);
		TextView shopname=(TextView) convertView.findViewById(R.id.namet);
		TextView state=(TextView) convertView.findViewById(R.id.ordertext);
		shopname.setText(entity.getShap_name());
		state.setText(entity.getState_name());
		return convertView;
	}

}
