package com.walkin.walkin;

import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.MyButton;
import com.walkin.common.RegExpValidator;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrationActivity extends Activity {
	private MyButton myhome;
	private EditText EditText_Email;
	private EditText EditText_Email_Confirmution;
	private EditText EditText_Password;
	private EditText EditText_Password_Confirmution,EditText_Promo_Code;
	private Button Button_Next;
//	private Button goback;
	private ImageView ImageView_Email;
	private ImageView ImageView_Password;
	private ImageView ImageView_Email_Confirmution;
	private ImageView ImageView_Password_Confirmution,ImageView_Promo_Code_Icon;
	private ProgressDialog progressDialog;
	public Handler mProgressHandler;
	public boolean mIsAddPass = false;
	public boolean  mIsAddEmail= false;
	public boolean mIsAddPassComfirmut = false;
	public boolean  mIsAddEmailComfirmut= false;
	protected final static UserMenuService user_mservice = UserMenuService
			.getInstance();
	private static Context context;
	private SharedPreferences sp;
	
	//
	public String editEmailStr ;
	public String editPasswordStr ;
	public String editEmailConfirmutionStr ;
	public String editPasswordConfirmutionStr ;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_act);
		context = this;
		user_mservice.setActivity(this);
		Button_Next = (Button) findViewById(R.id.Button_Next);
		Button_Next.setOnClickListener(new NextButtonListener());
//		goback= (Button) findViewById(R.id.goback);
//		goback.setOnClickListener(new gobackListener()); 
		
		
		EditText_Email = (EditText) findViewById(R.id.EditText_Email);
		EditText_Password = (EditText) findViewById(R.id.EditText_Password);
		EditText_Email_Confirmution = (EditText) findViewById(R.id.EditText_Email_Confirmution);
		EditText_Password_Confirmution = (EditText) findViewById(R.id.EditText_Password_Confirmution);
		EditText_Promo_Code= (EditText) findViewById(R.id.EditText_Promo_Code);
		
		
		ImageView_Email = (ImageView) findViewById(R.id.ImageView_Email);
		ImageView_Password = (ImageView) findViewById(R.id.ImageView_Password);
		ImageView_Email_Confirmution = (ImageView) findViewById(R.id.ImageView_Email_Confirmution);
		ImageView_Password_Confirmution = (ImageView) findViewById(R.id.ImageView_Password_Confirmution);
		ImageView_Promo_Code_Icon= (ImageView) findViewById(R.id.ImageView_Promo_Code_Icon);
		ImageView_Promo_Code_Icon.setOnClickListener(new ImageView_Promo_Code_IconListener()); 
		
		myhome = new MyButton(this);
		Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
		EditText_Email.setOnTouchListener(new EmailEditTextListener());
		EditText_Password.setOnTouchListener(new PasswordEditTextListener());
		EditText_Email_Confirmution
				.setOnTouchListener(new EmailConfirmutionEditTextListener());
		EditText_Password_Confirmution
				.setOnTouchListener(new PasswordConfirmutionEditTextListener());
		
		  mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	             
	                case 2 :   
	                		progressDialog.dismiss();
	            			Intent intent=new Intent();
	         				intent.setClass(RegistrationActivity.this, GenderAgeActivity.class);
	         				Bundle bundle=new  Bundle();
	         				String str1="aaaaaa";
	         				bundle.putString("Data", str1);
	         				intent.putExtras(bundle);
	         				startActivityForResult(intent, 0);
	            			
	            			mIsAddPass = false;
	            			mIsAddEmail= false;
	            			mIsAddPassComfirmut = false;
	            			mIsAddEmailComfirmut= false;
//	            			finish();
	            			break;  
	                case 3 :   
	                	Log.d("Rock", msg.obj+"bbbbbbbbbbbbbbbb");
	                		progressDialog.dismiss();
	                		AlertDialog.Builder b1 = new AlertDialog.Builder(RegistrationActivity.this);
	                		b1.setTitle("抱歉");
	    		            b1.setMessage((String)msg.obj);
	    		            b1.setPositiveButton("OK", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	    		            b1.show();
	                    break; 
	                case 4:
						AlertDialog.Builder b =new AlertDialog.Builder(context);
	                	b.setTitle("抱歉");
	                	b.setMessage("无法连接网络");
	                	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        }
	                    });
	                    b.show();
						break;
	                }   
	            }   
	        } ; 
	}
	class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
		}
	}
	class ImageView_Promo_Code_IconListener implements OnClickListener{
		public void onClick(View v) {
			
//			 sp = getSharedPreferences("user", 0);
//		        if ("1".equals(sp.getString("istransparentall", null))) {
		       	 Intent intent = new Intent(RegistrationActivity.this, TransparentAllActivity.class);
			         Bundle mBundle = new Bundle();
					 mBundle.putString("Activity", "RegistrationActivity");// 压入数据
					 intent.putExtras(mBundle);
					 startActivityForResult(intent, 0);
				//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
//					}
//			AlertDialog.Builder b1 = new AlertDialog.Builder(RegistrationActivity.this);
//			b1.setTitle("欢迎使用Walkin!");
//            b1.setMessage(R.string.TextView_Registration_Description);
//            b1.setPositiveButton("好", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int which){
//                }
//            });
//            b1.show();
			
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Intent intent = getIntent();
			Bundle b =intent.getExtras();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 1:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 2:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(2, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
	            break;
		case 3:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
	            break;
		default:
	           break;
		}
	}
	class NextButtonListener implements OnClickListener {
		public void onClick(View v) {
			 editEmailStr = EditText_Email.getText().toString();
			 editPasswordStr = EditText_Password.getText().toString();
			 editEmailConfirmutionStr = EditText_Email_Confirmution.getText().toString();
			 editPasswordConfirmutionStr = EditText_Password_Confirmution.getText().toString();
				if ("".equals(editPasswordStr)) {
					ImageView_Password.setVisibility(View.VISIBLE);
				}else{
					mIsAddPass = true;
					
				}
				if (!editPasswordStr.equals(editPasswordConfirmutionStr)||"".equals(editPasswordConfirmutionStr)) {
					ImageView_Password_Confirmution.setVisibility(View.VISIBLE);
				}else{
				
					mIsAddPassComfirmut = true;
				}
				if (RegExpValidator.isEmail(editEmailStr)==false||"".equals(editEmailStr)) {
					ImageView_Email.setVisibility(View.VISIBLE);
				}else{
					mIsAddEmail= true;
				}
				if (!editEmailStr.equals(editEmailConfirmutionStr)||"".equals(editEmailConfirmutionStr)) {
					ImageView_Email_Confirmution.setVisibility(View.VISIBLE);
				}else{
					mIsAddEmailComfirmut= true;
				}
				 if (mIsAddPass==true&&mIsAddPassComfirmut==true&&mIsAddEmail==true&&mIsAddEmailComfirmut==true) {
					 if(isNetworkAvailable()){
						 progressDialog = ProgressDialog.show(RegistrationActivity.this, "Loading...", "Please wait...", true, false);
						 new Thread(mthread).start();
					 }else{
						 Message msg= Message.obtain(mProgressHandler,4);
		            	 msg.sendToTarget();
					 }
					 
					}
		}
	}
	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        	 String	userpass_md5 =RegExpValidator.MD5(editEmailStr+":walkin:"+editPasswordStr);
				// String encodePass =RegExpValidator.encode(s.getBytes());
				// String editEmailStr_test="kevin.hu@gmail.com";
				 Map<String,String>params=new HashMap<String,String>()  ;
		    	 params.put("email",editEmailStr);
		    	 params.put("password",userpass_md5);
		    	 params.put("referral",EditText_Promo_Code.getText().toString());
		    	Log.d("Rock", userpass_md5+":userpass_md5");
				 String urlcons = SppaConstant.WALKIN_URL_REGISTER+"?apikey=BYauu6D9";
				 user_mservice.setRetrieveUrl(urlcons);
				 user_mservice.retrieveUserRegisterInfo(params);
				 
				 String user_register_meta_code = user_mservice.getCode();
				 String message_error = user_mservice.getMessage();
				
				 Log.d("Rock", user_register_meta_code+"RRRRRRssssss"+message_error+":message_error");
					 if ("200".equals(user_register_meta_code)) {
						 saveSharePreferences(true);
						 Message msg= Message.obtain(mProgressHandler,2);
		            	 msg.sendToTarget();
					}else{
						 Message msg= Message.obtain(mProgressHandler,3,message_error);
		            	 msg.sendToTarget();
					}
				 
		  
	         }
	     };
		  public boolean isNetworkAvailable() {
			   	 try{
			       ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
			       if(connectivity == null){
			       		return false;
			       }else{
			          NetworkInfo info = connectivity.getActiveNetworkInfo();
			          if(info.isAvailable()){
			        	  return true;
			          }
			       }
			   }catch (Exception e){}
			   return false;
		   }
	 	private void saveSharePreferences(boolean mIsSaveUser)  {
			sp = getSharedPreferences("user",Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
			if (mIsSaveUser==true) {
				sp.edit().putString("email",EditText_Email.getText().toString()).commit();
//				sp.edit().putString("password",EditText_Password.getText().toString()).commit();
			}
		}
	class EmailConfirmutionEditTextListener implements OnTouchListener {
		public boolean onTouch(View arg0, MotionEvent arg1) {
			ImageView_Email_Confirmution.setVisibility(View.GONE);
			return false;
		}
	}

	class PasswordConfirmutionEditTextListener implements OnTouchListener {
		public boolean onTouch(View arg0, MotionEvent arg1) {
			ImageView_Password_Confirmution.setVisibility(View.GONE);
			return false;
		}

	}

	class EmailEditTextListener implements OnTouchListener {
		public boolean onTouch(View arg0, MotionEvent arg1) {
			ImageView_Email.setVisibility(View.GONE);
			return false;
		}
	}

	class PasswordEditTextListener implements OnTouchListener {
		public boolean onTouch(View arg0, MotionEvent arg1) {
			ImageView_Password.setVisibility(View.GONE);
			return false;
		}

	}

	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	
	
}
