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


public class IconTextTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titles;
    private List<Fragment> fragments;
    private int[] tabIcons = {
            R.drawable.ic_dp,
            R.drawable.ic_bb,
            R.drawable.ic_new
    };
    private int[] tabIconsPressed = {
            R.drawable.ic_dp_red,
            R.drawable.ic_bb_red,
            R.drawable.ic_new_red
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
    }

    private void initEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setTextColor(Color.RED);
        if (txt_title.getText().toString().equals("店铺首页")) {
            img_title.setImageResource(R.drawable.ic_dp_red);
            viewPager.setCurrentItem(0);
        } else if (txt_title.getText().toString().equals("全部宝贝")) {
            img_title.setImageResource(R.drawable.ic_bb_red);
            viewPager.setCurrentItem(1);
        } else {
            img_title.setImageResource(R.drawable.ic_new_red);
            viewPager.setCurrentItem(2);
        }
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setTextColor(Color.WHITE);
        if (txt_title.getText().toString().equals("店铺首页")) {
            img_title.setImageResource(R.drawable.ic_dp);
        } else if (txt_title.getText().toString().equals("全部宝贝")) {
            img_title.setImageResource(R.drawable.ic_bb);
        } else {
            img_title.setImageResource(R.drawable.ic_new);
        }
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

        if (position == 0) {
            txt_title.setTextColor(Color.RED);
            img_title.setImageResource(tabIconsPressed[position]);
        } else {
            txt_title.setTextColor(Color.WHITE);
            img_title.setImageResource(tabIcons[position]);
        }
        return view;
    }

}
