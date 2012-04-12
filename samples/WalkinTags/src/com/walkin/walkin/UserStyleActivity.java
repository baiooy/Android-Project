package com.walkin.walkin;

import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class UserStyleActivity extends Activity{
	private Button FinishButton ;
//	private Button goback ;
	private RadioButton RadioButton_UserStyle01,RadioButton_UserStyle02,RadioButton_UserStyle03,
	RadioButton_UserStyle04,RadioButton_UserStyle05,RadioButton_UserStyle06;
	public int mRadioGroup_UserStyle_int = 0;
	private TextView TextView_UserStyle01,TextView_UserStyle02,TextView_UserStyle03,TextView_UserStyle04,TextView_UserStyle05,TextView_UserStyle06 ; 
	public boolean mIsRadioButton=false;
	private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
	private static Context context;
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	String data_type;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userstyle_act);
        
    	context =this;
        user_mservice.setActivity(this);
        
   	 Bundle bundle = getIntent().getExtras();    
		data_type =bundle.getString("Data");
		String data_FemaleOrMale =bundle.getString("FemaleOrMale");
		
        FinishButton=(Button) findViewById(R.id.Button_Finish);
	    FinishButton.setOnClickListener(new FinishButtonListener());
//		goback= (Button) findViewById(R.id.goback);
//		goback.setOnClickListener(new gobackListener()); 
	    
	    TextView_UserStyle01 =(TextView) findViewById(R.id.TextView_UserStyle01);
	    TextView_UserStyle02 =(TextView) findViewById(R.id.TextView_UserStyle02);
	    TextView_UserStyle03 =(TextView) findViewById(R.id.TextView_UserStyle03);
	    TextView_UserStyle04 =(TextView) findViewById(R.id.TextView_UserStyle04);
	    TextView_UserStyle05 =(TextView) findViewById(R.id.TextView_UserStyle05);
	    TextView_UserStyle06 =(TextView) findViewById(R.id.TextView_UserStyle06);
	    
	    if ("0".equals(data_FemaleOrMale)) {
	    	TextView_UserStyle01.setBackgroundResource(R.drawable.user_style_pic1_g);
	    	TextView_UserStyle02.setBackgroundResource(R.drawable.user_style_pic2_g);
	    	TextView_UserStyle03.setBackgroundResource(R.drawable.user_style_pic3_g);
	    	TextView_UserStyle04.setBackgroundResource(R.drawable.user_style_pic4_g);
	    	TextView_UserStyle05.setBackgroundResource(R.drawable.user_style_pic5_g);
	    	TextView_UserStyle06.setBackgroundResource(R.drawable.user_style_pic6_g);
		}
	    
	    
	    
	    
	    RadioButton_UserStyle01 =(RadioButton) findViewById(R.id.RadioButtonUserStyle01);
	    RadioButton_UserStyle02 =(RadioButton) findViewById(R.id.RadioButtonUserStyle02);
	    RadioButton_UserStyle03 =(RadioButton) findViewById(R.id.RadioButtonUserStyle03);
	    RadioButton_UserStyle04 =(RadioButton) findViewById(R.id.RadioButtonUserStyle04);
	    RadioButton_UserStyle05 =(RadioButton) findViewById(R.id.RadioButtonUserStyle05);
	    RadioButton_UserStyle06 =(RadioButton) findViewById(R.id.RadioButtonUserStyle06);
	    RadioButton_UserStyle01.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle01.isChecked()) {
						mRadioGroup_UserStyle_int = 1;
						RadioButton_UserStyle02.setChecked(false);
						RadioButton_UserStyle03.setChecked(false);
						RadioButton_UserStyle04.setChecked(false);
						RadioButton_UserStyle05.setChecked(false);
						RadioButton_UserStyle06.setChecked(false);
						
					}
				
			}
	    	
	    });
	    RadioButton_UserStyle02.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle02.isChecked()) {
						mRadioGroup_UserStyle_int = 2;
						RadioButton_UserStyle01.setChecked(false);
						RadioButton_UserStyle03.setChecked(false);
						RadioButton_UserStyle04.setChecked(false);
						RadioButton_UserStyle05.setChecked(false);
						RadioButton_UserStyle06.setChecked(false);
					}
				
			}
	    	
	    });
	    RadioButton_UserStyle03.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle03.isChecked()) {
						mRadioGroup_UserStyle_int = 3;
						RadioButton_UserStyle01.setChecked(false);
						RadioButton_UserStyle02.setChecked(false);
						RadioButton_UserStyle04.setChecked(false);
						RadioButton_UserStyle05.setChecked(false);
						RadioButton_UserStyle06.setChecked(false);
					}
				
			}
	    	
	    });
	    RadioButton_UserStyle04.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle04.isChecked()) {
						mRadioGroup_UserStyle_int = 4;
						RadioButton_UserStyle01.setChecked(false);
						RadioButton_UserStyle03.setChecked(false);
						RadioButton_UserStyle02.setChecked(false);
						RadioButton_UserStyle05.setChecked(false);
						RadioButton_UserStyle06.setChecked(false);
					}
				
			}
	    	
	    });
	    RadioButton_UserStyle05.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle05.isChecked()) {
						mRadioGroup_UserStyle_int = 5;
						RadioButton_UserStyle01.setChecked(false);
						RadioButton_UserStyle03.setChecked(false);
						RadioButton_UserStyle04.setChecked(false);
						RadioButton_UserStyle02.setChecked(false);
						RadioButton_UserStyle06.setChecked(false);
					}
				
			}
	    	
	    });
	    RadioButton_UserStyle06.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					if (RadioButton_UserStyle06.isChecked()) {
						mRadioGroup_UserStyle_int = 6;
						RadioButton_UserStyle01.setChecked(false);
						RadioButton_UserStyle03.setChecked(false);
						RadioButton_UserStyle04.setChecked(false);
						RadioButton_UserStyle05.setChecked(false);
						RadioButton_UserStyle02.setChecked(false);
					}
				
			}
	    	
	    });
	    
	    if ("SettingActivity".equals(data_type)) {
	    	String style= (String) user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_style);
	    	if("1".equals(style)){
	    		RadioButton_UserStyle01.setChecked(true);
			}else if("2".equals(style)){
				RadioButton_UserStyle02.setChecked(true);
			}else if("3".equals(style)){
				RadioButton_UserStyle03.setChecked(true);
			}else if("4".equals(style)){
				RadioButton_UserStyle04.setChecked(true);
			}else if("5".equals(style)){
				RadioButton_UserStyle05.setChecked(true);
			}else if("6".equals(style)){
				RadioButton_UserStyle06.setChecked(true);
			}
	    	
		}
	    
	    mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
                case 1 :   
                	AlertDialog.Builder b2 = new AlertDialog.Builder(UserStyleActivity.this);
                	b2.setTitle("抱歉");
                	b2.setMessage("请选择最适合你的风格");
                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
		                public void onClick(DialogInterface dialog, int which){
		                }
		            });
                	b2.show();
        			break; 
                case 2 :   
                		progressDialog.dismiss();
                		
                		if ("SettingActivity".equals(data_type)) {
                			 UserStyleActivity.this.finish();
						}else{
                	 Intent intent = new Intent(UserStyleActivity.this, SocialRegistrationActivity.class);
         			Bundle mBundle = new Bundle();  
         			mBundle.putString("Data", "identification");//压入数据  
         			intent.putExtras(mBundle);  
         			startActivityForResult(intent, 0);
                     intent = getIntent();
         			setResult(1, intent); //intent为
                     UserStyleActivity.this.finish();
						}
        			break; 
                case 3 :   
                		progressDialog.dismiss();
                		AlertDialog.Builder b1 = new AlertDialog.Builder(UserStyleActivity.this);
                		b1.setTitle("抱歉");
    		            b1.setMessage("有误");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            b1.show();
                    break;  
                case 4:
                	progressDialog.dismiss();
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

	class FinishButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			
			 if (mRadioGroup_UserStyle_int!=0) {
	        	 progressDialog = ProgressDialog.show(UserStyleActivity.this, "Loading...", "Please wait...", true, false);
				 new Thread(mthread).start();
				}else{
					Message msg= Message.obtain(mProgressHandler,1);
	           	 	msg.sendToTarget();
				}
			
		}		
}
	 private final Runnable mthread = new Runnable() {
         public void run() {
        	 
        	 
        	  String urlcon = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
              +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
              Log.d("Rock", urlcon);
              user_mservice.setRetrieveUrl(urlcon);
              user_mservice.retrieveUserQueryInfo();
        	 
        	 
        	 
        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	 Log.d("Rock", mRadioGroup_UserStyle_int+"");
			 Map<String,String>params=new HashMap<String,String>()  ;
			 params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
	    	 params.put("firstName","");
	    	 params.put("lastName","");
	    	 params.put("userName","");
	    	 params.put("address","");
	    	 params.put("phoneNumber","");
	    	 params.put("imageUrl","");
	    	 params.put("weiboAccount","");
	    	 params.put("gender","");
	    	 params.put("ageGroup","");
	    	 params.put("style",mRadioGroup_UserStyle_int+"");
			 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/update"+"?"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
			 user_mservice.setRetrieveUrl(urlcons);
			 user_mservice.retrieveUserUpdateInfo(params);
			 String user_login_meta_code = user_mservice.getCode();
			 Log.d("Rock", urlcons+"LLLLL");
			 
			 if(isNetworkAvailable()){
				 if ("200".equals(user_login_meta_code)) {
					 Message msg= Message.obtain(mProgressHandler,2);
	            	 msg.sendToTarget(); 
				}else{
					 Message msg= Message.obtain(mProgressHandler,3);
	            	 msg.sendToTarget();
	            	
				}
		        }else {
		    		Message msg = Message.obtain(mProgressHandler,4);
					msg.sendToTarget();
		        }
			 
			/* if ("200".equals(user_login_meta_code)) {
				 Message msg= Message.obtain(mProgressHandler,2);
            	 msg.sendToTarget(); 
			}else{
				 Message msg= Message.obtain(mProgressHandler,3);
            	 msg.sendToTarget();
            	
			}*/
		  
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
 	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return super.onKeyDown(keyCode, event);

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
