package com.walkin.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DownLoadApk {
    private String downloadUrl;// 下载路径   
    private long fileSize = 0;// 原始文件大小   
    private Handler handler;
    private static Handler listenerHandler=null;
    private boolean isCancelDownload = false;
    private final Runnable mThread = new Runnable(){
		public void run() {
			 Looper.prepare();
			 listenerHandler  = new Handler(){
 	            @Override
	            public void handleMessage(Message msg) {
 	            	isCancelDownload = true;
	            }
 		   };
 		  Looper.loop();
        }
    };
    public static Handler getListenerHandler(){
        return listenerHandler;
    }
    public long getFileSize() {
    	try {
        	URL url = new URL(downloadUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                fileSize = conn.getContentLength();
                conn.disconnect();
            } 
            catch (IOException e){ 
                e.printStackTrace();
            }
        } 
        catch (MalformedURLException e){ 
            e.printStackTrace();
        }
        return fileSize;   
    }   
  
    public DownLoadApk(Context con, String url, Handler handler) {   
        this.downloadUrl = url;   
        this.handler = handler;   
    } 
    
    public void createFile(String str)   
    {   
        try  
        {   
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))   
            {   
                File path = new File(str);   
                if (!path.exists())   
                {   
                    path.mkdirs();   
                }   
            }   
        } 
        catch (Exception e)   
        {   
            e.printStackTrace();   
        }   
    }   
    public void setDataSource() throws Exception  {   
    	final String pathName = "/sdcard/update";
        final String fileName = "/walkin_update.apk";

        createFile(pathName);
        final File file = new File(pathName + fileName);

        try {
        	URL url = new URL(downloadUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                if (conn.getResponseCode() >= 400){
                }else{
                	int readLen = 0; 
                	int index =0;
                	new Thread(mThread).start();
                	isCancelDownload = false;
	                while (true){
	                    if (is != null){
	                    	index++;
	                        int numRead = is.read(buf);
	                        if (numRead <= 0){
	                        	Message msg = new Message();
	                        	msg.what =5;
	                        	msg.getData().putInt("context", 5);   
	                            handler.sendMessage(msg);   
	                            break;
	                        }
	                      
	                        if(isCancelDownload)
	                        {
	                        	Message msg = new Message();
	                        	msg.what =-1;
	                        	msg.getData().putString("error", "取消下载");   
	                            handler.sendMessage(msg);   
	                        	break;
	                        }
                          readLen= readLen+numRead;
                          if(index/10 ==0)
                        	  updateProgressBar(readLen);
                          if(numRead != 256){
                        	  updateProgressBar(readLen);
                          }
                          fos.write(buf, 0, numRead);	  

                        } 
                        else 
                        	break;
	                 }
                }
                conn.disconnect();
                fos.close();
                try {   
                    is.close();    
                } catch (Exception e) {   
                    Message msg = new Message();
                	msg.what =-1;
                	msg.getData().putString("error", "下载失败");   
                    handler.sendMessage(msg);   
                } 
            } 
            catch (IOException e){ 
            	Message msg = new Message();
            	msg.what =-1;
            	msg.getData().putString("error", "下载失败");   
                handler.sendMessage(msg);   
                e.printStackTrace();
            }
        } 
        catch (MalformedURLException e){ 
    		Message msg = new Message();
        	msg.what =-1;
        	msg.getData().putString("error", "下载失败");   
            handler.sendMessage(msg);   
            e.printStackTrace();
        }
    }
    public void updateProgressBar(int readLen) {
        Message msg = new Message();   
        msg.what = 4;   
        msg.getData().putInt("size", readLen);   
        handler.sendMessage(msg);   
    }   


}
