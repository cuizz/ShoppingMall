package com.example.shoppingmall.activity;

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
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingmall.R;
import com.example.shoppingmall.utils.Consts;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ProductsConfirmActivity extends Activity implements OnCheckedChangeListener ,View.OnClickListener{

	private JSONArray shops;
	private String price;
	private JSONArray products;
	private SharedPreferences preferences;
	private com.nostra13.universalimageloader.core.ImageLoader mImageLoader;
	private DisplayImageOptions options;
	private CheckBox lineon, lineoff;
	private String product_ids, product_nums;
	private ImageView forreturned;
	private String cart_id;
	private String goods_id;
	private String number;
	private String user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.products_confirm);
		ListView lv = (ListView) super.findViewById(R.id.checked_products);
		TextView totalPrice = (TextView) super.findViewById(R.id.total_price);
		initeImage();
		products = new JSONArray();
		Intent it = super.getIntent();
		cart_id = it.getStringExtra("cart_id");
		goods_id = it.getStringExtra("goods_id");
		number = it.getStringExtra("number");
		user_id = it.getStringExtra("user_id");

		
		
		String checkedListStr = it.getStringExtra("checkedList");
		//Log.d("sht", "购物车里的 json 信息-->" + checkedListStr);
		
		try {
			JSONObject checkedList = new JSONObject(checkedListStr);
			price = checkedList.optString("total_price");
			totalPrice.setText("¥"+price);
			shops = checkedList.optJSONArray("product");
			for (int i = 0; i < shops.length(); i++) {
				JSONArray productsPart = shops.getJSONObject(i).getJSONArray(
						"childDatalist");
				for (int j = 0; j < productsPart.length(); j++) {
					products.put(productsPart.getJSONObject(j));
					JSONObject json = productsPart.getJSONObject(i);
				}
			}
			//System.out.println(shops.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String userids = "";
		String numb = "";
		String middlething;
		String middlenum;
		for (int i = 0; i < products.length(); i++) {
			try {
				JSONObject js = products.getJSONObject(i);
				//Log.d("sht",
						//"这个是线下支付时 生成的  json  我需要的是购物车的 id  user id跟  想看一下里面的内容--》"
							//	+ js);
				middlething = js.optString("product_id");
				middlenum = js.optString("num");
				// 是把 product +num 拼接 到一块了
				userids = userids + middlething + ",";
				numb = numb + middlenum + ",";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			product_ids = userids.substring(0, userids.length() - 1);
			product_nums = numb.substring(0, numb.length() - 1);
			//System.out.println("我看看用户 的id" + preferences.getString("id", "-1"));
		}
		//创建订单
		//CopyOfPostForCreateOrder order1= new CopyOfPostForCreateOrder(ProductsConfirmActivity.this, user_id, product_ids, product_nums, "1", mHandler);
		// 拿到这些参数 再去建立 这些请求
		// PostForOrder order = new
		// PostForOrder(mHandler,ProductsConfirmActivity.this,cart_id,user_id,goods_id,number);

		lv.setAdapter(new ShopAdapter());

	}

	private void initeImage() {

		//ShoppingMallActivities applic = (ShoppingMallActivities) this
				//.getApplicationContext();
		//mImageLoader = applic.mImageLoader;
		//options = applic.options;
		lineon = (CheckBox) findViewById(R.id.lineon);
		lineoff = (CheckBox) findViewById(R.id.lineoff);
		forreturned = (ImageView) findViewById(R.id.forreturned);
		forreturned.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProductsConfirmActivity.this.finish();
			}

		});
		lineon.setOnCheckedChangeListener(this);
		lineoff.setOnCheckedChangeListener(this);
		preferences = this.getSharedPreferences("user_id",
				Activity.MODE_PRIVATE);
		// 付款按钮
		TextView pay = (TextView) findViewById(R.id.pay);
		pay.setOnClickListener(this);
	}

	// 处理回调的数据

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		private String out_trade_no;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case  999:
				Bundle bunler = msg.getData();
				out_trade_no = bunler.getString("out_trade_no");
				//Log.d("sht", "out_trade_no---999->"+out_trade_no);
				break;
			
			case 11:
				JSONObject js = (JSONObject) msg.obj;
				String order_info = js.toString();			
				//Intent intent = new Intent(ProductsConfirmActivity.this,
					//	CreateOrder.class);
				Intent intent=new Intent();
				intent.putExtra("order_info", order_info);
				intent.putExtra("totalmoney", price);
				ProductsConfirmActivity.this.startActivity(intent);
				break;
			case 333:
				try {
					Bundle bundle = msg.getData();
					String sign = bundle.getString("sign");
					String partner = bundle.getString("partner");
					String notify_url = bundle.getString("notify_url");
					String subject = bundle.getString("subject");
					String seller_id = bundle.getString("seller_id");
					String body = bundle.getString("body");
					String service = bundle.getString("service");
					Intent intent2 = new Intent();
					intent2.putExtra("price", price);
					intent2.putExtra("products", products.toString());
					intent2.putExtra("sign", sign);
					intent2.putExtra("partner", partner);
					intent2.putExtra("notify_url", notify_url);
					intent2.putExtra("seller_id", seller_id);
					intent2.putExtra("subject", subject);
					intent2.putExtra("body", body);
					intent2.putExtra("out_trade_no", out_trade_no);
					Log.d("sht", "out_trade_no--333->"+out_trade_no);
					intent2.putExtra("service", service);
					startActivity(intent2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

		}
	};

	private class ShopAdapter extends BaseAdapter {

		private class ViewHolder {

			public TextView title;
			public ListView lv;
		}

		@Override
		public int getCount() {
			return shops.length();
		}

		@Override
		public Object getItem(int i) {
			Object obj = null;
			try {
				obj = shops.get(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return obj;
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View v, ViewGroup p) {

			View view = v;
			final ViewHolder holder;
			if (v == null) {
				view = getLayoutInflater()
						.inflate(R.layout.shop_list, p, false);
				holder = new ViewHolder();
				holder.title = (TextView) view.findViewById(R.id.shop_name);
				holder.lv = (ListView) view.findViewById(R.id.shop_products);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			JSONObject shop = shops.optJSONObject(i);
			holder.title.setText(shop.optString("shop_name"));
			//给 这个  lv   自定义  控件  设置 adapter 
			holder.lv.setAdapter(new ProductAdapter(shop
					.optJSONArray("childDatalist")));
			return view;
		}
	}

	private class ProductAdapter extends BaseAdapter {

		private JSONArray products;

		private class ViewHolder {

			public TextView product_name;
			public TextView product_price;
			public TextView product_number;
			public ImageView product_img;
		}

		public ProductAdapter(JSONArray products) {
			this.products = products;
			//System.out.println(products.toString());
		}

		@Override
		public int getCount() {
			return products.length();
		}

		@Override
		public Object getItem(int i) {
			Object obj = null;
			try {
				obj = products.get(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return obj;
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View v, ViewGroup p) {
			View view = v;
			ViewHolder holder = null;
			if (v == null) {
				view = getLayoutInflater().inflate(R.layout.product_content, p,
						false);
				holder = new ViewHolder();
				holder.product_name = (TextView) view
						.findViewById(R.id.product_name);
				holder.product_price = (TextView) view
						.findViewById(R.id.product_price);
				holder.product_number = (TextView) view
						.findViewById(R.id.product_number);
				holder.product_img = (ImageView) view
						.findViewById(R.id.product_Img);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			JSONObject product = products.optJSONObject(i);
			holder.product_name.setText(product.optString("child_name"));
			holder.product_price.setText("¥"+product.optString("price"));
			holder.product_number.setText(product.optString("num"));
			// "http://www.tjx365.com/upload/goods/middle/"
			//mImageLoader.displayImage(
					//Const.IMAGE_GOODS_MIDDLE + product.optString("img"),
					//holder.product_img, options);
			return view;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.lineon:
			if (isChecked) {
				lineoff.setChecked(false);
			}
			break;

		case R.id.lineoff:
			if (isChecked) {
				lineon.setChecked(false);
			}
			break;

		}

	}

	@SuppressWarnings("unused")
	@SuppressLint("HandlerLeak")
	@Override
	public void onClick(View arg0) {

		if (lineon.isChecked()) {

			// 点击了 跳转 按钮 ，如果 支付宝 被选中 的 话 我们就去 执行执行 跳转 页面
			// 方法 去请求 支付宝 数据
			//PostForPay pay = new PostForPay(ProductsConfirmActivity.this,
				//	mHandler);
			
			/*
			 * postpay(); Intent it = new Intent(this, PayActivity.class);
			 * it.putExtra("price", price); it.putExtra("products",
			 * products.toString()); Log.d("sht","a==========>" +a);
			 * super.startActivity(it);
			 */
		} else if (lineoff.isChecked()) {

			// 这一个 是请求 订单 号
			//PostForCreateOrder postOrder = new PostForCreateOrder(
				//	ProductsConfirmActivity.this, preferences.getString("id",
					//		"-1"), product_ids, product_nums, "2", mHandler);
			//Log.d("sht", "product_ids->"+product_ids+",product_nums->"+product_nums);

		} else {
			Toast.makeText(ProductsConfirmActivity.this, "请你选择支付方式...",
					Toast.LENGTH_LONG).show();
		}

	}

	private String cartTime;
	private JSONObject jsparam;
	private String cartOrder;
	protected String a;
	protected String b;
	protected String c;
	protected String d;
	protected String e;

	public void param() {
		jsparam = new JSONObject();
		try {
			jsparam.put("out_trade_no", "");
			jsparam.put("order_ids", "");
			jsparam.put("order_type", "orders");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void postpay() {
		param();
		RequestQueue mqQueue = Volley
				.newRequestQueue(ProductsConfirmActivity.this);
		int method = Method.POST;
		String url = Consts.URL;
		Listener<String> listener = new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				//Log.i("sht", "这个是 支付宝的信息----》----第二步" + response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					String sign = obj.getString("sign");
					//Log.d("sht12300", "sign-->第三步" + sign);
					JSONObject ary = obj.getJSONObject("param");
					String partner = ary.getString("partner");
					a = partner;
					String notify_url = ary.getString("notify_url");
					b = notify_url;
					String subject = ary.getString("subject");
					c = subject;
					String seller_id = ary.getString("seller_id");
					d = seller_id;
					String body = ary.getString("body");
					e = body;
					//Log.d("sht", "ary-->第四部" + ary);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};
		// 请求失败？
		ErrorListener errorListener = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		};
		StringRequest request = new StringRequest(method, url, listener,
				errorListener) {

			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				String c = "alipay";
				String a = "createSign";
				map.put("openid", "1");
				cartTime = new Date().getTime() + "";
				getCartInfo(c, a);
				map.put("sign", cartOrder);
				map.put("a", a);
				map.put("c", c);
				map.put("timesnamp", cartTime);
				map.put("param", jsparam.toString());
				//Log.d("sht ", "这个 是请求的参数c->" + c + ",a->" + a + ",cartTime->"
					//	+ cartTime + ",cartOrder->" + cartOrder);

				return map;
			}
		};
		mqQueue.add(request);

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
		md5sCart(strr + str + cartTime + "sunrock");
		md5sCart(cartOrder);
	}

}
