package com.walkin.common;

import java.util.HashMap;
import java.util.Map;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.walkin.service.UserMenuService;


public class BaseAuthenicationHttpsClient {
    public static String sessionId = "";
    private static int timeoutConnection = 10000;   
    private BaseAuthenicationHttpsClient(){
    }
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {  
        public boolean verify(String hostname, SSLSession session) {  
            return true;  
        }  
    };  
  
    /** 
     * Trust every server - dont check for any certificate 
     */  
    private static void trustAllHosts() {  
        // Create a trust manager that does not validate certificate chains  
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {  
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                return new java.security.cert.X509Certificate[] {};  
            }  
  
            public void checkClientTrusted(X509Certificate[] chain,  
                    String authType) throws CertificateException {  
            }  
  
            public void checkServerTrusted(X509Certificate[] chain,  
                    String authType) throws CertificateException {  
            }  
        } };  
  
        // Install the all-trusting trust manager  
        try {  
            SSLContext sc = SSLContext.getInstance("TLS");  
            sc.init(null, trustAllCerts, new java.security.SecureRandom());  
            HttpsURLConnection  
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   
    public static String doRequest(Context context,String urlString,String name,String password, Map<String,String>params) throws IOException{
         Log.d("Rock", "RockURL");
         String rc = "";  
//         Map<String,String>param=new HashMap<String,String>()  ;
//         param.put("userId",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID));
//         param.put("token",user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN));
//         param.put("apikey","BYauu6D9");
         
         try {  
             HttpURLConnection http = null;  
             URL url = new URL(urlString);  
             if (url.getProtocol().toLowerCase().equals("https"))  
             {  
                 trustAllHosts();  
                 HttpsURLConnection https = (HttpsURLConnection) url.openConnection();  
                 https.setHostnameVerifier(DO_NOT_VERIFY);  
                 http = https;  
                
             }else  
             {  
                 http = (HttpURLConnection) url.openConnection();  
             }
             
           //  StringBuilder entityBuilder = new StringBuilder("");
             byte bytes[] = null;
             if(params!=null && !params.isEmpty()){
            	 StringBuffer sb = new StringBuffer();
                 for(Map.Entry entry : params.entrySet()){
                     sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                 }
                 sb.deleteCharAt(0);
                 Log.d("Rock", sb.toString()+"7777777777777777777777777");
                  bytes = sb.toString().getBytes("UTF-8");
             }
             
             http.setRequestProperty("Content-Type",  
                     "application/x-www-form-urlencoded;charset=UTF-8");  
             if(params!=null && !params.isEmpty()){
            	 ((HttpsURLConnection) http).setRequestMethod("POST");  
                 http.setRequestProperty("Content-Length",  
                         String.valueOf(bytes.length));  
             }else{
            	 ((HttpsURLConnection) http).setRequestMethod("GET");  
             }
             
             
             
             
             http.setDoOutput(true);  
             http.setDoInput(true);  
             http.connect();  
             
             if(params!=null && !params.isEmpty()){
            	 OutputStream outStream = http.getOutputStream();  
                 outStream.write(bytes);  
             }
            
//             String query = "mail=" + name + "&passwd=" + password; // 请求参数  
//             byte[] entitydata = query.getBytes();// 得到实体数据  
             
          //   http.setRequestProperty("Content-Length",  
           //          String.valueOf(entitydata.length));  
             // 把封装好的实体数据发送到输出流  
//             InputStream in = uc.getInputStream();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
//             String line = reader.readLine();
//             reader.close();
//             return line.trim();
             // 服务器返回输入流并读写  
             BufferedReader in = new BufferedReader(new InputStreamReader(  
                     http.getInputStream(),"UTF-8"));  
             String line = in.readLine();
             in.close();
             rc= line.trim();
             //String line;  
            // StringBuffer sb = new StringBuffer();  
            // while ((line = in.readLine()) != null)  
            //     sb.append(line);  
   
            // rc = sb.toString();  
             Log.d("Rock", rc);
         } catch (Exception e) {  
             Log.e("Rock", e.getMessage());  
         }  
         return rc;  
//    	return "walkin";
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
