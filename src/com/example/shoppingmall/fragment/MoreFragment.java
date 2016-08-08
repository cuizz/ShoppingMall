package com.example.shoppingmall.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.zxing.activity.CaptureActivity;

public class MoreFragment extends Fragment{
	private RelativeLayout scan,cache;
	private View  view;
	private static int OK_RESULT=100;
	private TextView scantextview;
	private ImageView scanimageview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(getActivity()).inflate(R.layout.more, null);
		initViews();
		addlisteners();
		 
		return view;
	}
	private void initViews() {
		// TODO Auto-generated method stub
		scan=(RelativeLayout) view.findViewById(R.id.scan);
		cache=(RelativeLayout) view.findViewById(R.id.cache);
		scantextview=(TextView) view.findViewById(R.id.scantextview);
		scanimageview=(ImageView) view.findViewById(R.id.scanimageview);
	}
	private void addlisteners() {
		// TODO Auto-generated method stub
		scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),CaptureActivity.class); 
	               startActivityForResult(intent, OK_RESULT);
			}
		});
		cache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 100:
			if(resultCode==getActivity().RESULT_OK){
			 Bundle bundle = data.getExtras();  
             //��ʾɨ�赽������  
			 scantextview.setText(bundle.getString("result"));  
             //��ʾ  
			 scanimageview.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));  
			 }
			break;
		}
	}
}
