package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class BadgesListActivity extends Activity{
//	private MyButton myhome;
	private Button goback ;
	private LinearLayout LinearLayout_all_xunzhang,LinearLayout_act_xunzhang,LinearLayout_walkin_xunzhang,LinearLayout_brand_xunzhang,LinearLayout_my_xunzhang ;
//	private Button Button_Next ;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badgeslist_act);
        
        
        //Button_ME=(Button) findViewById(R.id.Button_ME);
//        Button_Next=(Button) findViewById(R.id.Button_Next);
       // myhome = new MyButton(this);
       // Integer[] mHomeState = { R.drawable.common_me_img,R.drawable.key_touch, R.drawable.key_touch };
        
        goback= (Button) findViewById(R.id.goback);
		goback.setOnClickListener(new gobackListener()); 
        
        LinearLayout_all_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_all_xunzhang);
        LinearLayout_all_xunzhang.setOnClickListener(new LinearLayout_all_xunzhangListener());
        LinearLayout_act_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_act_xunzhang);
      //  LinearLayout_act_xunzhang.setOnClickListener(new LinearLayout_act_xunzhangListener());
        LinearLayout_walkin_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_walkin_xunzhang);
      //  LinearLayout_walkin_xunzhang.setOnClickListener(new LinearLayout_walkin_xunzhangListener());
        LinearLayout_brand_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_brand_xunzhang);
     //   LinearLayout_brand_xunzhang.setOnClickListener(new LinearLayout_brand_xunzhangListener());
        LinearLayout_my_xunzhang=(LinearLayout) findViewById(R.id.LinearLayout_my_xunzhang);
        LinearLayout_my_xunzhang.setOnClickListener(new LinearLayout_my_xunzhangListener());
        
        
	   // Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
	   // Button_ME.setOnClickListener(new Button_MEListener());
//        Button_Next.setOnClickListener(new Button_NextListener());
    }
	  class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}
    class LinearLayout_my_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(BadgesListActivity.this, BadgesGridActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 0);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
 		}
 	}
    class LinearLayout_brand_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(BadgesListActivity.this, BadgesGridActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 1);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
 		}
 	}
    class LinearLayout_walkin_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(BadgesListActivity.this, BadgesGridActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 2);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
 		}
 	}
    class LinearLayout_act_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(BadgesListActivity.this, BadgesGridActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 3);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
 		}
 	}
    class LinearLayout_all_xunzhangListener implements OnClickListener{
 		public void onClick(View v) {
 			Intent intent = new Intent(BadgesListActivity.this, BadgesGridActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("Data", 4);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
 		}
 	}
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
	//		finish();
		}
	}
	class Button_NextListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.setClass(BadgesListActivity.this, BadgesDetailActivity.class);
			Bundle bundle=new  Bundle();
			String str1="aaaaaa";
			bundle.putString("str1", str1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
            
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Log.d("Rock", RESULT_OK+"");
			Log.d("Rock", resultCode+"");
			Intent intent = getIntent();
//			Bundle b =intent.getExtras();
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
