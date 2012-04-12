package com.walkin.service;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;



import com.walkin.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DefaultsMenuService {
    private static DefaultsMenuService instance = null;
    private SharedPreferences sharepre;
    

    
   
    public static final String CONFIG_BRANDS = "brands"; //LOGIN 与 REGISTER 公用一个json解析
    public static final String CONFIG_BRANDS_brandWhiteLogo = "brandWhiteLogo";
    public static final String CONFIG_BRANDS_brandBlackLogo = "brandBlackLogo";
    public static final String CONFIG_BRANDS_brandColorLogo = "brandColorLogo"; 
    public static final String CONFIG_BRANDS_brandBG = "brandBG";
    public static final String CONFIG_BRANDS_brandDetailImage = "brandDetailImage";
    
    public static final String CONFIG_MESSAGES= "messages";
    public static final String CONFIG_MESSAGES_UPGRADEVERSION = "upgradeVersion";
    public static final String CONFIG_MESSAGES_INTRODUCTIONUPGRADE = "introductionUpgrade";
    public static final String CONFIG_MESSAGES_DOWNCLIENTURL = "downClientUrl";
    public static final String CONFIG_MESSAGES_EMAILMESSAGE= "emailMessage";
    public static final String CONFIG_MESSAGES_SMSMESSAGE = "smsMessage";
    public static final String CONFIG_MESSAGES_WEIBOMESSAGE = "weiboMessage";
    public static final String CONFIG_MESSAGES_CLEARCACHEDATE= "clearCacheDate"; 
    public static final String CONFIG_MESSAGES_CLEARCACHETYPE = "clearCacheType"; 
    
    public static final String CONFIG_DEFAULTS = "defaults";
    public static final String CONFIG_DEFAULTS_STATICASSETSURL = "staticAssetsUrl";
    public static final String CONFIG_DEFAULTS_BRANDURL = "brandURL";
    public static final String CONFIG_DEFAULTS_UIURL = "uiURL";
    private String retrieveUrl;
    private boolean successed = false;
    
	private String upgradeVersion;               
	private String introductionUpgrade;            
	private String downClientUrl;  
	private String emailMessage;               
	private String smsMessage;            
	private String weiboMessage; 
	private String clearCacheDate;  
	private String clearCacheType; 

	
	private String staticAssetsUrl; 
	private String brandURL;
	private String uiURL;
	
	private String brandWhiteLogo; 
	private String brandBlackLogo;
	private String brandColorLogo;
	private String brandBG; 
	private String brandDetailImage;
	  
	
	
	
	

	
	
	

	/**
	 * @return the upgradeVersion
	 */
	public String getUpgradeVersion() {
		return upgradeVersion;
	}

	/**
	 * @param upgradeVersion the upgradeVersion to set
	 */
	public void setUpgradeVersion(String upgradeVersion) {
		this.upgradeVersion = upgradeVersion;
	}

	/**
	 * @return the introductionUpgrade
	 */
	public String getIntroductionUpgrade() {
		return introductionUpgrade;
	}

	/**
	 * @param introductionUpgrade the introductionUpgrade to set
	 */
	public void setIntroductionUpgrade(String introductionUpgrade) {
		this.introductionUpgrade = introductionUpgrade;
	}

	/**
	 * @return the downClientUrl
	 */
	public String getDownClientUrl() {
		return downClientUrl;
	}

	/**
	 * @param downClientUrl the downClientUrl to set
	 */
	public void setDownClientUrl(String downClientUrl) {
		this.downClientUrl = downClientUrl;
	}

	/**
	 * @return the emailMessage
	 */
	public String getEmailMessage() {
		return emailMessage;
	}

	/**
	 * @param emailMessage the emailMessage to set
	 */
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	/**
	 * @return the smsMessage
	 */
	public String getSmsMessage() {
		return smsMessage;
	}

	/**
	 * @param smsMessage the smsMessage to set
	 */
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	/**
	 * @return the weiboMessage
	 */
	public String getWeiboMessage() {
		return weiboMessage;
	}

	/**
	 * @param weiboMessage the weiboMessage to set
	 */
	public void setWeiboMessage(String weiboMessage) {
		this.weiboMessage = weiboMessage;
	}

	/**
	 * @return the clearCacheDate
	 */
	public String getClearCacheDate() {
		return clearCacheDate;
	}

	/**
	 * @param clearCacheDate the clearCacheDate to set
	 */
	public void setClearCacheDate(String clearCacheDate) {
		this.clearCacheDate = clearCacheDate;
	}

	/**
	 * @return the clearCacheType
	 */
	public String getClearCacheType() {
		return clearCacheType;
	}

	/**
	 * @param clearCacheType the clearCacheType to set
	 */
	public void setClearCacheType(String clearCacheType) {
		this.clearCacheType = clearCacheType;
	}

	/**
	 * @return the staticAssetsUrl
	 */
	public String getStaticAssetsUrl() {
		return staticAssetsUrl;
	}

	/**
	 * @param staticAssetsUrl the staticAssetsUrl to set
	 */
	public void setStaticAssetsUrl(String staticAssetsUrl) {
		this.staticAssetsUrl = staticAssetsUrl;
	}

	/**
	 * @return the brandURL
	 */
	public String getBrandURL() {
		return brandURL;
	}

	/**
	 * @param brandURL the brandURL to set
	 */
	public void setBrandURL(String brandURL) {
		this.brandURL = brandURL;
	}

	/**
	 * @return the uiURL
	 */
	public String getUiURL() {
		return uiURL;
	}

	/**
	 * @param uiURL the uiURL to set
	 */
	public void setUiURL(String uiURL) {
		this.uiURL = uiURL;
	}

	/**
	 * @return the brandWhiteLogo
	 */
	public String getBrandWhiteLogo() {
		return brandWhiteLogo;
	}

	/**
	 * @param brandWhiteLogo the brandWhiteLogo to set
	 */
	public void setBrandWhiteLogo(String brandWhiteLogo) {
		this.brandWhiteLogo = brandWhiteLogo;
	}

	/**
	 * @return the brandBlackLogo
	 */
	public String getBrandBlackLogo() {
		return brandBlackLogo;
	}

	/**
	 * @param brandBlackLogo the brandBlackLogo to set
	 */
	public void setBrandBlackLogo(String brandBlackLogo) {
		this.brandBlackLogo = brandBlackLogo;
	}

	/**
	 * @return the brandColorLogo
	 */
	public String getBrandColorLogo() {
		return brandColorLogo;
	}

	/**
	 * @param brandColorLogo the brandColorLogo to set
	 */
	public void setBrandColorLogo(String brandColorLogo) {
		this.brandColorLogo = brandColorLogo;
	}

	/**
	 * @return the brandBG
	 */
	public String getBrandBG() {
		return brandBG;
	}

	/**
	 * @param brandBG the brandBG to set
	 */
	public void setBrandBG(String brandBG) {
		this.brandBG = brandBG;
	}

	/**
	 * @return the brandDetailImage
	 */
	public String getBrandDetailImage() {
		return brandDetailImage;
	}

	/**
	 * @param brandDetailImage the brandDetailImage to set
	 */
	public void setBrandDetailImage(String brandDetailImage) {
		this.brandDetailImage = brandDetailImage;
	}
	private Context context;
    public void setActivity(Activity activity) {
    	context = activity;
        sharepre = PreferenceManager.getDefaultSharedPreferences(activity);
    }
    
    private DefaultsMenuService(){}

    public static DefaultsMenuService getInstance(){
        if(instance == null){
            instance =  new  DefaultsMenuService();
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

    
     public void retrieveDefaultsInfo(String urlcons){
         try{
         
       //	  String jsStr = BaseAuthenicationHttpClient.doRequest(context,retrieveUrl);
        	 /*String jsStr ;
            	InputStream is = context.getAssets().open("android_defaults");  
               int size = is.available();  
               byte[] buffer = new byte[size];  
               is.read(buffer);  
               is.close();  
                 jsStr = new String(buffer, "utf8");  */
             String jsStr ;
        	 try{
             	URL myURL = new URL(urlcons);
             	URLConnection ucon = myURL.openConnection();
             	InputStream is = ucon.getInputStream();
             	BufferedInputStream bis = new BufferedInputStream(is);
             	ByteArrayBuffer baf = new ByteArrayBuffer(50);
             	int current = 0;
             	while((current = bis.read()) != -1){
             	baf.append((byte)current);
             	}
             	jsStr = EncodingUtils.getString(baf.toByteArray(),"UTF-8");
             	} catch(Exception e){
         		jsStr = e.getMessage();
             	}
//           Log.d("Rock", jsStr+"=111111111111111");
             
             
             JSONObject json = null;
             JSONObject jobj_defaults = null;
             JSONObject jobj_messages = null;
             JSONObject jobj_brands = null;
             
             json = new JSONObject(jsStr);
            /* jobj_defaults = json.getJSONObject(CONFIG_DEFAULTS);
             staticAssetsUrl= jobj_defaults.getString(CONFIG_DEFAULTS_STATICASSETSURL);
             brandURL = jobj_defaults.getString(CONFIG_DEFAULTS_BRANDURL);
             uiURL = jobj_defaults.getString(CONFIG_DEFAULTS_UIURL);*/
             jobj_messages = json.getJSONObject(CONFIG_MESSAGES);
             upgradeVersion= jobj_messages.getString(CONFIG_MESSAGES_UPGRADEVERSION);
             introductionUpgrade = jobj_messages.getString(CONFIG_MESSAGES_INTRODUCTIONUPGRADE);
             downClientUrl= jobj_messages.getString(CONFIG_MESSAGES_DOWNCLIENTURL);
             emailMessage= jobj_messages.getString(CONFIG_MESSAGES_EMAILMESSAGE);
             smsMessage= jobj_messages.getString(CONFIG_MESSAGES_SMSMESSAGE);
             weiboMessage= jobj_messages.getString(CONFIG_MESSAGES_WEIBOMESSAGE);
             clearCacheDate= jobj_messages.getString(CONFIG_MESSAGES_CLEARCACHEDATE);
             clearCacheType= jobj_messages.getString(CONFIG_MESSAGES_CLEARCACHETYPE);
             
             jobj_brands = json.getJSONObject(CONFIG_BRANDS);
             brandWhiteLogo= jobj_brands.getString(CONFIG_BRANDS_brandWhiteLogo);
             brandBlackLogo = jobj_brands.getString(CONFIG_BRANDS_brandBlackLogo);
             brandColorLogo= jobj_brands.getString(CONFIG_BRANDS_brandColorLogo);
             brandBG= jobj_brands.getString(CONFIG_BRANDS_brandBG);
             brandDetailImage= jobj_brands.getString(CONFIG_BRANDS_brandDetailImage);
             setSuccessed(true);
             
         }catch (final Exception e){
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
