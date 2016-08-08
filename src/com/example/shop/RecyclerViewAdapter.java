package com.example.shop;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.shop.ProductBean;
import com.example.shoppingmall.R;

/**
 * Created by Administrator on 2016/3/28.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
	
	  private List<ProductBean> myitembean;
	  //���캯��
	  public RecyclerViewAdapter(List<ProductBean> dataList) {
	    this.myitembean = dataList;
	  }
	  
	 
	  @Override
	  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

		  return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, null));
	  }
	 
	  @Override
	  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
	    
		  ((HeaderViewHolder)viewHolder).getTextView().setText(myitembean.get(position).getName());
	  
	  }
	 
	  @Override
	  public int getItemCount() {
	    return myitembean.size();
	  }
	 
	  public class HeaderViewHolder extends RecyclerView.ViewHolder {
	    private TextView textView;
	    public HeaderViewHolder(View itemView) {
	      super(itemView);
	      textView = (TextView) itemView.findViewById(R.id.productName);
	    }
	    public TextView getTextView() {
	      return textView;
	    }
	  }
	}
