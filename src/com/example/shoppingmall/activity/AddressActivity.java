package com.example.shoppingmall.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ChangeCityActivity.MyAdapter;
import com.example.shoppingmall.activity.ChangeCityActivity.ViewHolder;
import com.example.shoppingmall.entity.AddressEntity;
import com.example.shoppingmall.entity.City;
import com.example.shoppingmall.utils.Consts;
import com.example.shoppingmall.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddressActivity extends Activity{
	private ListView listview;
	private ImageView backimageview;
	private Button button;
	private String cartOrder;
	private String cartTime;
	private List<AddressEntity>list=new ArrayList<AddressEntity>();
	private AddressAdapter adapter;
	private String del_id;
	private String id;
	private AddressReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address);
		intviews();
		adslisteners();
		new AddressTask().execute(Consts.URL);
		IntentFilter filter=new IntentFilter();
		receiver=new AddressReceiver();
		filter.addAction("address");
		registerReceiver(receiver, filter);
	}

	private void intviews() {
		// TODO Auto-generated method stub
		listview=(ListView) findViewById(R.id.listview);
		backimageview=(ImageView) findViewById(R.id.backImage);
		button=(Button) findViewById(R.id.button);
	}

	private void adslisteners() {
		// TODO Auto-generated method stub
		backimageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddressActivity.this.finish();
			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AddressActivity.this,NewAddressActivity.class);
				startActivity(intent);
			}
		});
	}
	class AddressTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "orders");
				requestParams.addBodyParameter("a", "AddressList");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("user_id", "45");
				jsonObject.put("num", "");
				jsonObject.put("num", "1");
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("orders", "AddressList");
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
									AddressEntity entity=new AddressEntity();
									JSONObject object=array.getJSONObject(i);
									entity.setAddress(object.getString("address"));
									entity.setName(object.getString("name"));
									entity.setPhone(object.getString("phone"));
									entity.setId(object.getString("id"));
									list.add(entity);
								}
								adapter=new AddressAdapter(list, AddressActivity.this);
								listview.setAdapter(adapter);
							}else{
								Toast.makeText(AddressActivity.this, "请求服务器失败", 1).show();
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

	class AddressAdapter extends BaseAdapter{
		List<AddressEntity>lists=new ArrayList<AddressEntity>();
		Context context;

		public AddressAdapter(List<AddressEntity>list,Context context) {
			// TODO Auto-generated constructor stub
			this.lists=list;
			this.context=context;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(AddressActivity.this, R.layout.address_adapter, null);
				holder.nametv=(TextView) convertView.findViewById(R.id.nametextview);
				holder.addresstv=(TextView) convertView.findViewById(R.id.addresstextview);
				holder.phonetv=(TextView) convertView.findViewById(R.id.phonetextview);
				holder.bianyi=(LinearLayout) convertView.findViewById(R.id.bianjiLL);
				holder.delect=(LinearLayout) convertView.findViewById(R.id.delectLL);
				holder.checkBox=(CheckBox) convertView.findViewById(R.id.checkbox_select_child);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.nametv.setText(lists.get(position).getName());
			holder.addresstv.setText(lists.get(position).getAddress());
			holder.phonetv.setText(lists.get(position).getPhone());
			holder.delect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					del_id=list.get(position).getId();
					list.remove(lists.get(position));
					new DelAddressTask().execute(Consts.URL);
					adapter.notifyDataSetChanged();
				}
			});
			holder.bianyi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
					id=lists.get(position).getId();
					new SetAddressTask().execute(Consts.URL);
					}else{
						isChecked=false;
					}
				}
			});
			return convertView;
		}

	}
	class ViewHolder{
		TextView nametv;
		TextView addresstv;
		TextView phonetv;
		CheckBox checkBox;
		LinearLayout bianyi;
		LinearLayout delect;
	}

	class SetAddressTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "orders");
				requestParams.addBodyParameter("a", "setDefault");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("orders", "setDefault");
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
							//JSONArray array=jsonobject.getJSONArray("result");
							if(jsonobject.get("error").equals("0")){
								ToastUtil.showToast(AddressActivity.this, jsonobject.getString("msg"));
							}else{
								Toast.makeText(AddressActivity.this, jsonobject.getString("msg"), 1).show();
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
	
	
	class DelAddressTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "orders");
				requestParams.addBodyParameter("a", "delReceAddress");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", del_id);
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("orders", "delReceAddress");
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
							//JSONArray array=jsonobject.getJSONArray("result");
							if(jsonobject.get("error").equals("0")){
								ToastUtil.showToast(AddressActivity.this, jsonobject.getString("msg"));
							}else{
								Toast.makeText(AddressActivity.this, jsonobject.getString("msg"), 1).show();
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
	class AddressReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals("address")){
				if(list!=null){
					list.clear();
				}
				new AddressTask().execute(Consts.URL);
			}
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
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
