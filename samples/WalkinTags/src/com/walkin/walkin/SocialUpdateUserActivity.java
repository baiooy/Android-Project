package com.walkin.walkin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.AsyncImageLoader3;
import com.walkin.common.Common;
import com.walkin.common.FormFile;
import com.walkin.common.NetworkToPic;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SocialUpdateUserActivity extends Activity {
	/** Called when the activity is first created. */
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
	private EditText EditText_UserName;
	private ImageView ImageView_UserName,ImageView_UserImage ;
	private Button  Button_Next,Button_upload;
	protected final static UserMenuService user_mservice = UserMenuService
			.getInstance();
	private static Context context;
	private SharedPreferences sp;
	public String editaccessToken;
	public String editaccessTokenSecret;
	public String editUserNameStr;
	public boolean  mIsUserName= false;
	public boolean  mIsUserPic= false;
	public User weibo_user;
	private MyTask mTask;

    public static final int NONE = 0;   
    public static final int PHOTOHRAPH = 11;// 拍照    
    public static final int PHOTOZOOM = 12; // 缩放    
    public static final int PHOTORESOULT = 13;// 结果    
    public static final String IMAGE_UNSPECIFIED = "image/*";   
    File picture;
    Bitmap photo;
    String act_data;
    String profileImageURL="";
    byte[] data2_img = null;
    private OnClickListener imgViewListener;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_updateuser_act);

		context = this;
		user_mservice.setActivity(this);
		EditText_UserName = (EditText) findViewById(R.id.EditText_UserName);
		EditText_UserName.setOnTouchListener(new UserNameEditTextListener());
		ImageView_UserImage=(ImageView) findViewById(R.id.ImageView_UserImage);
		ImageView_UserName=(ImageView) findViewById(R.id.ImageView_UserName);
		Button_Next = (Button) findViewById(R.id.Button_Next);
		Button_upload = (Button) findViewById(R.id.Button_upload);
		//ImageView_UserName.setOnTouchListener(new ImageView_UserNameListener());
		Button_Next.setOnClickListener(new Button_NextListener());
		Button_upload.setOnClickListener(new Button_uploadListener());
		sp = getSharedPreferences("user",Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		
		
		Bundle bundle = getIntent().getExtras();    
		 act_data =bundle.getString("Activity");
		String wuweibo =bundle.getString("Data");
		
		 if("".equals(act_data)){
			 String ovu =bundle.getString("oauth_verifier_url");
			 String []ovurl=ovu.trim().split("oauth_verifier=");
//			 Log.d("Rock", ovurl[1]+":ovu");
			try {
				RequestToken requestToken= OAuthConstant.getInstance().getRequestToken();
				AccessToken accessToken=requestToken.getAccessToken(ovurl[1]);
				OAuthConstant.getInstance().setAccessToken(accessToken);
				sp.edit().putString("accessToken",accessToken.getToken()).commit();
				sp.edit().putString("accessTokenSecret",accessToken.getTokenSecret()).commit();
			//	textView.setText("得到AccessToken的key和Secret,可以使用这两个参数进行授权登录了.\n Access token:\n"+accessToken.getToken()+"\n Access token secret:\n"+accessToken.getTokenSecret());
			} catch (WeiboException e) {
				e.printStackTrace();
			}
		 }
		 if ("wuweibo".equals(wuweibo)) {
				finish();
				return;
			}
		 if (data2_img==null) {
				Button_upload.setVisibility(View.GONE);
			}
			
		
		
		
		
		//sp = getSharedPreferences("user", 0);
		editaccessToken = sp.getString("accessToken", null);
		editaccessTokenSecret = sp.getString("accessTokenSecret", null);
		 mProgressHandler = new Handler() {   
	            public void handleMessage(Message msg) { 
	                switch (msg.what){   
	                case 1 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
	              			SocialUpdateUserActivity.this.finish();
	        			break; 
	                case 2 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "sssssssssss");
	            			Intent intent = new Intent(SocialUpdateUserActivity.this, IndexActivity.class);
	            			Bundle bundle=new  Bundle();
	         				String str1="aaaaaa";
	         				bundle.putString("Data", str1);
	         				intent.putExtras(bundle);
	         				startActivityForResult(intent, 0);
	         				try {
	         					Message message = Message.obtain(AuthorizationAct.getExitHandler(),0);
		        				message.sendToTarget();
							} catch (Exception e) {
								// TODO: handle exception
							}
	         			
	         				
//	         				 intent = getIntent();
//	              			setResult(1, intent); //intent为
	              			SocialUpdateUserActivity.this.finish();
	        			break; 
	                case 4 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "ddddddddd");
	                	 AlertDialog.Builder b1 = new AlertDialog.Builder(SocialUpdateUserActivity.this);
	                	 b1.setTitle("抱歉");
	    		            b1.setMessage("用户名已经存在");
	    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	    		            b1.show();
	                    break;  
	                case 3 :   
//	                	if(progressDialog !=null){
	                		progressDialog.dismiss();
//	                		progressDialog =null;
//	                	}
	                	 AlertDialog.Builder b2 = new AlertDialog.Builder(SocialUpdateUserActivity.this);
	                	 b2.setTitle("恭喜");
	                	 b2.setMessage("上传成功");
	                	 b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	                	 b2.show();
	                    break;  
	                case 5 :   
//	                	if(progressDialog !=null){
//	                		progressDialog =null;
//	                	}
	                	 Log.d("Rock", "ddddddddd");
	                	 b1 = new AlertDialog.Builder(SocialUpdateUserActivity.this);
	                	 b1.setTitle("抱歉");
	    		            b1.setMessage("用户名用户图片为空");
	    		            b1.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	    		                public void onClick(DialogInterface dialog, int which){
	    		                }
	    		            });
	    		            b1.show();
	                    break;
	                case 6:
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
		
		mTask = new MyTask();
		mTask.execute();
		
		
		
		imgViewListener = new OnClickListener()
		{
			public void onClick ( View v )
			{
			final CharSequence[] items =
			{ "相册", "拍照" };
			AlertDialog dlg = new AlertDialog.Builder(SocialUpdateUserActivity.this).setTitle("选择").setItems(items,
					new DialogInterface.OnClickListener()
					{
						public void onClick ( DialogInterface dialog , int item )
						{
							// 这里item是根据选择的方式，
							// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
							if (item == 0)
							{
								 Intent intent = new Intent(Intent.ACTION_PICK, null);   
					                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);   
					                startActivityForResult(intent, PHOTORESOULT);   
							} else
							{
//								Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
//								getImage.addCategory(Intent.CATEGORY_OPENABLE);
//								getImage.setType("image/jpeg");
								Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
//				                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   
//				                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));   
				                startActivityForResult(getImageByCamera, PHOTOHRAPH);   
				                Toast.makeText(SocialUpdateUserActivity.this, "请上传分辨率较小的图片", Toast.LENGTH_SHORT).show();
							}
						}
					}).create();
			dlg.show();
		}
		};
		// 给imageView控件绑定点点击监听器
		ImageView_UserImage.setOnClickListener(imgViewListener);
	}

	private class MyTask extends AsyncTask<String, Integer, String> {
		private static final String TAG = "Rock";

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute() called");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "doInBackground(Params... params) called");
			
			Weibo weibo=OAuthConstant.getInstance().getWeibo();
			
			weibo.setToken(editaccessToken, editaccessTokenSecret);
			List<Status> friendsTimeline;
				try {
					 if("".equals(act_data)){
					weibo_user = weibo.verifyCredentials();
					profileImageURL=weibo_user.getProfileImageURL()+"";
					 }
				} catch (WeiboException e) {
					e.printStackTrace();
					if (e.getStatusCode() == 400) {
						// 内容重复，新浪微博不允许重复的内容发布 如果内容重复会在这里抛出异常
					} else if (e.getStatusCode() == 403) {
						// 帐号密码错误
					}
				}
			return null;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "onPostExecute(Result result) called");
			String weibo_name = null;
			
				 if("".equals(act_data)){
						 weibo_name = weibo_user.getName();
					 }else {
						 try {
						String pic_path = (String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_imageUrl);
						if ("http".equals(pic_path.substring(0, 4))) {
							profileImageURL =pic_path;
						}else{
							profileImageURL =SppaConstant.WALKIN_URL_IP+pic_path;
						}
						 } catch (Exception e) {
								// TODO: handle exception
							}
						weibo_name =(String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_userName);
					}
			
				 loadImage4(profileImageURL,(ImageView) findViewById(R.id.ImageView_UserImage));
			EditText_UserName.setText(weibo_name);
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Log.i(TAG, "onCancelled() Stopcalled");
		}
	}

	private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();

	// 采用Handler+Thread+封装外部接口
	private void loadImage5(final String url, final ImageView imageView) {
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = asyncImageLoader3.loadDrawable(url,
				new AsyncImageLoader3.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {

						if (imageDrawable != null) {
							imageView.setImageDrawable(imageDrawable);
						} else {
							imageView.setImageResource(R.drawable.none_color);
						}
					}
				});
		if (cacheImage != null) {
			imageView.setImageDrawable(cacheImage);
		} else {
			imageView.setImageResource(R.drawable.none_color);
		}
	}
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(SocialUpdateUserActivity.this, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	            	   imageView.setImageDrawable(imageDrawable);
	               }else{
	            		 imageView.setImageResource(R.drawable.profile_default_pic);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	imageView.setImageDrawable(cachedImage);
	        }else{
    		 imageView.setImageResource(R.drawable.profile_default_pic);
    	 }
	    	}
	 class UserNameEditTextListener implements OnTouchListener {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				 ImageView_UserName.setVisibility(View.GONE);
				return false;
			}
		}
	class Button_NextListener implements OnClickListener {
		public void onClick(View v) {
		/*	 if (data2==null||profileImageURL=="") {
				 Log.d("Rock", profileImageURL+"333333333");
				 mIsUserPic = false;
	 		}else{
	 			 Log.d("Rock", profileImageURL+"44444444444");
	 			mIsUserPic = true;
	 		}*/
			
			 editUserNameStr = EditText_UserName.getText().toString();
			if ("".equals(editUserNameStr)) {
				ImageView_UserName.setVisibility(View.VISIBLE);
				mIsUserName = false;
			}else{
				mIsUserName = true;
			}
			 if (mIsUserName==true) {
				 
//				if (mIsUserPic== false) {
//					 Message msg= Message.obtain(mProgressHandler,5);
//		           	 msg.sendToTarget(); 
//				}else{
				 
				 if(isNetworkAvailable()){
					 progressDialog = ProgressDialog.show(SocialUpdateUserActivity.this, "Loading...", "Please wait...", true, false);
		        	 if("".equals(act_data)||"SocialRegistrationActivity".equals(act_data)){
		        		 new Thread(mthread).start();
		        		 
		        		 Log.d("Rock", "11111111111");
						}else{
							 new Thread(mthread02).start();
							 Log.d("Rock", "222222222222");
						}
				 }else{
					 Message msg= Message.obtain(mProgressHandler,6);
	            	 msg.sendToTarget();
				 }
				 
			
//				}
			 }
		}
	}
	
	class Button_uploadListener implements OnClickListener {
		public void onClick(View v) {
			
			 editUserNameStr = EditText_UserName.getText().toString();
			if ("".equals(editUserNameStr)) {
				ImageView_UserName.setVisibility(View.VISIBLE);
				mIsUserName = false;
			}else{
				mIsUserName = true;
			}
			 if (mIsUserName==true) {
				 
				 if(isNetworkAvailable()){
					 progressDialog = ProgressDialog.show(SocialUpdateUserActivity.this, "Loading...", "Please wait...", true, false);
		        	 new Thread(mthread_upload).start();
				 }
				 else{
					 Message msg= Message.obtain(mProgressHandler,6);
	            	 msg.sendToTarget();
				 }
        	
			 }
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
	private final Runnable mthread_upload = new Runnable() {
        public void run() {
       //	 Log.d("Rock", weibo_user.getProfileImageURL()+";;;");
        	//String str = null;
    		FormFile formfile = new FormFile("android_profile_img.jpg",data2_img, "img", null);
    		final FormFile[] files = new FormFile[]{formfile};
    		
    		final Map<String, String> params = new HashMap<String, String>();
    		params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
       	    params.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
    		
       	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/upload"+"?"
         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
    			
			//	str = FileUtil.post(urlcons, params, files);
			//	Toast.makeText(SocialUpdateUserActivity.this, str, Toast.LENGTH_LONG).show();
    				
			 user_mservice.setRetrieveUrl(urlcons);
			 user_mservice.retrieveUserUpload(params,files);
			 String user_login_meta_code = user_mservice.getCode();
			 Log.d("Rock", user_login_meta_code+"LLLLL");
			 if ("200".equals(user_login_meta_code)) {
				 Message msg= Message.obtain(mProgressHandler,3);
           	 msg.sendToTarget(); 
			}else{
				 Message msg= Message.obtain(mProgressHandler,4);
           	 msg.sendToTarget();
			}
		  
        }
    };
	
    private final Runnable mthread02 = new Runnable() {
        public void run() {
       	// Log.d("Rock", weibo_user.getProfileImageURL()+";;;");
			 Map<String,String>params=new HashMap<String,String>()  ;//user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID
			 params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
	    	 params.put("userName",editUserNameStr);
	    	 params.put("address","");
	    	 params.put("weiboAccount","");
	    	 params.put("firstName","");
	    	 params.put("lastName","");
	    	 params.put("phoneNumber","");
	    	 params.put("gender","");
	    	 params.put("ageGroup","");
	    	 params.put("style","");
	    	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/update"+"?"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
			 user_mservice.setRetrieveUrl(urlcons);
			 user_mservice.retrieveUserUpdateInfo(params);
			 String user_login_meta_code = user_mservice.getCode();
			 Log.d("Rock", user_login_meta_code+"LLLLL");
			 if ("200".equals(user_login_meta_code)) {
				  Message msg= Message.obtain(mProgressHandler,1);
		           	 msg.sendToTarget(); 
				
			}else{
				 Message msg= Message.obtain(mProgressHandler,4);
           	 msg.sendToTarget();
           	
			}
		  
        }
    };
	
	
	private final Runnable mthread = new Runnable() {
        public void run() {
       	// Log.d("Rock", weibo_user.getProfileImageURL()+";;;");
			 Map<String,String>params=new HashMap<String,String>()  ;//user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID
			 params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
	    	 params.put("userName",editUserNameStr);
	    	 if ("SocialRegistrationActivity".equals(act_data)) {
	    		 params.put("address","");
				}else{
					 params.put("address",weibo_user.getLocation());
			    	 if (data2_img==null) {
			    		 params.put("imageUrl",profileImageURL);
			 		}
				}
	    	
	    	 params.put("weiboAccount",editUserNameStr);
	    	 params.put("firstName","");
	    	 params.put("lastName","");
	    	 params.put("phoneNumber","");
	    	 params.put("gender","");
	    	 params.put("ageGroup","");
	    	 params.put("style","");
	    	 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/update"+"?"
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
			 user_mservice.setRetrieveUrl(urlcons);
			 user_mservice.retrieveUserUpdateInfo(params);
			 String user_login_meta_code = user_mservice.getCode();
			 Log.d("Rock", user_login_meta_code+"LLLLL");
			 if ("200".equals(user_login_meta_code)) {
				  Message msg= Message.obtain(mProgressHandler,2);
		           	 msg.sendToTarget(); 
				
			}else{
				 Message msg= Message.obtain(mProgressHandler,4);
           	 msg.sendToTarget();
           	
			}
		  
        }
    };
    private Bitmap myBitmap;
//	private byte[] mContent;
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
					Log.d("Rock", "img:"+bm);
					imageView.setImageBitmap(bm);*/
	        	try
				{
					super.onActivityResult(requestCode, resultCode, data);
					Bundle extras = data.getExtras();
					myBitmap = (Bitmap) extras.get("data");
//					ByteArrayOutputStream baos = new ByteArrayOutputStream();
//					myBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
					int width = myBitmap.getWidth();
					int height = myBitmap.getHeight();
					myBitmap = Bitmap.createScaledBitmap(myBitmap, width/2, height/2, false);
//					myBitmap = Bitmap.createScaledBitmap(myBitmap, 200, 200, false);
					data2_img = NetworkToPic.getBytes(myBitmap);
//					mContent = baos.toByteArray();
				} catch ( Exception e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 把得到的图片绑定在控件上显示
				ImageView_UserImage.setImageBitmap(myBitmap);
				Button_upload.setVisibility(View.VISIBLE);
	        	
	        	
	        	
	        }   
	           
	        if (data == null)   
	            return;   
	           
	        // 读取相册缩放图片    
	        if (requestCode == PHOTOZOOM) {   
	        	Log.d("Rock", picture+"2222222222");
	        	
	            //startPhotoZoom(data.getData());   
	        }   
	        // 处理结果    
	        if (requestCode == PHOTORESOULT) {   
	        	//Log.d("Rock", data.getData()+"333333333333");
	            	 ContentResolver resolver = getContentResolver();
	                Uri originalUri = data.getData();        //获得图片的uri 
	                Log.d("Rock", originalUri+":originalUri");
	                try {
	                	myBitmap= MediaStore.Images.Media.getBitmap(resolver, originalUri);
//	                	ByteArrayOutputStream stream = new ByteArrayOutputStream();   
//	                	myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);// (0 - 100)压缩文件    
	                	int width = myBitmap.getWidth();
						int height = myBitmap.getHeight();
						myBitmap = Bitmap.createScaledBitmap(myBitmap, width/2, height/2, false);
	        			data2_img = NetworkToPic.getBytes(myBitmap);
	                	ImageView_UserImage.setImageBitmap(myBitmap);
	                	Button_upload.setVisibility(View.VISIBLE);
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