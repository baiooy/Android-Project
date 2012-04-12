package com.walkin.walkin;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.MarkerMenuService;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TransparentBadgesActivity extends Activity {
	/** Called when the activity is first created. */
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	private static Context context;
	private LinearLayout linearLayout1;
	private TextView TextView_badges;
	
	public void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);  
    		context = this;
    		marker_mservice.setActivity(this);
            setTheme(R.style.Transparent);  
            setContentView(R.layout.transparentbadges_act);  
          	 Bundle bundle = getIntent().getExtras();    
          	 int	data_type =bundle.getInt("Data");
            
            
            linearLayout1=(LinearLayout) findViewById(R.id.linearLayout1);
            linearLayout1.setOnClickListener(new linearLayout1Listener());
            TextView_badges=(TextView) findViewById(R.id.TextView_badges);
        	loadImage5(  (String)marker_mservice.getList_item_newNotifications().get(data_type)
     			   .get(MarkerMenuService.JSONObj_item_newNotifications_imageURL), (ImageView)findViewById(R.id.badges_ImageView));
            try {
            	Log.e("Rock", marker_mservice.getList_item_newNotifications().get(data_type)+":transparent");
            	   TextView_badges.setText( (String)marker_mservice.getList_item_newNotifications().get(data_type)
            			   .get(MarkerMenuService.JSONObj_item_newNotifications_text));
			} catch (Exception e) {
//				Log.e("Rock",e+":e"+marker_mservice.getList_item_newNotifications()+":transparent");
				
				// TODO: handle exception
			}
         
            
        }
    class linearLayout1Listener implements OnClickListener{
 		public void onClick(View v) {
 			finish();
 			marker_mservice.setList_item_newNotifications(null);
			// overridePendingTransition(R.anim.alpha_scale_translate,
			// R.anim.my_alpha_action);
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
        Drawable cachedImage = asyncImageLoader.loadDrawable(TransparentBadgesActivity.this, url, new ImageCallback() {
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
//	@Override
//	protected void glSurfaceViewConfig() {
//		// !important
//		_glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
//		_glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//	}


}