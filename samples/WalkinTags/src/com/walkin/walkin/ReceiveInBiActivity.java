package com.walkin.walkin;






import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReceiveInBiActivity extends Activity{
	private TextView TextView_inbi_num ,TextView_congriation;
//	private MyTask mTask;  
	private ImageView ImageView_inbi;
	private static Context context;
	private TextView textview01;
	private ImageButton ImageButton01;
	private ImageButton ImageButton02;
	private PopupWindow popupWindow;
	private Animation myAnimation_Scale;
	private LinearLayout LinearLayout_inbi_congriation;
	String TextView_bgimg_state,data_type;
	String markerID ,event=null;
	String data_enName;
//	private static Handler mProgressHandler=null;
	private SharedPreferences sp;
	public String editaccessToken;
	public String editaccessTokenSecret;
	protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setTheme(R.style.Transparent);  
        setContentView(R.layout.receiveinbi_act);
        
    	context = this;
		defaults_mservice.setActivity(this); 
		marker_mservice.setActivity(this); 
		Bundle bundle = getIntent().getExtras();    
    	
		data_type =bundle.getString("type");
    	 markerID=bundle.getString("markerID");
    	 event=bundle.getString("event");
    	Log.e("Rock", markerID+":markerID"+data_type+":data_type");
    	
    /*	if("400".equals(data_type)&&"".equals(markerID)){
        	Log.e("Rock", markerID+":markerID"+data_type+":data_type");
	 }*/
    	
    		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret",Weibo.CONSUMER_SECRET);
    	    sp = getSharedPreferences("user", 0);
			editaccessToken = sp.getString("accessToken", null);
			editaccessTokenSecret = sp.getString("accessTokenSecret", null);
			if ("".equals(editaccessToken)||editaccessToken==null) {
				/*Intent intent = new Intent(ReceiveInBiActivity.this,AuthorizationAct.class);
				// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
				Bundle mBundle = new Bundle();
				mBundle.putString("Data", "wuweibo");// 压入数据
				intent.putExtras(mBundle);
				startActivityForResult(intent, 0);
				ReceiveInBiActivity.this.finish();
				Message message = Message.obtain(InActivity.getExitHandler(),0);
				message.sendToTarget();*/
			//	return;
			}else{
				if ("1".equals(sp.getString("ischeck", null))) {
					new Thread(mthread2).start();
				}
				
			}
			
			
			
			
			
			
			
			
			
    	
		 data_enName=Common.stringChangeString(bundle.getString("enName"));
		String data_inbiEarned=bundle.getString("inbiEarned");
		
		Log.e("Rock", data_inbiEarned+":data_inbiEarned");
	TextView_bgimg_state = (SppaConstant.WALKIN_URL_BASE+"brands/"+data_enName+"/ios/"+data_enName+defaults_mservice.getBrandBG()+".jpg").toLowerCase();
       LinearLayout_inbi_congriation = (LinearLayout) findViewById(R.id.LinearLayout_inbi_congriation); 
       
		myAnimation_Scale= AnimationUtils.loadAnimation(this,R.anim.my_scale_action);
		LinearLayout_inbi_congriation.startAnimation(myAnimation_Scale);
   	   TextView_inbi_num=( TextView) findViewById(R.id.TextView_inbi_num);
   	TextView_congriation=( TextView) findViewById(R.id.TextView_congriation);
   	ImageView_inbi=( ImageView) findViewById(R.id.ImageView_inbi);
   	   
  	   TextView_inbi_num.setOnClickListener(new TextView_inbi_numListener());
  	   
  	   if ("200".equals(data_type)||"inbi".equals(data_type)) {
  		   	TextView_inbi_num.setText(data_inbiEarned);
			TextView_congriation.setText("您赢了");
			Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.congriation_buttom);
	  		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
		}else if("1007".equals(data_type)){
			TextView_inbi_num.setText("距离实在是太远了，Sorry");
			TextView_inbi_num.setTextSize(15);
			TextView_congriation.setText("太远了");
			Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
	  		ImageView_inbi.setBackgroundDrawable(drawable_inbi_im);
			Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.congriation_buttom);
	  		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
	 }else if("deal".equals(data_type)){
	  		 TextView_inbi_num.setText("祝贺你获得一个新的优惠。\n请进入优惠页面查找。");
	   		TextView_inbi_num.setTextSize(18);
	   		//Drawable drawable_deals = getResources().getDrawable(R.drawable.common_place_deals);
	   		//TextView_congriation.setBackgroundDrawable(drawable_deals);
	   		TextView_congriation.setText("");
	   		TextView_congriation.setTextSize(25);
	   		Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
	   		ImageView_inbi.setBackgroundDrawable(drawable_inbi_im);
	   		Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.getdeal);
	   		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
		}else if("game".equals(data_type)){
			TextView_inbi_num.setText("游戏");
	   		TextView_inbi_num.setTextSize(18);
	   		//Drawable drawable_deals = getResources().getDrawable(R.drawable.common_place_deals);
	   		//TextView_congriation.setBackgroundDrawable(drawable_deals);
	   		TextView_congriation.setText("");
	   		TextView_congriation.setTextSize(25);
	   		Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
	   		ImageView_inbi.setBackgroundDrawable(drawable_inbi_im);
	   		Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.game_buttom);
	   		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
		}else if("1005".equals(data_type)){
			TextView_inbi_num.setText("每日只能获得1次in币奖励。\n欢迎你明天再来:)");
	  		TextView_inbi_num.setTextSize(15);
	  		TextView_congriation.setText("欢迎回来");
	  		TextView_congriation.setTextSize(25);
	  		Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
	  		ImageView_inbi.setBackgroundDrawable(drawable_inbi_im);
	  		Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.backharte);
	  		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
		 }else{
			TextView_inbi_num.setText("每日只能获得1次in币奖励。\n欢迎你明天再来:)");
	  		TextView_inbi_num.setTextSize(15);
	  		TextView_congriation.setText("欢迎回来");
	  		TextView_congriation.setTextSize(25);
	  		Drawable drawable_inbi_im = getResources().getDrawable(R.drawable.none_color);
	  		ImageView_inbi.setBackgroundDrawable(drawable_inbi_im);
	  		Drawable drawable_inbi_congriation = getResources().getDrawable(R.drawable.backharte);
	  		LinearLayout_inbi_congriation.setBackgroundDrawable(drawable_inbi_congriation);
		}
       
  	/* mProgressHandler = new Handler() {   
         public void handleMessage(Message msg) { 
             switch (msg.what){   
            
             case 2 :   
             	AlertDialog.Builder b = new AlertDialog.Builder(ReceiveInBiActivity.this);
             	 b.setTitle("恭喜");
                 b.setMessage("发送成功");
                 b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int which){
                     	finish();
                    	Message message = Message.obtain(InActivity.getExitHandler(),0);
            			message.sendToTarget();
                     }
                 });
                 b.show();
     			break; 
             case 3 :   
             	 b = new AlertDialog.Builder(ReceiveInBiActivity.this);
					b.setTitle("抱歉");
                 b.setMessage("发送出错");
                 b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int which){
                     }
                 });
                 b.show();
                 break;  
             case 1 :   
             	 b = new AlertDialog.Builder(ReceiveInBiActivity.this);
					b.setTitle("抱歉");
                 b.setMessage("发送内容为空");
                 b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int which){
                     }
                 });
                 b.show();
                 break;  
             }   super.handleMessage(msg);
			}
         
     } ; */    
  	   
  	   
  	   
  	   
//       mTask = new MyTask();  
//       mTask.execute();  
//       
   
    
       // TextView03=(TextView) findViewById(R.id.TextView03);
       // TextView03.setText("在店内体验互动游戏,"+"\n"+"获得更多优惠和奖励!");
       // Intent intent = getIntent();
		//Bundle b =intent.getExtras();
		//if (b.getString("Data").equals("0")) {
		//	Drawable drawable02 = getResources().getDrawable(R.drawable.triumphsexybackground);
		//	RelativeLayout_bg.setBackgroundDrawable(drawable02);
		//}else{
		//	Drawable drawable02 = getResources().getDrawable(R.drawable.zhong_ding);
		//	RelativeLayout_bg.setBackgroundDrawable(drawable02);
		//}
//       new Thread(mthread).start();
  	   
  	   
		try {
			if ("true".equals(event)) {
			if (marker_mservice.getList_item_newNotifications().size()!=0) {
				for (int i = 0; i < marker_mservice.getList_item_newNotifications().size(); i++) {
					Log.e("Rock",(String)marker_mservice.getList_item_newNotifications().get(i).get(MarkerMenuService.JSONObj_item_newNotifications_type)+":JSONObj_item_newNotifications_type");
					if ("badge".equals((String)marker_mservice.getList_item_newNotifications().get(i).get(MarkerMenuService.JSONObj_item_newNotifications_type))) {
						Intent intent=new Intent();
						intent.setClass(ReceiveInBiActivity.this, TransparentBadgesActivity.class);
						Bundle mBundle=new  Bundle();
						mBundle.putInt("Data", i);//压入数据  
						intent.putExtras(mBundle);
						startActivityForResult(intent, 0);
					}
				}
			}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		}
    
/*	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        	 try {
					Thread.sleep(3000);
				
					// TextView_inbi_num=(Button) findViewById(R.id.TextView_inbi_num);
				    //    TextView_inbi_num.setOnClickListener(new TextView_inbi_numListener());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	         }
	     };*/
	   /*  private class MyTask extends AsyncTask<String, Integer, String> {  
	         private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	         @Override  
	         protected void onPreExecute() {  
//	           textView.setText("loading...");  
	             Log.i(TAG, "onPreExecute() called");  
	           //  LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	           
	         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	         @Override  
	         protected String doInBackground(String... params) {  
	             Log.i(TAG, "doInBackground(Params... params) called");  
	             return null;  
	         }  
	           
	         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	         @Override  
	         protected void onPostExecute(String result) {  
	        	 Log.i(TAG, "onPostExecute(Result result) called");  
					// LinearLayout_inbi_congriation.setVisibility(View.VISIBLE);
					// loadImage4(TextView_bgimg_state,(RelativeLayout)findViewById(R.id.RelativeLayout_bg));
					  	Log.d("Rock", TextView_bgimg_state);
	         }  
	           
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");  
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }*/       
	     
	     
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
		 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		    private void loadImage4(final String url, final RelativeLayout bgimageView) {
		        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
		        Drawable cachedImage = asyncImageLoader.loadDrawable(ReceiveInBiActivity.this, url, new ImageCallback() {
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
    class TextView_inbi_numListener implements OnClickListener{
		public void onClick(View v) {
			
			if(isNetworkAvailable()){
	       
		
			
		
			String	mRedemptionType = null;
        	
        	if(!"400".equals(data_type)&&!"game".equals(data_type)&&!"inbi".equals(data_type)){
        		try {
        			mRedemptionType=(String)marker_mservice.getList_jobj_item_deals().get(0).get(MarkerMenuService.JSONObj_brands_deals_redemptionType);
				} catch (Exception e) {
					// TODO: handle exception
					mRedemptionType="";
				}
        	
        	}
        	Log.e("Rock", data_type+":data_type");
        	
        	if ("deal".equals(data_type)&&!"gift".equals(mRedemptionType)) {
        		Intent intent = new Intent(ReceiveInBiActivity.this, InStoreDetailActivity.class);
    			Bundle mBundle = new Bundle();  
    			mBundle.putInt("Data", 0);//压入数据  
    			intent.putExtras(mBundle);  
    			startActivityForResult(intent, 0);
                finish();
			}else if ("deal".equals(data_type)&&"gift".equals(mRedemptionType)){
				Intent intent = new Intent(ReceiveInBiActivity.this, RedeemGiftActivity.class);
    			Bundle mBundle = new Bundle();  
    			mBundle.putInt("Data", 0);//压入数据  
    			intent.putExtras(mBundle);  
    			startActivityForResult(intent, 0);
                finish();
			}else if("game".equals(data_type)){
				Intent intent = new Intent(ReceiveInBiActivity.this, GameInfoActivity.class);
    			Bundle mBundle = new Bundle();  
    			mBundle.putInt("Data", 0);//压入数据  
    			intent.putExtras(mBundle);  
    			startActivityForResult(intent, 0);
                finish();
			 }else if("400".equals(data_type)||"".equals(markerID)){
	                finish();
			 }else if("inbi".equals(data_type)&&"".equals(markerID)){
	                finish();
			 }else if("1007".equals(data_type)){
	                finish();
			 }else if("true".equals(event)){
				    finish();
			 } else{
				Log.d("Rock", "Intent intent = new Intent(ReceiveInBiActivity.this, StoreHomeActivity.class);");
				Intent intent = new Intent(ReceiveInBiActivity.this, StoreHomeActivity.class);
	            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
				Bundle mBundle = new Bundle();  
				mBundle.putString("markerID", markerID);//压入数据  
				mBundle.putString("enName", data_enName);//压入数据  
				intent.putExtras(mBundle);  
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.fade, R.anim.hold);
	            finish();
			}
        	 if(!"true".equals(event)){
        		Message message = Message.obtain(InActivity.getExitHandler(),0);
   			 	message.sendToTarget();
        	 }
        	 Log.d("Rock", "Message.obtain(InActivity.getExitHandler(),0);");
         	 
			 }else {
				 AlertDialog.Builder b =new AlertDialog.Builder(context);
             	b.setTitle("抱歉");
             	b.setMessage("无法连接网络");
             	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int which){
                     }
                 });
                 b.show();
		        }
		}
	}
    

	private final Runnable mthread2 = new Runnable() {
        public void run() {
       	 String editStr ="我在#"+data_enName+"#签到";
			 Weibo weibo=OAuthConstant.getInstance().getWeibo();
			weibo.setToken(editaccessToken, editaccessTokenSecret);
			try {
				String strText = null;
				Status status = null;
				if (!"".equals(editStr)) {
					strText = editStr+"\n"+"#walkin_checkin#";
						 status= weibo.updateStatus(strText);
				    	//Message msg= Message.obtain(mProgressHandler,2);
						//msg.sendToTarget();
				}else{
					//Message msg= Message.obtain(mProgressHandler,3);
					//msg.sendToTarget();
				}
				} catch (WeiboException e) {
				e.printStackTrace();
				if (e.getStatusCode() == 400) {
					// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
				         	//Message msg= Message.obtain(mProgressHandler,3);
							//msg.sendToTarget();
					
				} else if (e.getStatusCode() == 403) {
					// 帐号密码错误
				}
				
				return;
			}
	  
        }
    };
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			finish();
			Message message = Message.obtain(InActivity.getExitHandler(),0);
			message.sendToTarget();
		
			return true;
		} else {
			return super.onKeyDown(keyCode, event);

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
