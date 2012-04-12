package com.walkin.view;


import com.walkin.spp.SppaConstant;
import com.walkin.walkin.R;
import com.walkin.walkin.SplashActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class WarnDialog extends Dialog{
    
    private SplashActivity sactivity;
    private Button buttonOk;
    private Button buttonCancle;
    private TextView content;
    private CheckBox checkBox;
    public WarnDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.warndialog);
        sactivity = (SplashActivity)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonOk = (Button)findViewById(R.id.buttonOK);
        buttonOk.setOnClickListener(new OKListener());
        buttonCancle = (Button)findViewById(R.id.buttonCancel);
        buttonCancle.setOnClickListener(new CancleListener());
        checkBox = (CheckBox)findViewById(R.id.checkbox);
        content = (TextView)findViewById(R.id.w_content);
        String str1 ="欢迎使用walkin软件，该软件在使用过程中仅会产生流量费，流量费请咨询当地运营商。是否允许应用建立连接？\n";
    	String str2 = "\n\n关于walkin"+SppaConstant.APP_VERSION+"\n...\n";
    	String str3 = "walkin信息技术(上海)有限公司  版权所有 \n客户热线:400 071 5100\n客服邮箱:service@walkin.com";
    	String tmp = str1+str2+str3;
    	if(SppaConstant.app.equals("ZTE320x480"))
    		tmp = "欢迎使用walkin客户端软件，客户端使用完全免费，在使用过程中会产生流量费，流量费用请咨询当地运营商，是否允许应用建立连接。";
    	content.setText(tmp);
    }  

    private class OKListener implements android.view.View.OnClickListener {

         public void onClick(View v) {
              WarnDialog.this.dismiss();
              SharedPreferences sharepre = PreferenceManager.getDefaultSharedPreferences(sactivity);
              sharepre.edit().putBoolean("checked",checkBox.isChecked()).commit();
              sactivity.checkNetworkStatus();
         }
    }

    private class CancleListener implements android.view.View.OnClickListener {
        public void onClick(View view) {
            WarnDialog.this.dismiss();
            sactivity.OnCloseAllActivity();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	  if (keyCode == KeyEvent.KEYCODE_BACK) {
    		  sactivity.OnCloseAllActivity();
              }
          return super.onKeyDown(keyCode, event);
    }
    
}
