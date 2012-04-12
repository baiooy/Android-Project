package com.walkin.walkin;

import java.util.List;
import java.util.Map;



import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.LeaderBoardMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class LeaderBoardListActivity extends  ListActivity{
	private MyButton myhome;

	
//	private Button Button_ME ;
	private Button goback,Button_ALL,Button_week ;
	private TextView shuiping_TextView;
	private Button Button_top_arrow;
 
		private List<Map<String, Object>> mData;

		protected final static LeaderBoardMenuService leaderboard_mservice = LeaderBoardMenuService.getInstance();
//	    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
		private static Context context;
		
		LinearLayout.LayoutParams lp;
    	RelativeLayout relativeLayout;
    	private LinearLayout linearLayout3;
    	int screenWidth;
        private MyTask mTask;  
        private MyTask02 mTask02;  
    	private TextView TextView_Loading;
    	private ProgressBar progressBar_Loading;
    	SeekBar sb;
    	int seek_int =0;
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboardlist_act);
		
		context = this;
		leaderboard_mservice.setActivity(this);
//		defaults_mservice.setActivity(this);
//        Button_ME=(Button) findViewById(R.id.Button_ME);
//    	Button_ME.setText(user_mservice.getInbiBalance());
		Button_ALL=(Button) findViewById(R.id.Button_ALL);
		Button_ALL.setOnClickListener(new Button_ALLListener()); 
		Button_week=(Button) findViewById(R.id.Button_week);
		Button_week.setOnClickListener(new Button_weekListener()); 
		shuiping_TextView=(TextView) findViewById(R.id.shuiping_TextView);
	   // Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_arrow=(Button) findViewById(R.id.Button_top_arrow);
	    Button_top_arrow.setOnClickListener(new Button_top_logoListener());
	  //  Button_top_logo.setOnClickListener(new Button_top_logoListener());
		String pic_path ;
		String ImageView01_getThumbnail_pic = (String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_imageUrl);
		try {
			if ("http".equals(ImageView01_getThumbnail_pic.substring(0, 4))) {
				pic_path =ImageView01_getThumbnail_pic;
			}else{
				pic_path =SppaConstant.WALKIN_URL_IP+ImageView01_getThumbnail_pic;
			}
			Log.d("Rock", pic_path+":pic_path");
			loadImage5(pic_path,(ImageView)findViewById(R.id.user_ImageView));
		} catch (Exception e) {
			// TODO: handle exception
		}
	
    	  // 找到拖动条和文本框  
        sb = (SeekBar) findViewById(R.id.SeekBar01);  
        linearLayout3= (LinearLayout) findViewById(R.id.linearLayout3);
        // 设置拖动条的初始值和文本框的初始值  
        sb.setMax(100);  
        sb.setProgress(seek_int);  
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
        DisplayMetrics dm = new DisplayMetrics();
 		getWindowManager().getDefaultDisplay().getMetrics(dm);
 		screenWidth = dm.widthPixels;
        Log.d("Rock",screenWidth+"");
       
    	
         // 设置拖动条改变监听器  
         OnSeekBarChangeListener osbcl = new OnSeekBarChangeListener() {  
   
             @Override  
             public void onProgressChanged(SeekBar seekBar, int progress,  
                     boolean fromUser) {  
//                 tv1.setText("当前进度：" + sb.getProgress());  
               
                // lp.setMargins(  (sb.getProgress()*(screenWidth-100))/100-40, 0, 0, 0);  
               //  linearLayout3.setLayoutParams(lp);  
               //  ding_TextView.setPadding(sb.getProgress(), 0, 0, 0);
               //  LinearLayout layout = new LinearLayout(getContext());    
               //  layout.setPadding(20, 20, 20, 20);  
                // Toast.makeText(getApplicationContext(), "onProgressChanged",  
                 //        Toast.LENGTH_SHORT).show();  
             }  
   
             @Override  
             public void onStartTrackingTouch(SeekBar seekBar) {  
                // Toast.makeText(getApplicationContext(), "onStartTrackingTouch",  
                 //        Toast.LENGTH_SHORT).show();  
             }  
   
             @Override  
             public void onStopTrackingTouch(SeekBar seekBar) {  
                // Toast.makeText(getApplicationContext(), "onStopTrackingTouch",  
                //        Toast.LENGTH_SHORT).show();  
             }  
   
         };  
   
         // 为拖动条绑定监听器  
        // sb.setOnSeekBarChangeListener(osbcl);  
   
    	
    	
    	
         	
    	
    	
     /*   myhome = new MyButton(this);
        Integer[] mHomeState_me = { R.drawable.common_me_img,R.drawable.none_color, R.drawable.none_color };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState_me));
	    Button_ME.setOnClickListener(new Button_MEButtonListener());*/
//	    ImageView_brands=(TextView) findViewById(R.id.ImageView_brands);
	    goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
		TextView_Loading=(TextView) findViewById(R.id.TextView_Loading);
		progressBar_Loading=(ProgressBar) findViewById(R.id.progressBar_Loading);
		mTask = new MyTask();  
        mTask.execute(); 
		
		
		
		
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
	            
	             String urlcons = SppaConstant.WALKIN_URL_LEADERBOARD+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
	             +"?"+"userId="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
	             +"&metric=checkins"+"&timeFrame=allTime"+"&limit=20"+"&offset=0"+"&apikey=BYauu6D9";
	             Log.d("Rock", urlcons);
	             leaderboard_mservice.setRetrieveUrl(urlcons);
	             leaderboard_mservice.retrieveLeaderBoardInfo();
	             return null;  
	         }  
	         
	         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	         @Override  
	         protected void onPostExecute(String result) {  
	        	 Log.i(TAG, "onPostExecute(Result result) called");  
	        	 TextView_Loading.setVisibility(View.GONE); 
	        	 progressBar_Loading.setVisibility(View.GONE); 
	        	
	        	 if("thisweek".equals(leaderboard_mservice.getTimeFrame())){
	        		 shuiping_TextView.setText("本周水平");
	        	 }else{
	        		 shuiping_TextView.setText("总体水平");
	        	 }
	        	 try {
	        		// Log.e("Rock", leaderboard_mservice.getTotalInBiEarnedPercentile()+":leaderboard_mservice.getTotalCheckinsPercentile()");
	        		
	        		double aaa = Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile());
	        		 Log.d("Rock",  aaa+":=111");
	        		 double bbb = 1-aaa;
	        		 Log.d("Rock",  bbb+":=222");
	        		 double CCC = (1-aaa)*100;
	        		 Log.d("Rock",  CCC+":=333");
	        		 int pro_int=(int)CCC;
	        		 Log.d("Rock",  pro_int+":=4444");
	        		// int a = (int) (1-(Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile())));
		          	// Log.d("Rock",  Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile())+":222WgetTotalCheckinsPerADAScentile");
		           	// Log.d("Rock", (int)( Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile())*100)+":333WgetTotalCheckinsPerADAScentile");
		           	// Log.d("Rock", 	 (int)(((a+seek_int)*100)/(screenWidth-100))+":444WgetTotalCheckinsPerADAScentile");
		           	 //int pro_int = (int)( (1-Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile()))*100);
		             int pro2_int = (screenWidth-70-pro_int);
		             int pro3_int = (screenWidth-70-(screenWidth-140)+(((screenWidth-140)*pro_int)/100));
		             Log.d("Rock",  pro2_int+":=pro2_int");
		             Log.d("Rock",  pro3_int+":=pro3_int");
		        	  lp.setMargins(pro3_int-80, 0, 0, 0);  
		             linearLayout3.setLayoutParams(lp);  
		        	 
		             sb.setProgress(pro_int); 
				} catch (Exception e) {
					// TODO: handle exception
				}
	        	
	        	 
	        	 
	        	 mData = getData(leaderboard_mservice.getList_jobj_item());
      		     MyAdapter adapter = new MyAdapter(LeaderBoardListActivity.this);
  			     setListAdapter(adapter);
	         }  
	           
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");
	        	 
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }
	
	  private class MyTask02 extends AsyncTask<String, Integer, String> {  
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
	            
	             String urlcons = SppaConstant.WALKIN_URL_LEADERBOARD+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
	             +"?"+"userId="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
	             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
	             +"&metric=checkins"+"&timeFrame=thisweek"+"&limit=20"+"&offset=0"+"&apikey=BYauu6D9";
	             Log.d("Rock", urlcons);
	             leaderboard_mservice.setRetrieveUrl(urlcons);
	             leaderboard_mservice.retrieveLeaderBoardInfo();
	             return null;  
	         }  
	         
	         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	         @Override  
	         protected void onPostExecute(String result) {  
	        	 Log.i(TAG, "onPostExecute(Result result) called");  
	        	 TextView_Loading.setVisibility(View.GONE); 
	        	 progressBar_Loading.setVisibility(View.GONE); 
	        	
	        	 if("thisweek".equals(leaderboard_mservice.getTimeFrame())){
	        		 shuiping_TextView.setText("本周水平");
	        	 }else{
	        		 shuiping_TextView.setText("总体水平");
	        	 }
	        	 
	          	 try {
	          		double aaa = Double.parseDouble(leaderboard_mservice.getTotalInBiEarnedPercentile());
	        		 Log.d("Rock",  aaa+":=111");
	        		 double bbb = 1-aaa;
	        		 Log.d("Rock",  bbb+":=222");
	        		 double CCC = (1-aaa)*100;
	        		 Log.d("Rock",  CCC+":=333");
	        		 int pro_int=(int)CCC;
	        		 Log.d("Rock",  pro_int+":=4444");
			             int pro2_int = (screenWidth-70-pro_int);
			             int pro3_int = (screenWidth-70-(screenWidth-140)+(((screenWidth-140)*pro_int)/100));
			             Log.d("Rock",  pro2_int+":=pro2_int");
			             Log.d("Rock",  pro3_int+":=pro3_int");
			        	  lp.setMargins(pro3_int-80, 0, 0, 0);  
			             linearLayout3.setLayoutParams(lp);  
			        	 
			             sb.setProgress(pro_int); 
		             
				} catch (Exception e) {
					// TODO: handle exception
				}
	        	 
	        	 mData = getData(leaderboard_mservice.getList_jobj_item());
   		 MyAdapter adapter = new MyAdapter(LeaderBoardListActivity.this);
			 setListAdapter(adapter);
	         }  
	           
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");
	        	 
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }
		 class Button_ALLListener implements OnClickListener{
				public void onClick(View v) {

					mTask = new MyTask();  
			        mTask.execute(); 
					
					
					
					
				}
			}
		 class Button_weekListener implements OnClickListener{
				public void onClick(View v) {

					mTask02 = new MyTask02();  
			        mTask02.execute(); 
					
					
					
					
				}
			}
	
	
	
	
	
	protected void onStart() {
		super.onStart();
		
		
//		loadImage5(photo, R.id.ImageView01);
		
		//TextView01= (TextView) findViewById(R.id.TextView01);
//		TextView01.setText(username);
		//TextViewNeirong01= (TextView) findViewById(R.id.TextViewNeirong01);
//		TextViewNeirong01.setText(style_line1+"\n"+style_line2);
		//TextViewNeirong02= (TextView) findViewById(R.id.TextViewNeirong02);
//		/TextViewNeirong02.setText(favorites_line1+"\n"+favorites_line2);
		//TextView04= (TextView) findViewById(R.id.TextView04);
//		TextView04.setText(homeCity);
		//me_TextView_08= (TextView) findViewById(R.id.me_TextView_08);
		//me_TextView_09= (TextView) findViewById(R.id.me_TextView_09);
		//me_TextView_10= (TextView) findViewById(R.id.me_TextView_10);
		//me_TextView_11= (TextView) findViewById(R.id.me_TextView_11);
		//for (int i = 0; i <user_list_items.size(); i++) {
			//	me_TextView_08.setText(user_list_items.get(0).get("user_content"));
//				me_TextView_08.setOnClickListener(new meTextView08Listener());
			//	me_TextView_09.setText(user_list_items.get(1).get("user_content"));
			//	me_TextView_10.setText(user_list_items.get(2).get("user_content"));
			//	me_TextView_11.setText(user_list_items.get(3).get("user_content"));
	//	}
		
	//	loadImagebg5(photo, R.id.RelativeLayout_bg);
		
	}
	 class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}
	class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}   

/*	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
	         Intent intent = new Intent(LeaderBoardListActivity.this, MeActivity.class);
	   		 Bundle mBundle = new Bundle();  
	   		 intent.putExtras(mBundle);  
	   		 startActivityForResult(intent, 0);
		}
	}*/
	class meTextView05Listener implements OnClickListener{
		public void onClick(View v) {
//			Intent intents = new Intent();
//			intents.setClass(MeActivity.this, ExampleTransparentGlSurface.class);
//			startActivity(intents);
			Log.d("Rock", "intents");
	/*		for (int i = 0; i < user_list.size(); i++) {
				Log.d("Rock", i+"");
				if (i==0) {
					Intent intents = new Intent();
					
					
					intents.setClass(BrandsDetailActivity.this, MeInBiDetailView.class);
					startActivity(intents);	
				}
			}*/
			
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
    
	private List<Map<String, Object>> getData(List<Map<String, Object>>  list) {
		return list;
	}
    
    
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
	}
    
    
    
    public final class ViewHolder{
		public TextView user_num;
		public TextView user_name;
		public TextView inbi_num;
		public ImageView user_img;
		
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
		String path;
		public View getView(int position, View convertView, ViewGroup parent) {
			final String user_num = position+1+"";
			
			
			
			final String userName = (String)mData.get(position).get(LeaderBoardMenuService.JSONObj_item_user_userName);
			final String imageUrl = (String)mData.get(position).get(LeaderBoardMenuService.JSONObj_item_user_imageUrl);
			Log.d("Rock", imageUrl);
			if (!"".equals(imageUrl)) {
				if ("http".equals(imageUrl.substring(0, 4))) {
					path=imageUrl;
				}else{
					path=SppaConstant.WALKIN_URL_IP+imageUrl;
				}
			}else{
				path=imageUrl;
			}
		
			
			final String totalInBiEarned = (String)mData.get(position).get(LeaderBoardMenuService.JSONObj_item_scores_totalInBiEarned);
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.leaderboardlist_detail_act, null);
			/*	if ("true".equals(huodong)) {
					holder.huodong = (ImageView)convertView.findViewById(R.id.huodong);
					Drawable drawable02 = getResources().getDrawable(R.drawable.cmd_huodong_color);
					holder.huodong.setImageDrawable(drawable02);
				}*/
				holder.user_num = (TextView)convertView.findViewById(R.id.user_num);
				holder.user_name = (TextView)convertView.findViewById(R.id.user_name);
				holder.inbi_num = (TextView)convertView.findViewById(R.id.inbi_num);
				
				loadImage5(path, (ImageView)convertView.findViewById(R.id.user_img));
			
				
			
				holder.user_num.setText(user_num);
				holder.user_name.setText(userName);
				holder.inbi_num.setText(totalInBiEarned);
			}else {
					holder=new ViewHolder();  
					holder.user_num = (TextView)convertView.findViewById(R.id.user_num);
					holder.user_name = (TextView)convertView.findViewById(R.id.user_name);
					holder.inbi_num = (TextView)convertView.findViewById(R.id.inbi_num);
					loadImage5(path, (ImageView)convertView.findViewById(R.id.user_img));
				
					holder.user_num.setText(user_num);
					holder.user_name.setText(userName);
					holder.inbi_num.setText(totalInBiEarned);
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
        		 imageView.setImageResource(R.drawable.profile_default_pic_leaderboard);
            	 }
             }
         });
        if(cacheImage!=null){
        	imageView.setImageDrawable(cacheImage);
        }else{
   		 imageView.setImageResource(R.drawable.profile_default_pic_leaderboard);
   	 }
    }
 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
    private void loadImage4(final String url, final ImageView imageView) {
        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
        Drawable cachedImage = asyncImageLoader.loadDrawable(LeaderBoardListActivity.this, url, new ImageCallback() {
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
