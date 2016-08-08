package com.example.shoppingmall.orders;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingmall.R;
import com.example.shoppingmall.entity.OrderslistEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ItemOrdersMiddle implements OrderContent{
 private List<OrderslistEntity>list=new ArrayList<OrderslistEntity>();
 private OrderslistEntity orders;
public ItemOrdersMiddle(List<OrderslistEntity>list,OrderslistEntity orders) {
	// TODO Auto-generated constructor stub
	this.list=list;
	this.orders=orders;
	list.add(orders);
}
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.ordercontent_middle;
	}

	@Override
	public boolean isClickable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getView(Context context, View convertView,
			LayoutInflater inflater) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(getLayout(), null);
		TextView name=(TextView) convertView.findViewById(R.id.name);
		TextView jianjie=(TextView) convertView.findViewById(R.id.jianjie);
		TextView price=(TextView) convertView.findViewById(R.id.price);
		TextView number=(TextView) convertView.findViewById(R.id.number);
		name.setText(orders.getGoods_name());
		//jianjie.setText(orders.get);
		price.setText(orders.getPrice());
		number.setText(orders.getNum());
		return convertView;
	}

}
