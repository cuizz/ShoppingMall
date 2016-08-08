package com.example.shoppingmall.activity;

import java.io.File;

import com.example.shoppingmall.R;
import com.example.shoppingmall.fragment.ShopFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyinfoActivity extends Activity implements OnClickListener{
	private ImageView backimageview,photoimageview;
	private RelativeLayout photo,address,vip,phone,password;
	private PopupWindow orderhighWindow;
	private View view2;
	private LinearLayout orderuseless;
	private LinearLayout orderhigh;
	private LinearLayout orderlow;
	private File tempFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myinfo);
		intviews();
	}

	private void intviews() {
		// TODO Auto-generated method stub
		photoimageview=(ImageView) findViewById(R.id.imageview);
		backimageview=(ImageView) findViewById(R.id.backImage);
		backimageview.setOnClickListener(this);
		photo=(RelativeLayout) findViewById(R.id.photoRL);
		photo.setOnClickListener(this);
		address=(RelativeLayout) findViewById(R.id.addressRL);
		address.setOnClickListener(this);
		vip=(RelativeLayout) findViewById(R.id.vipRL);
		vip.setOnClickListener(this);
		phone=(RelativeLayout) findViewById(R.id.phoneRL);
		phone.setOnClickListener(this);
		password=(RelativeLayout) findViewById(R.id.passwordRL);
		password.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.addressRL:
			Intent intent=new Intent(this,AddressActivity.class);
			startActivity(intent);
			break;
		case R.id.vipRL:
			Intent intentvip=new Intent(this,ChangeNameActivity.class);
			startActivity(intentvip);
			break;
		case R.id.passwordRL:
			Intent intentpwd=new Intent(this,ChangepasswordActivity.class);
			startActivity(intentpwd);
			break;
        case R.id.photoRL:
			showGeniusOrderWindow(v);
			break;
        case R.id.orderhigh:
        	gallery(v);
        	break;
        case R.id.orderlow:
        	orderhighWindow.dismiss();
        	break;
		}
	}
	/*
	 * 从相册获取
	 */
	public void gallery(View view) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
		startActivityForResult(intent, 100);
	}

	/*
	 * 从相机获取
	 */
	public void camera(View view) {
		// 激活相机
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			tempFile = new File(Environment.getExternalStorageDirectory(),
					"touxiang.jpg");
			// 从文件中创建uri
			Uri uri = Uri.fromFile(tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		}
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
		startActivityForResult(intent, 200);
	}
	/*
	 * 剪切图片
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);

		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		startActivityForResult(intent, 300);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if (requestCode == 100) {
			// 从相册返回的数据
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
				orderhighWindow.dismiss();
			}

		} 
		else if (requestCode == 200) {
			// 从相机返回的数据
			if (hasSdcard()) {
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(MyinfoActivity.this, "未找到存储卡，无法存储照片！", 0).show();
			}

		} 
		else if (requestCode == 300) {
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				this.photoimageview.setImageBitmap(bitmap);
				orderhighWindow.dismiss();
			}
			try {
				// 将临时文件删除
				//tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	private boolean hasSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
				{
				return true;
				}
		return false;
	}

	private void showGeniusOrderWindow(View parent) {
		WindowManager windowManager = (WindowManager) 
				getSystemService(Context.WINDOW_SERVICE);
		if (orderhighWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view2 = layoutInflater.inflate(R.layout.photopop, null);
			view2.getBackground().setAlpha(150);
	        orderuseless = (LinearLayout) view2.findViewById(R.id.orderuseless) ;
	        orderuseless.setOnClickListener(this) ;
	        orderuseless.setBackgroundColor(this.getResources().getColor(R.color.bg_Gray_light)) ;
	        TextView ttv = (TextView) orderuseless.getChildAt(0) ;
	        ttv.setTextColor(getResources().getColor(R.color.unknownColor)) ;
			
	        orderhigh = (LinearLayout) view2.findViewById(R.id.orderhigh);
			orderhigh.getBackground().setAlpha(255);
			orderhigh.setOnClickListener(this);
			orderlow = (LinearLayout) view2.findViewById(R.id.orderlow);
			orderlow.getBackground().setAlpha(255);
			orderlow.setOnClickListener(this);
			//ShopFragment certificate = (ShopFragment) ShopFragment.this;
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
