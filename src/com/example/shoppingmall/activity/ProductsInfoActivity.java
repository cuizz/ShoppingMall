package com.example.shoppingmall.activity;

import com.example.shoppingmall.R;
import com.example.shoppingmall.utils.PostForClass;
import com.example.shoppingmall.utils.PostForClassfour;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsInfoActivity extends FragmentActivity implements OnClickListener{
	private Button cartBtn,payBtn;
	private PopupWindow popupWindow;
	private View views;
	private final int ADDORREDUCE = 1;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
	private TextView pop_num,pop_add,pop_reduce,titleTV,priceTV;
	private ImageView phoneimage;
	private String shop_hone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products_info);
		intviews();
		addlisteners();
		InitateImageLoader();
	}

	private void intviews() {
		// TODO Auto-generated method stub
		cartBtn=(Button) findViewById(R.id.cartButton);
		cartBtn.setOnClickListener(this);
		payBtn=(Button) findViewById(R.id.payButton);
		payBtn.setOnClickListener(this);
		
		views = LayoutInflater.from(ProductsInfoActivity.this).inflate(
				R.layout.adapter_popwindow, null);
		pop_num=(TextView) views.findViewById(R.id.pop_num);
		pop_reduce=(TextView) views.findViewById(R.id.pop_reduce);
		pop_reduce.setOnClickListener(this);
		pop_add=(TextView) views.findViewById(R.id.pop_add);
		pop_add.setOnClickListener(this);
		phoneimage=(ImageView) findViewById(R.id.phoneimageview);
		titleTV=(TextView) findViewById(R.id.textView1);
		priceTV=(TextView) findViewById(R.id.textView2);
	}

	private void addlisteners() {
		//打电话的
		// TODO Auto-generated method stub
		phoneimage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneNum = (String) shop_hone;
				//System.out.println("我想要拨打的号码是：" + phoneNum);
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + phoneNum));
				startActivity(intent);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cartButton:
			p(v);
			break;
		case R.id.payButton:
			hh(v);
			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("10000")) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				//Log.d("sht", "num_add--->"+num_add);
				pop_num.setText(num_add);
			} else {
				Toast.makeText(this, "选取的件数超出范围", Toast.LENGTH_SHORT).show();
			}
			break;
			//这个 是购买的按钮
		case R.id.pop_reduce:

			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText().toString()) - ADDORREDUCE + "";
				pop_num.setText(num_reduce);
			} else {
				Toast.makeText(this, "您选择的商品不能低于一件...", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}
	}
	
	private void p(View v) {
		WindowManager windowManager = (WindowManager) ProductsInfoActivity.this
				.getSystemService(Service.WINDOW_SERVICE);
		popupWindow = new PopupWindow(views, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		views.getBackground().setAlpha(150);
		LinearLayout notuse = (LinearLayout) views.findViewById(R.id.notuse);
		Button popOk = (Button) views.findViewById(R.id.pop_ok);
		ScrollView scrollHeight = (ScrollView) views
				.findViewById(R.id.scrollHeight);
		LinearLayout titleHeight = (LinearLayout) views
				.findViewById(R.id.titleHeight);
		LinearLayout.LayoutParams layoutParamsss = (android.widget.LinearLayout.LayoutParams) titleHeight
				.getLayoutParams();
		layoutParamsss.height = windowManager.getDefaultDisplay().getHeight() / 6;
		LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) scrollHeight
				.getLayoutParams();
		layoutParams.height = windowManager.getDefaultDisplay().getHeight() / 3;
		LinearLayout.LayoutParams layoutParamss = (android.widget.LinearLayout.LayoutParams) notuse
				.getLayoutParams();
		layoutParamss.height = windowManager.getDefaultDisplay().getHeight() / 3;
		LinearLayout.LayoutParams layoutOk = (android.widget.LinearLayout.LayoutParams) popOk
				.getLayoutParams();
		layoutOk.height = windowManager.getDefaultDisplay().getHeight() / 6;
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		
		
		//String p=productId;
		//String s=shopId;
		//String p2=intent.getStringExtra("price");
		//String title = intent.getStringExtra("goods_title");
		//String img = intent.getStringExtra("goods_img");
		//Log.d("sht", "非常重要的参数  支付 所要的->p"+p+",s->"+s+",p2->"+p2+",title->"+title+",img-》"+img);
		// 传了 一个 goods—id 请求的是 弹窗里面的 内容
		PostForClassfour poforClass = new PostForClassfour(ProductsInfoActivity.this,
			"", views, mImageLoader, options,"","","","","");		
	}

	
	// 下面的这个方法是弹出来商品属性选择的窗口...    重点研究   
		public void hh(View v) {
			//先获取  一个窗口服务 
			WindowManager windowManager = (WindowManager) ProductsInfoActivity.this
					.getSystemService(Service.WINDOW_SERVICE);
			popupWindow = new PopupWindow(views, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			views.getBackground().setAlpha(150);
			LinearLayout notuse = (LinearLayout) views.findViewById(R.id.notuse);
			//确定 按钮
			Button popOk = (Button) views.findViewById(R.id.pop_ok);
			ScrollView scrollHeight = (ScrollView) views
					.findViewById(R.id.scrollHeight);
			LinearLayout titleHeight = (LinearLayout) views
					.findViewById(R.id.titleHeight);
			LinearLayout.LayoutParams layoutParamsss = (android.widget.LinearLayout.LayoutParams) titleHeight
					.getLayoutParams();
			//设置这个 titleHeight 的高度 为   屏幕的  六分之一
			layoutParamsss.height = windowManager.getDefaultDisplay().getHeight() / 6;
			LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) scrollHeight
					.getLayoutParams();
			layoutParams.height = windowManager.getDefaultDisplay().getHeight() / 3;
			LinearLayout.LayoutParams layoutParamss = (android.widget.LinearLayout.LayoutParams) notuse
					.getLayoutParams();
			layoutParamss.height = windowManager.getDefaultDisplay().getHeight() / 3;
			LinearLayout.LayoutParams layoutOk = (android.widget.LinearLayout.LayoutParams) popOk
					.getLayoutParams();
			layoutOk.height = windowManager.getDefaultDisplay().getHeight() / 6;
			
			popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			//第一个参数   是哪一个控件  第二个参数  是   比重  第三个参数 是  x  轴   第四个参数 是 y  轴 
			popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.update();
			
			PostForClass poforClass = new PostForClass(ProductsInfoActivity.this,"", views, mImageLoader,options);
		}
		public void InitateImageLoader() {
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					this.getApplicationContext())
					.threadPoolSize(2)
					// 线程池内加载的数量
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
					.discCacheSize(50 * 1024 * 1024)
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					// 将保存的时候的URI名称用MD5 加密
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.discCacheFileCount(40)
					.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
					.imageDownloader(
							new BaseImageDownloader(this.getApplicationContext(),
									5 * 1000, 30 * 1000)) // connectTimeout
					.writeDebugLogs() // Remove for release
					.build();// 开始构建
			// 这个 应该是拿到 jar 包 里边的方法
			/**
			 * public static ImageLoader getInstance() { if (instance == null) {
			 * synchronized (ImageLoader.class) { if (instance == null) { instance =
			 * new ImageLoader(); } } } return instance; }
			 */
			//得到的是一个 imageloader  对象
			mImageLoader = ImageLoader.getInstance();
			
			mImageLoader.init(config);
			options = new DisplayImageOptions.Builder()
					//.showStubImage(R.drawable.fiv1)
					//.showImageForEmptyUri(R.drawable.ic_empty)
					//.showImageOnFail(R.drawable.fiv1).cacheInMemory(false)
					.cacheOnDisc(false).bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
		}
}
