package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.shoppingmall.R;
import com.example.zhuye.ProductBean;

/**
 * Created by Administrator on 2016/3/28.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
	
	  private List<ProductBean> myitembean;
	  private Context mContext;
	  public RecyclerViewAdapter(Context context,List<ProductBean> dataList) {
		  this.mContext = context;
		  this.myitembean = dataList;
	  }
	  
	 
	  @Override
	  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		  View view = null;
	      ViewHolder holder = null;
	      view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
          holder = new ViewHolderOne(view);
          return holder;
	  }
	 
	 
	  @Override
	    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
	                final ViewHolderOne holderZero = (ViewHolderOne) holder;
	                
	                holderZero.text1.setText(myitembean.get(position).getGoodsName());
	                
	    }
	 
	  
	  
	  @Override
	  public int getItemCount() {
	    return myitembean.size();
	  }
	  
	 
	  class ViewHolderOne extends RecyclerView.ViewHolder {
	        TextView text1,text2;
	        ImageView image;
	        public ViewHolderOne(View itemView) {
	            super(itemView);
	            text1 = (TextView) itemView.findViewById(R.id.productName);
	    }
	}
}