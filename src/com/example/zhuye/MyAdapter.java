//package com.example.zhuye;
//
//import java.util.List;
//
//import com.example.shop.MainActivity5;
//import com.example.shoppingmall.R;
//import com.example.shoppingmall.activity.GoodsActivity;
//import com.example.shoppingmall.entity.ZhuYeBean;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.ViewHolder;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
//
//	  public final static int TYPE_ONE = 0;
//      public final static int TYPE_TWO = 1;
//      private Context mContext;
//	  private List<ZhuYeBean> myitembean;
//
//	  public MyAdapter(Context context, List<ZhuYeBean> dataList) {
//		  this.mContext = context;
//	      this.myitembean = dataList;
//	  }
//	  
//	  public boolean isHeader(int position) {
//		  return position % 5 == 0;
//	  }
//	  
//	 
//	    
//	  @Override
//	  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		  View view = null;
//	      ViewHolder holder = null;
//	      switch (viewType) {
//          case TYPE_ONE:
//              view = LayoutInflater.from(mContext).inflate(R.layout.dianputou, parent, false);
//              holder = new ViewHolderOne(view);
//              break;
//          case TYPE_TWO:
//              view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
//              holder = new ViewHolderTwo(view);
//              break;
//	      }
//	      return holder;
//
//	  }
//	 
//	  @Override
//	    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//	        switch (getItemViewType(position)) {
//	            case TYPE_ONE:
//	                final ViewHolderOne holderOne = (ViewHolderOne) holder;
//	                //holderOne.tv1.setText(beans.get(position));
//	                holderOne.tv1.setText(myitembean.get(position).getShopName());
//	                //holderOne.tv2.setText(myitembean.get(position).getTitle());
//	                holderOne.itemView.setTag(myitembean.get(position).getShopName());
//	                break;
//	            case TYPE_TWO:
//	                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
//	                //holderTwo.text1.setText(beans.get(position));
//	                //holderTwo.text1.setText(myitembean.get(position).getTitle());
//	                holderTwo.image.setImageResource(myitembean.get(position).getGoodsImage());
//	                holderTwo.itemView.setTag(myitembean.get(position).getPeople());
//	                break;
//	        }
//	    }
//	 
//	  @Override
//	  public int getItemCount() {
//	    return myitembean.size();
//	  }
//	 
//	  @Override
//	  public int getItemViewType(int position) {
//
//		  if(isHeader(position)){
//			 return TYPE_ONE;
//		  }
//	     else{
//	    	 return TYPE_TWO;
//	     }
//	  }
//	  class ViewHolderOne extends RecyclerView.ViewHolder {
//	        TextView tv1,tv2;
//
//	        public ViewHolderOne(View itemView) {
//	            super(itemView);
//	            tv1 = (TextView) itemView.findViewById(R.id.shopname);
//	            //tv2 = (TextView) itemView.findViewById(R.id.textView2);
//	            itemView.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(getActivity(),MainActivity5.class);
//						startActivity(intent);
//					}
//				});
//	        }
//	    }
//	  class ViewHolderTwo extends RecyclerView.ViewHolder {
//	        TextView text1;
//	        ImageView image;
//	        public ViewHolderTwo(View itemView) {
//	            super(itemView);
//	            //text1 = (TextView) itemView.findViewById(R.id.text);
//	            image = (ImageView) itemView.findViewById(R.id.productimage);
//itemView.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(getActivity(),GoodsActivity.class);
//						startActivity(intent);
//					}
//				});
//	        }
//	    }
//	}
