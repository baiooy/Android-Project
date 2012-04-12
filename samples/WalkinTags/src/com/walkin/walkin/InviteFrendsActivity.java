package com.walkin.walkin;



import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.MyButton;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class InviteFrendsActivity extends Activity{
//	private Button Button_ME ;
	private MyButton myhome;
	private Button Button_email_send;
	private Button Button_sms_send;
	private Button Button_weibo_send;
	private TextView TextView_code;
	
	
	//private Button Button_Buy ;
	private static Context context;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	   protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitefrends_act);
        context = this;
		user_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
      //  Button_Buy=(Button) findViewById(R.id.Button_Buy);
//        Button_ME=(Button) findViewById(R.id.Button_ME);
//        Button_ME.setText(user_mservice.getInbiBalance());
//        myhome = new MyButton(this);
//        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
//        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
//        Button_ME.setOnClickListener(new Button_MEListener());
        
        TextView_code=(TextView) findViewById(R.id.TextView_code);
        TextView_code.setText(user_mservice.getUserName());
        
        
        
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Floraless.ttf");   
        Button_email_send = (Button)findViewById(R.id.Button_email_send);   
        Button_email_send.setOnClickListener(new Button_email_sendListener());
        Button_email_send.setTypeface(tf);    //设置TextView的风格 
        Button_sms_send = (Button)findViewById(R.id.Button_sms_send);  
        Button_sms_send.setOnClickListener(new Button_sms_sendListener());
        Button_sms_send.setTypeface(tf);    //设置TextView的风格 
        Button_weibo_send = (Button)findViewById(R.id.Button_weibo_send);   
        Button_weibo_send.setTypeface(tf);    //设置TextView的风格 
        Button_weibo_send.setOnClickListener(new Button_weibo_sendListener());
    }
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(InviteFrendsActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class Button_email_sendListener implements OnClickListener{
		public void onClick(View v) {
			 try {
	    			Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"));
	    			intent.putExtra(Intent.EXTRA_TEXT, defaults_mservice.getEmailMessage()+user_mservice.getUserName());
	    	        startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(InviteFrendsActivity.this, "请设置邮箱", Toast.LENGTH_SHORT).show();
				}
		}
	}
	
	class Button_sms_sendListener implements OnClickListener{
		public void onClick(View v) {
			 try {
			Intent intent = new Intent(Intent.ACTION_VIEW);   
        	intent.setType("vnd.android-dir/mms-sms");  
        	intent.putExtra("sms_body",defaults_mservice.getSmsMessage()+user_mservice.getUserName());
            startActivity(intent);
			 } catch (Exception e) {
					//Toast.makeText(InviteFrendsActivity.this, "请设置邮箱", Toast.LENGTH_SHORT).show();
				}
		}
	}
	
	class Button_weibo_sendListener implements OnClickListener{
		public void onClick(View v) {
			try {
				Intent intent = new Intent(InviteFrendsActivity.this, SocialSendWeiboActivity.class);
		   		 
	    		Bundle mBundle = new Bundle();  
	    		mBundle.putString("strType", defaults_mservice.getWeiboMessage()+user_mservice.getUserName());//压入数据  
	    		intent.putExtras(mBundle);  
	    		startActivityForResult(intent, 0);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(InviteFrendsActivity.this, "不可大于70个字符", Toast.LENGTH_SHORT).show();
			}
    	
            
            
		}
	}
	
	protected void onStart() {
		super.onStart();
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
