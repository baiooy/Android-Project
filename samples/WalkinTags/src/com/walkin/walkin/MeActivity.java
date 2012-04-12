package com.walkin.walkin;


import java.util.List;
import java.util.Map;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MeActivity extends Activity{
	private MyButton myhome;
	
	
	private Button Button_Invite_friend;
	private TextView TextView_user_name,TextView_walkin_message; 
	private TextView TextView_total_num01; 
	private TextView TextView_total_num02; 
//	private TextView TextView_City; 
	
    private TextView me_TextView_08 ; 
    private TextView me_TextView_09 ; 
    private TextView me_TextView_10 ; 
    private TextView me_TextView_11 ; 
	private TextView me_TextView_05;
	
	private TextView TextView_style; 
	private TextView TextView_AddressAndTime; 
	
	private Button Button_setting ;
	private Button Button_top_logo;
	private LinearLayout LinearLayout_xunzhang,LinearLayout_Leaderboard,LinearLayout_checkinsHistory,LinearLayout_notifications;
	
	private RelativeLayout  RelativeLayout_middle_lines02;
    
	private ImageView ImageView_user_img;
    
	private Button goback ;
	private List<Map<String, Object>> mData;
	private static Context context;
    private MyTaskMeInbiBalance mTaskInbi;  
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private SharedPreferences sp;
	String style = null;
    private static Handler mProgressHandler=null;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_act);
        context = this;
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
		 sp = getSharedPreferences("user", 0);
		goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
       // intoday=(Button) findViewById(R.id.intoday);
        myhome = new MyButton(this);
        Integer[] mHomeState_me = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	  //  intoday.setBackgroundDrawable(myhome.setbg(mHomeState_me));
	  //  intoday.setOnClickListener(new intodayButtonListener());
        TextView_walkin_message= (TextView) findViewById(R.id.TextView_walkin_message);
        TextView_walkin_message.setText(user_mservice.getUnreadCount());
	    Button_Invite_friend= (Button) findViewById(R.id.Button_Invite_friend);
	    Button_Invite_friend.setOnClickListener(new Button_Invite_friendListener());
	    Button_setting= (Button) findViewById(R.id.Button_setting);
	    Button_setting.setOnClickListener(new Button_settingListener());
	    
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    LinearLayout_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_xunzhang);
	    LinearLayout_xunzhang.setOnClickListener(new LinearLayout_xunzhangListener());
	    LinearLayout_Leaderboard=(LinearLayout) findViewById(R.id.LinearLayout_Leaderboard);
	    LinearLayout_Leaderboard.setOnClickListener(new LinearLayout_LeaderboardListener());
	    LinearLayout_checkinsHistory=(LinearLayout) findViewById(R.id.LinearLayout_checkinsHistory);
	    LinearLayout_checkinsHistory.setOnClickListener(new LinearLayout_checkinsHistoryListener());
	    LinearLayout_notifications=(LinearLayout) findViewById(R.id.LinearLayout_notifications);
	    LinearLayout_notifications.setOnClickListener(new LinearLayout_notificationsListener());
	    RelativeLayout_middle_lines02=(RelativeLayout) findViewById(R.id.RelativeLayout_middle_lines02);
	    RelativeLayout_middle_lines02.setOnClickListener(new  RelativeLayout_middle_lines02Listener());
	    TextView_user_name= (TextView) findViewById(R.id.TextView_user_name);
	    ImageView_user_img=(ImageView) findViewById(R.id.ImageView_user_img);
	    ImageView_user_img.setOnClickListener(new ImageView_user_imgListener());
	    try {
	    	 mData = getData(user_mservice.getList_jobj_item());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 
		   if ("1".equals(sp.getString("istransparentall", null))) {
	        	 Intent intent = new Intent(MeActivity.this, TransparentAllActivity.class);
		         Bundle mBundle = new Bundle();
				 mBundle.putString("Activity", "MeActivity");// 压入数据
				 intent.putExtras(mBundle);
				 startActivityForResult(intent, 0);
			//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
				}
		   mProgressHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 1:
						break;
					case 2:
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
				
					super.handleMessage(msg);
				}
			};
			checkNetworkStatus();
    }
    
	public void checkNetworkStatus(){
    	if(isNetworkAvailable()){
        }else {
    		Message msg = Message.obtain(mProgressHandler,2);
			msg.sendToTarget();
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
    
	class ImageView_user_imgListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent(MeActivity.this, SocialUpdateUserActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Activity", "SettingActivity");//压入数据  
        	mBundle.putString("oauth_verifier_url", "");//压入数据  
        	mBundle.putString("Data", "false");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	 private class MyTaskMeInbiBalance extends AsyncTask<String, Integer, String> {  

	        protected String doInBackground(String... params) {  
	           
	       	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
		          +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
		          Log.d("Rock", urlcons);
		          user_mservice.setRetrieveUrl(urlcons);
		          user_mservice.retrieveUserQueryInfo();
	            return null;  
	        }  
	        
	        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	        @Override  
	        protected void onPostExecute(String result) {  
	        	String pic_path = null ;
	        	try {
				String ImageView01_getThumbnail_pic = (String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_imageUrl);
				if ("http".equals(ImageView01_getThumbnail_pic.substring(0, 4))) {
					pic_path =ImageView01_getThumbnail_pic;
				}else{
					pic_path =SppaConstant.WALKIN_URL_IP+ImageView01_getThumbnail_pic;
				}
	        	} catch (Exception e) {
					// TODO: handle exception
				}
				loadImage4(pic_path,(ImageView)findViewById(R.id.ImageView_user_img));
				
					TextView_user_name.setText((String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_userName));
					TextView_walkin_message.setText(user_mservice.getUnreadCount());
					TextView_style.setText(style);
			
	        	
	       	 //Button_ME.setText(user_mservice.getInbiBalance());
	        }  
	          
	    }
	protected void onRestart() {
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		 mTaskInbi = new MyTaskMeInbiBalance();  
	 	 mTaskInbi.execute(); 
		super.onRestart();
	}
    class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
		}
	}
	private List<Map<String, Object>> getData(List<Map<String, Object>>  userlist) {
		String pic_path = null ;
		
		try {
			String ImageView01_getThumbnail_pic = (String)userlist.get(0).get(UserMenuService.response_user_imageUrl);
			if ("http".equals(ImageView01_getThumbnail_pic.substring(0, 4))) {
				pic_path =ImageView01_getThumbnail_pic;
			}else{
				pic_path =SppaConstant.WALKIN_URL_IP+ImageView01_getThumbnail_pic;
			}
		} catch (Exception e) {
			// TODO: handle exception
			
		}
			Log.d("Rock", pic_path+":pic_path");
			loadImage4(pic_path,(ImageView)findViewById(R.id.ImageView_user_img));
			System.out.println((String)userlist.get(0).get(UserMenuService.response_user_userName)+":(String)userlist.get(0).get(UserMenuService.response_user_userName)");
			TextView_user_name.setText((String)userlist.get(0).get(UserMenuService.response_user_userName));
			TextView_style= (TextView) findViewById(R.id.TextView_style);
			
			if ("1".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "休闲舒适";
			}else if("2".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "活力运动";
			}else if("3".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "时尚达人";
			}else if("4".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "性感派对";
			}else if("5".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "简约典雅";
			}else if("6".equals((String)userlist.get(0).get(UserMenuService.response_user_style))) {
				 style = "前卫个性";
			}
			TextView_style.setText(style);
			TextView_AddressAndTime= (TextView) findViewById(R.id.TextView_AddressAndTime);
			TextView_AddressAndTime.setText((String)userlist.get(0).get(UserMenuService.response_user_address));
		//	TextView_City= (TextView) findViewById(R.id.TextView_City);
		//	TextView_City.setText((String)userlist.get(0).get(UserMenuService.response_user_address));
			TextView_total_num01 =(TextView) findViewById(R.id.TextView_total_num01);
			TextView_total_num01.setText((String)userlist.get(0).get(UserMenuService.response_user_inbiFromStart));
			TextView_total_num02 =(TextView) findViewById(R.id.TextView_total_num02);
			TextView_total_num02.setText((String)userlist.get(0).get(UserMenuService.response_user_inbiBalance));
		//	Toast.makeText(MeActivity.this, "请修改用户名和头像", Toast.LENGTH_SHORT).show();
		
		
		
		
		return userlist;
	}
	
	 private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();
	    //采用Handler+Thread+封装外部接口
	    private void loadImage5(final String url, final ImageView imageView) {
	          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	         Drawable cacheImage = asyncImageLoader3.loadDrawable(url,new AsyncImageLoader3.ImageCallback() {
	             //请参见实现：如果第一次加载url时下面方法会执行
	             public void imageLoaded(Drawable imageDrawable) {
	            	 
	            	 if (imageDrawable != null ) { 
	            	 imageView.setImageDrawable(imageDrawable);
	            	 }else{
         		 imageView.setImageResource(R.drawable.profile_default_pic);
	            	 }
	             }
	         });
	        if(cacheImage!=null){
	        	imageView.setImageDrawable(cacheImage);
	        }else{
    		 imageView.setImageResource(R.drawable.profile_default_pic);
    	 }
	    }
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(MeActivity.this, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	            	   imageView.setImageDrawable(imageDrawable);
	               }else{
	            		 imageView.setImageResource(R.drawable.profile_default_pic);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	imageView.setImageDrawable(cachedImage);
	        }else{
    		 imageView.setImageResource(R.drawable.profile_default_pic);
    	 }
	    	}
	    
	    
	    
    class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}  
    class LinearLayout_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(MeActivity.this, BadgesGridActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 0);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
 		}
 	} 
    class LinearLayout_LeaderboardListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(MeActivity.this, LeaderBoardListActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
 		}
 	} 
    class LinearLayout_checkinsHistoryListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(MeActivity.this, CheckinsHistoryListActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
 		}
 	}
    class LinearLayout_notificationsListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(MeActivity.this, NotificationsListActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
 		}
 	}
    class RelativeLayout_middle_lines02Listener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(MeActivity.this, InbiExchangeActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
 		}
 	}
    
	class Button_Invite_friendListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intents = new Intent();
			intents.setClass(MeActivity.this, InviteFrendsActivity.class);
			startActivityForResult(intents, 0);

		}
	}
	class Button_settingListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intents = new Intent();
			intents.setClass(MeActivity.this, SettingActivity.class);
			startActivityForResult(intents, 0);

		}
	}




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
 		case RESULT_OK:
 			Intent intent = getIntent();
 			Bundle b =intent.getExtras();
// 			Log.d("Rock",b.getString("str1")+"00000");
 			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
 			finish();
             break;
 		case 1:
 			intent = getIntent();
// 			Log.d("Rock",b.getString("str1")+"00000");
 			setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
 			finish();
             break;
 		case 2:
 			intent = getIntent();
// 			Log.d("Rock",b.getString("str1")+"00000");
 			setResult(2, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
 			finish();
 	            break;
 		case 3:
 			intent = getIntent();
// 			Log.d("Rock",b.getString("str1")+"00000");
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
