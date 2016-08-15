package com.example.shop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.utils.PostToProductList;
import com.example.zhuye.ProductBean;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cui on 2016/1/28.
 */
public class FragSport extends Fragment {
	
	private RecyclerView recyclerView;
	private RecyclerViewAdapter recyclerViewAdapter;
	private PostToProductList productList;
	private List<ProductBean> productBean = new ArrayList<>();
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater
				.inflate(R.layout.product_recycle, container, false);
    	
    	recyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
  		
  		
  		productList=new PostToProductList(getActivity(), "10", mhandler, recyclerView,"1","new");
  		productList.addListData();
  		
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            
            recyclerView.setLayoutManager(layoutManager);
    	
    	
        return view;
    }
    
    Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 88:
				try {
					JSONArray array=(JSONArray) msg.obj;
					
					
		        	for(int i = 0;i < array.length(); i++){
		        		
		        		ProductBean product = new ProductBean();
		        		JSONObject json = array.optJSONObject(i);
		        		
		        		product.setGoodsName(json.optString("title"));
  	                	product.setPrice(json.optString("price"));
  	                	productBean.add(product);
		        	}
					
					if(recyclerViewAdapter==null){
						recyclerViewAdapter=new RecyclerViewAdapter(getActivity(), productBean);
						recyclerView.setAdapter(recyclerViewAdapter);
					}else{
						//recyclerViewAdapter.notifyDataSetChanged();
						recyclerViewAdapter=new RecyclerViewAdapter(getActivity(), productBean);
						recyclerView.setAdapter(recyclerViewAdapter);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		};
	};
    
}
