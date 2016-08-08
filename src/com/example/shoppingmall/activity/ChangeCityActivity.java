package com.example.shoppingmall.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.shoppingmall.R;
import com.example.shoppingmall.customview.Delateslide;
import com.example.shoppingmall.customview.MyListView;
import com.example.shoppingmall.entity.City;
import com.example.shoppingmall.utils.Consts;
import com.example.shoppingmall.utils.GetData;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeCityActivity extends Activity implements OnClickListener,OnScrollListener{
	private TextView changeCity_reback,dingweicityTV;
	private Delateslide gridview;
	private ListView slListView,resultlistview;
	private MyListView listview;
	private EditText search;
	private String cartOrder;
	private String cartTime;
	private ScrollView sc;
	private List<City>list=new ArrayList<City>();
	private List<City>allcitylist=new ArrayList<City>();
	private List<String>zimulist=new ArrayList<String>();
	private List<String>result=new ArrayList<String>();
	private MyAdapter1 adapter;
	private ArrayAdapter<String>adapters;
	private LocationClient mLocationClient;
	private double latitude;
	private double longitude;
	private int locateProcess;
	private String currentCity;
	private MyLocationListener mMyLocationListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.changecities);
		intviews();
		adalistensers();
		new HotcityTask().execute(Consts.URL);
		new AllcityTask().execute(Consts.URL);
		// 这个是 百度定位sdk 的 工具 类
		// com.baidu.location.LocationClient.LocationClient(Context arg0)
		mLocationClient = new LocationClient(this);
		initLocation();
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();// 定位SDK
		// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.requestLocation();
		adapters=new ArrayAdapter<String>(ChangeCityActivity.this, android.R.layout.simple_list_item_1, result);
		resultlistview.setAdapter(adapters);
	}
	private void adalistensers() {
		// TODO Auto-generated method stub
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(final CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				resultlistview.setVisibility(View.VISIBLE);
				sc.setVisibility(View.GONE);
				result.clear();
				String[] vals;
				try {
					HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
					format.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
					format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
					for(City city:allcitylist){
						String name=city.getName();
						StringBuffer buffer=new StringBuffer();
						StringBuffer buffer1=new StringBuffer();
						for(int i = 0 ; i < name.length() ; i++)  
						{  
							char c = name.charAt(i);         
							vals = PinyinHelper.toHanyuPinyinStringArray(c, format);
							char cc=vals[0].charAt(0);
							buffer.append(cc);
							if(s.toString().equals("")){
								result.clear();
								adapters.notifyDataSetChanged();
								resultlistview.setVisibility(View.GONE);
								sc.setVisibility(View.VISIBLE);
								return;
							}else{
								if(i<s.toString().length()){		
									char ccc=s.toString().toUpperCase().charAt(i);
									buffer1.append(ccc);
									if(buffer.toString().contains(buffer1)||buffer==buffer1){
										result.add(name);		
									}
								}
							}
						}
						if(name.contains(s)){
							result.add(name);
						}
					}

					adapters.notifyDataSetChanged();
					// if(result==null){
					//result.add("没有找到相关城市");
					// }

				} catch (Exception e) {
					// TODO Auto-generated catch block
					//BadHanyuPinyinOutputFormatCombination
					e.printStackTrace();
				}  
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		slListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//1.根据导航条的位置获得联系人的section
				String key=getContactKeys().get(position);//"A","B",C,D,...
				int section=key.charAt(0);//section
				//2.根据section获得联系人列表的初始位置
				int pos=adapter.getPositionForSection(section);
				//3.定位到联系人的位置
				listview.setSelectionFromTop(pos, 0);//y=0为偏移量
			}
		});
		resultlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = ChangeCityActivity.this.getIntent();
				intent.putExtra("city", result.get(position));
				setResult(11, intent);
				ChangeCityActivity.this.finish();
			}
		});
	}
	private void intviews() {
		// TODO Auto-generated method stub
		changeCity_reback=(TextView) findViewById(R.id.changeCity_reback);
		changeCity_reback.setOnClickListener(this);
		dingweicityTV=(TextView) findViewById(R.id.dingweicityTV);
		gridview=(Delateslide) findViewById(R.id.hotcityGridView);
		listview=(MyListView) findViewById(R.id.allcityListView);
		slListView=(ListView) findViewById(R.id.slidinglistview);
		search=(EditText) findViewById(R.id.search);
		resultlistview=(ListView) findViewById(R.id.resultlistview);
		sc=(ScrollView) findViewById(R.id.sc);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.changeCity_reback:
			finish();
			break;
		}
	}
	/**
	 * 定位一系列的 要求
	 */
	public void initLocation() {
		// com.baidu.location.LocationClient.LocationClient(Context arg0)
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(false);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps 可以使用 gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location.getCity() == null) {
				locateProcess = 3;// 定位失败...
				dingweicityTV.setText("定位失败");
				return;
			}
			latitude=location.getLatitude();
			longitude=location.getLongitude();

			locateProcess = 2;
			currentCity = location.getCity();
			dingweicityTV.setText(currentCity);
		}
	}


	class HotcityTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "area");
				requestParams.addBodyParameter("a", "hot_city");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("num", "18");
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("area", "hot_city");
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
							if(jsonobject.get("error").equals("0")){
								for(int i=0;i<jsonobject.length()-2;i++){
									JSONObject object=jsonobject.getJSONObject(String.valueOf(i));
									City city=new City();
									city.setName(object.getString("city"));
									list.add(city);
								}
								gridview.setAdapter(new MyAdapter(list, ChangeCityActivity.this));
								gridview.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										//TextView textView=(TextView) view;
										Intent intent = ChangeCityActivity.this.getIntent();
										intent.putExtra("city", list.get(position)
												.getName());
										setResult(11, intent);
										ChangeCityActivity.this.finish();
									}
								});
							}else{
								Toast.makeText(ChangeCityActivity.this, "请求服务器失败", 1).show();
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
	class AllcityTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "area");
				requestParams.addBodyParameter("a", "citys");
				//JSONObject jsonObject=new JSONObject();
				//jsonObject.put("num", "18");
				JSONArray jsonObject=new JSONArray();
				requestParams.addBodyParameter("param", jsonObject.toString());
				//requestParams.addBodyParameter("timesnamp",String.valueOf(System.currentTimeMillis()));
				cartTime=new Date().getTime()+"";
				getCartInfo("area", "citys");
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
							zimulist=GetData.getdata();
							if(jsonobject.get("error").equals("0")){
								for(int i=0;i<22;i++){
									//JSONObject object=jsonobject.getJSONObject(zimulist.get(i));

									//if(jsonobject.getJSONObject(zimulist.get(i))==null){	
									//}else{
									JSONArray array=jsonobject.getJSONArray(zimulist.get(i));
									for(int j=0;j<array.length();j++){
										JSONObject object=array.getJSONObject(j);
										City city=new City();
										city.setName(object.getString("city"));
										city.setPinyi(object.getString("type"));
										allcitylist.add(city);

										//}	
									}
								}
								adapter=new MyAdapter1(allcitylist, ChangeCityActivity.this);
								listview.setAdapter(adapter);
								listview.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										//TextView textView=(TextView) view;
										Intent intent = ChangeCityActivity.this.getIntent();
										intent.putExtra("city", allcitylist.get(position)
												.getName());
										setResult(11, intent);
										ChangeCityActivity.this.finish();
									}
								});
								slListView.setAdapter(new SliingAdapter(getContactKeys(), ChangeCityActivity.this));
							}else{
								Toast.makeText(ChangeCityActivity.this, "请求服务器失败", 1).show();
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
	class MyAdapter extends BaseAdapter{
		List<City>lists=new ArrayList<City>();
		Context context;
		int positions;

		public MyAdapter(List<City>list,Context context) {
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(ChangeCityActivity.this, R.layout.total_item, null);
				holder.tv=(TextView) convertView.findViewById(R.id.locateHint);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv.setText(lists.get(position).getName());
			return convertView;
		}

	}
	class ViewHolder{
		TextView tv;
	}
	class MyAdapter1 extends BaseAdapter implements SectionIndexer{
		List<City>lists=new ArrayList<City>();
		Context context;
		int positions;

		public MyAdapter1(List<City>list,Context context) {
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder1 holder=null;
			if(convertView==null){
				holder=new ViewHolder1();
				convertView=View.inflate(ChangeCityActivity.this, R.layout.total_item1, null);
				holder.tv=(TextView) convertView.findViewById(R.id.locateHint);
				holder.ktv=(TextView) convertView.findViewById(R.id.keytv);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder1) convertView.getTag();
			}
			holder.tv.setText(lists.get(position).getName());
			holder.ktv.setText(lists.get(position).getPinyi());
			//根据位置判定是否隐藏或显示item中key
			int sec=getSectionForPosition(position);
			if(position==getPositionForSection(sec)){
				//显示
				holder.ktv.setVisibility(View.VISIBLE);
			}else{
				//隐藏
				holder.ktv.setVisibility(View.GONE);
			}
			return convertView;
		}
		@Override
		public Object[] getSections() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public int getPositionForSection(int sectionIndex) {
			//i代表所有位置(position)
			for(int i=0;i<lists.size();i++){
				//根据位置获得section(章节)
				//此值为名字的第一个字母的ascii码值
				int sec=getSectionForPosition(i);
				if(sec==sectionIndex){
					return i;//i为section的初始位置
				}
			}
			return 0;
		}
		@Override
		public int getSectionForPosition(int position) {
			return lists.get(position)
					.getPinyi().charAt(0);
		}

	}
	class ViewHolder1{
		TextView tv;
		TextView ktv;
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
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		//根据可见位置获得联系人列表的section
		int n=adapter.getSectionForPosition(firstVisibleItem);
		// Log.i("TAG", "n="+n);//字符key的ASCII码值
		String section=String.valueOf((char)n);

		List<String> keys=getContactKeys();      
		for(int i=0;i<keys.size();i++){//"A","B","C",....
			//获得textview(数据是显示在了TextView上)
			TextView tv=(TextView)slListView.getChildAt(i);
			if(tv==null)break;
			//比较key与section
			if(tv.getText().toString().equals(section)){
				//设置字体颜色为红色  
				tv.setTextColor(Color.RED);
			}else{
				//设置字体颜色为黑色
				tv.setTextColor(Color.BLACK);
			}
		}
	}
	public  List<String> getContactKeys(){
		Set<String> keySet=
				new TreeSet<String>();
		for(City c:allcitylist){
			keySet.add(""+c.getPinyi().charAt(0));
		}
		return new ArrayList<String>(keySet);
	}
	class SliingAdapter extends BaseAdapter{
		List<String>lists=new ArrayList<String>();
		Context context;

		public SliingAdapter(List<String>list,Context context) {
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder2 holder=null;
			if(convertView==null){
				holder=new ViewHolder2();
				convertView=View.inflate(ChangeCityActivity.this, R.layout.total_item2, null);
				holder.tv=(TextView) convertView.findViewById(R.id.locateHint);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder2) convertView.getTag();
			}
			holder.tv.setText(lists.get(position));
			return convertView;
		}

	}
	class ViewHolder2{
		TextView tv;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();
	}
}
