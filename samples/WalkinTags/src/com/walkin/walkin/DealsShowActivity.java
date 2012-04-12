package com.walkin.walkin;

import java.util.List;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.VeDate;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DealsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DealsShowActivity extends Activity{
	private MyButton myhome;
	private Button Button_ME,Button_top_logo;
	private Button goback;
	private LinearLayout LinearLayout_Buy;
	private TextView TextView_logo,TextView_youhui,TextView_Buy ,TextView_jieshao,TextView_shortDescription,TextView_ref,TextView_validUntil;
	protected final static DealsMenuService deals_mservice = DealsMenuService.getInstance();
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
	private static Context context;
	private List<Map<String, Object>> mData;
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
    private ImageView ImageView_item; 
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
    private  String mBrandId,imageUrl;
    
    
	private PopupWindow popupWindow;
	private RelativeLayout RelativeLayout_pop;
	private ImageButton ImageButton02,ImageButton01;
	private ProgressBar ProgressBar01;
	
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealsshow_act);
        
        context = this;
		user_mservice.setActivity(this);
		deals_mservice.setActivity(this);
		brands_mservice.setActivity(this);
		locationinfo.setActivity(this);
		
		LinearLayout_Buy=(LinearLayout) findViewById(R.id.LinearLayout_Buy);
        TextView_logo=(TextView) findViewById(R.id.TextView_logo);
        TextView_youhui=(TextView) findViewById(R.id.TextView_youhui);
        TextView_jieshao=(TextView) findViewById(R.id.TextView_jieshao);
        TextView_shortDescription=(TextView) findViewById(R.id.TextView_shortDescription);
        TextView_ref=(TextView) findViewById(R.id.TextView_ref);
        
        TextView_validUntil=(TextView) findViewById(R.id.TextView_validUntil);
        
        ImageView_item=(ImageView) findViewById(R.id.ImageView_item);
        ImageView_item.setOnClickListener(new ImageView_itemListener());
        Button_ME=(Button) findViewById(R.id.Button_ME);
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.none_color, R.drawable.none_color };
        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
    	Button_ME.setText(user_mservice.getInbiBalance());
        Button_ME.setOnClickListener(new Button_MEListener());
        LinearLayout_Buy.setOnClickListener(new LinearLayout_BuyListener());
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    
	    Bundle bundle = getIntent().getExtras();    
		int data_canshu=bundle.getInt("Data");
		mData = getData(deals_mservice.getList_jobj_item());
		final String mEnName = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_brandEnName);
		final String mRedemptionType = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_redemptionType);
		mBrandId = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_brandId);
		String mTextStr ;
		if("bogof".equals(mRedemptionType)){
			mTextStr="买一送一";
		}else if("discount".equals(mRedemptionType)){
			
			mTextStr= (100-Double.parseDouble((String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_discount)))/10+"";
			if ("0".equals(mTextStr.substring(mTextStr.length()-1))) {
				mTextStr=(100- Math.round(Double.parseDouble((String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_discount))))/10+"折优惠";
			}else{
				mTextStr=mTextStr+"折优惠";
			}
			
			
			
		}else if("rebate".equals(mRedemptionType)){
			mTextStr = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_rebate)+"元优惠";
		}else{
			mTextStr = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_giftDescription);
		}
		final String mDetailDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_detailDescription);
		imageUrl = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_imageUrl);
		final String mShortDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_shortDescription);
		final String mReference = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_reference);
		
		final String mValidUntil = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_validUntil);
		String strValidUntil;
		if ("".equals(mValidUntil)) {
			 strValidUntil ="暂未解锁";
		}else{
			 strValidUntil ="今日至"+VeDate.DateToStr(VeDate.StrToDate(mValidUntil))+"有效";
		}
//		Log.d("Rock", mValidUntil);
		TextView_logo.setText(mEnName);
		TextView_youhui.setText(mTextStr);
		TextView_jieshao.setText(mDetailDescription);
		TextView_ref.setText(mReference);
		TextView_shortDescription.setText(mShortDescription);
		TextView_validUntil.setText(strValidUntil);
		loadImage4(SppaConstant.WALKIN_URL_BASE+imageUrl, (ImageView)findViewById(R.id.ImageView_item));
		
		mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
               
                case 2 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "sssssssssss");
          			Intent intent=new Intent();
     				intent.setClass(DealsShowActivity.this, DealsShowDetailActivity.class);
     				Bundle bundle=new  Bundle();
     				int str1=0;
     				bundle.putInt("Data", str1);
     				intent.putExtras(bundle);
     				startActivityForResult(intent, 0);
//            			finish();
        			break; 
                case 3 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "ddddddddd");
                		AlertDialog.Builder b1 = new AlertDialog.Builder(DealsShowActivity.this);
                		b1.setTitle("抱歉");
    		            b1.setMessage("有误");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            b1.show();
                    break;  
                }   
            }   
        } ; 
		
    }
	 class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}
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
	 private List<Map<String, Object>> getData(List<Map<String, Object>>  deals_list) {
			
			return deals_list;
		}
    
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(DealsShowActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}  
	class ImageView_itemListener implements OnClickListener{
 		public void onClick(View v) {
 			openPopupwin("");
 			
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
		loadbgimageView(SppaConstant.WALKIN_URL_BASE+imageUrl, (RelativeLayout)menuView.findViewById(R.id.RelativeLayout_pop));
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
	class LinearLayout_BuyListener implements OnClickListener{
		public void onClick(View v) {
			
			 progressDialog = ProgressDialog.show(DealsShowActivity.this, "Loading...", "Please wait...", true, false);
			 new Thread(mthread).start();
			
			
		}
	}
	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
			//	 String encodePass =RegExpValidator.encode(s.getBytes());
			//	 String editEmailStr_test="kevin.hu@gmail.com";
	        	// http://122.195.135.91:2861/api/brands/ 2?brandId=2
				 String urlcons = SppaConstant.WALKIN_URL_BRANDSID+mBrandId+"?brandId="+mBrandId+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
	             +"&"+"ll="+(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044)+"&"+"acc="+locationinfo.getAccuracy()+"&apikey=BYauu6D9";
				 Log.d("Rock", urlcons+":urlcons-DealsShowActivity");
				 brands_mservice.setRetrieveUrl(urlcons);
				 brands_mservice.retrieveBrandsIDInfo();
				 String user_login_meta_code = brands_mservice.getCode();
				 Log.d("Rock", user_login_meta_code+"LLLLL");
				 if ("200".equals(user_login_meta_code)) {
					 Message msg= Message.obtain(mProgressHandler,2);
	            	 msg.sendToTarget(); 
				}else{
					 Message msg= Message.obtain(mProgressHandler,3);
	            	 msg.sendToTarget();
				}
		  
	         }
	     };
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(DealsShowActivity.this, url, new ImageCallback() {
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
	    private void loadbgimageView(final String url, final RelativeLayout bgimageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(DealsShowActivity.this, url, new ImageCallback() {
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
	protected void onStart() {
		super.onStart();
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
