package com.walkin.walkin;

import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.walkin.SettingActivity.LinearLayout_aboutourListener;
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
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LoginRegistrationActivity extends Activity{

	private MyButton myhome;
	private EditText EditText_Email ;
	private EditText EditText_Password ;
	private Button Button_Login ;
	private Button Button_Registration ;
	private ImageView ImageView_Email ;
	private ImageView ImageView_Password ;
	private TextView TextView_Forgotpassword ;
	private LinearLayout LinearLayout_liyou ;
	
	public boolean mIsSendPass = false;
	public boolean  mIsSendEmail= false;
	private SharedPreferences sp;
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private static Context context;
	
	//
	public String editEmailStr ;
	public String editPassword ;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_registration_act);
  	  	context =this;
  	  	user_mservice.setActivity(this);
  /*	  	progressDialog = new ProgressDialog(this); 
  	  	
  	  	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  	  	progressDialog.setMessage("Loading...");
  	  	progressDialog.setCancelable(false); */
  	
  	  	Button_Login=(Button) findViewById(R.id.Button_Login);
	    Button_Login.setOnClickListener(new LoginButtonListener());
	    Button_Registration=(Button) findViewById(R.id.Button_Registration);
	    Button_Registration.setOnClickListener(new RegistrationButtonListener());
	    EditText_Email=(EditText) findViewById(R.id.EditText_Email);
	    EditText_Password=(EditText) findViewById(R.id.EditText_Password);
	    ImageView_Email=(ImageView) findViewById(R.id.ImageView_Email);
	    ImageView_Password=(ImageView) findViewById(R.id.ImageView_Password);
	    TextView_Forgotpassword=(TextView) findViewById(R.id.TextView_Forgotpassword);
	    TextView_Forgotpassword.setText(Html.fromHtml("<u>忘记密码了?</u>"));
	    TextView_Forgotpassword.setOnClickListener(new TextView_ForgotpasswordListener());
	    LinearLayout_liyou=(LinearLayout) findViewById(R.id.LinearLayout_liyou);
	    LinearLayout_liyou.setOnClickListener(new LinearLayout_liyouListener());
        myhome = new MyButton(this);
//        ScrollView_gun=(ScrollView) findViewById(R.id.ScrollView_gun);
//        ScrollView_gun.setSelected(mIsSendPass);
	    EditText_Email.setOnTouchListener(new EmailEditTextListener());
	    EditText_Password.setOnTouchListener(new PasswordEditTextListener());
	    
	    sp = getSharedPreferences("user", 0);
	    EditText_Email.setText(sp.getString("email", null));
	   // EditText_Password.setText(sp.getString("password", null));
	 /*   EditText_Email.setOnEditorActionListener(new OnEditorActionListener() {   
            @Override  
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {   
                Toast.makeText(MainActivity.this, "text2", Toast.LENGTH_SHORT).show();   
                return false;   
            }   
        }); */ 
	    mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
               
                case 2 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "sssssssssss");
            			Intent intent = new Intent(LoginRegistrationActivity.this, IndexActivity.class);
            			Bundle mBundle = new Bundle();
            			mBundle.putString("Data", "identification");// 压入数据
            			intent.putExtras(mBundle);
            			startActivity(intent);
            			finish();
        			break; 
                case 3 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "ddddddddd");
                		AlertDialog.Builder b1 = new AlertDialog.Builder(LoginRegistrationActivity.this);
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
        };   
    }
		class LoginButtonListener implements OnClickListener{
			public void onClick(View v) {
//				finish();
				 editEmailStr = EditText_Email.getText().toString();
				 editPassword = EditText_Password.getText().toString();
				if ("".equals(editPassword)) {
					ImageView_Password.setVisibility(View.VISIBLE);
					mIsSendPass = false;
				}else{
					mIsSendPass = true;
				}
				 if("".equals(editEmailStr)||RegExpValidator.isEmail(editEmailStr)==false){
					 ImageView_Email.setVisibility(View.VISIBLE);
					 mIsSendEmail = false;
				 }else{
					 mIsSendEmail = true;
				 }
				 if (mIsSendEmail==true&&mIsSendPass==true) {
//				 Message msg= Message.obtain(mProgressHandler,1);
//            	 msg.sendToTarget();
            	 progressDialog = ProgressDialog.show(LoginRegistrationActivity.this, "Loading...", "Please wait...", true, false);
            	 if(isNetworkAvailable()){
            		 new Thread(mthread).start();
            	 }else{
            		 Message msg= Message.obtain(mProgressHandler,4);
	            	 msg.sendToTarget();
            	 }
				
				}
				 
				 }
	}
	/*    protected void onRestart() {
	    	progressDialog = new ProgressDialog(this); 
			
			super.onRestart();
		}*/
		class TextView_ForgotpasswordListener implements OnClickListener{
			public void onClick(View v) {
				 if(isNetworkAvailable()){
					 Intent intent = new Intent(LoginRegistrationActivity.this, SettingWebActivity.class);
					Bundle mBundle = new Bundle();  
					mBundle.putString("DataUrl",SppaConstant.WALKIN_URL_FORPASS);//压入数据  
					intent.putExtras(mBundle);  
					startActivityForResult(intent, 0);
				 }else{
					 Message msg= Message.obtain(mProgressHandler,4);
	            	 msg.sendToTarget();
				 }
				
				
			}
		}
		class LinearLayout_liyouListener implements OnClickListener{
			public void onClick(View v) {
				 if(isNetworkAvailable()){
					 Intent intent = new Intent(LoginRegistrationActivity.this, SettingWebActivity.class);
						Bundle mBundle = new Bundle();  
						mBundle.putString("DataUrl",SppaConstant.WALKIN_URL_LIYOU);//压入数据  
						intent.putExtras(mBundle);  
						startActivityForResult(intent, 0);
				 }else{
					 Message msg= Message.obtain(mProgressHandler,4);
	            	 msg.sendToTarget();
				 }
				
			}
		}
	    protected void onDestroy(){
	    	super.onDestroy();
	    }
		  private final Runnable mthread = new Runnable() {
		         public void run() {
		        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
				//	 String encodePass =RegExpValidator.encode(s.getBytes());
				//	 String editEmailStr_test="kevin.hu@gmail.com";
				/*	 Map<String,String>params=new HashMap<String,String>()  ;
    		    	 params.put("email",editEmailStr);
    		    	 params.put("password",editPassword);*/
    		    	 String	userpass_md5 =RegExpValidator.MD5(editEmailStr+":walkin:"+editPassword);
    				// String encodePass =RegExpValidator.encode(s.getBytes());
    				// String editEmailStr_test="kevin.hu@gmail.com";
    				Map<String,String>params=new HashMap<String,String>()  ;
    			    params.put("email",editEmailStr);
    			    params.put("password",userpass_md5);
    		    	 
					 String urlcons = SppaConstant.WALKIN_URL_LOGIN+"?apikey=BYauu6D9";
					 user_mservice.setRetrieveUrl(urlcons);
					 user_mservice.retrieveUserLoginInfo(params);
					 String user_login_meta_code = user_mservice.getCode();
					 String message_error = user_mservice.getMessage();
					
					 Log.d("Rock", user_login_meta_code+"md5:"+userpass_md5+"LLLLL");
					 if ("200".equals(user_login_meta_code)) {
						 saveSharePreferences(true);
						 Message msg= Message.obtain(mProgressHandler,2);
		            	 msg.sendToTarget(); 
					}else{
						 Message msg= Message.obtain(mProgressHandler,3,message_error);
		            	 msg.sendToTarget();
		            	
					}
 			  
		         }
		     };
		 
		private void saveSharePreferences(boolean mIsSaveUser)  {
			sp = getSharedPreferences("user",Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
			if (mIsSaveUser==true) {
				sp.edit().putString("email",EditText_Email.getText().toString()).commit();
//				sp.edit().putString("password",EditText_Password.getText().toString()).commit();
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
		class RegistrationButtonListener implements OnClickListener{
			public void onClick(View v) {
				/*Log.d("Rock", RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c"));
				String s = "1012:1318337552556:eaf838196f11de06e1a8fbb3d3f83c96";
				System.out.println("加密前:"+s);
				String x =RegExpValidator.encode(s.getBytes());
				System.out.println("加密后:"+x);
				try {
					String x1 = new String(RegExpValidator.decode(x)) ;
					System.out.println("解密后:"+x1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}*/
				
				 if(isNetworkAvailable()){
					 Intent intent=new Intent();
						intent.setClass(LoginRegistrationActivity.this, RegistrationActivity.class);
						Bundle bundle=new  Bundle();
						String str1="aaaaaa";
						bundle.putString("Data", str1);
						intent.putExtras(bundle);
						startActivityForResult(intent, 0);
				 }else{
					 Message msg= Message.obtain(mProgressHandler,4);
	            	 msg.sendToTarget();
				 }
    			
			}		
	}
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
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
			case RESULT_OK:
				Intent intent = getIntent();
				Bundle b =intent.getExtras();
//				Log.d("Rock",b.getString("str1")+"00000");
				setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				finish();
	            break;
			case 1:
				intent = getIntent();
//				Log.d("Rock",b.getString("str1")+"00000");
				setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				finish();
	            break;
			case 2:
				intent = getIntent();
//				Log.d("Rock",b.getString("str1")+"00000");
				setResult(2, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				finish();
		            break;
			case 3:
				intent = getIntent();
//				Log.d("Rock",b.getString("str1")+"00000");
				setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				finish();
		            break;
			default:
		           break;
			}
		}
/*		class RegistrationvvvButtonListener implements OnClickListener{
			public void onClick(View v) {
				Log.d("Rock", RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c"));
				String s = "1012:1318337552556:eaf838196f11de06e1a8fbb3d3f83c96";
				System.out.println("加密前:"+s);
				String x =RegExpValidator.encode(s.getBytes());
				System.out.println("加密后:"+x);
				try {
					String x1 = new String(RegExpValidator.decode(x)) ;
					System.out.println("解密后:"+x1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}		
	}*/
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
