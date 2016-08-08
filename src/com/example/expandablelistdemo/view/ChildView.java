package com.example.expandablelistdemo.view;


import com.example.shoppingmall.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class ChildView extends LinearLayout implements View.OnClickListener{

	private int groupPosition;
	private int childPosition;
	private OnChildClickListener listener;

	private CheckBox selectChild;
	private ImageView childImage;
	private TextView childName, childNum, childNum2,childPrice ,btn ,reduceCart ;
	private LinearLayout addPro, proInfo;
	private Button deletea;

	private TextView tv_chakan_one;

	private TextView tv_chakan_two;

	private TextView tv_chakan_three;

	private PopupWindow popupWindow;

	public ChildView(OnChildClickListener listener, Context context) {
		this(listener, context, null);
	}

	public ChildView(OnChildClickListener listener, Context context,
			AttributeSet attrs) {
		this(listener, context, attrs, 0);
	}
	
	
	/**
	 * 这个是他的  构造 方法
	 * @param listener
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	@SuppressLint("NewApi")
	public ChildView(OnChildClickListener listener, Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.listener = listener;
		intViews();
	}

	
	
	
	public void intViews() {
		final LayoutInflater mLayoutInflater = LayoutInflater
				.from(getContext());
		View v = mLayoutInflater.inflate(R.layout.child, ChildView.this, false);
		addView(v);
		//所隐藏部分整个大的布局
		addPro = (LinearLayout) v.findViewById(R.id.linearGone);
		//这个是 空白部分
		proInfo = (LinearLayout) v.findViewById(R.id.layoutVisible);
		//孩子 部分的 单选钮
		selectChild = (CheckBox) v.findViewById(R.id.checkbox_select_child);
		//商品的 图片
		childImage = (ImageView) v.findViewById(R.id.textview_child_image);
		//商品的名字
		childName = (TextView) v.findViewById(R.id.textview_child_name);
		//商品的数量
		childNum = (TextView) v.findViewById(R.id.textview_child_num);
		//商品的数量  *2
		childNum2 = (TextView) v.findViewById(R.id.textview_child_num2);
		//childNum2.setText("1");
		//价格
		childPrice = (TextView) v.findViewById(R.id.textview_child_price) ;
		//删除键
		deletea = (Button) v.findViewById(R.id.delete) ;
		//减 
		reduceCart = (TextView) v.findViewById(R.id.cart_reduce) ;
		//首先去判断 有没有  通用券   有能够点击 没有 就不能点击   然后再去 判断 有没有选中   选中的话 就去减   反之 你也知道了 
		tv_chakan_one=(TextView)v.findViewById(R.id.tv_chakan_one);	
		tv_chakan_two=(TextView)v.findViewById(R.id.tv_chakan_two);
		tv_chakan_three=(TextView)v.findViewById(R.id.tv_chakan_three);
		reduceCart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int shopNum = Integer.valueOf(childNum2.getText().toString());
				//int shopNum = Integer.valueOf("1");
                if(shopNum>1){
             		shopNum = shopNum - 1;
                }else{
             	   Toast.makeText(v.getContext(), "你所选的数量应该大于一件...", Toast.LENGTH_LONG).show() ;
                }
                
				String a = String.valueOf(shopNum);
				childNum.setText("x"+a);
				childNum2.setText(a);
				listener.onUpdataChild(groupPosition, childPosition, a) ;
			}
			
		});
		
		deletea.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				listener.onDeleteChilde(groupPosition, childPosition) ;
			}
		});
		//加
		btn = (TextView) v.findViewById(R.id.bt);
		
		btn.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				int shopNum = Integer.valueOf(childNum2.getText().toString());

				shopNum = shopNum + 1;
				//System.out.println(shopNum + "-------------");
				String a = String.valueOf(shopNum);
				childNum.setText("x"+a);
				childNum2.setText(a);
				
				listener.onUpdataChild(groupPosition, childPosition, a) ;

			}

		});
		//这个是checkbox 的按钮 ？？？？ 为什么   点击事件  呢？
		selectChild.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectChild.isChecked()) {
					
					listener.onChildChecked(groupPosition, childPosition);
					
				} else {
					listener.onChildUnChecked(groupPosition, childPosition);
				}
			}
		});
	}

	public interface OnChildClickListener {
		public void onChildChecked(int groupPosition, int childPosition);
		public void onChildUnChecked(int groupPosition, int childPosition);
		public void onDeleteChilde(int groupPosition, int childPosition) ;
		public void onUpdataChild(int groupPositon ,int childPosition ,String a ) ;
	}

	public int getGroupPosition() {
		return groupPosition;
	}

	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}

	public int getChildPosition() {
		return childPosition;
	}

	public void setChildPosition(int childPosition) {
		this.childPosition = childPosition;
	}

	public CheckBox getSelectChild() {
		return selectChild;
	}

	public void setSelectChild(CheckBox selectChild) {
		this.selectChild = selectChild;
	}

	public ImageView getChildImage() {
		return childImage;
	}

	public void setChildImage(ImageView childImage) {
		this.childImage = childImage;
	}

	public void setChildNum(TextView childNum) {
		this.childNum = childNum;
	}

	public TextView getChildNum() {
		return childNum;
	}

	public void setChildNumm(TextView childNum2) {
		this.childNum2 = childNum2;
	}

	public TextView getChildNumm() {
		return childNum2;
	}

	public TextView getChildName() {
		return childName;
	}

	public void setChildName(TextView childName) {
		this.childName = childName;
	}
	public TextView getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(TextView childPrice) {
		this.childPrice = childPrice;
	}
	

	public LinearLayout getChildNamee() {
		return addPro;
	}

	public void setChildNamee(LinearLayout addPro) {
		this.addPro = addPro;
	}

	public LinearLayout getChildNameee() {
		return proInfo;
	}

	public void setChildNameee(LinearLayout proInfo) {
		this.proInfo = proInfo;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_chakan_one:
		
			//首先 你得  判断   这个商品 有没有 优惠券 然后才能 隐藏  或者  显示
			//这个地方的问题是我没有 一个   购物车的  goods id   去跟 所请求的 goods id  来比较  这个地方 是  在  购物车的类 里面 我们 去准备一个方法        去 获得 集合      然后    在  准备 一个  方法  获得 这个   
			//我还是卡在这个地方   
			break;
		case R.id.tv_chakan_two:
			
			//这个地方 是    弹出来一个  弹窗 让他 选取     如果  有的话  那么 我们把券 显示出来
			showPopUpWindow(v);
			
			break;
		case R.id.tv_chakan_three:
			
			showPopUpWindowTwo(v);
			break;
		}
		
	}

	private void showPopUpWindowTwo(View v) {
		LinearLayout layout = new LinearLayout(getContext());  
        layout.setBackgroundColor(Color.GRAY);  
        TextView tv = new TextView(getContext());  
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //这个地方 讲道理  应该    去请求  东西 才能够  设置 text  
        
        tv.setText("这数据 我没法写  不知道怎么写");  
        tv.setTextColor(Color.WHITE);  
        layout.addView(tv);  
        popupWindow = new PopupWindow(layout,LayoutParams.FILL_PARENT,300);  
        popupWindow.setFocusable(true);  
        popupWindow.setOutsideTouchable(true);  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
          
        int[] location = new int[2];  
        v.getLocationOnScreen(location);  
         
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());  
		
	}

	private void showPopUpWindow(View v) {
		//Intent  intent = new Intent(getContext(),quanAcivity.class);
		//getContext().startActivity(intent);
	}

}
