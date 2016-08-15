package com.example.shoppingmall.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.imgod.testtabicontext.activitys.IconTextTabsActivity;
import com.example.shop.MainActivity5;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ChangeCityActivity;
import com.example.shoppingmall.activity.GoodsActivity;
import com.example.shoppingmall.activity.MainActivity;
import com.example.shoppingmall.activity.WomenActivity;
import com.example.shoppingmall.customview.AbSlidingPlayView1;
import com.example.shoppingmall.entity.ShopEntity;
import com.example.shoppingmall.utils.PostForOneShop;
import com.example.zhuye.ProductBean;


/**
 * 主页
 */
public class MainFragment extends Fragment{
	
	//店铺假数据
	public static String ShopName[] = new String[]{"店铺0","店铺1","店铺2","店铺3","店铺4","店铺5","店铺6",
		"店铺7","店铺8","店铺9","店铺10"};
	//商品假数据
	public static String goodsName[] = new String[]{"商品0","商品1","商品2","商品3"};
	//,"商品4","商品5","商品6","商品7","商品8","商品9","商品10","商品11","商品12"
	
	//商品假数据
	public static int goodsImage[] = new int[]{R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06,R.drawable.ic_01,R.drawable.ic_02,R.drawable.ic_03,R.drawable.ic_04,
		R.drawable.ic_05,R.drawable.ic_06};

	private LinearLayout.LayoutParams linearParams, imageParams,linearParams2;
	LinearLayout linearL,linearL2;
	
	private AbSlidingPlayView1 viewPager;
	private ExpandableListView expandlistview;
	private ImageView iv;
	
	//主页Bean
	private List<ShopEntity>list=new ArrayList<ShopEntity>();
	
	 
	//获取主页shop数据
	private PostForOneShop shoplist;
	
	private ScrollView mScrollView;
	
	private ArrayList<View> allListView;

	private int[] resId = {R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };

	private LocationClient mLocationClient;

	private MyLocationListener mMyLocationListener;
	
	private MyAdapter adapter;
	private RecyclerView recyclerView;
	private ImageView womenimageview;
	private TextView changecityTV;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home, null);

		
		
		
		
		
		mScrollView = (ScrollView) view.findViewById(R.id.scrollView2);
		mScrollView.smoothScrollTo(0,0);
		
		//iv=(ImageView) view.findViewById(R.id.shopimage);
		//Intent intent=new Intent(getActivity(),ProductsInfoActivity.class);
		//startActivity(intent);
		
		recyclerView = (RecyclerView) view.findViewById(R.id.home_rv01);
		
		shoplist=new PostForOneShop(getActivity(), "10", mhandler, recyclerView);
		shoplist.addListData();
		
		

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
	 
	    recyclerView.setLayoutManager(layoutManager);
		
	    
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
		
				//new Thread(getJson).start();
//				adapter = new MyAdapter(getActivity(),list);
//				recyclerView.setAdapter(adapter);
				
		return view;
	}
	
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 11:
				try {
					JSONArray array=(JSONArray) msg.obj;
					
					
		        	for(int i = 0;i < array.length(); i++){
		        		ShopEntity shop = new ShopEntity();
		        		JSONObject json = array.optJSONObject(i);
		        		shop.setName(json.getString("name"));
		        		shop.setAddress(json.optString("address"));
		        		shop.setImg(json.optString("img"));
		        		shop.setId(json.optString("id"));
		        		
		                JSONArray jArray = json.optJSONArray("product");
		        		List<ProductBean> productBean = new ArrayList<>();
		                if(json.optJSONArray("product") != null){
		        		
		                	for(int m = 0;m < jArray.length();m++){
		  	                	ProductBean product = new ProductBean();
		  	                	JSONObject jsons =(JSONObject) jArray.opt(m);
		  	                	product.setGoodsName(jsons.optString("title"));
		  	                	product.setPrice(json.optString("price"));
		  	                	productBean.add(product);
		                	}
		                }else{
		  	                	
		                }
		                shop.setList(productBean);
		                list.add(shop);
		        	}
					
					if(adapter==null){
						adapter=new MyAdapter(getActivity(), list);
						recyclerView.setAdapter(adapter);
					}else{
						adapter.notifyDataSetChanged();
					}
					//listview.onRefreshComplete();
				} catch (Exception e) {
					// TODO Auto-generated catch block   14696862124973.jpg
					e.printStackTrace();
				}

				break;
			}
		};
	};
	

	
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

	 @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();
	}
	 

		
	 /**
	 * @author 殷一粟
	 *	recyclerView adapter
	 */
	public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
		  
		  public final static int TYPE_ZERO = 0;
		  public final static int TYPE_ONE = 1;
	      public final static int TYPE_TWO = 2;
	      public final static int TYPE_THREE = 3;
	      public final static int TYPE_FOUR = 4;
	      
	      private Context mContext;
		  //private List<ZhuYeBean> shopData;
	      
	      private List<ShopEntity> list;
		  
		  public MyAdapter(Context context, List<ShopEntity> list) {
			  this.mContext = context;
		      this.list = list;
		  }
		  
		  public boolean isHeader(int position) {
			  return position % 5 == 0;
		  }
		  public boolean isTypeOne(int position){
			  return position % 5 == 1;
		  }
		  public boolean isTypeTwo(int position){
			  return position % 5 == 2;
		  }
		  public boolean isTypeThree(int position){
			  return position % 5 == 3;
		  }
		  public boolean isTypeFour(int position){
			  return position % 5 == 4;
		  }
		 
		    
		  @Override
		  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			  View view = null;
		      ViewHolder holder = null;
		      switch (viewType) {
	          case TYPE_ZERO:
	              view = LayoutInflater.from(mContext).inflate(R.layout.dianputou, parent, false);
	              holder = new ViewHolderZero(view);
	              break;
	          case TYPE_ONE:
	              view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
	              holder = new ViewHolderOne(view);
	              break;
	          case TYPE_TWO:
	        	  view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
	              holder = new ViewHolderTwo(view);
	              break;
	          case TYPE_THREE:
	        	  view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
	              holder = new ViewHolderThree(view);
	              break;
	          case TYPE_FOUR:
	        	  view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
	              holder = new ViewHolderFour(view);
	              break;
	              
		      }
		      return holder;

		  }
		 
		  @Override
		    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		        switch (getItemViewType(position)) {
		            case TYPE_ZERO:
		                final ViewHolderZero holderZero = (ViewHolderZero) holder;
		                
		                holderZero.tv1.setText(list.get(position / 5).getName());
		                holderZero.tv2.setText("地址：" + list.get(position / 5).getAddress());
		                
		                holderZero.tv1.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								//Intent intent1 = new Intent("MY_NEW_LIFEFORM");
								Intent intent2 = new Intent(getActivity(),IconTextTabsActivity.class);
								//intent1.putExtra("shopName", list.get(position /5).getName());
								//intent1.putExtra("shop_id", list.get(position /5).getId());
								intent2.putExtra("shopName", list.get(position /5).getName());
								intent2.putExtra("shop_id", list.get(position /5).getId());
								//LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
								startActivity(intent2);
								//startActivity(intent);
							}
						});
		                
		                break;
		            case TYPE_ONE:
		                ViewHolderOne holderOne = (ViewHolderOne) holder;
		                if(list.get((int) Math.floor(position / 5)).getList().size()==0){
		                	
		                }else{
		                	holderOne.text1.setText(list.get((int) Math.floor(position / 5)).getList().get(0).getGoodsName());
		                	holderOne.text2.setText("￥" + list.get((int) Math.floor(position / 5)).getList().get(0).getPrice());
		                }

		                break;
		            case TYPE_TWO:
		                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
		                if(list.get((int) Math.floor(position / 5)).getList().size()==0){
		                	
		                }else{
		                	holderTwo.text1.setText(list.get((int) Math.floor(position / 5)).getList().get(1).getGoodsName());
		                	holderTwo.text2.setText("￥" + list.get((int) Math.floor(position / 5)).getList().get(0).getPrice());
		                }
		                break;
		            case TYPE_THREE:
		                ViewHolderThree holderThree = (ViewHolderThree) holder;
		                if(list.get((int) Math.floor(position / 5)).getList().size()==0){
		                	
		                }else{
		                	holderThree.text1.setText(list.get((int) Math.floor(position / 5)).getList().get(2).getGoodsName());
		                	holderThree.text2.setText("￥" + list.get((int) Math.floor(position / 5)).getList().get(0).getPrice());
		                }
		                break;
		            case TYPE_FOUR:
		                ViewHolderFour holderFour = (ViewHolderFour) holder;
		                if(list.get((int) Math.floor(position / 5)).getList().size()>=4){
		                	holderFour.text2.setText("￥" + list.get((int) Math.floor(position / 5)).getList().get(0).getPrice());
		                	holderFour.text1.setText(list.get((int) Math.floor(position / 5)).getList().get(3).getGoodsName());
		                }else{
		                }
		                break;
		        }
		    }
		 
		  @Override
		  public int getItemCount() {
		    return list.size() *5;
		  }
		 
		  @Override
		  public int getItemViewType(int position) {

			  if(isHeader(position)){
				 return TYPE_ZERO;
			  }
		     else if(isTypeOne(position)){
		    	 return TYPE_ONE;
		     }else if(isTypeTwo(position)){
		    	 return TYPE_TWO;
		     }else if(isTypeThree(position)){
		    	 return TYPE_THREE;
		     }else{
		    	 return TYPE_FOUR;
		     }
		  }
		  class ViewHolderZero extends RecyclerView.ViewHolder {
		        TextView tv1,tv2;

		        public ViewHolderZero(View itemView) {
		            super(itemView);
		            tv1 = (TextView) itemView.findViewById(R.id.shopname);
		            tv2 = (TextView) itemView.findViewById(R.id.address);
		            //tv2 = (TextView) itemView.findViewById(R.id.textView2);
//		            itemView.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							Intent intent = new Intent(getActivity(),MainActivity5.class);
//							intent.putExtra("shopName", list.get(position).)
//							startActivity(intent);
//						}
//					});
		        }
		    }
		  class ViewHolderOne extends RecyclerView.ViewHolder {
		        TextView text1,text2;
		        ImageView image;
		        public ViewHolderOne(View itemView) {
		            super(itemView);
		            text1 = (TextView) itemView.findViewById(R.id.productName);
		            //image = (ImageView) itemView.findViewById(R.id.productimage);
		            text2 = (TextView) itemView.findViewById(R.id.prices);
//		            itemView.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							Intent intent = new Intent(getActivity(),MainActivity5.class);
//							startActivity(intent);
//						}
//					});
		        }
		    }
		  class ViewHolderTwo extends RecyclerView.ViewHolder {
		        TextView text1,text2;
		        ImageView image;
		        public ViewHolderTwo(View itemView) {
		            super(itemView);
		            text1 = (TextView) itemView.findViewById(R.id.productName);
		            text2 = (TextView) itemView.findViewById(R.id.prices);
		            itemView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),GoodsActivity.class);
							startActivity(intent);
						}
					});
		        }
		    }
		  class ViewHolderThree extends RecyclerView.ViewHolder {
		        TextView text1,text2;
		        ImageView image;
		        public ViewHolderThree(View itemView) {
		            super(itemView);
		            text1 = (TextView) itemView.findViewById(R.id.productName);
		            text2 = (TextView) itemView.findViewById(R.id.prices);
		            itemView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),GoodsActivity.class);
							startActivity(intent);
						}
					});
		        }
		    }
		  class ViewHolderFour extends RecyclerView.ViewHolder {
		        TextView text1,text2;
		        ImageView image;
		        public ViewHolderFour(View itemView) {
		            super(itemView);
		            text1 = (TextView) itemView.findViewById(R.id.productName);
		            text2 = (TextView) itemView.findViewById(R.id.prices);
		            itemView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),GoodsActivity.class);
							startActivity(intent);
						}
					});
		        }
		    }
		}
}

