package com.walkin.walkin;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.bean.Vidicon;

public class BaseMapActivity extends MapActivity {
	private Button Button_top_logo,Button_top_arrow;
	private MapController mMapController;
	protected MapView mapView;
	private View popView;
	private View dingView;
	private static String TAG ="Rock";
	public  List<GeoPoint> qpoin= new ArrayList<GeoPoint>();
	private List<String> arraylist_data = new ArrayList<String>(); 
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
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
//    private MyTaskMeLBS mTaskLBS;  
//	GeoPoint mGeoPoint04 = new GeoPoint((int) (31.200544 * 1E6),(int) (121.620080 * 1E6));
    private static Context context;
	private List<Vidicon> vidiconlist;
	private Vidicon vidiconOne;
	private Button Button_ding ;
	private Button goback ;
	private SharedPreferences sp;
	private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {
		public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) {
			// 创建气泡窗口
			if (popView != null) {
				popView.setVisibility(View.GONE);
			}
			if (newFocus != null) {
				MapView.LayoutParams geoLP = (MapView.LayoutParams) popView.getLayoutParams();
				geoLP.height=150;
				final int pao_lat = newFocus.getPoint().getLatitudeE6();
				final int pao_long = newFocus.getPoint().getLongitudeE6();
				final String neirong =newFocus.getTitle();

				
				
				GeoPoint Point01 = new GeoPoint((pao_lat), (pao_long));
				geoLP.point =Point01;// 这行用于popView的定位
				
				TextView title = (TextView) popView.findViewById(R.id.map_bubbleTitle);
				title.setText(newFocus.getTitle());
				TextView desc = (TextView) popView.findViewById(R.id.map_bubbleText);
//				Drawable drawable02 = getResources().getDrawable(R.drawable.mapinfo_redarrow);
//				ImageView img = (ImageView) popView.findViewById(R.id.map_bubbleImage);
//				img.setImageDrawable(drawable02);
				desc.setVisibility(View.VISIBLE);
				if (newFocus.getSnippet() == null|| newFocus.getSnippet().length() == 0) {
					desc.setVisibility(View.GONE);
				} else {
					desc.setVisibility(View.VISIBLE);
					desc.setText(newFocus.getSnippet());
				}
				mapView.updateViewLayout(popView, geoLP);
				popView.setVisibility(View.VISIBLE);
			/*	popView.setOnClickListener(new OnClickListener() {
				    public void onClick(View v) {
				    	Log.d("Rock", qpoin.size()+"4455787");
				    	String dangqian =qpoin.get(qpoin.size()-1).getLatitudeE6() / 1E6+","+qpoin.get(qpoin.size()-1).getLongitudeE6() / 1E6;
    				  	String zhongdian =pao_lat / 1E6+","+pao_long / 1E6;
				        Intent intents = new Intent();
				        intents.putExtra("titles", neirong);
				        intents.putExtra("dangqian", dangqian);
				        intents.putExtra("zhongdian", zhongdian);
				        intents.setClass(BaseMapActivity.this, StoresActivity.class);
				     //   Log.d(TAG, "=1111111111==========");
				    	startActivity(intents);
				    //	return;
//				    	 final CharSequence[] items = { "乘车", "步行", "驾车" };
//				    	 AlertDialog.Builder builder = new AlertDialog.Builder(
//			                		BaseMapActivity.this);
//			                builder.setTitle("选择交通方式");
//			                builder.setItems(items, new DialogInterface.OnClickListener() {
//			                    public void onClick(DialogInterface dialog, int item) {
//			                    	String dangqian =qpoin.get(0).getLatitudeE6() / 1E6+","+qpoin.get(0).getLongitudeE6() / 1E6;
//			    				  	String zhongdian =bb / 1E6+","+cc / 1E6;
//			                        StringBuilder params = new StringBuilder().append("&dirflg=");
//			                        switch (item) {
//			                        case 0:
//			                            params.append("r");
//			                            break;
//			                        case 1:
//			                            params.append("w");
//			                            break;
//			                        case 2:
//			                            params.append("d");
//			                            break;
//			                        default:
//			                            break;
//			                        }
//			                        getMap(params.toString(),dangqian,zhongdian);
//			                    }
//			                });
//			 
//			                AlertDialog alert = builder.create();
//			                alert.show();
				    }
				});*/
				
			}
//			return;
		}
//		 protected void getMap(String params, String dangqian, String zhongdian) {
//		        Intent i = new Intent(
//		                Intent.ACTION_VIEW,
//		                Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="+dangqian+"&daddr="+zhongdian+"&hl=zh&t=m&"+ params));
//		        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//		                & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//		        i.setClassName("com.google.android.apps.maps",
//		                "com.google.android.maps.MapsActivity");
//		        startActivity(i);
//		    }
//
	};
/*	  private class MyTaskMeLBS extends AsyncTask<String, Integer, String> {  

	         protected String doInBackground(String... params) {  
	            
	        	
	             return null;  
	         }  
	         
	         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	         @Override  
	         protected void onPostExecute(String result) {  
	        	 
	         }  
	           
	     }*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basemap_act);
		
		context = this;
		locationinfo.setActivity(this);
		 sp = getSharedPreferences("user", 0);
		Button_ding=(Button) findViewById(R.id.Button_ding);
		Button_ding.setOnClickListener(new Button_dingListener());
		mapView = (MapView) findViewById(R.id.MapView01);
		Bundle bundle = getIntent().getExtras();    
		String str_distance=bundle.getString("str_distance");
		int distance = Integer.parseInt(str_distance);//将字符串转化成整形
		Log.d("Rock", distance+":distance");
		int zoom_ll = 18;
		if (distance<50) {
			zoom_ll=18;
		}else if (distance>50&&distance<1000){
			zoom_ll=17;
		}else if (distance>1000&&distance<5000){
			zoom_ll=14;
		}else if (distance>5000&&distance<10000){
			zoom_ll=12;
		}else if (distance>10000&&distance<100000){
			zoom_ll=8;
		}else {
			zoom_ll=6;
		}
		/*mapView.setSatellite(false);
		mapView.setStreetView(true);*/
		mMapController = mapView.getController();
		mapView.setEnabled(true);
		mapView.setClickable(true);
		
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
		// 设置地图支持缩放
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		
		
		
		mMapController.setZoom(zoom_ll);
		popView = View.inflate(this, R.layout.mapoverlay_pop, null);
		mapView.addView(popView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		popView.setVisibility(View.GONE);
		dingView = View.inflate(this, R.layout.mapoverlay_pop, null);
		mapView.addView(dingView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		dingView.setVisibility(View.GONE);
		
		
		GeoPoint geopoing_dang = new GeoPoint((int) ((locationinfo.getLatitude()) * 1E6),(int) ((locationinfo.getLongitude()) * 1E6));
		qpoin.add(geopoing_dang);
		Log.d(TAG, geopoing_dang+"bbbbbbbbbbbbbbbb");
		mMapController.animateTo(geopoing_dang);
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
	    List<Overlay> list = mapView.getOverlays();
	    list.add(myLocationOverlay);
	    mapView.invalidate();
		
		
	    Drawable drawable01 = getResources().getDrawable(R.drawable.mapinfo_needle);
		RestaurantOverlay overlay = new RestaurantOverlay(drawable01);
		// 设置显示/隐藏泡泡的监听器
//			if(msg.obj!=null){
		

		arraylist_data=bundle.getStringArrayList("Data");
				// Log.d(TAG, al.size()+"paopao");
//				for (int i = 0; i < al.size(); i++) {
//					Coordinate cdother=(Coordinate) al.get(i);
//					 Log.d(TAG,  cdother.getX()+"dingdingding");
					GeoPoint mGeoPoint1 = new GeoPoint((int) ( Float.parseFloat(arraylist_data.get(0)) * 1E6),(int) ( Float.parseFloat(arraylist_data.get(1)) * 1E6));
					overlay.addOverlay(new OverlayItem(mGeoPoint1,  arraylist_data.get(2),  arraylist_data.get(3)));
					overlay.setOnFocusChangeListener(onFocusChangeListener);
					mapView.getOverlays().add(overlay);
//				}
//				}
					Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
					  Button_top_arrow=(Button) findViewById(R.id.Button_top_arrow);
					    Button_top_arrow.setOnClickListener(new Button_top_logoListener());
					    Button_top_logo.setOnClickListener(new Button_top_logoListener());
		
//		qpoin.add(mGeoPoint04);
//		Log.d("Rock", qpoin.add(mGeoPoint04)()+"///////////////");
		
		
//		GeoPoint geopoing_dang=(GeoPoint)msg.obj;
					 meGPSProgressHandler = new Handler() {
							public void handleMessage(Message msg) {
								switch (msg.what) {
								case 1:
									GeoPoint geopoing_dang = new GeoPoint((int) (locationinfo.getLatitude() * 1E6),(int) (locationinfo.getLongitude() * 1E6));
									qpoin.add(geopoing_dang);
									Log.d(TAG, geopoing_dang+"bbbbbbbbbbbbbbbb");
									mMapController.animateTo(geopoing_dang);
									MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
								    List<Overlay> list = mapView.getOverlays();
								    list.add(myLocationOverlay);
								    mapView.invalidate();
									break;
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
//	   if ("1".equals(sp.getString("istransparentall", null))) {
//        	 Intent intent = new Intent(BaseMapActivity.this, TransparentAllActivity.class);
//	         Bundle mBundle = new Bundle();
//			 mBundle.putString("Activity", "BaseMapActivity");// 压入数据
//			 intent.putExtras(mBundle);
//			 startActivityForResult(intent, 0);
//		//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
//			}
		
	}
	  class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}
	class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}  
	class Button_dingListener implements OnClickListener{
		public void onClick(View v) {
			startnetLoactionService();
			startgpsLoactionService();
		}
	}
	class MyLocationOverlay extends Overlay
	{
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			String[] stront = new String[]{"当前位置"};
			String[] url = new String[]{""};
			vidiconlist = new ArrayList<Vidicon>();
				Paint paint = new Paint();
				
				Point myScreenCoords = new Point();
				try {
//					Log.d(TAG, qpoin.get(qpoin.size()-1).getLatitudeE6()+"bbbbbbbbbbbbbbbb");
					mapView.getProjection().toPixels(qpoin.get(qpoin.size()-1), myScreenCoords);
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ding);
					canvas.drawBitmap(bmp, myScreenCoords.x-15, myScreenCoords.y+2, paint);
					vidiconOne = new Vidicon(qpoin.get(qpoin.size()-1),myScreenCoords.x, myScreenCoords.y,stront[0],url[0]);
					vidiconlist.add(vidiconOne);
				} catch (Exception e) {
				}
			return true;
		}
		public boolean onTap(GeoPoint p, MapView mapView) {
			super.onTap(p, mapView);
			Point screen= new Point();
			mapView.getProjection().toPixels(p, screen);
			try {
			if(!vidiconlist.isEmpty())
		       {
		            Vidicon vd=vidiconlist.get(0);
		            if((vd.getX()-screen.x<=30 && vd.getX()-screen.x>=-30) && (vd.getY()-screen.y<=30 && vd.getY()-screen.y>=-30))
		            {
		            	MapView.LayoutParams geoLP = (MapView.LayoutParams) dingView.getLayoutParams();
						GeoPoint Point01 = new GeoPoint((vd.getLongitude().getLatitudeE6()), (vd.getLongitude().getLongitudeE6()));
						geoLP.point =Point01;// 这行用于dingView的定位
						TextView title = (TextView) dingView.findViewById(R.id.map_bubbleTitle);
						title.setText(vd.getVName());
						title.setVisibility(View.VISIBLE);
						mapView.updateViewLayout(dingView, geoLP);
						dingView.setVisibility(View.GONE);
						
		            }else{
		            	dingView.setVisibility(View.GONE);
		            }
		           }
			} catch (Exception e) {
				// TODO: handle exception
			}
			return true;
		}
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
	class RestaurantOverlay extends ItemizedOverlay<OverlayItem> {
		private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		private Drawable marker = null;
		public RestaurantOverlay(Drawable defaultMarker) {
			super(defaultMarker);
			this.marker = defaultMarker;
		}
		public void addOverlay(OverlayItem overlay) {
			items.add(overlay);
			populate();
		}
		protected boolean isRouteDisplayed() {
			return false;
		}
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}
		public int size() {
			return items.size();
		}
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			boundCenterBottom(marker);
			super.draw(canvas, mapView, shadow);
		}

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
//		       if(currentLocation_gps == null){
		           locationManager.requestLocationUpdates(currentProvider_gps,  500, 500, locationListener);
//		       }else{
		    	   handlerTime_gps.removeCallbacks(runnableTime_gps);
		    	   handlerTime_gps.postDelayed(runnableTime_gps, 5000);   
//		       }
		       //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
		       //每隔10秒获取一次位置信息
		     
		  
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
			private void openGPSSettings() {
		  		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		  		if (!alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
		  				&&!alm.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
		  			AlertDialog.Builder b = new AlertDialog.Builder(BaseMapActivity.this);
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
		 			Toast.makeText(BaseMapActivity.this, "无线网络模块正常", Toast.LENGTH_SHORT).show();
		 			}
		 		if(alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
		 		{
		 			Toast.makeText(BaseMapActivity.this, "GPS模块正常", Toast.LENGTH_SHORT).show();
		 		}
		  		
		  	}
			private void updateToGPSLocation(Location location) {

//			       TextView tv1;
//			        tv1 = (TextView) this.findViewById(R.id.tv1);
			        if (location != null) {
			              latitude_gps = location.getLatitude();
			             longitude_gps= location.getLongitude();
			              accuracy_gps = location.getAccuracy();
			          // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
			            Toast.makeText(BaseMapActivity.this, "维度：" +  latitude_gps+ "\n经度" + longitude_gps+ "\n水平精度" + accuracy_gps, Toast.LENGTH_SHORT).show();
			            if (accuracy_gps<accuracy_net) {
			            	locationinfo.setLatitude(latitude_gps-0.0018);
				            locationinfo.setLongitude(longitude_gps+0.0044);
				            locationinfo.setAccuracy(accuracy_gps);
						}
			        	Message msg= Message.obtain(meGPSProgressHandler,1);
		           	 	msg.sendToTarget();
			            
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
//			            tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
			        } else {
//			            tv1.setText("无法获取地理信息");
			        }

			    }
			private void updateToNewLocation(Location location) {

//		       TextView tv1;
//		        tv1 = (TextView) this.findViewById(R.id.tv1);
		        if (location != null) {
		              latitude_net = location.getLatitude();
		             longitude_net= location.getLongitude();
		              accuracy_net = location.getAccuracy();
		          // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
		            Toast.makeText(BaseMapActivity.this, "维度：" +  latitude_net+ "\n经度" + longitude_net+ "\n水平精度" + accuracy_net, Toast.LENGTH_SHORT).show();
		            if (latitude_net<latitude_gps) {
	            	locationinfo.setLatitude(latitude_net-0.0018);
		            locationinfo.setLongitude(longitude_net+0.0044);
		            locationinfo.setAccuracy(accuracy_net);
					}
		      
		        	Message msg= Message.obtain(meGPSProgressHandler,1);
	           	 	msg.sendToTarget();
		            
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
//		            tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
		        } else {
//		            tv1.setText("无法获取地理信息");
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
			
			
	/*public static  Handler getDingweiGPSProgressHandler() {
		Log.v("Rock", "getDingweiGPSProgressHandler");
		return DingweiGPSProgressHandler;
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

