package com.walkin.walkin;

import java.util.ArrayList;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.Common;
//import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BadgesDetailActivity extends Activity{
//	private Button Button_ME ;
	private Button  Button_top_logo;
	private Button goback ;
	private TextView textview_badg,textview_description,textview_neirong ;
	
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badgesdetail_act);
    	goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
        Button_top_logo.setOnClickListener(new Button_top_logoListener());
        textview_badg=(TextView) findViewById(R.id.textview_badg);
        textview_description=(TextView) findViewById(R.id.textview_description);
        textview_neirong=(TextView) findViewById(R.id.textview_neirong);
        Bundle bundle = getIntent().getExtras();    
		ArrayList<String> arraylist_data = bundle.getStringArrayList("Data");
		
		String description = arraylist_data.get(0);
		String imagesURL =arraylist_data.get(1);
		String name =arraylist_data.get(2);
		String notification =arraylist_data.get(3);
		Log.e("Rock", imagesURL+":imagesURL");
		loadImage4(SppaConstant.WALKIN_URL_BASE+"badges/ios/"+imagesURL+"_full.png", (ImageView)findViewById(R.id.imageview_badges_img));
		textview_badg.setText(name);
		textview_description.setText(Common.rmStrBlank(description));
		textview_neirong.setText(notification);
        
//        Button_ME=(Button) findViewById(R.id.Button_ME);
//        Button_ME.setText(user_mservice.getInbiBalance());
//        myhome = new MyButton(this);
//        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
//	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
//	    Button_ME.setOnClickListener(new Button_MEListener());
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
	/*	 private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();
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
	    }*/
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(BadgesDetailActivity.this, url, new ImageCallback() {
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
