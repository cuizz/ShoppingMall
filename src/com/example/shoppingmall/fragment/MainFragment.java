package com.example.shoppingmall.fragment;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ChangeCityActivity;
import com.example.shoppingmall.activity.GoodsActivity;
import com.example.shoppingmall.activity.ProductsInfoActivity;
import com.example.shoppingmall.activity.ShopActivity;
import com.example.shoppingmall.activity.WomenActivity;
import com.example.shoppingmall.customview.AbSlidingPlayView1;
import com.example.shoppingmall.entity.ZhuYeBean;
import com.example.zhuye.MyAdapter;


/**
 * @author wanan
 *
 */
public class MainFragment extends Fragment{
	
	//ͼƬ�͵���������
	public static String title[]=new String[]{"����1","����1","����1","����2","����2","����2","����3","����3","����3","����4","����4","����4","����5","����5","����5"};
	
	public static int image[] = new int[]{R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06};

	private LinearLayout.LayoutParams linearParams, imageParams,linearParams2;
	LinearLayout linearL,linearL2;
	//ImageView iv2,iv3,iv4,iv5,iv6,iv7;
	private AbSlidingPlayView1 viewPager;
	private ExpandableListView expandlistview;
	private ImageView iv;
	private List<ZhuYeBean> mData;
	private ScrollView mScrollView;
	
	private ArrayList<View> allListView;

	private int[] resId = {R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };

	private LocationClient mLocationClient;

	private MyLocationListener mMyLocationListener;
	private ImageView womenimageview;
	private TextView changecityTV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home, null);
		//ListView listview = (ListView) view.findViewById(R.id.listview01);
		mScrollView = (ScrollView) view.findViewById(R.id.scrollView2);
		mScrollView.smoothScrollTo(0,0);
		//iv=(ImageView) view.findViewById(R.id.shopimage);
		//Intent intent=new Intent(getActivity(),ProductsInfoActivity.class);
		//startActivity(intent);
		
		RecyclerView home_rv = (RecyclerView) view.findViewById(R.id.home_rv01);
	    
		initData();
	    final MyAdapter adapter = new MyAdapter(getActivity(),mData);
	    home_rv.setAdapter(adapter);

	    final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
	 
	    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
	      @Override
	      public int getSpanSize(int position) {
	    	  if(adapter.isHeader(position)){
	    		  return 2;
	    	  }else{
	    		  return 1;
	    	  }
	    	  
	      }
	    });
	 
	    home_rv.setLayoutManager(layoutManager);
		
	    
		linearL = (LinearLayout) view.findViewById(R.id.linearl);
		linearL2 = (LinearLayout) view.findViewById(R.id.linearl2);
		changecityTV=(TextView) view.findViewById(R.id.earatextView);
		changecityTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),ChangeCityActivity.class);
				startActivityForResult(intent, 100);
			}
		});
		womenimageview=(ImageView) view.findViewById(R.id.womenimageview);
		womenimageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),WomenActivity.class);
				startActivity(intent);
			}
		});
		initData();
		initView(view) ;
		
		//百度地图定位
				mLocationClient = new LocationClient(getActivity());

				//latitude=applic.CurrentLatitude;
				//longitude=applic.CurrentLongitude;
				
				//nearBy = new PostForNearBy(getActivity(), listview,latitude,longitude);
				//PostForRecommendShops recomdShop = new PostForRecommendShops(getActivity(),
					//	"3", "1", gridViewShops, mImageLoader, options,latitude,longitude);
				initLocation();
				mMyLocationListener = new MyLocationListener();
				mLocationClient.registerLocationListener(mMyLocationListener);
				mLocationClient.start();// 定位SDK
				 //start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
				mLocationClient.requestLocation();
		
		
		return view;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 100:
			if(resultCode==11){
				changecityTV.setText(data.getStringExtra("city"));
			}
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
				//locateProcess = 3;// 定位失败...
				return;
			}
			double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		//Log.i("", String.valueOf(latitude));
		//定位到的城市
		String city=location.getCity();
		//	postfor();
		}
	}
	
	/**
	 * @param activity
	 * @return ��ȡ��Ļ�Ŀ��
	 */
	private Dimension getAreaOne(Activity activity){  
        Dimension dimen = new Dimension();  
        Display disp = activity.getWindowManager().getDefaultDisplay();  
        Point outP = new Point();  
       // disp.getSize(outP);
        dimen.mWidth = outP.x / 4 ;
        dimen.imageWidth = outP.x / 6;
        dimen.mHeight = outP.y;  
        return dimen;  
    }  
	
	/**
	 * @param activity
	 * @return ��ȡ����������
	 */
	private Dimension getAreaThree(Activity activity){  
        Dimension dimen = new Dimension();  
     // �û���������   
        Rect outRect = new Rect();  
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);  
        dimen.linearWidth = outRect.width() / 2 ;  
        dimen.mHeight = outRect.height();  
        // end  
        return dimen;  
    }  
	
	private class Dimension {  
	    public int mWidth, imageWidth,linearWidth;  
	    public int mHeight ;  
	    public Dimension(){}  
	}  
	
	private void initView(View view) {
		
		//�ֲ�
		viewPager = (AbSlidingPlayView1) view.findViewById(R.id.viewPager_menu2);
		//���ò��ŷ�ʽΪ˳�򲥷�
		viewPager.setPlayType(1);
		//���ò��ż��ʱ��
		viewPager.setSleepTime(3000);

		initViewPager();
	}

	private void initViewPager() {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		for (int i = 0; i < resId.length; i++) {
			//����ViewPager�Ĳ���
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			imageView.setImageResource(resId[i]);
			allListView.add(view);
		}
		
		
		viewPager.addViews(allListView);
		//��ʼ�ֲ�
		viewPager.startPlay();
	}

	 private void initData(){
			mData = new ArrayList<ZhuYeBean>();
			ZhuYeBean bean = new ZhuYeBean();
			for(int i=0;i<15;i++){
				bean = new ZhuYeBean();
				bean.setShopName(title[i]);
				bean.setGoodsImage(image[i]);
				mData.add(bean);
			}
	    }
	 @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();
	}
}

