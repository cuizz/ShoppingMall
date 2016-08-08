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
import com.example.shoppingmall.activity.AddressActivity.DelAddressTask;
import com.example.shoppingmall.activity.AddressActivity.ViewHolder;
import com.example.shoppingmall.entity.AddressEntity;
import com.example.shoppingmall.entity.CityEntity;
import com.example.shoppingmall.entity.CountryEntity;
import com.example.shoppingmall.entity.ProvinceEntity;
import com.example.shoppingmall.utils.Consts;
import com.example.shoppingmall.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewAddressActivity extends Activity implements OnClickListener{
	private ImageView backimageview;
	private RelativeLayout choice;
	private View view2;
	private PopupWindow orderhighWindow;
	private TextView xianshi,dismiss,sure,address,sureTV,cityTv,countryTv,choiceTV;
	private EditText xiangxi,name,phone;
	private String cartOrder;
	private String cartTime;
	private String province_id,province_name,country_name;
	private String city_id,city_name;
	private String province_ids,country_id;
	private ProvinceAdapter adapter;
	private CityAdapter adapters;
	private CountryAdapter adapterss;
	private ListView listview,listviews,countrylistview;
	private List<ProvinceEntity>list=new ArrayList<ProvinceEntity>();
	private List<CityEntity>city=new ArrayList<CityEntity>();
	private List<CountryEntity>country=new ArrayList<CountryEntity>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address_new);
		intviews();
		new ProvinceTask().execute(Consts.URL);
	}
	private void intviews() {
		// TODO Auto-generated method stub
		backimageview=(ImageView) findViewById(R.id.backImage);
		backimageview.setOnClickListener(this);
		choice=(RelativeLayout) findViewById(R.id.choice);
		choice.setOnClickListener(this);
		xiangxi=(EditText) findViewById(R.id.xiangxiET);
		sureTV=(TextView) findViewById(R.id.suretextView);
		sureTV.setOnClickListener(this);
		name=(EditText) findViewById(R.id.name);
		phone=(EditText) findViewById(R.id.phone);
		choiceTV=(TextView) findViewById(R.id.choicetextview);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.choice:
			showGeniusOrderWindow(v);
			if(adapter==null){
				adapter=new ProvinceAdapter(list, NewAddressActivity.this);
				listview.setAdapter(adapter);
				}else{
					adapter.notifyDataSetChanged();
				}
			break;
		case R.id.dismiss:
			orderhighWindow.dismiss();
			break;
		case R.id.sure:
			choiceTV.setText(province_name+city_name+country_name);
			orderhighWindow.dismiss();
			break;
		case R.id.suretextView:
			new AddAddressTask().execute(Consts.URL);
			finish();
			break;
		}
	}
	private void showGeniusOrderWindow(View parent) {
		WindowManager windowManager = (WindowManager) 
				getSystemService(Context.WINDOW_SERVICE);
		if (orderhighWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view2 = layoutInflater.inflate(R.layout.newaddresspop, null);
			view2.getBackground().setAlpha(150);
			xianshi=(TextView) view2.findViewById(R.id.gaodi);
			cityTv=(TextView) view2.findViewById(R.id.citytextVIew);
			countryTv=(TextView) view2.findViewById(R.id.countrytextview);
			address=(TextView) view2.findViewById(R.id.gaodi);
			dismiss=(TextView) view2.findViewById(R.id.dismiss);
			dismiss.setOnClickListener(this);
			sure=(TextView) view2.findViewById(R.id.sure);
			sure.setOnClickListener(this);
			//为listview设置动画
			Animation animation = (Animation) AnimationUtils.loadAnimation(
					this, R.anim.list_anim);
			LayoutAnimationController lac = new LayoutAnimationController(animation);
			lac.setDelay(0.4f);  //设置动画间隔时间
			lac.setOrder(LayoutAnimationController.ORDER_NORMAL); //设置列表的显示顺序
			listview=(ListView) view2.findViewById(R.id.provincelistview);
			listview.setLayoutAnimation(lac);
			listview.setVerticalScrollBarEnabled(false);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					province_id=list.get(position).getProvinceid();
					province_name=list.get(position).getProvince();
					xianshi.setText(list.get(position).getProvince());
					cityTv.setText("");
					countryTv.setText("");
					if(city!=null){
						city.clear();
					}
					if(country!=null){
						country.clear();
					}
					new CityTask().execute(Consts.URL);
				}
			});
			listviews=(ListView) view2.findViewById(R.id.citylistview);
			listviews.setVerticalScrollBarEnabled(false);
			listviews.setLayoutAnimation(lac);
			listviews.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String citys=city.get(position).getCity();
					String province=xianshi.getText().toString();
					city_name=city.get(position).getCity();
					city_id=city.get(position).getCityid();
					province_ids=city.get(position).getFatherid();
					cityTv.setText(city.get(position).getCity());
					countryTv.setText("");
					//address.setText(province+citys);
					if(country!=null){
						country.clear();
					}
					new CountryTask().execute(Consts.URL);
				}
			});
			countrylistview=(ListView) view2.findViewById(R.id.countrylistview);
			countrylistview.setLayoutAnimation(lac);
			countrylistview.setVerticalScrollBarEnabled(false);
			countrylistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					countryTv.setText(country.get(position).getCounty());
					country_name=country.get(position).getCounty();
					country_id=country.get(position).getCountyid();
				}
			});
			orderhighWindow = new PopupWindow(view2, windowManager
					.getDefaultDisplay().getWidth(), LayoutParams.MATCH_PARENT);
		}
		// 使其聚集
		orderhighWindow.setFocusable(true);
		// 设置允许在外点击消失
		orderhighWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		orderhighWindow.setBackgroundDrawable(new BitmapDrawable());
		orderhighWindow.setAnimationStyle(R.anim.push_out);
		orderhighWindow.showAsDropDown(parent, 0, 0);
	}
	class AddAddressTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "orders");
				requestParams.addBodyParameter("a", "addAddress");
				JSONObject object=new JSONObject();
				object.put("user_id","45");
				object.put("name",name.getText().toString());
				object.put("phone",phone.getText().toString());
				object.put("province_id",province_ids);
				object.put("province",province_name);
				object.put("city_id",city_id);
				object.put("city",city_name);
				object.put("county_id",country_id);
				object.put("county",country_name);
				object.put("address",province_name+city_name+country_name+xiangxi.getText().toString());
				object.put("state","1");
				requestParams.addBodyParameter("param", object.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("orders", "addAddress");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
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
							if(jsonobject.get("error").equals("0")){
								ToastUtil.showToast(NewAddressActivity.this, "添加成功");
								Intent intent=new Intent();
								intent.setAction("address");
								NewAddressActivity.this.sendBroadcast(intent);
							}else{
								Toast.makeText(NewAddressActivity.this, "添加失败", 1).show();
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
	class ProvinceTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "area");
				requestParams.addBodyParameter("a", "province");
				JSONArray array=new JSONArray();
				requestParams.addBodyParameter("param", array.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("area", "province");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
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
									JSONObject object=array.getJSONObject(i);
									ProvinceEntity entity=new ProvinceEntity();
									entity.setProvince(object.getString("province"));
									entity.setId(object.getString("id"));
									entity.setProvinceid(object.getString("provinceid"));
									list.add(entity);
								}
							}else{
								Toast.makeText(NewAddressActivity.this, "请求服务器失败", 1).show();
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
	class CityTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "area");
				requestParams.addBodyParameter("a", "city");
				JSONObject object=new JSONObject();
				object.put("provinceid", province_id);
				requestParams.addBodyParameter("param", object.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("area", "city");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
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
									JSONObject object=array.getJSONObject(i);
									CityEntity entity=new CityEntity();
									entity.setCity(object.getString("city"));
									entity.setCityid(object.getString("cityid"));
									entity.setFatherid(object.getString("fatherid"));
									city.add(entity);
								}
								if(adapters==null){
									adapters=new CityAdapter(city, NewAddressActivity.this);
									listviews.setAdapter(adapters);
								}else{
									adapters.notifyDataSetChanged();
								}

							}else{
								Toast.makeText(NewAddressActivity.this, "请求服务器失败", 1).show();
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
	
	class CountryTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "area");
				requestParams.addBodyParameter("a", "county");
				JSONObject object=new JSONObject();
				object.put("cityid", city_id);
				requestParams.addBodyParameter("param", object.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("area", "county");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
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
									JSONObject object=array.getJSONObject(i);
									CountryEntity entity=new CountryEntity();
									entity.setCounty(object.getString("county"));
									entity.setCountyid(object.getString("countyid"));
									entity.setFatherid(object.getString("fatherid"));
									country.add(entity);
								}
								if(adapterss==null){
									adapterss=new CountryAdapter(country, NewAddressActivity.this);
									countrylistview.setAdapter(adapterss);
								}else{
									adapterss.notifyDataSetChanged();
								}

							}else{
								Toast.makeText(NewAddressActivity.this, "请求服务器失败", 1).show();
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
	class ProvinceAdapter extends BaseAdapter{
		List<ProvinceEntity>lists=new ArrayList<ProvinceEntity>();
		Context context;

		public ProvinceAdapter(List<ProvinceEntity>list,Context context) {
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
				convertView=View.inflate(NewAddressActivity.this, R.layout.province_adapter, null);
				holder.nametv=(TextView) convertView.findViewById(R.id.nametextview);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.nametv.setText(lists.get(position).getProvince());
			return convertView;
		}

	}
	class ViewHolder{
		TextView nametv;
	}
	class CityAdapter extends BaseAdapter{
		List<CityEntity>lists=new ArrayList<CityEntity>();
		Context context;

		public CityAdapter(List<CityEntity>list,Context context) {
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
			ViewHolder1 holder=null;
			if(convertView==null){
				holder=new ViewHolder1();
				convertView=View.inflate(NewAddressActivity.this, R.layout.province_adapter, null);
				holder.nametv=(TextView) convertView.findViewById(R.id.nametextview);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder1) convertView.getTag();
			}
			holder.nametv.setText(lists.get(position).getCity());
			return convertView;
		}

	}
	class ViewHolder1{
		TextView nametv;
	}
	
	class CountryAdapter extends BaseAdapter{
		List<CountryEntity>lists=new ArrayList<CountryEntity>();
		Context context;

		public CountryAdapter(List<CountryEntity>list,Context context) {
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
			ViewHolder2 holder=null;
			if(convertView==null){
				holder=new ViewHolder2();
				convertView=View.inflate(NewAddressActivity.this, R.layout.province_adapter, null);
				holder.nametv=(TextView) convertView.findViewById(R.id.nametextview);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder2) convertView.getTag();
			}
			holder.nametv.setText(lists.get(position).getCounty());
			return convertView;
		}

	}
	class ViewHolder2{
		TextView nametv;
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
