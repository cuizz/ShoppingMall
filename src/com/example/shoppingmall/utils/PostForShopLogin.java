package com.example.shoppingmall.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PostForShopLogin {
	private String name, password;
	private Context context;
	private Handler handler;
	protected String cartTime;
	
	private JSONObject jsGoodsInfo;

	public PostForShopLogin(Context context,  String name,
			String password, String c, String a) {
		super();
		this.name = name;
		this.password = password;
		
		this.context = context;
		this.handler = handler;
		Log.d("sht", "name->"+name+",password->"+password+",c->"+c+",a->"+a);
		// ���� �̵�ķ���?
		postforshoplogin(c,a);
	}

	private void goodsInfo(String name, String password) {

		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("username", name);
			jsGoodsInfo.put("password", password);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void postforshoplogin(final String c,final String a) {
		goodsInfo(name, password);
		RequestQueue queue = Volley.newRequestQueue(context);
		int method = Method.POST;
		String url = "http://115.28.175.114/tjx_shop/test.php";
		Listener<String> listener = new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d("sht", "商店的登�?->"+response);
				
			}
		};
		ErrorListener errorListener = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		};
		StringRequest stringRequest = new StringRequest(method, url, listener,
				errorListener) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("c", c);
				map.put("a", a);
				// ��ȡ��ǰ��ʱ��
				map.put("param", jsGoodsInfo.toString());
				cartTime = new Date().getTime() + "";
				map.put("timesnamp", cartTime);
				map.put("openid", "3");
				String sign1=FormSign(cartTime);
				String sign2=md5(sign1);
				String sign = md5(sign2);
				map.put("sign", sign);
				
				Log.d("sht", "c->"+c+",a->"+a+",param->"+jsGoodsInfo.toString()+",timesnamp->"+cartTime+",sign->"+sign);
				return map;
			}
		};
		queue.add(stringRequest);
	}
	/**
	 * ����ǩ���ķ���
	 */
	@SuppressWarnings("unchecked")
	public String FormSign(String time) {
		String c = "shop";
		String a = "login";

		String token = "sunrock";
		// ���� ���� ��Щ�ַ��� ���� ���� ����Щ �ַ��� ƴ�� ����
		// ����Ҫ�ȸ� �� 5 ������ ���� �������� treeMap Ĭ�� ���������?
		Map<String, String> map = new TreeMap<String, String>();
		map.put("c", c);
		map.put("a", a);
		map.put("timenamp", time);
		map.put("token", token);
		Iterator it = map.values().iterator();
		StringBuffer sb = new StringBuffer();

		while (it.hasNext()) {
			// ���ڵõ�����key
			String values = (String) it.next();
			// ������ط�ƴ��?
			sb.append(values);
		}
		
		String join = sb.toString();
		return join;
	}
	/**
	 * ��ƴ����ɵ��ַ���? ���� md5 ����
	 */
	public static String md5(String str) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh,UTF-8 should be supported?", e);
		}
		StringBuffer hex = new StringBuffer(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	
}
