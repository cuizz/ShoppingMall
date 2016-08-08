package com.example.shop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shop.ProductBean;
import com.example.shoppingmall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LittleShopFragment extends Fragment{
	
	List<View> views=new ArrayList<>();
	private List<ProductBean> data;
	public static String productName[]=new String[]{"商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5",
		"商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5","商品名称1","商品名称2","商品名称3","商品名称4","商品名称5"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater
				.inflate(R.layout.product_recycle, container, false);
		RecyclerView id_rv= (RecyclerView) view.findViewById(R.id.id_rv);
		//gridView = (GridView) view.findViewById(R.id.products);
		
		initData();
            RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(data);
            final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            
            id_rv.setLayoutManager(layoutManager);
            id_rv.setAdapter(recyclerViewAdapter);
            view.setTag(id_rv);
             views.add(view);
             
		return view;
	}
	
	private void initData(){
		data = new ArrayList<ProductBean>();
		ProductBean bean = new ProductBean();
		for(int i=0;i<productName.length;i++){
			bean = new ProductBean();
			bean.setName(productName[i]);
			data.add(bean);
		}
    }
}
