package com.example.shop;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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


import com.example.shoppingmall.R;

/**
 * 店铺详情
 * 
 * 需求：TabLayout的简单使用
 *  确定布局---找到控件--设置adapter--
 *  1 initView()--
 *  2 initAdapter
 *  3 initTablayout
 *  4 initViewpager*/
public class MainActivity5 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> tabs;
    private List<Fragment> fragments;
    private MyAdapter adapter;
    private TextView textView;
    //about data
    private String strResult = null;
	private String result;
	private List<NameValuePair> params;
	private String myurl = "http://192.168.1.115/liangpin/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.shop_toolbar);
        textView = (TextView) findViewById(R.id.shopname);
        
        	//get system time
      		Calendar cl = Calendar.getInstance();
      		long milliSecond = cl.getTimeInMillis();
      		//long milliSecond = 1470360989;
      		final String miSecond = Long.toString(milliSecond);
      		String password = "getProductListproduct";
      		String token = "liangpin";
      		String Md5Password = Md5.getMD5Str(password + milliSecond + token);
      		
      		String sign = Md5.getMD5Str(Md5Password);
      		String shop_id = "1";
            String city_id = "370100";
            String order = "";
      		String num = "1";
            String page = "1";
            String openid = "1";
            String a = "getProductList";
            String c = "product";
              
            //textView.setText(sign);
            
      		params=new ArrayList<NameValuePair>();
      		params.add(new BasicNameValuePair("a", a));
      		params.add(new BasicNameValuePair("c", c));
      		params.add(new BasicNameValuePair("param", shop_id + city_id + num + page));
      		params.add(new BasicNameValuePair("timesnamp", miSecond));
      		params.add(new BasicNameValuePair("openid", openid));
      		params.add(new BasicNameValuePair("sign", sign));
      		new Thread(getJson).start();
        
        initView();
        initData();
        initAdapter();
        initTabLayout();
        initViewPager();
    }

	private Runnable getJson=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try
			{
				result=GetJson(myurl, params);
				handler.sendEmptyMessage(0x00);
			}
			catch(Exception e)
			{
				handler.sendEmptyMessage(0x01);
			}
		}
	};
	
	Handler handler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg){
			if(msg.what==0x00)
			{
				//textview.setText(result);
				Log.v("PostGetJson",""+result);
			}
			else if(msg.what==0x01)
			{
				Toast.makeText(getApplicationContext(), "��ȡʧ��", Toast.LENGTH_LONG).show();
			}
		}
	};
    
	private String GetJson(String url, List<NameValuePair> params) {
		HttpPost httpRequest = new HttpPost(url);
		
		HttpParams httpParameters1 = new BasicHttpParams();
		HttpConnectionParams
		.setConnectionTimeout(httpParameters1, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParameters1, 10 * 1000);

		try {
			httpRequest.setParams(httpParameters1);
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) 
			{

				strResult = EntityUtils.toString(httpResponse.getEntity());
				Log.d("data", strResult);
				//parseJson(strResult);
				//textView.setText(strResult);
			} 
			else 
			{

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	
    
    
    private void initViewPager() {
    	
        /**注意这2行代码的顺序：viewpaper要先设置adapter，才可以让 tablayout绑定
         *  否则报错：viewpager没有setAdapter()*/
    	
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    private void initData() {
        tabs = new ArrayList<>();
        tabs.add("店铺首页");
        tabs.add("全部宝贝");
        tabs.add("新品上架");

        fragments = new ArrayList<>();
        fragments.add(new LittleShopFragment());
        fragments.add(new FragFun());
        fragments.add(new FragSport());
    }

    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(0)));/**注意创建TAB对象：tabLayout.newTab()*/
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(2)));
    }

    private void initAdapter() {
        adapter = new MyAdapter(getSupportFragmentManager(),tabs,fragments);
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_01);/**如果不提示导包可以Clean Project*/
        viewPager = (ViewPager) findViewById(R.id.viewPager_01);
    }
}
