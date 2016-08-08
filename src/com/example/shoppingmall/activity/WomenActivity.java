package com.example.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.entity.WomenEntity;
import com.example.shoppingmall.utils.PostToWomenList;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WomenActivity extends Activity{
	private ListView listview;
	private List<WomenEntity>list=new ArrayList<WomenEntity>();
	private WomenAdapter adapter;
	private ImageView backimageview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.women_activity);
		listview=(ListView) findViewById(R.id.listView);
		backimageview=(ImageView) findViewById(R.id.imageView);
		backimageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WomenActivity.this.finish();
			}
		});
		PostToWomenList list=new PostToWomenList(this, "10", mhandler, listview);
		list.addListData();
	}
	
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 88:
				try {
					JSONArray array=(JSONArray) msg.obj;
					for(int i=0;i<array.length();i++){
						JSONObject jsonObject=array.getJSONObject(i);
						WomenEntity entity=new WomenEntity();
						entity.setImg(jsonObject.getString("img"));
						entity.setId(jsonObject.getString("id"));
						entity.setTitle(jsonObject.getString("title"));
						entity.setPrice(jsonObject.getString("price"));
						entity.setAttr1_name(jsonObject.getString("attr1_name"));
						entity.setAttr2_name(jsonObject.getString("attr2_name"));
						//entity.setCity(jsonObject.getString("city"));
						//entity.setCountry(jsonObject.getString("country"));
						list.add(entity);
					}
					if(adapter==null){
						adapter=new WomenAdapter(WomenActivity.this, list);
						listview.setAdapter(adapter);
					}else{
						adapter.notifyDataSetChanged();
					}
					listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(WomenActivity.this,ShopActivity.class);
							startActivity(intent);
						}
					});
					//listview.onRefreshComplete();
				} catch (Exception e) {
					// TODO Auto-generated catch block   14696862124973.jpg
					e.printStackTrace();
				}

				break;
			}
		};
	};
	class WomenAdapter extends BaseAdapter{
		private Context context;
		private List<WomenEntity>lists=new ArrayList<WomenEntity>();
		public WomenAdapter(Context context,List<WomenEntity>lists) {
			// TODO Auto-generated constructor stub
			this.context=context;
			this.lists=lists;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
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
				convertView=View.inflate(context, R.layout.womenadapter, null);
				holder.iv=(ImageView) convertView.findViewById(R.id.shopimageview);
				holder.shopnameTV=(TextView) convertView.findViewById(R.id.shopnameTV);
				holder.pingjiaTV=(TextView) convertView.findViewById(R.id.pingjiaTV);
				holder.addressTV=(TextView) convertView.findViewById(R.id.addressTV);
				holder.priceTV=(TextView) convertView.findViewById(R.id.priceTV);
				holder.distanceTV=(TextView) convertView.findViewById(R.id.distanceTV);
				holder.arrrTv=(TextView) convertView.findViewById(R.id.attrnameTV);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			BitmapUtils bitmapUtils=new BitmapUtils(context);
			//bitmapUtils.display(holder.iv, list.get(position).getImg());
			holder.shopnameTV.setText(lists.get(position).getTitle());
			holder.priceTV.setText("¥"+lists.get(position).getPrice());
			holder.pingjiaTV.setText(lists.get(position).getAttr1_name());
			holder.distanceTV.setText("<"+"99");
			holder.arrrTv.setText(lists.get(position).getAttr2_name());
			//holder.addressTV.setText(list.get(position).getCountry());
			return convertView;
		}
		class ViewHolder{
			ImageView iv;
			TextView shopnameTV;
			TextView pingjiaTV;
			TextView addressTV;
			TextView priceTV;
			TextView distanceTV;
			TextView arrrTv;
		}
	}
}