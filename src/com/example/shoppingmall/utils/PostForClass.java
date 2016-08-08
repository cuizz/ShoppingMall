package com.example.shoppingmall.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingmall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 这一部分 请求的 是 popupwindow 里面的数据
 * 
 * @author sht
 * 
 */
@SuppressLint("NewApi")
@SuppressWarnings({ "unused", "unchecked" })
public class PostForClass {
	private Context mcontext;
	private String threeId, cartTime, cartOrder;
	private JSONObject jsGoodsInfo, allStyle, pro;
	private View view;
	private Set<String> attrs1, attrs2, attrs3;
	private LinearLayout prensetPro;
	private Map<String, List<Button>> btnMap;
	private Set<String> styleSet = null;
	private Map<String, String> selectedContain;
	private JSONArray productList;
	private Button pop_ok;
	private TextView pop_num;
	private ImageView iv_adapter_grid_pic;
	private SharedPreferences preferences = null;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	public PostForClass(Context context, String threeId, View view,
			ImageLoader mImageLoader, DisplayImageOptions options) {

		this.mcontext = context;
		this.threeId = threeId;
		this.view = view;
		this.mImageLoader = mImageLoader;
		this.options = options;
		//System.out.println("我想看看我的goodsid" + threeId);

		postToCart("product", "getProductDetial", threeId);
		initeViews();
	}

	private void initeViews() {

		prensetPro = (LinearLayout) view.findViewById(R.id.prensetPro);
		prensetPro.removeAllViews();
		iv_adapter_grid_pic = (ImageView) view
				.findViewById(R.id.iv_adapter_grid_pic);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_ok = (Button) view.findViewById(R.id.pop_ok);
		//点击了  确定按钮  
		pop_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				boolean allChecked = true;
				boolean allCheckedd = true;
				preferences = mcontext.getSharedPreferences("user_id",
						Activity.MODE_PRIVATE);
				if (styleSet.isEmpty()) {
					if (preferences.getString("id", "-1").equals("-1")) {
						//如果 是 没有登陆的情况下   那么我们 就 跳转到  登陆 界面
						//Intent intent = new Intent(mcontext,
								//ProductShopingCartLogin.class);
						try {
							//intent.putExtra("product_id", productList.getJSONObject(0).optString("product_id"));
							
							//intent.putExtra("product_num", pop_num.getText().toString());
							//mcontext.startActivity(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							AddCart addCart = new AddCart(mcontext, preferences
									.getString("id", "-1"), productList
									.getJSONObject(0).optString("product_id"),
									pop_num.getText().toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				} else {
					for (String sty : styleSet) {
						if (selectedContain.get(sty).equals("[\\s\\S]*")) {
							allChecked = false;
						} else {
							allChecked = true;
						}
						allCheckedd = allCheckedd && allChecked;
					}
					if (allCheckedd) {
						Toast.makeText(v.getContext(), "所有的属性选择完成...",
								Toast.LENGTH_LONG).show();
						for (int i = 0; i < productList.length(); i++) {
							try {
								pro = productList.getJSONObject(i);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							if (styleSet.size() == 1) {
								try {
									if (pro.getString("attr1").equals(
											selectedContain.get("attr1"))) {
										if (preferences.getString("id", "-1")
												.equals("-1")) {
											//Intent intent = new Intent(
												//	mcontext,
													//ProductShopingCartLogin.class);
											Intent intent=new Intent();
											intent.putExtra("product_id",
													pro.optString("product_id"));
											intent.putExtra("product_num",
													pop_num.getText()
															.toString());
											mcontext.startActivity(intent);
										} else {
											AddCart addCart = new AddCart(
													mcontext,
													preferences.getString("id",
															"-1"),
													pro.optString("product_id"),
													pop_num.getText()
															.toString());
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							if (styleSet.size() == 2) {
								try {
									if (pro.getString("attr1").equals(
											selectedContain.get("attr1"))) {
										if (pro.getString("attr2").equals(
												selectedContain.get("attr2"))) {
											if (preferences.getString("id",
													"-1").equals("-1")) {
											//	Intent intent = new Intent(
														//mcontext,
														//ProductShopingCartLogin.class);
												Intent intent=new Intent();
												intent.putExtra(
														"product_id",
														pro.optString("product_id"));
												intent.putExtra("product_num",
														pop_num.getText()
																.toString());
												mcontext.startActivity(intent);
											} else {
												AddCart addCart = new AddCart(
														mcontext,
														preferences.getString(
																"id", "-1"),
														pro.optString("product_id"),
														pop_num.getText()
																.toString());
											}
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
							if (styleSet.size() == 3) {
								try {
									if (pro.getString("attr1").equals(
											selectedContain.get("attr1"))) {
										if (pro.getString("attr2").equals(
												selectedContain.get("attr2"))) {
											if (pro.getString("attr3").equals(
													selectedContain
															.get("attr3"))) {
												if (preferences.getString("id",
														"-1").equals("-1")) {
													//Intent intent = new Intent(
														//	mcontext,
															//ProductShopingCartLogin.class);
													Intent intent=new Intent();
													intent.putExtra(
															"product_id",
															pro.optString("product_id"));
													intent.putExtra(
															"product_num",
															pop_num.getText()
																	.toString());
													mcontext.startActivity(intent);
												} else {
													AddCart addCart = new AddCart(
															mcontext,
															preferences
																	.getString(
																			"id",
																			"-1"),
															pro.optString("product_id"),
															pop_num.getText()
																	.toString());
												}
											}

										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
						
					} else {
						Toast.makeText(v.getContext(), "请选择剩下的属性...",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	private void goodsInfo(String str, String strr, String strrr, String strrrr) {
		jsGoodsInfo = new JSONObject();
		try {
			jsGoodsInfo.put("pid", "6");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void postToCart(final String str, final String strr, String threeId) {
		goodsInfo(threeId, "100", "sunxueleid@sina.com", "1234564");
		RequestQueue mQueue = Volley.newRequestQueue(mcontext);
		StringRequest stringRequest = new StringRequest(Method.POST,
				Consts.URL, new Response.Listener<String>() {
					private JSONArray arrays;
					private JSONArray arrayss;

					@SuppressLint("NewApi")
					@Override
					public void onResponse(String response) {
						//System.out.println("我是一个好人的啊..." + response);
						try {
							JSONObject json1 = new JSONObject(response);
							JSONObject json2=json1.getJSONObject("attrs");
							JSONArray array=json2.getJSONArray("result");
							//if (json.has("result")) {
								// 这个方法是过滤掉里面是不是有result的这样的一个情况...
								styleSet = new HashSet<String>();
							//} else {
								// 准备了 一个 集合 分别 去 放这 3组东西
								attrs1 = new HashSet<String>();
								attrs2 = new HashSet<String>();
								attrs3 = new HashSet<String>();
								productList = new JSONArray();
								allStyle = new JSONObject();
								for (int i = 0; i < array.length(); i++) {
									JSONObject p = new JSONObject();
									arrays=array.optJSONObject(0).getJSONArray("values");
									arrayss=array.optJSONObject(1).getJSONArray("values");	
									arrays=array.optJSONObject(0).getJSONArray("values");
									arrayss=array.optJSONObject(1).getJSONArray("values");	
									//arraysss=array.optJSONObject(2).getJSONArray("values");
									// arrayss=array.optJSONObject(1).getJSONArray("values");
									//for(int j=0;j<arrays.length();j++){
										if (arrays.optJSONObject(0).optString("value").isEmpty()) {
										} else {
											for(int a=0;a<arrays.length();a++){
												attrs1.add(arrays.optJSONObject(a).optString("value"));
												p.accumulate("品牌", arrays.optJSONObject(a).optString("value"));
											}	
										}
										if (arrayss.optJSONObject(1).optString("value").isEmpty()) {
										} else {
											for(int b=0;b<arrayss.length();b++){
												attrs2.add(arrayss.optJSONObject(b).optString("value"));
												p.accumulate("网络", arrayss.optJSONObject(b).optString("value"));
											}	
											//attrs2.add(arrays.optJSONObject(j).optString("value"));
										}
										/*if(array.optJSONObject(2).getJSONArray("values")==null){
										}else{
											
											if (arraysss.optJSONObject(2).optString("value").isEmpty()) {
											} else {
												for(int c=0;c<arraysss.length();c++){
													attrs3.add(arraysss.optJSONObject(c).optString("value"));
												}	
												//attrs2.add(arrays.optJSONObject(j).optString("value"));
											}
										}*/
										//p.accumulate("attrs3", arraysss.optJSONObject(i).optString("value"));
										productList.put(p);
									}
								if (attrs1.isEmpty()) {
								} else {

									allStyle.put("品牌", attrs1);
								}
								if (attrs2.isEmpty()) {

								} else {
									allStyle.put("网络", attrs2);
								}
								if (attrs3.isEmpty()) {

								} else {
									//allStyle.put("attr3", attrs3);
								}
								btnMap = new HashMap<String, List<Button>>();
								Iterator<String> styles = allStyle.keys();
								while (styles.hasNext()) {
									String style = styles.next();
									// 这个 地方是自定义 的 atter 1 里面的 控件集合
									GrounpVieww layout = new GrounpVieww(
											mcontext);
									TextView title = new TextView(mcontext);
									title.setLayoutParams(new LinearLayout.LayoutParams(
											LayoutParams.WRAP_CONTENT,
											LayoutParams.WRAP_CONTENT));
									title.setText(style);
									prensetPro.addView(title);
									Set<String> sets = (Set<String>) allStyle
											.get(style);
									List<String> allContain = new ArrayList<String>(
											sets);
									List<Button> btnList = new ArrayList<Button>();
									for (int i = 0; i < allContain.size(); i++) {
										String contain = allContain.get(i);
										Button btn = new Button(mcontext);
										btn.setLayoutParams(new LinearLayout.LayoutParams(
												LayoutParams.WRAP_CONTENT,
												LayoutParams.WRAP_CONTENT));
										GradientDrawable drawable = new GradientDrawable();
										drawable.setShape(GradientDrawable.RECTANGLE);
										drawable.setStroke(1, Color.BLACK);
										drawable.setColor(Color.WHITE);
										btn.setBackground(drawable);
										btn.setText(contain);
										btn.setTextColor(Color.BLACK);
										layout.addView(btn);
										ChoiceChangeListener ccl = new ChoiceChangeListener(
												style, contain);
										btn.setOnClickListener(ccl);
										// 给这个button 设置 了一个标签 为可点...
										btn.setTag(ChoiceChangeListener.ENABLED);
										btnList.add(btn);
									}
									prensetPro.addView(layout);
									btnMap.put(style, btnList);
								}
								// 这里的keySet 是指的：尺寸 、材料、颜色。。。
								styleSet = btnMap.keySet();
								selectedContain = new HashMap<String, String>();
								// 下面的这个方法是 将上面的三个属性加上后面的那一串东西放到 一个哈希表里面 去。。。
								for (String styl : styleSet) {
									selectedContain.put(styl, "[\\s\\S]*");
								}
								// 下面的代码是判断该商品的属性是不是为空的情况
								if (styleSet.isEmpty()) {
									String productId = productList
											.getJSONObject(0).optString(
													"product_id");
									//PostForProductImage productImage = new PostForProductImage(
										//	mcontext, productId, "1",
										//	mImageLoader, options,
										//	iv_adapter_grid_pic);
								}
								checkForDisable();
							//}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("openid", "1");
				cartTime = new Date().getTime() + "";
				getCartInfo(str, strr);
				map.put("sign", cartOrder);
				map.put("a", strr);
				map.put("c", str);
				map.put("timesnamp", cartTime);
				map.put("param", jsGoodsInfo.toString());
				Log.d("sht", "sign-->" + cartOrder + ",c-->" + str + ",a-->"
						+ strr + ",timesnamp-->" + cartTime + ",param-->"
						+ jsGoodsInfo.toString());
				return map;
			}
		};
		mQueue.add(stringRequest);

	}

	public void md5sCart(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			cartOrder = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void getCartInfo(String str, String strr) {
		md5sCart(strr + str + cartTime + "liangpin");
		md5sCart(cartOrder);
	}

	private class ChoiceChangeListener implements OnClickListener {
		public static final int SELECTED = 1;
		public static final int ENABLED = 2;
		public static final int DISABLED = 3;
		private String style;
		private String contain;

		public ChoiceChangeListener(String style, String contain) {
			this.style = style;
			this.contain = contain;
		}

		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			int btnStatus = ((Integer) btn.getTag()).intValue();
			List<Button> thisLine = btnMap.get(style);
			switch (btnStatus) {
			case SELECTED:
				enableLine(thisLine);
				selectedContain.put(style, "[\\s\\S]*");
				break;
			case ENABLED:
				enableLine(thisLine);
				selectButton(btn);
				selectedContain.put(style, contain);
				boolean allChecked = true;
				boolean allCheckedd = true;
				for (String sty : styleSet) {
					if (selectedContain.get(sty).equals("[\\s\\S]*")) {
						allChecked = false;
					} else {
						allChecked = true;
					}
					allCheckedd = allCheckedd && allChecked;
				}
				if (allCheckedd) {
					for (int i = 0; i < productList.length(); i++) {
						try {
							pro = productList.getJSONObject(i);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (styleSet.size() == 1) {
							try {
								if (pro.getString("attr1").equals(
										selectedContain.get("attr1"))) {
									String productId = pro
											.optString("product_id");
									//PostForProductImage productImage = new PostForProductImage(
											//mcontext, productId, "1",
										//	mImageLoader, options,
										//	iv_adapter_grid_pic);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						if (styleSet.size() == 2) {
							try {
								if (pro.getString("attr1").equals(
										selectedContain.get("attr1"))) {
									if (pro.getString("attr2").equals(
											selectedContain.get("attr2"))) {
										String productId = pro
												.optString("product_id");
										//PostForProductImage productImage = new PostForProductImage(
											//	mcontext, productId, "1",
											//	mImageLoader, options,
											//	iv_adapter_grid_pic);
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						if (styleSet.size() == 3) {
							try {
								if (pro.getString("attr1").equals(
										selectedContain.get("attr1"))) {
									if (pro.getString("attr2").equals(
											selectedContain.get("attr2"))) {
										if (pro.getString("attr3").equals(
												selectedContain.get("attr3"))) {
											String productId = pro
													.optString("product_id");
											//PostForProductImage productImage = new PostForProductImage(
													//mcontext, productId, "1",
												//	mImageLoader, options,
													//iv_adapter_grid_pic);
										}
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}
				break;
			case DISABLED:
				return;
			}
			try {
				checkForDisable();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("NewApi")
	private void disableButton(Button btn) {
		btn.setTag(ChoiceChangeListener.DISABLED);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(1, Color.GRAY);
		drawable.setColor(Color.WHITE);
		btn.setBackground(drawable);
		btn.setTextColor(Color.GRAY);
	}

	@SuppressLint("NewApi")
	private void enableButton(Button btn) {
		btn.setTag(ChoiceChangeListener.ENABLED);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(1, Color.BLACK);
		drawable.setColor(Color.WHITE);
		btn.setBackground(drawable);
		btn.setTextColor(Color.BLACK);
	}

	private void selectButton(Button btn) {
		btn.setTag(ChoiceChangeListener.SELECTED);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(2, Color.RED);
		drawable.setColor(Color.WHITE);
		btn.setBackground(drawable);
		btn.setTextColor(Color.RED);
	}

	private void enableLine(List<Button> list) {
		for (Button btn : list) {
			this.enableButton(btn);
		}
	}

	private void checkForDisable() throws JSONException {
		for (String styl : styleSet) {
			List<Button> list = btnMap.get(styl);
			// 这一步的方法是让所有的button 都处于一个可选的状态...
			this.enableLine(list);
			for (Button btn : list) {
				// 下面的这个方法 是设置 哪一个被 选中了...第一次进入这个页面的时候 ，，，下面的这个方法是不会执行的。。。
				if (!selectedContain.get(styl).equals("[\\s\\S]*")
						&& btn.getText().equals(selectedContain.get(styl))) {
					selectButton(btn);
				}
				boolean exist = false;
				for (int i = 0; i < productList.length(); i++) {
					boolean b = true;
					JSONObject prod = productList.getJSONObject(i);
					for (String sty : styleSet) {
						String selected = null;
						if (sty.equals(styl)) {
							selected = (String) btn.getText();
						} else {
							selected = selectedContain.get(sty);
						}
						String prodAttr = prod.getString(sty);
						b = b && prodAttr.matches(selected);
					}
					exist = exist || b;
				}
				if (!exist) {
					this.disableButton(btn);
				}
			}
		}
	}
}
