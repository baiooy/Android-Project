package com.walkin.walkin;




import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

public class SplashActivity extends BgWalkinActivity {
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
    public static Handler mProgressHandler =null;
    public static Context context;
    private boolean isWifi= false;
    private String startupTips="网络初始化...";
	private SharedPreferences sp;
    private boolean m_bIsFirstLogin = true;
	protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	int screenWidth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
  	  	context =this;
    	defaults_mservice.setActivity(this);
    	user_mservice.setActivity(this);
    	locationinfo.setActivity(this);
    	MobclickAgent.update(this);
        MobclickAgent.setUpdateOnlyWifi(false);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.onError(this);

        
  		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
  	
  	
        sp = getSharedPreferences("user", 0);
        if (!"".equals(sp.getString("email", null))&&!"".equals(user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN))&&user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)!=null) {
        	m_bIsFirstLogin=false;
		}
        mProgressHandler = new Handler() {   
            @Override  
            public void handleMessage(Message msg) { 
                switch (msg.what){  
                case 1:
                	AlertDialog.Builder b =new AlertDialog.Builder(context);
                	b.setTitle("抱歉");
                	b.setMessage("无法连接网络");
                	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                           	OnCloseAllActivity();
                        }
                    });
                    b.show();
                	break;
                case 2: 
                	 
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
		Log.d("Rock", screenWidth+":screenWidth");
		if (screenWidth>440) {
			checkNetworkStatus();
		}else{
			AlertDialog.Builder b =new AlertDialog.Builder(context);
        	b.setTitle("提醒");
        	b.setMessage("480*800以上分辨率是最好的体验");
        	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                	checkNetworkStatus();
                }
            });
            b.show();
		}
        
	    startnetLoactionService();
		startgpsLoactionService();
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
        	locationManager.requestLocationUpdates(currentProvider_net,  500, 500, locationListener);
		} catch (Exception e) {
			// TODO: handle exception
		//  Toast.makeText(IndexActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
		}
       //根据当前provider对象获取最后一次位置信息
   
       //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
       updateToNewLocation(currentLocation);
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
	//	  Toast.makeText(IndexActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
		}
      
      //根据当前provider对象获取最后一次位置信息
     
      //如果位置信息为null，则请求更新位置信息
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
		updateToGPSLocation(currentLocation);
//      if(currentLocation_gps == null){
         
//      }else{
   	   handlerTime_gps.removeCallbacks(runnableTime_gps);
   	   handlerTime_gps.postDelayed(runnableTime_gps, 5000);   
//      }
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

//      TextView tv1;
//       tv1 = (TextView) this.findViewById(R.id.tv1);
       if (location != null) {
             latitude_net = location.getLatitude();
            longitude_net= location.getLongitude();
             accuracy_net = location.getAccuracy();
         // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
       //   Toast.makeText(SplashActivity.this, "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy, Toast.LENGTH_SHORT).show();
           
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
//           tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
       } else {
//           tv1.setText("无法获取地理信息");
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
       //   Toast.makeText(SplashActivity.this, "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy, Toast.LENGTH_SHORT).show();
           
             if (accuracy_gps<accuracy_net) {
            	 locationinfo.setLatitude(latitude_gps-0.0018);
                 locationinfo.setLongitude(longitude_gps+0.0044);
                 locationinfo.setAccuracy(accuracy_gps);
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
	
    protected void onDestroy(){
    	super.onDestroy();
    	mProgressHandler = null;
    }
    @Override
    
    
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	AlertDialog.Builder b=new AlertDialog.Builder(SplashActivity.this);
            b.setTitle(R.string.quit_title);
            b.setMessage(R.string.quit_desc);
            b.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          }
            });
            b.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	OnCloseAllActivity();
                }
            });
            b.show();
            return false;
            }
        return super.onKeyDown(keyCode, event);
    }*/
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return super.onKeyDown(keyCode, event);

		}

	}
    public void OnCloseAllActivity(){
    	finish();
    }
    
    public void checkNetworkStatus(){
    	
    	if(isNetworkAvailable()){
        /*	if(!checkWifi()){
        		isWifi = false;
        		int mode =checkCurNetwork(SplashActivity.this);
        		if(mode !=0){
	        		String str="";
	        		switch(mode){
	        		case 1:
	        			str ="本程序仅支持NET接入,在您的APN列表中发现适合的项，是否切换到NET网络？";
	        			break;
	        		case 2:
	        			str ="本程序需要接入NET网络，您当前网络不支持，是否需要创建一个NET网络？";
	        			break;
	        		default :
	        			break;  
	        		}
	        		return;
        		}
        		
        	}else{
        		isWifi = true;
        	}*/
        	
        	
        	new Thread(sendDefaultsThread).start();//第一次登陆
        	
        	
        	if(m_bIsFirstLogin==true){
        		new Thread(LoginThread_f).start();//第一次登陆
        	}
        	else{
        		new Thread(MainThread).start();//不是第一次登陆
        	}
        }else {
        	startupTips = "未检测到可用网络...";
    		Message msg = Message.obtain(mProgressHandler,1);
			msg.sendToTarget();
        }
    }

   
    /*public void ContinueLoadWcc(){
    	new Thread(MainThread).start();
   	   
    }*/
	public Runnable sendDefaultsThread =new Runnable(){
    	public void run()
    	{
    		
    		if ("1".equals(sp.getString("ischeck", null))||"0".equals(sp.getString("ischeck", null))) {
				
			}else{
				sp.edit().putString("ischeck","1").commit();
			}
    		
    		if ("1".equals(sp.getString("istransparentall", null))||"0".equals(sp.getString("istransparentall", null))) {
				
			}else{
				sp.edit().putString("istransparentall","1").commit();
			}
    		
    		
    		
    		String urlcons = SppaConstant.WALKIN_URL_DEFAULTS;
			 defaults_mservice.setRetrieveUrl(urlcons);
			 defaults_mservice.retrieveDefaultsInfo(urlcons);
    	}
    };

	public Runnable MainThread =new Runnable(){
    	public void run()
    	{
			try{
		 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
            +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
            +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
            Log.d("Rock", urlcons);
            user_mservice.setRetrieveUrl(urlcons);
            user_mservice.retrieveUserQueryInfo();
   		 String user_register_meta_code = user_mservice.getCode();
   		 Log.d("Rock", user_register_meta_code+"RRRRRRssssss"+":message_error");
   		 
   		 if (user_register_meta_code==null) {
   			Thread.sleep(2000);
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, LoginRegistrationActivity.class);
			startActivityForResult(intent, 0);
			finish();
			return;
		}else{
			
		}
   		 
   		 
   		 
            if("0".equals((String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_ageGroup))) {
    			Intent intent=new Intent();
 				intent.setClass(SplashActivity.this, GenderAgeActivity.class);
 				Bundle bundle=new  Bundle();
 				String str1="aaaaaa";
 				bundle.putString("Data", str1);
 				intent.putExtras(bundle);
 				startActivityForResult(intent, 0);
 				finish();
 				return;
    		}else if("".equals((String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_style))) {
    	 		 Intent intent=new Intent();
        			intent.setClass(SplashActivity.this, UserStyleActivity.class);
        			Bundle bundle=new  Bundle();
        			String str1="aaaaaa";
        			bundle.putString("Data", str1);
        			intent.putExtras(bundle);
        			startActivityForResult(intent, 0);
        			finish();
        			return;
    		}else if (user_mservice.isSuccessed()==true) {
            	Thread.sleep(2000);
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, IndexActivity.class);
				startActivityForResult(intent, 0);
				finish();
    		} else{
				//Message message = Message.obtain(SplashActivity.mProgressHandler,1);
				//message.sendToTarget();
    			Thread.sleep(2000);
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, LoginRegistrationActivity.class);
				startActivityForResult(intent, 0);
				finish();
			}
				
			}catch (InterruptedException e){
				Thread.currentThread().interrupt();
			}
			
    	}
    };
	public Runnable LoginThread_f =new Runnable(){
    	public void run()
    	{
			try{
				Thread.sleep(2000);
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, LoginRegistrationActivity.class);
				startActivityForResult(intent, 0);
				finish();
			}catch (InterruptedException e){
				Thread.currentThread().interrupt();
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
