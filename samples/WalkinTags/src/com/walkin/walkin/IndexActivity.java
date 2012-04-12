package com.walkin.walkin;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.BrandsInfo;
import com.walkin.bean.LocationInfo;
import com.walkin.bean.NetworkBitmapInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.MyButton;
import com.walkin.common.NetworkToPic;
import com.walkin.common.VeDate;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
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

public class IndexActivity extends Activity {
	private Button inButton;
	private MyButton myhome;
	private RelativeLayout RelativeLayout_brands;
	private RelativeLayout RelativeLayout_deals;
	private RelativeLayout RelativeLayout_games;
	private RelativeLayout RelativeLayout_social;
	private Button Button_ME,Button_ding,button_question;
	private TextView TextView_brands;
	private TextView TextView_deals;
	private TextView TextView_games;
	private TextView TextView_social;
//	private TextView TextView_reward;
	private RelativeLayout RelativeLayout_bg;
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
	protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	protected final static NetworkBitmapInfo networkbitmapinfo = NetworkBitmapInfo.getInstance();
	protected final static BrandsInfo brandsinfo = BrandsInfo.getInstance();
	private List<Map<String, Object>> mData;

	private Map<String, Object> map ;
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
    private MyTask mTask;  
	private static Context context;
    private MyTaskMeInbiBalance mTaskInbi;  
    private ProgressDialog progressDialog; 
	private Animation myAnimation_Alpha;
	PackageManager pm;
	private SharedPreferences sp;
 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_act);
		context = this;
		defaults_mservice.setActivity(this);
		locationinfo.setActivity(this);
		user_mservice.setActivity(this);
		networkbitmapinfo.setActivity(this);
		brands_mservice.setActivity(this);
		brandsinfo.setActivity(this);
	     pm = getPackageManager();
		Button_ME = (Button) findViewById(R.id.Button_ME);
		myhome = new MyButton(this);
		Integer[] mHomeState = {R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
		Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
		Button_ME.setOnClickListener(new MEButtonListener());
		Button_ding = (Button) findViewById(R.id.Button_ding);
		Button_ding.setOnClickListener(new Button_dingListener());
		button_question	= (Button) findViewById(R.id.button_question);
		button_question.setOnClickListener(new button_questionListener());
		
		 sp = getSharedPreferences("user", 0);
		inButton = (Button) findViewById(R.id.inButton);
		myhome = new MyButton(this);
		Integer[] mHomeState_in = { R.drawable.inbuttoncentertab,
				R.drawable.inbuttoncentertab, R.drawable.inbuttoncentertab };
		inButton.setBackgroundDrawable(myhome.setbg(mHomeState_in));
		inButton.setOnClickListener(new inButtonListener());
		
		
		
		if ("true".equals(defaults_mservice.getClearCacheType())&&(VeDate.getNow().getTime())>(VeDate.strToDateLong(defaults_mservice.getClearCacheDate()).getTime())) {
			 clearnCrash1();
		}

		
		
		TextView_brands = (TextView) findViewById(R.id.TextView_brands);
		TextView_brands.setText("合作店铺");
		TextView_deals = (TextView) findViewById(R.id.TextView_deals);
		TextView_deals.setText("我的优惠");
		TextView_games = (TextView) findViewById(R.id.TextView_games);
		TextView_games.setText("精彩互动");
		TextView_social = (TextView) findViewById(R.id.TextView_social);
		TextView_social.setText("微博分享");
		RelativeLayout_bg = (RelativeLayout) findViewById(R.id.RelativeLayout_bg);
		RelativeLayout_brands = (RelativeLayout) findViewById(R.id.RelativeLayout_brands);
		RelativeLayout_brands.setOnClickListener(new brandsRelativeLayoutListener());
		RelativeLayout_deals = (RelativeLayout) findViewById(R.id.RelativeLayout_deals);
		RelativeLayout_deals.setOnClickListener(new dealsRelativeLayoutListener());
		RelativeLayout_games = (RelativeLayout) findViewById(R.id.RelativeLayout_games);
		RelativeLayout_games.setOnClickListener(new gamesRelativeLayoutListener());
		RelativeLayout_social = (RelativeLayout) findViewById(R.id.RelativeLayout_social);
		RelativeLayout_social.setOnClickListener(new socialRelativeLayoutListener());
		Drawable drawable02 = getResources().getDrawable(R.drawable.overview);
		RelativeLayout_bg.setBackgroundDrawable(drawable02);
	      exitHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 1:
						 mTask = new MyTask();  
				         mTask.execute(); 
			            break;
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
				openGPSSettings();
				Button_ME.setText(user_mservice.getInbiBalance());
				String urlcons = SppaConstant.WALKIN_URL_DEFAULTS;
				 defaults_mservice.setRetrieveUrl(urlcons);
				 defaults_mservice.retrieveDefaultsInfo(urlcons);
				mTaskInbi = new MyTaskMeInbiBalance();  
			 	mTaskInbi.execute(); 
			    startnetLoactionService();
				startgpsLoactionService();
			 	 mTask = new MyTask();  
		         mTask.execute(); 
				
		         progressDialog = ProgressDialog.show(IndexActivity.this, "Loading...", "Please wait...", true, false);
		         
		         
		         if ("1".equals(sp.getString("istransparentall", null))) {
		        	 Intent intent = new Intent(IndexActivity.this, TransparentAllActivity.class);
			         Bundle mBundle = new Bundle();
					 mBundle.putString("Activity", "IndexActivity");// 压入数据
					 intent.putExtras(mBundle);
					 startActivityForResult(intent, 0);
					// overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
					}
		         
		         
		         
			
	}
	class Button_dingListener implements OnClickListener{
		public void onClick(View v) {
			
			myAnimation_Alpha = AnimationUtils.loadAnimation(IndexActivity.this,R.anim.my_alpha_action);
			Button_ding.startAnimation(myAnimation_Alpha);
			startnetLoactionService();
			startgpsLoactionService();
		}
	}
	class button_questionListener implements OnClickListener{
		public void onClick(View v) {
			
			myAnimation_Alpha = AnimationUtils.loadAnimation(IndexActivity.this,R.anim.my_alpha_action);
			button_question.startAnimation(myAnimation_Alpha);
			
			 Intent intent = new Intent(IndexActivity.this, TransparentAllActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "Activity_Message");// 压入数据
			 intent.putExtras(mBundle);
			 startActivityForResult(intent, 0);
		}
	}
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	 private class MyTask extends AsyncTask<String, Integer, String> {  
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
             String urlcons = SppaConstant.WALKIN_URL_BRANDS+"?"+"ll="+(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044)+"&"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
             +"&acc="+locationinfo.getAccuracy()+"&apikey=BYauu6D9";
             Log.d("Rock", urlcons);
        	 brands_mservice.setRetrieveUrl(urlcons);
        	 brands_mservice.retrieveBrandsInfo();
             return null;  
         }  
         
         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
         @SuppressWarnings("unchecked")
		@Override  
         protected void onPostExecute(String result) {  
        	 Log.i(TAG, "onPostExecute(Result result) called");  
        	 Bitmap[] bitmap = new Bitmap[brands_mservice.getList_jobj_item().size()]; 
 			 for (int i = 0; i < brands_mservice.getList_jobj_item().size(); i++) {
			 try {
				 //List<Map<String, Object>>  list = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_stores);
				// String str_distance = (String) list.get(0).get(BrandsMenuService.JSONObj_item_stores_distance);
				// int  int_distance=new Integer(Integer.parseInt(str_distance)).intValue();
				// Log.e("Rock", int_distance+":int_distance");
//				 if (int_distance<1000) {
					 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_marker);
					 String marker_imageURL = (String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
					 if ("".equals(marker_imageURL)) {
						 marker_imageURL = "http://static.tieba.baidu.com/tb/indexfiles/v2/images/header_line.png";
					}else{
						marker_imageURL = SppaConstant.WALKIN_URL_BASE+(String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
					}
				 			//urlPath[i]= new String(marker_imageURL);//为第一个数组元素开辟空间 
				 			
		 			Drawable cachedImage = asyncImageLoader.loadImageFromUrl(IndexActivity.this, marker_imageURL);
		 			Log.d("Rock", cachedImage+":cachedImage");
		 			bitmap[i]=NetworkToPic.drawableToBitmap(cachedImage);
//				}
				
			} catch (Exception e) {
			}	 
			
			}
 			 
 			//Bitmap[] bitmapa = networkbitmapinfo.getBitmap();
 			
				//if (bitmapa==null) {
				//	Bitmap[] bitmap = NetworkToPic.getBitmapArray(urlPath);
		 			networkbitmapinfo.setBitmap(bitmap);
		 		//	brandsinfo.setmList(brands_mservice.getList_jobj_item());
		 			 mData = getData(brands_mservice.getList_jobj_item());
				//}
 			
 			inButton.setVisibility(View.VISIBLE); 
 			progressDialog.dismiss();
         }  
           
         
         
         
         //onCancelled方法用于在取消执行中的任务时更改UI  
         @Override  
         protected void onCancelled() {  
        	 Log.i(TAG, "onCancelled() called");
        	 
//             LinearLayout_inbi_congriation.setVisibility(View.GONE);
         }  
     }
//	 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		private List<Map<String, Object>> getData(List<Map<String, Object>> brands_list ) {
			for (int i = 0; i < brands_list.size(); i++) {
		 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_list.get(i).get(BrandsMenuService.list_item_marker);
		 String markerID = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_id);
		 String TextView_inbiEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
		 String	TextView_enName =  (String)brands_list.get(i).get(BrandsMenuService.JSONObj_item_enName);
		 String	TextView_dealsEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
		 String	event = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_event);
					map = new HashMap<String, Object>();
						map.put("JSONObj_item_marker_id", markerID);
						map.put("JSONObj_item_marker_inbiEarned", TextView_inbiEarned);
						map.put("JSONObj_item_enName", TextView_enName);
						map.put("JSONObj_item_marker_dealsEarned", TextView_dealsEarned);
						map.put("JSONObj_item_marker_event", event);
					mList.add(map);
				}
			brandsinfo.setmList(mList);
			
//			list=brands_list;
//			for (int i = 0; i < list.size(); i++) {
//				Log.e("Rock", (String)brands_list.get(i).get(BrandsMenuService.JSONObj_item_display)+":JSONObj_item_display");
//				if ("false".equals((String)list.get(i).get(BrandsMenuService.JSONObj_item_display))) {
//					list.remove(i);
//				//	num_s=isremovenum++;
//					}
//					//list.add(map);
//				
//			}
			
		return mList;
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
	        	 Button_ME.setText(user_mservice.getInbiBalance());
	         }  
	           
	     }
	
	
	class MEButtonListener implements OnClickListener {
		public void onClick(View v) {
			Log.d("Rock", ":MoreActivity");
			// mRenderer.isnum0=0;
			// finish();
			Intent intent = new Intent(IndexActivity.this,MeActivity.class);
			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "mainbar");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
		

		}
	}

	class inButtonListener implements OnClickListener {
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(IndexActivity.this,R.anim.my_alpha_action);
			inButton.startAnimation(myAnimation_Alpha);
			Intent intent = new Intent(IndexActivity.this, StringOGLTutorial.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "identification");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}

	class brandsRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(IndexActivity.this, MainActivity.class);

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

			Intent intent = new Intent(IndexActivity.this, MainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "1");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}

	class gamesRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(IndexActivity.this, MainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "3");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
		}
	}

	class socialRelativeLayoutListener implements OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(IndexActivity.this, MainActivity.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("Data", "4");// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			
		}
	}
	
		
	protected void onRestart() {
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
	
		 mTaskInbi = new MyTaskMeInbiBalance();  
	 	 mTaskInbi.execute(); 
	     Button_ME.setText(user_mservice.getInbiBalance());
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
  			AlertDialog.Builder b = new AlertDialog.Builder(IndexActivity.this);
  	         b.setTitle("抱歉");
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
 			Toast.makeText(IndexActivity.this, "无线网络模块正常", Toast.LENGTH_SHORT).show();
 			}
 		if(alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
 		{
 			Toast.makeText(IndexActivity.this, "GPS模块正常", Toast.LENGTH_SHORT).show();
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
        try {
            currentLocation = locationManager.getLastKnownLocation(currentProvider_net);
		} catch (Exception e) {
			// TODO: handle exception
		  Toast.makeText(IndexActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
		}
        //根据当前provider对象获取最后一次位置信息
    
        //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        updateToNewLocation(currentLocation);
        //if(currentLocation_net != null){
        try { 
        	locationManager.requestLocationUpdates(currentProvider_net,  500, 500, locationListener);
		} catch (Exception e) {
			// TODO: handle exception
		  Toast.makeText(IndexActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
		}
           
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
      
      
       try { 
    	   currentLocation = locationManager.getLastKnownLocation(currentProvider_gps);
    	   locationManager.requestLocationUpdates(currentProvider_gps,  500, 500, locationListener);
		} catch (Exception e) {
			// TODO: handle exception
		  Toast.makeText(IndexActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
		}
       
       //根据当前provider对象获取最后一次位置信息
      
       //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
       updateToGPSLocation(currentLocation);
//       if(currentLocation_gps == null){
          
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
		    	try {
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
				} catch (Exception e) {
					// TODO: handle exception
				}
		    	
		    	
	     		handlerTime_gps.postDelayed(this, 5000);  
		    }  
		};
		 public Handler handlerTime_net = new Handler();  
			public Runnable runnableTime_net = new Runnable() {  
			    public void run() {
			    	try {
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
					} catch (Exception e) {
						// TODO: handle exception
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
          // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
            Toast.makeText(IndexActivity.this, "维度：" +  latitude_net+ "\n经度" + longitude_net+ "\n水平精度" + accuracy_net, Toast.LENGTH_SHORT).show();
            
            locationinfo.setLatitude(latitude_net-0.0018);
            locationinfo.setLongitude(longitude_net+0.0044);
            locationinfo.setAccuracy(accuracy_net);
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
        	 Toast.makeText(IndexActivity.this, "定位失败请设置定位功能", Toast.LENGTH_SHORT).show();
//            tv1.setText("无法获取地理信息");
        }

    }
	private void updateToGPSLocation(Location location) {

//      TextView tv1;
//       tv1 = (TextView) this.findViewById(R.id.tv1);
       if (location != null) {
             latitude_gps = location.getLatitude();
            longitude_gps= location.getLongitude();
             accuracy_gps = location.getAccuracy();
         // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
          Toast.makeText(IndexActivity.this, "维度：" +  latitude_gps+ "\n经度" + longitude_gps+ "\n水平精度" + accuracy_gps, Toast.LENGTH_SHORT).show();
           
             if (accuracy_gps<accuracy_net) {
            	 locationinfo.setLatitude(latitude_gps-0.0018);
                 locationinfo.setLongitude(longitude_gps+0.0044);
                 locationinfo.setAccuracy(accuracy_gps);
			}
//           tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
       } else {
//           tv1.setText("无法获取地理信息");
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
    private  long getEnvironmentSize()
    {
      File localFile = Environment.getDataDirectory();
      long l1;
      if (localFile == null)
        l1 = 0L;
      while (true)
      {
        
        String str = localFile.getPath();
        StatFs localStatFs = new StatFs(str);
        long l2 = localStatFs.getBlockSize();
        l1 = localStatFs.getBlockCount() * l2;
        return l1;
      }
    }
	public void clearnCrash1(){
		try {
			Method localMethod = pm.getClass().getMethod("freeStorageAndNotify", Long.TYPE,IPackageDataObserver.class);
			Long localLong = Long.valueOf(getEnvironmentSize() - 1L);
			Object[] arrayOfObject = new Object[2];
		      arrayOfObject[0] = localLong;
		      localMethod.invoke(pm,localLong,new IPackageDataObserver.Stub(){
				@Override
				public void onRemoveCompleted(String packageName,
						boolean succeeded) throws RemoteException {
				}});
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String bg_img_url=SppaConstant.WALKIN_URL_BASE+"style/android/overview.jpg";
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		super.onStart();
	}
    //采用Handler+Thread+封装外部接口
    private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
         Drawable cacheImage = asyncImageLoader.loadDrawable(IndexActivity.this,url,new ImageCallback() {
             //请参见实现：如果第一次加载url时下面方法会执行
             public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            	 
            	 if (imageDrawable != null ) { 
            		 bgimageView.setBackgroundDrawable(imageDrawable);
            	 }else{
            		 Drawable drawable_bg = getResources().getDrawable(R.drawable.overview);
            		 bgimageView.setBackgroundDrawable(drawable_bg);
            	 }
             }
         });
        if(cacheImage!=null){
        	bgimageView.setBackgroundDrawable(cacheImage);
        }else{
        	 Drawable drawable_bg = getResources().getDrawable(R.drawable.overview);
        	bgimageView.setBackgroundDrawable(drawable_bg);
   	 }
    }
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(
					"关闭应用程序").setMessage(R.string.quit_desc).setNegativeButton(
					R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).setPositiveButton(R.string.confirm,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							finish();
						}
					}).show();
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
