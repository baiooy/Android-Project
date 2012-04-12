package com.walkin.walkin;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class TransparentAllActivity extends Activity{
	
	private RelativeLayout RelativeLayout_bg;
	private static Handler exitHandler=null;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparentall_act);
        
        RelativeLayout_bg= (RelativeLayout) findViewById(R.id.RelativeLayout_bg);
    	Bundle bundle = getIntent().getExtras();    
    	String data_Activity=bundle.getString("Activity");
    	Drawable drawable_background = getResources().getDrawable(R.drawable.walkin_main_help);
        if ("IndexActivity".equals(data_Activity)) {
        	  drawable_background = getResources().getDrawable(R.drawable.walkin_main_auto);
		}else if ("Activity_Message".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.walkin_main_help);
		}else if ("BrandsDetailActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_branddetailview_marker);
		}else if ("BrandsDetailActivity_youhui_Message".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_branddetailview_deal_pop);
		}else if ("BrandsDetailActivity_inbi_Message".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_branddetailview_inbi_pop);
		}else if ("DealsActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_branddetailview_deal_pop);
		}else if ("MeActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_profile);
		}else if ("BaseMapActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_map);
		}else if ("InStoreDetailActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.aftercheckin_deal);
		}else if ("BrandsActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_places);
		}else if ("SocialActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.tut_social_out);
		}else if ("RegistrationActivity".equals(data_Activity)) {
			  drawable_background = getResources().getDrawable(R.drawable.register_tutorial);
		}
       
        RelativeLayout_bg.setBackgroundDrawable(drawable_background);
        RelativeLayout_bg.setOnClickListener(new RelativeLayout_bgListener()); 
        
        exitHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					finish();
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);	
					break;
			}
				super.handleMessage(msg);
			}
		};
    }
    class RelativeLayout_bgListener implements OnClickListener{
		public void onClick(View v) {
			finish();
			overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);	
			}
	}
    public static Handler getExitHandler(){ 
        return exitHandler;
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
