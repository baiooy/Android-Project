package com.walkin.walkin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.VeDate;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DealsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class RedeemGiftActivity extends Activity{
	/**
	 * InStoreShowActivity 无用
	 * @param args
	 */
	private MyButton myhome;
	private Button Button_ME,Button_Buy ,Button_top_logo,goback;
	private TextView TextView_logo,TextView_youhui,TextView_jieshao,TextView_shortDescription,TextView_validUntil,TextView_xiaolian,TextView_ref,TextView_getText;
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private static Context context;
	private List<Map<String, Object>> mData;
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static DealsMenuService deals_mservice = DealsMenuService.getInstance();
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
    private  String dealID,isused;
    String data_activity,imageUrl ;
    int data_canshu;
    private ImageView ImageView_item; 
    private PopupWindow popupWindow;
	private RelativeLayout RelativeLayout_pop;
	private ImageButton ImageButton02,ImageButton01;
	private ProgressBar ProgressBar01;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeemgift_act);
        
        context = this;
		defaults_mservice.setActivity(this);
        marker_mservice.setActivity(this);
        user_mservice.setActivity(this);
        deals_mservice.setActivity(this);
        
        Button_Buy=(Button) findViewById(R.id.Button_Buy);
        TextView_logo=(TextView) findViewById(R.id.TextView_logo);
        TextView_youhui=(TextView) findViewById(R.id.TextView_youhui);
        TextView_jieshao=(TextView) findViewById(R.id.TextView_jieshao);
        TextView_shortDescription=(TextView) findViewById(R.id.TextView_shortDescription);
        TextView_ref=(TextView) findViewById(R.id.TextView_ref);
        TextView_validUntil=(TextView) findViewById(R.id.TextView_validUntil);
        TextView_xiaolian=(TextView) findViewById(R.id.TextView_xiaolian);
        TextView_getText=(TextView) findViewById(R.id.TextView_getText);
        Button_ME=(Button) findViewById(R.id.Button_ME);
        Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
        Button_ME.setOnClickListener(new Button_MEListener());
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
		ImageView_item=(ImageView) findViewById(R.id.ImageView_item);
        ImageView_item.setOnClickListener(new ImageView_itemListener());
		
        Button_Buy.setOnClickListener(new Button_BuyListener());
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    
	    Bundle bundle = getIntent().getExtras();    
	    data_activity=bundle.getString("Activity");
		 data_canshu=bundle.getInt("Data");
		final String mTextStr,strlegalText ;
		String mEnName = null;
		String mRedemptionType = null,mshortDescription = null,mDetailDescription = null,mValidUntil = null,legalText=null,mreference=null;
		
		
		
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
					 isused = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_used);
					 dealID=(String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_id);
					 mTextStr = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_giftDescription);
		    	
			}else{
				mData = getData(marker_mservice.getList_jobj_item_deals());
			  mEnName = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_brandEnName);
			  mRedemptionType = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_redemptionType);
				 mshortDescription = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_shortDescription);
				 mreference= (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_reference);
				 mDetailDescription = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_detailDescription);
				 imageUrl = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_imageUrl);
				 mValidUntil = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_validUntil);
				 legalText = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_legalText);
				 isused = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_used);
				 dealID=(String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_id);
				 mTextStr = (String)mData.get(data_canshu).get(MarkerMenuService.JSONObj_brands_deals_giftDescription);
			}
		
		 Log.d("Rock", mData.get(data_canshu).get(DealsMenuService.JSONObj_item_giftDescription)+":mData");
//		 if ("".equals(legalText)) {
//		    	TextView_legalText.setText(R.string.TextView_strlegalText);
//			}else{
//				TextView_legalText.setText(legalText);
//			}
		 if ("false".equals(isused)) {
				Button_Buy.setVisibility(View.VISIBLE);
				TextView_xiaolian.setVisibility(View.GONE);
			}else{
				Button_Buy.setVisibility(View.GONE);
				TextView_xiaolian.setVisibility(View.VISIBLE);
				TextView_getText.setText("恭喜您:)已经兑换了此礼品");
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
		
			checkNetworkStatus();
		mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
                case 4 :   
//                		progressDialog.dismiss();
                		AlertDialog.Builder b1 = new AlertDialog.Builder(RedeemGiftActivity.this);
                		 b1.setTitle("抱歉");
    		            b1.setMessage("您已经兑换过礼品");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                	finish();
    		                }
    		            });
    		            b1.show();
        			break; 
                case 2 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                		 b1 = new AlertDialog.Builder(RedeemGiftActivity.this);
                		 b1.setTitle("恭喜");
     		            b1.setMessage("恭喜您!兑换成功.");
     		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
     		                public void onClick(DialogInterface dialog, int which){
     		                	
     		           		try {
     		           		Message message = Message.obtain(InStoreActivity.getProgressHandler(),5);
     		   				message.sendToTarget();
	     		   			 message = Message.obtain(DealsActivity.getProgressHandler(),5);
	 		   				message.sendToTarget();
     		   			} catch (Exception e) {
     		   				// TODO: handle exception
     		   			}
     		                	
     		                	finish();
     		                }
     		            });
     		            b1.show();
                	 Log.d("Rock", "sssssssssss");
//            			finish();
        			break; 
                case 3 :   
                		progressDialog.dismiss();
                		 b1 = new AlertDialog.Builder(RedeemGiftActivity.this);
                		 b1.setTitle("抱歉");
    		            b1.setMessage("有误");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            b1.show();
                    break;  
                case 6 :   
                	AlertDialog.Builder b =new AlertDialog.Builder(context);
                	b.setTitle("抱歉");
                	b.setMessage("无法连接网络");
                	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                        }
                    });
                    b.show();
                break;  
                }   
            }   
        } ; 
		
    }
	public void checkNetworkStatus(){
    	if(isNetworkAvailable()){
        }else {
    		Message msg = Message.obtain(mProgressHandler,6);
			msg.sendToTarget();
        }
    }
	  public boolean isNetworkAvailable() {
		   	 try{
		       ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		       if(connectivity == null){
		       		return false;
		       }else{
		          NetworkInfo info = connectivity.getActiveNetworkInfo();
		          if(info.isAvailable()){
		        	  return true;
		          }
		       }
		   }catch (Exception e){}
		   return false;
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
			
			Intent intent = new Intent(RedeemGiftActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "11111");//压入数据  
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
	class Button_BuyListener implements OnClickListener{
		public void onClick(View v) {
			AlertDialog.Builder b1 = new AlertDialog.Builder(RedeemGiftActivity.this);
			b1.setTitle("注意");
            b1.setMessage("你是收银员吗？只有收银员才能完成礼品赠送确认。");
            b1.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                	
                }
               
            });
            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                	Log.i("Rock", isused);
                	if ("false".equals(isused)) {
                		progressDialog = ProgressDialog.show(RedeemGiftActivity.this, "Loading...", "Please wait...", true, false);
           			 	new Thread(mthread).start();
					}else{
						 Message msg= Message.obtain(mProgressHandler,4);
	    				 msg.sendToTarget();
					}
                }
               
            });
            b1.show();
		}
	}
	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        	 Map<String,String>params1=new HashMap<String,String>()  ;
	        	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
	        	 params1.put("dealId",dealID);
	        	 params1.put("rating","like");
	        	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
	        	 params1.put("timeInterval","1");
	             String urlcons = SppaConstant.WALKIN_URL_DEALS+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/deal/"+dealID
	             +"/used"+"?apikey=BYauu6D9";
	             Log.d("Rock", urlcons);
	             deals_mservice.setRetrieveUrl(urlcons);
	             deals_mservice.retrieveDealsRate(params1);
	    			 String meta_code = deals_mservice.getCode();
	    			 Log.d("Rock", meta_code+"LLLLL");
	    			 if ("200".equals(meta_code)) {
	    				 Message msg= Message.obtain(mProgressHandler,2);
	    				 msg.sendToTarget(); 
	    			}else if("400".equals(meta_code)){
	    				 Message msg= Message.obtain(mProgressHandler,3);
	    				 msg.sendToTarget();
	    				
	    			}else{
	    				 Message msg= Message.obtain(mProgressHandler,4);
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(RedeemGiftActivity.this, url, new ImageCallback() {
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
	    private void loadImage_Button4(final String url, final Button bgimageView) {
	        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	    	 Drawable cacheImage = asyncImageLoader.loadDrawable( RedeemGiftActivity.this,url, new ImageCallback() {
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
		 private void loadImage_Button(final String url, final Button bgimageView) {
		        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
			 Drawable cacheImage = asyncImageLoader3.loadDrawable( url, new AsyncImageLoader3.ImageCallback() {
		           //请参见实现：如果第一次加载url时下面方法会执行
		           public void imageLoaded(Drawable imageDrawable ) {
		          	 
		          	 if (imageDrawable != null ) { 
		          		 bgimageView.setBackgroundDrawable(imageDrawable);
		          	 }else{
		          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.deal_default_pic);
		          		 bgimageView.setBackgroundDrawable(drawable_bg);
		          	 }
		           }
		       });
		      if(cacheImage!=null){
		      	bgimageView.setBackgroundDrawable(cacheImage);
		      }else{
		      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.deal_default_pic);
		      	bgimageView.setBackgroundDrawable(drawable_bg);
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
//				menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
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
		     Drawable cachedImage = asyncImageLoader.loadDrawable(RedeemGiftActivity.this, url, new ImageCallback() {
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
				// TODO Auto-generated method stub
				Log.d("lifecycle", "onStart()");
				String enname;
				if ("DealsActivity".equals(data_activity)) {
					// enname=	Common.stringChangeString((String) deals_mservice.getList_jobj_item().get(data_canshu).get(DealsMenuService.JSONObj_item_brandEnName)) ;
				}else{
					 enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName)) ;
//					Log.d("Rock", enname+"");
					 String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
						Log.d("Rock", enname_img_url+":bg_img_url");
						loadImage_Button4(enname_img_url, (Button)findViewById(R.id.Button_top_logo));
						
				}
				
				
				
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
