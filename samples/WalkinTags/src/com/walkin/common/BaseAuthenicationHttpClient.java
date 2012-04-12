package com.walkin.common;

import java.util.Map;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



public class BaseAuthenicationHttpClient {
    public static String sessionId = "";
    private static int timeoutConnection = 10000;   
    private BaseAuthenicationHttpClient(){
    }
    
   
    public static String doRequest(Context context,String urlString,String name,String password, Map<String,String>params) throws IOException{
         URL url = null;
         HttpURLConnection uc = null; 
         Log.d("Rock", "RockURL");
        try{ 
		// 看是否可以wap上网
//        	{
//               url = new URL(urlString);
//                Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("10.0.0.172",80));
//                uc =(HttpURLConnection)url.openConnection(proxy); 
//        	}  
        	NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();  
        	if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        		String host = android.net.Proxy.getDefaultHost();  
        	    
        	       // 获取端口  
        	       int port = android.net.Proxy.getDefaultPort();  
        	       if (host != null && port != -1) {  
		        	String realLink = urlString.substring(7);
		            String realHost = realLink.substring(0, realLink.indexOf("/"));
		            
		            String newUrl = "http://"+host+ realLink.substring(realLink.indexOf("/"));
		           
		            url = new URL(newUrl);
		            uc = (HttpURLConnection) url.openConnection();
		            uc.setRequestProperty("X-Online-Host", realHost);
		            
        	       }else{
        	    	   url = new URL(urlString);  
        	    	   uc = (HttpURLConnection) url.openConnection();
        	       }
        	       
        	}
        	else{
        		url = new URL(urlString);  
        		 uc = (HttpURLConnection) url.openConnection();
        	}
        	
        	
            url = new URL(urlString);  
            uc = (HttpURLConnection) url.openConnection();
            if(params!=null && !params.isEmpty()){
                uc.setRequestMethod("POST");
            }else{
            	uc.setRequestMethod("GET");
            }
            uc.setConnectTimeout(timeoutConnection);
            uc.setReadTimeout(timeoutConnection);
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.setDefaultUseCaches(false);
            uc.setRequestProperty("Accept",   "text/html");
            uc.setRequestProperty("Connection",   "close");
            if(name !=null && !"".equals(name) && password != null && !"".equals(name)){
               uc.setRequestProperty("Authorization",name+password); 
            }
            if(params!=null && !params.isEmpty()){
                StringBuffer sb = new StringBuffer();
                for(Map.Entry entry : params.entrySet()){
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
                sb.deleteCharAt(0);
                byte bytes[] = sb.toString().getBytes("UTF-8");
                uc.setRequestProperty("Content-Length",   String.valueOf(bytes.length));
                uc.getOutputStream().write(bytes);
            }
            InputStream in = uc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = reader.readLine();
            reader.close();
            return line.trim();
        } catch (RuntimeException e) {
        }catch(SocketTimeoutException e){
    	}finally {
           if(uc !=null){
        	   uc.disconnect();
        	   Log.d("Rock", "disconnect");
           }
        }
    	return "walkin";
    }

    public static String doRequest(Context context,String urlString,String name, String password) throws IOException{
        return doRequest(context,urlString,name,password,null);
    }

    public static String doRequest(Context context,String urlString)throws IOException{
        return doRequest(context,urlString,null,null,null);
    }

    public static String doRequest(Context context,String urlString,Map<String,String>params)throws IOException{
        return doRequest(context,urlString,null,null,params);
    }
}
