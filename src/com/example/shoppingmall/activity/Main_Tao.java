package com.example.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.fragment.MainFragment;
import com.example.shoppingmall.fragment.MineFagment;
import com.example.shoppingmall.fragment.MoreFragment;
import com.example.shoppingmall.fragment.ShopCartFragment;
import com.example.shoppingmall.fragment.ShopFragment;
import com.example.shoppingmall.utils.IBtnCallListener;

public class Main_Tao extends Activity implements OnClickListener, IBtnCallListener {

	// 界面底部的菜单按钮
	private ImageView[] bt_menu = new ImageView[5];
	// 界面底部的菜单按钮id
	private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4 };

	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.ic_zheye_on, R.drawable.ic_shangjia_on, R.drawable.ic_gwc_on, R.drawable.ic_mine_on, R.drawable.ic_gengduo_on };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select, R.drawable.bt_menu_4_select };
	private TextView mainTextView,shopTextView,cartTextView,mineTextView,moreTextView;
	private List<TextView>list=new ArrayList<TextView>();
	/** 主界面 */
	private ShopFragment mainActivity;
	/** 商家界面 */
	private MainFragment shop;
	//我的
	private MineFagment mine;
	//更多
	private MoreFragment more;
	//购物车
	private ShopCartFragment shopCartFragment;
	private RelativeLayout rl;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tao);
		initView();
	}

	// 初始化组件
	private void initView() {
		rl=(RelativeLayout) findViewById(R.id.show_layout);
		mainTextView=(TextView) findViewById(R.id.maintextview);
		shopTextView=(TextView) findViewById(R.id.shoptextview);
		cartTextView=(TextView) findViewById(R.id.carttextview);
		mineTextView=(TextView) findViewById(R.id.minetextview);
		moreTextView=(TextView) findViewById(R.id.moretextview);
		list.add(mainTextView);
		list.add(shopTextView);
		list.add(cartTextView);
		list.add(mineTextView);
		list.add(moreTextView);
		mainTextView.setTextColor(getResources().getColor(R.color.top_background));
		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// 初始化默认显示的界面
		/*if (mainActivity == null) {
			mainActivity = new MainFragment();
			addFragment(mainActivity);
			showFragment(mainActivity);
		} else {
			showFragment(mainActivity);
		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);*/
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		//if(mainActivity==null){
			mainActivity=new ShopFragment();
		//}
		ft.replace(R.id.show_layout, mainActivity);
		ft.commit();
		textColor(0);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_menu_0:
			// 主界面
			/*if (mainActivity == null) {
				mainActivity = new MainFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(mainActivity);
				showFragment(mainActivity);
			} else {
				if (mainActivity.isHidden()) {
					showFragment(mainActivity);
				}
			}*/
			FragmentTransaction ft = this.getFragmentManager().beginTransaction();
			if(mainActivity==null){
				mainActivity=new ShopFragment();
			}
			ft.replace(R.id.show_layout, mainActivity);
			ft.commit();
			textColor(0);
			break;
		case R.id.iv_menu_1:
			// 商家
			/*if (shop == null) {
				shop = new ShopFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!shop.isHidden()) {
					addFragment(shop);
					showFragment(shop);
				}
			} else {
				if (shop.isHidden()) {
					showFragment(shop);
				}
			}*/
			FragmentTransaction ft1 = this.getFragmentManager().beginTransaction();
			//if(shop==null){
				shop=new MainFragment();
			//}
			ft1.replace(R.id.show_layout, shop);
			ft1.commit();
			textColor(1);
			break;
			//购物车
		case R.id.iv_menu_2:
			/*if(shopCartFragment==null){
				shopCartFragment=new ShopCartFragment();
				if(!shopCartFragment.isHidden()){
					addFragment(shopCartFragment);
					showFragment(shopCartFragment);
				}
			}else{
				if(shopCartFragment.isHidden()){
					showFragment(shopCartFragment);
				}
			}*/
			FragmentTransaction ft2 = this.getFragmentManager().beginTransaction();
			if(shopCartFragment==null){
				shopCartFragment=new ShopCartFragment();
			}
			ft2.replace(R.id.show_layout, shopCartFragment);
			ft2.commit();
			textColor(2);
			break;
		case R.id.iv_menu_3:
			// 我的界面
			/*if (mine == null) {
				mine = new MineFagment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!mine.isHidden()) {
					addFragment(mine);
					showFragment(mine);
				}
			} else {
				if (mine.isHidden()) {
					showFragment(mine);
				}
			}*/
			FragmentTransaction ft3 = this.getFragmentManager().beginTransaction();
			if(mine==null){
				mine=new MineFagment();
			}
			ft3.replace(R.id.show_layout, mine);
			ft3.commit();
			textColor(3);
			break;
		case R.id.iv_menu_4:
			// 更多
			/*if (more == null) {
				more = new MoreFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!more.isHidden()) {
					addFragment(more);
					showFragment(more);
				}
			} else {
				if (more.isHidden()) {
					showFragment(more);
				}
			}*/
			FragmentTransaction ft4 = this.getFragmentManager().beginTransaction();
			if(more==null){
				more=new MoreFragment();
			}
			ft4.replace(R.id.show_layout, more);
			ft4.commit();
			textColor(4);
			break;
		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}
	public void textColor(int position){

		for(int i=0;i<list.size();i++){
			if(i==position){
				list.get(position).setTextColor(getResources().getColor(R.color.top_background));
			}else{
				list.get(i).setTextColor(getResources().getColor(R.color.bg_Black));
			}

		}
	}
	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		// 设置Fragment的切换动画
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (mainActivity != null) {
			//ft.hide(mainActivity);
		}
		if (shop != null) {
			//ft.hide(shop);
		}
		if (mine != null){
			//ft.hide(mine);
		}
		if (more != null){
			//ft.hide(more);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
	/** Fragment的回调函数 */
	@SuppressWarnings("unused")
	private IBtnCallListener btnCallListener;

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			btnCallListener = (IBtnCallListener) fragment;
		} catch (Exception e) {
		}

		super.onAttachFragment(fragment);
	}

	/**
	 * 响应从Fragment中传过来的消息
	 */
	@Override
	public void transferMsg() {
		if (mainActivity == null) {
			mainActivity = new ShopFragment();
			//addFragment(mainActivity);
			//showFragment(mainActivity);
		} else {
			//showFragment(mainActivity);
		}
		bt_menu[3].setImageResource(select_off[3]);
		bt_menu[0].setImageResource(select_on[0]);

		System.out.println("由Fragment中传送来的消息");
	}

}
