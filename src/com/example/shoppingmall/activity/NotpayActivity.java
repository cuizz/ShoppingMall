package com.example.shoppingmall.activity;

import com.example.shoppingmall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class NotpayActivity extends Activity implements OnClickListener{
	private ImageView backimage;
	private ListView listview;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notpay);
		backimage=(ImageView) findViewById(R.id.backImage);
		backimage.setOnClickListener(this);
		listview=(ListView) findViewById(R.id.listview);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		}
	}
}
