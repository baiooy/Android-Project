package com.walkin.walkin;


import java.net.URLEncoder;

import com.walkin.service.DefaultsMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

public class BgWalkinActivity extends Activity {
	private static BgWalkinActivity instance = null;
//	private UserInfo user;
	protected static SharedPreferences sharepre = null;
	private static Context context;
    private String curNetworkType ="46000";
    public int newNetworkId = -1;
    public static String uuid = "";
    public static String imsi="";
    
    public boolean isScanDialog = false;
    public boolean isUpgrading = false;
    public boolean isScanMode = false;
	public boolean isInputMode = false;
	  protected final static DefaultsMenuService defaults_mservice = DefaultsMenuService.getInstance();
	
	
	
//    public static GcBarcode mScanner =null;//gc_lib
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  context =this;
		  defaults_mservice.setActivity(this);
		 sharepre = PreferenceManager.getDefaultSharedPreferences(this);
//      if(!SppaConstant.app.equals("rc")){
//    	MobclickAgent.onError(this);}//友盟  
		
//		mservice.setActivity(this);
	}
    public boolean isNetworkAvailable() {
	   	 try{
	       ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	       if(connectivity == null){
	       		return false;
	       }else{
	          NetworkInfo info = connectivity.getActiveNetworkInfo();
	          if(info.isAvailable()){
	        	  return true;
	          }
	       }
	   }catch (Exception e){}
	   return false;
   }
    public static boolean checkWifi() {   
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);   
        if(mWifiManager !=null){
	        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();  
	        if(wifiInfo != null){
	       
		        int ipAddress = wifiInfo.getIpAddress();   
		        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {   
		             return true;   
		        }
	        }
        }
        return false;       
    }  
    
    //0-net;1-wap;2-!net&wap
    public int checkCurNetwork(final Activity mActivity){
    	Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");   
    	if(PREFERRED_APN_URI ==null)
    		return 0;
        String proxy="";
        Cursor c =mActivity.getContentResolver().query(PREFERRED_APN_URI,null, null, null, null);  
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        curNetworkType=tm.getSimOperator();
        
        if(c != null && c.getCount() >= 1){ 
            c.moveToFirst(); 
            proxy=c.getString(c.getColumnIndex("proxy"));
              
            c.close();   
            if(proxy == null){
            	return 0;
            }  
           if(proxy.startsWith("10.0.0"))
           { 
        	   RecordcurrentNetwork(this);
        	   newNetworkId = lookforNetApn(this);
        	   if(newNetworkId != -1){
        		  return 1;
        	   }else{ 
        		   return 2;  
        	   }   
            }else{  
            } 
        }else{
        	return 2;
        }
       
        return 0;
    }
    ////////////shenme   //回复原来厂商
    private int RecordcurrentNetwork(final Activity mActivity){
    	int id =-1;
    	Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");  
    	if(PREFERRED_APN_URI != null){
	        Cursor c =mActivity.getContentResolver().query(PREFERRED_APN_URI,null, null, null, null);  
	        if(c != null && c.getCount() >= 1){ 
	            c.moveToFirst();
	            id = c.getColumnIndex("_id");
	            return id;
	        }
    	}
        return -1;
    }
public int lookforNetApn(final Activity mActivity){
	 	
	 	Uri CURRENT_APN_URI = Uri.parse("content://telephony/carriers/current");     
	     int _id=-1; 
	     
	     String name;
	     String name1 = "none";
	     if(curNetworkType.equals("46000")||curNetworkType.equals("46002"))
	    	 name = "cmnet";
	     else if(curNetworkType.equals("46001"))
	    	 name ="uninet";
	     else if(curNetworkType.equals("46003"))
	     {
	    	 name = "#777";
	    	 name1 = "ctnet";
	     }
	     else{
	    	 name ="none";
	    	 name1 = "none";
	    	 return _id;
	     }
	     
	     Cursor c =mActivity.getContentResolver().query(CURRENT_APN_URI,null, null, null, null); 
	     if(c.moveToFirst()){
	    	 do{ 
		         String id = c.getString(c.getColumnIndex("_id"));     
		         String apn = c.getString(c.getColumnIndex("apn")).toLowerCase(); 
		         String proxy = c.getString(c.getColumnIndex("proxy"));
		         if(proxy==null)
		         	proxy ="0";
		         if((apn.equals(name)||apn.equals(name1))&&(!proxy.startsWith("10.0.0"))){
		         	_id = Integer.valueOf(id).intValue();
		         	break;
		         }
	    	 }while(c.moveToNext());
	     }
	    return _id;
	 }
	public void CreateParameters()
	{
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	    WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
	    int screenHeight = display.getHeight();
	    int screenWidth = display.getWidth();
	    uuid =  tm.getDeviceId();
	    imsi =tm.getSubscriberId(); 
	
	   /* String urlcons = SppaConstant.WAP_URL+"?udid="+urlEncode(uuid)+"&v="+SppaConstant.APP_VERSION+"&dos=Android&dosv="
	            +urlEncode(Build.VERSION.SDK)+"&model="+urlEncode(Build.MODEL)+"&hres="+screenWidth+"&vres="+screenHeight
	            +"&dist="+urlEncode(SppaConstant.dist)+"&app="+urlEncode(SppaConstant.app); //"&kosv="+urlEncode(getVersion())
	    Log.d("Rock", urlcons);
	    mservice.setRetrieveUrl(urlcons);*/
//	    mservice.retrieveInfo();
	}
	 public String urlEncode(String str){
	    	if(null == str || "".equalsIgnoreCase(str)){
	        return str;
	    	}else{
	    		return URLEncoder.encode(str);
	    	}
	    }
	 public void changeApn(int id){
	    	if(id ==-1)
	    		return;
	    	 ContentResolver resolver = this.getContentResolver();
	         ContentValues values = new ContentValues();
	         
	    	 values.put("apn_id", id);
	    	 Uri uri = Uri.parse("content://telephony/carriers/preferapn");
	    	 if(uri !=null){
		 		try {
		 		    resolver.update(uri, values, null, null);
		 		} catch (SQLException e) {
		 		
		 		}
	    	 }

	    }
	 
	 public void CreateApn(){
	        int id=-1; 
	        String name="";
	        String apn_addr="";
	        if(curNetworkType.length()==0){
	            return ;
	        }
	        if(curNetworkType.equals("46000") || curNetworkType.equals("46002")){ //中国移动 
	        	name   = "cmnet";
	        	apn_addr  = "cmnet";
		    }else if(curNetworkType.equals("46001")){ //中国联通 
		     name   = "uninet";
		     apn_addr  = "uninet";
		    }else if(curNetworkType.equals("46003")){ //中国电信 
		    	name   = "ctnet";
			    apn_addr = "ctnet";
		    } 

	        ContentResolver resolver = context.getContentResolver();
	        ContentValues values = new ContentValues();//创建APN相关数据结构
			values.put("name", name); 
			values.put("apn", apn_addr);
			values.put("type", "default"); 
			values.put("numeric", curNetworkType); 
			values.put("mcc", curNetworkType.substring(0, 3)); 
			values.put("mnc", curNetworkType.substring(3, curNetworkType.length())); 
			values.put("proxy", ""); 
			values.put("port", ""); 
			values.put("mmsproxy", ""); 
			values.put("mmsport", ""); 
			if(apn_addr.equals("ctnet"))
			{
				values.put("user", "card"); 
				values.put("password", "card"); 
			}else{
				values.put("user", ""); 
				values.put("password", ""); 
			}
			values.put("server", ""); 
			values.put("mmsc", ""); 
			 
			Cursor c = null;
	        try{
	            Uri newRow = resolver.insert(Uri.parse("content://telephony/carriers"), values);
	            if(newRow != null)
	            {
	                c = resolver.query(newRow, null, null, null, null);
	                if(c==null){
	                int idindex = c.getColumnIndex("_id");
	                c.moveToFirst();
	                id = c.getShort(idindex);
	                }
	            }
	        }
	        catch(SQLException e)
	        {
	        }

	        if(c !=null )
	            c.close();
	        changeApn(id);

	    }
	 
	    public static boolean needUpgrade(){
	    	String t1 = SppaConstant.APP_VERSION.replace(".","");
	    	
	    	
	    	if(t1.length()<=2)
	    		t1 +="0";
	    	if(defaults_mservice.getUpgradeVersion()==null)
	    		return false;
	    	String t2 = defaults_mservice.getUpgradeVersion().replace(".","");
	    	if(t2==null || t2=="" || t2.length() >3)
	    		return false;
	    	if(t2.length()<=2)
	    		t2 +="0";
	    	
	    	int n1 = Integer.valueOf(t1).intValue();
	    	int n2=0;
	    	try{
	    	n2 = Integer.valueOf(t2).intValue();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    	return (n2>n1);
	    }
	 
	 public static BgWalkinActivity getInstance(){
	        if(instance == null){
	            instance =  new  BgWalkinActivity();
	        }
	        return instance;
	    }
	 
}
