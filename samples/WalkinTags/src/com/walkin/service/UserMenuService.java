package com.walkin.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.walkin.common.BaseAuthenicationHttpClient;
import com.walkin.common.FileUtil;
import com.walkin.common.FormFile;
import com.walkin.json.JSONArray;
import com.walkin.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserMenuService {
    private static UserMenuService instance = null;
    private SharedPreferences sharepre;
    

    
/*    public static final String USER_LOGIN_META = "meta";
    public static final String USER_LOGIN_TOKEN = "token";
    public static final String USER_LOGIN_META_MESSAGE = "message";
    public static final String USER_LOGIN_META_CODE = "code";
    public static final String USER_LOGIN_META = "meta";
    public static final String USER_LOGIN_TOKEN = "token";
    public static final String USER_LOGIN_META_MESSAGE = "message";
    public static final String USER_LOGIN_META_CODE = "code";*/
   
    public static final String USER_REGISTER_RESPONSE = "response"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String USER_REGISTER_RESPONSE_TOKEN = "token";
    public static final String USER_REGISTER_RESPONSE_ID = "id";
    public static final String USER_REGISTER_META = "meta";
    public static final String USER_REGISTER_META_MESSAGE = "message";
    public static final String USER_REGISTER_META_CODE = "code";
    public static final String USER_REGISTER_NOTIFICATIONS = "notifications";
    
    
    

    
    
//    public static final String USER_REGISTER_NOTIFICATIONS_TYPE = "type";
    public static final String USER_REGISTER_NOTIFICATIONS_UNREADCOUNT = "unreadCount";
    
    private  List<Map<String, Object>>  list_item_newNotifications = new ArrayList<Map<String, Object>>(); 
    private  Map<String, Object>   map_item_newNotifications  = null; 
    
    
    
    public static final String JSONObj_item_newNotifications_id = "JSONObj_item_newNotifications_id"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String JSONObj_item_newNotifications_imageURL = "JSONObj_item_newNotifications_imageURL";
    public static final String JSONObj_item_newNotifications_text = "JSONObj_item_newNotifications_text";
    public static final String JSONObj_item_newNotifications_type = "JSONObj_item_newNotifications_type";
    public static final String JSONObj_item_newNotifications_unread = "JSONObj_item_newNotifications_unread";
     	  
     	  
    public static final String response_user_address = "response_user_address"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String response_user_ageGroup = "response_user_ageGroup";
    public static final String response_user_badgeCount = "response_user_badgeCount";
    public static final String response_user_checkinCount = "response_user_checkinCount";
    public static final String response_user_city = "response_user_city";
    public static final String response_user_email = "response_user_email";
    public static final String response_user_favoritedBrandId = "response_user_favoritedBrandId";
    public static final String response_user_firstName = "response_user_firstName";
    public static final String response_user_gender = "response_user_gender";
    public static final String response_user_imageUrl = "response_user_imageUrl"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String response_user_inbiBalance = "response_user_inbiBalance";
    public static final String response_user_inbiFromStart = "response_user_inbiFromStart";
    public static final String response_user_lastName = "response_user_lastName";
    public static final String response_user_postalCode = "response_user_postalCode";
    public static final String response_user_registeredDate = "response_user_registeredDate";
    public static final String response_user_style = "response_user_style";
    public static final String response_user_userName = "response_user_userName";
    public static final String response_user_weiboAccountId = "response_user_weiboAccountId";
    
    
    
    
    
    private String retrieveUrl;
    private boolean successed = false;
	private String message;               
	private String code;            
	private String token;  
	private String id;  
	private String userName; 
	private String unreadCount;
	private String inbiBalance;
    
    private  Map<String, Object> map_item = null;     
    private  List<Map<String, Object>> list_jobj_item = new ArrayList<Map<String, Object>>(); 
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the inbiBalance
	 */
	public String getInbiBalance() {
		return inbiBalance;
	}

	/**
	 * @param inbiBalance the inbiBalance to set
	 */
	public void setInbiBalance(String inbiBalance) {
		this.inbiBalance = inbiBalance;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
    
    private UserMenuService(){}

    public static UserMenuService getInstance(){
        if(instance == null){
            instance =  new  UserMenuService();
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

    public void retrieveUserUpdateInfo(Map<String,String>params){
        try{
            final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params);
            JSONObject json = null;
            JSONObject jobj_response = null;
            JSONObject jobj_meta = null;
            JSONObject jobj_notifications = null;
            JSONObject jobj_response_user = null;
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            json = new JSONObject(jsStr);
            
            Log.d("Rock", jsStr+":jsStr");
            
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
           /* jobj_notifications = json.getJSONObject(USER_REGISTER_NOTIFICATIONS);
            
            
            JOARRAY_jobj_notifications_newNotifications=	jobj_notifications.getJSONArray("newNotifications");
            
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
                     	    }*/
            
//            unreadCount = jobj_notifications.getString(USER_REGISTER_NOTIFICATIONS_UNREADCOUNT);
            jobj_meta = json.getJSONObject(USER_REGISTER_META);
            message= jobj_meta.getString(USER_REGISTER_META_MESSAGE);
            code = jobj_meta.getString(USER_REGISTER_META_CODE);
            
           	jobj_response = json.getJSONObject(USER_REGISTER_RESPONSE);
           	jobj_response_user =jobj_response.getJSONObject("user");
   	list_jobj_item = new ArrayList<Map<String, Object>>();            	
   	map_item = new HashMap<String, Object>(); // 存放到MAP里面             	  
   		id =jobj_response_user.getString(USER_REGISTER_RESPONSE_ID);
           String	response_user_address =jobj_response_user.getString("address");
           String	response_user_ageGroup =jobj_response_user.getString("ageGroup");
           String	response_user_badgeCount =jobj_response_user.getString("badgeCount");
           String	response_user_checkinCount =jobj_response_user.getString("checkinCount");
           String	response_user_city =jobj_response_user.getString("city");
           String	response_user_email =jobj_response_user.getString("email");
           String	response_user_favoritedBrandId =jobj_response_user.getString("favoritedBrandId");
           String	response_user_firstName =jobj_response_user.getString("firstName");
           String	response_user_gender =jobj_response_user.getString("gender");
           String	response_user_imageUrl =jobj_response_user.getString("imageUrl");
           inbiBalance =jobj_response_user.getString("inbiBalance");
           String	response_user_inbiFromStart =jobj_response_user.getString("inbiFromStart");
           String	response_user_lastName =jobj_response_user.getString("lastName");
           String 	response_user_postalCode =jobj_response_user.getString("postalCode");
           String	response_user_style =jobj_response_user.getString("style");
           userName =jobj_response_user.getString("userName");
           String	response_user_weiboAccountId =jobj_response_user.getString("weiboAccountId");
            	
           map_item.put("response_user_id",id);   
	      	map_item.put("response_user_address",response_user_address);    
	      	map_item.put("response_user_ageGroup",response_user_ageGroup);    
	      	map_item.put("response_user_badgeCount",response_user_badgeCount);    
 	    	map_item.put("response_user_checkinCount",response_user_checkinCount);    
 	    	map_item.put("response_user_city",response_user_city); 
 	    	map_item.put("response_user_email",response_user_email); 
 	    	map_item.put("response_user_favoritedBrandId",response_user_favoritedBrandId); 
 	    	map_item.put("response_user_firstName",response_user_firstName);   
     	  	map_item.put("response_user_gender",response_user_gender);    
     	  	map_item.put("response_user_imageUrl",response_user_imageUrl);    
     	  	map_item.put("response_user_inbiBalance",inbiBalance);    
     	  	map_item.put("response_user_inbiFromStart",response_user_inbiFromStart);    
     	  	map_item.put("response_user_lastName",response_user_lastName);    
     	  	map_item.put("response_user_postalCode",response_user_postalCode); 
     	  	map_item.put("response_user_style",response_user_style); 
     		map_item.put("response_user_userName",userName); 
	    	map_item.put("response_user_weiboAccountId",response_user_weiboAccountId); 
	    	list_jobj_item.add(map_item); 	
	    	Log.d("Rock", list_jobj_item+":list_jobj_item");
            setSuccessed(true);
            
        }catch (final Exception e){
        	
        	
        }
    }
 
    public void retrieveUserUpload(Map<String,String>params, FormFile[] files){
        try{
            final String jsStr = FileUtil.post(retrieveUrl, params, files);
            Log.d("Rock", retrieveUrl+"111111111");
            JSONObject json = null;
            JSONObject jobj_meta = null;
            JSONObject jobj_notifications = null;
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            json = new JSONObject(jsStr);
            Log.d("Rock", jsStr+":jsStr");
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            jobj_notifications = json.getJSONObject(USER_REGISTER_NOTIFICATIONS);
            
            
           
            JOARRAY_jobj_notifications_newNotifications=	jobj_notifications.getJSONArray("newNotifications");
            
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
              	    	
//              	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                  	    }
                  	    }
            
            
            
            
            
            
            
            
            
            unreadCount = jobj_notifications.getString(USER_REGISTER_NOTIFICATIONS_UNREADCOUNT);
            jobj_meta = json.getJSONObject(USER_REGISTER_META);
            message= jobj_meta.getString(USER_REGISTER_META_MESSAGE);
            code = jobj_meta.getString(USER_REGISTER_META_CODE);
            
           
            setSuccessed(true);
            
        }catch (final Exception e){}
    }   
     public void retrieveUserLoginInfo(Map<String,String>params){
         try{
             final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params);
             JSONObject json = null;
             JSONObject jobj_response = null;
             JSONObject jobj_meta = null;
             JSONObject jobj_notifications = null;
             JSONObject JSONObj_item_newNotifications = null;
             JSONArray JOARRAY_jobj_notifications_newNotifications = null;
             json = new JSONObject(jsStr);
             Log.d("Rock", jsStr+"：retrieveUserLoginInfo");
             
             if ("null".equals(json.getString(USER_REGISTER_NOTIFICATIONS))) {
				
			}else{
				
			
             
             jobj_notifications = json.getJSONObject(USER_REGISTER_NOTIFICATIONS);
             
             JOARRAY_jobj_notifications_newNotifications=	jobj_notifications.getJSONArray("newNotifications");
             
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
                  	    	
//                  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                      	    }
                      	    }
             unreadCount = jobj_notifications.getString(USER_REGISTER_NOTIFICATIONS_UNREADCOUNT);
			}
             jobj_meta = json.getJSONObject(USER_REGISTER_META);
             message= jobj_meta.getString(USER_REGISTER_META_MESSAGE);
             code = jobj_meta.getString(USER_REGISTER_META_CODE);
             if("200".equals(code)){ 
            	jobj_response = json.getJSONObject(USER_REGISTER_RESPONSE);
            	id =jobj_response.getString(USER_REGISTER_RESPONSE_ID);
            	token =jobj_response.getString(USER_REGISTER_RESPONSE_TOKEN);
            	regParam(USER_REGISTER_RESPONSE_ID,id);
            	regParam(USER_REGISTER_RESPONSE_TOKEN,token);
  			}
             setSuccessed(true);
             
         }catch (final Exception e){
        		Log.d("Rock", e+":e");
         }
     }
     public void retrieveUserRegisterInfo(Map<String,String>params){
         try{
             final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params);
             JSONObject json = null;
             JSONObject jobj_response = null;
             JSONObject jobj_meta = null;
             JSONObject jobj_notifications = null;
             JSONObject JSONObj_item_newNotifications = null;
             JSONArray JOARRAY_jobj_notifications_newNotifications = null;
             Log.d("Rock", jsStr+":jsStr");
             json = new JSONObject(jsStr);
             
             /*jobj_notifications = json.getJSONObject(USER_REGISTER_NOTIFICATIONS);
             
             
  JOARRAY_jobj_notifications_newNotifications=	jobj_notifications.getJSONArray("newNotifications");
             
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
                  	    	
//                  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                      	    }
                      	    }
             unreadCount = jobj_notifications.getString(USER_REGISTER_NOTIFICATIONS_UNREADCOUNT);*/
             jobj_meta = json.getJSONObject(USER_REGISTER_META);
             message= jobj_meta.getString(USER_REGISTER_META_MESSAGE);
             code = jobj_meta.getString(USER_REGISTER_META_CODE);
             if("200".equals(code)){ 
            	jobj_response = json.getJSONObject(USER_REGISTER_RESPONSE);
            	id =jobj_response.getString(USER_REGISTER_RESPONSE_ID);
            	token =jobj_response.getString(USER_REGISTER_RESPONSE_TOKEN);
            	regParam(USER_REGISTER_RESPONSE_ID,id);
            	regParam(USER_REGISTER_RESPONSE_TOKEN,token);
  			}
             setSuccessed(true);
             
         }catch (final Exception e){
        	 Log.d("Rock", e+":e");
        	 
        	 
         }
     }
     
     public void retrieveUserQueryInfo(){
         try{
             final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl);
             JSONObject json = null;
             JSONObject jobj_response = null;
             JSONObject jobj_meta = null;
             JSONObject jobj_notifications = null;
             JSONObject jobj_response_user = null;
             JSONObject JSONObj_item_newNotifications = null;
             JSONArray JOARRAY_jobj_notifications_newNotifications = null;
             json = new JSONObject(jsStr);
             if(!list_jobj_item.isEmpty()){
             	list_jobj_item.clear();
             }
             jobj_notifications = json.getJSONObject(USER_REGISTER_NOTIFICATIONS);
             
  JOARRAY_jobj_notifications_newNotifications=	jobj_notifications.getJSONArray("newNotifications");
             
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
                  	    	
//                  	    	Log.d("Rock", list_item_stores.size()+":list_item_stores.size()的值");
                      	    }
                      	    }
             
             unreadCount = jobj_notifications.getString(USER_REGISTER_NOTIFICATIONS_UNREADCOUNT);
             jobj_meta = json.getJSONObject(USER_REGISTER_META);
             message= jobj_meta.getString(USER_REGISTER_META_MESSAGE);
             code = jobj_meta.getString(USER_REGISTER_META_CODE);
             
            	jobj_response = json.getJSONObject(USER_REGISTER_RESPONSE);
            	jobj_response_user =jobj_response.getJSONObject("user");
    	list_jobj_item = new ArrayList<Map<String, Object>>();            	
    	map_item = new HashMap<String, Object>(); // 存放到MAP里面             	  
    		id =jobj_response_user.getString(USER_REGISTER_RESPONSE_ID);
            String	response_user_address =jobj_response_user.getString("address");
            String	response_user_ageGroup =jobj_response_user.getString("ageGroup");
            String	response_user_badgeCount =jobj_response_user.getString("badgeCount");
            String	response_user_checkinCount =jobj_response_user.getString("checkinCount");
            String	response_user_city =jobj_response_user.getString("city");
            String	response_user_email =jobj_response_user.getString("email");
            String	response_user_favoritedBrandId =jobj_response_user.getString("favoritedBrandId");
            String	response_user_firstName =jobj_response_user.getString("firstName");
            String	response_user_gender =jobj_response_user.getString("gender");
            String	response_user_imageUrl =jobj_response_user.getString("imageUrl");
            inbiBalance =jobj_response_user.getString("inbiBalance");
            String	response_user_inbiFromStart =jobj_response_user.getString("inbiFromStart");
            String	response_user_lastName =jobj_response_user.getString("lastName");
            String 	response_user_postalCode =jobj_response_user.getString("postalCode");
            String 	response_user_registeredDate =jobj_response_user.getString("registeredDate");
            String	response_user_style =jobj_response_user.getString("style");
            userName =jobj_response_user.getString("userName");
            String	response_user_weiboAccountId =jobj_response_user.getString("weiboAccountId");
             	
            map_item.put("response_user_id",id);   
	      	map_item.put("response_user_address",response_user_address);    
	      	map_item.put("response_user_ageGroup",response_user_ageGroup);    
	      	map_item.put("response_user_badgeCount",response_user_badgeCount);    
  	    	map_item.put("response_user_checkinCount",response_user_checkinCount);    
  	    	map_item.put("response_user_city",response_user_city); 
  	    	map_item.put("response_user_email",response_user_email); 
  	    	map_item.put("response_user_favoritedBrandId",response_user_favoritedBrandId); 
  	    	map_item.put("response_user_firstName",response_user_firstName);   
      	  	map_item.put("response_user_gender",response_user_gender);    
      	  	map_item.put("response_user_imageUrl",response_user_imageUrl);    
      	  	map_item.put("response_user_inbiBalance",inbiBalance);    
      	  	map_item.put("response_user_inbiFromStart",response_user_inbiFromStart);    
      	  	map_item.put("response_user_lastName",response_user_lastName);    
      	  	map_item.put("response_user_postalCode",response_user_postalCode); 
      		map_item.put("response_user_registeredDate",response_user_registeredDate); 
      	  
      	  	map_item.put("response_user_style",response_user_style); 
      		map_item.put("response_user_userName",userName); 
	    	map_item.put("response_user_weiboAccountId",response_user_weiboAccountId); 
	    	list_jobj_item.add(map_item); 	
	    	Log.d("Rock", list_jobj_item+":list_jobj_item");
             setSuccessed(true);
             
         }catch (final Exception e){
        	 
         }
     }
     
     public void retrieveEventsInfoParams(Map<String, String> params1){
         try{
            final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl,params1);
             
             Log.d("Rock", jsStr+":值");
             setSuccessed(true);
             
         }catch (Exception e){
         	Log.d("Rock", e+":e");
         	
         }
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
