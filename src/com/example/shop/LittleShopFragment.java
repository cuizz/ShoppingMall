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
	
	
	public static String productName[]=new String[]{"商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5",
		"商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5"};
	
	private String mshop_id;
	private Context mContext;
	private RecyclerView recyclerView;
	private RecyclerViewAdapter recyclerViewAdapter;
	
	private String strResult = null;
	private String result;
	private List<NameValuePair> params;
	private String myurl = "http://192.168.1.115/liangpin/index.php";
	protected String s;
	
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
		
		
//		recyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
		
		
//		productList = new PostToProductList(getActivity(), "10", mhandler, recyclerView, mshop_id);
		
//		s = new Date().getTime() + "";
//  		String time = s.substring(0,10);
//  		String password = "getProductListproduct";
//  		String token = "liangpin";
//  		String Md5Password = Md5.getMD5Str(password + time + token);
//  		
//  		String sign = Md5.getMD5Str(Md5Password);
//        String openid = "1";
//        String a = "getProductList";
//        String c = "product";
//
//        params=new ArrayList<NameValuePair>();
//  		params.add(new BasicNameValuePair("a", a));
//  		params.add(new BasicNameValuePair("c", c));
//  		try{
//  			JSONObject json = new JSONObject();
//  			json.put("shop_id", "1");
//  	  		json.put("city_id", "370100");
//  	  		json.put("order", "1");
//  	  		json.put("num", "5");
//  	  		json.put("shop_page", "1");
//  	  		
//  	  	params.add(new BasicNameValuePair("param",json.toString() ));
//  		}catch(Exception e){
//  			
//  		}
//  		params.add(new BasicNameValuePair("timesnamp", time));
//  		params.add(new BasicNameValuePair("openid", openid));
//  		params.add(new BasicNameValuePair("sign", sign));
  		
		
		
		
		
  		recyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
  		
  		
  		productList=new PostToProductList(getActivity(), "10", mhandler, recyclerView,"1");
  		productList.addListData();
  		
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            
            recyclerView.setLayoutManager(layoutManager);
            
            
            
            //view.setTag(recyclerView);
             //views.add(view);
             
//             new Thread(getJson).start();
//             Log.d("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", "执行了线程");
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
						recyclerViewAdapter.notifyDataSetChanged();
					}
					//listview.onRefreshComplete();
				} catch (Exception e) {
					// TODO Auto-generated catch block   14696862124973.jpg
					e.printStackTrace();
				}

				break;
			}
		};
	};
	
	
	
	
	
	
	
	
//	private Runnable getJson=new Runnable(){
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			try
//			{
//				result=GetJson(myurl, params);
//				//handler.sendEmptyMessage(0x00);
//			}
//			catch(Exception e)
//			{
//				//handler.sendEmptyMessage(0x01);
//			}
//		}
//	};
//	
////	Handler handler=new Handler(){
////		@Override
////		public void handleMessage(android.os.Message msg){
////			if(msg.what==0x00)
////			{
////				//textview.setText(result);
////				Log.v("PostGetJson",""+result);
////			}
////			else if(msg.what==0x01)
////			{
////			}
////		}
////	};
//	
//	/**
//	 * ����post�����ȡjson�ַ�
//	 * @param url ��վ
//	 * @param params ����List
//	 * @return json�ַ�
//	 */
//	private String GetJson(String url, List<NameValuePair> params) {
//		HttpPost httpRequest = new HttpPost(url);
//		
//		HttpParams httpParameters1 = new BasicHttpParams();
//		HttpConnectionParams
//		.setConnectionTimeout(httpParameters1, 10 * 1000);
//		HttpConnectionParams.setSoTimeout(httpParameters1, 10 * 1000);
//
//		try {
//			httpRequest.setParams(httpParameters1);
//			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//
//			HttpResponse httpResponse = new DefaultHttpClient()
//					.execute(httpRequest);
//
//			if (httpResponse.getStatusLine().getStatusCode() == 200) 
//			{
//
//				strResult = EntityUtils.toString(httpResponse.getEntity());
//				Log.d("datatatas", strResult);
//				parseJson(strResult);
//			} 
//			else 
//			{
//
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return strResult;
//	}
//	
//	 private void parseJson(String strResult) {   
//		 
//	        try {   
//	        	
//	        	JSONObject jsonObject=new JSONObject(strResult);
//	        	JSONArray jsonArray = jsonObject.optJSONArray("result");
//	        	for(int i = 0;i < jsonArray.length(); i++){
//	        		ProductBean product = new ProductBean();
//	        		JSONObject json = jsonArray.optJSONObject(i);
//	        		product.setGoodsName(json.getString("title"));
//	        		productBean.add(product);
//	        	}
//	        	if(recyclerViewAdapter==null){
//	        		recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),productBean);
//					recyclerView.setAdapter(recyclerViewAdapter);
//					Log.d("aaaaaaaaaaaaaaa", productBean.size()+"");
//	        	}else{
//	        		recyclerViewAdapter.notifyDataSetChanged();
//	        	}
//	        } catch (JSONException e) {   
//	            e.printStackTrace();   
//	        }  
//	        
//	    }   
	 
}
