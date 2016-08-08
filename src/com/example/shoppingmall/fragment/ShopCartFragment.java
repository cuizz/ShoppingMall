package com.example.shoppingmall.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.expandablelistdemo.helper.ChildData;
import com.example.expandablelistdemo.helper.DataHolder;
import com.example.expandablelistdemo.helper.GroupData;
import com.example.expandablelistdemo.helper.ViewHolder;
import com.example.expandablelistdemo.utils.DeleteProduct;
import com.example.expandablelistdemo.utils.UpProductNum;
import com.example.expandablelistdemo.utils.Utils;
import com.example.expandablelistdemo.view.ChildView;
import com.example.expandablelistdemo.view.ChildView.OnChildClickListener;
import com.example.expandablelistdemo.view.GroupView;
import com.example.expandablelistdemo.view.GroupView.OnGroupClickListeners;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.ProductsConfirmActivity;
import com.example.shoppingmall.utils.PostToCartList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class ShopCartFragment extends Fragment  implements GroupView.OnGroupClickListeners, ChildView.OnChildClickListener{
	private TextView totalPirce;
	 
	private LinearLayout checkOut;
	/**
	 * ExpandableListView 当点击他的父 控件 时 可以将它的 子item控件显示出来 最关键的就是 设置 adapter 必须继承
	 * baseExpandbalelistAdapter 核心 就是继承 这个 adapter
	 */
	private ExpandableListView listView;
	private DataHolder dataHolder = new DataHolder();
	private ViewHolder viewholder = new ViewHolder();
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;
	private List<GroupData> contentData;
	private ExpandableListAdapter adapter;
	private Dialog dialog;
	private CheckBox checkall;
	private TextView tv,bianyiTV;
	private Button resultBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.shop_cart, null);
		listView = (ExpandableListView) view
				.findViewById(R.id.listview);
		tv = (TextView) view.findViewById(R.id.tv_gwc);
		bianyiTV=(TextView) view.findViewById(R.id.bianjiTV);
		bianyiTV.setText("编译");
		bianyiTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(bianyiTV.getText().toString().equals("编译")){
					bianyiTV.setText("确定");
					resultBtn.setText("删除");
				}else{
					bianyiTV.setText("编译");
					resultBtn.setText("结算");
				}
			}
		});
		
		// 这个是全选 是显示出来的 总钱数
		totalPirce = (TextView) view.findViewById(R.id.tatalprice);
		// 这个是 确认下单的按钮
		//checkOut = (LinearLayout) view.findViewById(R.id.checkOut);
		resultBtn=(Button) view.findViewById(R.id.resultButton);
		// 下面初始化imageLoader
		initImageLoader();
		PostToCartList cartList=new PostToCartList(getActivity(), "", mHandlerr, listView);
		cartList.addListData();
		// 这个是 checkbox 的选中 钮 全选的选中 钮
				checkall = (CheckBox) view.findViewById(R.id.btn_select_all);
				// 选中 改变的监听
				checkall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							//这个 是 设置 所有的 组 跟  子空间 全选  是怎么 全选的  呢 --》需要看看
							
							dataHolder.setAllGroupAndChildChecked();
							viewholder.setAllGroupAndChildChecked();
							double price = dataHolder.getotalPrice();
							totalPirce.setText(String.valueOf(price));
							
						} else {
							
							//否则 全都 没选中
							dataHolder.setAllGroupAndChildUnChecked();
							viewholder.setAllGroupAndChildUnChecked();
							double price = dataHolder.getotalPrice();
							totalPirce.setText(String.valueOf(price));
							
						}
					}
				});
		return view;
	}
	Handler mHandlerr = new Handler() {

		private String productone;
		private String cart_id;
		private String goods_id;
		private String number;

		@Override
		public void handleMessage(Message msg) {
			// 这消息 是从second 里面 来的

			switch (msg.what) {
			case 12:
				// 这个就是购物车 里面的   这个  12  就是  11
				JSONObject js = (JSONObject) msg.obj;
				//Log.d("sht", "屏道好 为  12的  js ====》" + js);

				if (dialog != null) {
					dialog.dismiss();
					dialog = null;
				}
				try {
					String product = js.getString("result");
					//Log.d("sht", "product-->" + product);
					JSONArray ary = new JSONArray(product);
					JSONObject unkown = (JSONObject) ary.get(0);
					productone = unkown.getString("products");
					JSONArray aryone = new JSONArray(productone);
					JSONObject unkownone = aryone.getJSONObject(0);
					//cart_id = unkownone.getString("cart_id");
					goods_id = unkownone.getString("goods_id");
					//number = unkownone.getString("number");
					//Log.d("sht", "cart_id-->" + cart_id);
					resultBtn.setOnClickListener(new OnClickListener() {
						// 当点击了确认下单的时候 那么我们就跳转到支付界面 去实现支付功能

						@Override
						public void onClick(View v) {
							if(resultBtn.getText().toString().equals("结算")){
								
							try {
								// 获取的 json 的字符串
								String checkedList = getJson();
								// ProductsConfirmActivity.class 为 支付功能的显示
								SharedPreferences sf = getActivity()
										.getSharedPreferences("user_id",
												Activity.MODE_PRIVATE);
								Intent intent = new Intent(getActivity(),
										ProductsConfirmActivity.class);
								intent.putExtra("checkedList", checkedList);
								intent.putExtra("cart_id", "12");
								intent.putExtra("goods_id", goods_id);
								intent.putExtra("number", number);
								
								String str = sf.getString("id", "-1");
								intent.putExtra("user_id", "45");
								//Log.d("sht", "user_id--->" + str);
								startActivity(intent);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							}else{
								List<GroupData> listInfo = dataHolder.getCheckedDataList();
								for(int i=0;i<listInfo.size();i++){
									contentData.remove(listInfo.get(i));
								}
								adapter.notifyDataSetChanged();	
								resultBtn.setText("结算");
								bianyiTV.setText("编译");
							}
						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				// 加载数据   这个地方   已经把 数据源  给返回来了   TODO
				contentData = setContetData(js);
				//已经把  这个  group 的数据 传递到了    dataholer  层次  里面了
				dataHolder.setContentData(contentData);
				// 下面的是向listView里面填充数据...
				//if(adapter==null){
					//adapter=new 
					//adapter = new ExpandableListAdapter(geta);
				//}
				newAdapter();
				listView.setAdapter(adapter);
				
				// 首次加载全部展开
				for (int i = 0; contentData != null && i < contentData.size(); i++) {
					listView.expandGroup(i);
				}
				// 去掉系统默认的箭头图标
				listView.setGroupIndicator(null);
				// 点击Group不收缩
				listView.setOnGroupClickListener(new OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						return true;
					}
				});
				break;
			}
		}
	};

	public Handler getHandler() {
		return mHandlerr;
	}

	private void initImageLoader() {
		// 创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				this.getActivity()).writeDebugLogs().build();// default
		ImageLoader.getInstance().init(configuration);
		// 获取图片加载实例
		mImageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheInMemory(false)
				.cacheOnDisc(false).bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
	}
	// 最主要的是adapter
		protected void newAdapter() {
			adapter = new ExpandableListAdapter(this);
		}
		/**
		 * 删除子控件的 方法   这个第地方是回调  子控件 里面的   监听 方法
		 */
		
		@Override
		public void onDeleteChilde(int groupPosition, int childPosition) {
			//得子数据
			ChildData childdata = dataHolder.getChildData(groupPosition,
					childPosition);
			//得到 子的 id 
			String cartId = childdata.getCartId();
			//这一部去 请求 删除 订单的 方法   也是去消订单的方法
			//DeleteProduct deleteProduct = new DeleteProduct(getActivity(),
					//preferences.getString("id", "-1"), cartId);
			DeleteProduct deleteProduct = new DeleteProduct(getActivity(),
					"45", cartId);
			//去移除 这个 订单  数据层
			dataHolder.deleteChildData(groupPosition, childPosition);
			//这个 是 视图层   清空 集合 的 方法
			viewholder.deleteAllgroupMap();
			//  然后通知 adapter  更新
			adapter.notifyDataSetChanged();
			
			double price = dataHolder.getotalPrice();
			
			totalPirce.setText(String.valueOf(price));
		}

		/**
		 * 更新子空间的 方法
		 */
		@Override
		public void onUpdataChild(int groupPositon, int childPosition, String a) {
			//传过去的 数 数量
			dataHolder.onChangeChildData(groupPositon, childPosition, a);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));
		}

		/**
		 * 子空间 已经被选中的方法
		 */
		@Override
		public void onChildChecked(int groupPosition, int childPosition) {
			dataHolder.setChildChecked(groupPosition, childPosition);
			viewholder.setChildChecked(groupPosition, childPosition);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));

		}

		/**
		 * 子空间 没有 被选中的方法
		 */
		@Override
		public void onChildUnChecked(int groupPosition, int childPosition) {
			dataHolder.setChildUnChecked(groupPosition, childPosition);
			viewholder.setChildUnChecked(groupPosition, childPosition);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));
		}

		/**
		 * 这个 组 被选中那个的方法
		 */
		@Override
		public void onGroupChecked(int groupPosition) {
			dataHolder.setGroupChecked(groupPosition);
			viewholder.setGroupChecked(groupPosition);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));
			
		}

		/**
		 * 这个 组 没有被选中的方法
		 */
		@Override
		public void onGroupUnChecked(int groupPosition) {
			dataHolder.setGroupUnChecked(groupPosition);
			// 我终于看明白了，，，这个viewholder 才是决定checkbox 有没有被选中的地方 的...
			viewholder.setGroupUnChecked(groupPosition);

			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));

		}

		@Override
		public void onSetingClicked(int groupPosition) {
			viewholder.setGroupSettingChecked(groupPosition);
			dataHolder.setGroupSetingChecked(groupPosition);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));
		}

		@Override
		public void onSetingUnChecked(int groupPosition) {
			viewholder.setGroupSettingUnChecked(groupPosition);
			for (int i = 0; i < dataHolder.getChildCount(groupPosition); i++) {
				ChildData childdata = dataHolder.getChildData(groupPosition, i);
				String cartId = childdata.getCartId();
				String productNum = childdata.getChildNum();
				UpProductNum upProductNum = new UpProductNum(getActivity(),
						"45", cartId, productNum);
			}
			dataHolder.setGroupSetingUnChecked(groupPosition);
			double price = dataHolder.getotalPrice();
			totalPirce.setText(String.valueOf(price));
		}

		
		
		public class ExpandableListAdapter extends BaseExpandableListAdapter {
			//
			private Fragment activity;

			// 构造 方法中只需要传过来一个activity
			public ExpandableListAdapter(Fragment activity) {
				this.activity = activity;
			}

			//
			// 这个方法是得到 某一个大组。。。
			@Override
			public Object getGroup(int groupPosition) {
				return dataHolder.getGroupData(groupPosition);
			}

			// 下面的这个方法是得到 这个list 表里面一共有几个这样的大组。 。。。
			@Override
			public int getGroupCount() {
				return dataHolder.getGroupCount();
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return dataHolder.getChildData(groupPosition, childPosition);
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return dataHolder.getChildCount(groupPosition);
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			// 看看孩子是不是可以被点击的...这个设置咋样貌似都没有啥影响的...
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				return false;
			}

			@Override
			public View getGroupView(final int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				Utils.log("getGroupView:[" + groupPosition + "]");
				Context context=getActivity();
				final GroupView groupView = new GroupView((OnGroupClickListeners) activity,context);
				//final GroupView groupView =null;
				groupView.setGroupPosition(groupPosition);

				GroupData groupData = (GroupData) getGroup(groupPosition);
				// 第一次进来 肯定是没被 选中的啊...为false 我感觉下面的代码都是 动态监听的，，试试看下子 经验证，我的
				// 想法是错的，，这里的代码 除了监听器之外 别的都不能实现动态 的监听的这样的一个效果...
				groupView.getSelectGroup().setChecked(groupData.isGroupSelected());
				if (groupData.isSetingCkicked()) {
					groupView.getGroupSeting().setText("确定");
				} else {
					groupView.getGroupSeting().setText("编辑");
				}

				groupView.getGroupName().setText(groupData.getGroupName());
				viewholder.setGroupView(groupPosition, groupView);
				return groupView;
			}

			@Override
			public View getChildView(final int groupPosition,
					final int childPosition, boolean isLastChild, View convertView,
					ViewGroup parent) {

				Utils.log("getChildView:[" + groupPosition + "," + childPosition
						+ "]");

				ImageHolder holder;
				if (convertView == null) {
					holder = new ImageHolder();
					convertView = new ChildView((OnChildClickListener) activity,
							getActivity());
					holder.childView = (ChildView) convertView;
					convertView.setTag(holder);
				} else {
					holder = (ImageHolder) convertView.getTag();
				}
				ChildView childView = holder.childView;
				// 下面的这两个是分别设定父类的position 和子类的position的... 这个地方的作用是什么 ？
				childView.setGroupPosition(groupPosition);
				childView.setChildPosition(childPosition);

				ChildData childData = (ChildData) getChild(groupPosition,
						childPosition);
				// 下面的这个是显示该checkbox 是否被选中的...这个还有一个特点 就是复用的时候 。。可以用的着... 这点十分的关键...
				childView.getSelectChild().setChecked(childData.isChildSelected());
				if (childData.isChildSetingClicked()) {
					childView.getChildNamee().setVisibility(View.VISIBLE);
					childView.getChildNameee().setVisibility(View.GONE);
				} else {
					childView.getChildNamee().setVisibility(View.GONE);
					childView.getChildNameee().setVisibility(View.VISIBLE);
				}
				childView.getChildImage().setImageBitmap(childData.getBitMap());

				childView.getChildPrice().setText("￥" + childData.getChildPrice());

				childView.getChildName().setText(childData.getChildName());

				childView.getChildNum().setText("x" + childData.getChildNum());
				childView.getChildNumm().setText(childData.getChildNum());

				viewholder.setChildView(groupPosition, childPosition, childView);

				return childView;
			}

			class ImageHolder {
				Drawable drawable;
				ChildView childView;
			}
		}

		public class ImageLoadingListenerImpl implements ImageLoadingListener {

			private ChildData childData;

			public ImageLoadingListenerImpl(ChildData childData) {
				this.childData = childData;
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				childData.setBitMap(loadedImage);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {

			}

		}

		// 这里是请求 数据 去访问 服务器 如果
		private List<GroupData> setContetData(JSONObject js) {
			
			
			List<GroupData> groupDatas = new ArrayList<GroupData>();
			//Log.i("购物车的错误",
				//	"product-====================》" + js.optJSONArray("products"));
			
			if (js.optJSONArray("result") == null) {
				tv.setVisibility(View.VISIBLE);
				tv.setText("购物车是空的哟 ，赶快去购买吧!");
				listView.setVisibility(View.GONE);
				
			} else {
				for (int i = 0; i < js.optJSONArray("result").length(); i++) {
					GroupData groupData = new GroupData();
					try {
						//这个 是      根据   date holder  里面的  东西  去  设置 组 名   
						//groupData.setGroupName(js.getJSONArray("products")
							//	.getJSONObject(0).getJSONObject("shop")
							//	.optString("shop_name"));
						groupData.setGroupName(js.getJSONArray("result")
									.getJSONObject(0).getString("name"));
						//这个是设置 组  没有选中
						groupData.setGroupSelected(false);

					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					//声明  子 集合
					final List<ChildData> items = new ArrayList<ChildData>();
					for (int j = 0; j < js.optJSONArray("result").optJSONObject(i)
							.optJSONArray("products").length(); j++) {
						
						//子对象
						ChildData childData = new ChildData();
						ImageLoadingListenerImpl imageLoader = new ImageLoadingListenerImpl(
								childData);
						// "http://www.tjx365.com/upload/goods/middle/"
						//mImageLoader.loadImage(Const.IMAGE_GOODS_MIDDLE
								//+ js.optJSONArray("product").optJSONObject(i)
										//.optJSONArray("product").optJSONObject(j)
										//.optString("img"), options, imageLoader);
						//给子 里面设置 东西
						childData.setChildName(js.optJSONArray("result")
								.optJSONObject(i).optJSONArray("products")
								.optJSONObject(j).optString("title"));
						childData.setChildNum(js.optJSONArray("result")
								.optJSONObject(i).optJSONArray("products")
								.optJSONObject(j).optString("num"));
						childData.setChildPrice(js.optJSONArray("result")
								.optJSONObject(i).optJSONArray("products")
								.optJSONObject(j).optString("market_price"));
						childData.setChildImage(js.optJSONArray("result")
								.optJSONObject(i).optJSONArray("products")
								.optJSONObject(j).optString("img"));
						
						childData.setId(js.optJSONArray("result").optJSONObject(i)
								.optJSONArray("products").optJSONObject(j)
								.optString("id"));
						//childData.setCartId(js.optJSONArray("result")
								//.optJSONObject(i).optJSONArray("products")
								//.optJSONObject(j).optString("cart_id"));
						
						//设置 子  没有选中
						childData.setChildSelected(false);
						items.add(childData);
					}
					
					
					groupData.setItems(items);
					groupDatas.add(groupData);
				}
			}
			return groupDatas;
		}

		/**
		 * 获取了 json 里边的元素
		 * 
		 * @return
		 * @throws JSONException
		 */
		private String getJson() throws JSONException {

			List<GroupData> listInfo = dataHolder.getCheckedDataList();
			if (listInfo == null) {
				return (new JSONObject()).put("result", "").toString();
			}
			/**
			 * 里面有 商品的名字 价格 数 量 注意 这里是向 json 里面 放入 了 数据 不是解析json
			 */
			JSONObject supershops = new JSONObject();
			JSONArray shops = new JSONArray();
			for (GroupData shopData : listInfo) {
				JSONObject shop = new JSONObject();
				shop.put("shop_name", shopData.getGroupName());
				JSONArray childDatalist = new JSONArray();
				List<ChildData> items = shopData.getItems();
				for (ChildData child : items) {
					JSONObject childData = new JSONObject();
					childData.put("price", child.getChildPrice());
					childData.put("num", child.getChildNum());
					childData.put("child_name", child.getChildName());
					childData.put("img", child.getChildImage());
					childData.put("product_id", child.getId());
					childDatalist.put(childData);
				}
				shop.put("childDatalist", childDatalist);
				shops.put(shop);
			}
			
			supershops.put("product", shops);
			supershops
					.put("total_price", String.valueOf(dataHolder.getotalPrice()));
			//System.out.println(supershops.toString());
			return supershops.toString();
		}

}
