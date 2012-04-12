package com.walkin.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.walkin.common.BaseAuthenicationHttpClient;
import com.walkin.json.JSONArray;
import com.walkin.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DealsMenuService {
    private static DealsMenuService instance = null;
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
	  	
    public static final String JSONObj_item_brandCnName = "JSONObj_item_brandCnName";
    public static final String JSONObj_item_brandEnName = "JSONObj_item_brandEnName"; 
    public static final String JSONObj_item_brandId = "JSONObj_item_brandId";
    public static final String JSONObj_item_detailDescription = "JSONObj_item_detailDescription";
    public static final String JSONObj_item_discount = "JSONObj_item_discount";
    public static final String JSONObj_item_distance = "JSONObj_item_distance";
    public static final String JSONObj_item_giftDescription = "JSONObj_item_giftDescription";
    public static final String JSONObj_item_id = "JSONObj_item_id";
    public static final String JSONObj_item_imageUrl = "JSONObj_item_imageUrl"; 
    public static final String JSONObj_item_isEarned = "JSONObj_item_isEarned";
    public static final String JSONObj_item_isNew = "JSONObj_item_isNew";
    public static final String JSONObj_item_legalText = "JSONObj_item_legalText";
    public static final String JSONObj_item_liked = "JSONObj_item_liked";
    public static final String JSONObj_item_rebate = "JSONObj_item_rebate";
    public static final String JSONObj_item_redemptionCode = "JSONObj_item_redemptionCode";
    public static final String JSONObj_item_redemptionType = "JSONObj_item_redemptionType";
    public static final String JSONObj_item_reference = "JSONObj_item_reference";
    public static final String JSONObj_item_retailPrice = "JSONObj_item_retailPrice";
    public static final String JSONObj_item_shortDescription = "JSONObj_item_shortDescription";
    public static final String JSONObj_item_used = "JSONObj_item_used";
    public static final String JSONObj_item_validUntil = "JSONObj_item_validUntil";
    
    public static String JSONObj_item_groups_groupName = "JSONObj_item_groups_groupName";
    public static String JSONObj_item_groups_items = "JSONObj_item_groups_items";
    
    
    private String retrieveUrl;
    private boolean successed = false;
	private String message;               
	private String code;            
//	private String type; 
	private String unreadCount;

    private  Map<String, Object> map_item = null;     
    private  List<Map<String, Object>> list_jobj_item = new ArrayList<Map<String, Object>>(); 
    private  List<Map<String, Object>>  list_item_groups = new ArrayList<Map<String, Object>>(); 
    private  Map<String, Object>   map_item_groups  = null; 

    
    
	/**
	 * @return the list_item_groups
	 */
	public List<Map<String, Object>> getList_item_groups() {
		return list_item_groups;
	}

	/**
	 * @param listItemGroups the list_item_groups to set
	 */
	public void setList_item_groups(List<Map<String, Object>> listItemGroups) {
		list_item_groups = listItemGroups;
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
    
    private DealsMenuService(){}

    public static DealsMenuService getInstance(){
        if(instance == null){
            instance =  new  DealsMenuService();
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

    public void retrieveDealsInfo(){
        try{
          final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl);
            
//              InputStream is = context.getAssets().open("newdeals");  
//                int size = is.available();  
//                byte[] buffer = new byte[size];  
//                is.read(buffer);  
//                is.close();  
//               String   jsStr = new String(buffer, "utf8");  
//               Log.d("Rock", jsStr+"=2222222222");
            
            
            JSONObject json = null;
            JSONObject JSONOBJ_response = null;
            JSONObject JSONOBJ_meta = null;
            JSONObject JSONOBJ_notifications = null;
            
            JSONArray JOARRAY_deals = null;
            JSONArray JOARRAY_item_groups = null;
            JSONObject JSONObj_item=null;
            JSONObject JSONObj_item_groups = null;
            
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            if(!list_item_groups.isEmpty()){
            	list_item_groups.clear();
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
                     	    String	JSONObj_item_newNotifications_imageURL = JSONObj_item_newNotifications.getString("imageURL");     
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
           
            JOARRAY_deals=	JSONOBJ_response.getJSONArray("deals");
list_jobj_item = new ArrayList<Map<String, Object>>(); 
            for (int i = 0; i < JOARRAY_deals.length(); i++) {     
map_item = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item = JOARRAY_deals.getJSONObject(i); // 得到每个对象     
           String JSONObj_item_brandCnName = JSONObj_item.getString("brandCnName");
           String JSONObj_item_brandEnName = JSONObj_item.getString("brandEnName");     
           String JSONObj_item_brandId = JSONObj_item.getString("brandId");     
           String JSONObj_item_detailDescription = JSONObj_item.getString("detailDescription");  
           String JSONObj_item_discount = JSONObj_item.getString("discount");  
           String JSONObj_item_distance = JSONObj_item.getString("distance");  
           String JSONObj_item_giftDescription = JSONObj_item.getString("giftDescription"); 
           String JSONObj_item_id = JSONObj_item.getString("id");
           String JSONObj_item_imageUrl = JSONObj_item.getString("imageUrl");   
           String JSONObj_item_isEarned = JSONObj_item.getString("isEarned");     
           String JSONObj_item_isNew = JSONObj_item.getString("isNew");     
           String JSONObj_item_legalText = JSONObj_item.getString("legalText");  
           String JSONObj_item_liked = JSONObj_item.getString("liked");  
           String JSONObj_item_rebate = JSONObj_item.getString("rebate");  
           String JSONObj_item_redemptionCode = JSONObj_item.getString("redemptionCode");  
           String JSONObj_item_redemptionType = JSONObj_item.getString("redemptionType");            
           String JSONObj_item_reference = JSONObj_item.getString("reference");
           String JSONObj_item_retailPrice = JSONObj_item.getString("retailPrice");     
           String JSONObj_item_shortDescription = JSONObj_item.getString("shortDescription");   
           String JSONObj_item_used = JSONObj_item.getString("used");    
           String JSONObj_item_validUntil = JSONObj_item.getString("validUntil");  
           
           
           map_item.put("JSONObj_item_brandCnName",JSONObj_item_brandCnName);   
	      	map_item.put("JSONObj_item_brandEnName",JSONObj_item_brandEnName);    
	      	map_item.put("JSONObj_item_brandId",JSONObj_item_brandId);    
	      	map_item.put("JSONObj_item_detailDescription",JSONObj_item_detailDescription);    
	    	map_item.put("JSONObj_item_discount",JSONObj_item_discount);    
	    	map_item.put("JSONObj_item_distance",JSONObj_item_distance); 
	    	map_item.put("JSONObj_item_giftDescription",JSONObj_item_giftDescription); 
	    	
	    	map_item.put("JSONObj_item_id",JSONObj_item_id);   
	 	  	map_item.put("JSONObj_item_imageUrl",JSONObj_item_imageUrl);    
	 	  	map_item.put("JSONObj_item_isEarned",JSONObj_item_isEarned);    
	 	  	map_item.put("JSONObj_item_isNew",JSONObj_item_isNew);    
	 	  	map_item.put("JSONObj_item_legalText",JSONObj_item_legalText);    
	 		map_item.put("JSONObj_item_liked",JSONObj_item_liked); 
	 	  	
	 	  	map_item.put("JSONObj_item_rebate",JSONObj_item_rebate);    
	 	  	map_item.put("JSONObj_item_redemptionCode",JSONObj_item_redemptionCode);    
	 	  	map_item.put("JSONObj_item_redemptionType",JSONObj_item_redemptionType); 
	 	  	map_item.put("JSONObj_item_reference",JSONObj_item_reference); 
	 	  	map_item.put("JSONObj_item_retailPrice",JSONObj_item_retailPrice); 
	 	  	map_item.put("JSONObj_item_shortDescription",JSONObj_item_shortDescription); 
	 		map_item.put("JSONObj_item_used",JSONObj_item_used); 
	 	  	map_item.put("JSONObj_item_validUntil",JSONObj_item_validUntil); 
	    	list_jobj_item.add(map_item); 
	    	  
            }
         
JOARRAY_item_groups = JSONOBJ_response.getJSONArray("groups");
list_item_groups = new ArrayList<Map<String, Object>>(); 
      	    	if(JOARRAY_item_groups!=null && !"".equals(JOARRAY_item_groups)){
      	     for (int j = 0; j < JOARRAY_item_groups.length(); j++) {  
map_item_groups = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item_groups = JOARRAY_item_groups.getJSONObject(j); // 得到每个对象     
      	    String	JSONObj_item_groups_groupName = JSONObj_item_groups.getString("groupName");
      	    String	JSONObj_item_groups_items = JSONObj_item_groups.getString("items");     
  	     
      	  map_item_groups.put("JSONObj_item_groups_groupName",JSONObj_item_groups_groupName);   
      	  map_item_groups.put("JSONObj_item_groups_items",JSONObj_item_groups_items);    
      	  list_item_groups.add(map_item_groups);   
  	    	
//  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
      	    }
      	    }
            
            setSuccessed(true);
            
        }catch (Exception e){}
    }
    
    public void retrieveDealsRate(Map<String, String> params1){
        try{
          final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params1);
            
               Log.d("Rock", jsStr+"=2222222222");
            
            
            JSONObject json = null;
            JSONObject JSONOBJ_response = null;
            JSONObject JSONOBJ_meta = null;
            JSONObject JSONOBJ_notifications = null;
            
            JSONArray JOARRAY_deals = null;
            JSONArray JOARRAY_item_groups = null;
            JSONObject JSONObj_item=null;
            JSONObject JSONObj_item_groups = null;
            
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            if(!list_item_groups.isEmpty()){
            	list_item_groups.clear();
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
                     	    String	JSONObj_item_newNotifications_imageURL = JSONObj_item_newNotifications.getString("imageURL");     
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
           
            JOARRAY_deals=	JSONOBJ_response.getJSONArray("deal");
list_jobj_item = new ArrayList<Map<String, Object>>(); 
            for (int i = 0; i < JOARRAY_deals.length(); i++) {     
map_item = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item = JOARRAY_deals.getJSONObject(i); // 得到每个对象     
           String JSONObj_item_brandCnName = JSONObj_item.getString("brandCnName");
           String JSONObj_item_brandEnName = JSONObj_item.getString("brandEnName");     
           String JSONObj_item_brandId = JSONObj_item.getString("brandId");     
           String JSONObj_item_detailDescription = JSONObj_item.getString("detailDescription");  
           String JSONObj_item_discount = JSONObj_item.getString("discount");  
           String JSONObj_item_distance = JSONObj_item.getString("distance");  
           String JSONObj_item_giftDescription = JSONObj_item.getString("giftDescription"); 
           String JSONObj_item_id = JSONObj_item.getString("id");
           String JSONObj_item_imageUrl = JSONObj_item.getString("imageUrl");    
           String JSONObj_item_isEarned = JSONObj_item.getString("isEarned");  
           String JSONObj_item_isNew = JSONObj_item.getString("isNew");     
           String JSONObj_item_legalText = JSONObj_item.getString("legalText");  
           String JSONObj_item_liked = JSONObj_item.getString("liked");  
           String JSONObj_item_rebate = JSONObj_item.getString("rebate");  
           String JSONObj_item_redemptionCode = JSONObj_item.getString("redemptionCode");  
           String JSONObj_item_redemptionType = JSONObj_item.getString("redemptionType");            
           String JSONObj_item_reference = JSONObj_item.getString("reference");
           String JSONObj_item_retailPrice = JSONObj_item.getString("retailPrice");     
           String JSONObj_item_shortDescription = JSONObj_item.getString("shortDescription");   
           String JSONObj_item_used = JSONObj_item.getString("used");    
           String JSONObj_item_validUntil = JSONObj_item.getString("validUntil");  
           
           
           map_item.put("JSONObj_item_brandCnName",JSONObj_item_brandCnName);   
	      	map_item.put("JSONObj_item_brandEnName",JSONObj_item_brandEnName);    
	      	map_item.put("JSONObj_item_brandId",JSONObj_item_brandId);    
	      	map_item.put("JSONObj_item_detailDescription",JSONObj_item_detailDescription);    
	    	map_item.put("JSONObj_item_discount",JSONObj_item_discount);    
	    	map_item.put("JSONObj_item_distance",JSONObj_item_distance); 
	    	map_item.put("JSONObj_item_giftDescription",JSONObj_item_giftDescription); 
	    	
	    	map_item.put("JSONObj_item_id",JSONObj_item_id);   
	 	  	map_item.put("JSONObj_item_imageUrl",JSONObj_item_imageUrl);    
	 	  	map_item.put("JSONObj_item_isEarned",JSONObj_item_isEarned);    
	 	  	map_item.put("JSONObj_item_isNew",JSONObj_item_isNew);    
	 	  	map_item.put("JSONObj_item_legalText",JSONObj_item_legalText);    
	 		map_item.put("JSONObj_item_liked",JSONObj_item_liked); 
	 	  	
	 	  	map_item.put("JSONObj_item_rebate",JSONObj_item_rebate);    
	 	  	map_item.put("JSONObj_item_redemptionCode",JSONObj_item_redemptionCode);    
	 	  	map_item.put("JSONObj_item_redemptionType",JSONObj_item_redemptionType); 
	 	  	map_item.put("JSONObj_item_reference",JSONObj_item_reference); 
	 	  	map_item.put("JSONObj_item_retailPrice",JSONObj_item_retailPrice); 
	 	  	map_item.put("JSONObj_item_shortDescription",JSONObj_item_shortDescription); 
	 		map_item.put("JSONObj_item_used",JSONObj_item_used); 
	 	  	map_item.put("JSONObj_item_validUntil",JSONObj_item_validUntil); 
	    	list_jobj_item.add(map_item); 
	    	  
            }
         
JOARRAY_item_groups = JSONOBJ_response.getJSONArray("groups");
list_item_groups = new ArrayList<Map<String, Object>>(); 
      	    	if(JOARRAY_item_groups!=null && !"".equals(JOARRAY_item_groups)){
      	     for (int j = 0; j < JOARRAY_item_groups.length(); j++) {  
map_item_groups = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item_groups = JOARRAY_item_groups.getJSONObject(j); // 得到每个对象     
      	    String	JSONObj_item_groups_groupName = JSONObj_item_groups.getString("groupName");
      	    String	JSONObj_item_groups_items = JSONObj_item_groups.getString("items");     
  	     
      	  map_item_groups.put("JSONObj_item_groups_groupName",JSONObj_item_groups_groupName);   
      	  map_item_groups.put("JSONObj_item_groups_items",JSONObj_item_groups_items);    
      	  list_item_groups.add(map_item_groups);   
  	    	
//  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
      	    }
      	    }
            
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
