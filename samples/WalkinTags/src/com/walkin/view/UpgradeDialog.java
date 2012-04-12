package com.walkin.view;

import com.walkin.walkin.R;
import com.walkin.walkin.SettingActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class UpgradeDialog extends Dialog{
    private SettingActivity settingActivity;
//    private MainActivity mainActivity;
    private Button buttonOk;
    private Button buttonCancel;
    public TextView title;
    public TextView content;
    private int whichDialog =0;
    public UpgradeDialog(Context context,int dialog) {
        super(context);
        whichDialog =dialog;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upgradedialog);
        if(whichDialog ==1){
        	settingActivity = (SettingActivity)context;
        }
        //else if(whichDialog ==1){
        //	mainActivity = (MainActivity)context;
        //}
        title = (TextView)findViewById(R.id.upgrade_title);
        content = (TextView)findViewById(R.id.clienttips);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        buttonOk = (Button)findViewById(R.id.buttonOK);
        buttonOk.setText(R.string.upgrade_continue);
        buttonOk.setOnClickListener(new OKListener());
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonCancel.setText(R.string.upgrade_delay);
        buttonCancel.setOnClickListener(new CancleListener());
        
    }
       
    private class OKListener implements android.view.View.OnClickListener {

         public void onClick(View v) {
	          UpgradeDialog.this.dismiss();
	          //如果有特殊要求还是调用网页下载
	      /*  Intent intent=new Intent();
	          intent.setAction("android.intent.action.VIEW");
	          intent.setData(Uri.parse(mservice.getValue(MenuService.VERSION_URL)));
	          intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
	          mactivity.startActivity(intent);*/
              
	          //自动下载
      		  Message msg = new Message();
      		  msg.what = 3;  
      		  msg.getData().putInt("context", 0);
      		  if(whichDialog ==0)
      		 	settingActivity.mProgressHandler.sendMessage(msg);
      		  else if(whichDialog ==1){
      			settingActivity.mProgressHandler.sendMessage(msg);
      		  }
      			  
         }
    }

    private class CancleListener implements android.view.View.OnClickListener {
        public void onClick(View view) {
            UpgradeDialog.this.dismiss();
            if(whichDialog ==0){
            	
            }
            //	startupActivity.ContinueLoadWcc();
        }
    }
    
    public void SetUpgradeDialogTitle(String str){
    	title.setText(str);
    }
    public void SetUpgradeDialogContent(String str){
    	String tmp =str.replace("\r\n", "\n");
    	content.setText(tmp);
  //  	Linkify.addLinks(content, Linkify.WEB_URLS);
    	
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	UpgradeDialog.this.dismiss();
        	if(whichDialog ==0)
            	//startupActivity.ContinueLoadWcc();
            return false;
            }
        return super.onKeyDown(keyCode, event);
    }
}
