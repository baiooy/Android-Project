package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RelativeLayout;

public class InActivity extends Activity{
	private static Context context;
	protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	private static Handler exitHandler=null;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_act);
        context = this;
		defaults_mservice.setActivity(this); 
        
        Bundle bundle = getIntent().getExtras();    
		String data_enName=Common.stringChangeString(bundle.getString("enName"));
        
		String TextView_bgimg_state = (SppaConstant.WALKIN_URL_BASE+"brands/"+data_enName+"/ios/"+data_enName+defaults_mservice.getBrandBG()+".jpg").toLowerCase();
        Log.d("Rock", TextView_bgimg_state+":TextView_bgimg_state");
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
	    try {
    		Message message = Message.obtain(TransparentCameraActivity.getExitHandler(),0);
    		message.sendToTarget();
    		 message = Message.obtain(StringOGLTutorial.getExitHandler(),0);
    		message.sendToTarget();
    		 message = Message.obtain(StringOGLTutorialInStore.getExitHandler(),0);
     		message.sendToTarget();
	    } catch (Exception e) {
		// TODO: handle exception
	    }
	    loadImage4(TextView_bgimg_state,(RelativeLayout)findViewById(R.id.RelativeLayout_bg));
    }
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final RelativeLayout bgimageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(InActivity.this, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	            	 if (imageDrawable != null ) { 
	            		 bgimageView.setBackgroundDrawable(imageDrawable);
	            	 }else{
	            		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
	            		 bgimageView.setBackgroundDrawable(drawable_bg);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	bgimageView.setBackgroundDrawable(cachedImage);
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
	public static Handler getExitHandler(){ 
        return exitHandler;
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
