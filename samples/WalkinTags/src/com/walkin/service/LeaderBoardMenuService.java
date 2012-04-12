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

public class LeaderBoardMenuService {
    private static LeaderBoardMenuService instance = null;
    private SharedPreferences sharepre;
    
   
    public static final String RESPONSE = "response"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String META = "meta";
    public static final String META_MESSAGE = "message";
    public static final String META_CODE = "code";
    public static final String NOTIFICATIONS = "notifications";
    public static final String NOTIFICATIONS_UNREADCOUNT = "unreadCount";
    private  List<Map<String, Object>>  list_item_newNotifications = new ArrayList<Map<String, Object>>(); 
    private  Map<String, Object>   map_item_newNotifications  = null; 
    
    
    
    public static final String JSONObj_item_newNotifications_id = "JSONObj_item_newNotifications_id"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String JSONObj_item_newNotifications_imageURL = "JSONObj_item_newNotifications_imageURL";
    public static final String JSONObj_item_newNotifications_text = "JSONObj_item_newNotifications_text";
    public static final String JSONObj_item_newNotifications_type = "JSONObj_item_newNotifications_type";
    public static final String JSONObj_item_newNotifications_unread = "JSONObj_item_newNotifications_unread";
    
    
    
    public static final String JSONObj_item_scores_totalCheckins = "JSONObj_item_scores_totalCheckins";
    public static final String JSONObj_item_scores_totalCheckinsPercentile = "JSONObj_item_scores_totalCheckinsPercentile"; 
    public static final String JSONObj_item_scores_totalInBiEarned = "JSONObj_item_scores_totalInBiEarned";
    public static final String JSONObj_item_scores_totalInBiEarnedPercentile = "JSONObj_item_scores_totalInBiEarnedPercentile";
    public static final String JSONObj_item_user_gender = "JSONObj_item_user_gender";
    public static final String JSONObj_item_user_homeCity = "JSONObj_item_user_homeCity";
    public static final String JSONObj_item_user_id = "JSONObj_item_user_id";
    public static final String JSONObj_item_user_imageUrl = "JSONObj_item_user_imageUrl";
    public static final String JSONObj_item_user_userName = "JSONObj_item_user_userName"; 
    
    
    private String retrieveUrl;
    private boolean successed = false;
	private String message;               
	private String code;            
	private String unreadCount;
	private String timeFrame; 
	private String totalCheckins;               
	private String totalCheckinsPercentile;            
	private String totalInBiEarned;
	private String totalInBiEarnedPercentile; 
	
	
	
    private  Map<String, Object> map_item = null;     
    private  List<Map<String, Object>> list_jobj_item = new ArrayList<Map<String, Object>>(); 
	
    

	/**
	 * @return the timeFrame
	 */
	public String getTimeFrame() {
		return timeFrame;
	}

	/**
	 * @param timeFrame the timeFrame to set
	 */
	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	/**
	 * @return the totalCheckins
	 */
	public String getTotalCheckins() {
		return totalCheckins;
	}

	/**
	 * @param totalCheckins the totalCheckins to set
	 */
	public void setTotalCheckins(String totalCheckins) {
		this.totalCheckins = totalCheckins;
	}

	/**
	 * @return the totalCheckinsPercentile
	 */
	public String getTotalCheckinsPercentile() {
		return totalCheckinsPercentile;
	}

	/**
	 * @param totalCheckinsPercentile the totalCheckinsPercentile to set
	 */
	public void setTotalCheckinsPercentile(String totalCheckinsPercentile) {
		this.totalCheckinsPercentile = totalCheckinsPercentile;
	}

	/**
	 * @return the totalInBiEarned
	 */
	public String getTotalInBiEarned() {
		return totalInBiEarned;
	}

	/**
	 * @param totalInBiEarned the totalInBiEarned to set
	 */
	public void setTotalInBiEarned(String totalInBiEarned) {
		this.totalInBiEarned = totalInBiEarned;
	}

	/**
	 * @return the totalInBiEarnedPercentile
	 */
	public String getTotalInBiEarnedPercentile() {
		return totalInBiEarnedPercentile;
	}

	/**
	 * @param totalInBiEarnedPercentile the totalInBiEarnedPercentile to set
	 */
	public void setTotalInBiEarnedPercentile(String totalInBiEarnedPercentile) {
		this.totalInBiEarnedPercentile = totalInBiEarnedPercentile;
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
    
    private LeaderBoardMenuService(){}

    public static LeaderBoardMenuService getInstance(){
        if(instance == null){
            instance =  new  LeaderBoardMenuService();
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

    public void retrieveLeaderBoardInfo(){
        try{
           final String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl);
            
//              InputStream is = context.getAssets().open("newbrands");  
//                int size = is.available();  
//                byte[] buffer = new byte[size];  
//                is.read(buffer);  
//                is.close();  
//                String   jsStr = new String(buffer, "utf8");  
//                Log.d("Rock", jsStr+"=111111111111111");
       
            
            
            JSONObject json = null;
            JSONObject JSONOBJ_response = null;
            JSONObject JSONOBJ_meta = null;
            JSONObject JSONOBJ_notifications = null;
            
            JSONArray JOARRAY_leaderboard = null;
            JSONObject JSONObj_item_user= null;
            JSONObject JSONOBJ_response_userScore= null;
            
            JSONObject JSONObj_item=null;
            JSONObject JSONObj_item_scores = null;
            JSONObject JSONObj_item_newNotifications = null;
            JSONArray JOARRAY_jobj_notifications_newNotifications = null;
            json = new JSONObject(jsStr);
            if(!list_jobj_item.isEmpty()){
            	list_jobj_item.clear();
            }
            JSONOBJ_notifications = json.getJSONObject(NOTIFICATIONS);
            
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
            
//            type= JSONOBJ_notifications.getString(NOTIFICATIONS_TYPE);
            unreadCount = JSONOBJ_notifications.getString(NOTIFICATIONS_UNREADCOUNT);
            
            JSONOBJ_meta = json.getJSONObject(META);
            message= JSONOBJ_meta.getString(META_MESSAGE);
            code = JSONOBJ_meta.getString(META_CODE);
             
            JSONOBJ_response = json.getJSONObject(RESPONSE);
           
       timeFrame= JSONOBJ_response.getString("timeFrame");
           JSONOBJ_response_userScore= JSONOBJ_response.getJSONObject("userScore");
         totalCheckins= JSONOBJ_response_userScore.getString("totalCheckins");
         totalCheckinsPercentile= JSONOBJ_response_userScore.getString("totalCheckinsPercentile");
          totalInBiEarned= JSONOBJ_response_userScore.getString("totalInBiEarned");
         totalInBiEarnedPercentile= JSONOBJ_response_userScore.getString("totalInBiEarnedPercentile");
         Log.d("Rock", totalInBiEarnedPercentile+":"+totalInBiEarned+":"+totalCheckinsPercentile+":"+totalCheckins+":"+timeFrame+":");
          
          
            JOARRAY_leaderboard=JSONOBJ_response.getJSONArray("leaderboard");
list_jobj_item = new ArrayList<Map<String, Object>>(); 
            for (int i = 0; i < JOARRAY_leaderboard.length(); i++) {     
map_item = new HashMap<String, Object>(); // 存放到MAP里面     
JSONObj_item = JOARRAY_leaderboard.getJSONObject(i); // 得到每个对象     





		 JSONObj_item_scores = JSONObj_item.getJSONObject("scores");
		   String JSONObj_item_scores_totalCheckins= JSONObj_item_scores.getString("totalCheckins");
		   String JSONObj_item_scores_totalCheckinsPercentile= JSONObj_item_scores.getString("totalCheckinsPercentile");
           String JSONObj_item_scores_totalInBiEarned = JSONObj_item_scores.getString("totalInBiEarned");
           String JSONObj_item_scores_totalInBiEarnedPercentile = JSONObj_item_scores.getString("totalInBiEarnedPercentile");
           
           JSONObj_item_user = JSONObj_item.getJSONObject("user");
           
           
           String JSONObj_item_user_gender = JSONObj_item_user.getString("gender");     
           String JSONObj_item_user_homeCity = JSONObj_item_user.getString("homeCity");     
           String JSONObj_item_user_id = JSONObj_item_user.getString("id");  
           String JSONObj_item_user_imageUrl = JSONObj_item_user.getString("imageUrl");  
           String JSONObj_item_user_userName = JSONObj_item_user.getString("userName");  
          
         
      	    	map_item.put("JSONObj_item_scores_totalCheckins",JSONObj_item_scores_totalCheckins);   
      	    	map_item.put("JSONObj_item_scores_totalCheckinsPercentile",JSONObj_item_scores_totalCheckinsPercentile);   
    	      	map_item.put("JSONObj_item_scores_totalInBiEarned",JSONObj_item_scores_totalInBiEarned);    
    	    	map_item.put("JSONObj_item_scores_totalInBiEarnedPercentile",JSONObj_item_scores_totalInBiEarnedPercentile); 
    	      	map_item.put("JSONObj_item_user_gender",JSONObj_item_user_gender);    
    	      	map_item.put("JSONObj_item_user_homeCity",JSONObj_item_user_homeCity);    
      	    	map_item.put("JSONObj_item_user_id",JSONObj_item_user_id);    
      	    	map_item.put("JSONObj_item_user_imageUrl",JSONObj_item_user_imageUrl); 
      	    	map_item.put("JSONObj_item_user_userName",JSONObj_item_user_userName); 
    	    	list_jobj_item.add(map_item); 
	      }
            
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
