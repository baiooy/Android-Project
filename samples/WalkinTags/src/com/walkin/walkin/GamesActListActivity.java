package com.walkin.walkin;
/**
 * 
 */

import java.util.List;
import java.util.Map;





import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.GamesMenuService;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author allin
 * 
 */
public class GamesActListActivity extends ListActivity {
	
	private MyButton myhome;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
//	private ImageView nbuy_pic;
//	private Button Button_Refresh;
	private Button Button_ME ;
	private Button Button_top_logo,Button_top_arrow;
	private List<Map<String, Object>> mData;
//	private PopupWindow mPopupWindow;

	private Button goback;
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
 	
 	
 	
	protected final static GamesMenuService games_mservice = GamesMenuService.getInstance();
	private static Context context;
    private MyTask mTask;  
//    private MyTaskMeInbiBalance mTaskInbi;  
    private static Handler ListBrandsHandler=null;
    private static Handler exitHandler=null;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameactlist_act);
		context = this;
		defaults_mservice.setActivity(this);
		games_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
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
     	Button_ME=(Button) findViewById(R.id.Button_ME);
        myhome = new MyButton(this);
	    Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new MEButtonListener());
		Button_ME.setText(user_mservice.getInbiBalance());
//	    Button_Refresh=(Button) findViewById(R.id.Button_Refresh);
//	    Button_Refresh.setOnClickListener(new Button_RefreshListener());
		  goback= (Button) findViewById(R.id.goback);
			goback.setOnClickListener(new gobackListener()); 
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
		             Log.i(TAG, "doInBackground(Params... params) called"); 
		            
		             String urlcons = SppaConstant.WALKIN_URL_BASE+"activityList.json";
		             Log.d("Rock", urlcons);
		        	 games_mservice.setRetrieveUrl(urlcons);
		        	 games_mservice.retrieveGameInfo(urlcons);
		             return null;  
		         }  
		         
		         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		         @Override  
		         protected void onPostExecute(String result) {  
		        	 Log.i(TAG, "onPostExecute(Result result) called");  
		        	 TextView_Loading.setVisibility(View.GONE); 
		        	 progressBar_Loading.setVisibility(View.GONE); 
		        	 mData = getData(games_mservice.getList_jobj_item());
	         		 MyAdapter adapter = new MyAdapter(GamesActListActivity.this);
	     			 setListAdapter(adapter);
		         }  
		           
		         //onCancelled方法用于在取消执行中的任务时更改UI  
		         @Override  
		         protected void onCancelled() {  
		        	 Log.i(TAG, "onCancelled() called");
		        	 
//		             LinearLayout_inbi_congriation.setVisibility(View.GONE);
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
 		
 /*	protected void onRestart() {
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
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
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
		     
	class MEButtonListener implements OnClickListener{
		public void onClick(View v) {
         Intent intent = new Intent(GamesActListActivity.this, MeActivity.class);
   		 Bundle mBundle = new Bundle();  
   		 intent.putExtras(mBundle);  
   		 startActivityForResult(intent, 0);
            
	
		}
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String bg_img_url=SppaConstant.WALKIN_URL_BASE+"style/android/activity.jpg";
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		super.onStart();
	}
	private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
       Drawable cacheImage = asyncImageLoader.loadDrawable(GamesActListActivity.this,url,new ImageCallback() {
           //请参见实现：如果第一次加载url时下面方法会执行
           public void imageLoaded(Drawable imageDrawable, String imageUrl) {
          	 
          	 if (imageDrawable != null ) { 
          		 bgimageView.setBackgroundDrawable(imageDrawable);
          	 }else{
          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.games_bg);
          		 bgimageView.setBackgroundDrawable(drawable_bg);
          	 }
           }
       });
      if(cacheImage!=null){
      	bgimageView.setBackgroundDrawable(cacheImage);
      }else{
      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.games_bg);
      	bgimageView.setBackgroundDrawable(drawable_bg);
 	 }
  }
/*	class Button_RefreshListener implements OnClickListener{
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
		private List<Map<String, Object>> getData(List<Map<String, Object>>  brands_list) {
	
		return brands_list;
	}
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		 
		Intent intent = new Intent(GamesActListActivity.this, GamesActListDetailActivity.class);
		 
        // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
		Bundle mBundle = new Bundle();  
		mBundle.putInt("Data", position);//压入数据  
		intent.putExtras(mBundle);  
		startActivityForResult(intent, 0);
	}
	
	
	
	
	public final class ViewHolder{
		public TextView TextView_paoku;
		public TextView TextView_paoku_neirong;
		
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
			final String title = (String)mData.get(position).get(GamesMenuService.JSONObj_item_title);
			final String excerpt = (String)mData.get(position).get(GamesMenuService.JSONObj_item_excerpt);
	
			
			 
			
			
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.gamesactlist_detail, null);
				holder.TextView_paoku = (TextView)convertView.findViewById(R.id.TextView_paoku);
				holder.TextView_paoku_neirong = (TextView)convertView.findViewById(R.id.TextView_paoku_neirong);
				holder.TextView_paoku.setText(title);
				holder.TextView_paoku_neirong.setText(excerpt);
				
			}else {
					holder=new ViewHolder();  
					holder.TextView_paoku = (TextView)convertView.findViewById(R.id.TextView_paoku);
					holder.TextView_paoku_neirong = (TextView)convertView.findViewById(R.id.TextView_paoku_neirong);
					holder.TextView_paoku.setText(title);
					holder.TextView_paoku_neirong.setText(excerpt);
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(GamesActListActivity.this, url, new ImageCallback() {
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
		
}
