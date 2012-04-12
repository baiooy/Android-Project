package com.walkin.walkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weibo4android.Count;
import weibo4android.Weibo;
import weibo4android.WeiboException;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.UserMenuService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SocialDetailActivity extends Activity{
	private MyButton myhome;
	private Button Button_ME,Button_repost ,Button_comments,Button_top_logo;
	private Button goback;
	private ImageView ImageView_pic,ImageView_img;
	private TextView TextView_name,TextView_neirong,TextView_time,TextView_Retweeted,TextView_forward,TextView_comment;
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
    private  String userid;
    private  String Mid;
    
	private static Context context;
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private List<String> arraylist_data = new ArrayList<String>(); 
    private MyTask mTask;  
	private SharedPreferences sp;
	public String editaccessToken;
	public String editaccessTokenSecret;
	
	private LinearLayout LinearLayout_Retweeted;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialdetail_act);
        
        context = this;
		user_mservice.setActivity(this);
        
        
        Button_ME=(Button) findViewById(R.id.Button_ME);
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
        Button_ME.setText(user_mservice.getInbiBalance());
        Button_ME.setOnClickListener(new Button_MEListener());
        ImageView_img=(ImageView) findViewById(R.id.ImageView_img);
        ImageView_pic=(ImageView) findViewById(R.id.ImageView_pic);
        TextView_name=(TextView) findViewById(R.id.TextView_name);
        TextView_neirong=(TextView) findViewById(R.id.TextView_neirong);
        TextView_time=(TextView) findViewById(R.id.TextView_time);
        TextView_Retweeted = (TextView)findViewById(R.id.TextView_Retweeted);
        TextView_forward= (TextView)findViewById(R.id.TextView_forward);
        TextView_comment= (TextView)findViewById(R.id.TextView_comment);
        LinearLayout_Retweeted = (LinearLayout)findViewById(R.id.LinearLayout_Retweeted);
        Bundle bundle = getIntent().getExtras();    
		
		arraylist_data=bundle.getStringArrayList("Data");
		
		
		
		 userid =arraylist_data.get(0);
		String name =arraylist_data.get(1);
		String times =arraylist_data.get(2);
		String neirong =arraylist_data.get(3);
		String ImageView01_ProfileImageURL =arraylist_data.get(4);
		String ImageView02_Bmiddle_pic =arraylist_data.get(5);
		Mid =arraylist_data.get(6);
		String Retweeted_status =arraylist_data.get(7);
		String Retweeted_Bmiddle_pic =arraylist_data.get(8);
		TextView_name.setText(name);
		TextView_time.setText("时间:"+times);
		TextView_neirong.setText(neirong);
		TextView_Retweeted.setText(Retweeted_status);
		Log.e("Rock", Retweeted_status+":Retweeted_status");
		if (Retweeted_status== null) {
			LinearLayout_Retweeted.setVisibility(View.GONE);
			TextView_Retweeted.setVisibility(View.GONE);
		}else{
			LinearLayout_Retweeted.setVisibility(View.VISIBLE);
			TextView_Retweeted.setVisibility(View.VISIBLE);
		}
		
		
		loadImage5(ImageView01_ProfileImageURL, (ImageView)findViewById(R.id.ImageView_pic));
		loadImage5(ImageView02_Bmiddle_pic, (ImageView)findViewById(R.id.ImageView_img));
		loadImage5(Retweeted_Bmiddle_pic, (ImageView)findViewById(R.id.ImageView_Retweeted));
		
		
	
		Button_repost=(Button) findViewById(R.id.Button_repost);
		Button_repost.setOnClickListener(new Button_repostListener());
        
		Button_comments=(Button) findViewById(R.id.Button_comments);
		Button_comments.setOnClickListener(new Button_commentsListener());
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    sp = getSharedPreferences("user", 0);
		  //  EditText_Email.setText(sp.getString("weiboEmail", null));
		   // EditText_Password.setText(sp.getString("weiboPassword", null));
			editaccessToken = sp.getString("accessToken", null);
			editaccessTokenSecret = sp.getString("accessTokenSecret", null);
			
			if ("".equals(editaccessToken)||editaccessToken==null) {
				
				Intent intent = new Intent(SocialDetailActivity.this,AuthorizationAct.class);
				// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
				Bundle mBundle = new Bundle();
				mBundle.putString("Data", "wuweibo");// 压入数据
				intent.putExtras(mBundle);
				startActivityForResult(intent, 0);
				SocialDetailActivity.this.finish();
				return;
			}
			
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    mTask = new MyTask();  
        mTask.execute(); 
       
        
       /* context = this;
		user_mservice.setActivity(this);
		deals_mservice.setActivity(this);
		brands_mservice.setActivity(this);
		locationinfo.setActivity(this);
		
        Button_Buy=(Button) findViewById(R.id.Button_Buy);
        TextView_logo=(TextView) findViewById(R.id.TextView_logo);
        TextView_youhui=(TextView) findViewById(R.id.TextView_youhui);
        TextView_jieshao=(TextView) findViewById(R.id.TextView_jieshao);
        TextView_shortDescription=(TextView) findViewById(R.id.TextView_shortDescription);
        TextView_validUntil=(TextView) findViewById(R.id.TextView_validUntil);
        Button_ME=(Button) findViewById(R.id.Button_ME);
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
    	Button_ME.setText(user_mservice.getInbiBalance());
        Button_ME.setOnClickListener(new Button_MEListener());
        Button_Buy.setOnClickListener(new Button_BuyListener());
        Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    
	    Bundle bundle = getIntent().getExtras();    
		int data_canshu=bundle.getInt("Data");
		mData = getData(deals_mservice.getList_jobj_item());
		final String mEnName = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_brandEnName);
		final String mRedemptionType = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_redemptionType);
		mBrandId = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_brandId);
		final String mTextStr ;
		if("bogof".equals(mRedemptionType)){
			mTextStr="买一送一";
		}else if("discount".equals(mRedemptionType)){
			mTextStr= Math.round(Double.parseDouble((String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_discount))*100)+"% 优惠";
		}else if("gift".equals(mRedemptionType)){
			mTextStr = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_giftDescription);
		}else{
			mTextStr="空";
		}
		final String mDetailDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_detailDescription);
		final String imageUrl = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_imageUrl);
		final String mShortDescription = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_shortDescription);
		final String mValidUntil = (String)mData.get(data_canshu).get(DealsMenuService.JSONObj_item_validUntil);
		String strValidUntil;
		if ("".equals(mValidUntil)) {
			 strValidUntil ="暂未解锁";
		}else{
			 strValidUntil ="今日至"+Common.DateToStr(Common.StrToDate(mValidUntil))+"有效";
		}
//		Log.d("Rock", mValidUntil);
		TextView_logo.setText(mEnName);
		TextView_youhui.setText(mTextStr);
		TextView_jieshao.setText(mDetailDescription);
		TextView_shortDescription.setText(mShortDescription);
		TextView_validUntil.setText(strValidUntil);
		loadImage4(imageUrl, (ImageView)findViewById(R.id.ImageView_item));
		
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
     				intent.setClass(SocialDetailActivity.this, DealsShowDetailActivity.class);
     				Bundle bundle=new  Bundle();
     				String str1="aaaaaa";
     				bundle.putString("Data", str1);
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
                		AlertDialog.Builder b1 = new AlertDialog.Builder(SocialDetailActivity.this);
    		            b1.setMessage("有误");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            b1.show();
                    break;  
                }   
            }   
        } ; */
		
    }
	protected void onRestart() {
		   mTask = new MyTask();  
	        mTask.execute(); 
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		super.onRestart();
	}
    
    private class MyTask extends AsyncTask<String, Integer, String> {  
        private static final String TAG = "Rock";
        String str_Comments,str_Rt;
		//onPreExecute方法用于在执行后台任务前做一些UI操作  
        @Override  
        protected void onPreExecute() {  
            Log.i(TAG, "onPreExecute() called");  
        }  
          
        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
        @Override  
        protected String doInBackground(String... params) {  
            Log.i(TAG, "doInBackground(Params... params) called"); 
           
            Weibo weibo=OAuthConstant.getInstance().getWeibo();
			weibo.setToken(editaccessToken, editaccessTokenSecret);
           /* Paging paging = new Paging();
         	paging.setCount(200);
     	    paging.setPage(1);
     	    String trends_name="#walkin#";*/
     		try{
     			//Weibo weibo = new Weibo();
     			//weibo.getXAuthAccessToken(sp.getString("weiboEmail", null), sp.getString("weiboPassword", null), "client_auth");
     			List<Count> counts = weibo.getCounts(Mid);
     			for (Count count:counts){
     			    //System.out.println(Mid+count.getComments()+" - "+count.getRt());
     			   str_Comments=count.getComments()+"";
     			   str_Rt=count.getRt()+"";
     			}
     		}catch (WeiboException e) {
     			e.printStackTrace();
     			if (e.getStatusCode() == 400) {
     				// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
     			} else if (e.getStatusCode() == 403) {
     				// 帐号密码错误
     			}
     		}
            return null;  
        }  
        
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
       	 Log.i(TAG, "onPostExecute(Result result) called");  
       	 
       	 
       	 
       	TextView_forward.setText("转发("+str_Rt+")"); 
       	TextView_comment.setText("评论("+str_Comments+")"); 
     
        }  
          
        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override  
        protected void onCancelled() {  
       	 Log.i(TAG, "onCancelled() called");
       	 
//            LinearLayout_inbi_congriation.setVisibility(View.GONE);
        }  
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
			
			Intent intent = new Intent(SocialDetailActivity.this, MeActivity.class);
			 
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
	class Button_commentsListener implements OnClickListener{
 		public void onClick(View v) {
 			//评论
 			Intent intent = new Intent(SocialDetailActivity.this, SocialCommentAndRepostActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "Comment");//压入数据  
			mBundle.putStringArrayList("arraylist", (ArrayList<String>) arraylist_data);
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
 		}
 	}  
	class Button_repostListener implements OnClickListener{
		public void onClick(View v) {
			//转发
			Intent intent = new Intent(SocialDetailActivity.this, SocialCommentAndRepostActivity.class);
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "Repost");//压入数据  
			mBundle.putStringArrayList("arraylist", (ArrayList<String>) arraylist_data);
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
			//	 String encodePass =RegExpValidator.encode(s.getBytes());
			//	 String editEmailStr_test="kevin.hu@gmail.com";
	        	// http://122.195.135.91:2861/api/brands/ 2?brandId=2
/*				 String urlcons = SppaConstant.WALKIN_URL_BRANDSID+mBrandId+"?brandId="+mBrandId+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
	             +"&"+"ll="+locationinfo.getLatitude()+","+locationinfo.getLongitude()+"&apikey=BYauu6D9";
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
		  
*/	         }
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(SocialDetailActivity.this, url, new ImageCallback() {
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
