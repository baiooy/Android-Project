package com.walkin.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {
	private static final String TAG="AsyncImageLoader";

	private HashMap<String, SoftReference<Drawable>> imageCache;
	private BlockingQueue queue ;   
	private ThreadPoolExecutor executor ;
	  
	     public AsyncImageLoader() {
	    	 imageCache = new HashMap<String, SoftReference<Drawable>>();
	    	
	    	 // 线程池：最大50条，每次执行：1条，空闲线程结束的超时时间：180秒
	    	 queue = new LinkedBlockingQueue();
	    	 executor = new ThreadPoolExecutor(1, 50, 180, TimeUnit.SECONDS, queue);
	     }
	  
	     public Drawable loadDrawable(final Context context, final String imageUrl, final ImageCallback imageCallback) {
	         if (imageCache.containsKey(imageUrl)) {
	             SoftReference<Drawable> softReference = imageCache.get(imageUrl);
	             Drawable drawable = softReference.get();
	             if (drawable != null) {
	                 return drawable;
	             }
	         }
	         final Handler handler = new Handler() {
	             public void handleMessage(Message message) {
	                 imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
	             }
	         };
	         
	         // 用线程池来做下载图片的任务
	         executor.execute(new Runnable() {
	             @Override
	           
	             public void run() {
	                 Drawable drawable = loadImageFromUrl(context, imageUrl);
	                 imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
	                 Message message = handler.obtainMessage(0, drawable);
	                 handler.sendMessage(message);
	             }
	         });
	         
	         return null;
	     }
	  
	    // 网络图片先下载到本地cache目录保存，以imagUrl的图片文件名保存。如果有同名文件在cache目录就从本地加载
		public static Drawable loadImageFromUrl(Context context, String imageUrl) {
			Drawable drawable = null;  
	        if(imageUrl == null )  
	            return null;  
	        String imagePath = "";  
	        String   fileName   = "";  
	              
	        // 获取url中图片的文件名与后缀  
	        if(imageUrl!=null&&imageUrl.length()!=0){   
	            fileName  = imageUrl.substring(imageUrl.lastIndexOf("/")+1);  
	        }  
	          
	        // 图片在手机本地的存放路径,注意：fileName为空的情况  
	        imagePath = context.getCacheDir() + "/" + fileName;  
	  
	        Log.i(TAG,"imagePath = " + imagePath);  
	        File file = new File(context.getCacheDir(),fileName);// 保存文件  
//	        Log.i(TAG,"file.toString()=" + file.toString());  
	        if(!file.exists()&&!file.isDirectory())  
	        {  
	            try {  
	                // 可以在这里通过文件名来判断，是否本地有此图片  
	                  
	                FileOutputStream   fos=new   FileOutputStream( file );  
	                InputStream is = new URL(imageUrl).openStream();  
	                int   data = is.read();   
	                while(data!=-1){   
	                        fos.write(data);   
	                        data=is.read();;   
	                }   
	                fos.close();  
	                is.close();  
//	              drawable = Drawable.createFromStream(  
//	                      new URL(imageUrl).openStream(), file.toString() ); // (InputStream) new URL(imageUrl).getContent();  
	                drawable = Drawable.createFromPath(file.toString());  
//	                Log.i(TAG, "file.exists()不文件存在，网上下载:" + drawable.toString());  
	            } catch (IOException e) {  
	                Log.e(TAG, e.toString() + "图片下载及保存时出现异常！");  
	            }  
	        }else  
	        {  
	            drawable = Drawable.createFromPath(file.toString());  
//	            Log.i("test", "file.exists()文件存在，本地获取");  
	        } 
	        return drawable ;
		}
	  
	     public interface ImageCallback {
	         public void imageLoaded(Drawable imageDrawable, String imageUrl);
	     }

}
