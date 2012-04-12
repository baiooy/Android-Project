package com.walkin.walkin;
/**
 * 
 */

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
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.NetworkToPic;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author allin
 * 
 */
public class BrandsActivity extends ListActivity {
	
	private MyButton myhome;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
//	private ImageView nbuy_pic;
	private Button Button_Refresh;
	private Button Button_ME ;
	private Button Button_top_logo,Button_top_arrow;
//	private PopupWindow mPopupWindow;
	private List<Map<String, Object>> mData;

	private Map<String, Object> map ;
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
 	
 	
 	
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
	private static Context context;
    private MyTask mTask;  
    private MyTask02 mTask02;  
    private MyTaskMeInbiBalance mTaskInbi;  
    private static Handler mProgressHandler=null;
    private static Handler exitHandler=null;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	protected final static NetworkBitmapInfo networkbitmapinfo = NetworkBitmapInfo.getInstance();
	protected final static BrandsInfo brandsinfo = BrandsInfo.getInstance();
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
	private SharedPreferences sp;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brands_act);
		context = this;
		defaults_mservice.setActivity(this);
		brands_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
		networkbitmapinfo.setActivity(this);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		 sp = getSharedPreferences("user", 0);
		Bundle bundle = getIntent().getExtras();    
		int pop_int=bundle.getInt("can_int");
//		linearParams = (LinearLayout.LayoutParams) picview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams = (LinearLayout.LayoutParams) amountview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams_item = (LinearLayout.LayoutParams) itemview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams = (LinearLayout.LayoutParams) buyview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		wother=linearParams.width = (screenWidth * 1) / 5;// 当控件的高强制设成75象素
//		wname=linearParams_item.width = (screenWidth * 2) / 5;// 当控件的高强制设成75象素
//		picview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//		itemview.setLayoutParams(linearParams_item); // 使设置好的布局参数应用到控件mGrid2
//		amountview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//		buyview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		
//		openGPSSettings();
		/*Intent intent = getIntent();
		Bundle b =intent.getExtras();
		Log.d("Rock", b+"传递过来的值");*/
		TextView_Loading=(TextView) findViewById(R.id.TextView_Loading);
		progressBar_Loading=(ProgressBar) findViewById(R.id.progressBar_Loading);
     	Button_ME=(Button) findViewById(R.id.Button_ME);
        myhome = new MyButton(this);
	    Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new MEButtonListener());
		Button_ME.setText(user_mservice.getInbiBalance());
	    Button_Refresh=(Button) findViewById(R.id.Button_Refresh);
	    Button_Refresh.setOnClickListener(new Button_RefreshListener());
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_arrow=(Button) findViewById(R.id.Button_top_arrow);
	    Button_top_arrow.setOnClickListener(new Button_top_logoListener());
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
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
			 mProgressHandler = new Handler() {
					public void handleMessage(Message msg) {
						switch (msg.what) {
						case 1:
							 mTask = new MyTask();  
					         mTask.execute(); 
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
		 mTask = new MyTask();  
         mTask.execute(); 
         
         if ("1".equals(sp.getString("istransparentall", null))&&pop_int==0) {
        	 Intent intent = new Intent(BrandsActivity.this, TransparentAllActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "BrandsActivity");// 压入数据
			 intent.putExtras(mBundle);
			 startActivityForResult(intent, 0);
		//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
			}
	  //  new Thread(mthread).start();
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
	    
/*	  private final Runnable mthread = new Runnable() {
		         public void run() {
					 Message msg= Message.obtain(ListBrandsHandler,0);
	            	 msg.sendToTarget();
		         }
		     };*/
		     
		     private class MyTask extends AsyncTask<String, Integer, String> {  
		         private static final String TAG = "Rock";

				//onPreExecute方法用于在执行后台任务前做一些UI操作  
		         @Override  
		         protected void onPreExecute() {  
		        	 TextView_Loading.setVisibility(View.VISIBLE); 
		        	 progressBar_Loading.setVisibility(View.VISIBLE); 
		             Log.i(TAG, "onPreExecute() called");  
		         }  
		           
		         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
		         @Override  
		         protected String doInBackground(String... params) {  
		             Log.i(TAG, "doInBackground(Params... params) called"); 
		            
		             String urlcons = SppaConstant.WALKIN_URL_BRANDS+"?"+"ll="+(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044)+"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&acc="+locationinfo.getAccuracy()+"&apikey=BYauu6D9";
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
		        	
	     			 //brands_mservice.getList_jobj_item().size()-5
	     			 Bitmap[] bitmap = new Bitmap[brands_mservice.getList_jobj_item().size()]; 
	     			 for (int i = 0; i <brands_mservice.getList_jobj_item().size(); i++) {
	     				 try {
	     					 //List<Map<String, Object>>  list = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_stores);
	     					 //String str_distance = (String) list.get(0).get(BrandsMenuService.JSONObj_item_stores_distance);
	     					 //int  int_distance=new Integer(Integer.parseInt(str_distance)).intValue();
	     					// Log.e("Rock", int_distance+":int_distance");
//	     					 if (int_distance<1000) {
	     						 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_marker);
	     						 String marker_imageURL = (String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
	     						 Log.d("Rock", marker_imageURL+":marker_imageURL");
	     						 if ("".equals(marker_imageURL)) {
	     							 marker_imageURL = "http://static.tieba.baidu.com/tb/indexfiles/v2/images/header_line.png";
	     						}else{
	     							marker_imageURL = SppaConstant.WALKIN_URL_BASE+(String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
	     						}
//	     					 			urlPath[i]= new String(marker_imageURL);//为第一个数组元素开辟空间 
	     						Drawable cachedImage = asyncImageLoader.loadImageFromUrl(BrandsActivity.this, marker_imageURL);
	     			 			Log.d("Rock", cachedImage+":cachedImage");
	     			 			bitmap[i]=NetworkToPic.drawableToBitmap(cachedImage);
//	     					}
	     					
	     				} catch (Exception e) {
	     				}	 
	     				}
	     			
//	     			Bitmap[] bitmapa = networkbitmapinfo.getBitmap();
	     			
//					if (bitmapa==null) {
//						Bitmap[] bitmap = NetworkToPic.getBitmapArray(urlPath);
	     			 	networkbitmapinfo.setBitmap(bitmap);
	     			 
	     			 	 mData = getData(brands_mservice.getList_jobj_item());
		         		 MyAdapter adapter = new MyAdapter(BrandsActivity.this);
		     			 setListAdapter(adapter);
//					}
	     			 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		         }  
		         
		         //onCancelled方法用于在取消执行中的任务时更改UI  
		         @Override  
		         protected void onCancelled() {  
		        	 Log.i(TAG, "onCancelled() called");
//		             LinearLayout_inbi_congriation.setVisibility(View.GONE);
		         }  
		     }
		     
		     
		     private class MyTask02 extends AsyncTask<String, Integer, String> {  
		         private static final String TAG = "Rock";

				//onPreExecute方法用于在执行后台任务前做一些UI操作  
		         @Override  
		         protected void onPreExecute() {  
		        	 TextView_Loading.setVisibility(View.VISIBLE); 
		        	 progressBar_Loading.setVisibility(View.VISIBLE); 
		             Log.i(TAG, "onPreExecute() called");  
		         }  
		           
		         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
		         @Override  
		         protected String doInBackground(String... params) {  
		             Log.i(TAG, "doInBackground(Params... params) called"); 
		            
		             String urlcons = SppaConstant.WALKIN_URL_BRANDS+"?"+"ll="+(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044)+"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&acc="+locationinfo.getAccuracy()+"&apikey=BYauu6D9";
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
		        	
		        
		        	 
	     			 //brands_mservice.getList_jobj_item().size()-5
		        	 Log.d("Rock", brands_mservice.getList_jobj_item().size()+":brands_mservice.getList_jobj_item().size()");
	     			 Bitmap[] bitmap = new Bitmap[brands_mservice.getList_jobj_item().size()]; 
	     			// String[] urlPath=new   String[brands_mservice.getList_jobj_item().size()]; 
	     			 for (int i = 0; i <brands_mservice.getList_jobj_item().size(); i++) {
	     				 try {
	     					// List<Map<String, Object>>  list = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_stores);
	     					 //String str_distance = (String) list.get(0).get(BrandsMenuService.JSONObj_item_stores_distance);
	     					// int  int_distance=new Integer(Integer.parseInt(str_distance)).intValue();
	     					// Log.e("Rock", int_distance+":int_distance");
//	     					 if (int_distance<1000) {
	     						 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.list_item_marker);
	     						 String marker_imageURL = (String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
	     						 if ("".equals(marker_imageURL)) {
	     							 marker_imageURL = "http://static.tieba.baidu.com/tb/indexfiles/v2/images/header_line.png";
	     						}else{
	     							marker_imageURL = SppaConstant.WALKIN_URL_BASE+(String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_imageURL);
	     						}
	     					 		//	urlPath[i]= new String(marker_imageURL);//为第一个数组元素开辟空间 
	     						Log.d("Rock", marker_imageURL+":cachedImage");
	     						Drawable cachedImage = asyncImageLoader.loadImageFromUrl(BrandsActivity.this, marker_imageURL);
	     			 			
	     			 			bitmap[i]=NetworkToPic.drawableToBitmap(cachedImage);
//	     					}
	     					
	     				} catch (Exception e) {
	     					 Log.d("Rock", e+":e");
	     				}	 
	     				}
	     			
	     			//Bitmap[] bitmapa = networkbitmapinfo.getBitmap();
	     			
					//if (bitmapa==null) {
					//	Bitmap[] bitmap = NetworkToPic.getBitmapArray(urlPath);
		     			networkbitmapinfo.setBitmap(bitmap);
		     			
		     			 mData = getData(brands_mservice.getList_jobj_item());
		     			 MyAdapter adapter = new MyAdapter(BrandsActivity.this);
		     			 setListAdapter(adapter);
					//}
	     			 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		         }  
		         
		         //onCancelled方法用于在取消执行中的任务时更改UI  
		         @Override  
		         protected void onCancelled() {  
		        	 Log.i(TAG, "onCancelled() called");
//		             LinearLayout_inbi_congriation.setVisibility(View.GONE);
		         }  
		     }
		     
		     
		     
		     
 	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String bg_img_url=SppaConstant.WALKIN_URL_BASE+"style/android/nearbyPlaces.jpg";
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		super.onStart();
	}
 
 	protected void onRestart() {
 		 mTaskInbi = new MyTaskMeInbiBalance();  
 		mTaskInbi.execute(); 
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		super.onRestart();
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
	     
     public void onResume() {
 	    super.onResume();
 	    MobclickAgent.onResume(this);
 	}
 	public void onPause() {
 	    super.onPause();
 	    MobclickAgent.onPause(this);
 	}
 	protected void onStop() {
 		Log.d("lifecycle", "onStop()"); 
 		// TODO Auto-generated method stub
 		super.onStop();
 	}
	class Button_top_logoListener implements OnClickListener{
		 		public void onClick(View v) {
		 			Intent intent = getIntent();
	    			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	    			finish();//此处一定要调用finish()方
		 		}
		 	}   
		     
	class MEButtonListener implements OnClickListener{
		public void onClick(View v) {
         Intent intent = new Intent(BrandsActivity.this, MeActivity.class);
   		 Bundle mBundle = new Bundle();  
   		 intent.putExtras(mBundle);  
   		 startActivityForResult(intent, 0);
            
	
		}
	}
	class Button_RefreshListener implements OnClickListener{
		public void onClick(View v) {
			mTask02 = new MyTask02();  
	        mTask02.execute(); 
	        startnetLoactionService();
			startgpsLoactionService();
			checkNetworkStatus();
		}
	}
	
/*	class Button03Listener implements OnClickListener{
		public void onClick(View v) {
			Log.d("Rock", "button03");
			try{
	            InputStream is = getAssets().open("friend_ss");  
	            int size = is.available();  
	            byte[] buffer = new byte[size];  
	            is.read(buffer);  
	            is.close();  
	            friend_json = new String(buffer, "utf8");   
	            retrieveFriendInfo(friend_json);
	           // Log.d("Rock", user_json+"=111111111111111");
	    }
	    catch (Exception e){
	           // Toast.makeText(this, "Exception: File not found!", 2000).show();
	    }
			
		}
	}*/
/*	class Button01Listener implements OnClickListener{
		public void onClick(View v) {
			Log.d("Rock", "button01");
			try{
	            InputStream is = getAssets().open("friend");  
	            int size = is.available();  
	            byte[] buffer = new byte[size];  
	            is.read(buffer);  
	            is.close();  
	            friend_json = new String(buffer, "utf8");   
	            retrieveFriendInfo(friend_json);
	           // Log.d("Rock", user_json+"=111111111111111");
	    }
	    catch (Exception e){
	           // Toast.makeText(this, "Exception: File not found!", 2000).show();
	    }
			
		}
	}*/
    
	
	
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
 	//public static int isremovenum=0;
	//int num_s=0;
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
			
			for (int i = 0; i < brands_list.size(); i++) {
				//Log.e("Rock", (String)brands_list.get(i).get(BrandsMenuService.JSONObj_item_display)+":JSONObj_item_display");
				if ("false".equals((String)brands_list.get(i).get(BrandsMenuService.JSONObj_item_display))) {
					brands_list.remove(i);
				//	num_s=isremovenum++;
					}
					//list.add(map);
				
			}
			
		return brands_list;
	}
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		 
		 Intent intent = new Intent(BrandsActivity.this, BrandsDetailActivity.class);
		 Bundle mBundle = new Bundle();  
//		Log.d("Rock", position+"position");
		 
		 mBundle.putInt("Data", position);//压入数据  
		 intent.putExtras(mBundle);  
		 startActivityForResult(intent, 0);
	}
	
	
	
	
	public final class ViewHolder{
		public ImageView ImageView_loge_pic;
		public TextView TextView_amount_state;
		public ImageView ImageView_inbi_img;
		public TextView TextView_brands_distance;
		
	}
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			return mData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}
		String str_distance,distance,ImageView_loge_pic,ImageView01_getThumbnail_pic,lastCheckinHere_time,isCheckinToday,TextView_amount_state;
		
		public View getView(int position, View convertView, ViewGroup parent) {
		
			try {
				String ImageView_loge_pic  = Common.stringChangeString((String) mData.get(position).get(BrandsMenuService.JSONObj_item_enName));
				 ImageView01_getThumbnail_pic = (SppaConstant.WALKIN_URL_BASE+"brands/"+ImageView_loge_pic+"/android/"+ImageView_loge_pic+defaults_mservice.getBrandWhiteLogo()+".png").toLowerCase();
				
				 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(position).get(BrandsMenuService.list_item_marker);
				   TextView_amount_state =(String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
				
				//final String TextView_amount_state = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
				 lastCheckinHere_time = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_lastCheckinHere);
				isCheckinToday = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_isCheckinToday);
				 List<Map<String, Object>>  list = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(position).get(BrandsMenuService.list_item_stores);
				 str_distance =(String) list.get(0).get(BrandsMenuService.JSONObj_item_stores_distance);
				if (str_distance.length()>3) {
					java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
					distance=df.format(Double.parseDouble(str_distance)/1000)+"公里";
				}else{
					distance=str_distance+"米";
				}
				 Log.d("Rock", ImageView01_getThumbnail_pic+"//进行时间比对 -未完成");//进行时间比对 -未完成
			} catch (Exception e) {
				
				distance="";
				// TODO: handle exception
			}
			
			 
			try {
				
			
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.brands_detail, null);
				loadImage4(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView_loge_pic));
				holder.TextView_amount_state = (TextView)convertView.findViewById(R.id.TextView_amount_state);
				holder.ImageView_inbi_img = (ImageView)convertView.findViewById(R.id.ImageView_inbi_img);
				holder.TextView_brands_distance = (TextView)convertView.findViewById(R.id.TextView_brands_distance);
				
				if("true".equals(isCheckinToday)){
					Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.common_yiqiandao);
					holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
					holder.TextView_amount_state.setVisibility(View.GONE);
					holder.TextView_brands_distance.setVisibility(View.GONE);
				}else{
					Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.inbiwhite);
					holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
					holder.TextView_amount_state.setVisibility(View.VISIBLE);
					holder.TextView_brands_distance.setVisibility(View.VISIBLE);
					holder.TextView_amount_state.setText(TextView_amount_state);
					holder.TextView_brands_distance.setText(distance);
				}
				
			}else {
					holder=new ViewHolder();  
					loadImage4(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView_loge_pic));
					holder.TextView_amount_state = (TextView)convertView.findViewById(R.id.TextView_amount_state);
					holder.TextView_brands_distance = (TextView)convertView.findViewById(R.id.TextView_brands_distance);
					holder.ImageView_inbi_img = (ImageView)convertView.findViewById(R.id.ImageView_inbi_img);
					if("true".equals(isCheckinToday)){
						Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.common_yiqiandao);
						holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
						holder.TextView_amount_state.setVisibility(View.GONE);
						holder.TextView_brands_distance.setVisibility(View.GONE);
					}else{
						Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.inbiwhite);
						holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
						holder.TextView_amount_state.setVisibility(View.VISIBLE);
						holder.TextView_brands_distance.setVisibility(View.VISIBLE);
						holder.TextView_amount_state.setText(TextView_amount_state);
						holder.TextView_brands_distance.setText(distance);
					}
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return convertView;
		}
	}
	private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
       Drawable cacheImage = asyncImageLoader.loadDrawable(BrandsActivity.this,url,new ImageCallback() {
           //请参见实现：如果第一次加载url时下面方法会执行
           public void imageLoaded(Drawable imageDrawable, String imageUrl) {
          	 
          	 if (imageDrawable != null ) { 
          		 bgimageView.setBackgroundDrawable(imageDrawable);
          	 }else{
          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.brands_list_bg);
          		 bgimageView.setBackgroundDrawable(drawable_bg);
          	 }
           }
       });
      if(cacheImage!=null){
      	bgimageView.setBackgroundDrawable(cacheImage);
      }else{
      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.brands_list_bg);
      	bgimageView.setBackgroundDrawable(drawable_bg);
 	 }
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
            		 imageView.setImageResource(R.drawable.none_color);
	            	 }
	             }
	         });
	        if(cacheImage!=null){
	        	imageView.setImageDrawable(cacheImage);
	        }else{
       		 imageView.setImageResource(R.drawable.none_color);
       	 }
	    }
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(BrandsActivity.this, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	            	   imageView.setImageDrawable(imageDrawable);
	               }else{
	            		 imageView.setImageResource(R.drawable.none_color);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	imageView.setImageDrawable(cachedImage);
	        }else{
       		 imageView.setImageResource(R.drawable.none_color);
       	 }
	    	}
		public static Handler getExitHandler(){ 
	        return exitHandler;
	    }
		public static Handler getmProgressHandler(){ 
	        return mProgressHandler;
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
			  Toast.makeText(BrandsActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
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
			  Toast.makeText(BrandsActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
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
			  Toast.makeText(BrandsActivity.this, "无法获取定位数据", Toast.LENGTH_SHORT).show();
			}
	       
	       //根据当前provider对象获取最后一次位置信息
	      
	       //如果位置信息为null，则请求更新位置信息
			// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
	       updateToGPSLocation(currentLocation);
//	       if(currentLocation_gps == null){
	          
//	       }else{
	    	   handlerTime_gps.removeCallbacks(runnableTime_gps);
	    	   handlerTime_gps.postDelayed(runnableTime_gps, 5000);   
//	       }
	       //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
	       //每隔10秒获取一次位置信息
	     
	  
		}
		   public Handler handlerTime_gps = new Handler();  
			public Runnable runnableTime_gps = new Runnable() {  
			    public void run() {
			    	currentLocation = locationManager.getLastKnownLocation(currentProvider_gps);
			            if(currentLocation != null){
//			                Log.d("Location", "Latitude: " + currentLocation.getLatitude());
//			                Log.d("Location", "location: " + currentLocation.getLongitude());
			       		 Message msg= Message.obtain(meGPSProgressHandler,6);
			    	     msg.sendToTarget();
			            }else{
//			                Log.d("Location", "Latitude: " + 0);
//			                Log.d("Location", "location: " + 0);
			            }
			    	
		     		handlerTime_gps.postDelayed(this, 5000);  
			    }  
			};
			 public Handler handlerTime_net = new Handler();  
				public Runnable runnableTime_net = new Runnable() {  
				    public void run() {
				    	currentLocation = locationManager.getLastKnownLocation(currentProvider_net);
				            if(currentLocation != null){
//				                Log.d("Location", "Latitude: " + currentLocation.getLatitude());
//				                Log.d("Location", "location: " + currentLocation.getLongitude());
				       		 Message msg= Message.obtain(meGPSProgressHandler,5);
				    	     msg.sendToTarget();
				            }else{
//				                Log.d("Location", "Latitude: " + 10);
//				                Log.d("Location", "location: " + 10);
				            }
				    	
			     		handlerTime_net.postDelayed(this, 5000);  
				    }  
				};
		private void updateToNewLocation(Location location) {

//	       TextView tv1;
//	        tv1 = (TextView) this.findViewById(R.id.tv1);
			  if (location != null) {
	              latitude_net = location.getLatitude();
	             longitude_net= location.getLongitude();
	              accuracy_net = location.getAccuracy();
	          // Log.d("Rock", "维度：" +  latitude+ "\n经度" + longitude+ "\n水平精度" + accuracy);  
	            Toast.makeText(BrandsActivity.this, "维度：" +  latitude_net+ "\n经度" + longitude_net+ "\n水平精度" + accuracy_net, Toast.LENGTH_SHORT).show();
	            
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
//	            tv1.setText("维度：" +  latitude+ "\n经度" + longitude);
	        } else {
//	            tv1.setText("无法获取地理信息");
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
	          Toast.makeText(BrandsActivity.this, "维度：" +  latitude_gps+ "\n经度" + longitude_gps+ "\n水平精度" + accuracy_gps, Toast.LENGTH_SHORT).show();
	           
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
		
		
		
/*	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	}*/
	
}
