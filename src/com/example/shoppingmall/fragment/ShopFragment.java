package com.example.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ShopActivity;
import com.example.shoppingmall.adapter.ShopAdapter;
import com.example.shoppingmall.entity.ShopEntity;
import com.example.shoppingmall.utils.PostToShopList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ShopFragment extends Fragment implements OnClickListener{
	private ListView listview;
	private List<ShopEntity>list=new ArrayList<ShopEntity>();
	private PostToShopList shoplist;
	private ShopAdapter adapter;
	private TextView paixu;
	private View view;
	private View view2;
	private LinearLayout orderuseless,orderhigh,orderlow,zhinengpaixu;
	private PopupWindow orderhighWindow;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = LayoutInflater.from(getActivity()).inflate(R.layout.shopfragment, null);
		initviews();
		//	listview.setMode(Mode.PULL_UP_TO_REFRESH);
		shoplist=new PostToShopList(getActivity(), "10", mhandler, listview);
		shoplist.addListData();
		/*listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy()
				.setLastUpdatedLabel(label);
				list.clear();
				shoplist.removeListData();
				shoplist.addListData();
			}
		});*/
		return view;
	}
	private void initviews() {
		// TODO Auto-generated method stub
		listview = (ListView) view.findViewById(R.id.pull_refresh_listtt);
		paixu=(TextView) view.findViewById(R.id.paixuTV);
		zhinengpaixu=(LinearLayout) view.findViewById(R.id.coupon_geniusorder);
		zhinengpaixu.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.coupon_geniusorder:
			showGeniusOrderWindow(v);
			break;
		case R.id.orderuseless :
			paixu.setText("智能排序") ;
			TextView tv = (TextView) orderhigh.getChildAt(0);
			tv.setTextColor(Color.BLACK) ;
			orderhigh.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White));
			TextView tview = (TextView) orderlow.getChildAt(0) ;
			tview.setTextColor(Color.BLACK) ;
			orderlow.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White)) ;
			TextView vt = (TextView) orderuseless.getChildAt(0) ;
			vt.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			orderuseless.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_Gray_light)) ;
			list.clear();
			shoplist.removeListData();
			shoplist.addListData();
			orderhighWindow.dismiss(); 
			break ;

		case R.id.orderhigh:
			LinearLayout lineV = (LinearLayout) v ;
			TextView tvv = (TextView) lineV.getChildAt(0) ;
			tvv.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			orderhigh.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_Gray_light));
			paixu.setText("由高到低") ;
			paixu.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			TextView tvieww = (TextView) orderlow.getChildAt(0) ;
			tvieww.setTextColor(Color.BLACK) ;
			orderlow.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White)) ;
			TextView vtt = (TextView) orderuseless.getChildAt(0) ;
			vtt.setTextColor(Color.BLACK) ;
			orderuseless.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White)) ;
			list.clear();	
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			shoplist.removeListData();
			shoplist.setAttrs("asc");
			shoplist.addListData();
			 orderhighWindow.dismiss();
			break;
		case R.id.orderlow :
			paixu.setText("由低到高") ;
			paixu.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			TextView tsv = (TextView) orderhigh.getChildAt(0) ;
			tsv.setTextColor(Color.BLACK) ;
			orderhigh.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White)) ;
			TextView tviesw = (TextView) orderlow.getChildAt(0) ;
			orderlow.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_Gray_light)) ;
			tviesw.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			TextView vst = (TextView) orderuseless.getChildAt(0) ;
			vst.setTextColor(Color.BLACK) ;
			orderuseless.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_White)) ;
			list.clear();
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			shoplist.removeListData();
			shoplist.setAttrs("desc");
			shoplist.addListData();
			orderhighWindow.dismiss();
			break ;
		}
	}
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 11:
				try {
					JSONArray array=(JSONArray) msg.obj;
					for(int i=0;i<array.length();i++){
						JSONObject jsonObject=array.getJSONObject(i);
						ShopEntity entity=new ShopEntity();
						entity.setImg(jsonObject.getString("img"));
						entity.setId(jsonObject.getString("id"));
						entity.setName(jsonObject.getString("name"));
						entity.setCity(jsonObject.getString("city"));
						entity.setCountry(jsonObject.getString("country"));
						list.add(entity);
					}
					if(adapter==null){
						adapter=new ShopAdapter(getActivity(), list);
						listview.setAdapter(adapter);
					}else{
						adapter.notifyDataSetChanged();
					}
					listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(getActivity(),ShopActivity.class);
							getActivity().startActivity(intent);
						}
					});
					//listview.onRefreshComplete();
				} catch (Exception e) {
					// TODO Auto-generated catch block   14696862124973.jpg
					e.printStackTrace();
				}

				break;
			}
		};
	};
	
	private void showGeniusOrderWindow(View parent) {
		WindowManager windowManager = (WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE);
		if (orderhighWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view2 = layoutInflater.inflate(R.layout.groupgeniusorder, null);
			view2.getBackground().setAlpha(150);
	        orderuseless = (LinearLayout) view2.findViewById(R.id.orderuseless) ;
	        orderuseless.setOnClickListener(ShopFragment.this) ;
	        orderuseless.setBackgroundColor(ShopFragment.this.getResources().getColor(R.color.bg_Gray_light)) ;
	        TextView ttv = (TextView) orderuseless.getChildAt(0) ;
	        ttv.setTextColor(ShopFragment.this.getResources().getColor(R.color.unknownColor)) ;
			
	        orderhigh = (LinearLayout) view2.findViewById(R.id.orderhigh);
			orderhigh.getBackground().setAlpha(255);
			orderhigh.setOnClickListener(ShopFragment.this);
			orderlow = (LinearLayout) view2.findViewById(R.id.orderlow);
			orderlow.getBackground().setAlpha(255);
			orderlow.setOnClickListener(ShopFragment.this);
			ShopFragment certificate = (ShopFragment) ShopFragment.this;
			//int titleHeight = certificate.getTitleHeight();
			//int actualHeight = windowManager.getDefaultDisplay().getHeight()
					//- titleHeight - coupon_nearby.getHeight();
			orderhighWindow = new PopupWindow(view2, windowManager
					.getDefaultDisplay().getWidth(), LayoutParams.MATCH_PARENT);
		}
		// 使其聚集
		orderhighWindow.setFocusable(true);
		// 设置允许在外点击消失
		orderhighWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		orderhighWindow.setBackgroundDrawable(new BitmapDrawable());
		orderhighWindow.showAsDropDown(parent, 0, 0);
		
	}
	
	
}
