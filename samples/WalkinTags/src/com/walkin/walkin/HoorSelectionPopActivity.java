package com.walkin.walkin;



import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

public class HoorSelectionPopActivity extends Activity {
    /** Called when the activity is first created. */
	ImageZoomView mZoomView;
	Bitmap image;
	private Button goback;
	ZoomState mZoomState;
	SimpleZoomListener mZoomListener;
	private Button Button_ME;
	private MyButton myhome;
	private TextView TextView_top_logo; 
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    private static Context context;
    String  mShortDescription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floor_pop_act);
        context = this;
		marker_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
        ZoomControls zoomCtrl = (ZoomControls) findViewById(R.id.zoomCtrl);
//        Intent i=this.getIntent();
//        if(i!=null){
//            Bundle b=i.getExtras();
//            if(b!=null){
//                if(b.containsKey("url")){
                 //   String url = b.getString("url");
        goback=(Button) findViewById(R.id.goback);
        goback.setOnClickListener(new gobackButtonListener());
        TextView_top_logo=(TextView) findViewById(R.id.TextView_top_logo);
    	Button_ME=(Button) findViewById(R.id.Button_ME);
		Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
        Button_ME.setOnClickListener(new Button_MEListener());
        
        Bundle bundle = getIntent().getExtras();    
		String mImageUrl=bundle.getString("ImageUrl");
		 mShortDescription=bundle.getString("ShortDescription");
		
        Log.d("Rock", mImageUrl+":mImageUrl");
        
                //  String url="http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png";
        Drawable cachedImage= AsyncImageLoaderPop.loadImageFromUrl(mImageUrl);
               //   Drawable cachedImage = asyncImageLoader.loadImageFromUrl(HoorSelectionPopActivity.this, mImageUrl);
//                  Common.drawableToBitmap(cachedImage);
                  image = drawableToBitmap(cachedImage);
                  mZoomView=(ImageZoomView)findViewById(R.id.pic);
                  mZoomView.setImage(image);
                    
                    mZoomState = new ZoomState();
                    mZoomView.setZoomState(mZoomState);
                    mZoomListener = new SimpleZoomListener();
                    mZoomListener.setZoomState(mZoomState);
                    
                    mZoomView.setOnTouchListener(mZoomListener);
                    resetZoomState();
//                }
//            }
//        }
          zoomCtrl.setOnZoomInClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
			    float z= mZoomState.getZoom()+0.25f;
             mZoomState.setZoom(z);
             mZoomState.notifyObservers();
			}
//              @Override
//              public void onClick(View view) {
//                  float z= mZoomState.getZoom()+0.25f;
//                  mZoomState.setZoom(z);
//                  mZoomState.notifyObservers();
//              }
              
          });
          zoomCtrl.setOnZoomOutClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				 float z= mZoomState.getZoom()-0.25f;
              mZoomState.setZoom(z);
               mZoomState.notifyObservers();
			}

//              @Override
//              public void onClick(View v) {
//                  float z= mZoomState.getZoom()-0.25f;
//                  mZoomState.setZoom(z);
//                  mZoomState.notifyObservers();
//              }
              
          }); 
    }
    class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent(HoorSelectionPopActivity.this, MeActivity.class);
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
      		startActivityForResult(intent, 0);
		}
	}
	class gobackButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			finish();
		}
	}
private void resetZoomState() {
        mZoomState.setPanX(0.5f);
        mZoomState.setPanY(0.5f);
        
        final int mWidth = image.getWidth();
        final int vWidth= mZoomView.getWidth();
        Log.e("iw:",vWidth+"");
        mZoomState.setZoom(1f);
        mZoomState.notifyObservers();
        
    }
    
    public Bitmap drawableToBitmap(Drawable drawable) {
    	Bitmap bitmap = null;
    	try {
    		 bitmap = ((BitmapDrawable)drawable).getBitmap(); 
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return bitmap;
    }

	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName)) ;
//		Log.d("Rock", enname+"");
		String	bg_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/ios/"+enname+defaults_mservice.getBrandBG()+".jpg").toLowerCase();
		String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
		
		TextView_top_logo.setText(mShortDescription);
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		
		
		super.onStart();
	}
	  private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	 private void loadImage_Button4(final String url, final Button bgimageView) {
	        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	    	 Drawable cacheImage = asyncImageLoader.loadDrawable( HoorSelectionPopActivity.this,url, new ImageCallback() {
		           //请参见实现：如果第一次加载url时下面方法会执行
		           public void imageLoaded(Drawable imageDrawable, String imageUrl ) {
	          	 
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
	//采用Handler+Thread+封装外部接口
	    private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
	          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	         Drawable cacheImage = asyncImageLoader.loadDrawable(HoorSelectionPopActivity.this,url,new ImageCallback() {
	             //请参见实现：如果第一次加载url时下面方法会执行
	             public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	            	 
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
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this);
		}
    }

