package com.example.shop;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingmall.R;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cui on 2016/1/28.
 */
public class FragFun extends Fragment {
	
	private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> tabs;
    private List<Fragment> fragments;
    private MyAdapter adapter;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater
				.inflate(R.layout.tablayout, container, false);
    	
		tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_02);/**如果不提示导包可以Clean Project*/
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_02);
        
        initData();
        initAdapter();
        initTabLayout();
        initViewPager();
        return view;
    }
private void initViewPager() {
    	
        /**注意这2行代码的顺序：viewpaper要先设置adapter，才可以让 tablayout绑定
         *  否则报错：viewpager没有setAdapter()*/
    	
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    private void initData() {
        tabs = new ArrayList<>();
        tabs.add("综合");
        tabs.add("销量");
        tabs.add("新品");
        tabs.add("价格");
        
        fragments = new ArrayList<>();
        fragments.add(new LittleShopFragment());
        fragments.add(new FragSport());
        fragments.add(new FragSport());
        fragments.add(new FragSport());
    }

    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(0)));/**注意创建TAB对象：tabLayout.newTab()*/
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(2)));
    }

    private void initAdapter() {
        adapter = new MyAdapter(getChildFragmentManager(),tabs,fragments);
    }

}
