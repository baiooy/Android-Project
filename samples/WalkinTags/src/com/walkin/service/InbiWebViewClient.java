package com.walkin.service;

import java.net.URLEncoder;

import com.walkin.walkin.InbiExchangeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InbiWebViewClient extends WebViewClient{
       private Context con;
//       private final static MenuService mservice = MenuService.getInstance();
//       private final static BgboboActivity wccactivity = BgboboActivity.getInstance();
       private InbiExchangeActivity inbiexchangeactivity ;
       public ProgressDialog progressDialog; 
    //   public static String[] strSID={"0"};
       public static String[] strSMS={"0"};
       public InbiWebViewClient(Activity activity){
           con = activity;
           progressDialog = new ProgressDialog(con);
           inbiexchangeactivity = (InbiExchangeActivity)activity;
       }
       
       @Override
       public boolean shouldOverrideUrlLoading(WebView view, String url) { 
    	    if(url!=null){
	    	   if(url.contains("geke")){
	                String newUrl = url.replace("geke","http");
	                Log.d("Rock", newUrl+":newUrl");
	    			Intent intent = new Intent();
	    			intent.setClass(inbiexchangeactivity,InbiExchangeActivity.class);
	    			Bundle bundle = new Bundle();
	    			String str1 = "aaaaaa";
	    			bundle.putString("str1", str1);
	    			intent.putExtras(bundle);
	    			inbiexchangeactivity.startActivityForResult(intent, 0);
	    			// if(economizedetailsactivity.mProgressHandler!=null){
	    		           	Message msg = Message.obtain(inbiexchangeactivity.mProgressHandler,2);
	    		         //  	if(economizedetailsactivity.isWebviewBack){
	    		        //  		economizedetailsactivity.isWebviewBack =false;
	    		        //   	}else{
	    		           		msg.sendToTarget();
	    		        //   	}
	    		       //    }
//	    			Message msg =Message.obtain(economizedetailsactivity.getMainProgressHandler(),2);
//	                msg.sendToTarget();
	               /* if(MainActivity.autoScan){
	                	Intent intent =  new Intent(con, BarcodeScanActivity.class);
	                	intent.putExtra("urlstr",newUrl);
	                	con.startActivity(intent);
	                }else{
	                	Intent intent =  new Intent(con, BarcodeInputActivity.class);
	                	intent.putExtra("urlstr",newUrl);
	                	con.startActivity(intent);
	                }*/
	                return true;
	            }else if(url.contains("tel")){
	                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
	                con.startActivity(i);
	                return true;
	            
	            }else if(url.contains("mailto")){
	               Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
	               con.startActivity(intent);
	               return true;
	            }else if(url.contains("sms:")){
	            	strSMS = url.split("=");
	            	Intent intent;
	                if(url.contains("body"))//添加好友sms
	                {
//	                	Log.d("Rock", RegExpValidator.getLocalIpAddress());
	                /*	try{
	                    	Message message = Message.obtain(MainActivity.getGPSProgressHandler(), 8);//wcc
	                        message.sendToTarget();
	                   	}catch (Exception e){
                   		Log.d("Rock","thread error");
	                       }*/
	                	//朋友推荐点击 
	                	/* String urlcons = WccConstant.WCC_RGF+"?act=click&mod=recommend&udid="+urlEncode(wccactivity.uuid);
                    	 Log.d("Rock", urlcons);
                    	 mservice.setRetrieveUrl(urlcons);
                    	 new Thread(LoginThread).start();//发送手机参数给服务器
	                   	intent = new Intent(con, MeunDialogActivity.class);*/
	                  //---end
	                }
	                else   
	                {
	                	intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
	                	intent.putExtra("sms_body","");
	                }
//	                con.startActivity(intent);
	                return true;
	            }else if(url.contains("wccb")){
	            	progressDialog.setMessage("加载中，请稍等");
	         	    progressDialog.setCancelable(true);
	                progressDialog.show();
	            	 
	                String strUrl = url.replace("wccb","http");
	      //          strSID = strUrl.split("=",3);
//	                strUrl =strUrl+"&udid="+mainactivity.urlEncode(mainactivity.uuid);
//	                mservice.setRetrieveUrl(strUrl);//Send message to server
//	                new Thread(mthread).start();
	            	
	                return true;
	                
	            }else{
	            	
	            	return super.shouldOverrideUrlLoading(view, url);
	            }
    	   } 
    	   return super.shouldOverrideUrlLoading(view, url);
       }
       
       public String urlEncode(String str){
       	if(null == str || "".equalsIgnoreCase(str)){
           return str;
       	}else{
       		return URLEncoder.encode(str);
       	}
       }
       /*public void SelectStoreMode(int mode){
	    	   switch(mode)
	    	   {
	    	   case 0:
	    		    AlertDialog.Builder b = new AlertDialog.Builder(mainactivity);
	    	    	b.setTitle("错误");
	    	    	b.setMessage("打开失败，请重试");
	    	    	b.setPositiveButton("确定",new DialogInterface.OnClickListener(){
	    	    		public void onClick(DialogInterface dialog,int which){
	    	    		}
	    	    	});
	    	    	b.show();
	    		   break;
	    	   case 1://wap
	    		    openStoreWap();
	    		   break;
	    	   case 2://client
	    		   if(openStoreClient()){
	    		   }else{
	    			   String tmp=mservice.get_StoreWapUrl();
	    			   String tmp1=mservice.get_StoreClientDownload();
	    			   if((!tmp.equals("0"))&&tmp1.equals("N"))
	    				   openStoreWap();
	    			   else if((!tmp.equals("0"))&&tmp1.equals("Y")){
	    				   String str = "没有找到\""+mservice.get_StoreName()+"\"客户端\n安装->下载客户端\n取消->进入网页版";
		    			   ClientErrorDlg(str);
	    			   }else if((tmp.equals("0"))&&tmp1.equals("Y")){
	    				   String str = "没有找到\""+mservice.get_StoreName()+"\"客户端,是否下载？";
		    			   ClientErrorDlg(str);
	    			   }else if((tmp.equals("0"))&&tmp1.equals("N")){
	    				   AlertDialog .Builder b1=new AlertDialog.Builder(mainactivity);
	                		String upbut = "确定";
	                		b1.setTitle("错误!");
	                		b1.setMessage("没有客户端，无法购物");
	                		b1.setPositiveButton(upbut,  new DialogInterface.OnClickListener(){
	                	          public void onClick(DialogInterface dialog, int which){}
	                	         });
	                	    b1.show();
	    			   }
	    		   }
	    		   break;
	    	   }
	    	   
       };*/
    /* private void openStoreWap(){
    	 String StoreUrl = mservice.get_StoreWapUrl();
    	 if(StoreUrl.equals("0")){
    		 AlertDialog .Builder b1=new AlertDialog.Builder(mainactivity);
     		String upbut = "确定";
     		b1.setTitle("错误!");
     		b1.setMessage("参数错误，无法购物");
     		b1.setPositiveButton(upbut,  new DialogInterface.OnClickListener(){
     	          public void onClick(DialogInterface dialog, int which){}
     	         });
     	    b1.show();
    		 return;
    	 }
    	 String openmode = mservice.get_StoreOpenMode();
    	 if(openmode.equals("1"))
    		 mainactivity.webview.loadUrl(StoreUrl);//内嵌是网页
    	 else if(openmode.equals("0")){
    		 Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(StoreUrl));
    		 con.startActivity(intent);//Open new browser
    	 }
		 mservice.set_StoreWapUrl("0");
     }
     private boolean openStoreClient(){
    	String clientPath =mservice.get_StoreClientPath() ;
    	String clientName =mservice.get_StoreClientName() ;
    	String clientPar = mservice.get_StoreClientParameters();
        
    	if(clientPar.equals("0")){
    		AlertDialog .Builder b1=new AlertDialog.Builder(mainactivity);
    		String upbut = "确定";
    		b1.setTitle("错误!");
    		b1.setMessage("参数错误，无法购物");
    		b1.setPositiveButton(upbut,  new DialogInterface.OnClickListener(){
    	          public void onClick(DialogInterface dialog, int which){}
    	         });
    	    b1.show();
   		 return false;
   	 }
    	
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(clientPath, clientName));
        intent.putExtra(WccConstant.WCC_KEYWORD, clientPar);
        intent.setAction(clientName); 

        try{
        	con.startActivity(intent);
        }catch(Exception e){
            return false;
        }
        return true;
     }
     public void ClientErrorDlg(String str){
    	 AlertDialog.Builder b = new AlertDialog.Builder(mainactivity);
         b.setTitle("提示");
    	 b.setMessage(str);
    	 b.setPositiveButton("安装",  new DialogInterface.OnClickListener(){
             public void onClick(DialogInterface dialog, int which){
            //	 if(strSID[2]==null)
            //		 strSID[2] ="0";
            	 String reporturl =mservice.get_DownReportUrl();
            	 String info =reporturl+"&download=y"+"&udid="+mainactivity.urlEncode(mainactivity.uuid);
            	 mservice.retrieveClientStatus(info);
            	 Intent intent=new Intent();
                 intent.setAction("android.intent.action.VIEW");
                 intent.setData(Uri.parse(mservice.getValue(MenuService.STORE_DOWNLOAD_DOWNURL)));
                 intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                 con.startActivity(intent);
            	
             }
            });
    	 b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
            	 String tmp=mservice.get_StoreWapUrl();
             	if(tmp.equals("0")&& tmp !=null)
             	{
             		String reporturl =mservice.get_DownReportUrl();
             		String info =reporturl+"&download=n"+"&udid="+mainactivity.urlEncode(mainactivity.uuid);
             		mservice.retrieveClientStatus(info);
             		AlertDialog .Builder b=new AlertDialog.Builder(mainactivity);
             		String upbut = "确定";
             		b.setTitle("错误!");
             		b.setMessage("没有客户端，无法购物");
             		b.setPositiveButton(upbut,  new DialogInterface.OnClickListener(){
             	          public void onClick(DialogInterface dialog, int which){}
             	         });
             	          b.show();
             	}
             	else if(!tmp.equals("0") && tmp !=null)
             	{
             		openStoreWap();
             	}
             }
 		});
         b.show();
     }
    
     private String getFileType(String url)
 	{
 		String result="wochacha";
 		int dotIndex = url.lastIndexOf(".");
 		if(dotIndex < 0){
 			return result;
 		}
 		String apk=url.substring(dotIndex,url.length()).toLowerCase();
 		if(apk=="")return result;
 		
 		return apk;
 	}

    
   
   
    
    private final Runnable mthread = new Runnable() {
        public void run() {
            mservice.retrieveStore();
            progressDialog.dismiss();
            try{
            	Message message = Message.obtain(mainactivity.getBuyHandler(),R.id.buy_start);
                message.sendToTarget();
            	}catch (Exception e){
                }
        }
    };
    private final Runnable LoginThread = new Runnable() {
        public void run() {
        	try{
        		mservice.retrievefeedbook();
        	}catch(Exception  e){
        		e.printStackTrace(); 
        	}
            
        }
    };*/
       @Override
       public void onPageFinished(WebView view,String url){
   	   /*if(economizedetailsactivity.isFirstRun){
   		economizedetailsactivity.StartupFinished = true;
   		   return;
   	   	}*/
           if(inbiexchangeactivity.mProgressHandler!=null){
           	Message msg = Message.obtain(inbiexchangeactivity.mProgressHandler,2);
           	if(inbiexchangeactivity.isWebviewBack){
           		inbiexchangeactivity.isWebviewBack =false;
           	}else{
           		msg.sendToTarget();
           	}
           }
           if(inbiexchangeactivity.btnHandler !=null){
           	Message msg = new Message();
           	inbiexchangeactivity.btnHandler.sendEmptyMessage(0);
           }
       	//检测是否此处能检测到网页加载结束
       	if (!url.equalsIgnoreCase("file:///android_asset/error.html"))
   		{
   			
   		}
    	   super.onPageFinished(view, url);
       }
       public void onPageStarted(WebView view,String url,Bitmap favicon){
       		
       	if(inbiexchangeactivity.isFirstRun){
       		return;	
       	}
           if(inbiexchangeactivity.mProgressHandler!=null){
           	Message msg = Message.obtain(inbiexchangeactivity.mProgressHandler,1);
           	if(inbiexchangeactivity.isWebviewBack){
           		inbiexchangeactivity.isWebviewBack = false;
           	}else{
           		msg.sendToTarget();
           	}
           }
           if(inbiexchangeactivity.btnHandler !=null){
           	Message msg = new Message();
           	inbiexchangeactivity.btnHandler.sendEmptyMessage(0);
           }
    	   super.onPageStarted(view, url, favicon);
       }
       public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
       
       	if(inbiexchangeactivity.isFirstRun){
       		
       		inbiexchangeactivity.StartupNoError = false;
       	/*	try{
               	if(StartupActivity.getStartupProgressHandler()!=null){
               		Message msg = Message.obtain(inbiexchangeactivity.mProgressHandler,3);
               		msg.sendToTarget();
   	                }
               	}catch (Exception e){
                   }  */
       	}  
       	else
       		view.loadUrl("file:///android_asset/error.html");
           super.onReceivedError(view, errorCode, description, failingUrl); 
       }
}
