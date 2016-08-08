package com.example.shoppingmall.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.example.shoppingmall.R;
import com.example.shoppingmall.utils.Consts;
import com.example.shoppingmall.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends Activity{
	private TextView phonetv,yanzhengmatv,passwordtv;
	private LinearLayout yanzhengmall ,zhucell,phonell;
	private Button btn,btn1,getcodebtn,registerbtn;
	private  EditText phoneet,codeedittext,passwordet,querenpasswordet;
	private ImageView backimageview;
	protected String cartTime;
	private String cartOrder;
	private String code;
	private int time=60;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
			//time-=time;
				//int a=0;
				if(time>0){
					time--;
					//a=time;
					getcodebtn.setText("重新获取"+"("+String.valueOf(time)+")");
				}else{
					getcodebtn.setClickable(true);
					getcodebtn.setText("获取验证码");
				}
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		intviews();
		addlisteners();
	}
	private void intviews() {
		// TODO Auto-generated method stub
		yanzhengmall=(LinearLayout) findViewById(R.id.yanzhengma);
		phonell=(LinearLayout) findViewById(R.id.phone);
		zhucell=(LinearLayout) findViewById(R.id.zhuce);
		btn=(Button) findViewById(R.id.button1);
		btn1=(Button) findViewById(R.id.yanzhengmabutton);
		getcodebtn=(Button) findViewById(R.id.getcodebtn);
		phoneet=(EditText) findViewById(R.id.phoneet);
		phonetv=(TextView) findViewById(R.id.phonetv);
		yanzhengmatv=(TextView) findViewById(R.id.yanzhengmatv);
		passwordtv=(TextView) findViewById(R.id.passwordtv);
		backimageview=(ImageView) findViewById(R.id.backimageview);
		codeedittext=(EditText) findViewById(R.id.yanzhengmaEditText);
		passwordet=(EditText) findViewById(R.id.passwordEditText);
		querenpasswordet=(EditText) findViewById(R.id.passwordquerenEditText);
		registerbtn=(Button) findViewById(R.id.zhucebutton);
	}
	private void addlisteners() {

		// TODO Auto-generated method stub
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phoneet.getText().toString().length()==11){
					phonell.setVisibility(View.GONE);
					yanzhengmall.setVisibility(View.VISIBLE);
					phonetv.setTextColor(getResources().getColor(R.color.bg_Black));
					yanzhengmatv.setTextColor(getResources().getColor(R.color.top_background));
				}else{
					ToastUtil.showToast(RegisterActivity.this, "手机号格式不正确");
				}
			}
		});

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(code!=null){
					//String s=code;
					//String sd=codeedittext.getText().toString();
					if(codeedittext.getText().toString().equals(code)){
						yanzhengmall.setVisibility(View.GONE);
						phonell.setVisibility(View.GONE);
						zhucell.setVisibility(View.VISIBLE);
						phonetv.setTextColor(getResources().getColor(R.color.bg_Black));
						yanzhengmatv.setTextColor(getResources().getColor(R.color.bg_Black));
						passwordtv.setTextColor(getResources().getColor(R.color.top_background));
					}
				}else{
					ToastUtil.showToast(RegisterActivity.this, "验证码不正确");
				}
			}
		});
		getcodebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new GetCodetask().execute(Consts.URL);
				Timer timer = new Timer(true);
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					}
				};
				timer.schedule(task, 0, 1000);
				getcodebtn.setClickable(false);
			}
		});
		registerbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(passwordet.getText().toString().equals(querenpasswordet.getText().toString())){
					new SetPassWordTask().execute(Consts.URL);
				}else{
					ToastUtil.showToast(RegisterActivity.this, "两次输入的密码不同");
				}
			}
		});
		backimageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegisterActivity.this.finish();
			}
		});
	}
	class SetPassWordTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "setPwd");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", phoneet.getText().toString());
				jsonObject.put("password", passwordet.getText().toString());
				jsonObject.put("repassword", querenpasswordet.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("user", "setPwd");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				//requestParams.addBodyParameter("sign", GetSign.sign("site_voucher", "sv_list"));
				requestParams.addBodyParameter("sign", cartOrder);
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								new RegisterTask().execute(Consts.URL);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
	}
	class RegisterTask extends AsyncTask<String, String, String>{
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "regist");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", phoneet.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("user", "regist");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				requestParams.addBodyParameter("sign", cartOrder);
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								//String msg=object.getString("msg");
								ToastUtil.showToast(RegisterActivity.this, "注册成功");
								Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
								startActivity(intent);
							}else{
								ToastUtil.showToast(RegisterActivity.this, "注册失败");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}
	class GetCodetask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "getcode");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", phoneet.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("user", "getcode");
				requestParams.addBodyParameter("timesnamp",cartTime);
				requestParams.addBodyParameter("openid", "1");
				//requestParams.addBodyParameter("sign", GetSign.sign("site_voucher", "sv_list"));
				requestParams.addBodyParameter("sign", cartOrder);
				httpUtils.send(HttpMethod.POST, url, requestParams,new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String response=arg0.result;
						try {
							JSONObject object=new JSONObject(response);
							if(object.getString("error").equals("0")){
								code=object.getString("code");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

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
}
