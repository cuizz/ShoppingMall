package com.example.shoppingmall.activity;

import com.example.shoppingmall.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author �������
 *	  ��Ʒ����
 */
public class GoodsActivity extends Activity{
	private ListView listview;
	MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shopfragment);
		
		listview=(ListView) findViewById(R.id.listView11);
		adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
	}
	public class MyAdapter extends BaseAdapter {

		Context mcontext;
		LinearLayout layout = null;
		LayoutInflater inflater;
		final int VIEW_TYPE = 2; // �ܵ�item��ʽ��
		final int TYPE_0 = 0;
		final int TYPE_1 = 1;
		private String[] info = { "ʱ������찡��", "���ǻ�����", "���������ˣ�", "����������",
				"��Ҫ������ǲ��ܼ��棿" };

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
	        	   convertView=inflater.inflate(R.layout.goods, parent,false);
	        	   convertView.setTag(holder);
	        	   
	        	   break;
	           case TYPE_1:
	        	   
	        	   convertView=inflater.inflate(R.layout.rate, parent,false);
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
