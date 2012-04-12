package com.walkin.walkin;


import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.bean.NetworkBitmapInStoreInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.NetworkToPic;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StoreHomeActivity extends Activity {
	private Button inButton,goback;
	private MyButton myhome;
	private RelativeLayout RelativeLayout_brands;
	private RelativeLayout RelativeLayout_deals;
	private RelativeLayout RelativeLayout_games;
	private RelativeLayout RelativeLayout_social;
	private Button Button_ME,button_question;
	private TextView TextView_brands;
	private TextView TextView_deals;
	private TextView TextView_games;
	private TextView TextView_social;
//	private ImageView ImageView_reward;
//	private RelativeLayout RelativeLayout_bg;
	private static Handler exitHandler=null;
	
	//gps
	private LocationManager locationManager;
	public static Handler meGPSProgressHandler;
	public String currentProvider_net;
	public String currentProvider_gps;
	public Location currentLocation;
    double  latitude_net,latitude_gps;
    double longitude_net,longitude_gps;
    float accuracy_net, accuracy_gps;
	//--gps
	protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	private static Context context;
	protected final static NetworkBitmapInStoreInfo networkbitmapinstoreinfo = NetworkBitmapInStoreInfo.getInstance();
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private MyTask mTask;  
	private MyTask02 mTask02;  
	private MyTask03 mTask03;  
	String enname;
	String markerID;
	private Animation myAnimation_Alpha;
//    private ProgressDialog progressDialog; 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storehome_act);
		context = this;
		locationinfo.setActivity(this);
		marker_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
		networkbitmapinstoreinfo.setActivity(this);
		Button_ME = (Button) findViewById(R.id.Button_ME);
		myhome = new MyButton(this);
		Integer[] mHomeState = {R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
		Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
		Button_ME.setOnClickListener(new MEButtonListener());
		
		goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
		inButton = (Button) findViewById(R.id.inButton);
		myhome = new MyButton(this);
		Integer[] mHomeState_in = { R.drawable.inbuttoncentertab,
				R.drawable.inbuttoncentertab, R.drawable.inbuttoncentertab };
		inButton.setBackgroundDrawable(myhome.setbg(mHomeState_in));
		inButton.setOnClickListener(new inButtonListener());
		button_question	= (Button) findViewById(R.id.button_question);
		button_question.setOnClickListener(new button_questionListener());
		Bundle bundle = getIntent().getExtras();    
    	 markerID=bundle.getString("markerID");
    	 enname =bundle.getString("enName");
		Log.e("Rock", markerID+":mark121323123erID");
		
		//ImageView_reward = (ImageView) findViewById(R.id.ImageView_reward);
		//ImageView_reward.setOnClickListener(new ImageView_rewardListener());
		
		TextView_brands = (TextView) findViewById(R.id.TextView_brands);
		TextView_brands.setText("各地楼层");
		TextView_deals = (TextView) findViewById(R.id.TextView_deals);
		TextView_deals.setText("我的优惠" );
		TextView_games = (TextView) findViewById(R.id.TextView_games);
		TextView_games.setText("精彩互动");
		TextView_social = (TextView) findViewById(R.id.TextView_social);
		TextView_social.setText("微博分享");
//		RelativeLayout_bg = (RelativeLayout) findViewById(R.id.RelativeLayout_bg);
		RelativeLayout_brands = (RelativeLayout) findViewById(R.id.RelativeLayout_brands);
		RelativeLayout_brands.setOnClickListener(new brandsRelativeLayoutListener());
		RelativeLayout_deals = (RelativeLayout) findViewById(R.id.RelativeLayout_deals);
		RelativeLayout_deals.setOnClickListener(new dealsRelativeLayoutListener());
		RelativeLayout_games = (RelativeLayout) findViewById(R.id.RelativeLayout_games);
		RelativeLayout_games.setOnClickListener(new gamesRelativeLayoutListener());
		RelativeLayout_social = (RelativeLayout) findViewById(R.id.RelativeLayout_social);
		RelativeLayout_social.setOnClickListener(new socialRelativeLayoutListener());
		//Drawable drawable02 = getResources().getDrawable(R.drawable.brands_list_bg);
		//RelativeLayout_bg.setBackgroundDrawable(drawable02);

		
//		Log.d("Rock", marker_mservice.getList_item_newNotifications()+":getList_item_newNotifications()");
//		Log.d("Rock", marker_mservice.getList_item_newNotifications().size()+":getList_item_newNotifications()");
		
		
//		Log.d("Rock", marker_mservice.getList_jobj_item_floors()+":getList_jobj_item_floors()");
//		Log.d("Rock", marker_mservice.getList_jobj_item_floors().size()+":getList_jobj_item_floors()");
		
//		Log.d("Rock", marker_mservice.getList_jobj_item_nextMarkers()+":marker_mservice.getList_jobj_item_nextMarkers()");
//		Log.d("Rock", marker_mservice.getList_jobj_item_nextMarkers().size()+":marker_mservice.getList_jobj_item_nextMarkers()");
		
		
		if ( marker_mservice.getList_jobj_item_floors().size()==0) {
			RelativeLayout_brands.setVisibility(View.GONE);
		}
		if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
			RelativeLayout_games.setVisibility(View.GONE);
			inButton.setVisibility(View.GONE); 
		}else{
		//	progressDialog = ProgressDialog.show(StoreHomeActivity.this, "Loading...", "Please wait...", true, true);
			mTask02 = new MyTask02();  
	        mTask02.execute(); 
	        
		}
		try {
			if (marker_mservice.getList_item_newNotifications().size()!=0) {
				for (int i = 0; i < marker_mservice.getList_item_newNotifications().size(); i++) {
					Log.e("Rock",(String)marker_mservice.getList_item_newNotifications().get(i).get(MarkerMenuService.JSONObj_item_newNotifications_type)+":JSONObj_item_newNotifications_type");
					if ("badge".equals((String)marker_mservice.getList_item_newNotifications().get(i).get(MarkerMenuService.JSONObj_item_newNotifications_type))) {
						Intent intent=new Intent();
						intent.setClass(StoreHomeActivity.this, TransparentBadgesActivity.class);
						Bundle mBundle=new  Bundle();
						mBundle.putInt("Data", i);//压入数据  
						intent.putExtras(mBundle);
						startActivityForResult(intent, 0);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		
		
		
		
		
	      exitHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 0:
						finish();
						break;
				}
					super.handleMessage(msg);
				}
			};
			
			 meGPSProgressHandler = new Handler() {
					public void handleMessage(Message msg) {
						switch (msg.what) {
						
						case 5:
							 handlerTime_net.removeCallbacks(runnableTime_net);
							break;
						case 6:
							handlerTime_gps.removeCallbacks(runnableTime_gps);
							
							break;
						}
					
						super.handleMessage(msg);
					}
				};
//				openGPSSettings();
				mTask = new MyTask();  
		        mTask.execute(); 
			    
		        
			
	}
	
	
	 private class MyTask extends AsyncTask<String, Integer, String> {  
	        private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	        @Override  
	        protected void onPreExecute() {  
	            Log.i(TAG, "onPreExecute() called");  
	        }  
	          
	        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	        @Override  
	        protected String doInBackground(String... params) {  
	            Log.i(TAG, "doInBackground(Params... params) called"); 
	            try {
	         	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
		          +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
		          Log.d("Rock", urlcons);
		          user_mservice.setRetrieveUrl(urlcons);
		          user_mservice.retrieveUserQueryInfo();
	            } catch (Exception e) {
					// TODO: handle exception
				}
	            return null;  
	        }  
	        
	        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	        @Override  
	        protected void onPostExecute(String result) {  
	       	 Log.i(TAG, "onPostExecute(Result result) called");  
	       	Button_ME.setText(user_mservice.getInbiBalance());
	        }  
	          
	        //onCancelled方法用于在取消执行中的任务时更改UI  
	        @Override  
	        protected void onCancelled() {  
	       	 Log.i(TAG, "onCancelled() called");
	       	 
//	            LinearLayout_inbi_congriation.setVisibility(View.GONE);
	        }  
	    }
	 
	 private class MyTask02 extends AsyncTask<String, Integer, String> {  
	        private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	        @Override  
	        protected void onPreExecute() {  
	            Log.i(TAG, "onPreExecute() called");  
	            inButton.setVisibility(View.GONE);
	        }  
	          
	        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	        @Override  
	        protected String doInBackground(String... params) {  
	            Log.i(TAG, "doInBackground(Params... params) called"); 
	        //    http://122.195.135.91:2861/api/users/1?userId=1
	         /*   Map<String,String>params1=new HashMap<String,String>()  ;
	       	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
	       	 params1.put("markerId",markerID);
	       	 params1.put("ll",locationinfo.getLatitude()+","+locationinfo.getLongitude());
	       	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
	       	 params1.put("acc",locationinfo.getAccuracy()+"");
	            String urlconss = SppaConstant.WALKIN_URL_MARKER+"users/"+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/marker/"+markerID+"?"
	            +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
	            Log.d("Rock", locationinfo.getLatitude()+","+locationinfo.getLongitude()+urlconss);
	            marker_mservice.setRetrieveUrl(urlconss);
	            marker_mservice.retrieveMarkerInfo(params1);*/
	            
	            
	            return null;  
	        }  
	        
	        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	        @Override  
	        protected void onPostExecute(String result) {  
	       	 Log.i(TAG, "onPostExecute(Result result) called");  
	       	Button_ME.setText(user_mservice.getInbiBalance());
	     // 	 String[] urlPath=new   String[marker_mservice.getList_jobj_item_nextMarkers().size()]; 
	        Bitmap[] bitmap = new Bitmap[marker_mservice.getList_jobj_item_nextMarkers().size()]; 
    		 for (int i = 0; i < marker_mservice.getList_jobj_item_nextMarkers().size(); i++) {
    			 
    	String imageURL = (String)marker_mservice.getList_jobj_item_nextMarkers().get(i).get(MarkerMenuService.JSONObj_brands_nextMarkers_imageURL);
    			 if ("".equals(imageURL)) {
    				 imageURL = "http://static.tieba.baidu.com/tb/indexfiles/v2/images/header_line.png";
    			}else{
    				imageURL = SppaConstant.WALKIN_URL_BASE+imageURL;
    			}
    			 
    	 		//	urlPath[i]= new String(imageURL);//为第一个数组元素开辟空间 
    			 Log.d("Rock", imageURL+":imageURL");
    			 Drawable cachedImage = asyncImageLoader.loadImageFromUrl(StoreHomeActivity.this, imageURL);
 	 			Log.d("Rock", cachedImage+":cachedImage");
 	 			try {
 	 				bitmap[i]=NetworkToPic.drawableToBitmap(cachedImage);
				} catch (Exception e) {
					// TODO: handle exception
				}
 	 			
    		 }
    	 			//Bitmap[] bitmapa = networkbitmapinstoreinfo.getBitmap();
         			
    				//if (bitmapa==null) {
    				//	Bitmap[] bitmap = NetworkToPic.getBitmapArray(urlPath);
    		 			networkbitmapinstoreinfo.setBitmap(bitmap);
    				//}
				//progressDialog.dismiss();
		        inButton.setVisibility(View.VISIBLE);
	       	
	       	
	        }  
	          
	        //onCancelled方法用于在取消执行中的任务时更改UI  
	        @Override  
	        protected void onCancelled() {  
	       	 Log.i(TAG, "onCancelled() called");
	       	 
//	            LinearLayout_inbi_congriation.setVisibility(View.GONE);
	        }  
	    }
	 
	 
		class button_questionListener implements OnClickListener{
			public void onClick(View v) {
				
				myAnimation_Alpha = AnimationUtils.loadAnimation(StoreHomeActivity.this,R.anim.my_alpha_action);
				button_question.startAnimation(myAnimation_Alpha);
				 Intent intent = new Intent(StoreHomeActivity.this, TransparentAllActivity.class);
		         Bundle mBundle = new Bundle();
				 mBundle.putString("Activity", "Activity_Message");// 压入数据
				 intent.putExtras(mBundle);
				 startActivityForResult(intent, 0);
				
				
			}
		}
	 private class MyTask03 extends AsyncTask<String, Integer, String> {  
	        private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	        @Override  
	        protected void onPreExecute() {  
	            Log.i(TAG, "onPreExecute() called");  
	            inButton.setVisibility(View.GONE); 
	        }  
	          
	        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	        protected String doInBackground(String... params) {  
	             Log.i(TAG, "doInBackground(Params... params) called"); 
	             //http://122.195.135.91:2861/api/deals/users/1?userId=1&ll=31.2184028%2C121.4174401
	         	 Map<String,String>params1=new HashMap<String,String>()  ;
	        	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
	        	 params1.put("markerId", markerID);
	        	 params1.put("ll",(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044));
	        	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
	        	 params1.put("acc",locationinfo.getAccuracy()+"");
	        	 String urlcons = SppaConstant.WALKIN_URL_MARKER+"users/"+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/marker/"+markerID+"?"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
	             Log.d("Rock", locationinfo.getLatitude()+","+locationinfo.getLongitude()+urlcons);
	         	//if (marker_mservice.getList_jobj_item_deals().size()<2) {
	         		marker_mservice.setRetrieveUrl(urlcons);
	                marker_mservice.retrieveMarkerInfo(params1);
	    		//}
	             
	             return null;  
	         }  
	        
	        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	        @Override  
	        protected void onPostExecute(String result) {  
	      //  	 String[] urlPath=new   String[marker_mservice.getList_jobj_item_nextMarkers().size()]; 
	        	 Bitmap[] bitmap = new Bitmap[marker_mservice.getList_jobj_item_nextMarkers().size()]; 
	    		 for (int i = 0; i < marker_mservice.getList_jobj_item_nextMarkers().size(); i++) {
	    			 
	    	String imageURL = (String)marker_mservice.getList_jobj_item_nextMarkers().get(i).get(MarkerMenuService.JSONObj_brands_nextMarkers_imageURL);
	    			 if ("".equals(imageURL)) {
	    				 imageURL = "http://static.tieba.baidu.com/tb/indexfiles/v2/images/header_line.png";
	    			}else{
	    				imageURL = SppaConstant.WALKIN_URL_BASE+imageURL;
	    			}
	    			 
	    	 	//		urlPath[i]= new String(imageURL);//为第一个数组元素开辟空间 
	    			 Drawable cachedImage = asyncImageLoader.loadImageFromUrl(StoreHomeActivity.this, imageURL);
	  	 			Log.d("Rock", cachedImage+":cachedImage");
	  	 			bitmap[i]=NetworkToPic.drawableToBitmap(cachedImage);
	    		 }
	    	 		//	Bitmap[] bitmapa = networkbitmapinstoreinfo.getBitmap();
	         			
	    			//	if (bitmapa==null) {
	    			//		Bitmap[] bitmap = NetworkToPic.getBitmapArray(urlPath);
	    		 			networkbitmapinstoreinfo.setBitmap(bitmap);
	    			//	}
//			progressDialog.dismiss();
	        inButton.setVisibility(View.VISIBLE); 
	        }  
	          
	        //onCancelled方法用于在取消执行中的任务时更改UI  
	        @Override  
	        protected void onCancelled() {  
	       	 Log.i(TAG, "onCancelled() called");
	       	 
//	            LinearLayout_inbi_congriation.setVisibility(View.GONE);
	        }  
	    }
	 
	 class gobackListener implements OnClickListener{
			public void onClick(View v) {
				AlertDialog.Builder b1 = new AlertDialog.Builder(StoreHomeActivity.this);
				b1.setTitle("注意喔");
	            b1.setMessage("您真的想离开"+enname+"页面吗?\n"+"一旦离开，您还需要在签到回来。");
	            b1.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	                public void onClick(DialogInterface dialog, int which){
	               
	                }
	            });
	            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                public void onClick(DialogInterface dialog, int which){
	                	 //	Message message = Message.obtain(IndexActivity.getExitHandler(),1);
		    			//	message.sendToTarget();
		    				try {
			    		//		 message = Message.obtain(BrandsActivity.getmProgressHandler(),1);
			    		//		message.sendToTarget();
			    				
			    			} catch (Exception e) {
			    				// TODO: handle exception
			    			}
		                	
		                	finish();
	                }
	            });
	            b1.show();
				
				
				
			}
		}
	class MEButtonListener implements OnClickListener {
		public void onClick(View v) {
			Log.d("Rock", ":MoreActivity");
			// mRenderer.isnum0=0;
			// finish();
			Intent intent = new Intent(StoreHomeActivity.this,MeActivity.class);
			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "mainbar");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);

		}
	}

	class inButtonListener implements OnClickListener {
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(StoreHomeActivity.this,R.anim.my_alpha_action);
			inButton.startAnimation(myAnimation_Alpha);
			if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
				AlertDialog.Builder b =new AlertDialog.Builder(context);
            	b.setTitle("抱歉");
            	b.setMessage("暂无店内marker");
            	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                    }
                });
                b.show();
			}else{
			
			Intent intent = new Intent(StoreHomeActivity.this, StringOGLTutorialInStore.class);
			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
			}
		}
	}

	class brandsRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(StoreHomeActivity.this, ProductMainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			mBundle.putString("markerID", markerID);// 压入数据
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}
	class ImageView_rewardListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(StoreHomeActivity.this, BadgesGridActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "0");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}
	class dealsRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(StoreHomeActivity.this, ProductMainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "1");// 压入数据
			mBundle.putString("markerID", markerID);// 压入数据
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}

	class gamesRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(StoreHomeActivity.this, ProductMainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "3");// 压入数据
			mBundle.putString("markerID", markerID);// 压入数据
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}

	class socialRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(StoreHomeActivity.this, ProductMainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "4");// 压入数据
			mBundle.putString("markerID", markerID);// 压入数据
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			
		}
	}

	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		// enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName)) ;
	//	Log.d("Rock", (String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_weiboAccountName)+":(String) marker_mservice");
		String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
		String	bg_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/ios/"+enname+defaults_mservice.getBrandBG()+".jpg").toLowerCase();
		Log.d("Rock", enname_img_url+":bg_img_url");
		loadImage_Button4(enname_img_url, (Button)findViewById(R.id.Button_top_logo));
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		super.onStart();
	}
		
	
	protected void onRestart() {
		Log.d("lifecycle", markerID+"onRestart()");
		if (marker_mservice.getList_jobj_item_deals().size()<2) {
			
			
//		    	if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
//					RelativeLayout_games.setVisibility(View.GONE);
//					inButton.setVisibility(View.GONE); 
//				}else{
				//	progressDialog = ProgressDialog.show(StoreHomeActivity.this, "Loading...", "Please wait...", true, true);
					mTask03 = new MyTask03();  
			        mTask03.execute(); 
			        
//				}
		}
		 mTask = new MyTask();  
	        mTask.execute(); 
        
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	protected void onStop() {
		Log.d("lifecycle", "onStop()");
		// TODO Auto-generated method stub
		super.onStop();
	}
	protected void onDestroy() {
		// TODO Auto-generated method stub
		 handlerTime_net.removeCallbacks(runnableTime_net);
		 handlerTime_gps.removeCallbacks(runnableTime_gps);
		stopLocationService();
//		System.exit(0);
		// 或者下面这种方式
		// android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	private void openGPSSettings() {
  		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
  		if (!alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
  				&&!alm.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
  			AlertDialog.Builder b = new AlertDialog.Builder(StoreHomeActivity.this);
  	         b.setTitle("友情提示");
  	         b.setMessage("如果在室外请开启GPS"+"\n"+"如果在室内请开启无线网络或wifi");
  	         
  	         b.setNegativeButton("取消", new DialogInterface.OnClickListener(){
  	             public void onClick(DialogInterface dialog, int which){
  	             }
  	         });
  	         b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
  	             public void onClick(DialogInterface dialog, int which) {
  	            	Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
  	          		startActivityForResult(intent, 0); 
  	             }
  	         });
  	         b.show();
  		}
  		 if (alm.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER))
 		{
 			Toast.makeText(StoreHomeActivity.this, "无线网络模块正常", Toast.LENGTH_SHORT).show();
 			}
 		if(alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
 		{
 			Toast.makeText(StoreHomeActivity.this, "GPS模块正常", Toast.LENGTH_SHORT).show();
 		}
  		
  	}


	public void startnetLoactionService() {
		 //获取到LocationManager对象
         locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //创建一个Criteria对象
        Criteria criteria = new Criteria();
        //设置粗略精确度
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //设置是否需要返回海拔信息
        criteria.setAltitudeRequired(false);
        //设置是否需要返回方位信息
        criteria.setBearingRequired(false);
        //设置是否允许付费服务
        criteria.setCostAllowed(true);
        //设置电量消耗等级
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //设置是否需要返回速度信息
        criteria.setSpeedRequired(false);
        //根据设置的Criteria对象，获取最符合此标准的provider对象
         currentProvider_net = locationManager.getBestProvider(criteria, true);
        Log.d("Location", "currentProvider_net: " + currentProvider_net);
        
        //根据当前provider对象获取最后一次位置信息
        currentLocation = locationManager.getLastKnownLocation(currentProvider_net);
        //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        updateToNewLocation(currentLocation);
        //if(currentLocation_net != null){
            locationManager.requestLocationUpdates(currentProvider_net,  500, 500, locationListener);
       // }else{
            //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
            //每隔10秒获取一次位置信息
        	handlerTime_net.removeCallbacks(runnableTime_net);
        	handlerTime_net.postDelayed(runnableTime_net, 5000);   
       // }

	}
	public void startgpsLoactionService() {
		 //获取到LocationManager对象
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
       //创建一个Criteria对象
       Criteria criteria = new Criteria();
       //设置粗略精确度
       criteria.setAccuracy(Criteria.ACCURACY_FINE);
       //设置是否需要返回海拔信息
       criteria.setAltitudeRequired(false);
       //设置是否需要返回方位信息
       criteria.setBearingRequired(false);
       //设置是否允许付费服务
       criteria.setCostAllowed(true);
       //设置电量消耗等级
       criteria.setPowerRequirement(Criteria.POWER_LOW);
       //设置是否需要返回速度信息
       criteria.setSpeedRequired(false);

       //根据设置的Criteria对象，获取最符合此标准的provider对象
       currentProvider_gps = locationManager.getBestProvider(criteria, true);
       Log.d("Location", "currentProvider_gps: " + currentProvider_gps);
      
      
       
       
       //根据当前provider对象获取最后一次位置信息
       currentLocation = locationManager.getLastKnownLocation(currentProvider_gps);
       //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
       updateToGPSLocation(currentLocation);
//       if(currentLocation_gps == null){
           locationManager.requestLocationUpdates(currentProvider_gps,  500, 500, locationListener);
//       }else{
    	   handlerTime_gps.removeCallbacks(runnableTime_gps);
    	   handlerTime_gps.postDelayed(runnableTime_gps, 5000);   
//       }
       //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
       //每隔10秒获取一次位置信息
     
  
	}
	   public Handler handlerTime_gps = new Handler();  
		public Runnable runnableTime_gps = new Runnable() {  
		    public void run() {
		    	currentLocation = locationManager.getLastKnownLocation(currentProvider_gps);
		            if(currentLocation != null){
//		                Log.d("Location", "Latitude: " + currentLocation.getLatitude());
//		                Log.d("Location", "location: " + currentLocation.getLongitude());
		       		 Message msg= Message.obtain(meGPSProgressHandler,6);
		    	     msg.sendToTarget();
		            }else{
//		                Log.d("Location", "Latitude: " + 0);
//		                Log.d("Location", "location: " + 0);
		            }
		    	
	     		handlerTime_gps.postDelayed(this, 5000);  
		    }  
		};
		 public Handler handlerTime_net = new Handler();  
			public Runnable runnableTime_net = new Runnable() {  
			    public void run() {
			    	currentLocation = locationManager.getLastKnownLocation(currentProvider_net);
			            if(currentLocation != null){
//			                Log.d("Location", "Latitude: " + currentLocation.getLatitude());
//			                Log.d("Location", "location: " + currentLocation.getLongitude());
			       		 Message msg= Message.obtain(meGPSProgressHandler,5);
			    	     msg.sendToTarget();
			            }else{
//			                Log.d("Location", "Latitude: " + 10);
//			                Log.d("Location", "location: " + 10);
			            }
			    	
		     		handlerTime_net.postDelayed(this, 5000);  
			    }  
			};
	private void updateToNewLocation(Location location) {

//       TextView tv1;
//        tv1 = (TextView) this.findViewById(R.id.tv1);
        if (location != null) {
              latitude_net = location.getLatitude();
             longitude_net= location.getLongitude();
             accuracy_net = location.getAccuracy();
            Log.d("Rock", "维度：" +  latitude_net+ "\n经度" + longitude_net+ "\n水平精度" + accuracy_net);  
            
            if (accuracy_net<accuracy_gps) {
            locationinfo.setLatitude(latitude_net-0.0018);
            locationinfo.setLongitude(longitude_net+0.0044);
            locationinfo.setAccuracy(accuracy_net);
            }
           /*        //更具地理环境来确定编码
            Geocoder gc=new Geocoder(this,Locale.getDefault());
            try
            {
            	//取得地址相关的一些信息\经度、纬度
                List<Address> addresses=gc.getFromLocation(latitude, longitude,1);
                StringBuilder sb=new StringBuilder();
                if(addresses.size()>0)
                {
                    Address address=addresses.get(0);
                    for(int i=0;i<address.getMaxAddressLineIndex();i++)
                        sb.append(address.getAddressLine(i)).append("\n");
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                      String  addressString=sb.toString();
                        Toast.makeText(IndexActivity.this, addressString, Toast.LENGTH_LONG).show();
                }
            }catch(IOException e){
            	Toast.makeText(IndexActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
            }*/
//            tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
        } else {
//            tv1.setText("无法获取地理信息");
        }

    }
	private void updateToGPSLocation(Location location) {

//	      TextView tv1;
//	       tv1 = (TextView) this.findViewById(R.id.tv1);
	       if (location != null) {
	             latitude_gps = location.getLatitude();
	            longitude_gps= location.getLongitude();
	             accuracy_gps = location.getAccuracy();
	         // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
	       //   Toast.makeText(SplashActivity.this, "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy, Toast.LENGTH_SHORT).show();
	           
	             if (accuracy_gps<accuracy_net) {
	            	 locationinfo.setLatitude(latitude_gps-0.0018);
	                 locationinfo.setLongitude(longitude_gps+0.0044);
	                 locationinfo.setAccuracy(accuracy_gps);
				}
	           
	       } else {
//	           tv1.setText("无法获取地理信息");
	       }

	   }
	public void stopLocationService() {
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	//创建位置监听器
    private LocationListener locationListener = new LocationListener(){
        //位置发生改变时调用
        @Override
        public void onLocationChanged(Location location) {
        	 if (location != null) {
        		 updateToNewLocation(location);
             } 
        	
            Log.d("Location", "onLocationChanged");
            Log.d("Location", "onLocationChanged Latitude" + location.getLatitude());
            Log.d("Location", "onLocationChanged location" + location.getLongitude());
        }

        //provider失效时调用
        @Override
        public void onProviderDisabled(String provider) {
        	updateToNewLocation(null);
            Log.d("Location", "onProviderDisabled");
        }

        //provider启用时调用
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Location", "onProviderEnabled");
        }

        //状态改变时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Location", "onStatusChanged");
        }
    };
    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
    //采用Handler+Thread+封装外部接口
    private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
         Drawable cacheImage = asyncImageLoader.loadDrawable(StoreHomeActivity.this,url,new ImageCallback() {
             //请参见实现：如果第一次加载url时下面方法会执行
             public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            	 
            	 if (imageDrawable != null ) { 
            		 bgimageView.setBackgroundDrawable(imageDrawable);
            	 }else{
            		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
            		 bgimageView.setBackgroundDrawable(drawable_bg);
            	 }
             }
         });
        if(cacheImage!=null){
        	bgimageView.setBackgroundDrawable(cacheImage);
        }else{
        	 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
        	bgimageView.setBackgroundDrawable(drawable_bg);
   	 }
    }
    
    private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();
    //采用Handler+Thread+封装外部接口
    private void loadImage5(final String url, final RelativeLayout bgimageView) {
          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
         Drawable cacheImage = asyncImageLoader3.loadDrawable(url,new AsyncImageLoader3.ImageCallback() {
             //请参见实现：如果第一次加载url时下面方法会执行
             public void imageLoaded(Drawable imageDrawable) {
            	 
            	 if (imageDrawable != null ) { 
            		 bgimageView.setBackgroundDrawable(imageDrawable);
            	 }else{
            		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
            		 bgimageView.setBackgroundDrawable(drawable_bg);
            	 }
             }
         });
        if(cacheImage!=null){
        	 bgimageView.setBackgroundDrawable(cacheImage);
        }else{
        	 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
	        	bgimageView.setBackgroundDrawable(drawable_bg);
   	 }
    }
    
    
    private void loadImage_Button(final String url, final Button bgimageView) {
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
    	 Drawable cacheImage = asyncImageLoader3.loadDrawable( url, new AsyncImageLoader3.ImageCallback() {
	           //请参见实现：如果第一次加载url时下面方法会执行
	           public void imageLoaded(Drawable imageDrawable ) {
          	 
          	 if (imageDrawable != null ) { 
          		 bgimageView.setBackgroundDrawable(imageDrawable);
          	 }else{
          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
          		 bgimageView.setBackgroundDrawable(drawable_bg);
          	 }
           }
       });
      if(cacheImage!=null){
      	bgimageView.setBackgroundDrawable(cacheImage);
      }else{
      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
      	bgimageView.setBackgroundDrawable(drawable_bg);
 	 }
  }
    private void loadImage_Button4(final String url, final Button bgimageView) {
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
    	 Drawable cacheImage = asyncImageLoader.loadDrawable( StoreHomeActivity.this,url, new ImageCallback() {
	           //请参见实现：如果第一次加载url时下面方法会执行
	           public void imageLoaded(Drawable imageDrawable, String imageUrl ) {
          	 
          	 if (imageDrawable != null ) { 
          		 bgimageView.setBackgroundDrawable(imageDrawable);
          	 }else{
          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
          		 bgimageView.setBackgroundDrawable(drawable_bg);
          	 }
           }
       });
      if(cacheImage!=null){
      	bgimageView.setBackgroundDrawable(cacheImage);
      }else{
      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
      	bgimageView.setBackgroundDrawable(drawable_bg);
 	 }
  }
	 
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder b1 = new AlertDialog.Builder(StoreHomeActivity.this);
			b1.setTitle("注意喔");
            b1.setMessage("确定要离开"+enname+"页面吗?\n"+"一旦离开，您还需要在签到回来。");
            b1.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                }
            });
            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                	//Message message = Message.obtain(IndexActivity.getExitHandler(),1);
    				//message.sendToTarget();
    				try {
	    				// message = Message.obtain(BrandsActivity.getmProgressHandler(),1);
	    				//message.sendToTarget();
	    				
	    			} catch (Exception e) {
	    				// TODO: handle exception
	    			}
                	finish();
                }
            });
            b1.show();
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
	    handlerTime_net.removeCallbacks(runnableTime_net);
		 handlerTime_gps.removeCallbacks(runnableTime_gps);
		stopLocationService();
		Log.d("lifecycle", "onPause()");
	    MobclickAgent.onPause(this);
	}
}
