package com.example.shoppingmall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingmall.R;
import com.example.shoppingmall.entity.ShopEntity;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdapter extends BaseAdapter{
	private Context context;
	private List<ShopEntity>list=new ArrayList<ShopEntity>();
	public ShopAdapter(Context context,List<ShopEntity>list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.shopadapter, null);
			holder.iv=(ImageView) convertView.findViewById(R.id.shopimageview);
			holder.shopnameTV=(TextView) convertView.findViewById(R.id.shopnameTV);
			holder.pingjiaTV=(TextView) convertView.findViewById(R.id.pingjiaTV);
			holder.addressTV=(TextView) convertView.findViewById(R.id.addressTV);
			holder.priceTV=(TextView) convertView.findViewById(R.id.priceTV);
			holder.distanceTV=(TextView) convertView.findViewById(R.id.distanceTV);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		BitmapUtils bitmapUtils=new BitmapUtils(context);
		//bitmapUtils.display(holder.iv, list.get(position).getImg());
		holder.shopnameTV.setText(list.get(position).getName());
		//holder.priceTV.setText(list.get(position).get);
		holder.addressTV.setText(list.get(position).getCountry());
		return convertView;
	}
	class ViewHolder{
		ImageView iv;
		TextView shopnameTV;
		TextView pingjiaTV;
		TextView addressTV;
		TextView priceTV;
		TextView distanceTV;
		
	}
}
