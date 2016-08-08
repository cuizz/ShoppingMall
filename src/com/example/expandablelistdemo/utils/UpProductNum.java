package com.example.expandablelistdemo.utils;

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
import com.example.shoppingmall.utils.Consts;

import android.content.Context;
import android.os.Handler;

@SuppressWarnings("unused")
public class UpProductNum {
	private Context mcontext ;
	private String uerId,cartTime ,cartOrder ,CartId , productNum ;
	private JSONObject jsGoodsInfo ;
	

	public UpProductNum(Context context ,String uerId,String CartId ,String productNum ) {
	     
		this.mcontext = context ;
		this.uerId = uerId ;
		this.CartId = CartId  ;
		this.productNum = productNum ;
		postToCart("cart", "changeNum",uerId,CartId ,productNum);
	}

	private void goodsInfo(String str, String strr, String strrr, String strrrr) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("user_id", str);
			jsGoodsInfo.put("id", strr) ;
			jsGoodsInfo.put("num", strrr) ;
//			jsGoodsInfo.put("product_id", "158");
//			jsGoodsInfo.put("number", "20");
//			jsGoodsInfo.put("type", "goods_product") ;
//		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr,String User_id,String CartId ,String productNum) {
		goodsInfo(User_id, CartId, productNum, "1234564");

		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//System.out.println(response+"孙学磊是一个大傻比....");
					

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
