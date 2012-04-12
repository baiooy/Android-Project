package com.walkin.walkin;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.service.UserMenuService;

import weibo4android.Weibo;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SocialRegistrationActivity extends Activity {
	/** Called when the activity is first created. */
	
	private Button Button_Next,Button_skip ;
	private SharedPreferences sp;
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private static Context context;
//    private MyTask mTask;  
//	public User weibo_user;
//	public int int_StackTrace;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_registration_act);
		
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",Weibo.CONSUMER_SECRET);
		
  	  	context =this;
  	  	user_mservice.setActivity(this);
		    
	    sp = getSharedPreferences("user", 0);
	  //  EditText_Email.setText(sp.getString("weiboEmail", null));
	   // EditText_Password.setText(sp.getString("weiboPassword", null));
	    Button_Next=(Button) findViewById(R.id.Button_Next);
	    Button_Next.setOnClickListener(new NextButtonListener());
	    Button_skip=(Button) findViewById(R.id.Button_skip);
	    Button_skip.setOnClickListener(new Button_skipListener());
		    mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	               
	                case 2 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "sssssssssss");
	            			Intent intent = new Intent(SocialRegistrationActivity.this, SocialUpdateUserActivity.class);
	            			Bundle bundle=new  Bundle();
	         				bundle.putString("Activity", "");//压入数据  
	         				bundle.putString("oauth_verifier_url", "");//压入数据  
	         				bundle.putString("Data", "false");//压入数据  
	         				intent.putExtras(bundle);
	         				startActivityForResult(intent, 0);
	        			break; 
	                case 3 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "ddddddddd");
	                		AlertDialog.Builder b1 = new AlertDialog.Builder(SocialRegistrationActivity.this);
	                		b1.setTitle("抱歉");
	    		            b1.setMessage("用户名或密码有误");
	    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	    		            b1.show();
	                    break;  
	                case 4 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "ddddddddd");
	                		 b1 = new AlertDialog.Builder(SocialRegistrationActivity.this);
	                		 b1.setTitle("抱歉");
	    		            b1.setMessage("内容重复");
	    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	    		            b1.show();
	                    break;  
	                case 5:
	                //	progressDialog.dismiss();
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
	class Button_skipListener implements OnClickListener{
		public void onClick(View v) {
			if(isNetworkAvailable()){
				Intent intent = new Intent(SocialRegistrationActivity.this, SocialUpdateUserActivity.class);
				Bundle bundle=new  Bundle();
				bundle.putString("Activity", "SocialRegistrationActivity");//压入数据  
				bundle.putString("oauth_verifier_url", "");//压入数据  
				bundle.putString("Data", "false");//压入数据  
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
				finish();
        }else {
    		Message msg = Message.obtain(mProgressHandler,5);
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
	class NextButtonListener implements OnClickListener{
		public void onClick(View v) {
			if(isNetworkAvailable()){
				Intent intent = new Intent(SocialRegistrationActivity.this,AuthorizationAct.class);
				// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
				Bundle mBundle = new Bundle();
				mBundle.putString("Data", "mainbar");// 压入数据
				intent.putExtras(mBundle);
				startActivityForResult(intent, 0);
				SocialRegistrationActivity.this.finish();
			}else{
				Message msg = Message.obtain(mProgressHandler,5);
				msg.sendToTarget();
			}
			
			
			 }
}
	
	   protected void onDestroy(){
	    	super.onDestroy();
	    }
	   
	   private final Runnable mthread = new Runnable() {
	         public void run() {
	         	Message msg= Message.obtain(mProgressHandler,2);
				 msg.sendToTarget();
	        	 
			  
	         }
	     };
	   
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
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}