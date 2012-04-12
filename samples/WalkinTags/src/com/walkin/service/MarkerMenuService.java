package com.walkin.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.walkin.common.BaseAuthenicationHttpClient;
import com.walkin.common.BaseAuthenicationHttpsClient;
import com.walkin.json.JSONArray;
import com.walkin.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class MarkerMenuService {
    private static MarkerMenuService instance = null;
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
    
    public static final String JSONObj_brands_activityCount = "JSONObj_brands_activityCount";
    public static final String JSONObj_brands_barcodeStandard = "JSONObj_brands_barcodeStandard";
    public static final String JSONObj_brands_checkins = "JSONObj_brands_checkins"; 
    public static final String JSONObj_brands_cnName = "JSONObj_brands_cnName";
    public static final String JSONObj_brands_description = "JSONObj_brands_description";
    public static final String JSONObj_brands_id = "JSONObj_brands_id";
    public static final String JSONObj_brands_enName = "JSONObj_brands_enName";
    public static final String JSONObj_brands_imagesURL = "JSONObj_brands_imagesURL";
    public static final String JSONObj_brands_isCheckinToday = "JSONObj_brands_isCheckinToday";
    public static final String JSONObj_brands_lastCheckinHere = "JSONObj_brands_lastCheckinHere";
    public static final String JSONObj_brands_weiboAccountName = "JSONObj_brands_weiboAccountName";
    
    
    
    public static final String JSONObj_brands_deals_brandCnName = "JSONObj_brands_deals_brandCnName";
    public static final String JSONObj_brands_deals_brandEnName = "JSONObj_brands_deals_brandEnName"; 
    public static final String JSONObj_brands_deals_brandId = "JSONObj_brands_deals_brandId";
    public static final String JSONObj_brands_deals_detailDescription = "JSONObj_brands_deals_detailDescription";
    public static final String JSONObj_brands_deals_discount = "JSONObj_brands_deals_discount";
    public static final String JSONObj_brands_deals_distance = "JSONObj_brands_deals_distance";
    public static final String JSONObj_brands_deals_giftDescription = "JSONObj_brands_deals_giftDescription";
    public static final String JSONObj_brands_deals_id = "JSONObj_brands_deals_id";
    public static final String JSONObj_brands_deals_imageUrl = "JSONObj_brands_deals_imageUrl";
    
    public static final String JSONObj_brands_deals_isNew = "JSONObj_brands_deals_isNew"; 
    public static final String JSONObj_brands_deals_legalText = "JSONObj_brands_deals_legalText";
    public static final String JSONObj_brands_deals_liked = "JSONObj_brands_deals_liked";
    
    public static final String JSONObj_brands_deals_rebate = "JSONObj_brands_deals_rebate";
    public static final String JSONObj_brands_deals_redemptionCode = "JSONObj_brands_deals_redemptionCode";
    public static final String JSONObj_brands_deals_redemptionType = "JSONObj_brands_deals_redemptionType";
    public static final String JSONObj_brands_deals_reference = "JSONObj_brands_deals_reference";
    public static final String JSONObj_brands_deals_retailPrice = "JSONObj_brands_deals_retailPrice";
    public static final String JSONObj_brands_deals_shortDescription = "JSONObj_brands_deals_shortDescription";
    public static final String JSONObj_brands_deals_used = "JSONObj_brands_deals_used";
    public static final String JSONObj_brands_deals_validUntil = "JSONObj_brands_deals_validUntil";
    
    
    
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
    
    public static final String JSONObj_item_stores_floors_shortDescription = "JSONObj_item_stores_floors_shortDescription";
    public static final String JSONObj_item_stores_floors_longDescription = "JSONObj_item_stores_floors_longDescription";
    public static final String JSONObj_item_stores_floors_id = "JSONObj_item_stores_floors_id";
    public static final String JSONObj_item_stores_floors_imageUrl = "JSONObj_item_stores_floors_imageUrl";
    public static final String JSONObj_item_stores_floors_label = "JSONObj_item_stores_floors_label";
    
    public static String JSONObj_item_usedMarker_activityType = "JSONObj_item_usedMarker_activityType";
    public static String JSONObj_item_usedMarker_animationsURL = "JSONObj_item_usedMarker_animationsURL";
    public static String JSONObj_item_usedMarker_dealsEarned = "JSONObj_item_usedMarker_dealsEarned";
    public static String JSONObj_item_usedMarker_distance = "JSONObj_item_usedMarker_distance";
    public static String JSONObj_item_usedMarker_id = "JSONObj_item_usedMarker_id";
    public static String JSONObj_item_usedMarker_gameId = "JSONObj_item_usedMarker_gameId";
    public static String JSONObj_item_usedMarker_imageURL = "JSONObj_item_usedMarker_imageURL";
    public static String JSONObj_item_usedMarker_inbiEarned = "JSONObj_item_usedMarker_inbiEarned";
    public static String JSONObj_brands_nextMarkers_activityType = "JSONObj_brands_nextMarkers_activityType";
    public static String JSONObj_brands_nextMarkers_animationsURL = "JSONObj_brands_nextMarkers_animationsURL";
    public static String JSONObj_brands_nextMarkers_dealsEarned = "JSONObj_brands_nextMarkers_dealsEarned";
    public static String JSONObj_brands_nextMarkers_distance = "JSONObj_brands_nextMarkers_distance";
    public static String JSONObj_brands_nextMarkers_gameId = "JSONObj_brands_nextMarkers_gameId";
    public static String JSONObj_brands_nextMarkers_id = "JSONObj_brands_nextMarkers_id";
    public static String JSONObj_brands_nextMarkers_imageURL = "JSONObj_brands_nextMarkers_imageURL";
    public static String JSONObj_brands_nextMarkers_inbiEarned = "JSONObj_brands_nextMarkers_inbiEarned";
    
    
    
    private String retrieveUrl;
    private boolean successed = false;
	private String message;               
	private String code= "null";            
//	private String type; 
	private String unreadCount;

    private  Map<String, Object> map_item = null;     
    private  List<Map<String, Object>> list_jobj_item = new ArrayList<Map<String, Object>>(); 
    private  Map<String, Object> map_item_nextMarkers = null;     
    private  List<Map<String, Object>> list_jobj_item_nextMarkers = new ArrayList<Map<String, Object>>();
    private  Map<String, Object> map_item_deals = null;     
    private  List<Map<String, Object>> list_jobj_item_deals = new ArrayList<Map<String, Object>>();
    private  Map<String, Object> map_item_floors = null;     
    private  List<Map<String, Object>> list_jobj_item_floors = new ArrayList<Map<String, Object>>();
    
    
    
    
    
    
	/**
	 * @return the list_item_newNotifications
	 */
	public List<Map<String, Object>> getList_item_newNotifications() {
		return list_item_newNotifications;
	}

	/**
	 * @param listItemNewNotifications the list_item_newNotifications to set
	 */
	public void setList_item_newNotifications(
			List<Map<String, Object>> listItemNewNotifications) {
		list_item_newNotifications = listItemNewNotifications;
	}

	/**
	 * @return the list_jobj_item_floors
	 */
	public List<Map<String, Object>> getList_jobj_item_floors() {
		return list_jobj_item_floors;
	}

	/**
	 * @param listJobjItemFloors the list_jobj_item_floors to set
	 */
	public void setList_jobj_item_floors(
			List<Map<String, Object>> listJobjItemFloors) {
		list_jobj_item_floors = listJobjItemFloors;
	}

	/**
	 * @return the list_jobj_item_nextMarkers
	 */
	public List<Map<String, Object>> getList_jobj_item_nextMarkers() {
		return list_jobj_item_nextMarkers;
	}

	/**
	 * @param listJobjItemNextMarkers the list_jobj_item_nextMarkers to set
	 */
	public void setList_jobj_item_nextMarkers(
			List<Map<String, Object>> listJobjItemNextMarkers) {
		list_jobj_item_nextMarkers = listJobjItemNextMarkers;
	}

	/**
	 * @return the list_jobj_item_deals
	 */
	public List<Map<String, Object>> getList_jobj_item_deals() {
		return list_jobj_item_deals;
	}

	/**
	 * @param listJobjItemDeals the list_jobj_item_deals to set
	 */
	public void setList_jobj_item_deals(List<Map<String, Object>> listJobjItemDeals) {
		list_jobj_item_deals = listJobjItemDeals;
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
    
    private MarkerMenuService(){}

    public static MarkerMenuService getInstance(){
        if(instance == null){
            instance =  new  MarkerMenuService();
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

    public void retrieveMarkerInfo(Map<String,String>params){
        try{
           final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params);
            
             /* InputStream is = context.getAssets().open("newmarker");  
                int size = is.available();  
                byte[] buffer = new byte[size];  
                is.read(buffer);  
                is.close();  
                String   jsStr = new String(buffer, "utf8");  */
                Log.d("Rock", jsStr+"=111111111111111");
       
            
            
            JSONObject json = null;
            JSONObject JSONOBJ_response = null;
            JSONObject JSONOBJ_response_checkin = null;
            JSONObject JSONOBJ_response_checkin_brand = null;
            
            JSONObject JSONOBJ_meta = null;
            JSONObject JSONOBJ_notifications = null;
            
            JSONArray JOARRAY_brands_nextMarkers = null;
            JSONObject JSONObj_brands_nextMarkers = null;
            JSONArray JOARRAY_item_stores = null;
            JSONArray JOARRAY_item_stores_floors=null;
            JSONObject JSONObj_item_stores_floors=null;
            JSONArray JOARRAY_brands_deals = null;
            JSONObject JSONObj_brands_deals=null;
            
            JSONObject JSONObj_item_stores = null;
            JSONObject JSONObj_item_usedMarker = null;
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            JSONOBJ_notifications = json.getJSONObject(NOTIFICATIONS);
//            type= JSONOBJ_notifications.getString(NOTIFICATIONS_TYPE);
            JOARRAY_jobj_notifications_newNotifications=	JSONOBJ_notifications.getJSONArray("newNotifications");
            
            list_item_newNotifications = new ArrayList<Map<String, Object>>(); 
                     	    	if(JOARRAY_jobj_notifications_newNotifications!=null && !"".equals(JOARRAY_jobj_notifications_newNotifications)){
                     	     for (int j = 0; j < JOARRAY_jobj_notifications_newNotifications.length(); j++) {  
               map_item_newNotifications = new HashMap<String, Object>(); // 存放到MAP里面     
               JSONObj_item_newNotifications = JOARRAY_jobj_notifications_newNotifications.getJSONObject(j); // 得到每个对象     
                     	    String	JSONObj_item_newNotifications_id = JSONObj_item_newNotifications.getString("id");
                     	    String	JSONObj_item_newNotifications_imageURL = JSONObj_item_newNotifications.getString("imageUrl");     
                     	  String	JSONObj_item_newNotifications_text = JSONObj_item_newNotifications.getString("text");   
                     	String	JSONObj_item_newNotifications_type = JSONObj_item_newNotifications.getString("type");   
                     	String	JSONObj_item_newNotifications_unread = JSONObj_item_newNotifications.getString("unread");  
                     	  map_item_newNotifications.put("JSONObj_item_newNotifications_id",JSONObj_item_newNotifications_id);   
                     	map_item_newNotifications.put("JSONObj_item_newNotifications_imageURL",JSONObj_item_newNotifications_imageURL);   
                    	  map_item_newNotifications.put("JSONObj_item_newNotifications_text",JSONObj_item_newNotifications_text);   
                       	map_item_newNotifications.put("JSONObj_item_newNotifications_type",JSONObj_item_newNotifications_type);   
                        	  map_item_newNotifications.put("JSONObj_item_newNotifications_unread",JSONObj_item_newNotifications_unread);   
                     	list_item_newNotifications.add(map_item_newNotifications);   
                 	    	
//                 	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                     	    }
                     	    }
            
                     	    	
                     	    	
                     	    	
    unreadCount = JSONOBJ_notifications.getString(NOTIFICATIONS_UNREADCOUNT);
            JSONOBJ_meta = json.getJSONObject(META);
            message= JSONOBJ_meta.getString(META_MESSAGE);
            code = JSONOBJ_meta.getString(META_CODE);
             
            JSONOBJ_response = json.getJSONObject(RESPONSE);
            
            
            JSONOBJ_response_checkin = JSONOBJ_response.getJSONObject("checkin");
            JSONOBJ_response_checkin_brand= JSONOBJ_response_checkin.getJSONObject("brand");
         
            list_jobj_item = new ArrayList<Map<String, Object>>();    
            map_item = new HashMap<String, Object>(); // 存放到MAP里面     
            
          String JSONObj_brands_activityCount= JSONOBJ_response_checkin_brand.getString("activityCount");
          String JSONObj_brands_barcodeStandard= JSONOBJ_response_checkin_brand.getString("barcodeStandard");
          String JSONObj_brands_checkins = JSONOBJ_response_checkin_brand.getString("checkins");
          String JSONObj_brands_cnName= JSONOBJ_response_checkin_brand.getString("cnName");
          String JSONObj_brands_description = JSONOBJ_response_checkin_brand.getString("description");
          String  JSONObj_brands_enName= JSONOBJ_response_checkin_brand.getString("enName");
          String JSONObj_brands_id = JSONOBJ_response_checkin_brand.getString("id");
          String  JSONObj_brands_imagesURL= JSONOBJ_response_checkin_brand.getString("imagesUrl");
          String JSONObj_brands_isCheckinToday = JSONOBJ_response_checkin_brand.getString("isCheckinToday");
          String JSONObj_brands_lastCheckinHere = JSONOBJ_response_checkin_brand.getString("lastCheckinHere");
          String  JSONObj_brands_weiboAccountName= JSONOBJ_response_checkin_brand.getString("weiboAccountName");
          
           JOARRAY_brands_deals = JSONOBJ_response_checkin_brand.getJSONArray("deals");  
          list_jobj_item_deals = new ArrayList<Map<String, Object>>(); 
           if(JOARRAY_brands_deals!=null && !"".equals(JOARRAY_brands_deals)){
                       for (int i = 0; i < JOARRAY_brands_deals.length(); i++) {     
           map_item_deals = new HashMap<String, Object>(); // 存放到MAP里面   
           JSONObj_brands_deals = JOARRAY_brands_deals.getJSONObject(i); // 得到每个对象     
           String JSONObj_brands_deals_brandCnName = JSONObj_brands_deals.getString("brandCnName");
           String JSONObj_brands_deals_brandEnName = JSONObj_brands_deals.getString("brandEnName");     
           String JSONObj_brands_deals_brandId = JSONObj_brands_deals.getString("brandId");     
           String JSONObj_brands_deals_detailDescription = JSONObj_brands_deals.getString("detailDescription");  
           String JSONObj_brands_deals_discount = JSONObj_brands_deals.getString("discount");  
           String JSONObj_brands_deals_distance = JSONObj_brands_deals.getString("distance");  
           String JSONObj_brands_deals_giftDescription = JSONObj_brands_deals.getString("giftDescription"); 
           String JSONObj_brands_deals_id = JSONObj_brands_deals.getString("id");
           String JSONObj_brands_deals_imageUrl = JSONObj_brands_deals.getString("imageUrl");
           String JSONObj_brands_deals_isNew = JSONObj_brands_deals.getString("isNew");     
           String JSONObj_brands_deals_legalText = JSONObj_brands_deals.getString("legalText");    
           String JSONObj_brands_deals_liked = JSONObj_brands_deals.getString("liked");    
           String JSONObj_brands_deals_rebate = JSONObj_brands_deals.getString("rebate");  
           String JSONObj_brands_deals_redemptionCode = JSONObj_brands_deals.getString("redemptionCode");  
           String JSONObj_brands_deals_redemptionType = JSONObj_brands_deals.getString("redemptionType");  
           String JSONObj_brands_deals_reference = JSONObj_brands_deals.getString("reference"); 
           String JSONObj_brands_deals_retailPrice = JSONObj_brands_deals.getString("retailPrice");
           String JSONObj_brands_deals_shortDescription = JSONObj_brands_deals.getString("shortDescription"); 
           String JSONObj_brands_deals_used = JSONObj_brands_deals.getString("used"); 
           String JSONObj_brands_deals_validUntil = JSONObj_brands_deals.getString("validUntil");
           map_item_deals.put("JSONObj_brands_deals_brandCnName",JSONObj_brands_deals_brandCnName);   
	    	map_item_deals.put("JSONObj_brands_deals_brandEnName",JSONObj_brands_deals_brandEnName);    
	    	map_item_deals.put("JSONObj_brands_deals_brandId",JSONObj_brands_deals_brandId);    
	    	map_item_deals.put("JSONObj_brands_deals_detailDescription",JSONObj_brands_deals_detailDescription);    
	    	map_item_deals.put("JSONObj_brands_deals_discount",JSONObj_brands_deals_discount); 
	    	map_item_deals.put("JSONObj_brands_deals_distance",JSONObj_brands_deals_distance); 
	    	map_item_deals.put("JSONObj_brands_deals_giftDescription",JSONObj_brands_deals_giftDescription);   
	    	map_item_deals.put("JSONObj_brands_deals_id",JSONObj_brands_deals_id);    
	        map_item_deals.put("JSONObj_brands_deals_imageUrl",JSONObj_brands_deals_imageUrl);   
	        map_item_deals.put("JSONObj_brands_deals_isNew",JSONObj_brands_deals_isNew);   
	        map_item_deals.put("JSONObj_brands_deals_legalText",JSONObj_brands_deals_legalText); 
	    	map_item_deals.put("JSONObj_brands_deals_liked",JSONObj_brands_deals_liked);    
	    	map_item_deals.put("JSONObj_brands_deals_rebate",JSONObj_brands_deals_rebate);    
	    	map_item_deals.put("JSONObj_brands_deals_redemptionCode",JSONObj_brands_deals_redemptionCode);    
	    	map_item_deals.put("JSONObj_brands_deals_redemptionType",JSONObj_brands_deals_redemptionType);    
	    	map_item_deals.put("JSONObj_brands_deals_reference",JSONObj_brands_deals_reference); 
	    	map_item_deals.put("JSONObj_brands_deals_retailPrice",JSONObj_brands_deals_retailPrice); 
	    	map_item_deals.put("JSONObj_brands_deals_shortDescription",JSONObj_brands_deals_shortDescription);  
	    	map_item_deals.put("JSONObj_brands_deals_used",JSONObj_brands_deals_used);  
	    	map_item_deals.put("JSONObj_brands_deals_validUntil",JSONObj_brands_deals_validUntil);    
           
           list_jobj_item_deals.add(map_item_deals);  
                       }
                       }
          JOARRAY_brands_nextMarkers=	JSONOBJ_response_checkin_brand.getJSONArray("nextMarkers");
list_jobj_item_nextMarkers = new ArrayList<Map<String, Object>>(); 
if(JOARRAY_brands_nextMarkers!=null && !"".equals(JOARRAY_brands_nextMarkers)){
            for (int i = 0; i < JOARRAY_brands_nextMarkers.length(); i++) {     
map_item_nextMarkers = new HashMap<String, Object>(); // 存放到MAP里面     
            	JSONObj_brands_nextMarkers = JOARRAY_brands_nextMarkers.getJSONObject(i); // 得到每个对象     
           String JSONObj_brands_nextMarkers_activityType = JSONObj_brands_nextMarkers.getString("activityType");
           String JSONObj_brands_nextMarkers_animationsURL = JSONObj_brands_nextMarkers.getString("animationsUrl");     
           String JSONObj_brands_nextMarkers_dealsEarned = JSONObj_brands_nextMarkers.getString("dealsEarned");     
           String JSONObj_brands_nextMarkers_distance = JSONObj_brands_nextMarkers.getString("distance");  
           String JSONObj_brands_nextMarkers_gameId = JSONObj_brands_nextMarkers.getString("game");  
           String JSONObj_brands_nextMarkers_id = JSONObj_brands_nextMarkers.getString("id");  
           String JSONObj_brands_nextMarkers_imageURL = JSONObj_brands_nextMarkers.getString("imageUrl"); 
           String JSONObj_brands_nextMarkers_inbiEarned = JSONObj_brands_nextMarkers.getString("inbiEarned");
           
           
           map_item_nextMarkers.put("JSONObj_brands_nextMarkers_activityType",JSONObj_brands_nextMarkers_activityType);   
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_animationsURL",JSONObj_brands_nextMarkers_animationsURL);    
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_dealsEarned",JSONObj_brands_nextMarkers_dealsEarned);    
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_distance",JSONObj_brands_nextMarkers_distance);    
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_gameId",JSONObj_brands_nextMarkers_gameId); 
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_id",JSONObj_brands_nextMarkers_id); 
 	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_imageURL",JSONObj_brands_nextMarkers_imageURL);   
	    	map_item_nextMarkers.put("JSONObj_brands_nextMarkers_inbiEarned",JSONObj_brands_nextMarkers_inbiEarned);    
	        
	    	list_jobj_item_nextMarkers.add(map_item_nextMarkers);   
            }
			}
		JSONObj_item_stores = JSONOBJ_response_checkin_brand.getJSONObject("store"); // 得到每个对象     
		
		    String JSONObj_item_stores_address = JSONObj_item_stores.getString("address");
	        String JSONObj_item_stores_area = JSONObj_item_stores.getString("area");     
	        String JSONObj_item_stores_city = JSONObj_item_stores.getString("city");     
	        String JSONObj_item_stores_distance = JSONObj_item_stores.getString("distance");  
	        
	        
	        
	        
	        JOARRAY_item_stores_floors=	JSONObj_item_stores.getJSONArray("floors");
	        list_jobj_item_floors = new ArrayList<Map<String, Object>>(); 
	        if(JOARRAY_item_stores_floors!=null && !"".equals(JOARRAY_item_stores_floors)){
	                    for (int i = 0; i < JOARRAY_item_stores_floors.length(); i++) {     
	        map_item_floors = new HashMap<String, Object>(); // 存放到MAP里面     
	                    	JSONObj_item_stores_floors = JOARRAY_item_stores_floors.getJSONObject(i); // 得到每个对象     
	                   String JSONObj_item_stores_floors_shortDescription = JSONObj_item_stores_floors.getString("shortDescription");  
	                   String JSONObj_item_stores_floors_longDescription = JSONObj_item_stores_floors.getString("longDescription");  
	                   String JSONObj_item_stores_floors_id = JSONObj_item_stores_floors.getString("id");  
	                   String JSONObj_item_stores_floors_imageUrl = JSONObj_item_stores_floors.getString("imageUrl"); 
	                   String JSONObj_item_stores_floors_label = JSONObj_item_stores_floors.getString("label");
	                           
	                   
	                   map_item_floors.put("JSONObj_item_stores_floors_shortDescription",JSONObj_item_stores_floors_shortDescription);   
	         	    	map_item_floors.put("JSONObj_item_stores_floors_longDescription",JSONObj_item_stores_floors_longDescription);    
	         	    	map_item_floors.put("JSONObj_item_stores_floors_id",JSONObj_item_stores_floors_id);    
	         	    	map_item_floors.put("JSONObj_item_stores_floors_imageUrl",JSONObj_item_stores_floors_imageUrl);    
	         	    	map_item_floors.put("JSONObj_item_stores_floors_label",JSONObj_item_stores_floors_label); 
	        	        
                    	list_jobj_item_floors.add(map_item_floors);   
	                    }
	        			}
	        
	        String JSONObj_item_stores_id = JSONObj_item_stores.getString("id");  
	        String JSONObj_item_stores_imageURL = JSONObj_item_stores.getString("imageUrl");  
	        String JSONObj_item_stores_lastCheckinHere = JSONObj_item_stores.getString("lastCheckinHere"); 
	        String JSONObj_item_stores_lat = JSONObj_item_stores.getString("lat");
	        String JSONObj_item_stores_lng = JSONObj_item_stores.getString("lng");
	        String JSONObj_item_stores_name = JSONObj_item_stores.getString("name");
	        String JSONObj_item_stores_phoneNumber = JSONObj_item_stores.getString("phoneNumber");
	        String JSONObj_item_stores_postalCode = JSONObj_item_stores.getString("postalCode");
	        String JSONObj_item_stores_state = JSONObj_item_stores.getString("state");
	        String JSONObj_item_stores_storeNo = JSONObj_item_stores.getString("storeNo");
	        
	        
        JSONObj_item_usedMarker = JSONOBJ_response_checkin_brand.getJSONObject("usedMarker"); // 得到每个对象     
			
		    String JSONObj_item_usedMarker_activityType = JSONObj_item_usedMarker.getString("activityType");
	        String JSONObj_item_usedMarker_animationsURL = JSONObj_item_usedMarker.getString("animationsUrl");     
	        String JSONObj_item_usedMarker_dealsEarned = JSONObj_item_usedMarker.getString("dealsEarned");     
	        String JSONObj_item_usedMarker_distance = JSONObj_item_usedMarker.getString("distance");  
	        String JSONObj_item_usedMarker_id = JSONObj_item_usedMarker.getString("id");  
	        String JSONObj_item_usedMarker_gameId = JSONObj_item_usedMarker.getString("game");  
	        String JSONObj_item_usedMarker_imageURL = JSONObj_item_usedMarker.getString("imageUrl"); 
	        String JSONObj_item_usedMarker_inbiEarned = JSONObj_item_usedMarker.getString("inbiEarned");

	          	map_item.put("JSONObj_brands_activityCount",JSONObj_brands_activityCount); 
	          	map_item.put("JSONObj_brands_barcodeStandard",JSONObj_brands_barcodeStandard); 
	          	
		      	map_item.put("JSONObj_brands_checkins",JSONObj_brands_checkins);    
		      	map_item.put("JSONObj_brands_cnName",JSONObj_brands_cnName);    
		      	map_item.put("JSONObj_brands_description",JSONObj_brands_description);    
	  	    	map_item.put("JSONObj_brands_enName",JSONObj_brands_enName);
	  	    	map_item.put("JSONObj_brands_id",JSONObj_brands_id); 
	  	    	map_item.put("JSONObj_brands_imagesURL",JSONObj_brands_imagesURL); 
    	    	map_item.put("JSONObj_brands_isCheckinToday",JSONObj_brands_isCheckinToday);   
  	      		map_item.put("JSONObj_brands_lastCheckinHere",JSONObj_brands_lastCheckinHere);  
  	      		map_item.put("JSONObj_brands_weiboAccountName",JSONObj_brands_weiboAccountName);  
  	      	
		        map_item.put("JSONObj_item_stores_address",JSONObj_item_stores_address);   
		      	map_item.put("JSONObj_item_stores_area",JSONObj_item_stores_area);    
		      	map_item.put("JSONObj_item_stores_city",JSONObj_item_stores_city);    
		      	map_item.put("JSONObj_item_stores_distance",JSONObj_item_stores_distance);    
	  	    	map_item.put("JSONObj_item_stores_id",JSONObj_item_stores_id);    
	  	    	map_item.put("JSONObj_item_stores_imageURL",JSONObj_item_stores_imageURL); 
	  	    	map_item.put("JSONObj_item_stores_lastCheckinHere",JSONObj_item_stores_lastCheckinHere); 
      	    	map_item.put("JSONObj_item_stores_lat",JSONObj_item_stores_lat);   
    	      	map_item.put("JSONObj_item_stores_lng",JSONObj_item_stores_lng);    
    	      	map_item.put("JSONObj_item_stores_name",JSONObj_item_stores_name);    
    	      	map_item.put("JSONObj_item_stores_phoneNumber",JSONObj_item_stores_phoneNumber);    
      	    	map_item.put("JSONObj_item_stores_postalCode",JSONObj_item_stores_postalCode);    
      	    	map_item.put("JSONObj_item_stores_state",JSONObj_item_stores_state); 
      	    	map_item.put("JSONObj_item_stores_storeNo",JSONObj_item_stores_storeNo); 
      	    	
      	    	map_item.put("JSONObj_item_usedMarker_activityType",JSONObj_item_usedMarker_activityType);   
	      	  	map_item.put("JSONObj_item_usedMarker_animationsURL",JSONObj_item_usedMarker_animationsURL);    
	      	  	map_item.put("JSONObj_item_usedMarker_dealsEarned",JSONObj_item_usedMarker_dealsEarned);    
	      	  	map_item.put("JSONObj_item_usedMarker_distance",JSONObj_item_usedMarker_distance);    
	      	  	map_item.put("JSONObj_item_usedMarker_id",JSONObj_item_usedMarker_id);    
	      	  	map_item.put("JSONObj_item_usedMarker_gameId",JSONObj_item_usedMarker_gameId);    
	      	  	map_item.put("JSONObj_item_usedMarker_imageURL",JSONObj_item_usedMarker_imageURL); 
	      	  	map_item.put("JSONObj_item_usedMarker_inbiEarned",JSONObj_item_usedMarker_inbiEarned); 

    	    	list_jobj_item.add(map_item); 
            
           // Log.d("Rock", JSONObj_brands_enName+":222值");
          //  Log.d("Rock", list_jobj_item+":111值");
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
