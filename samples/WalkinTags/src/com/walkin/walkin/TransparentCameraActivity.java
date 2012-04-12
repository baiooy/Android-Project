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

public class TransparentCameraActivity extends Activity{
	
	private RelativeLayout RelativeLayout_bg;
	private static Handler exitHandler=null;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_act);
        RelativeLayout_bg= (RelativeLayout) findViewById(R.id.RelativeLayout_bg);
        Drawable drawable_background = getResources().getDrawable(R.drawable.help_tutorial_camera);
        RelativeLayout_bg.setBackgroundDrawable(drawable_background);
        RelativeLayout_bg.setOnClickListener(new RelativeLayout_bgListener()); 
        
        exitHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					TransparentCameraActivity.this.finish();
					break;
			}
				super.handleMessage(msg);
			}
		};
    }
    class RelativeLayout_bgListener implements OnClickListener{
		public void onClick(View v) {
			finish();
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
