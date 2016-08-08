package com.example.shoppingmall.activity;

import com.example.shoppingmall.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ListView listview;
	MyAdapter1 adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainn);
		
		listview=(ListView) findViewById(R.id.listView11);
		 adapter=new MyAdapter1(this);
		 listview.setAdapter(adapter);
		
	}
	public class MyAdapter1 extends BaseAdapter {
		Context mcontext;
		LinearLayout layout = null;
		LayoutInflater inflater;
		final int VIEW_TYPE = 2; // �ܵ�item��ʽ��
		final int TYPE_0 = 0;
		final int TYPE_1 = 1;
		private String[] info = {"0","1","2","3","4","5","6"};
		
		public MyAdapter1(Context context) {

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
			// TODO Auto-generated method stub
			return info.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
	        	   convertView=inflater.inflate(R.layout.zhuye, parent,false);
	        	  // holder.tv1=(TextView) convertView.findViewById(R.id.tv1);
	        	 //  holder.tv2=(TextView) convertView.findViewById(R.id.tv2);
	        	  // holder.tv3=(TextView) convertView.findViewById(R.id.tv3);
	        	  // holder.tv4=(TextView) convertView.findViewById(R.id.tv4);
	        	  // holder.tv5=(TextView) convertView.findViewById(R.id.tv5);
	        	  // holder.tv6=(TextView) convertView.findViewById(R.id.tv6);
	        	   //holder.iv1=(ImageView) convertView.findViewById(R.id.iv1);
	        	   //holder.iv2=(ImageView) convertView.findViewById(R.id.iv2);
	        	   //holder.iv3=(ImageView) convertView.findViewById(R.id.iv3);
	        	   //holder.iv4=(ImageView) convertView.findViewById(R.id.iv4);
	        	   //holder.iv5=(ImageView) convertView.findViewById(R.id.iv5);
	        	   //holder.iv6=(ImageView) convertView.findViewById(R.id.iv6);
	        	  // holder.iv7=(ImageView) convertView.findViewById(R.id.iv7);
	        	   //holder.iv7.setTag(position);
	        	  // holder.iv6.setTag(position);
	        	  // holder.iv1.setTag(position);
	        	 //  holder.iv6.setOnClickListener(new View.OnClickListener() {
						
					//	@Override
					//	public void onClick(View v) {
						//	// TODO Auto-generated method stub
							//Toast.makeText(MainActivity.this, "����������", Toast.LENGTH_SHORT).show();
						//}
					//});
	        	  /* holder.iv7.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "������ͯװ", Toast.LENGTH_SHORT).show();
						}
					});
	        	   holder.iv1.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "ddd", Toast.LENGTH_SHORT).show();
					}
				});*/
	        	   
	        	   break;
	           case TYPE_1:
	        	   
	        	   convertView=inflater.inflate(R.layout.dianpu, parent,false);
	        	   holder1.tv7=(TextView) convertView.findViewById(R.id.tv7);
	        	   holder1.tv8=(TextView) convertView.findViewById(R.id.tv8);
	        	   holder1.tv9=(TextView) convertView.findViewById(R.id.tv9);
	        	   holder1.tv10=(TextView) convertView.findViewById(R.id.tv10);
	        	   holder1.tv11=(TextView) convertView.findViewById(R.id.tv11);
	        	   holder1.tv12=(TextView) convertView.findViewById(R.id.tv12);
	        	   holder1.tv13=(TextView) convertView.findViewById(R.id.tv13);
	        	   holder1.tv14=(TextView) convertView.findViewById(R.id.tv14);
	        	   holder1.tv15=(TextView) convertView.findViewById(R.id.tv15);
	        	   holder1.tv16=(TextView) convertView.findViewById(R.id.tv16);
	        	   holder1.tv17=(TextView) convertView.findViewById(R.id.tv17);
	        	   holder1.tv18=(TextView) convertView.findViewById(R.id.tv18);
	        	   holder1.tv19=(TextView) convertView.findViewById(R.id.tv19);
	        	   holder1.tv20=(TextView) convertView.findViewById(R.id.tv20);
	        	   holder1.tv21=(TextView) convertView.findViewById(R.id.tv21);
	        	   holder1.tv22=(TextView) convertView.findViewById(R.id.tv22);
	        	   holder1.iv8=(ImageView) convertView.findViewById(R.id.iv8);
	        	   holder1.iv9=(ImageView) convertView.findViewById(R.id.iv9);
	        	   holder1.iv10=(ImageView) convertView.findViewById(R.id.iv10);
	        	   holder1.iv11=(ImageView) convertView.findViewById(R.id.iv11);
	        	   holder1.iv12=(ImageView) convertView.findViewById(R.id.iv12);
	        	   holder1.iv13=(ImageView) convertView.findViewById(R.id.iv13);
	        	   holder1.iv14=(ImageView) convertView.findViewById(R.id.iv14);
	        	   holder1.rl1=(RelativeLayout) convertView.findViewById(R.id.rl1);
	        	   holder1.rl1.setTag(position);
	        	   holder1.iv11.setTag(position);
	        	   holder1.iv11.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this, GoodsActivity.class);
						startActivity(intent);
					}
				});
	        	   holder1.rl1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this, ShopActivity.class);
						startActivity(intent);
					}
				});
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
//	     	   holder.txt_name.setText("���ں�����Ŷ��");
//	     	   holder.txt_con.setText("��֮���Ի����ģ�����Ϊ��û�������������");
//	     	   holder.img_header.setBackgroundResource(R.drawable.pic);
	     	   
	     	   break;
	        case TYPE_1:
	     	   
	     	
	     	   break;
	        }
			

			return convertView;
		}

		class ViewHolder {
			private TextView tv1,tv2,tv3,tv4,tv5,tv6;
			private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7;
		}

		class ViewHolder1 {
			@SuppressWarnings("unused")
			public RelativeLayout rl1;
			private TextView tv7,tv8,tv9,tv10,tv11,tv12,tv13,
			tv14,tv15,tv16,tv17,tv18,tv19,tv20,tv21,tv22;
			private ImageView iv8,iv9,iv10,iv11,iv12,iv13,iv14;
		}

	}
}
