package com.example.imgod.testtabicontext.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imgod.testtabicontext.adapter.FragmentViewPagerAdapter;
import com.example.imgod.testtabicontext.fragments.OneFragment;
import com.example.shop.FragFun;
import com.example.shop.FragSport;
import com.example.shop.LittleShopFragment;
import com.example.shoppingmall.R;

import java.util.ArrayList;
import java.util.List;


public class IconTextTabsActivityTemp2 extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titles;
    private List<Fragment> fragments;
    private int[] tabIcons = {
            R.drawable.selector_tab1,
            R.drawable.selector_tab2,
            R.drawable.selector_tab3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_toolbar);
        initView();
        initValue();
        initEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager_01);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_01);
    }

    private void initValue() {
        fragments = new ArrayList<>();
        fragments.add(new LittleShopFragment());
        fragments.add(new FragFun());
        fragments.add(new FragSport());
        titles = new ArrayList<>();
        titles.add("店铺首页");
        titles.add("全部宝贝");
        titles.add("新品上架");
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);//直接设置0的话竟然不起作用,好吧.就这样迂回一下吧
    }

    private void initEvent() {
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

}
