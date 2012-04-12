package com.walkin.walkin;
/**
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

/**
 * @author allin
 * 
 */
public class MarkerListActivity extends ListActivity {
	
	private MyButton myhome;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
//	private ImageView nbuy_pic;
	private Button Button_Refresh;
	private Button Button_ME ;
	private Button Button_top_logo;
	private List<Map<String, Object>> mData;
//	private PopupWindow mPopupWindow;

	
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
 	
 	
	  private static Handler mProgressHandler=null;
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	private static Context context;
    private MyTask mTask;  
    private static Handler ListBrandsHandler=null;
 	
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
    public String markerID =null;
    public String TextView_inbiEarned ;
    public String TextView_enName ;
    public String TextView_dealsEarned;
    private ProgressDialog progressDialog; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.markerlist_act);
		context = this;
		defaults_mservice.setActivity(this);
		marker_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
		brands_mservice.setActivity(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		
		
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
     	/*Button_ME=(Button) findViewById(R.id.Button_ME);
        myhome = new MyButton(this);
	    Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new MEButtonListener());
	    
	    */
//	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
//	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
		Button_Refresh=(Button) findViewById(R.id.Button_Refresh);
	    Button_Refresh.setOnClickListener(new Button_RefreshListener());
	    mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
                case 4 :   
                	progressDialog.dismiss();
                	AlertDialog.Builder b2 = new AlertDialog.Builder(MarkerListActivity.this);
                	b2.setTitle("抱歉");
                	b2.setMessage("对不起没有获取到任何数据");
                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
		                public void onClick(DialogInterface dialog, int which){
		                }
		            });
                	b2.show();
        			break; 
                case 2 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "sssssssssss");
                	 
            			Intent intent = new Intent(MarkerListActivity.this, ReceiveInBiActivity.class);
            			Bundle mBundle = new Bundle();
            			mBundle.putString("enName", TextView_enName);// 压入数据
            			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
            			mBundle.putString("type", "");// 压入数据
            			mBundle.putString("markerID", markerID);// 压入数据
            			intent.putExtras(mBundle);
            			startActivity(intent);
            			finish();
        			break; 
                case 3 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                		 intent = new Intent(MarkerListActivity.this, ReceiveInBiActivity.class);
            			 mBundle = new Bundle();
            			mBundle.putString("enName", TextView_enName);// 压入数据
            			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
            			mBundle.putString("type", "400");// 压入数据
            			mBundle.putString("markerID", markerID);// 压入数据
            			
            			intent.putExtras(mBundle);
            			startActivity(intent);
            			finish();
                    break;  
                }   
            }   
        } ;
		 mTask = new MyTask();  
         mTask.execute(); 
	  //  new Thread(mthread).start();
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
		        	 
		        	/* Map<String,String>params1=new HashMap<String,String>()  ;
			    	 params1.put("userId","1");//user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
			    	 params1.put("markerId","1");
			    	 params1.put("ll","31.2184028,121.4174401");//locationinfo.getLatitude()+","+locationinfo.getLongitude());
		             String urlcons = SppaConstant.WALKIN_URL_MARKER+"users/1"
		             //+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
		             +"/marker/1";
		             Log.d("Rock", urlcons);
		             marker_mservice.setRetrieveUrl(urlcons);
		             marker_mservice.retrieveMarkerInfo(params1);*/
		             return null;  
		         }  
		         
		         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		         @Override  
		         protected void onPostExecute(String result) {  
		        	 Log.i(TAG, "onPostExecute(Result result) called");  
		        	 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		        	 
		        	 mData = getData(brands_mservice.getList_jobj_item());
	         		 MyAdapter adapter = new MyAdapter(MarkerListActivity.this);
	     			 setListAdapter(adapter);
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
 		super.onStart();
 	}
 		
 	protected void onRestart() {
 		/*mTask = new MyTask();  
        mTask.execute(); */
 		Log.d("lifecycle", "onRestart()");
 		// TODO Auto-generated method stub
 		super.onRestart();
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
        /* Intent intent = new Intent(MarkerListActivity.this, MeActivity.class);
   		 Bundle mBundle = new Bundle();  
   		 intent.putExtras(mBundle);  
   		 startActivityForResult(intent, 0);*/
            
	
		}
	}
	class Button_RefreshListener implements OnClickListener{
		public void onClick(View v) {
			mTask = new MyTask();  
	        mTask.execute(); 
            
	
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
		private List<Map<String, Object>> getData(List<Map<String, Object>>  brands_list) {
	
		return brands_list;
	}
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
	 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)mData.get(position).get(BrandsMenuService.list_item_marker);
	 final String TextView_amount_state =(String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
		
		
	markerID = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_id);
	TextView_inbiEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
	TextView_enName =  (String)mData.get(position).get(BrandsMenuService.JSONObj_item_enName);
	TextView_dealsEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
/*		 Intent intent = new Intent(MarkerListActivity.this, ReceiveInBiActivity.class);
		 Bundle mBundle = new Bundle();  
//		Log.d("Rock", position+"position");
		 
		 mBundle.putInt("Data", position);//压入数据  
		 intent.putExtras(mBundle);  
		 startActivityForResult(intent, 0);*/
	 progressDialog = ProgressDialog.show(MarkerListActivity.this, "Loading...", "Please wait...", true, false);
		 new Thread(mthread).start();
	}
	private final Runnable mthread = new Runnable() {
        public void run() {
       //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	
       	 Map<String,String>params1=new HashMap<String,String>()  ;
    	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
    	 params1.put("markerId",markerID);
    	 params1.put("ll",(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044));
    	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
    	 params1.put("acc",locationinfo.getAccuracy()+"");
         String urlcons = SppaConstant.WALKIN_URL_MARKER+"users/"+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/marker/"+markerID+"?"
         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
         Log.d("Rock", locationinfo.getLatitude()+","+locationinfo.getLongitude()+urlcons);
         marker_mservice.setRetrieveUrl(urlcons);
         marker_mservice.retrieveMarkerInfo(params1);
			 String meta_code = marker_mservice.getCode();
			 
			 Log.d("Rock", meta_code+"LLLLL"+"\n"+marker_mservice.getMessage());
			 if ("200".equals(meta_code)) {
				 Message msg= Message.obtain(mProgressHandler,2);
				 msg.sendToTarget(); 
			}else if("1005".equals(meta_code)){
				 Message msg= Message.obtain(mProgressHandler,3);
				 msg.sendToTarget();
				
			}else{
				 Message msg= Message.obtain(mProgressHandler,4);
				 msg.sendToTarget();
			}
	  
        }
    };
	
	
	
	public final class ViewHolder{
		public ImageView ImageView_loge_pic;
		public TextView TextView_amount_state;
		public ImageView ImageView_inbi_img;
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

		public View getView(int position, View convertView, ViewGroup parent) {
			final String ImageView_loge_pic = Common.stringChangeString((String) mData.get(position).get(BrandsMenuService.JSONObj_item_enName));
			String ImageView01_getThumbnail_pic = (SppaConstant.WALKIN_URL_BASE+"brands/"+ImageView_loge_pic+"/ios/"+ImageView_loge_pic+defaults_mservice.getBrandDetailImage()+".png").toLowerCase();
			 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)mData.get(position).get(BrandsMenuService.list_item_marker);
			// final String TextView_amount_state =(String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
				
				
			final String TextView_inbiEarned = (String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
			final String TextView_enName = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_enName);
			final String TextView_dealsEarned = (String)list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
			String TextView_s="itemName:"+TextView_enName+"\n"+"inbiEarned:"+TextView_inbiEarned+"\n" +"dealsEarned:"+TextView_dealsEarned;
			Log.d("Rock", ImageView01_getThumbnail_pic+"//进行时间比对 -未完成");//进行时间比对 -未完成
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.brands_detail, null);
				loadImage4(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView_loge_pic));
				holder.TextView_amount_state = (TextView)convertView.findViewById(R.id.TextView_amount_state);
				holder.ImageView_inbi_img = (ImageView)convertView.findViewById(R.id.ImageView_inbi_img);
				Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.inbiwhite);
				holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
				holder.TextView_amount_state.setText(TextView_s);
			}else {
					holder=new ViewHolder();  
					loadImage4(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView_loge_pic));
					holder.TextView_amount_state = (TextView)convertView.findViewById(R.id.TextView_amount_state);
					holder.TextView_amount_state.setText(TextView_s);
					holder.ImageView_inbi_img = (ImageView)convertView.findViewById(R.id.ImageView_inbi_img);
					Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.inbiwhite);
					holder.ImageView_inbi_img.setImageDrawable(drawable_inbi_im);
			}
			return convertView;
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(MarkerListActivity.this, url, new ImageCallback() {
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
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
