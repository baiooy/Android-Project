package com.walkin.walkin;

import java.util.List;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AndroidBarcodeView;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.VeDate;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DealsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class InStoreDetailActivity extends Activity{
	private MyButton myhome;
	private Button Button_ME,Button_top_logo,goback ;
	private TextView TextView_logo,TextView_shortDescription,TextView_ref,TextView_youhui,
	TextView_validUntil,TextView_jieshao,TextView_legalText,TextView_barcode_description,TextView_barcode;
    private ImageView ImageView_Barcode;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	protected final static DealsMenuService deals_mservice = DealsMenuService.getInstance();
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
	private static Context context;
    private ImageView ImageView_item; 
    private PopupWindow popupWindow;
	private RelativeLayout RelativeLayout_pop;
	private ImageButton ImageButton02,ImageButton01;
	private ProgressBar ProgressBar01;
	private List<Map<String, Object>> mData;
	float screenW;
    float screenH;
    String data_activity;
    String barcode,barcodeStandard,imageUrl ;
	int data_canshu;
	private SharedPreferences sp;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instoredetail_act);
        context = this;
        deals_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
        marker_mservice.setActivity(this);
        brands_mservice.setActivity(this);
   	 	sp = getSharedPreferences("user", 0);
        TextView_logo=(TextView) findViewById(R.id.TextView_logo);
        TextView_shortDescription=(TextView) findViewById(R.id.TextView_shortDescription);
        TextView_ref=(TextView) findViewById(R.id.TextView_ref);
        TextView_youhui=(TextView) findViewById(R.id.TextView_youhui);
        TextView_validUntil=(TextView) findViewById(R.id.TextView_validUntil);
        TextView_jieshao=(TextView) findViewById(R.id.TextView_jieshao);
        TextView_legalText=(TextView) findViewById(R.id.TextView_legalText);
        TextView_barcode_description=(TextView) findViewById(R.id.TextView_barcode_description);
        TextView_barcode=(TextView) findViewById(R.id.TextView_barcode);
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
        Button_ME=(Button) findViewById(R.id.Button_ME);
        Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new Button_MEListener());
	    
	    Bundle bundle = getIntent().getExtras();   
	    
	     data_activity=bundle.getString("Activity");
		 data_canshu=bundle.getInt("Data");
		String mTextStr ;
		final String strlegalText;
		String mEnName = null;
		String mRedemptionType = null,mshortDescription = null,mreference = null,mDetailDescription = null,mValidUntil = null,legalText=null;
	    if ("DealsActivity".equals(data_activity)) {
	    	mData = getData(deals_mservice.getList_jobj_item());
	    	 mEnName =  (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_brandEnName);
			  mRedemptionType = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_redemptionType);
				 mshortDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_shortDescription);
				 mreference = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_reference);
				 mDetailDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_detailDescription);
				 imageUrl = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_imageUrl);
				 mValidUntil = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_validUntil);
				 legalText = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_legalText);
				 barcode =(String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_redemptionCode);
				 Log.d("Rock", mEnName+":mEnName");
				 for (int i = 0; i <brands_mservice.getList_jobj_item().size(); i++) {
					 if (mEnName.equals(brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.JSONObj_item_enName))) {
						 barcodeStandard= (String) brands_mservice.getList_jobj_item().get(i).get(BrandsMenuService.JSONObj_item_barcodeStandard);
					}
				 }
		}else{
			mData = getData(marker_mservice.getList_jobj_item_deals());
			
			barcodeStandard=(String)marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_barcodeStandard);
			
			  mEnName = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_brandEnName);
		  mRedemptionType = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_redemptionType);
			 mshortDescription = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_shortDescription);
			 mreference = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_reference);
			 mDetailDescription = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_detailDescription);
			 imageUrl = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_imageUrl);
			 mValidUntil = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_validUntil);
			 legalText = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_legalText);
			 barcode =(String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_redemptionCode);
		}
		
		 
		 
		 
		 
	    if ("".equals(legalText)) {
	    	TextView_legalText.setText(R.string.TextView_strlegalText);
		}else{
			TextView_legalText.setText(legalText);
		}
	    
		if("bogof".equals(mRedemptionType)){
			mTextStr="买一送一";
		}else if("discount".equals(mRedemptionType)){
			 if ("DealsActivity".equals(data_activity)) {
			mTextStr= (100-Double.parseDouble((String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_discount)))/10+"";
			if ("0".equals(mTextStr.substring(mTextStr.length()-1))) {
				mTextStr=(100- Math.round(Double.parseDouble((String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_discount))))/10+"折优惠";
			}else{
				mTextStr=mTextStr+"折优惠";
			}
		}else{
			mTextStr= (100-Double.parseDouble((String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_discount)))/10+"";
			if ("0".equals(mTextStr.substring(mTextStr.length()-1))) {
				mTextStr=(100- Math.round(Double.parseDouble((String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_discount))))/10+"折优惠";
			}else{
				mTextStr=mTextStr+"折优惠";
			}
		}
		}else if("rebate".equals(mRedemptionType)){
			if ("DealsActivity".equals(data_activity)) {
			 	mTextStr = "优惠"+(String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_rebate)+"元";
			}else{
				mTextStr = "优惠"+(String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_rebate)+"元";
			}
			
		}else{
			 if ("DealsActivity".equals(data_activity)) {
				 	mTextStr = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_giftDescription);
				}else{
					mTextStr = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_giftDescription);
				}
			
		}

		String strValidUntil;
		if ("".equals(mValidUntil)) {
			 strValidUntil ="暂未解锁";
		}else{
			 strValidUntil ="今日至"+VeDate.DateToStr(VeDate.StrToDate(mValidUntil))+"有效";
		}
		TextView_logo.setText(mEnName);
		TextView_shortDescription.setText(mshortDescription);
		TextView_ref.setText(mreference);
		TextView_youhui.setText(mTextStr);
		TextView_jieshao.setText(mDetailDescription);
		TextView_validUntil.setText(strValidUntil);
		
		loadImage4(SppaConstant.WALKIN_URL_BASE+imageUrl, (ImageView)findViewById(R.id.ImageView_item));
	    
		DisplayMetrics  dm = new DisplayMetrics();  
	      //取得窗口属性  
	      getWindowManager().getDefaultDisplay().getMetrics(dm);  
	      //窗口的宽度  
	      int screenWidth = dm.widthPixels;  
	      //窗口高度  
	      int screenHeight = dm.heightPixels;  
	      Log.d("Rock", screenWidth+"");
	      Log.d("Rock", screenHeight+"");
	      if (screenWidth==320) {
	    	  screenW=2f;
	    	  screenH=80f;
		}else{
			screenW=3f;
			screenH=100f;
		}
	     
	      if ("0".equals(barcode)) {
	    	TextView_barcode.setText("暂无代码");
		}else{
			TextView_barcode.setText(barcode);
		
			
		if ("NoBarcode".equals(barcodeStandard)) {
			TextView_barcode_description.setVisibility(View.VISIBLE);
			TextView_barcode_description.setText("优惠代码");
		
		}else{
	      AndroidBarcodeView view = new AndroidBarcodeView(this,screenW,screenH,barcode);
	      ImageView_Barcode = (ImageView) findViewById(R.id.ImageView_Barcode); 

	    	Bitmap b = Bitmap.createBitmap(screenWidth-40, 120, Bitmap.Config.RGB_565);
	    	Canvas canvasTemp = new Canvas(b);
	    	canvasTemp.drawColor(Color.WHITE);
	    	view.draw(canvasTemp);
	    
	    	ImageView_Barcode.setScaleType(ScaleType.CENTER);
	    	ImageView_Barcode.setImageBitmap(b);
		}
		}
	        ImageView_item=(ImageView) findViewById(R.id.ImageView_item);
	        ImageView_item.setOnClickListener(new ImageView_itemListener());
	        
	        
	   /*     if ("1".equals(sp.getString("istransparentall", null))) {
	          	 Intent intent = new Intent(InStoreDetailActivity.this, TransparentAllActivity.class);
	   	         Bundle mBundle = new Bundle();
	   			 mBundle.putString("Activity", "InStoreDetailActivity");// 压入数据
	   			 intent.putExtras(mBundle);
	   			 startActivityForResult(intent, 0);
	   		//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
	   			}*/
	        
	        
	        
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
        		 imageView.setImageResource(R.drawable.deal_default_pic);
            	 }
             }
         });
        if(cacheImage!=null){
        	imageView.setImageDrawable(cacheImage);
        }else{
   		 imageView.setImageResource(R.drawable.deal_default_pic);
   	 }
    }
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(InStoreDetailActivity.this, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	            	   imageView.setImageDrawable(imageDrawable);
	               }else{
	            		 imageView.setImageResource(R.drawable.deal_default_pic);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	imageView.setImageDrawable(cachedImage);
	        }else{
		 imageView.setImageResource(R.drawable.deal_default_pic);
	 }
}
		 private void loadImage_Button(final String url, final Button bgimageView) {
		        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
			 Drawable cacheImage = asyncImageLoader3.loadDrawable( url, new AsyncImageLoader3.ImageCallback() {
		           //请参见实现：如果第一次加载url时下面方法会执行
		           public void imageLoaded(Drawable imageDrawable ) {
		          	 
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
		    private void loadImage_Button4(final String url, final Button bgimageView) {
		        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		    	 Drawable cacheImage = asyncImageLoader.loadDrawable( InStoreDetailActivity.this,url, new ImageCallback() {
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
	 private List<Map<String, Object>> getData(List<Map<String, Object>>  deals_list) {
			
			return deals_list;
		}
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String enname;
		if ("DealsActivity".equals(data_activity)) {
			// enname=	Common.stringChangeString((String) deals_mservice.getList_jobj_item().get(data_canshu).get(DealsMenuService.JSONObj_item_brandEnName)) ;
		}else{
			 enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName)) ;
//			Log.d("Rock", enname+"");
			 String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
			Log.d("Rock", enname_img_url+":bg_img_url");
			loadImage_Button4(enname_img_url, (Button)findViewById(R.id.Button_top_logo));
		}
		
		
		
		
		
		
		
		
		super.onStart();
	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
	 		case RESULT_OK:
	 			Intent intent = getIntent();
	 			Bundle b =intent.getExtras();
//	 			Log.d("Rock",b.getString("str1")+"00000");
	 			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	 			finish();
	             break;
	 		case 1:
	 			intent = getIntent();
//	 			Log.d("Rock",b.getString("str1")+"00000");
	 			setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	 			finish();
	             break;
	 		case 2:
	 			intent = getIntent();
//	 			Log.d("Rock",b.getString("str1")+"00000");
	 			setResult(2, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	 			finish();
	 	            break;
	 		case 3:
	 			intent = getIntent();
//	 			Log.d("Rock",b.getString("str1")+"00000");
	 			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	 			finish();
	 	            break;
	 		default:
	 	           break;
	 		}
	 	}
		class Button_top_logoListener implements OnClickListener{
	 		public void onClick(View v) {
	 			Intent intent = getIntent();
    			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
    			finish();//此处一定要调用finish()方
	 		}
	 	}  
 class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
		}
	}
	class ImageView_itemListener implements OnClickListener{
 		public void onClick(View v) {
 			openPopupwin(SppaConstant.WALKIN_URL_BASE+imageUrl);
 			
 		}
 	}
 private void openPopupwin(String deal_img) {
		
		
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View menuView = (View) mLayoutInflater.inflate(
				R.layout.gridview_pop, null, true);
		RelativeLayout_pop= (RelativeLayout) menuView.findViewById(R.id.RelativeLayout_pop);
		ImageButton01 = (ImageButton) menuView.findViewById(R.id.ImageButton01);
		ImageButton02=(ImageButton) menuView.findViewById(R.id.ImageButton02);
		ProgressBar01=(ProgressBar) menuView.findViewById(R.id.ProgressBar01);
		ProgressBar01.setVisibility(View.VISIBLE);
		/*ImageButton02.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < friendLLlist.size(); i++) {
					Log.d("Rock", friendLLlist.get(i).get("locations_address")+"11111111111");
				}
				
			}
		});*/
		ImageButton01.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});
//		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		RelativeLayout_pop.requestFocus();
		loadbgimageView(deal_img, (RelativeLayout)menuView.findViewById(R.id.RelativeLayout_pop));
	/*	menuGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 11) {
					popupWindow.dismiss();
				}
			}
		});*/
		RelativeLayout_pop.setOnKeyListener(new OnKeyListener() {// 焦点到了gridview上，所以需要监听此处的键盘事件。否则会出现不响应键盘事件的情况
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						switch (keyCode) {
						case KeyEvent.KEYCODE_MENU:
							if (popupWindow != null && popupWindow.isShowing()) {
								popupWindow.dismiss();
							}
							break;
						}
						System.out.println("menuGridfdsfdsfdfd");
						return true;
					}
				});
		popupWindow = new PopupWindow(menuView, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupFriendAnimation);
		popupWindow.showAtLocation(findViewById(R.id.RelativeLayout_list), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
 private void loadbgimageView(final String url, final RelativeLayout bgimageView) {
     // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
     Drawable cachedImage = asyncImageLoader.loadDrawable(InStoreDetailActivity.this, url, new ImageCallback() {
         public void imageLoaded(Drawable imageDrawable, String imageUrl) {
             
         	 if (imageDrawable != null ) { 
         		 bgimageView.setBackgroundDrawable(imageDrawable);
         			ProgressBar01.setVisibility(View.GONE);
         	 }else{
         		 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
         		 bgimageView.setBackgroundDrawable(drawable_bg);
         	 }
         }
     });
     if(cachedImage!=null){
     	bgimageView.setBackgroundDrawable(cachedImage);
     				ProgressBar01.setVisibility(View.GONE);
     }else{
     	 Drawable drawable_bg = getResources().getDrawable(R.drawable.none_color);
     	bgimageView.setBackgroundDrawable(drawable_bg);
	 }
 	}
	class Button_MEListener implements OnClickListener{
	public void onClick(View v) {
			
			Intent intent = new Intent(InStoreDetailActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
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
