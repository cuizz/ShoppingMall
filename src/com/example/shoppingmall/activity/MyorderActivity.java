package com.example.shoppingmall.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.AddressActivity.AddressAdapter;
import com.example.shoppingmall.entity.AddressEntity;
import com.example.shoppingmall.entity.OrdersEntity;
import com.example.shoppingmall.entity.OrderslistEntity;
import com.example.shoppingmall.utils.Consts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class MyorderActivity extends Activity{
	private ListView listview;
	private String cartOrder;
	private String cartTime;
	private List<OrdersEntity>order=new ArrayList<OrdersEntity>();
	private List<OrderslistEntity>goods=new ArrayList<OrderslistEntity>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myorder);
		intviews();
		new OrdersTask().execute(Consts.URL);
	}
	private void intviews() {
		// TODO Auto-generated method stub
		listview=(ListView) findViewById(R.id.listview);
	}
	class OrdersTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "orders");
				requestParams.addBodyParameter("a", "getOrderList");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("user_id", "45");
				jsonObject.put("num", "100");
				jsonObject.put("num", "1");
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("orders", "getOrderList");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				//requestParams.addBodyParameter("sign", GetSign.sign("site_voucher", "sv_list"));
				requestParams.addBodyParameter("sign", cartOrder);
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject jsonobject=new JSONObject(response);
							JSONArray array=jsonobject.getJSONArray("result");
							if(jsonobject.get("error").equals("0")){
								for(int i=0;i<array.length();i++){
									OrdersEntity entity=new OrdersEntity();
									JSONObject object=array.getJSONObject(i);
									entity.setShap_name(object.getString("shap_name"));
									entity.setState_name(object.getString("state_name"));
									JSONObject object2=array.getJSONObject(i);
									JSONArray arrays=object2.getJSONArray("lists");
									for(int j=0;j<arrays.length();j++){
										OrderslistEntity entity2=new OrderslistEntity();
										JSONObject object3=arrays.getJSONObject(j);
										entity2.setGoods_name(object3.getString("goods_name"));
										entity2.setPrice(object3.getString("price"));
										entity2.setNum(object3.getString("num"));
										goods.add(entity2);
									}
									order.add(entity);
								}
								
							}else{
								Toast.makeText(MyorderActivity.this, "请求服务器失败", 1).show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
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
