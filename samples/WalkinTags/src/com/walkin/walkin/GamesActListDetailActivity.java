package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.GamesMenuService;
import com.walkin.service.UserMenuService;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GamesActListDetailActivity extends Activity{
	private MyButton myhome;
	private Button Button_ME ;
	private TextView TextView_title,TextView_excerpt,TextView_content ;
	private ImageView ImageView_thumbnail ;
	private Button goback;
	
	
//	private Button Button_Next ;
	   protected final static UserMenuService user_mservice = UserMenuService.getInstance();
		protected final static GamesMenuService games_mservice = GamesMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamesactlistdetail_act);
        user_mservice.setActivity(this);
        games_mservice.setActivity(this);
     
        TextView_title=(TextView) findViewById(R.id.TextView_title);
        TextView_excerpt=(TextView) findViewById(R.id.TextView_excerpt);
        TextView_content=(TextView) findViewById(R.id.TextView_content);
        
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        
        Button_ME=(Button) findViewById(R.id.Button_ME);
        Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new Button_MEListener());
	    
	    Bundle bundle = getIntent().getExtras();    
		int data_canshu=bundle.getInt("Data");
	    
		 String strTitle=(String) games_mservice.getList_jobj_item().get(data_canshu).get(GamesMenuService.JSONObj_item_title);
		 String strExcerpt=(String) games_mservice.getList_jobj_item().get(data_canshu).get(GamesMenuService.JSONObj_item_excerpt);
		 String strThumbnail=(String) games_mservice.getList_jobj_item().get(data_canshu).get(GamesMenuService.JSONObj_item_thumbnail);
		 String strContent=(String) games_mservice.getList_jobj_item().get(data_canshu).get(GamesMenuService.JSONObj_item_content);
		 String strDate=(String) games_mservice.getList_jobj_item().get(data_canshu).get(GamesMenuService.JSONObj_item_date);
		 TextView_title.setText(strTitle);
		 TextView_excerpt.setText(strExcerpt);
		 TextView_content.setText(strContent);
		 loadImage5(strThumbnail, (ImageView)findViewById(R.id.ImageView_thumbnail));
		 
	    
	    
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
        Drawable cachedImage = asyncImageLoader.loadDrawable(GamesActListDetailActivity.this, url, new ImageCallback() {
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
    class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
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
       Drawable cacheImage = asyncImageLoader.loadDrawable(GamesActListDetailActivity.this,url,new ImageCallback() {
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
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			   Intent intent = new Intent(GamesActListDetailActivity.this, MeActivity.class);
		   		 Bundle mBundle = new Bundle();  
		   		 intent.putExtras(mBundle);  
		   		 startActivityForResult(intent, 0);
		}
	}
	class Button_NextListener implements OnClickListener{
		public void onClick(View v) {
            Intent intent=new Intent();
			intent.setClass(GamesActListDetailActivity.this, BadgesListActivity.class);
			Bundle bundle=new  Bundle();
			String str1="aaaaaa";
			bundle.putString("str1", str1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Intent intent = getIntent();
			Bundle b =intent.getExtras();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 1:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		default:
	           break;
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
