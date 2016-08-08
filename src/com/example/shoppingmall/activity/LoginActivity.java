package com.example.shoppingmall.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingmall.R;
import com.example.shoppingmall.utils.AppConstants;
import com.example.shoppingmall.utils.Constantsss;
import com.example.shoppingmall.utils.Consts;
import com.example.shoppingmall.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends Activity implements OnClickListener{
	private TextView registertv;
	private LinearLayout userll,phonell;
	private TextView usertv,phonetv;
	private EditText userEditText,pwdEditText,phoneEditText,codeEditText;
	private Button userbtn,codebutton,loginbycode;
	private ImageView backimage,clickforqq,clickforweixin,clickforweibo,pwdimageview;
	private String cartOrder;
	private String cartTime;
	private SharedPreferences sp;
	private View userView,phoneView;
	private String code;
	private String nickname;
	private static String mappid;
	private static String APP_ID="";
	private Oauth2AccessToken mAccessToken;
	/** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;	
	private Tencent mTencent;
	private SendAuth.Req req;
	private IWXAPI msgapi;
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
					codebutton.setText("重新获取"+"("+String.valueOf(time)+")");
				}else{
					codebutton.setClickable(true);
					codebutton.setText("获取验证码");
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
		setContentView(R.layout.login);
		intviews();
		addlisteners();
		//初始化第三方信息
		intetQQinfor();
		intetWBinfo();
	}
	private void intetQQinfor() {
		mappid = AppConstants.APP_ID;
		//String s=mappid;
		//mappid = "222222";
		if (mTencent == null) {
			mTencent = Tencent.createInstance(mappid, LoginActivity.this);
			//Log.i("", s);
		}
	}

	private void intetWBinfo() {

		mAuthInfo = new AuthInfo(this, Constantsss.APP_KEY, Constantsss.REDIRECT_URL, Constantsss.SCOPE);
	}
	private void intviews() {
		// TODO Auto-generated method stub
		phoneEditText=(EditText) findViewById(R.id.phoneEditText);
		codeEditText=(EditText) findViewById(R.id.phonecode);
		codebutton=(Button) findViewById(R.id.codeButton);
		loginbycode=(Button) findViewById(R.id.buttonphone);
		registertv=(TextView) findViewById(R.id.register);
		userll=(LinearLayout) findViewById(R.id.phone);
		phonell=(LinearLayout) findViewById(R.id.phonelogin);
		usertv=(TextView) findViewById(R.id.usertv);
		phonetv=(TextView) findViewById(R.id.phonetv);
		userEditText=(EditText) findViewById(R.id.useredittext);
		pwdEditText=(EditText) findViewById(R.id.passwordedit);
		userbtn=(Button) findViewById(R.id.userbutton);
		userView=findViewById(R.id.userView);
		phoneView=findViewById(R.id.phoneView);
		backimage=(ImageView) findViewById(R.id.backimage);
		clickforqq=(ImageView) findViewById(R.id.clickforqq);
		clickforweibo=(ImageView) findViewById(R.id.clickforweibo);
		clickforweixin=(ImageView) findViewById(R.id.clickforweixin);
		pwdimageview=(ImageView) findViewById(R.id.pwdimageview);
		sp=getSharedPreferences("user_id", Context.MODE_PRIVATE);
		//if(sp.getString("phone", "")!=null){
			//userEditText.setText(sp.getString("phone", ""));
			//phoneEditText.setText(sp.getString("phone", ""));
			
		//}
	}
	private void addlisteners() {
		// TODO Auto-generated method stub
		registertv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		usertv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phonell.setVisibility(View.GONE);
				userll.setVisibility(View.VISIBLE);
				userView.setBackgroundColor(getResources().getColor(R.color.top_background));
				phoneView.setBackgroundColor(getResources().getColor(R.color.my_background_jianxi));
			}
		});
		phonetv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phonell.setVisibility(View.VISIBLE);
				userll.setVisibility(View.GONE);
				userView.setBackgroundColor(getResources().getColor(R.color.my_background_jianxi));
				phoneView.setBackgroundColor(getResources().getColor(R.color.top_background));
			}
		});
		userbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userEditText.getText().toString()!=null&&pwdEditText.getText().toString()!=null){
					//用户名和密码登录
					new Logintask().execute(Consts.URL);
				}else{
					ToastUtil.showToast(LoginActivity.this, "用户名或密码为空");
				}
			}
		});
		//获取验证码
		codebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetCodetask().execute(Consts.URL);
				codebutton.setClickable(false);
				Timer timer=new Timer(true);
				TimerTask task=new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					}
				};
				timer.schedule(task, 0, 1000);
			}
		});
		loginbycode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phoneEditText.getText().toString()!=null&&codeEditText.getText().toString()!=null){
					//手机号快捷登录
					new QuickLogintask().execute(Consts.URL);
				}else{
					ToastUtil.showToast(LoginActivity.this, "手机号或验证码为空");
				}
			}
		});
		backimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginActivity.this.finish();
			}
		});
		//qq登录
		clickforqq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTencent.login(LoginActivity.this, "all", loginListener);
			}
		});
		//微博登录
		clickforweibo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
				mSsoHandler.authorize(new AuthListener());
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//微信登录
		case R.id.clickforweixin:
			msgapi=WXAPIFactory.createWXAPI(this,APP_ID ,true);
			msgapi.registerApp(APP_ID);
			req=new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			//req.state = "carjob_wx_login";
			req.state = "wechat_sdk_demo_test";
			//吊起微信登录
			msgapi.sendReq(req);
			finish();
			break;
		}
		
	}
	class QuickLogintask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "quickLogin");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", phoneEditText.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("user", "quickLogin");
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
								String userid=object.getString("user_id");
								Editor editor = sp.edit();
								editor.putString("userid", userid);
								editor.putString("phone", userEditText.getText().toString());
								editor.commit();
								ToastUtil.showToast(LoginActivity.this, "登录成功");
								LoginActivity.this.finish();
							}else{
								ToastUtil.showToast(LoginActivity.this, "用户名或密码错误");
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
				jsonObject.put("phoneNumber", phoneEditText.getText().toString());
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
							}else{
								ToastUtil.showToast(LoginActivity.this, "服务器响应失败");
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

	class Logintask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			try {
				HttpUtils httpUtils=new HttpUtils();
				RequestParams requestParams=new RequestParams();
				requestParams.addBodyParameter("c", "user");
				requestParams.addBodyParameter("a", "login");
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("phoneNumber", userEditText.getText().toString());
				jsonObject.put("password", pwdEditText.getText().toString());
				requestParams.addBodyParameter("param", jsonObject.toString());
				cartTime=new Date().getTime()+"";
				getCartInfo("user", "login");
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
								String userid=object.getString("user_id");
								Editor editor = sp.edit();
								editor.putString("userid", userid);
								editor.putString("phone", phonetv.getText().toString());
								editor.commit();
								ToastUtil.showToast(LoginActivity.this, "登录成功");
								LoginActivity.this.finish();
							}else{
								ToastUtil.showToast(LoginActivity.this, "用户名或密码错误");
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUEST_LOGIN
				|| requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data,
					loginListener);
		}
		 // SSO ��Ȩ�ص�
        // ��Ҫ������ SSO ��½�� Activity ������д onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
		super.onActivityResult(requestCode, resultCode, data);
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
			//updateUserInfo();
		}
	};
	private UserInfo mInfo;
	private String openid;

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				//com.sun.shoppingmall.tools.Util.showResultDialog(Login.this,
						//"����Ϊ��", "��¼ʧ��");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				//com.sun.shoppingmall.tools.Util.showResultDialog(Login.this,
					//	"����Ϊ��", "��¼ʧ��");
				return;
			}
			initOpenidAndToken((JSONObject) response);
			updateUserInfo();
		//	Util.showResultDialog(Login.this, response.toString(),
				//	"��¼�ɹ�");
			//doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {
			//initOpenidAndToken(values);
		}

		@Override
		public void onError(UiError e) {
			//com.sun.shoppingmall.tools.Util.toastMessage(Login.this,
				//	"onError: " + e.errorDetail);
		//	com.sun.shoppingmall.tools.Util.dismissDialog();
		}

		@Override
		public void onCancel() {
		}
	}
	
	// ����Ĵ��� �������Tencent �������洫�͸��������ݣ��������´ε���ݵ�����...
	public void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			//openid  00DC31068890F54D0CD8668E58D71839
			openid = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openid)) {
				// ����������д��� �õ�֮�� ����������ʵ�� mTencent.isSessionValid() Ϊtrue �ˣ�֮ǰ
				// Ϊfalse
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openid);
			}
		} catch (Exception e) {
		}
	}

	// ��������������û����û����ͷ����Ϣ�� �ӿ�
	private void updateUserInfo() {

		IUiListener listener = new IUiListener() {

			
			@Override
			public void onError(UiError e) {
			}

			@Override
			public void onComplete(final Object response) {

				JSONObject js = (JSONObject) response;
				nickname = js.optString("nickname");
				//String s=nickname;
				//Log.i("nickname", s);
				//System.out.println(openid + "-----------" + nickname);
				//PostForLogin postLogin = new PostForLogin(Login.this, mHandler,openid, "qq");
			}
			@Override
			public void onCancel() {

			}
		};
		// �Ҿ���һ�� �����д��� �ǵ����Ի����ô....
		mInfo = new UserInfo(this, mTencent.getQQToken());
		mInfo.getUserInfo(listener);

	}
	
	//�Զ���һ���ص���  
		class AuthListener implements WeiboAuthListener{

			@Override
			public void onCancel() {
				
			}

			@Override
			public void onComplete(Bundle values) {
				   mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		            //�������ȡ�û������ �绰������Ϣ 
		            String  phoneNum =  mAccessToken.getPhoneNum();
		          //  System.out.println("���뿴���û�id:"+mAccessToken.getUid());
		            if (mAccessToken.isSessionValid()) {
		                // ��ʾ Token
		                // ���� Token �� SharedPreferences
		              //  AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
		               // Toast.makeText(Login.this, 
		                       // "��Ȩ�ɹ�",Toast.LENGTH_SHORT).show();
		            } else {          	
		            	 Toast.makeText(LoginActivity.this, 
			                        "��Ȩʧ��...",Toast.LENGTH_SHORT).show();
		            }
		            String openid=mAccessToken.getUid();
		           // PostForLogin forLoginweibo=new PostForLogin(Login.this, mHandler, openid, "weibo");
		           // thirdUser = new ThirdUserInfo();  
	               // thirdUser.setThirdID( mAccessToken.getUid());  //mAccessToken.getUid() ����ȡ��UID����Ϊ��ݵ�Ψһ��ʾ  
	              // UsersAPI mUsersAPI = new UsersAPI( mAccessToken );  
	              //  long uid = Long.parseLong(mAccessToken.getUid());  
	              // mUsersAPI.show(uid, mListener); //��ȡ�û�����Ϣ  
			}

			@Override
			public void onWeiboException(WeiboException arg0) {
				
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
