package com.walkin.service;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;



import com.walkin.json.JSONArray;
import com.walkin.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class GamesMenuService {
    private static GamesMenuService instance = null;
    private SharedPreferences sharepre;
    
   
    public static final String RESPONSE = "response"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String META = "meta";
    public static final String META_MESSAGE = "message";
    public static final String META_CODE = "code";
    public static final String NOTIFICATIONS = "notifications";
//    public static final String NOTIFICATIONS_TYPE = "type";
    public static final String NOTIFICATIONS_UNREADCOUNT = "unreadCount";
    private  List<Map<String, Object>>  list_item_newNotifications = new ArrayList<Map<String, Object>>(); 
    private  Map<String, Object>   map_item_newNotifications  = null; 
    
    
    
    public static final String JSONObj_item_newNotifications_id = "JSONObj_item_newNotifications_id"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String JSONObj_item_newNotifications_imageURL = "JSONObj_item_newNotifications_imageURL";
    public static final String JSONObj_item_newNotifications_text = "JSONObj_item_newNotifications_text";
    public static final String JSONObj_item_newNotifications_type = "JSONObj_item_newNotifications_type";
    public static final String JSONObj_item_newNotifications_unread = "JSONObj_item_newNotifications_unread";
    
    public static final String JSONObj_item_stores_address = "JSONObj_item_stores_address";
    public static final String JSONObj_item_stores_area = "JSONObj_item_stores_area"; 
    public static final String JSONObj_item_stores_city = "JSONObj_item_stores_city";
    public static final String JSONObj_item_stores_distance = "JSONObj_item_stores_distance";
    public static final String JSONObj_item_stores_id = "JSONObj_item_stores_id";
    public static final String JSONObj_item_stores_imageURL = "JSONObj_item_stores_imageURL";
    public static final String JSONObj_item_stores_lastCheckinHere = "JSONObj_item_stores_lastCheckinHere";
    public static final String JSONObj_item_stores_lat = "JSONObj_item_stores_lat";
    public static final String JSONObj_item_stores_lng = "JSONObj_item_stores_lng"; 
    public static final String JSONObj_item_stores_name = "JSONObj_item_stores_name";
    public static final String JSONObj_item_stores_phoneNumber = "JSONObj_item_stores_phoneNumber";
    public static final String JSONObj_item_stores_postalCode = "JSONObj_item_stores_postalCode";
    public static final String JSONObj_item_stores_state = "JSONObj_item_stores_state";
    public static final String JSONObj_item_stores_storeNo = "JSONObj_item_stores_storeNo";
    public static final String JSONObj_item_stores_hasActivities = "JSONObj_item_stores_hasActivities";
    
    

    
    public static final String JSONObj_item_barcodeStandard = "JSONObj_item_barcodeStandard";
    public static final String JSONObj_item_checkins = "JSONObj_item_checkins";
    public static final String JSONObj_item_cnName = "JSONObj_item_cnName";
    public static final String JSONObj_item_description = "JSONObj_item_description";
    public static final String JSONObj_item_enName = "JSONObj_item_enName";
//    public static final String JSONObj_item_id = "JSONObj_item_id";
    public static final String JSONObj_item_imagesURL = "JSONObj_item_imagesURL";
    public static final String JSONObj_item_isCheckinToday = "JSONObj_item_isCheckinToday";
    
    public static final String JSONObj_item_lastCheckinHere = "JSONObj_item_lastCheckinHere";
    public static final String list_item_marker = "list_item_marker";
    public static final String list_item_stores = "list_item_stores";
    public static String JSONObj_item_marker_activityType = "JSONObj_item_marker_activityType";
    public static String JSONObj_item_marker_animationsURL = "JSONObj_item_marker_animationsURL";
    public static String JSONObj_item_marker_dealsEarned = "JSONObj_item_marker_dealsEarned";
    public static String JSONObj_item_marker_gameId = "JSONObj_item_marker_gameId";
    public static String JSONObj_item_marker_distance = "JSONObj_item_marker_distance";
    public static String JSONObj_item_marker_id = "JSONObj_item_marker_id";
    public static String JSONObj_item_marker_imageURL = "JSONObj_item_marker_imageURL";
    public static String JSONObj_item_marker_inbiEarned = "JSONObj_item_marker_inbiEarned";
    
    
    public static String JSONObj_item_title = "JSONObj_item_title";
    public static String JSONObj_item_excerpt = "JSONObj_item_excerpt";
    public static String JSONObj_item_thumbnail = "JSONObj_item_thumbnail";
    public static String JSONObj_item_content = "JSONObj_item_content";
    public static String JSONObj_item_date = "JSONObj_item_date";
    public static String JSONObj_item_id = "JSONObj_item_id";
    
    private String retrieveUrl;
    private boolean successed = false;
	private String message;               
	private String code;            
//	private String type; 
	private String unreadCount;

    private  Map<String, Object> map_item = null;     
    private  List<Map<String, Object>> list_jobj_item = new ArrayList<Map<String, Object>>(); 
	
    private  Map<String, Object> map_item_brandsID = null;     
    private  List<Map<String, Object>> list_jobj_item_brandsID = new ArrayList<Map<String, Object>>(); 

    
    
    
	/**
	 * @return the list_jobj_item_brandsID
	 */
	public List<Map<String, Object>> getList_jobj_item_brandsID() {
		return list_jobj_item_brandsID;
	}

	/**
	 * @param listJobjItemBrandsID the list_jobj_item_brandsID to set
	 */
	public void setList_jobj_item_brandsID(
			List<Map<String, Object>> listJobjItemBrandsID) {
		list_jobj_item_brandsID = listJobjItemBrandsID;
	}

	/**
	 * @return the list_jobj_item
	 */
	public List<Map<String, Object>> getList_jobj_item() {
		return list_jobj_item;
	}

	/**
	 * @param listJobjItem the list_jobj_item to set
	 */
	public void setList_jobj_item(List<Map<String, Object>> listJobjItem) {
		list_jobj_item = listJobjItem;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	

	/**
	 * @return the unreadCount
	 */
	public String getUnreadCount() {
		return unreadCount;
	}

	/**
	 * @param unreadCount the unreadCount to set
	 */
	public void setUnreadCount(String unreadCount) {
		this.unreadCount = unreadCount;
	}
	private Context context;
    public void setActivity(Activity activity) {
    	context = activity;
        sharepre = PreferenceManager.getDefaultSharedPreferences(activity);
    }
    
    private GamesMenuService(){}

    public static GamesMenuService getInstance(){
        if(instance == null){
            instance =  new  GamesMenuService();
        }
        return instance;
    }
    
	public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    public String getRetrieveUrl() {
        return retrieveUrl;
    }

    public void setRetrieveUrl(String retrieveUrl) {
        this.retrieveUrl = retrieveUrl;
    }

    public void retrieveGameInfo(String urlcons){
        try{
          String jsStr ;
            
            /*  InputStream is = context.getAssets().open("activitylist.json");  
                int size = is.available();  
                byte[] buffer = new byte[size];  
                is.read(buffer);  
                is.close();  
                String   jsStr = new String(buffer, "utf8");  */
             //   Log.d("Rock", jsStr+"=111111111111111");
       
                try{
                	/*定义获取文件内容的URL*/
                	URL myURL = new URL(urlcons);
                	/*打开URL连接*/
                	URLConnection ucon = myURL.openConnection();
                	/*使用IputStreams，从URLConnection读取数据*/
                	InputStream is = ucon.getInputStream();
                	BufferedInputStream bis = new BufferedInputStream(is);
                	/*用ByteArrayBuffer做缓存*/
                	ByteArrayBuffer baf = new ByteArrayBuffer(50);
                	int current = 0;
                	while((current = bis.read()) != -1){
                	baf.append((byte)current);
                	}
                	/*将缓存的内容转为String，用Utf-8编码*/
                	jsStr = EncodingUtils.getString(baf.toByteArray(),"UTF-8");
                	} catch(Exception e){
                	/*捕捉异常错误*/
            		jsStr = e.getMessage();
                	}
                
                
                
                
                
                
                
                
                
            
            
            JSONArray json = null;
            JSONObject JSONOBJ_response = null;
            JSONObject JSONOBJ_meta = null;
            JSONObject JSONOBJ_notifications = null;
            
            JSONArray JOARRAY_brands = null;
            JSONArray JOARRAY_item_stores = null;
            JSONObject JSONObj_item_marker= null;
            JSONObject JSONObj_item=null;
            JSONObject JSONObj_item_stores = null;
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            json = new JSONArray(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            
      list_jobj_item = new ArrayList<Map<String, Object>>(); 
                     	    	if(json!=null && !"".equals(json)){
                     	     for (int j = 0; j < json.length(); j++) {  
 	    	map_item = new HashMap<String, Object>(); // 存放到MAP里面     
               JSONObj_item = json.getJSONObject(j); // 得到每个对象     
                     	    String	JSONObj_item_title = JSONObj_item.getString("title");
                     	    String	JSONObj_item_excerpt = JSONObj_item.getString("excerpt");     
                     	  String	JSONObj_item_thumbnail = JSONObj_item.getString("thumbnail");   
                     	String	JSONObj_item_content = JSONObj_item.getString("content");   
                     	String	JSONObj_item_date = JSONObj_item.getString("date");  
                     	String	JSONObj_item_id = JSONObj_item.getString("id");  
                     	map_item.put("JSONObj_item_title",JSONObj_item_title);   
                     	map_item.put("JSONObj_item_excerpt",JSONObj_item_excerpt);   
                     	map_item.put("JSONObj_item_thumbnail",JSONObj_item_thumbnail);   
                     	map_item.put("JSONObj_item_content",JSONObj_item_content);   
                     	map_item.put("JSONObj_item_date",JSONObj_item_date);   
                     	map_item.put("JSONObj_item_id",JSONObj_item_id);   
                     	list_jobj_item.add(map_item);   
                 	    	
//                 	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                     	    }
                     	    }
                     	    	 /*     
//            type= JSONOBJ_notifications.getString(NOTIFICATIONS_TYPE);
            unreadCount = JSONOBJ_notifications.getString(NOTIFICATIONS_UNREADCOUNT);
            
            JSONOBJ_meta = json.getJSONObject(META);
            message= JSONOBJ_meta.getString(META_MESSAGE);
            code = JSONOBJ_meta.getString(META_CODE);
             
            JSONOBJ_response = json.getJSONObject(RESPONSE);
           
            JOARRAY_brands=	JSONOBJ_response.getJSONArray("brands");
list_jobj_item = new ArrayList<Map<String, Object>>(); 
            for (int i = 0; i < JOARRAY_brands.length(); i++) {     
map_item = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item = JOARRAY_brands.getJSONObject(i); // 得到每个对象     

		   String JSONObj_item_barcodeStandard = JSONObj_item.getString("barcodeStandard");
           String JSONObj_item_checkins = JSONObj_item.getString("checkins");
           String JSONObj_item_cnName = JSONObj_item.getString("cnName");     
           String JSONObj_item_description = JSONObj_item.getString("description");     
           String JSONObj_item_enName = JSONObj_item.getString("enName");  
           String JSONObj_item_id = JSONObj_item.getString("id");  
           String JSONObj_item_imagesURL = JSONObj_item.getString("imagesURL");  
           String JSONObj_item_isCheckinToday = JSONObj_item.getString("isCheckinToday"); 
           String JSONObj_item_lastCheckinHere = JSONObj_item.getString("lastCheckinHere"); 
       

           JSONArray  JOARRAY_item_marker =  JSONObj_item.getJSONArray("markers");
           List<Map<String, String>> list_item_marker = new ArrayList<Map<String, String>>(); 
           
	if(JOARRAY_item_marker!=null && !"".equals(JOARRAY_item_marker)){
 for (int j = 0; j < JOARRAY_item_marker.length(); j++) {  
            Map<String, String> map_item_marker = new HashMap<String, String>(); // 存放到MAP里面     
           JSONObj_item_marker = JOARRAY_item_marker.getJSONObject(j); // 得到每个对象     
           String  JSONObj_item_marker_activityType= JSONObj_item_marker.getString("activityType");
           String  JSONObj_item_marker_animationsURL= JSONObj_item_marker.getString("animationsURL");
           String  JSONObj_item_marker_dealsEarned= JSONObj_item_marker.getString("dealsEarned");
           String  JSONObj_item_marker_distance= JSONObj_item_marker.getString("distance");
           String  JSONObj_item_marker_gameId= JSONObj_item_marker.getString("game"); 
           String  JSONObj_item_marker_id= JSONObj_item_marker.getString("id");
           String  JSONObj_item_marker_imageURL= JSONObj_item_marker.getString("imageURL");
           String  JSONObj_item_marker_inbiEarned= JSONObj_item_marker.getString("inbiEarned");
           
           
           map_item_marker.put("JSONObj_item_marker_activityType",JSONObj_item_marker_activityType);   
           map_item_marker.put("JSONObj_item_marker_animationsURL",JSONObj_item_marker_animationsURL);    
           map_item_marker.put("JSONObj_item_marker_dealsEarned",JSONObj_item_marker_dealsEarned);    
           map_item_marker.put("JSONObj_item_marker_distance",JSONObj_item_marker_distance);    
           map_item_marker.put("JSONObj_item_marker_gameId",JSONObj_item_marker_gameId);    
           map_item_marker.put("JSONObj_item_marker_id",JSONObj_item_marker_id); 
           map_item_marker.put("JSONObj_item_marker_imageURL",JSONObj_item_marker_imageURL); 
           map_item_marker.put("JSONObj_item_marker_inbiEarned",JSONObj_item_marker_inbiEarned);   
	        
           list_item_marker.add(map_item_marker);   
           
           
                 	     }
                 	     }
           
           
//Log.d("Rock", JSONObj_item_marker+"JSONObj_item_marker的值");
//if ( JSONObj_item_marker!=null && !"null".equals(JSONObj_item_marker)&& !"{}".equals(JSONObj_item_marker)) {
//if (  !"{}".equals(JSONObj_item_marker)) {	


//            }
JOARRAY_item_stores = JSONObj_item.getJSONArray("stores");
List<Map<String, String>> list_item_stores = new ArrayList<Map<String, String>>(); 
      	    	if(JOARRAY_item_stores!=null && !"".equals(JOARRAY_item_stores)){
      	     for (int j = 0; j < JOARRAY_item_stores.length(); j++) {  
 Map<String, String> map_item_stores = new HashMap<String, String>(); // 存放到MAP里面     
JSONObj_item_stores = JOARRAY_item_stores.getJSONObject(j); // 得到每个对象     
      	    String	JSONObj_item_stores_address = JSONObj_item_stores.getString("address");
      	    String	JSONObj_item_stores_area = JSONObj_item_stores.getString("area");     
  	    	String	JSONObj_item_stores_city = JSONObj_item_stores.getString("city");     
  	    	String	JSONObj_item_stores_distance = JSONObj_item_stores.getString("distance");  
  	    	String	JSONObj_item_stores_id = JSONObj_item_stores.getString("id");  
  	    	String	JSONObj_item_stores_imageURL = JSONObj_item_stores.getString("imageURL");  
  	    	String	JSONObj_item_stores_lastCheckinHere = JSONObj_item_stores.getString("lastCheckinHere");
  	    	String	JSONObj_item_stores_hasActivities = JSONObj_item_stores.getString("hasActivities");
  	    	
  	    	String	JSONObj_item_stores_lat = JSONObj_item_stores.getString("lat");  
  	    	String	JSONObj_item_stores_lng = JSONObj_item_stores.getString("lng");  
  	    	String	JSONObj_item_stores_name = JSONObj_item_stores.getString("name");
  	    	String	JSONObj_item_stores_phoneNumber = JSONObj_item_stores.getString("phoneNumber");  
  	    	String	JSONObj_item_stores_postalCode = JSONObj_item_stores.getString("postalCode");  
  	    	String	JSONObj_item_stores_state = JSONObj_item_stores.getString("state");
  	    	String	JSONObj_item_stores_storeNo = JSONObj_item_stores.getString("storeNo");  
  	     
  	    	map_item_stores.put("JSONObj_item_stores_address",JSONObj_item_stores_address);   
  	    	map_item_stores.put("JSONObj_item_stores_area",JSONObj_item_stores_area);    
  	    	map_item_stores.put("JSONObj_item_stores_city",JSONObj_item_stores_city);    
  	    	map_item_stores.put("JSONObj_item_stores_distance",JSONObj_item_stores_distance);    
  	    	map_item_stores.put("JSONObj_item_stores_id",JSONObj_item_stores_id);    
  	    	map_item_stores.put("JSONObj_item_stores_imageURL",JSONObj_item_stores_imageURL); 
  	    	map_item_stores.put("JSONObj_item_stores_lastCheckinHere",JSONObj_item_stores_lastCheckinHere); 
  	    	map_item_stores.put("JSONObj_item_stores_hasActivities",JSONObj_item_stores_hasActivities); 
  	    	map_item_stores.put("JSONObj_item_stores_lat",JSONObj_item_stores_lat);   
	    	map_item_stores.put("JSONObj_item_stores_lng",JSONObj_item_stores_lng);    
	    	map_item_stores.put("JSONObj_item_stores_name",JSONObj_item_stores_name);    
	    	map_item_stores.put("JSONObj_item_stores_phoneNumber",JSONObj_item_stores_phoneNumber);    
	    	map_item_stores.put("JSONObj_item_stores_postalCode",JSONObj_item_stores_postalCode);    
	    	map_item_stores.put("JSONObj_item_stores_state",JSONObj_item_stores_state);    
	    	map_item_stores.put("JSONObj_item_stores_storeNo",JSONObj_item_stores_storeNo); 
	        
  	    	list_item_stores.add(map_item_stores);   
  	    	
//  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
      	    }
      	    }
      	    	map_item.put("JSONObj_item_barcodeStandard",JSONObj_item_barcodeStandard);   
      	    	map_item.put("JSONObj_item_checkins",JSONObj_item_checkins);   
    	      	map_item.put("JSONObj_item_cnName",JSONObj_item_cnName);    
    	      	map_item.put("JSONObj_item_description",JSONObj_item_description);    
    	      	map_item.put("JSONObj_item_enName",JSONObj_item_enName);    
      	    	map_item.put("JSONObj_item_id",JSONObj_item_id);    
      	    	map_item.put("JSONObj_item_imagesURL",JSONObj_item_imagesURL); 
      	    	map_item.put("JSONObj_item_isCheckinToday",JSONObj_item_isCheckinToday); 
      	    	map_item.put("JSONObj_item_lastCheckinHere",JSONObj_item_lastCheckinHere); 
      	    	
      	    	map_item.put("JSONObj_item_marker_activityType",JSONObj_item_marker_activityType);   
	      	  	map_item.put("JSONObj_item_marker_animationsURL",JSONObj_item_marker_animationsURL);    
	      	  	map_item.put("JSONObj_item_marker_dealsEarned",JSONObj_item_marker_dealsEarned);    
	      	  	map_item.put("JSONObj_item_marker_gameId",JSONObj_item_marker_gameId);    
	      	  	map_item.put("JSONObj_item_marker_distance",JSONObj_item_marker_distance);    
	      	  	map_item.put("JSONObj_item_marker_id",JSONObj_item_marker_id);    
	      	  	map_item.put("JSONObj_item_marker_imageURL",JSONObj_item_marker_imageURL); 
	      	  	map_item.put("JSONObj_item_marker_inbiEarned",JSONObj_item_marker_inbiEarned); 
	      	  	map_item.put("list_item_marker",list_item_marker); 
    	    	map_item.put("list_item_stores",list_item_stores); 
    	    	list_jobj_item.add(map_item); */
//	      }
            
           // Log.d("Rock", ((ArrayList<?>) list_jobj_item.get(0).get("list_item_stores")).get(0)+":值");
            setSuccessed(true);
            
        }catch (Exception e){
        	Log.d("Rock", e+":e");
        	
        }
    }
    private void regParam(String key,String value){
        if(value != null && !"".equals(value)){
            sharepre.edit().putString(key,value).commit();
        }else{
        	 sharepre.edit().putString(key,"999").commit();
        }
    }
    public void setValue(String key){
    	sharepre.edit().putString(key,"").commit();
    }
    public String getValue(String key){
        return sharepre.getString(key,"");
    }

}
