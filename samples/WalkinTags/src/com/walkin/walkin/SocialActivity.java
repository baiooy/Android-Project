package com.walkin.walkin;
/**
 * 
 */

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * @author allin
 * 
 */
public class SocialActivity extends ListActivity implements OnScrollListener {
	private static final String TAG = "System.out";
	private MyButton myhome;
	private Button Button_ME ;
	private Map<String, Object> map ;
	private List<Status> mData;
	private ArrayList<String> arraylist ; 

	
	
	
	private TextView textview01;
	private ImageButton ImageButton01;
	private PopupWindow popupWindow;
	private Button Button01;
	private Button Button02;
	private Button Button03;
	private Button social_writebuttom; 
	private static Handler exitHandler,mProgressHandler=null;
	private static Context context;
    private MyTaskMeInbiBalance mTaskInbi;  
    private MyTaskGuanzhu mTask_guanzhu;  
    private MyTaskMeqita mTask_meqita;  
    private Button Button_top_logo;
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
//	private SharedPreferences sp;
	public List<Status> status=null;
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	 protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	private MyAdapter adapter;
	private int lastItem = 0;
	private ProgressBar progressBar;
	private TextView  textView;
	private static int nowpage = 1;
	private static   int mWalkin= 1;
	private static   int mQita = 0;
	public String editaccessToken="";
	public String editaccessTokenSecret="";
	String enname="";
	private SharedPreferences sp;
	private Animation myAnimation_Alpha;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_act);
		context = this;
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
		marker_mservice.setActivity(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		Log.d("Rock", screenWidth+":screenWidth"+screenHeight+":screenHeight");
		
		TextView_Loading=(TextView) findViewById(R.id.TextView_Loading);
		progressBar_Loading=(ProgressBar) findViewById(R.id.progressBar_Loading);
	   	TextView_Loading.setVisibility(View.GONE); 
    	progressBar_Loading.setVisibility(View.GONE); 
		Bundle bundle = getIntent().getExtras();    
		enname=bundle.getString("enname");
		int pop_int=bundle.getInt("can_int");
		 sp = getSharedPreferences("user", 0);
		
		
		Button_ME=(Button) findViewById(R.id.Button_ME);
        myhome = new MyButton(this);
        Integer[] mHomeState_me = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState_me));
	    Button_ME.setText(user_mservice.getInbiBalance());
	    Button_ME.setOnClickListener(new Button_MEButtonListener());
	    Button01=(Button) findViewById(R.id.Button01);
	    Button01.setOnClickListener(new Button01Listener());
	    Integer[] mHomeState01 = { R.drawable.zuo_di,R.drawable.zuo_ding, R.drawable.zuo_ding };
	    Button01.setBackgroundDrawable(myhome.setbg(mHomeState01));
	    Button02=(Button) findViewById(R.id.Button02);
	    Integer[] mHomeState02 = { R.drawable.zhong_di,R.drawable.zhong_ding, R.drawable.zhong_ding };
	    Button02.setBackgroundDrawable(myhome.setbg(mHomeState02));
//	    Button02.setOnClickListener(new Button02Listener());
	    Button03=(Button) findViewById(R.id.Button03);
	    Integer[] mHomeState03 = { R.drawable.you_di,R.drawable.you_ding, R.drawable.you_ding };
	    Button03.setBackgroundDrawable(myhome.setbg(mHomeState03));
	    Button03.setOnClickListener(new Button03Listener());
	    social_writebuttom=(Button) findViewById(R.id.social_writebuttom);
	    social_writebuttom.setOnClickListener(new social_writebuttomListener());
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	   
	    
	    if (!"".equals(enname)) {
	    	 Button01.setText(enname);
	 	     Button03.setText(enname+"官方微博");
		}else{
			 Button01.setText("潮人聚集地");
		}
	   
	    
		   mProgressHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 1:
						break;
					case 2:
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
				
					super.handleMessage(msg);
				}
			};
	    
	    
	    
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
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		
	
    	 
    	
    	
    	
    	
    	LinearLayout searchLayout = new LinearLayout(this);
		// 水平方向的线性布局
		searchLayout.setOrientation(LinearLayout.HORIZONTAL);
		// 添加进展条
		progressBar = new ProgressBar(this);
		progressBar.setPadding(0, 0, 2, 0);
		searchLayout.addView(progressBar, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		// 添加文字，设置文字垂直居中
		textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		searchLayout.addView(textView, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));

		// 同时将进展条和加载文字显示在中间
		searchLayout.setGravity(Gravity.CENTER);

		LinearLayout loadingLayout = new LinearLayout(this);
		loadingLayout.addView(searchLayout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		loadingLayout.setGravity(Gravity.CENTER);
		// 加载到listActivity的底部
		getListView().addFooterView(loadingLayout);
		registerForContextMenu(getListView());
/*		   if ("1".equals(sp.getString("istransparentall", null))&&pop_int==4) {
	        	 Intent intent = new Intent(SocialActivity.this, TransparentAllActivity.class);
		         Bundle mBundle = new Bundle();
				 mBundle.putString("Activity", "SocialActivity");// 压入数据
				 intent.putExtras(mBundle);
				 startActivityForResult(intent, 0);
			//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
				}*/
		
		checkNetworkStatus(1);
		
	}
	public void checkNetworkStatus(int int_i){
    	if(isNetworkAvailable()){
    		if (int_i==1) {
    			mTask_guanzhu = new MyTaskGuanzhu();  
    			mTask_guanzhu.execute(); 
			}else if (int_i==2) {
				mTask_meqita = new MyTaskMeqita();  
				mTask_meqita.execute(); 
			}
        }else {
    		Message msg = Message.obtain(mProgressHandler,2);
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
	   private class MyTaskGuanzhu extends AsyncTask<String, Integer, String> {  
	         private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	         @Override  
	         protected void onPreExecute() {  
	        	 TextView_Loading.setVisibility(View.VISIBLE); 
	        	 progressBar_Loading.setVisibility(View.VISIBLE); 
	             Log.i(TAG, "onPreExecute() called");  
	         }  
	           
	         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	         @Override  
	         protected String doInBackground(String... params) {  
	             Log.i(TAG, "doInBackground(Params... params) called"); 
	         	Weibo weibo=OAuthConstant.getInstance().getWeibo();
	         	  weibo.setToken("d6e37e8f2d3ccc604606077f18211afa", "596dbee2365ba7152d28dfc7e8c541f6");
			//	weibo.setToken(editaccessToken, editaccessTokenSecret);
	            Paging paging = new Paging();
	         	paging.setCount(50);
	     	    paging.setPage(1);
	     	   String trends_name;
	     	    if (!"".equals(enname)) {
	     	    	 trends_name="#"+enname+"#";
				}else{
					 trends_name="#walkin#";
				}
	     		try{
	     			//Weibo weibo = new Weibo();
	     			//weibo.getXAuthAccessToken(sp.getString("weiboEmail", null), sp.getString("weiboPassword", null), "client_auth");
	     			status = weibo.getTrendStatus(trends_name,paging);
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
	        	 TextView_Loading.setVisibility(View.GONE); 
	        	 progressBar_Loading.setVisibility(View.GONE); 
	        	 mData = getData(status);
	        	 adapter = new MyAdapter(SocialActivity.this);
	        	 setListAdapter(adapter);
	     		 getListView().setOnScrollListener(SocialActivity.this);

	        	
	         }  
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");
	        	 
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }
	   
	   private class MyTaskMeqita extends AsyncTask<String, Integer, String> {  
	         private static final String TAG = "Rock";
			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	         @Override  
	         protected void onPreExecute() {  
	        	 TextView_Loading.setVisibility(View.VISIBLE); 
	        	 progressBar_Loading.setVisibility(View.VISIBLE); 
	             Log.i(TAG, "onPreExecute() called");  
	         }  
	           
	         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	         @SuppressWarnings("deprecation")
			@Override  
	         protected String doInBackground(String... params) {  
	             Log.i(TAG, "doInBackground(Params... params) called"); 
	             Weibo weibo=OAuthConstant.getInstance().getWeibo();
	             weibo.setToken("d6e37e8f2d3ccc604606077f18211afa", "596dbee2365ba7152d28dfc7e8c541f6");
	             try {
					Log.d("Rock",weibo.verifyCredentials()+":getUserAgent");
				} catch (WeiboException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	             Log.d("Rock",weibo.toString()+":toString");
	             Log.d("Rock",weibo.getUserId()+":getUserId");
 				 Log.d("Rock",weibo.getSource()+":getSource");
                Paging paging = new Paging();
	         	paging.setCount(200);
	     	    paging.setPage(1);
	     	    
	     	    
	 			try{
	 				 if (!"".equals(enname)) {
	 					status = weibo.getUserTimeline((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_weiboAccountName), paging);
					}else{
						status = weibo.getFriendsTimeline();
					}
//	 				 weibo.showUser(user_id)
	 				
//	 				Weibo weibo = new Weibo();
//	 				weibo.getXAuthAccessToken(sp.getString("weiboEmail", null), sp.getString("weiboPassword", null), "client_auth");
	 				
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
	        	 TextView_Loading.setVisibility(View.GONE); 
	        	 progressBar_Loading.setVisibility(View.GONE); 
	        	
	        	 
	        	 
	         	 mData = getData(status);
	        	 adapter = new MyAdapter(SocialActivity.this);
	     		 getListView().setOnScrollListener(SocialActivity.this);
     			 setListAdapter(adapter);
	         }  
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");
	        	 
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }
	   
		protected void onStart() {
			// TODO Auto-generated method stub
				 if (!"".equals(enname)) {
			String enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName));
			String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
			loadImage_Button4(enname_img_url, (Button)findViewById(R.id.Button_top_logo));
				 }
			super.onStart();
		}
		 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		    private void loadImage_Button4(final String url, final Button bgimageView) {
		        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		    	 Drawable cacheImage = asyncImageLoader.loadDrawable( SocialActivity.this,url, new ImageCallback() {
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
		 
	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(SocialActivity.this, MeActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
    protected void onRestart() {
		 mTaskInbi = new MyTaskMeInbiBalance();  
		mTaskInbi.execute(); 
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		super.onRestart();
	}
    private class MyTaskMeInbiBalance extends AsyncTask<String, Integer, String> {  

        protected String doInBackground(String... params) {  
           
       	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
	          +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
	          Log.d("Rock", urlcons);
	          user_mservice.setRetrieveUrl(urlcons);
	          user_mservice.retrieveUserQueryInfo();
            return null;  
        }  
        
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
       	 Button_ME.setText(user_mservice.getInbiBalance());
        }  
          
    }
	class social_writebuttomListener implements OnClickListener{
		public void onClick(View v) {
			
			if(isNetworkAvailable()){
				Intent intent = new Intent(SocialActivity.this, SocialSendWeiboActivity.class);
	            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
				Bundle mBundle = new Bundle();  
				mBundle.putString("Data", "identification");//压入数据  
				intent.putExtras(mBundle);  
	            startActivity(intent);
			}else{
				Message msg = Message.obtain(mProgressHandler,2);
				msg.sendToTarget();
			}
			
			
			
		}
	}
	class Button01Listener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SocialActivity.this,R.anim.my_alpha_action);
			Button01.startAnimation(myAnimation_Alpha);
			
			nowpage=1;
			checkNetworkStatus(1);
			mWalkin=1;
			mQita=0;
		}
	}
	class Button03Listener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SocialActivity.this,R.anim.my_alpha_action);
			Button03.startAnimation(myAnimation_Alpha);
			nowpage=1;
			checkNetworkStatus(2);
	
			mWalkin=0;
			mQita=1;
			progressBar.setVisibility(View.VISIBLE);
			textView.setVisibility(View.VISIBLE);
		}
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
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		if(isNetworkAvailable()){
		final String userid =  (int)mData.get(position).getUser().getId()+"";
		final String email = (String)mData.get(position).getUser().getName();
		Date CreatedDate = (Date)mData.get(position).getCreatedAt();
		SimpleDateFormat df=new SimpleDateFormat("dd日HH:mm");
		String times=df.format(CreatedDate);
		final String neirong = (String)mData.get(position).getText();
		final URL ImageView01_getProfileImageURL = mData.get(position).getUser().getProfileImageURL();
		final String Mid =mData.get(position).getMid();
		final String ImageView02_Bmiddle_pic =mData.get(position).getBmiddle_pic();
		String ImageView01_ProfileImageURL = ImageView01_getProfileImageURL+"";
		Log.d("Rock", Mid+":ImageView02_getBmiddle_pic");
		String Retweeted_status = null;
		String Retweeted_Bmiddle_pic = null;
		try {
			Retweeted_status=(String)mData.get(position).getRetweeted_status().getText();
			Retweeted_Bmiddle_pic=(String)mData.get(position).getRetweeted_status().getBmiddle_pic();
		} catch (Exception e) {
			// TODO: handle exception
		}
		 Intent intent = new Intent(SocialActivity.this, SocialDetailActivity.class);
		 Bundle mBundle = new Bundle();  
//		Log.d("Rock", position+"position");
		 arraylist = new ArrayList<String>(); 
		 arraylist.add(userid);
		 arraylist.add(email);
		 arraylist.add(times);
		 arraylist.add(neirong);
		 arraylist.add(ImageView01_ProfileImageURL);
		 arraylist.add(ImageView02_Bmiddle_pic);
		 arraylist.add(Mid);
		 arraylist.add(Retweeted_status);
		 arraylist.add(Retweeted_Bmiddle_pic);
		 mBundle.putStringArrayList("Data", arraylist);//压入数据  
		 intent.putExtras(mBundle);  
		 Log.d("Rock", arraylist+":ImageView02_getBmiddle_pic");
		 startActivityForResult(intent, 0);
		}else{
			Message msg = Message.obtain(mProgressHandler,2);
			msg.sendToTarget();
		}
//		final String times = (String)mData.get(position).get("times");
//		final String name = (String)mData.get(position).get("name");
//		final String neirong = (String)mData.get(position).get("neirong");
//		final int  state  ;
//		if (mData.get(position).get("viewBtn").equals("2")) {
//			state=2;
//		}else {
//			state=1;
//		}
//		openPopupwin();		
	}
	
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			int count = 10 ;
			try {
				
				if(mData.size()<10*nowpage){
					count=mData.size();
					progressBar.setVisibility(View.GONE);
					textView.setVisibility(View.GONE);
				}else{
					count=10*nowpage;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return count;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public final class ViewHolder{
			
			public TextView email;
			public TextView times;
			public TextView neirong;
			public ImageView ImageView01;
			public TextView TextView_Retweeted;
			public ImageView ImageView_Retweeted;
			public LinearLayout LinearLayout_Retweeted;
		}
		    
		public View getView(int position, View convertView, ViewGroup parent) {
			 boolean int_num = false;
			String Retweeted_status = null;
			String Retweeted_Bmiddle_pic = null;
			String email = null;
			Date CreatedDate= null;
//			String Mid= null;
			String times = null;
			
			try {
				Retweeted_status=(String)mData.get(position).getRetweeted_status().getText();
				Retweeted_Bmiddle_pic=(String)mData.get(position).getRetweeted_status().getBmiddle_pic();
				 int_num=true;
			} catch (Exception e) {
				 int_num=false;
			}
			
			try {
				  email = (String)mData.get(position).getUser().getName();
				 CreatedDate = (Date)mData.get(position).getCreatedAt();
				 times	=VeDate.getHowLongStr(CreatedDate);
			} catch (Exception e) {
			}
			final String neirong = (String)mData.get(position).getText();
			final URL ImageView01_getProfileImageURL = mData.get(position).getUser().getProfileImageURL();
			
			final String ImageView02_getBmiddle_pic =mData.get(position).getBmiddle_pic();
			String ImageView01_getThumbnail_pic = ImageView01_getProfileImageURL+"";
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.social_detail, null);
				//holder.ImageView01 = (ImageView)convertView.findViewById(R.id.ImageView01);
				loadImage5(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView01));
				holder.email = (TextView)convertView.findViewById(R.id.email);
				holder.times = (TextView)convertView.findViewById(R.id.times);
				holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
				holder.email.setText(email);
				holder.times.setText(times);
				holder.neirong.setText(neirong);
				
				holder.TextView_Retweeted = (TextView)convertView.findViewById(R.id.TextView_Retweeted);
				holder.TextView_Retweeted.setText(Retweeted_status);
				holder.LinearLayout_Retweeted = (LinearLayout)convertView.findViewById(R.id.LinearLayout_Retweeted);
				if (!"".equals(ImageView02_getBmiddle_pic)) {
					//Log.i("Rock", "1111111");
					loadImage5(ImageView02_getBmiddle_pic, (ImageView)convertView.findViewById(R.id.ImageView02));
				}else{
					//Log.i("Rock", "22222");
					loadImage5("", (ImageView)convertView.findViewById(R.id.ImageView02));
				}
				loadImage5(Retweeted_Bmiddle_pic, (ImageView)convertView.findViewById(R.id.ImageView_Retweeted));
				if (int_num==true) {
					holder.LinearLayout_Retweeted.setVisibility(View.VISIBLE);
					
				}else{
					holder.LinearLayout_Retweeted.setVisibility(View.GONE);
				}
			}else {
				
					holder=new ViewHolder();  
					//holder.ImageView01 = (ImageView)convertView.findViewById(R.id.ImageView01);
					loadImage5(ImageView01_getThumbnail_pic, (ImageView)convertView.findViewById(R.id.ImageView01));
					holder.email = (TextView)convertView.findViewById(R.id.email);
					holder.email.setText(email);
					holder.times = (TextView)convertView.findViewById(R.id.times);
					holder.times.setText(times);
					holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
					holder.neirong.setText(neirong);
					
					holder.TextView_Retweeted = (TextView)convertView.findViewById(R.id.TextView_Retweeted);
					holder.TextView_Retweeted.setText(Retweeted_status);
					holder.LinearLayout_Retweeted = (LinearLayout)convertView.findViewById(R.id.LinearLayout_Retweeted);
					if (!"".equals(ImageView02_getBmiddle_pic)) {
						//Log.i("Rock", "333333333");
						loadImage5(ImageView02_getBmiddle_pic, (ImageView)convertView.findViewById(R.id.ImageView02));
					}else{
						//Log.i("Rock", "444444444");
						loadImage5("", (ImageView)convertView.findViewById(R.id.ImageView02));
					}
					loadImage5(Retweeted_Bmiddle_pic, (ImageView)convertView.findViewById(R.id.ImageView_Retweeted));
					if (int_num==true) {
						holder.LinearLayout_Retweeted.setVisibility(View.VISIBLE);
					}else{
						holder.LinearLayout_Retweeted.setVisibility(View.GONE);
					}
			}
			return convertView;
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

	private void openPopupwin() {
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View menuView = (View) mLayoutInflater.inflate(
				R.layout.social_gridview_pop, null, true);
		textview01 = (TextView) menuView.findViewById(R.id.textview01);
		ImageButton01 = (ImageButton) menuView.findViewById(R.id.ImageButton01);
		ImageButton01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});
//		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		textview01.requestFocus();
	/*	menuGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 11) {
					popupWindow.dismiss();
				}
			}
		});*/
		textview01.setOnKeyListener(new OnKeyListener() {// 焦点到了gridview上，所以需要监听此处的键盘事件。否则会出现不响应键盘事件的情况
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
		popupWindow.showAtLocation(findViewById(R.id.listLinearLayout), Gravity.CENTER
				| Gravity.CENTER, 0, 0);
		popupWindow.update();
	}
	class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}   
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
	/**
	 * firstVisbleItem 第一个可见的item visibleItemCount 可见的Item个数 totalItemCount 总的个数
	 * lastItem 可见状态中，最后一个item
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.i(TAG, "firstVisibleItem=" + firstVisibleItem);
		Log.i(TAG, "visibleItemCount=" + visibleItemCount);
		Log.i(TAG, "totalItemCount=" + totalItemCount);

		lastItem = firstVisibleItem + visibleItemCount - 1;
		Log.i(TAG, "lastItem:" + lastItem);
	}

	/**
	 * 如果视图正在滚动，此方法将被调用之前，滚动下一帧呈现。特别是，它会被调用之前调用getView
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (lastItem == adapter.getCount()
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			nowpage++;
			/*if (mWalkin==1) {
				mTask_guanzhu = new MyTaskGuanzhu();  
				mTask_guanzhu.execute(); 
			}
			if (mQita==1) {
				mTask_meqita = new MyTaskMeqita();  
				mTask_meqita.execute(); 
			}*/
			
			//mData.addAll(getData(status));
			adapter.notifyDataSetChanged();
		}
	}
	private List<Status> getData(List<Status> status2) {
	
		return status2;
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
