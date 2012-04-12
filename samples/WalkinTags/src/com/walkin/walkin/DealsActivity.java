package com.walkin.walkin;
/**
 * 
 */

import java.util.HashMap;
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
import com.walkin.service.DealsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author allin
 * 
 */
public class DealsActivity extends ListActivity {
	
	private MyButton myhome;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
	private Button Button_Refresh;
	private Button Button_ME ;
	private Button Button_top_logo;
	private  ImageView deals_imageview_02,deals_imageview_03;
	
	
	private List<Map<String, Object>> mData;
	  private static Handler mProgressHandler=null;
    private ProgressDialog progressDialog; 
	protected final static DealsMenuService deals_mservice = DealsMenuService.getInstance();
	private static Context context;
    private MyTask mTask;     
    private MyTaskMeInbiBalance mTaskInbi;  
    private static Handler ListDealsHandler=null;
	private static Handler exitHandler=null;
//    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	private SharedPreferences sp;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deals_act);
		
		context = this;
//		defaults_mservice.setActivity(this);
		deals_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
		 sp = getSharedPreferences("user", 0);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		Log.d("Rock", screenWidth+":screenWidth"+screenHeight+":screenHeight");
		
		Bundle bundle = getIntent().getExtras();    
		int pop_int=bundle.getInt("can_int");
		deals_imageview_02	=(ImageView) findViewById(R.id.deals_imageview_02);
		deals_imageview_03	=(ImageView) findViewById(R.id.deals_imageview_03);
//		linearParams = (LinearLayout.LayoutParams) picview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams = (LinearLayout.LayoutParams) amountview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams_item = (LinearLayout.LayoutParams) itemview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams = (LinearLayout.LayoutParams) buyview.getLayoutParams(); // 取控件mGrid当前的布局参数
//		wother=linearParams.width = (screenWidth * 1) / 5;// 当控件的高强制设成75象素
//		wname=linearParams_item.width = (screenWidth * 2) / 5;// 当控件的高强制设成75象素
//		picview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//		itemview.setLayoutParams(linearParams_item); // 使设置好的布局参数应用到控件mGrid2
//		amountview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
//		buyview.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		
		
		TextView_Loading=(TextView) findViewById(R.id.TextView_Loading);
		progressBar_Loading=(ProgressBar) findViewById(R.id.progressBar_Loading);
		
	/*	//Button_ME=(Button) findViewById(R.id.Button_ME);
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.none_color, R.drawable.none_color };
      //  Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
       // Button_ME.setOnClickListener(new Button_MEListener());
*/        
    	Button_ME=(Button) findViewById(R.id.Button_ME);
    	Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
	    Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.none_color, R.drawable.none_color };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new Button_MEListener());
        
        Button_Refresh=(Button) findViewById(R.id.Button_Refresh);
	    Button_Refresh.setOnClickListener(new Button_RefreshListener());
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
        mTask = new MyTask();  
        mTask.execute(); 
        
        
        mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
                case 4 :   
                	progressDialog.dismiss();
                	AlertDialog.Builder b2 = new AlertDialog.Builder(DealsActivity.this);
                	b2.setTitle("抱歉");
                	b2.setMessage("对不起没有获取到任何数据");
                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
		                public void onClick(DialogInterface dialog, int which){
		                }
		            });
                	b2.show();
        			break; 
                case 1 :   
            		 b2 = new AlertDialog.Builder(DealsActivity.this);
            		 b2.setTitle("抱歉");
                	b2.setMessage("对不起您已经评论过了");
                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
		                public void onClick(DialogInterface dialog, int which){
		                }
		            });
                	b2.show();
        			break; 
                case 2 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                		   mTask = new MyTask();  
                	        mTask.execute(); 
                	 Log.d("Rock", "sssssssssss");
                	 
        			break; 
                case 5 :   
                		   mTask = new MyTask();  
                	        mTask.execute(); 
                	 Log.d("Rock", "sssssssssss");
                	 
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
                case 3 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "ddddddddd");
                		AlertDialog.Builder b1 = new AlertDialog.Builder(DealsActivity.this);
                		b1.setTitle("抱歉");
    		            b1.setMessage("对不起您已签过到,不会获取积分");
    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            
    		            b1.show();
                    break;  
                }   
            }   
        } ;
        
        
        exitHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					finish();
					break;
			}
				super.handleMessage(msg);
			}};
		    if ("1".equals(sp.getString("istransparentall", null))&&pop_int==1) {
	        	 Intent intent = new Intent(DealsActivity.this, TransparentAllActivity.class);
		         Bundle mBundle = new Bundle();
				 mBundle.putString("Activity", "DealsActivity");// 压入数据
				 intent.putExtras(mBundle);
				 startActivityForResult(intent, 0);
			//	 overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
				}
        
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
	
	
	 private class MyTask extends AsyncTask<String, Integer, String> {  
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
             //http://122.195.135.91:2861/api/deals/users/1?userId=1&ll=31.2184028%2C121.4174401
             String urlcons = SppaConstant.WALKIN_URL_DEALS+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
             +"?userId="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
             +"&ll="+(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044)+"&"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
             +"&acc="+locationinfo.getAccuracy()+"&apikey=BYauu6D9";
             Log.d("Rock", urlcons);
        	 deals_mservice.setRetrieveUrl(urlcons);
        	 deals_mservice.retrieveDealsInfo();
             return null;  
         }  
         
         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
         @Override  
         protected void onPostExecute(String result) {  
        	 Log.i(TAG, "onPostExecute(Result result) called");  
        	 TextView_Loading.setVisibility(View.GONE); 
        	 progressBar_Loading.setVisibility(View.GONE); 
        	 mData = getData(deals_mservice.getList_jobj_item());
     		 MyAdapter adapter = new MyAdapter(DealsActivity.this);
 			 setListAdapter(adapter);
         }  
           
         //onCancelled方法用于在取消执行中的任务时更改UI  
         @Override  
         protected void onCancelled() {  
        	 Log.i(TAG, "onCancelled() called");
        	 
//             LinearLayout_inbi_congriation.setVisibility(View.GONE);
         }  
     }
		class Button_top_logoListener implements OnClickListener{
	 		public void onClick(View v) {
	 			Intent intent = getIntent();
    			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
    			finish();//此处一定要调用finish()方
	 		}
	 	}  
	 class Button_RefreshListener implements OnClickListener{
			public void onClick(View v) {
				mTask = new MyTask();  
		        mTask.execute(); 
		        checkNetworkStatus();
		
			}
		}
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(DealsActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
      		startActivityForResult(intent, 0);
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
	 private List<Map<String, Object>> getData(List<Map<String, Object>>  deals_list) {
			
			return deals_list;
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
	 	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		final String mRedemptionType = (String)mData.get(position).get(DealsMenuService.JSONObj_item_redemptionType);
//		final String mValidUntil = (String)mData.get(position).get(DealsMenuService.JSONObj_item_validUntil);
		final String isEarned = (String)mData.get(position).get(DealsMenuService.JSONObj_item_isEarned);
		Log.d("Rock", "asdfasdfasdfasdfsadfasdf");
		if (!"false".equals(isEarned) &&!"gift".equals(mRedemptionType)) {
			
			Intent intent=new Intent();
			intent.setClass(DealsActivity.this, InStoreDetailActivity.class);
			Bundle mBundle=new  Bundle();
			mBundle.putString("Activity", "DealsActivity");//压入数据  
			mBundle.putInt("Data", position);//压入数据  
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			
			}else if(!"false".equals(isEarned) &&"gift".equals(mRedemptionType)){
				
				Intent intent=new Intent();
				intent.setClass(DealsActivity.this, RedeemGiftActivity.class);
				Bundle mBundle=new  Bundle();
				mBundle.putString("Activity", "DealsActivity");//压入数据  
				mBundle.putInt("Data", position);//压入数据  
				intent.putExtras(mBundle);
				startActivityForResult(intent, 0);
//				drawable02 = getResources().getDrawable(R.drawable.me_gift);
			}else  {
				Intent intent=new Intent();
				intent.setClass(DealsActivity.this, DealsShowActivity.class);
				Bundle mBundle=new  Bundle();
				mBundle.putInt("Data", position);//压入数据  
				intent.putExtras(mBundle);
				startActivityForResult(intent, 0);
				
			}
		
	}
	
	
	
	public final class ViewHolder{
		public ImageView deals_imageview_01;
		public TextView deals_textview_01;
		public TextView deals_textview_02;
		public TextView deals_textview_03;
		public TextView deals_textview_04;
		public  ImageView ImageView_go_arrow;
		public  ImageView deals_imageview_02,deals_imageview_03;
		public  TextView TextView_isNew;
	
	}
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			return mData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}
Drawable drawable02, drawable_like ,drawable_dislike = null;
		public View getView(int position, View convertView, ViewGroup parent) {//defaults_mservice.getStaticAssetsUrl()+
			final String imageUrl = SppaConstant.WALKIN_URL_BASE+(String)mData.get(position).get(DealsMenuService.JSONObj_item_imageUrl);
			Log.d("Rock", imageUrl+":imageUrl");
			final String mShortDescription = (String)mData.get(position).get(DealsMenuService.JSONObj_item_shortDescription);
			final String mEnName = (String)mData.get(position).get(DealsMenuService.JSONObj_item_brandEnName);
			final String dealID = (String)mData.get(position).get(DealsMenuService.JSONObj_item_id);
			final String mRedemptionType = (String)mData.get(position).get(DealsMenuService.JSONObj_item_redemptionType);
			final String mValidUntil = (String)mData.get(position).get(DealsMenuService.JSONObj_item_validUntil);
			final String isEarned = (String)mData.get(position).get(DealsMenuService.JSONObj_item_isEarned);
			final String mliked = (String)mData.get(position).get(DealsMenuService.JSONObj_item_liked);
			final String isUsed = (String)mData.get(position).get(DealsMenuService.JSONObj_item_used);
			final String isNew = (String)mData.get(position).get(DealsMenuService.JSONObj_item_isNew);
			
			String mText_ValidUntil = null ;
			
				//try {
					
					//Log.d("Rock",VeDate.getWeekStr(mValidUntil)+":msgVeDate.getWeekStr(mValidUntil)" );
					
					//SimpleDateFormat df=new SimpleDateFormat("dd日hh:mm");
					//String times=df.format(Common.StrToDate(mValidUntil));
			if ("false".equals(isEarned)) {
				mText_ValidUntil="暂未解锁";
			}else{
			      String startDateStr = VeDate.getStringDate();
		         //   String bb;
				//	String	endDateStr = "2012-01-10 23:59:59";
		           // bb =VeDate.remainDateToString(startDateStr, mValidUntil);
				mText_ValidUntil="剩余 "+VeDate.remainDateToString(startDateStr, mValidUntil);
			}
				//} catch (Exception e) {
					// TODO: handle exception
					
				//}
		
			if (!"false".equals(isEarned) &&!"gift".equals(mRedemptionType)) {
				 drawable02 = getResources().getDrawable(R.drawable.common_keshiyong);
				}else if(!"false".equals(isEarned) &&"gift".equals(mRedemptionType)){
					if ("false".equals(isUsed)) {
						drawable02 = getResources().getDrawable(R.drawable.me_gift);
					}else{
						drawable02 = getResources().getDrawable(R.drawable.me_gift_wu);
					}
					
				}else  {
					drawable02 = getResources().getDrawable(R.drawable.common_go_arrow);
				}
			if ("like".equals(mliked)) {
				 drawable_like = getResources().getDrawable(R.drawable.thum_up_green);
				 drawable_dislike = getResources().getDrawable(R.drawable.thum_down_gray);
			}else if ("dislike".equals(mliked)){
				 drawable_like = getResources().getDrawable(R.drawable.thum_up_gray);
				 drawable_dislike = getResources().getDrawable(R.drawable.thum_down_red);
			}else if ("".equals(mliked)){
				drawable_like = getResources().getDrawable(R.drawable.thum_up_gray);
				 drawable_dislike = getResources().getDrawable(R.drawable.thum_down_gray);
			}
			
			
			
			String mTextStr ;
			if("bogof".equals(mRedemptionType)){
				mTextStr="买一送一";
			}else if("discount".equals(mRedemptionType)){
				mTextStr= (100-Double.parseDouble((String)mData.get(position).get(DealsMenuService.JSONObj_item_discount)))/10+"";
				if ("0".equals(mTextStr.substring(mTextStr.length()-1))) {
					mTextStr=(100- Math.round(Double.parseDouble((String)mData.get(position).get(DealsMenuService.JSONObj_item_discount))))/10+"折优惠";
				}else{
					mTextStr=mTextStr+"折优惠";
				}
				
				Log.d("Rock", mTextStr+":mTextStr");
			}else if("gift".equals(mRedemptionType)){
				mTextStr = (String)mData.get(position).get(DealsMenuService.JSONObj_item_giftDescription);
			}else if("rebate".equals(mRedemptionType)){
				mTextStr = "优惠"+(String)mData.get(position).get(DealsMenuService.JSONObj_item_rebate)+"元";
			}else{
				mTextStr = (String)mData.get(position).get(DealsMenuService.JSONObj_item_giftDescription);
			}
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.deals_detail, null);
				holder.deals_imageview_01 = (ImageView)convertView.findViewById(R.id.deals_imageview_01);
				loadImage4(imageUrl, (ImageView)convertView.findViewById(R.id.deals_imageview_01));
				holder.deals_textview_01 = (TextView)convertView.findViewById(R.id.deals_textview_01);
				holder.deals_textview_02 = (TextView)convertView.findViewById(R.id.deals_textview_02);
				holder.deals_textview_01.setText(mEnName);
				holder.deals_textview_02.setText(mShortDescription);
				holder.deals_textview_03 = (TextView)convertView.findViewById(R.id.deals_textview_03);
				holder.deals_textview_03.setText(mTextStr);
				holder.deals_textview_04 = (TextView)convertView.findViewById(R.id.deals_textview_04);
				holder.deals_textview_04.setText(mText_ValidUntil);
				holder.ImageView_go_arrow = (ImageView)convertView.findViewById(R.id.ImageView_go_arrow);
				holder.ImageView_go_arrow.setImageDrawable(drawable02);
				holder.deals_imageview_02 = (ImageView)convertView.findViewById(R.id.deals_imageview_02);
				holder.deals_imageview_03 = (ImageView)convertView.findViewById(R.id.deals_imageview_03);
				holder.deals_imageview_02.setBackgroundDrawable(drawable_like);
				holder.deals_imageview_03.setBackgroundDrawable(drawable_dislike);
				holder.TextView_isNew = (TextView)convertView.findViewById(R.id.TextView_isNew);
				if ("false".equals(isNew)) {
					holder.TextView_isNew.setVisibility(View.GONE);
				}else{
					holder.TextView_isNew.setVisibility(View.VISIBLE);
				}
			}else {
					holder=new ViewHolder();  
					loadImage4(imageUrl, (ImageView)convertView.findViewById(R.id.deals_imageview_01));
					holder.deals_textview_01 = (TextView)convertView.findViewById(R.id.deals_textview_01);
					holder.deals_textview_01.setText(mEnName);
					holder.deals_textview_02 = (TextView)convertView.findViewById(R.id.deals_textview_02);
					holder.deals_textview_02.setText(mShortDescription);
					holder.deals_textview_03 = (TextView)convertView.findViewById(R.id.deals_textview_03);
					holder.deals_textview_03.setText(mTextStr);
					holder.deals_textview_04 = (TextView)convertView.findViewById(R.id.deals_textview_04);
					holder.deals_textview_04.setText(mText_ValidUntil);
					holder.ImageView_go_arrow = (ImageView)convertView.findViewById(R.id.ImageView_go_arrow);
					holder.ImageView_go_arrow.setImageDrawable(drawable02);
					holder.deals_imageview_02 = (ImageView)convertView.findViewById(R.id.deals_imageview_02);
					holder.deals_imageview_03 = (ImageView)convertView.findViewById(R.id.deals_imageview_03);
					holder.deals_imageview_02.setBackgroundDrawable(drawable_like);
					holder.deals_imageview_03.setBackgroundDrawable(drawable_dislike);
					holder.TextView_isNew = (TextView)convertView.findViewById(R.id.TextView_isNew);
					if ("false".equals(isNew)) {
						holder.TextView_isNew.setVisibility(View.GONE);
					}else{
						holder.TextView_isNew.setVisibility(View.VISIBLE);
					}
			}
			if ("".equals(mliked)) {
				holder.deals_imageview_02.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
//						
						rateShow(dealID,"like");
						Log.d("Rock", "like");
					}

				});
				holder.deals_imageview_03.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						rateShow(dealID,"dislike");
					}
				});
			}else{
				holder.deals_imageview_02.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
//						
						 Message msg= Message.obtain(mProgressHandler,1);
						 msg.sendToTarget();
					}

				});
				holder.deals_imageview_03.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						 Message msg= Message.obtain(mProgressHandler,1);
						 msg.sendToTarget();
					}
				});
				
			}
			
			
			return convertView;
		}
	}
	
	String dealID;

	private void rateShow(final String strDealID, final String Rating) {
		// TODO Auto-generated method stub
		AlertDialog.Builder b2 = new AlertDialog.Builder(DealsActivity.this);
		b2.setTitle("喜欢否");
		if ("like".equals(Rating)) {
			b2.setMessage("您确认选择喜欢?");
		}else{
			b2.setMessage("您确认选择不喜欢?");
		}
		
    	b2.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
            
            	
            	
            }
        });
    	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
            	 if ("like".equals(Rating)) {
            		 progressDialog = ProgressDialog.show(DealsActivity.this, "Loading...", "Please wait...", true, false);
    				 new Thread(mthread02).start();
    				 dealID =strDealID;
         		}else{
         			 progressDialog = ProgressDialog.show(DealsActivity.this, "Loading...", "Please wait...", true, false);
    				 new Thread(mthread03).start();
    				 dealID =strDealID;
         		}
            }
        });
    	b2.show();
	}
	
	private final Runnable mthread02 = new Runnable() {
        public void run() {
       //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	
       	 Map<String,String>params1=new HashMap<String,String>()  ;
    	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
    	 params1.put("dealId",dealID);
    	 params1.put("rating","like");
    	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
    	 params1.put("timeInterval","1");
         String urlcons = SppaConstant.WALKIN_URL_DEALS+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/deal/"+dealID+"/rate"+"?apikey=BYauu6D9";
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
    private final Runnable mthread03 = new Runnable() {
        public void run() {
       //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	
       	 Map<String,String>params1=new HashMap<String,String>()  ;
    	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
    	 params1.put("dealId",dealID);
    	 params1.put("rating","dislike");
    	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
    	 params1.put("timeInterval","1");
         String urlcons = SppaConstant.WALKIN_URL_DEALS+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/deal/"+dealID+"/rate"+"?apikey=BYauu6D9";
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
	        Drawable cachedImage = asyncImageLoader.loadDrawable(DealsActivity.this, url, new ImageCallback() {
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
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
	public static Handler getProgressHandler(){ 
        return mProgressHandler;
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
