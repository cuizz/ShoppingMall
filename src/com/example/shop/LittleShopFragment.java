package com.example.shop;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ShopActivity;
import com.example.shoppingmall.adapter.ShopAdapter;
import com.example.shoppingmall.entity.ShopEntity;
import com.example.shoppingmall.fragment.MainFragment.MyAdapter;
import com.example.shoppingmall.utils.PostForOneShop;
import com.example.shoppingmall.utils.PostToProductList;
import com.example.zhuye.ProductBean;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LittleShopFragment extends Fragment{
	
	List<View> views=new ArrayList<>();
	
	private String mshop_id;
	private Context mContext;
	private RecyclerView recyclerView;
	private RecyclerViewAdapter recyclerViewAdapter;
	
	private String myurl = "http://192.168.1.115/liangpin/index.php";
	
	private PostToProductList productList;
	private List<ProductBean> productBean = new ArrayList<>();
	
	
//	public LittleShopFragment(Context context, String shop_id){
//		this.mContext = context;
//		this.mshop_id = shop_id;
//	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.product_recycle, container, false);
		
		
  		
		
		
		
		
  		recyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
  		
  		
  		productList=new PostToProductList(getActivity(), "10", mhandler, recyclerView,"1","1");
  		productList.addListData();
  		
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            
            recyclerView.setLayoutManager(layoutManager);
            
            
            
            //view.setTag(recyclerView);
             //views.add(view);
             
		return view;
	}
	
	
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 88:
				try {
					JSONArray array=(JSONArray) msg.obj;
					
					
		        	for(int i = 0;i < array.length(); i++){
		        		
		        		ProductBean product = new ProductBean();
		        		JSONObject json = array.optJSONObject(i);
		        		
		        		product.setGoodsName(json.optString("title"));
  	                	product.setPrice(json.optString("price"));
  	                	productBean.add(product);
		        	}
					
					if(recyclerViewAdapter==null){
						recyclerViewAdapter=new RecyclerViewAdapter(getActivity(), productBean);
						recyclerView.setAdapter(recyclerViewAdapter);
					}else{
						//recyclerViewAdapter.notifyDataSetChanged();
						recyclerViewAdapter=new RecyclerViewAdapter(getActivity(), productBean);
						recyclerView.setAdapter(recyclerViewAdapter);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		};
	};
	 
}
