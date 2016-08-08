package com.example.shoppingmall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import android.app.Application;
import android.content.res.AssetManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class TApplication extends Application{
	public String CurrentCity = null;
	public String CurrentCityCode = null;
	public String CurrentLongitude = null;
	public String CurrentLatitude = null;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public JSONObject jsonS;

	@Override
	public void onCreate() {
		super.onCreate();
		readerText();
		//instance = this;
		
	}


	public void readerText() {
		jsonS = new JSONObject();
		try {
			String encoding = "UTF-8";
			AssetManager am = getAssets();
			InputStream is = am.open("123456.txt");
			
			InputStreamReader read = new InputStreamReader(is, encoding);// ���ǵ������ʽ
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				String string1 = lineTxt.replaceAll("��", "");
				String string2 = string1.replaceAll(" ", "");
				jsonS.put(string2.substring(6, string2.length()),
						string2.substring(0, 6));
			}
			read.close();
			is.close();
			bufferedReader.close();
		} catch (Exception e) {
			//System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		mLocationClient = new LocationClient(this.getApplicationContext());
		initLocation();
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();// ��λSDK
								// start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
		mLocationClient.requestLocation();

	}

	/**
	 * ʵ��ʵʱλ�ûص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			CurrentLongitude = location.getLongitude() + "";
			CurrentLatitude = location.getLatitude() + "";
			CurrentCity = location.getCity();
			// ("��γ��"+CurrentLongitude+"---"+CurrentLatitude) ;
			CurrentCityCode = jsonS.optString(CurrentCity);
		}
	}

	// �õ� �Ǹ����� �Ĵ���

	public String getCurrentCityCode(String args) {
		return jsonS.optString(args);
	}

	public void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("gcjo2");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ������ϵ��
		option.setScanSpan(0);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(false);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶�����̣������Ƿ���stop��ʱ��ɱ�������̣�Ĭ�ϲ�ɱ��
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

}
