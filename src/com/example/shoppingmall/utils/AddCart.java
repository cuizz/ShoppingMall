package com.example.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

@SuppressWarnings({"unused"})
public class AddCart {
	private Context mcontext ;
	private String uerId,cartTime ,cartOrder,productId ,num  ;
	private JSONObject jsGoodsInfo ;
	

	public AddCart(Context context ,String uerId,String productId ,String num ) {
	     
		this.mcontext = context ;
		this.uerId = uerId ;
		this.productId = productId ;
		this.num = num ;
		
		postToCart("cart", "newCart",uerId,productId,num);
	}

	private void goodsInfo(String str, String strr, String strrr, String strrrr) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("user_id", str);
			jsGoodsInfo.put("product_id", strr) ;
			jsGoodsInfo.put("number", strrr) ;
			jsGoodsInfo.put("type", "goods_product") ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String User_id,String productId ,String num) {
		goodsInfo(User_id, productId,num, "1234564");
		
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject json = new JSONObject(response);
							if(json.optString("error").equals("0")){
									
								Toast.makeText(mcontext, "商品已被加入到购物车...", Toast.LENGTH_LONG).show() ;
							}else{
								Toast.makeText(mcontext, "添加购物车失败...", Toast.LENGTH_LONG).show() ;
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
				getCartInfo(str, strr);
				map.put("sign", cartOrder);
				map.put("a", strr);
				map.put("c", str);
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

	private void getCartInfo(String str, String strr) {

		md5sCart(strr + str + cartTime + "sunrock");
		md5sCart(cartOrder);

	}
}
