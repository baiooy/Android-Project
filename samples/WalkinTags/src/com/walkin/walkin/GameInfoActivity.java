package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.AsyncImageLoader;
import com.walkin.common.MyButton;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameInfoActivity extends Activity{
    private MyButton myhome;
	private Button Button_ME ;
    private Button button_catch;
    private Button button_tryOn;
    private Button button_question;
	   protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameinfo_act);
        user_mservice.setActivity(this);
        
        button_catch=(Button) findViewById(R.id.button_catch);
        myhome = new MyButton(this);
	    Integer[] mHomeState01 = { R.drawable.gameinfo_zhuaku_pic,R.drawable.gameinfo_zhuaku_pic, R.drawable.gameinfo_zhuaku_pic };
	    button_catch.setBackgroundDrawable(myhome.setbg(mHomeState01));
	    button_tryOn=(Button) findViewById(R.id.button_tryOn);
        myhome = new MyButton(this);
	    Integer[] mHomeState02 = { R.drawable.gameinfo_shichuan_pic,R.drawable.gameinfo_shichuan_pic, R.drawable.gameinfo_shichuan_pic };
	    button_tryOn.setBackgroundDrawable(myhome.setbg(mHomeState02));
	   /* button03=(Button) findViewById(R.id.button3);
        myhome = new MyButton(this);
	    Integer[] mHomeState03 = { R.drawable.innotin,R.drawable.innotinselected, R.drawable.innotinselected };
	    button03.setBackgroundDrawable(myhome.setbg(mHomeState03));*/
	    /*button04=(Button) findViewById(R.id.button4);
        myhome = new MyButton(this);
	    Integer[] mHomeState04 = { R.drawable.actinfo_question_icon,R.drawable.actinfo_question_icon, R.drawable.actinfo_question_icon };
	    button04.setBackgroundDrawable(myhome.setbg(mHomeState04));*/
	    Button_ME=(Button) findViewById(R.id.Button_ME);
		Button_ME.setText(user_mservice.getInbiBalance());
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
	    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	    Button_ME.setOnClickListener(new Button_MEButtonListener());
    }
	class Button_MEButtonListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intent = new Intent(GameInfoActivity.this, MeActivity.class);
			 
            // intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();  
			mBundle.putString("Data", "mainbar");//压入数据  
			intent.putExtras(mBundle);  
			startActivityForResult(intent, 0);
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
       Drawable cacheImage = asyncImageLoader.loadDrawable(GameInfoActivity.this,url,new ImageCallback() {
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
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
