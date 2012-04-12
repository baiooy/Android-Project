package com.walkin.walkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.NotificationsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationsListDetailActivity extends Activity{
	private MyButton myhome;
	private TextView TextView_title,textview_text,textview_neirong ;
	private ImageView imageview_img;
	private Button  Button_top_logo;
	private Button goback ;
    private MyTaskRead mTaskRead;  
	private static Context context;
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	protected final static NotificationsMenuService notifications_mservice = NotificationsMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationsdetail_act);
    	context = this;
		user_mservice.setActivity(this);
		notifications_mservice.setActivity(this);
    	goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
        Button_top_logo.setOnClickListener(new Button_top_logoListener());
        TextView_title=(TextView) findViewById(R.id.TextView_title);
        textview_text=(TextView) findViewById(R.id.textview_text);
        textview_neirong=(TextView) findViewById(R.id.textview_neirong);
        imageview_img=(ImageView) findViewById(R.id.imageview_img);
        
        Bundle bundle = getIntent().getExtras();    
		
		ArrayList<String> arraylist_data = bundle.getStringArrayList("Data");
		String isunread = bundle.getString("unread");
		
		 String imageURL = arraylist_data.get(0);
		String text =arraylist_data.get(1);
		String type =arraylist_data.get(2);
		String unread =arraylist_data.get(3);
        
	    TextView_title.setText(unread);
        textview_text.setText(type);
        textview_neirong.setText(text);
        
        if (!"badge".equals(type)) {
        	imageview_img.setVisibility(View.GONE);
		}
        
        loadImage5(imageURL,  (ImageView)findViewById(R.id.imageview_img));
        
        if ("true".equals(isunread)) {
			 mTaskRead = new MyTaskRead();  
	         mTaskRead.execute(); 
		}
		
        
        
//        Button_ME=(Button) findViewById(R.id.Button_ME);
//        Button_ME.setText(user_mservice.getInbiBalance());
//        myhome = new MyButton(this);
//        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
//	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
//	    Button_ME.setOnClickListener(new Button_MEListener());
    }
    
    private class MyTaskRead extends AsyncTask<String, Integer, String> {  
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
            Map<String,String>param=new HashMap<String,String>()  ;
            param.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
            param.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
				 String urlcons = SppaConstant.WALKIN_URL_NOTIFICATIONS
				 +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
	             +"/read"+"?apikey=BYauu6D9";
				 notifications_mservice.setRetrieveUrl(urlcons);
	             notifications_mservice.retrieveNotificationsInfoParams(param);
				 String user_login_meta_code = user_mservice.getCode();
				 Log.d("Rock", urlcons+"LLLLL");
            return null;  
        }  
        
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
       	 Log.i(TAG, "onPostExecute(Result result) called");  
        }  
          
        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override  
        protected void onCancelled() {  
       	 Log.i(TAG, "onCancelled() called");
       	 
//            LinearLayout_inbi_congriation.setVisibility(View.GONE);
        }  
    }

    
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
	//		finish();
		}
	}
	  class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}

	  
	  
	  
	class Button_top_logoListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = getIntent();
			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(NotificationsListDetailActivity.this, url, new ImageCallback() {
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
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
}
