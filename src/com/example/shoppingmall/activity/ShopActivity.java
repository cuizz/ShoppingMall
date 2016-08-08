package com.example.shoppingmall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * @author �������
 *  ����ҳ��
 */
public class ShopActivity extends Activity{
	
	private ListView listview;
	private MyAdapter adapter;
	private String shopName;
	private LinearLayout.LayoutParams linearParams;
	private PullToRefreshScrollView scollview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops);
		shopName = getIntent().getStringExtra("shopName");
		//Dimension dimen1 = getAreaOne(this);  
		initviews();
		addlisteners();
		listview=(ListView) findViewById(R.id.listview);
		
		linearParams = (LinearLayout.LayoutParams) listview.getLayoutParams(); // ��ȡ�ؼ���ǰ�Ĳ��ֲ���   
		//linearParams.height =dimen1.mHeight;// ���ÿؼ��߶�
		listview.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�   
		
		adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
	}

	private void initviews() {
		// TODO Auto-generated method stub
		scollview=(PullToRefreshScrollView) findViewById(R.id.scrollView1);
		scollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");   
		scollview.getLoadingLayoutProxy().setPullLabel("PULLLABLE");  
		scollview.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");  
		scollview.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");  
		scollview.setMode(Mode.PULL_FROM_START);
	}

	@Override  
	    public void onWindowFocusChanged(boolean hasFocus) {  
	        super.onWindowFocusChanged(hasFocus);  
	        if(hasFocus){  
	            Dimension dimen1 = getAreaOne(this);  
	            
	            linearParams.height =dimen1.mHeight; 
	        }  
	    }  
	
	private void addlisteners() {
		// TODO Auto-generated method stub
		scollview.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				
			}
		});
		
	}
	
	 @SuppressLint("NewApi")
	 private Dimension getAreaOne(Activity activity){  
	        Dimension dimen = new Dimension();  
	     // �û���������   
	        Rect outRect = new Rect();  
	        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);  
	        dimen.mWidth = outRect.width() ;  
	        dimen.mHeight = outRect.height();  
	        // end  
	        return dimen;  
	    }  
	
	 private class Dimension {  
		    public int mWidth ;  
		    public int mHeight ;  
		    public Dimension(){}  
		}  
	 
	 
	public class MyAdapter extends BaseAdapter {

		Context mcontext;
		LinearLayout layout = null;
		LayoutInflater inflater;
		final int VIEW_TYPE = 2; // �ܵ�item��ʽ��
		final int TYPE_0 = 0;
		final int TYPE_1 = 1;
		private String[] info = { "ʱ������찡��", "���ǻ�����", "���������ˣ�", "����������",
				"��Ҫ������ǲ��ܼ��棿","1","2","3","4","5","1","2","3","4","5" };
		
		
		public MyAdapter(Context context) {

			this.mcontext = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getItemViewType(int position) {  //item��ʽ�ķ������Ͱ�
			if (position == 0) {
				return TYPE_0;
			} else {
				return TYPE_1;
			}

		}

		@Override
		public int getViewTypeCount() {  //item��ʽ��������
			
			return VIEW_TYPE;
		}
		
		@Override
		public int getCount() {
			return info.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			ViewHolder1 holder1 = new ViewHolder1();
			
			int type=getItemViewType(position);  //��ȡ������ʽ��������
			if (convertView == null) {
	           switch(type){
	           case TYPE_0:
	        	   convertView=inflater.inflate(R.layout.dianpus, parent,false);
	        	   //holder.tv7 = (TextView) convertView.findViewById(R.id.tv7);
	        	   
	        	   convertView.setTag(holder);
	        	   
	        	   break;
	           case TYPE_1:
	        	   
	        	   convertView=inflater.inflate(R.layout.product, parent,false);
	        	   convertView.setTag(holder1);
	        	   break;
	        	  default:break;
	           }
			}else{
				 switch(type){
		           case TYPE_0:
		        	  holder=(ViewHolder) convertView.getTag();
		        	   break;
		           case TYPE_1:
		        	  holder1=(ViewHolder1) convertView.getTag();
		        	   break;
		           }
			}
			switch(type){
	        case TYPE_0:
	     	   //holder.tv7.setText(shopName);
	     	   break;
	        case TYPE_1:
	     	   
	     	
	     	   break;
	        }
			

			return convertView;
		}

		class ViewHolder {
			public TextView tv7;
			private TextView txt_name, txt_con;
			private ImageView img_header;
		}

		class ViewHolder1 {
			@SuppressWarnings("unused")
			private TextView txt_name1, txt_con1;
			private ImageView img1, img2, img3, img4, img5;
		}

	}

}
