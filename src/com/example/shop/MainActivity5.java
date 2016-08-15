package com.example.shop;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;




import com.example.shoppingmall.R;

/**
 * 店铺详情
 * 
 * 需求：TabLayout的简单使用
 *  确定布局---找到控件--设置adapter--
 *  1 initView()--
 *  2 initAdapter
 *  3 initTablayout
 *  4 initViewpager*/
public class MainActivity5 extends AppCompatActivity {
	
//	private PostToProductList postToProductList;
//	private RecyclerView recyclerView;
//	private List<ProductBean> productBean = new ArrayList<>();
	
	
	
	private String shop_id;
	private LittleShopFragment fragment;
	
	private String shopName;
	
    private TabLayout tabLayout;
    private ViewPager viewPager;
    
    private List<String> tabs;
    private List<Fragment> fragments;
    private MyAdapter adapter;
    private TextView textView;
    //about data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.shop_toolbar);
        //get shop id
        shop_id = getIntent().getStringExtra("shop_id");
        
        fragment = new LittleShopFragment();
        
        //加载toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //显示返回按钮图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        textView = (TextView) findViewById(R.id.shopname1);
        
        shopName = getIntent().getStringExtra("shopName");
        textView.setText(shopName);
        
        
        initView();
        initData();
        initAdapter();
        initTabLayout();
        initViewPager();
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
        
        tabs.add("店铺首页");
        tabs.add("全部宝贝");
        tabs.add("新品上架");

        fragments = new ArrayList<>();
        fragments.add(new LittleShopFragment());
        fragments.add(new FragFun());
        fragments.add(new FragSport());
    }

    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(0)));/**注意创建TAB对象：tabLayout.newTab()*/
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabs.get(2)));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_01);
    }

    private void initAdapter() {
        adapter = new MyAdapter(getSupportFragmentManager(),tabs,fragments);
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_01);/**如果不提示导包可以Clean Project*/
        viewPager = (ViewPager) findViewById(R.id.viewPager_01);
    }
}
