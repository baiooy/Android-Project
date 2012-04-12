package com.walkin.walkin;

import java.util.HashMap;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GenderAgeActivity extends Activity{
	
	
	private RadioGroup RadioGroup_FemaleOrMale,RadioGroup_Age;
	private RadioButton RadioButton_Female,RadioButton_Male,RadioButton_erold,
	RadioButton_sanold,RadioButton_siold,RadioButton_wuold,RadioButton_liuold;
	private Button Button_Next ;
//	private Button goback ;
	public int mFemaleOrMale_int = 9;//默认值 因为 0 1 已经占用
	public int mRadioGroup_Age_int = 9;//默认值
	public boolean mIsSendPass = false;
	public boolean  mIsSendEmail= false;
	private SharedPreferences sp;
    private ProgressDialog progressDialog; 
    private static Handler mProgressHandler=null;
	protected final static UserMenuService user_mservice = UserMenuService.getInstance();
	private static Context context;
	String data_type;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genderage_act);
      	context =this;
        user_mservice.setActivity(this);
	    Button_Next=(Button) findViewById(R.id.Button_Next);
	    Button_Next.setOnClickListener(new NextButtonListener());
//		goback= (Button) findViewById(R.id.goback);
//		goback.setOnClickListener(new gobackListener()); 
	    RadioGroup_FemaleOrMale =(RadioGroup) findViewById(R.id.RadioGroup_FemaleOrMale);
	    RadioButton_Female =(RadioButton) findViewById(R.id.RadioButton_Female);
	    RadioButton_Male =(RadioButton) findViewById(R.id.RadioButton_Male);
	    
	    RadioGroup_Age =(RadioGroup) findViewById(R.id.RadioGroup_Age);
	    RadioButton_erold =(RadioButton) findViewById(R.id.RadioButton_erold);
	    RadioButton_sanold =(RadioButton) findViewById(R.id.RadioButton_sanold);
	    RadioButton_siold =(RadioButton) findViewById(R.id.RadioButton_siold);
	    RadioButton_wuold =(RadioButton) findViewById(R.id.RadioButton_wuold);
	    RadioButton_liuold =(RadioButton) findViewById(R.id.RadioButton_liuold);

	    RadioGroup_FemaleOrMale.setOnCheckedChangeListener( mRadioGroup_FemaleOrMaleListener);
	    RadioGroup_Age.setOnCheckedChangeListener( mRadioGroup_AgeListener);
	    Log.d("Rock", user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+":USER_REGISTER_RESPONSE_ID");
	    
	    Bundle bundle = getIntent().getExtras();    
		data_type =bundle.getString("Data");
		 if ("SettingActivity".equals(data_type)) {
			String ageGroup= (String) user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_ageGroup);
			String gender= (String) user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_gender);
			if ("0".equals(gender)) {
				RadioButton_Female.setChecked(true);
			}else if ("1".equals(gender)) {
				RadioButton_Male.setChecked(true);
			}
			if("1".equals(ageGroup)){
				RadioButton_erold.setChecked(true);
			}else if("2".equals(ageGroup)){
				RadioButton_sanold.setChecked(true);
			}else if("3".equals(ageGroup)){
				RadioButton_siold.setChecked(true);
			}else if("4".equals(ageGroup)){
				RadioButton_wuold.setChecked(true);
			}else if("5".equals(ageGroup)){
				RadioButton_liuold.setChecked(true);
			}
		 }
	    
	    mProgressHandler = new Handler() {   
            public void handleMessage(Message msg) { 
                switch (msg.what){   
                case 1 :   
                	AlertDialog.Builder b2 = new AlertDialog.Builder(GenderAgeActivity.this);
                	b2.setTitle("抱歉");
                	b2.setMessage("此页不可跳过");
                	b2.setPositiveButton("OK", new DialogInterface.OnClickListener(){
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
                	 Log.d("Rock", "sssssssssss");
                	 
                	 if ("SettingActivity".equals(data_type)) {
                		 Intent intent=new Intent();
 	          			intent.setClass(GenderAgeActivity.this, UserStyleActivity.class);
 	          			Bundle bundle=new  Bundle();
 	          			String str1=data_type;
 	          			bundle.putString("Data", str1);
 	          			bundle.putString("FemaleOrMale", mFemaleOrMale_int+"");
 	          			intent.putExtras(bundle);
 	          			startActivityForResult(intent, 0);
                		 GenderAgeActivity.this.finish();
					}else{
                	  	Intent intent=new Intent();
	          			intent.setClass(GenderAgeActivity.this, UserStyleActivity.class);
	          			Bundle bundle=new  Bundle();
	          			String str1=data_type;
	          			bundle.putString("Data", str1);
	          			bundle.putString("FemaleOrMale", mFemaleOrMale_int+"");
	          			intent.putExtras(bundle);
	          			startActivityForResult(intent, 0);
					}
        			break; 
                case 3 :   
//                	if(progressDialog !=null){
                		progressDialog.dismiss();
//                		progressDialog =null;
//                	}
                	 Log.d("Rock", "ddddddddd");
                		AlertDialog.Builder b1 = new AlertDialog.Builder(GenderAgeActivity.this);
                		b1.setTitle("抱歉");
    		            b1.setMessage("出错");
    		            b1.setPositiveButton("OK", new DialogInterface.OnClickListener(){
    		                public void onClick(DialogInterface dialog, int which){
    		                }
    		            });
    		            b1.show();
                    break;  
                case 4:
                	progressDialog.dismiss();
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
    }
    class gobackListener implements OnClickListener{
		public void onClick(View v) {
			finish();
		}
	}
	private  RadioGroup.OnCheckedChangeListener mRadioGroup_FemaleOrMaleListener=new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedid) {
			// TODO Auto-generated method stub
			
			if(checkedid==RadioButton_Female.getId()){
				mFemaleOrMale_int=0;
			}else if(checkedid==RadioButton_Male.getId()){
				mFemaleOrMale_int=1;
			}
		}
	};
	private  RadioGroup.OnCheckedChangeListener mRadioGroup_AgeListener=new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedid) {
			// TODO Auto-generated method stub
			
			if(checkedid==RadioButton_erold.getId()){
				mRadioGroup_Age_int=1;
			}else if(checkedid==RadioButton_sanold.getId()){
				mRadioGroup_Age_int=2;
			}else if(checkedid==RadioButton_siold.getId()){
				mRadioGroup_Age_int=3;
			}else if(checkedid==RadioButton_wuold.getId()){
				mRadioGroup_Age_int=4;
			}else if(checkedid==RadioButton_liuold.getId()){
				mRadioGroup_Age_int=5;
			}
		}
	};
	 private final Runnable mthread = new Runnable() {
         public void run() {
        	 
        	 sp = getSharedPreferences("user", 0);
        //	 String	s =RegExpValidator.MD5("kevin.hu@gmail.com:walkin:1a2b3c");
		//	 String encodePass =RegExpValidator.encode(s.getBytes());
		//	 String editEmailStr_test="kevin.hu@gmail.com";
        	 Log.d("Rock", mFemaleOrMale_int+":mFemaleOrMale_int"+"\n"+mRadioGroup_Age_int);
			 Map<String,String>params=new HashMap<String,String>()  ;
			 params.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
	    	 params.put("firstName","");
	    	 params.put("lastName","");
	    	 params.put("userName",sp.getString("email", null));
	    	 params.put("address","上海市");
	    	 params.put("phoneNumber","");
	    	 params.put("imageUrl","");
	    	 params.put("weiboAccount","");
	    	 params.put("gender",mFemaleOrMale_int+"");
	    	 params.put("ageGroup",mRadioGroup_Age_int+"");
	    	 params.put("style","");
	    	 
	    	Log.e("Rock", sp.getString("email", null)+":user_mservice.getValue(UserMenuService.response_user_email)");
			 String urlcons = SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"/update?"+""
             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
			 user_mservice.setRetrieveUrl(urlcons);
			 user_mservice.retrieveUserUpdateInfo(params);
			 String user_login_meta_code = user_mservice.getCode();
			 Log.d("Rock", urlcons+"LLLLL");
			 if(isNetworkAvailable()){
				 if ("200".equals(user_login_meta_code)) {
					 Message msg= Message.obtain(mProgressHandler,2);
	            	 msg.sendToTarget(); 
				}else{
					 Message msg= Message.obtain(mProgressHandler,3);
	            	 msg.sendToTarget();
				}
		        }else {
		    		Message msg = Message.obtain(mProgressHandler,4);
					msg.sendToTarget();
		        }
         }
     };
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
	class NextButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			 if (mRadioGroup_Age_int!=9&&mFemaleOrMale_int!=9) {
        	 progressDialog = ProgressDialog.show(GenderAgeActivity.this, "Loading...", "Please wait...", true, false);
			 new Thread(mthread).start();
			}else{
				Message msg= Message.obtain(mProgressHandler,1);
           	 	msg.sendToTarget();
			}
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return super.onKeyDown(keyCode, event);

		}

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Intent intent = getIntent();
			Bundle b =intent.getExtras();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 1:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 2:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(2, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
	            break;
		case 3:
			intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
	            break;
		default:
	           break;
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
