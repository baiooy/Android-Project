package com.walkin.walkin;
/**
 * 
 */

import java.util.List;
import java.util.Map;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author allin
 * 
 */
public class HoorSelectionActivity extends ListActivity {
	
	private MyButton myhome;
	private LinearLayout.LayoutParams linearParams;
	private LinearLayout.LayoutParams linearParams_item;
	private Button deals_back;
//	private TextView nbuy_name;
//	private Button intoday ;
	private Map<String, Object> map ;
	private List<Map<String, Object>> mData;
	private static Handler mProgressHandler=null;
//	private PopupWindow mPopupWindow;
	private Button Button_top_logo,Button_ME;
    private MyTask mTask;  
	private TextView textview01;
	private ImageButton ImageButton01;
	private ImageView ImageView_blctmap_map; 
	private PopupWindow popupWindow;
	private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
    private static Context context;
	String markerID;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hoorselection_act);
		context = this;
		marker_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		Log.d("Rock", screenWidth+":screenWidth"+screenHeight+":screenHeight");
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
		Bundle bundle = getIntent().getExtras();    
   	 markerID=bundle.getString("markerID");
		deals_back=(Button) findViewById(R.id.deals_back);
		deals_back.setOnClickListener(new dealsbackButtonListener());
		 Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
		    Button_top_logo.setOnClickListener(new Button_top_logoListener());
		    
			Button_ME=(Button) findViewById(R.id.Button_ME);
			Button_ME.setText(user_mservice.getInbiBalance());
	        myhome = new MyButton(this);
	        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	        Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	        Button_ME.setOnClickListener(new Button_MEListener());
		    
	//	intoday=(Button) findViewById(R.id.intoday);
    //    myhome = new MyButton(this);
	//    Integer[] mHomeState = { R.drawable.img_hh,R.drawable.img_h, R.drawable.img_h };
	//    intoday.setBackgroundDrawable(myhome.setbg(mHomeState));
	//    intoday.setOnClickListener(new intodayButtonListener());
        
	        mProgressHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					 mTask = new MyTask();  
			         mTask.execute(); 
					break;
				case 1:
					Log.d("Rock", "222222222222");
					break;
				case 2:
						break;
			}
				super.handleMessage(msg);
			}
		};
		 mTask = new MyTask();  
         mTask.execute(); 
		
	  /*  mData = getData(marker_mservice.getList_jobj_item_deals());
		 MyAdapter adapter = new MyAdapter(InStoreActivity.this);
		 setListAdapter(adapter);*/
	 
		
	/*	mData = getData();
		MyAdapter adapter = new MyAdapter(HoorSelectionActivity.this);
		setListAdapter(adapter);*/
		 //TextView_Loading.setVisibility(View.GONE); 
	   	// progressBar_Loading.setVisibility(View.GONE);
	}
	
	
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent(HoorSelectionActivity.this, MeActivity.class);
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
      		startActivityForResult(intent, 0);
		}
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
            
            /* Log.i(TAG, "doInBackground(Params... params) called"); 
             //http://122.195.135.91:2861/api/deals/users/1?userId=1&ll=31.2184028%2C121.4174401
         	 Map<String,String>params1=new HashMap<String,String>()  ;
        	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
        	 params1.put("markerId", markerID);
        	 params1.put("ll",(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044));
        	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
        	 params1.put("acc",locationinfo.getAccuracy()+"");
             String urlcons = SppaConstant.WALKIN_URL_MARKER+"users/"+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/marker/"
             +(String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_item_usedMarker_id)+"?"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
             Log.d("Rock", locationinfo.getLatitude()+","+locationinfo.getLongitude()+urlcons);
             marker_mservice.setRetrieveUrl(urlcons);
             marker_mservice.retrieveMarkerInfo(params1);*/
        	 for (int i = 0; i < marker_mservice.getList_jobj_item_floors().size(); i++) {
        		 String mImageUrl = (String)marker_mservice.getList_jobj_item_floors().get(i).get(MarkerMenuService.JSONObj_item_stores_floors_imageUrl);
        		 AsyncImageLoaderPop.loadImageFromUrl(mImageUrl);
			}
        	 
        	 
        	 
        	 
        	 
             return null;  
         }  
         
         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
         @Override  
         protected void onPostExecute(String result) {  
        	 Log.i(TAG, "onPostExecute(Result result) called");  
        	 TextView_Loading.setVisibility(View.GONE); 
        	 progressBar_Loading.setVisibility(View.GONE); 
        	 mData = getData(marker_mservice.getList_jobj_item_floors());
     		 MyAdapter adapter = new MyAdapter(HoorSelectionActivity.this);
 			 setListAdapter(adapter);
         }  
           
         //onCancelled方法用于在取消执行中的任务时更改UI  
         @Override  
         protected void onCancelled() {  
        	 Log.i(TAG, "onCancelled() called");
        	 
//             LinearLayout_inbi_congriation.setVisibility(View.GONE);
         }  
     }
	
	
	
	
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String enname=	Common.stringChangeString((String) marker_mservice.getList_jobj_item().get(0).get(MarkerMenuService.JSONObj_brands_enName)) ;
//		Log.d("Rock", enname+"");
		String	bg_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/ios/"+enname+defaults_mservice.getBrandBG()+".jpg").toLowerCase();
		String	enname_img_url =  (SppaConstant.WALKIN_URL_BASE+"brands/"+enname+"/android/"+enname+defaults_mservice.getBrandColorLogo()+".png").toLowerCase(); 
		loadImage_Button4(enname_img_url, (Button)findViewById(R.id.Button_top_logo));
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		
		
		super.onStart();
	}

	class Button_top_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = getIntent();
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方
 		}
 	}  
	class intodayButtonListener implements OnClickListener{
		public void onClick(View v) {
			
//			Intent intent=new Intent();
//			intent.setClass(TodayActivity.this, AddnbuyActivity.class);
//			Bundle bundle=new  Bundle();
//			String str1="aaaaaa";
//			bundle.putString("str1", str1);
//			intent.putExtras(bundle);
//			startActivityForResult(intent, 0);
//			finish();
		}
	}
	class dealsbackButtonListener implements OnClickListener{
		public void onClick(View v) {
			
//			Intent intent=new Intent();
//			intent.setClass(BrowseActivity.this, MainActivity.class);
//			Bundle bundle=new  Bundle();
//			String str1="aaaaaa";
//			bundle.putString("str1", str1);
//			intent.putExtras(bundle);
//			startActivityForResult(intent, 0);
//			Log.d("Rock", "go bace");
			finish();
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
		protected void onRestart() {
	 //		mTask = new MyTask();  
	  //      mTask.execute(); 
			Log.d("lifecycle", "onRestart()");
			// TODO Auto-generated method stub
			super.onRestart();
		}
	private List<Map<String, Object>> getData(List<Map<String, Object>> list) {
		return list;
	}
	
	// ListView 中某项被选中后的逻辑
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		final String mImageUrl = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_imageUrl);
		final String mShortDescription = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_shortDescription);
		
		Log.d("Rock", mImageUrl+":mImageUrl");
		Log.d("Rock", mShortDescription+":mShortDescription");
//		final String myJpgPath = (String) mData.get(position).get("img");
//		final String ppid = (String)mData.get(position).get("ppid");
//		final String name = (String)mData.get(position).get("name");
//		final String amount = (String)mData.get(position).get("amount");
//		final int  state  ;
//		if (mData.get(position).get("viewBtn").equals("2")) {
//			state=2;
//		}else {
//			state=1;
//		}
		if (!"".equals(mImageUrl)) {
			Intent intent=new Intent();
			intent.setClass(HoorSelectionActivity.this, HoorSelectionPopActivity.class);
			Bundle bundle=new  Bundle();
			bundle.putString("ImageUrl", mImageUrl);
			bundle.putString("ShortDescription", mShortDescription);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
		}else{
			Toast.makeText(HoorSelectionActivity.this, "没有楼层图片", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	public final class ViewHolder{
		public TextView TextView_floor_num;
		public TextView TextView_floor_text;
		public RelativeLayout RelativeLayout_list;
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

		public View getView(int position, View convertView, ViewGroup parent) {
		//	final String img = (String)mData.get(position).get("img");
			final String mLabel = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_label);
			final String mImageUrl = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_imageUrl);
			
			Log.d("Rock", mImageUrl+":mImageUrl");
		//	final String mShortDescription = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_shortDescription);
			final String mlongDescription = (String)mData.get(position).get(MarkerMenuService.JSONObj_item_stores_floors_longDescription);
			final ViewHolder holder=new ViewHolder();  
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.hoorselection_detail, null);
				holder.TextView_floor_num = (TextView)convertView.findViewById(R.id.TextView_floor_num);
				holder.TextView_floor_num.setText(mLabel);
				holder.TextView_floor_text = (TextView)convertView.findViewById(R.id.TextView_floor_text);
				holder.TextView_floor_text.setText(mlongDescription);
			}else {
					holder.TextView_floor_num = (TextView)convertView.findViewById(R.id.TextView_floor_num);
					holder.TextView_floor_num.setText(mLabel);
					holder.TextView_floor_text = (TextView)convertView.findViewById(R.id.TextView_floor_text);
					holder.TextView_floor_text.setText(mlongDescription);
			}
			return convertView;
		}
	}
	
	  private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
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
    	 Drawable cacheImage = asyncImageLoader.loadDrawable( HoorSelectionActivity.this,url, new ImageCallback() {
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
    private void loadImage4(final String url, final ImageView imageView) {
        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
        Drawable cachedImage = asyncImageLoader.loadDrawable(HoorSelectionActivity.this, url, new ImageCallback() {
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
    //采用Handler+Thread+封装外部接口
    private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
         Drawable cacheImage = asyncImageLoader.loadDrawable(HoorSelectionActivity.this,url,new ImageCallback() {
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
	public static Handler getmProgressHandler(){ 
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
