package com.walkin.walkin;
/**
 * @description : 发送微博
 * @author : Rock
 * @param ：onCreate 
 * @date : 2011/03/09
 */
import java.util.ArrayList;
import java.util.List;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;





import weibo4android.Comment;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class SocialCommentAndRepostActivity extends Activity {

	private EditText Edit;
	private Button Button_send;
	private SharedPreferences sp;
	public String editaccessToken;
	public String editaccessTokenSecret;
	
    private static Handler mProgressHandler=null;
	private ProgressDialog progressDialog;
	private Animation myAnimation_Alpha;
	public String str_Data;
	public String mStr;
	  private  String Mid;
	private List<String> arraylist_data = new ArrayList<String>(); 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.social_commentandrepost_act);
		Button_send = (Button) findViewById(R.id.Button_send);
		
		Edit = (EditText) findViewById(R.id.Edit);
		
	    Bundle bundle = getIntent().getExtras();    
	    str_Data=bundle.getString("Data");
		arraylist_data=bundle.getStringArrayList("arraylist");
		
		
		Log.d("Rock", arraylist_data+"asdfasdfasdf");
		Mid =arraylist_data.get(6);
		if ("Repost".equals(str_Data)) {
			String neirong =arraylist_data.get(3);
			Edit.setText(neirong);
			Edit.setHint("转发微博最多140字");
			mStr="转发中...";
		}else{
			Edit.setText("");
			Edit.setHint("评论微博最多140字");
			mStr="评论中...";
		}
		
		
		
		 mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	               
	                case 2 :   
	                	progressDialog.dismiss();
	                	AlertDialog.Builder b = new AlertDialog.Builder(SocialCommentAndRepostActivity.this);
	                	b.setTitle("恭喜");
	                    b.setMessage("发送成功");
	                    b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        	finish();
	                        }
	                    });
	                    b.show();
	        			break; 
	                case 3 :   
	                	progressDialog.dismiss();
	                	 b = new AlertDialog.Builder(SocialCommentAndRepostActivity.this);
						b.setTitle("抱歉");
	                    b.setMessage("发送出错");
	                    b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        }
	                    });
	                    b.show();
	                    break;  
	                case 1 :   
	                	progressDialog.dismiss();
	                	 b = new AlertDialog.Builder(SocialCommentAndRepostActivity.this);
						b.setTitle("抱歉");
	                    b.setMessage("发送内容为空");
	                    b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        }
	                    });
	                    b.show();
	                    break;  
	                }   super.handleMessage(msg);
				}
	            
	        } ;     
		
		
		
	    Button_send = (Button)findViewById(R.id.Button_send);
	    sp = getSharedPreferences("user", 0);
		  //  EditText_Email.setText(sp.getString("weiboEmail", null));
		   // EditText_Password.setText(sp.getString("weiboPassword", null));
				
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
				System.setProperty("weibo4j.oauth.consumerSecret",Weibo.CONSUMER_SECRET);
				editaccessToken = sp.getString("accessToken", null);
				editaccessTokenSecret = sp.getString("accessTokenSecret", null);
				
				
					Log.i("Rock", editaccessTokenSecret+":editaccessTokenSecret");
					if ("".equals(editaccessToken)||editaccessToken==null) {
						
						Intent intent = new Intent(SocialCommentAndRepostActivity.this,AuthorizationAct.class);
						// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
						Bundle mBundle = new Bundle();
						mBundle.putString("Data", "wuweibo");// 压入数据
						intent.putExtras(mBundle);
						startActivityForResult(intent, 0);
						SocialCommentAndRepostActivity.this.finish();
						return;
					}
				
				
				
				Button_send.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						myAnimation_Alpha = AnimationUtils.loadAnimation(SocialCommentAndRepostActivity.this,R.anim.my_alpha_action);
						Button_send.startAnimation(myAnimation_Alpha);
							// TODO: handle exception
							 progressDialog = ProgressDialog.show(SocialCommentAndRepostActivity.this, "Loading...", mStr, true, false);
							new Thread(mthread2).start();
					}
				});
				
	}
	 private final Runnable mthread2 = new Runnable() {
         public void run() {
        	 String editStr = Edit.getText().toString();
			 Weibo weibo=OAuthConstant.getInstance().getWeibo();
			weibo.setToken(editaccessToken, editaccessTokenSecret);
			try {
				if (!"".equals(editStr)) {
					
					
					if ("Repost".equals(str_Data)) {
						Status status = weibo.repost(Mid,editStr);
					}else{
						 Comment comment = weibo.updateComment(editStr, Mid, null); 
					}
						
				    	Message msg= Message.obtain(mProgressHandler,2);
						 msg.sendToTarget();
				}else{
					Message msg= Message.obtain(mProgressHandler,3);
					 msg.sendToTarget();
				}
				} catch (WeiboException e) {
				e.printStackTrace();
				if (e.getStatusCode() == 400) {
					// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
				         	Message msg= Message.obtain(mProgressHandler,3);
							 msg.sendToTarget();
					
				} else if (e.getStatusCode() == 403) {
					// 帐号密码错误
				}
				
				return;
			}
	  
         }
     };
				
 	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}	
	 
			
}
