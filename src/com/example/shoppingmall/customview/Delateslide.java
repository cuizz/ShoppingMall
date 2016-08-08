package com.example.shoppingmall.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
* Created by 雷神
* User: all the people
* Date: 15-8-27
* Time: 下午14:31
*/
public class Delateslide extends GridView{

     public Delateslide(Context context, AttributeSet attrs){
          super(context, attrs);
     }

     //测量方法
     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	 
          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
          super.onMeasure(widthMeasureSpec, mExpandSpec);
     }
}

