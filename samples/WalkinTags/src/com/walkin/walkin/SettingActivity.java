package com.walkin.walkin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.DownLoadApk;
import com.walkin.common.MyButton;
import com.walkin.common.ThreadForRunnable;
import com.walkin.service.DefaultsMenuService;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;
import com.walkin.view.UpgradeDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends BgWalkinActivity{
    private MyButton myhome;
    private static SettingActivity instance = null;
    private LinearLayout  LinearLayout_outwalkin,
    LinearLayout_updatename,LinearLayout_Preferences,
    LinearLayout_outweibo,LinearLayout_checknewversion,
    LinearLayout_aboutour,LinearLayout_terms,LinearLayout_cache;
	private SharedPreferences sp;
	private ToggleButton ToggleButton_ischeck,ToggleButton_isTransparentAll;
	private TextView TextView_walkin_name,TextView_Versionnumber,TextView_cnumber,TextView_ctime;
    private static Context context;
    private MyTaskMeInbiBalance mTaskInbi;  
	private Button goback ;
	private Animation myAnimation_Alpha;
	String ischeck,isTransparentAll;
	PackageManager pm;
    private ProgressDialog progressDialog_d; 
    public static Handler mProgressHandler =null;
	  protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);
        
        
        context =SettingActivity.this;
        defaults_mservice.setActivity(this);
        user_mservice.setActivity(this);
        sp = getSharedPreferences("user", 0);
        pm = getPackageManager();
		  //  EditText_Email.setText(sp.getString("weiboEmail", null));
		   // EditText_Password.setText(sp.getString("weiboPassword", null));
        LinearLayout_outwalkin=(LinearLayout) findViewById(R.id.LinearLayout_outwalkin);
        LinearLayout_outwalkin.setOnClickListener(new LinearLayout_outwalkinListener());
        LinearLayout_updatename=(LinearLayout) findViewById(R.id.LinearLayout_updatename);
        LinearLayout_updatename.setOnClickListener(new LinearLayout_updatenameListener());
        TextView_walkin_name=(TextView) findViewById(R.id.TextView_walkin_name);
        TextView_walkin_name.setText(sp.getString("email", null));
        ischeck=sp.getString("ischeck", null);
        isTransparentAll=sp.getString("istransparentall", null);
        TextView_Versionnumber=(TextView) findViewById(R.id.TextView_Versionnumber);
        TextView_Versionnumber.setText(SppaConstant.APP_VERSION);
        LinearLayout_outweibo=(LinearLayout) findViewById(R.id.LinearLayout_outweibo);
        LinearLayout_outweibo.setOnClickListener(new LinearLayout_outweiboListener());
        TextView_cnumber=(TextView) findViewById(R.id.TextView_cnumber);
        TextView_cnumber.setText(user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
        TextView_ctime=(TextView) findViewById(R.id.TextView_ctime);
        TextView_ctime.setText((String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_registeredDate));
        LinearLayout_Preferences=(LinearLayout) findViewById(R.id.LinearLayout_Preferences);
        LinearLayout_Preferences.setOnClickListener(new LinearLayout_PreferencesListener());
        LinearLayout_checknewversion=(LinearLayout) findViewById(R.id.LinearLayout_checknewversion);
        LinearLayout_checknewversion.setOnClickListener(new LinearLayout_checknewversionListener());
        LinearLayout_aboutour=(LinearLayout) findViewById(R.id.LinearLayout_aboutour);
        LinearLayout_aboutour.setOnClickListener(new LinearLayout_aboutourListener());
        LinearLayout_terms=(LinearLayout) findViewById(R.id.LinearLayout_terms);
        LinearLayout_terms.setOnClickListener(new LinearLayout_termsListener());
        LinearLayout_cache=(LinearLayout) findViewById(R.id.LinearLayout_cache);
        LinearLayout_cache.setOnClickListener(new LinearLayout_cacheListener());
        ToggleButton_ischeck=(ToggleButton) findViewById(R.id.ToggleButton_ischeck);
        if ("1".equals(ischeck)) {
        	ToggleButton_ischeck.setChecked(true);
		}else{
			ToggleButton_ischeck.setChecked(false);
		}
        ToggleButton_isTransparentAll=(ToggleButton) findViewById(R.id.ToggleButton_isTransparentAll);
        if ("1".equals(isTransparentAll)) {
        	ToggleButton_isTransparentAll.setChecked(true);
		}else{
			ToggleButton_isTransparentAll.setChecked(false);
		}
        
        progressDialog_d = new ProgressDialog(SettingActivity.this);
        progressDialog_d.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog_d.setIndeterminate(false);
        progressDialog_d.setMessage("下载中，请稍候...");  
        progressDialog_d.setCancelable(true);
        ToggleButton_ischeck.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
//				isChecked就是按钮状态
				if(isChecked){
					sp.edit().putString("ischeck","1").commit();
				}else{
					sp.edit().putString("ischeck","0").commit();
					
 
				}
			}
 
        });
        ToggleButton_isTransparentAll.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
//				isChecked就是按钮状态
				if(isChecked){
					sp.edit().putString("istransparentall","1").commit();
				}else{
					sp.edit().putString("istransparentall","0").commit();
					
 
				}
			}
 
        });
        goback= (Button) findViewById(R.id.goback);
  		goback.setOnClickListener(new gobackListener()); 
  		
  		
  		
  		mProgressHandler = new Handler() {   
            @Override  
            public void handleMessage(Message msg) { 
                switch (msg.what){  
                case 1:
                	//检查是否需要升级
                		//if(needUpgrade()){
                			//StartupUpgradeAlert();
                		//}else{
                        	//ContinueLoadWcc();
                		//}
                	break;
	            case 3 :   //下载等待
	            	if(progressDialog_d !=null){
	            		progressDialog_d.show();
	            		progressDialog_d.setOnKeyListener(new DialogInterface.OnKeyListener() {
	                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
	                            AlertDialog.Builder b = new AlertDialog.Builder(SettingActivity.this);
	                            b.setTitle("警告");
	                            b.setMessage("你确定要取消吗?");
	                            b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	                                public void onClick(DialogInterface dialog, int which){
	                                	try{
	            			            	Message message = Message.obtain(DownLoadApk.getListenerHandler(),0);
	            			                message.sendToTarget();
	            			            	}catch (Exception e){
	            			                }
	                                	progressDialog_d.dismiss();
	                                }
	                            });
	                            b.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	                                public void onClick(DialogInterface dialog, int which) {
	                                }
	                            });
	                            b.show();
	                            
	                            return true;
	                        }
	                    });
	            		
	            		String tmp = defaults_mservice.getDownClientUrl();
	            		Log.e("Rock", tmp+":tmp");
	            		ThreadForRunnable thread = new ThreadForRunnable(context,progressDialog_d,mProgressHandler,tmp);
	            		new Thread(thread).start();
	            	}
	                break;   
	            case 4 :   
	            	if(progressDialog_d !=null){
	            		int size = msg.getData().getInt("size");
	            		progressDialog_d.setProgress(size);
	            	}
	                break;
	            case 5 :   
	            	if(progressDialog_d !=null){
	            		try{
	            			progressDialog_d.dismiss();
	            			isUpgrading = false;
                        	final File f = new File("/sdcard/update/walkin_update.apk");
                        	SetupFile(f);
                        	OnCloseAllActivity();
	            		}catch(Exception e){
	            			
	            		}
	            	}
	                break;
	            case -1://下载错误
	    			String error = msg.getData().getString("error");
	    			Toast.makeText(context, error, 1).show();
	    			if(progressDialog_d !=null)
	    				progressDialog_d.dismiss();
//                	ContinueLoadWcc();
	    			break;
	            
	            } 
                super.handleMessage(msg);
            } 
        };  
  		
  		
  		
  		
  		
  		
  		
  		
        
    }
/*	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(SettingActivity.this, MeActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
            startActivity(intent);
		}
	}*/
    public void OnCloseAllActivity(){
    	
    	Intent	intent = getIntent();
    	try {
			Message message = Message.obtain(IndexActivity.getExitHandler(),0);
			message.sendToTarget();
			message = Message.obtain(StoreHomeActivity.getExitHandler(),0);
			message.sendToTarget();
			message = Message.obtain(BrandsActivity.getExitHandler(),0);
			message.sendToTarget();
			message = Message.obtain(DealsActivity.getExitHandler(),0);
			message.sendToTarget();
			message = Message.obtain(GamesActivity.getExitHandler(),0);
			message.sendToTarget();
			message = Message.obtain(SocialActivity.getExitHandler(),0);
			message.sendToTarget();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
		finish();//此处一定要调用finish()方
    }
    class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
		}
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
       	 //Button_ME.setText(user_mservice.getInbiBalance());
        }  
          
    }
	  protected void SetupFile(File f){
	      Intent intent = new Intent();
	      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      intent.setAction(android.content.Intent.ACTION_VIEW);
	      intent.setDataAndType(Uri.fromFile(f),"application/vnd.android.package-archive");
	      startActivity(intent);
	 
	  }
	protected void onRestart() {
		Log.d("lifecycle", "onRestart()");
		// TODO Auto-generated method stub
		 mTaskInbi = new MyTaskMeInbiBalance();  
	 	 mTaskInbi.execute(); 
		super.onRestart();
	}
	
	class LinearLayout_outweiboListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			 LinearLayout_outweibo.startAnimation(myAnimation_Alpha);
			AlertDialog.Builder b2 = new AlertDialog.Builder(SettingActivity.this);
			b2.setTitle("退出微博");
			b2.setMessage("退出微博后，需要重新登陆。确定退出吗？");
			
	    	b2.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which){
	            }
	        });
	    	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which){
	            	saveSharePreferences_weibo(true);
	            }
	        });
	    	b2.show();
		}
	}
	class LinearLayout_outwalkinListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_outwalkin.startAnimation(myAnimation_Alpha);
			
			
			AlertDialog.Builder b2 = new AlertDialog.Builder(SettingActivity.this);
			b2.setTitle("退出帐号");
			b2.setMessage("退出后需要重新登陆。确定退出吗？");
	    	b2.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which){
	            }
	        });
	    	b2.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which){
	            	saveSharePreferences_outwalkin(true);
	    			Intent intent = new Intent(SettingActivity.this, LoginRegistrationActivity.class);
	    			Bundle mBundle = new Bundle();  
	    			mBundle.putString("Data", "mainbar");//压入数据  
	    			intent.putExtras(mBundle);  
	    			startActivityForResult(intent, 0);
	    			try {
	    				Message message = Message.obtain(IndexActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				message = Message.obtain(StoreHomeActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				message = Message.obtain(BrandsActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				message = Message.obtain(DealsActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				message = Message.obtain(GamesActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				message = Message.obtain(SocialActivity.getExitHandler(),0);
	    				message.sendToTarget();
	    				intent = getIntent();
	    			} catch (Exception e) {
	    				// TODO: handle exception
	    			}
	    			
	    			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
	    			finish();//此处一定要调用finish()方
	            }
	        });
	    	b2.show();
			
			
			
		}
	}
	class LinearLayout_updatenameListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_updatename.startAnimation(myAnimation_Alpha);
			Intent intent = new Intent(SettingActivity.this, SocialUpdateUserActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Activity", "SettingActivity");//压入数据  
        	mBundle.putString("oauth_verifier_url", "");//压入数据  
        	mBundle.putString("Data", "false");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class LinearLayout_PreferencesListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_Preferences.startAnimation(myAnimation_Alpha);
			Intent intent = new Intent(SettingActivity.this, GenderAgeActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "SettingActivity");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class LinearLayout_checknewversionListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_checknewversion.startAnimation(myAnimation_Alpha);
			Log.d("Rock", ":Setting:LinearLayout_checknewversionListener");
			MainUpdateDialog(SettingActivity.this);
			
			
		}
	}
	 public void MainUpdateDialog(final Activity mActivity)
	  {
		 if(needUpgrade()){
			 UpgradeDialog udlg = new UpgradeDialog(this,1);
			 String str = "检测到新版本"+defaults_mservice.getUpgradeVersion();
			 udlg.SetUpgradeDialogTitle(str);
			 str = defaults_mservice.getIntroductionUpgrade();
			 udlg.SetUpgradeDialogContent(str);
			 udlg.show();
		 }else{
			  AlertDialog.Builder builders = new AlertDialog.Builder(mActivity);
		      builders.setTitle("当前版本"+SppaConstant.APP_VERSION);
		      String upbut ="确定";
		      builders.setMessage("已是最新版");
	          builders.setPositiveButton(upbut,  new DialogInterface.OnClickListener(){
		          public void onClick(DialogInterface dialog, int which){}
		          	});
	          builders.show();
		}
	   } 
	
	class LinearLayout_aboutourListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_aboutour.startAnimation(myAnimation_Alpha);
			Intent intent = new Intent(SettingActivity.this, SettingWebActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("DataUrl",SppaConstant.WALKIN_URL_ABOUT);//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class LinearLayout_termsListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			LinearLayout_terms.startAnimation(myAnimation_Alpha);
			Intent intent = new Intent(SettingActivity.this, SettingWebActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("DataUrl", SppaConstant.WALKIN_URL_TERMS);//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
		}
	}
	class LinearLayout_cacheListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(SettingActivity.this,R.anim.my_alpha_action);
			 LinearLayout_cache.startAnimation(myAnimation_Alpha);
			 AlertDialog.Builder b = new AlertDialog.Builder(SettingActivity.this);
  	         b.setTitle("提示");
  	         b.setMessage("是否清理缓存");
  	         b.setNegativeButton("取消", new DialogInterface.OnClickListener(){
  	             public void onClick(DialogInterface dialog, int which){
  	            	
  	             }
  	         });
  	         b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
  	             public void onClick(DialogInterface dialog, int which) {
  	            	 clearnCrash1();
  	             }
  	         });
  	         b.show();
			 
		}
	}
	private  long getEnvironmentSize()
    {
      File localFile = Environment.getDataDirectory();
      long l1;
      if (localFile == null)
        l1 = 0L;
      while (true)
      {
        
        String str = localFile.getPath();
        StatFs localStatFs = new StatFs(str);
        long l2 = localStatFs.getBlockSize();
        l1 = localStatFs.getBlockCount() * l2;
        return l1;
      }
    }
	public void clearnCrash1(){
		try {
			Method localMethod = pm.getClass().getMethod("freeStorageAndNotify", Long.TYPE,IPackageDataObserver.class);
			Long localLong = Long.valueOf(getEnvironmentSize() - 1L);
			Object[] arrayOfObject = new Object[2];
		      arrayOfObject[0] = localLong;
		      localMethod.invoke(pm,localLong,new IPackageDataObserver.Stub(){
				@Override
				public void onRemoveCompleted(String packageName,
						boolean succeeded) throws RemoteException {
				}});
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void saveSharePreferences_outwalkin(boolean mIsSaveUser)  {
		sp = getSharedPreferences("user",Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		if (mIsSaveUser==true) {
//			sp.edit().putString("email","").commit();
			sp.edit().putString("accessToken","").commit();
			sp.edit().putString("accessTokenSecret","").commit();
			user_mservice.setValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
			user_mservice.setValue(UserMenuService.USER_REGISTER_RESPONSE_ID);
			//sp.edit().putString("password","").commit();
		}
	}
	private void saveSharePreferences_weibo(boolean mIsSaveUser)  {
		sp = getSharedPreferences("user",Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		if (mIsSaveUser==true) {
		//	sp.edit().putString("email","").commit();
			sp.edit().putString("accessToken","").commit();
			sp.edit().putString("accessTokenSecret","").commit();
		//	user_mservice.setValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
		//	user_mservice.setValue(UserMenuService.USER_REGISTER_RESPONSE_ID);
			//sp.edit().putString("password","").commit();
		}
	}
/*	class button_gamesActlistListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(SettingActivity.this, GamesActListActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
            startActivity(intent);
			
		}
	}*/
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
