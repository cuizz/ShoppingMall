package com.example.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PostToProductList {
	private String num, order;
	
	private RecyclerView recyclerView;
	private Context context;
	private Handler mhandler;
	protected String cartTime;
	private JSONObject jsGoodsInfo;
	private String cartOrder;
	private String shop_id;
	private int page=1;
	private String city_id = "370100";
	public PostToProductList(Context context,  String num,
			Handler mhandler,RecyclerView recyclerView,String shop_id,String order) {
		super();
		this.num = num;
		this.context = context;
		this.recyclerView=recyclerView;
		this.mhandler = mhandler;
		this.shop_id=shop_id;
		this.order = order;
	}
	public void setAttrs(String order){
		this.order=order;
		//this.orderType=orderType;
	}
	public void removeListData(){
		page=1;
	}
	public void addListData(){
		requestData("getProductList", "product", num, page+"",order,shop_id,city_id);
		page++;
	}
	
	private void goodsInfo(String str, String strr,String order,String shop_id ,String city_id) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("shop_id", shop_id) ;
			jsGoodsInfo.put("city_id", city_id) ;
			jsGoodsInfo.put("order", order) ;
			jsGoodsInfo.put("num", str);
			jsGoodsInfo.put("page", strr);	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void requestData(final String a,final String c,final String num,final String page,final String order,String shop_id,String city_id){
		goodsInfo(num, page,order,shop_id,city_id );

		RequestQueue mQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response) ;
							JSONArray array = json.optJSONArray("result") ;
							if(array==null){	
								Toast.makeText(context, "您已经拉到了最底部...", Toast.LENGTH_LONG).show();
								//listview.onRefreshComplete() ;
							}else{
								mhandler.sendMessage(mhandler.obtainMessage(88,array));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("openid", "1");
				cartTime = new Date().getTime() + "";
				getCartInfo(c, a);
				map.put("sign", cartOrder);
				map.put("a", a);
				map.put("c", c);
				map.put("timesnamp", cartTime);
				map.put("param", jsGoodsInfo.toString());
				return map;
			}
		};
		mQueue.add(stringRequest);
	}

		
	
	public void md5sCart(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			cartOrder = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这里边 是 md5 两次加密的 方法
	 * 
	 * @param str
	 *            这个 是 c
	 * @param strr
	 *            这个 是 a
	 */
	private void getCartInfo(String str, String strr) {
		md5sCart(strr + str + cartTime + "liangpin");
		md5sCart(cartOrder);
	}

}
