package com.walkin.walkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.common.AsyncImageLoader;
//import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import android.widget.TextView;

public class DealsShowDetailActivity extends  ListActivity{
	private MyButton myhome;

	private ImageView	ImageView_brands_logo;
	private Button Button_ME ;
	private TextView TextView_brands ;
	private TextView TextView_brands_neirong;
	private TextView TextView_inbi;
	private TextView TextView_youhui;
//	private TextView TextView_huodong;
	
	private Button Button_top_logo;
	private Button goback;
		private List<Map<String, Object>> mData;
		
		protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
	    protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
		private static Context context;
		private ArrayList<String> arraylist ; 
		private String item_enName, item_description;
		private LinearLayout LinearLayout_youhui,LinearLayout_inbi; 
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brandsdetail_act);
		
		context = this;
		brands_mservice.setActivity(this);
		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
        Button_ME=(Button) findViewById(R.id.Button_ME);
    	Button_ME.setText(user_mservice.getInbiBalance());
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        myhome = new MyButton(this);
        Integer[] mHomeState_me = { R.drawable.common_me_img,R.drawable.none_color, R.drawable.none_color };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState_me));
	    Button_ME.setOnClickListener(new Button_MEButtonListener());
	    TextView_brands=(TextView) findViewById(R.id.TextView_brands);
	    TextView_brands_neirong=(TextView) findViewById(R.id.TextView_brands_neirong);
	    TextView_inbi=(TextView) findViewById(R.id.TextView_inbi);
	    TextView_youhui=(TextView) findViewById(R.id.TextView_youhui);
//	    TextView_huodong=(TextView) findViewById(R.id.TextView_huodong);
	    LinearLayout_inbi=(LinearLayout) findViewById(R.id.LinearLayout_inbi);
	    LinearLayout_inbi.setOnClickListener(new LinearLayout_inbiListener());
	    LinearLayout_youhui=(LinearLayout) findViewById(R.id.LinearLayout_youhui);
	    LinearLayout_youhui.setOnClickListener(new LinearLayout_youhuiListener());
	    ImageView_brands_logo=(ImageView) findViewById(R.id.ImageView_brands_logo);
	    ImageView_brands_logo.setOnClickListener(new ImageView_brands_logoListener());
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
	    
	   // ImageView01= (ImageView) findViewById(R.id.ImageView01);
	   // me_TextView_05=(TextView) findViewById(R.id.me_TextView_05);
	   // me_TextView_05.setOnClickListener(new meTextView05Listener());
//        circleProgressBar = (ProgressBar)findViewById(R.id.circleProgressBar); 
//      progressDialog_c.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//      progressDialog_c.setIndeterminate(false);
//      progressDialog_c.setMessage("请稍候...");  
//      circleProgressBar.setVisibility(View.VISIBLE);
		
		
		Bundle bundle = getIntent().getExtras();    
		int data_canshu=bundle.getInt("Data");

		
		String ImageView_loge_pic=Common.stringChangeString((String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_enName));
	//http://221.181.72.61:81/static/brands/sephora/ios/sephora_detail.png
		String ImageView01_getThumbnail_pic = (SppaConstant.WALKIN_URL_BASE+"brands/"+ImageView_loge_pic+"/ios/"+ImageView_loge_pic+defaults_mservice.getBrandDetailImage()+".jpg").toLowerCase();
		String ImageView_Logo = (SppaConstant.WALKIN_URL_BASE+"brands/"+ImageView_loge_pic+"/ios/"+ImageView_loge_pic+defaults_mservice.getBrandBlackLogo()+".png").toLowerCase();
		item_enName=(String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_enName);
		 item_description=(String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_description);
		 
		List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.list_item_marker);
			String item_marker_inbiEarned=(String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
			String item_marker_dealsEarned=(String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
		//String item_marker_inbiEarned=(String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
		//String item_marker_dealsEarned=(String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
		String item_marker_gameId=(String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_marker_gameId);
		Log.d("Rock", ImageView01_getThumbnail_pic+"//进行时间比对 -未完成");//进行时间比对 -未完成
		loadImage4(ImageView01_getThumbnail_pic, R.id.ImageView_brands_logo);
//	    TextView_brands.setText(item_enName);
//	    loadImage5(ImageView_Logo, R.id.ImageView_brands);
	    TextView_brands_neirong.setText(item_description);
	    TextView_inbi.setText(item_marker_inbiEarned);
	    TextView_youhui.setText(item_marker_dealsEarned);
	    TextView_brands.setText((String) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.JSONObj_item_enName));
//	    TextView_huodong.setText(item_marker_gameId);
		//Log.d("Rock",  ((ArrayList<?>) brands_mservice.getList_jobj_item().get(data_canshu).get(BrandsMenuService.list_item_stores)).size()+"wewewewew");
	 	mData = getData((List<Map<String, Object>>) brands_mservice.getList_jobj_item_brandsID().get(data_canshu).get(BrandsMenuService.list_item_stores));
 		MyAdapter adapter = new MyAdapter(DealsShowDetailActivity.this);
		setListAdapter(adapter);
	    
	    
		checkNetworkStatus();
	    
//	    AnimationSet animationSet = new AnimationSet(true);
	  //创建一个RotateAnimation对象
	 /* RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
	  0,
	  0);////旋转点的x坐标为0.5倍父控件的宽度,y坐标为0.2倍父控件的长度
	  //设置动画执行的时间（单位：毫秒）
	  rotateAnimation.setDuration(5000);
	  //将RotateAnimation对象添加到AnimationSet当中
	  animationSet.addAnimation(rotateAnimation);
	  //开始动画
	    ImageView01.setAnimation(animationSet);*/
//	    ImageView01.
	  //  InputStream in;

/*        try{
            InputStream is = getAssets().open("friend1");  
            int size = is.available();  
            byte[] buffer = new byte[size];  
            is.read(buffer);  
            is.close();  
            friend_json = new String(buffer, "utf8");   
            retrieveFriendInfo(friend_json);
           // Log.d("Rock", user_json+"=111111111111111");
    }
    catch (Exception e){
           // Toast.makeText(this, "Exception: File not found!", 2000).show();
    }*/
	    
    }
	public void checkNetworkStatus(){
    	if(isNetworkAvailable()){
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
	class LinearLayout_youhuiListener implements OnClickListener{
 		public void onClick(View v) {
			 Intent intent = new Intent(DealsShowDetailActivity.this, TransparentAllActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "BrandsDetailActivity_youhui_Message");// 压入数据
			 intent.putExtras(mBundle);
			 startActivityForResult(intent, 0);
 		}
 	} 
	
	class LinearLayout_inbiListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(DealsShowDetailActivity.this, TransparentAllActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "BrandsDetailActivity_inbi_Message");// 压入数据
			 intent.putExtras(mBundle);
			 startActivityForResult(intent, 0);
 		}
 	} 
	class ImageView_brands_logoListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(DealsShowDetailActivity.this, TransparentAllActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "BrandsDetailActivity");// 压入数据
			 intent.putExtras(mBundle);
			 startActivityForResult(intent, 0);
 		}
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
	class meTextView08Listener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intents = new Intent();
			intents.setClass(DealsShowDetailActivity.this, HoorSelectionActivity.class);
			startActivityForResult(intents, 0);

		}
	}
	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
	         Intent intent = new Intent(DealsShowDetailActivity.this, MeActivity.class);
	   		 Bundle mBundle = new Bundle();  
	   		 intent.putExtras(mBundle);  
	   		 startActivityForResult(intent, 0);
		}
	}
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
	   private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
    private void loadImage4(final String url, final int id) {
        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
        Drawable cachedImage = asyncImageLoader.loadDrawable(DealsShowDetailActivity.this, url, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
               // ImageView imageViewByTag = (ImageView) list.findViewWithTag(imageUrl);
              //  Log.i("Rock", imageViewByTag+"Drawable cachedImage = asyncImageLoader.loadDrawable( 1)-->");
                
               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
                 //   imageViewByTag.setImageDrawable(imageDrawable);
                    ((ImageView) findViewById(id)).setImageDrawable(imageDrawable);
               }else{
            	   ((ImageView) findViewById(id)).setImageResource(R.drawable.marker_placeholder);
           	}
            }
        });
    	if (cachedImage == null) {
    		((ImageView) findViewById(id)).setImageResource(R.drawable.marker_placeholder);
    	}else{
    		 ((ImageView) findViewById(id)).setImageDrawable(cachedImage);
    	}
    	}
   
	 private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();
	    //采用Handler+Thread+封装外部接口
	    private void loadImage5(final String url,   final int id) {
	          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	         Drawable cacheImage = asyncImageLoader3.loadDrawable(url,new AsyncImageLoader3.ImageCallback() {
	             //请参见实现：如果第一次加载url时下面方法会执行
	             public void imageLoaded(Drawable imageDrawable) {
	            	 
	            	 if (imageDrawable != null ) { 
	            		 ((ImageView) findViewById(id)).setImageDrawable(imageDrawable);
	            	 }else{
	            		 ((ImageView) findViewById(id)).setImageResource(R.drawable.marker_placeholder);
	            	 }
	             }
	         });
	        if(cacheImage!=null){
	        	((ImageView) findViewById(id)).setImageDrawable(cacheImage);
	        }else{
	        	((ImageView) findViewById(id)).setImageResource(R.drawable.marker_placeholder);
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
		 String lat = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_lat);
		 String lng = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_lng);
		 String str_distance = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_distance);
		 String address = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_address);

		 if (locationinfo.getLatitude()!=0.0) {
			 Intent intent = new Intent(DealsShowDetailActivity.this, BaseMapActivity.class);
			 Bundle mBundle = new Bundle();  
//			Log.d("Rock", position+"position");
			 arraylist = new ArrayList<String>(); 
			 arraylist.add(lat);
			 arraylist.add(lng);
			 arraylist.add(item_enName);
			 arraylist.add(address);
			 mBundle.putStringArrayList("Data", arraylist);//压入数据  
			 mBundle.putString("str_distance", str_distance);//压入数据 
			 intent.putExtras(mBundle);  
			 startActivityForResult(intent, 0);
		}
	}
    
    
    
    public final class ViewHolder{
		public TextView neirong;
		public TextView email;
		public TextView times;
		public ImageView huodong;
		
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
String distance;
		public View getView(int position, View convertView, ViewGroup parent) {
			final String str_distance = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_distance);
			
			if (str_distance.length()>3) {
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0");  
				distance=df.format(Double.parseDouble(str_distance)/1000)+"公里";
			}else{
				distance=str_distance+"米";
			}
			
//			Log.d("Rock", lat+"");
			final String email = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_name);
			final String neirong = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_address);
			final String huodong = (String)mData.get(position).get(BrandsMenuService.JSONObj_item_stores_hasActivities);
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.brandsdetail_place_act, null);
				if ("true".equals(huodong)) {
					holder.huodong = (ImageView)convertView.findViewById(R.id.huodong);
					Drawable drawable02 = getResources().getDrawable(R.drawable.activity_small_sign);
					holder.huodong.setImageDrawable(drawable02);
				}
				holder.email = (TextView)convertView.findViewById(R.id.email);
				holder.times = (TextView)convertView.findViewById(R.id.times);
				holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
				holder.email.setText(email);
				holder.times.setText(distance);
				holder.neirong.setText(neirong);
			}else {
					holder=new ViewHolder();  
					if ("true".equals(huodong)) {
						holder.huodong = (ImageView)convertView.findViewById(R.id.huodong);
						Drawable drawable02 = getResources().getDrawable(R.drawable.activity_small_sign);
						holder.huodong.setImageDrawable(drawable02);
					}
					holder.email = (TextView)convertView.findViewById(R.id.email);
					holder.email.setText(email);
					holder.times = (TextView)convertView.findViewById(R.id.times);
					holder.times.setText(distance);
					holder.neirong = (TextView)convertView.findViewById(R.id.neirong);
					holder.neirong.setText(neirong);
			}
			return convertView;
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
