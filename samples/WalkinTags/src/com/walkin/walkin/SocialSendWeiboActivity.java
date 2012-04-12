package com.walkin.walkin;
/**
 * @description : 发送微博
 * @author : Rock
 * @param ：onCreate 
 * @date : 2011/03/09
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.NetworkToPic;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SocialSendWeiboActivity extends Activity {

	private SmiliesEditText Edit;
	private Button Button_send,Button_pic;
	private SharedPreferences sp;
	public String editaccessToken;
	public String editaccessTokenSecret;
	
	public static final int NONE = 0;   
    public static final int PHOTOHRAPH = 11;// 拍照    
    public static final int PHOTOZOOM = 12; // 缩放    
    public static final int PHOTORESOULT = 13;// 结果    
    public static final String IMAGE_UNSPECIFIED = "image/*";  
    
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private static Context context;
    private static Handler mProgressHandler=null;
	private ProgressDialog progressDialog;
    String path;
    File file ;
    Bitmap photo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.social_sendweibo_act);
		context = this;
//		defaults_mservice.setActivity(this);
		user_mservice.setActivity(this);
		Button_send = (Button) findViewById(R.id.Button_send);
		Button_pic= (Button) findViewById(R.id.Button_pic);
		
//		imageView = (ImageView) findViewById(R.id.imageView);
		Button_pic.setOnClickListener(new OnClickListener() {   
			
			
			
			public void onClick ( View v )
			{
				final CharSequence[] items =
				{ "相册", "拍照" };
				AlertDialog dlg = new AlertDialog.Builder(SocialSendWeiboActivity.this).setTitle("选择图片").setItems(items,
						new DialogInterface.OnClickListener()
						{
							public void onClick ( DialogInterface dialog , int item )
							{
								// 这里item是根据选择的方式，
								// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
								if (item == 1)
								{
									Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
									
									SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								      String filename = timeStampFormat.format(new Date(item, item, item));
								     ContentValues values = new ContentValues();
								     values.put(Media.TITLE, filename);
								     
								     
								Uri photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
								 ContentResolver resolver = getContentResolver();
								 String[] proj = {MediaStore.Images.Media.DATA};
			                       //好像是android多媒体数据库的封装接口，具体的看Android文档
			                       Cursor cursor = managedQuery(photoUri, proj, null, null, null); 
			                       //按我个人理解 这个是获得用户选择的图片的索引值
			                       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			                       //将光标移至开头 ，这个很重要，不小心很容易引起越界
			                       cursor.moveToFirst();
			                       //最后根据索引值获取图片路径
			                        path = cursor.getString(column_index);
			                        
			                        
			                      
			                        Log.d("Rock", path);
								
								//path = ImageUtil.getRealPathFromURI(photoUri,getContentResolver());
								getImageByCamera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
									
									startActivityForResult(getImageByCamera,PHOTOHRAPH );
									
									Toast.makeText(SocialSendWeiboActivity.this, "请上传分辨率较小的图片", Toast.LENGTH_SHORT).show();
									
								} else
								{
//									Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
//									getImage.addCategory(Intent.CATEGORY_OPENABLE);
//									getImage.setType("image/jpeg");
									 Intent intent = new Intent(Intent.ACTION_PICK, null);   
						             intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);   
									startActivityForResult(intent, PHOTORESOULT);
									//Toast.makeText(SocialSendWeiboActivity.this, "请上传分辨率较小的图片", Toast.LENGTH_SHORT).show();
									
								}
							}
						}).create();
				dlg.show();
			}
    });   
		
		 mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	               
	                case 2 :   
	                	progressDialog.dismiss();
	                	AlertDialog.Builder b = new AlertDialog.Builder(SocialSendWeiboActivity.this);
	                	b.setTitle("恭喜");
	                    b.setMessage("发送成功");
	                    b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        	finish();
	                        }
	                    });
	                    b.show();
	        			break; 
	                case 3 :   
	                	progressDialog.dismiss();
	                	 b = new AlertDialog.Builder(SocialSendWeiboActivity.this);
						b.setTitle("抱歉");
	                    b.setMessage("发送出错");
	                    b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which){
	                        }
	                    });
	                    b.show();
	                    break;  
	                case 1 :   
	                	progressDialog.dismiss();
	                	 b = new AlertDialog.Builder(SocialSendWeiboActivity.this);
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
	            
	        } ;     
		
	       
			
			
		Edit = (SmiliesEditText) findViewById(R.id.Edit);
		 Bundle bundle = getIntent().getExtras();    
			String data_canshu=bundle.getString("strType");
			if (!"".equals(data_canshu)) {
				Edit.setText(data_canshu);
			}
	    Button_send = (Button)findViewById(R.id.Button_send);
	    sp = getSharedPreferences("user", 0);
		  //  EditText_Email.setText(sp.getString("weiboEmail", null));
		   // EditText_Password.setText(sp.getString("weiboPassword", null));
				
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
				System.setProperty("weibo4j.oauth.consumerSecret",Weibo.CONSUMER_SECRET);
				editaccessToken = sp.getString("accessToken", null);
				editaccessTokenSecret = sp.getString("accessTokenSecret", null);
				if ("".equals(editaccessToken)||editaccessToken==null) {
					Intent intent = new Intent(SocialSendWeiboActivity.this,AuthorizationAct.class);
					// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
					Bundle mBundle = new Bundle();
					mBundle.putString("Data", "wuweibo");// 压入数据
					intent.putExtras(mBundle);
					startActivityForResult(intent, 0);
					SocialSendWeiboActivity.this.finish();
					return;
				}
				
				Button_send.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						try {
							if (file.length()>0) {
								 progressDialog = ProgressDialog.show(SocialSendWeiboActivity.this, "Loading...", "发送中...", true, true);
								 new Thread(mthread).start();
							}else{
								 progressDialog = ProgressDialog.show(SocialSendWeiboActivity.this, "Loading...", "发送中...", true, true);
								new Thread(mthread2).start();
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							 progressDialog = ProgressDialog.show(SocialSendWeiboActivity.this, "Loading...", "发送中...", true, true);
							new Thread(mthread2).start();
						}
						new Thread(mthread_tomyservice).start();
						
					}
				});
				
	}
	 private final Runnable mthread_tomyservice = new Runnable() {
		  public void run() {
		        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
  				Map<String,String>params=new HashMap<String,String>()  ;
  			    params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
  			    params.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
  			    
					 String urlcons = SppaConstant.WALKIN_URL_EVENTS
					 +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/event/weibo/posted"+"?apikey=BYauu6D9"
					 ;
					 Log.d("Rock", urlcons+"urlcons:"+"LLLLL");
					 user_mservice.setRetrieveUrl(urlcons);
					 user_mservice.retrieveEventsInfoParams(params);
					 String user_login_meta_code = user_mservice.getCode();
					 Log.d("Rock", user_login_meta_code+"user_login_meta_code:"+"LLLLL");
			  
		         }
     };
	 private final Runnable mthread2 = new Runnable() {
         public void run() {
        	 String editStr = Edit.getText().toString();
			 Weibo weibo=OAuthConstant.getInstance().getWeibo();
			weibo.setToken(editaccessToken, editaccessTokenSecret);
			try {
				String strText = null;
				Status status = null;
				if (!"".equals(editStr)) {
					strText = editStr+"\n"+"#Walkin沃迎#";
						 status= weibo.updateStatus(strText);
				    	Message msg= Message.obtain(mProgressHandler,2);
						 msg.sendToTarget();
				}else{
					Message msg= Message.obtain(mProgressHandler,3);
					 msg.sendToTarget();
				}
				} catch (WeiboException e) {
				e.printStackTrace();
				if (e.getStatusCode() == 400) {
					// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
				         	Message msg= Message.obtain(mProgressHandler,3);
							 msg.sendToTarget();
					
				} else if (e.getStatusCode() == 403) {
					// 帐号密码错误
				}
				
				return;
			}
	  
         }
     };
	  private final Runnable mthread = new Runnable() {
	         public void run() {
	        	 String editStr = Edit.getText().toString();
				 Weibo weibo=OAuthConstant.getInstance().getWeibo();
				weibo.setToken(editaccessToken, editaccessTokenSecret);
				try {
					String strText = null;
					Status status = null;
					if (!"".equals(editStr)) {
						strText = editStr+"\n"+"#Walkin沃迎#";
						if (!"".equals(file.getPath())) {
							status= weibo.uploadStatus(strText, file);
						}else{
							 status= weibo.updateStatus(strText);
						}
						Log.d("Rock",status.getId()+"");
						
						if (!"".equals(status.getId())&&status.getId()!=0) {
					    	Message msg= Message.obtain(mProgressHandler,2);
							 msg.sendToTarget();
						}else{
					    	Message msg= Message.obtain(mProgressHandler,3);
							 msg.sendToTarget();
						}
					}else{
						// strText = editStr;
						Message msg= Message.obtain(mProgressHandler,1);
						 msg.sendToTarget();
						 //Toast.makeText(SocialSendWeiboActivity.this, "发送内容为空", Toast.LENGTH_SHORT).show();
					}
				
				} catch (WeiboException e) {
					e.printStackTrace();
					if (e.getStatusCode() == 400) {
						// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
					         	Message msg= Message.obtain(mProgressHandler,3);
								 msg.sendToTarget();
						
					} else if (e.getStatusCode() == 403) {
						// 帐号密码错误
					}
					
					return;
				}
		  
	         }
	     };
	private Bitmap myBitmap;
	private byte[] mContent;
	 @Override   
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   
	        if (resultCode == NONE)   
	            return;   
	        // 拍照    
	        if (requestCode == PHOTOHRAPH) {   
	            //设置文件保存路径这里放在跟目录下    
	           /*  picture = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+ ".jpg");   
//	            startPhotoZoom(Uri.fromFile(picture)); 
	             Log.d("Rock", picture+"11111111111");
					BitmapFactory.Options options = new BitmapFactory.Options();
					Bitmap bm = BitmapFactory.decodeFile(picture+"", options);
//					ByteArrayOutputStream stream = new ByteArrayOutputStream();   
//          	    bm.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件    
			
					imageView.setImageBitmap(bm);*/
	        	try
				{
					super.onActivityResult(requestCode, resultCode, data);
					
					Drawable drawable = Drawable.createFromPath(path);  
					BitmapDrawable bd = (BitmapDrawable) drawable;
					myBitmap = bd.getBitmap();
					
					//Bundle extras = data.getExtras();
					//myBitmap = (Bitmap) extras.get("data");
					
				//	String name = data.getStringExtra("name"); 
					//File picture = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+ ".jpg"); 
					//path= picture.getPath();
					//file=new File(path);
				//	 Log.d("Rock", name);
					 //Log.d("Rock", picture.getPath()+"");
//					ByteArrayOutputStream baos = new ByteArrayOutputStream();
//					myBitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
					 // file=new File(path);
					int width = myBitmap.getWidth();
					int height = myBitmap.getHeight();
					myBitmap = Bitmap.createScaledBitmap(myBitmap, width/2, height/2, false);
					  file=NetworkToPic.getFileFromBytes(NetworkToPic.getBytes(myBitmap), path);
					Log.d("Rock", "img:"+"1111111");
					 drawable = NetworkToPic.resizeImage(myBitmap, 150, 150);
					Edit.insertIcon(drawable, " ");
					
				} catch ( Exception e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 把得到的图片绑定在控件上显示
				//imageView.setImageBitmap(myBitmap);
				
	        	
	        	
	        }   
	           
	        if (data == null)   
	            return;   
	           
	        // 读取相册缩放图片    
	        if (requestCode == PHOTOZOOM) {   
	        	Log.d("Rock", file+"2222222222");
	        	
	            //startPhotoZoom(data.getData());   
	        }   
	        // 处理结果    
	        if (requestCode == PHOTORESOULT) {   
	        	//Log.d("Rock", data.getData()+"333333333333");
	            	 ContentResolver resolver = getContentResolver();
	                Uri originalUri = data.getData();        //获得图片的uri 
	                try {
	                	myBitmap= MediaStore.Images.Media.getBitmap(resolver, originalUri);
                	    ByteArrayOutputStream stream = new ByteArrayOutputStream();   
//                	    myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);// (0 - 100)压缩文件    
	                	   String[] proj = {MediaStore.Images.Media.DATA};
	                       //好像是android多媒体数据库的封装接口，具体的看Android文档
	                       Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
	                       //按我个人理解 这个是获得用户选择的图片的索引值
	                       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	                       //将光标移至开头 ，这个很重要，不小心很容易引起越界
	                       cursor.moveToFirst();
	                       //最后根据索引值获取图片路径
	                        path = cursor.getString(column_index);
//	                        file=new File(path);
	                        int width = myBitmap.getWidth();
	    					int height = myBitmap.getHeight();
	    					myBitmap = Bitmap.createScaledBitmap(myBitmap, width/2, height/2, false);
	    					file=NetworkToPic.getFileFromBytes(NetworkToPic.getBytes(myBitmap), path);
	    					Drawable drawable = NetworkToPic.resizeImage(myBitmap, 150, 150);
	                //	imageView.setImageBitmap(myBitmap);   
	                	Edit.insertIcon(drawable," ");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}        //显得到bitmap图片
	        }   
	        super.onActivityResult(requestCode, resultCode, data);   
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
