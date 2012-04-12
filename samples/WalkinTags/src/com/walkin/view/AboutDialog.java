package com.walkin.view;

import com.walkin.common.MyButton;
import com.walkin.spp.SppaConstant;
import com.walkin.walkin.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * User: Andy
 * Author: andy_huang@wochacha.com
 * Date: Oct 15, 2010
 */
public class AboutDialog extends Dialog{
	public TextView titles;
	public Context con;
    public AboutDialog(Context context, boolean ischeck) {
        super(context);
        /*
         * No title invoke
         * requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
         */
        this.con = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutdialog);
        this.setTitle(R.string.dialog_about_title);
        Button buttonOk = (Button) findViewById(R.id.buttonOK);
        MyButton mybutton = new MyButton(con);
        Integer[] mButtonState = {R.drawable.none_color,R.drawable.none_color, R.drawable.none_color };

        buttonOk.setBackgroundDrawable(mybutton.setbg(mButtonState));
        titles = (TextView)findViewById(R.id.about_tips);
        
        buttonOk.setText(R.string.dialog_about_close);
        buttonOk.setOnClickListener(new OKListener());
        titles.setText("walkin"+SppaConstant.APP_VERSION+"\n...\n\n" +
        		"walkin信息技术(上海)有限公司  版权所有 \n客户热线:400 071 5100\n客服邮箱:service@service.com");
        if(SppaConstant.app.equals("Samsung320x240")){
        	titles.setText("版本号:"+SppaConstant.APP_VERSION+"\n软件所有权:" +
        			"\nwalkin信息技术(上海)有限公司 \n\n客户热线:\n400 071 5100\n\n客服邮箱:\nservice@service.com");
        }
    }

    private class OKListener implements android.view.View.OnClickListener {
       
         public void onClick(View v) {
              AboutDialog.this.dismiss();
         }
    }

    /*
    private class CancleListener implements android.view.View.OnClickListener {
    	
        @Override
        public void onClick(View view) {
            AboutDialog.this.dismiss();
            mactivity.finish();
        }
    }
    */

}
