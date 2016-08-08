package com.example.shoppingmall.fragment;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.LoginActivity;
import com.example.shoppingmall.activity.MyinfoActivity;
import com.example.shoppingmall.activity.NotpayActivity;
import com.example.shoppingmall.activity.PingjiaActivity;
import com.example.shoppingmall.activity.ProductsInfoActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineFagment extends Fragment implements OnClickListener{
	private ImageView myimageview,waitpay,waitmessage;
	private View  view;
	private TextView username,shoucang;
	private SharedPreferences sp;
	private RelativeLayout orders;
	private TextView pingjiaTV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = LayoutInflater.from(getActivity()).inflate(R.layout.my, null);
		initviews();
		addlisteners();
		return view;
	}
	private void initviews() {
		// TODO Auto-generated method stub
		myimageview=(ImageView) view.findViewById(R.id.myimageview);
		username=(TextView) view.findViewById(R.id.username);
		shoucang=(TextView) view.findViewById(R.id.shoucangText);
		orders=(RelativeLayout) view.findViewById(R.id.orderRL);
		pingjiaTV=(TextView) view.findViewById(R.id.pingjiaTV);
		pingjiaTV.setOnClickListener(this);
		waitpay=(ImageView) view.findViewById(R.id.waitpay);
		waitpay.setOnClickListener(this);
		waitmessage=(ImageView) view.findViewById(R.id.waitmessage);
		waitmessage.setOnClickListener(this);
		sp=getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
		//if(sp.getString("userid", "").equals("")){
		username.setText("点击图片登录");
		//	}else{
		//	username.setText(sp.getString("user_id", ""));
		//	}
	}
	private void addlisteners() {
		// TODO Auto-generated method stub
		myimageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),MyinfoActivity.class);
				startActivity(intent);
			}
		});
		shoucang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),ProductsInfoActivity.class);
				startActivity(intent);
			}
		});
		orders.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.waitpay:
			Intent intent=new Intent(getActivity(),NotpayActivity.class);
			startActivity(intent);
			break;
        case R.id.waitmessage:
			
			break;
        case R.id.pingjiaTV:
        	Intent intentpingjia=new Intent(getActivity(),PingjiaActivity.class);
			startActivity(intentpingjia);
        	break;
		}
	}
}
