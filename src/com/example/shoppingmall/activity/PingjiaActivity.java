package com.example.shoppingmall.activity;

import com.example.shoppingmall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PingjiaActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pingjia);
	}
}
