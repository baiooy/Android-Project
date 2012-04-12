package com.walkin.walkin;

import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.string.Renderer;
import com.string.RendererES2;
import com.string.RendererES2InStore;
import com.string.RendererInStore;
import com.string.core.StringOGL;
import com.walkin.walkin.R;
import com.walkin.bean.LocationInfo;
import com.walkin.bean.NetworkBitmapInStoreInfo;
import com.walkin.service.BrandsMenuService;
import com.walkin.service.MarkerMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class StringOGLTutorialInStore extends Activity
{
	private static Context context;
    protected final static NetworkBitmapInStoreInfo networkbitmapinstoreinfo = NetworkBitmapInStoreInfo.getInstance();
	protected final static BrandsMenuService brands_mservice = BrandsMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected final static LocationInfo locationinfo = LocationInfo.getInstance();
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	private static Handler mFrameMarkersBackHandler=null;
//	private static Handler mProgressHandler=null;
	private static Handler mProgressHandler2=null;
	
    public String markerID =null;
    public String TextView_inbiEarned ;
    public String TextView_enName ;
    public String TextView_dealsEarned;
//    public String data;
    public String  str_type;
	private static Handler exitHandler=null;
//    private ProgressDialog progressDialog; 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;
		networkbitmapinstoreinfo.setActivity(this);
		brands_mservice.setActivity(this);
		marker_mservice.setActivity(this);
		user_mservice.setActivity(this);
		locationinfo.setActivity(this);
		
		
		Bundle bundle = getIntent().getExtras();    
		
//		data=bundle.getString("Data");
		TextView_enName=bundle.getString("name");
		
		
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		try {
			StringOGL.init(this, 0.1f, 100.0f);
			StringOGL.setFullscreen(true);
			StringOGL.setPosition(0.5f, 0.5f);
//			StringOGL.setOrientation(StringOGL.ORIENTATION_AUTO);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
		Bitmap[] bitmap = networkbitmapinstoreinfo.getBitmap();
			for (int v = 0; v < bitmap.length; v++) {
				Log.e("Rock",bitmap[v]+"asdf");
				StringOGL.loadImageMarker(bitmap[v]);
		}
		
			
		if (StringOGL.getGLVersion() >= 20)
			StringOGL.addRenderer(new RendererES2InStore());
		else
			StringOGL.addRenderer(new RendererInStore());
		
		
		
		mFrameMarkersBackHandler = new Handler() {
			public void handleMessage(Message msg) {
				Log.d("Rock", msg.what+"msg.what");
				
				
//				 if ("identification".equals(data)) {
//					 List<Map<String, Object>>  list_marker = (List<Map<String, Object>>)brands_mservice.getList_jobj_item().get(msg.what).get(BrandsMenuService.list_item_marker);
//				 markerID = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_id);
//				TextView_inbiEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_inbiEarned);
//				TextView_enName =  (String)brands_mservice.getList_jobj_item().get(msg.what).get(BrandsMenuService.JSONObj_item_enName);
//				TextView_dealsEarned = (String) list_marker.get(0).get(BrandsMenuService.JSONObj_item_marker_dealsEarned);
//				
//				 }else{
				
				str_type = (String)marker_mservice.getList_jobj_item_nextMarkers().get(msg.what).get(MarkerMenuService.JSONObj_brands_nextMarkers_activityType);
				markerID = (String)marker_mservice.getList_jobj_item_nextMarkers().get(msg.what).get(MarkerMenuService.JSONObj_brands_nextMarkers_id);
				//if ("deal".equals(str_type)) {
				TextView_inbiEarned = (String) marker_mservice.getList_jobj_item_nextMarkers().get(msg.what).get(MarkerMenuService.JSONObj_brands_nextMarkers_inbiEarned);
//				 }
				// progressDialog = ProgressDialog.show(StringOGLTutorialInStore.this, "", "Please wait...", true, false);
					Intent intent = new Intent(StringOGLTutorialInStore.this, InActivity.class);
	     			Bundle mBundle = new Bundle();
	     			mBundle.putString("enName", TextView_enName);// 压入数据
	     			intent.putExtras(mBundle);
	     			startActivity(intent);
					 new Thread(mthread).start();
			/*	Intent intent = new Intent(StringOGLTutorial.this, InActivity.class);
				Bundle mBundle = new Bundle();  
				mBundle.putInt("Data", msg.what);//压入数据  
				intent.putExtras(mBundle);  
	            startActivity(intent);*/
			}
		}; 
		/*  mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	                case 4 :   
	              //  	progressDialog.dismiss();
	                	AlertDialog.Builder b2 = new AlertDialog.Builder(StringOGLTutorialInStore.this);
	                	b2.setTitle("抱歉");
	                	b2.setMessage("对不起没有获取到任何数据");
	                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			                public void onClick(DialogInterface dialog, int which){
			                	RendererES2.isnum99999999=0;
			            		RendererES2InStore.isnum99999999=0;
			            		Renderer.isnum99999999=0;
			            		RendererInStore.isnum99999999=0;
			                	
			                }
			            });
	                	b2.show();
	        			break; 
	                case 2 :   
//	                	if(progressDialog !=null){
//	                		progressDialog =null;
//	                	}
	                //	progressDialog.dismiss();
	                	 Log.d("Rock", "sssssssssss");
	                	 
	            			Intent intent = new Intent(StringOGLTutorialInStore.this, ReceiveInBiActivity.class);
	            			Bundle mBundle = new Bundle();
	            			mBundle.putString("enName", TextView_enName);// 压入数据
	            			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
	            			mBundle.putString("type", "");// 压入数据
	            			mBundle.putString("markerID", markerID);// 压入数据
	            			intent.putExtras(mBundle);
	            			startActivity(intent);
	        			break; 
	                case 3 :   
//	                	if(progressDialog !=null){
//	                		progressDialog =null;
//	                	}
	                //	progressDialog.dismiss();
	                		 intent = new Intent(StringOGLTutorialInStore.this, ReceiveInBiActivity.class);
	            			 mBundle = new Bundle();
	            			mBundle.putString("enName", TextView_enName);// 压入数据
	            			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
	            			mBundle.putString("type", "400");// 压入数据
	            			mBundle.putString("markerID", markerID);// 压入数据
	            			
	            			intent.putExtras(mBundle);
	            			startActivity(intent);
	                    break;  
	                }   
	            }   
	        };*/
	        mProgressHandler2 = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	              /*  case 4 :   
	               // 	progressDialog.dismiss();
	                	AlertDialog.Builder b2 = new AlertDialog.Builder(StringOGLTutorialInStore.this);
	                	b2.setTitle("抱歉");
	                	b2.setMessage("对不起没有获取到任何数据");
	                	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			                public void onClick(DialogInterface dialog, int which){
			                	RendererES2.isnum99999999=0;
			            		RendererES2InStore.isnum99999999=0;
			            		Renderer.isnum99999999=0;
			            		RendererInStore.isnum99999999=0;
			                }
			            });
	                	b2.show();
	        			break; */
	                case 2 :   
	                	 Log.d("Rock", "sssssssssss");
	                //	 progressDialog.dismiss();
	                		Intent intent = new Intent(StringOGLTutorialInStore.this, ReceiveInBiActivity.class);
	         			Bundle mBundle = new Bundle();
	         			mBundle.putString("enName", TextView_enName);// 压入数据
	         			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
	         			mBundle.putString("type", (String)msg.obj);// 压入数据
	         			mBundle.putString("markerID", "");// 压入数据
	         			intent.putExtras(mBundle);
	         			startActivity(intent);
	        			break; 
	              /*  case 3 :   
	              //  	progressDialog.dismiss();
	                		 intent = new Intent(StringOGLTutorialInStore.this, ReceiveInBiActivity.class);
	            			 mBundle = new Bundle();
	            			mBundle.putString("enName", TextView_enName);// 压入数据
	            			mBundle.putString("inbiEarned", TextView_inbiEarned);// 压入数据
	            			mBundle.putString("type", "400");// 压入数据
	            			mBundle.putString("markerID", "");// 压入数据
	            			intent.putExtras(mBundle);
	            			startActivity(intent);
	                    break;  */
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
				}
			};
	        Intent intent = new Intent(StringOGLTutorialInStore.this, TransparentCameraActivity.class);
	         Bundle mBundle = new Bundle();
			 mBundle.putString("Activity", "StringOGLTutorialInStore");// 压入数据
			 intent.putExtras(mBundle);
			 startActivity(intent);
			 overridePendingTransition(R.anim.fade, R.anim.hold);
	}
	private final Runnable mthread = new Runnable() {
        public void run() {
       //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	
       	 Map<String,String>params1=new HashMap<String,String>()  ;
    	 params1.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));//
    	 params1.put("markerId",markerID);
    	 params1.put("ll",(locationinfo.getLatitude()+0.0018)+","+(locationinfo.getLongitude()-0.0044));
    	 params1.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
    	 params1.put("acc",locationinfo.getAccuracy()+"");
         String urlcons = SppaConstant.WALKIN_URL_MARKER+"users/"+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/marker/"+markerID+"?"
         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
         Log.d("Rock", locationinfo.getLatitude()+","+locationinfo.getLongitude()+urlcons);
         marker_mservice.setRetrieveUrl(urlcons);
         marker_mservice.retrieveMarkerInfo(params1);
			 String meta_code = marker_mservice.getCode();
			 Log.d("Rock", meta_code+"LLLLL"+"\n"+marker_mservice.getMessage());
		/*	 if ("identification".equals(data)) {
				 if ("200".equals(meta_code)) {
					 Message msg= Message.obtain(mProgressHandler,2);
					 msg.sendToTarget(); 
				}else if("1005".equals(meta_code)){
					 Message msg= Message.obtain(mProgressHandler,3);
					 msg.sendToTarget();
					
				}else{
					 Message msg= Message.obtain(mProgressHandler,3);
					 msg.sendToTarget();
				}
			}else{*/
				
				if ("200".equals(meta_code)) {
					 Message msg= Message.obtain(mProgressHandler2,2,str_type);
					 msg.sendToTarget(); 
				}else if("1009".equals(meta_code)){
					if ("game".equals(str_type)) {
						Message msg= Message.obtain(mProgressHandler2,2,str_type);
						 msg.sendToTarget();
					}else{
						Message msg= Message.obtain(mProgressHandler2,2,"400");
						msg.sendToTarget();
					}
				}else if("1008".equals(meta_code)){
						Message msg= Message.obtain(mProgressHandler2,2,str_type);
						msg.sendToTarget();
				}else{
					 Message msg= Message.obtain(mProgressHandler2,2,"400");
					 msg.sendToTarget();
				}
//			}
        }
    };
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		RendererES2.isnum99999999=0;
		RendererES2InStore.isnum99999999=0;
		Renderer.isnum99999999=0;
		RendererInStore.isnum99999999=0;
	}

	public void onRestart()
	{
		super.onRestart();
		RendererES2.isnum99999999=0;
		RendererES2InStore.isnum99999999=0;
		Renderer.isnum99999999=0;
		RendererInStore.isnum99999999=0;
	}
	@Override
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
		StringOGL.resume();
		RendererES2.isnum99999999=0;
		RendererES2InStore.isnum99999999=0;
		Renderer.isnum99999999=0;
		RendererInStore.isnum99999999=0;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
//		StringOGL.pause();
	}
	
	protected void onDestroy()
	{
		StringOGL.term();
		super.onDestroy();
	
	}
	  public static Handler getFrameMarkersBackHandler(){ 
	        return mFrameMarkersBackHandler;
	    }
		public static Handler getExitHandler(){ 
	        return exitHandler;
	    }
/*	  public boolean onKeyDown(int keyCode, KeyEvent event) {
			// 按下键盘上返回按钮
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Intent intent = new Intent(StringOGLTutorialInStore.this, StringBlackActivity.class);
    			startActivity(intent);
				//finish();
			
				return true;
			} else {
				return super.onKeyDown(keyCode, event);

			}

		}*/
}
