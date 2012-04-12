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
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.NotificationsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;


import android.app.ListActivity;
import android.content.Context;
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
public class NotificationsListActivity extends ListActivity {
	
	private MyButton myhome;
	private Button goback ;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
//	private ImageView nbuy_pic;
//	private Button Button_Refresh;
	private Button Button_top_arrow;
	private List<Map<String, Object>> mData;
//	private PopupWindow mPopupWindow;
	private Button Button01;
	private Button Button03;
	
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
	private ArrayList<String> arraylist ; 
	private Map<String, Object> map ;
 	
	protected final static NotificationsMenuService notifications_mservice = NotificationsMenuService.getInstance();
	private static Context context;
    private MyTask mTask; 
    private MyTask02 mTask02;  

    private static Handler ListBrandsHandler=null;
    private static Handler exitHandler=null;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
//    public boolean isnum=false;
    private static Handler mProgressHandler=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationslist_act);
		context = this;
		defaults_mservice.setActivity(this);
		notifications_mservice.setActivity(this);
		user_mservice.setActivity(this);
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
	    myhome = new MyButton(this);
		Button01=(Button) findViewById(R.id.Button01);
	    Button01.setOnClickListener(new Button01Listener());
	    Integer[] mHomeState01 = { R.drawable.zuo_di,R.drawable.zuo_ding, R.drawable.zuo_ding };
	    Button01.setBackgroundDrawable(myhome.setbg(mHomeState01));
		
	    Button03=(Button) findViewById(R.id.Button03);
	    Button03.setOnClickListener(new Button03Listener());
	    Integer[] mHomeState03 = { R.drawable.you_di,R.drawable.you_ding, R.drawable.you_ding };
	    Button03.setBackgroundDrawable(myhome.setbg(mHomeState03));
		goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
//	    Button_Refresh=(Button) findViewById(R.id.Button_Refresh);
//	    Button_Refresh.setOnClickListener(new Button_RefreshListener());
	   // Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_arrow=(Button) findViewById(R.id.Button_top_arrow);
	    Button_top_arrow.setOnClickListener(new Button_top_logoListener());
	   // Button_top_logo.setOnClickListener(new Button_top_logoListener());
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
		  mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	                case 1 :   
//	                isnum=1;
	        			break; 
	              
	                }   
	            }   
	        } ;
		 mTask = new MyTask();  
         mTask.execute(); 
	  //  new Thread(mthread).start();
	}
	
	class Button01Listener implements OnClickListener{
		public void onClick(View v) {
			 mTask = new MyTask();  
	         mTask.execute(); 
		}
	}
	class Button03Listener implements OnClickListener{
		public void onClick(View v) {
			 mTask02 = new MyTask02();  
			 mTask02.execute(); 
		}
	}
	  class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
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
		            
		             String urlcons = SppaConstant.WALKIN_URL_NOTIFICATIONS
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
		             +"/list"
		             +"?"+"userId="
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
		             +"&limit=10"
		             +"&offset=0"
		             +"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN
		             +"="
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
		             Log.d("Rock", urlcons);
		             notifications_mservice.setRetrieveUrl(urlcons);
		             notifications_mservice.retrieveNotificationsInfo();
		             return null;  
		         }  
		         
		         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		         @Override  
		         protected void onPostExecute(String result) {  
		        	 Log.i(TAG, "onPostExecute(Result result) called");  
		        	 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		        	 mData = getData(notifications_mservice.getList_jobj_item(),"all");
	         		 MyAdapter adapter = new MyAdapter(NotificationsListActivity.this);
	     			 setListAdapter(adapter);
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
		            
		             String urlcons = SppaConstant.WALKIN_URL_NOTIFICATIONS
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
		             +"/list"
		             +"?"+"userId="
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
		             +"&limit=10"
		             +"&offset=0"
		             +"&"
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN
		             +"="
		             +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
		             Log.d("Rock", urlcons);
		            // notifications_mservice.setRetrieveUrl(urlcons);
		            // notifications_mservice.retrieveNotificationsInfo();
		             return null;  
		         }  
		         
		         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		         @Override  
		         protected void onPostExecute(String result) {  
		        	 Log.i(TAG, "onPostExecute(Result result) called");  
		        	 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		        	 mData = getData(notifications_mservice.getList_jobj_item(),"INBI");
	         		 MyAdapter adapter = new MyAdapter(NotificationsListActivity.this);
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
 		 mTask = new MyTask();  
         mTask.execute(); 
// 		 isnum=true;
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		super.onRestart();
	}
/*	     private class MyTaskMeInbiBalance extends AsyncTask<String, Integer, String> {  

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
	           
	     }*/
	     
	     
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
		     

	/*class Button_RefreshListener implements OnClickListener{
		public void onClick(View v) {
			mTask = new MyTask();  
	        mTask.execute(); 
            
	
		}
	}*/
	
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
		private List<Map<String, Object>> getData(List<Map<String, Object>>  brands_list, String str) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if ("INBI".equals(str)) {
				for (int i = 0; i < brands_list.size(); i++) {
					map = new HashMap<String, Object>();
						if ("INBI".equals(brands_list.get(i).get(NotificationsMenuService.JSONObj_item_type))) {
							map.put("JSONObj_item_type", brands_list.get(i).get(NotificationsMenuService.JSONObj_item_type));
							map.put("JSONObj_item_text", brands_list.get(i).get(NotificationsMenuService.JSONObj_item_text));
							map.put("JSONObj_item_unread",  brands_list.get(i).get(NotificationsMenuService.JSONObj_item_unread));
							map.put("JSONObj_item_imagesURL",  brands_list.get(i).get(NotificationsMenuService.JSONObj_item_imagesURL));
							 Log.d("Eagle", "R.drawable."+brands_list.get(i).get(NotificationsMenuService.JSONObj_item_type));
								list.add(map);
								
								
						}
					
					}
				 Log.d("Eagle", "R.drawable.:"+list.size());
				
				return list;
			}else{
				return brands_list;
			}
			
	}
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		 
		
		final String type = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_type);
		final String text = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_text);
		final String unread = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_unread);
		final String imageURL = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_imagesURL);
		
		Log.d("Rock", imageURL+":imageURLimageURL");
		
		 Intent intent = new Intent(NotificationsListActivity.this, NotificationsListDetailActivity.class);
		 Bundle mBundle = new Bundle();  
		 arraylist = new ArrayList<String>(); 
		 arraylist.add(imageURL);
		 arraylist.add(text);
		 arraylist.add(type);
		 arraylist.add(unread);
		 mBundle.putStringArrayList("Data", arraylist);//压入数据  
		 mBundle.putString("unread", unread);//压入数据  
		 intent.putExtras(mBundle);  
		 startActivityForResult(intent, 0);
		
	}
	
	
	
	
	
	public final class ViewHolder{
		public TextView email;
		public TextView neirong;
		public TextView times;
		public TextView img_new;
		
		
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
			
			//final String TextView_amount_state = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
			final String type = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_type);
			final String text = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_text);
			final String unread = (String)mData.get(position).get(NotificationsMenuService.JSONObj_item_unread);
			 
			Log.d("Rock", mData.size()+":unread");
			try {
				
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.notificationslist_detail, null);
				holder.email = (TextView)convertView.findViewById(R.id.email);
				holder.times = (TextView)convertView.findViewById(R.id.times);
				holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
				holder.img_new = (TextView)convertView.findViewById(R.id.img_new);
				if("true".equals(unread)){
					Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.me_message);
					holder.img_new.setBackgroundDrawable(drawable_inbi_im);
				}else{
					Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
					holder.img_new.setBackgroundDrawable(drawable_inbi_im);
				}
				holder.email.setText(type);
				holder.times.setText("");
				holder.neirong.setText(text);
				
			}else {
					holder=new ViewHolder();  
					holder.email = (TextView)convertView.findViewById(R.id.email);
					holder.times = (TextView)convertView.findViewById(R.id.times);
					holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
					holder.img_new = (TextView)convertView.findViewById(R.id.img_new);
					if("true".equals(unread)){
						Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.me_message);
						holder.img_new.setBackgroundDrawable(drawable_inbi_im);
					}else{
						Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
						holder.img_new.setBackgroundDrawable(drawable_inbi_im);
					}
					holder.email.setText(type);
					holder.times.setText("");
					holder.neirong.setText(text);
			}
			} catch (Exception e) {
				// TODO: handle exception
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(NotificationsListActivity.this, url, new ImageCallback() {
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
