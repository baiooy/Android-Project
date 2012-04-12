package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GamesActivity extends Activity{
    private MyButton myhome;
    private Button Button_ME ;
    private Button button_gameinfo;
    private Button button_gamesActlist;
    private Button button03;
	private Button Button_top_logo,Button_top_arrow;
	private static Handler exitHandler=null;
	   private MyTaskMeInbiBalance mTaskInbi;  
	private static Context context;
	   protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_act);
        context = this;
		user_mservice.setActivity(this);
        button_gameinfo=(Button) findViewById(R.id.button_gameinfo);
        myhome = new MyButton(this);
        Integer[] mHomeState01 = { R.drawable.game_actinfo_label1_chinese,R.drawable.game_actinfo_label1_chinese, R.drawable.game_actinfo_label1_chinese };
	    button_gameinfo.setBackgroundDrawable(myhome.setbg(mHomeState01));
	    button_gameinfo.setOnClickListener(new button_gameinfoListener());
	    
	    button_gamesActlist=(Button) findViewById(R.id.button_gamesActlist);
        myhome = new MyButton(this);
        Integer[] mHomeState02 = { R.drawable.game_actinfo_label2_chinese,R.drawable.game_actinfo_label2_chinese, R.drawable.game_actinfo_label2_chinese };
	    button_gamesActlist.setBackgroundDrawable(myhome.setbg(mHomeState02));
	    button_gamesActlist.setOnClickListener(new button_gamesActlistListener());
	   /* button03=(Button) findViewById(R.id.button3);
        myhome = new MyButton(this);
	    Integer[] mHomeState03 = { R.drawable.innotin,R.drawable.innotinselected, R.drawable.innotinselected };
	    button03.setBackgroundDrawable(myhome.setbg(mHomeState03));*/
	   // button_question=(Button) findViewById(R.id.button_question);
       // myhome = new MyButton(this);
       // Integer[] mHomeState04 = { R.drawable.gameinfo_question_buttom,R.drawable.gameinfo_question_buttom, R.drawable.gameinfo_question_buttom };
	   // button_question.setBackgroundDrawable(myhome.setbg(mHomeState04));
	    
	    Button_ME=(Button) findViewById(R.id.Button_ME);
	    Button_ME.setText(user_mservice.getInbiBalance());
	    Button_top_logo=(Button) findViewById(R.id.Button_top_logo);
	    Button_top_arrow=(Button) findViewById(R.id.Button_top_arrow);
	    Button_top_arrow.setOnClickListener(new Button_top_logoListener());
	    Button_top_logo.setOnClickListener(new Button_top_logoListener());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new Button_MEButtonListener());
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
		             +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)
		             +"&apikey=BYauu6D9";
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
	 	class Button_top_logoListener implements OnClickListener{
	 		public void onClick(View v) {
	 			Intent intent = getIntent();
    			setResult(3, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
    			finish();//此处一定要调用finish()方
	 		}
	 	}   
	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(GamesActivity.this, MeActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
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
	class button_gameinfoListener implements OnClickListener{
		public void onClick(View v) {
			if(isNetworkAvailable()){
				Intent intent = new Intent(GamesActivity.this, GamesActListActivity.class);
				Bundle mBundle = new Bundle();  
				mBundle.putString("Data", "mainbar");//压入数据  
				intent.putExtras(mBundle);  
				startActivityForResult(intent, 0);
			}else{
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
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("lifecycle", "onStart()");
		String bg_img_url=SppaConstant.WALKIN_URL_BASE+"style/android/activity.jpg";
		loadImage_RelativeLayout(bg_img_url, (RelativeLayout)findViewById(R.id.RelativeLayout_bg));
		super.onStart();
	}
	 private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	private void loadImage_RelativeLayout(final String url, final RelativeLayout bgimageView) {
        //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
       Drawable cacheImage = asyncImageLoader.loadDrawable(GamesActivity.this,url,new ImageCallback() {
           //请参见实现：如果第一次加载url时下面方法会执行
           public void imageLoaded(Drawable imageDrawable, String imageUrl) {
          	 
          	 if (imageDrawable != null ) { 
          		 bgimageView.setBackgroundDrawable(imageDrawable);
          	 }else{
          		 Drawable drawable_bg = getResources().getDrawable(R.drawable.games_bg);
          		 bgimageView.setBackgroundDrawable(drawable_bg);
          	 }
           }
       });
      if(cacheImage!=null){
      	bgimageView.setBackgroundDrawable(cacheImage);
      }else{
      	 Drawable drawable_bg = getResources().getDrawable(R.drawable.games_bg);
      	bgimageView.setBackgroundDrawable(drawable_bg);
 	 }
  }
	class button_gamesActlistListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(GamesActivity.this, GameInfoActivity.class);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
			
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
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
}
