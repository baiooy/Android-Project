package com.walkin.common;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ThreadForRunnable implements Runnable {
	 Context con;   
	 ProgressDialog pd;   
	 Handler handler;  
	 String url;
	 
	 public ThreadForRunnable(Context con, ProgressDialog pd,Handler handler,String url) {   
	        this.con = con;   
	        this.pd = pd;   
	        this.handler = handler;
	        this.url=url;
	    }   

	public void run() {
		try{
			DownLoadApk dl = new DownLoadApk(con, url, handler);
			pd.setMax((int) dl.getFileSize());
			dl.setDataSource();
		}catch(Exception e){
			Message msg = new Message();   
            msg.what = -1;   
            msg.getData().putString("error", "下载失败123123123");   
            handler.sendMessage(msg);   
            Log.d("Rock", e+":e");
		}
		
	}

}
