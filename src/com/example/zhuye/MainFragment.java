package com.example.zhuye;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.customview.AbSlidingPlayView1;
import com.example.shoppingmall.entity.ZhuYeBean;


/**
 * recyclerView首页
 * 
 */
public class MainFragment extends Fragment{
	
	public static String title[]=new String[]{"����1","����1","����1","����2","����2","����2","����3","����3","����3","����4","����4","����4","����5","����5","����5"};
	
	public static int image[] = new int[]{R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06};

	private LinearLayout.LayoutParams linearParams, imageParams,linearParams2;
	private LinearLayout linearL,linearL2;
	private ImageView iv2,iv3,iv4,iv5,iv6,iv7;
	/**轮播自定义view*/
	private AbSlidingPlayView1 viewPager;
	
	/**Bean数据*/
	private List<ZhuYeBean> mData;
	//
	private ScrollView mScrollView;
	/** */
	private ArrayList<View> allListView;
	/**轮播资源*/
	private int[] resId = {R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_01, null);
		//ListView listview = (ListView) view.findViewById(R.id.listview01);
		mScrollView = (ScrollView) view.findViewById(R.id.scrollView2);
		mScrollView.smoothScrollTo(0,0);
		
		RecyclerView home_rv = (RecyclerView) view.findViewById(R.id.home_rv);
	    
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
		
		iv2 = (ImageView) view.findViewById(R.id.iv2);
		iv3 = (ImageView) view.findViewById(R.id.iv3);
		iv4 = (ImageView) view.findViewById(R.id.iv4);
		iv5 = (ImageView) view.findViewById(R.id.iv5);
		iv6 = (ImageView) view.findViewById(R.id.iv6);
		iv7 = (ImageView) view.findViewById(R.id.iv7);
		
		linearParams = (LinearLayout.LayoutParams) linearL.getLayoutParams();
		linearParams = (LinearLayout.LayoutParams) linearL2.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv2.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv3.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv4.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv5.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv6.getLayoutParams();
		imageParams = (LinearLayout.LayoutParams) iv7.getLayoutParams();
		
		Dimension dimen = getAreaOne(getActivity()); 
		
		
		imageParams.height = dimen.imageWidth;
		imageParams.width = dimen.imageWidth;
		linearParams.height =dimen.mWidth;
		
		linearL.setLayoutParams(linearParams);
		linearL2.setLayoutParams(linearParams);
		iv2.setLayoutParams(imageParams);
		iv3.setLayoutParams(imageParams);
		iv4.setLayoutParams(imageParams);
		iv5.setLayoutParams(imageParams);
		iv6.setLayoutParams(imageParams);
		iv7.setLayoutParams(imageParams);
		
		
		initData();
		initView(view) ;
		return view;
	}
	
	
	
	/**
	 * @param activity
	 * @return ��ȡ��Ļ�Ŀ��
	 */
	@SuppressLint("NewApi")
	private Dimension getAreaOne(Activity activity){  
        Dimension dimen = new Dimension();  
        Display disp = activity.getWindowManager().getDefaultDisplay();  
        Point outP = new Point();  
        disp.getSize(outP);
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
}

