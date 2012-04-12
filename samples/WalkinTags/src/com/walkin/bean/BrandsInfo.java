package com.walkin.bean;






import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public class BrandsInfo {
    private static BrandsInfo instance = null;
   
    List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();


	/**
	 * @return the mList
	 */
	public List<Map<String, Object>> getmList() {
		return mList;
	}

	/**
	 * @param mList the mList to set
	 */
	public void setmList(List<Map<String, Object>> mList) {
		this.mList = mList;
	}

	private Context context;
    public void setActivity(Activity activity) {
    	context = activity;
    }
    
    private BrandsInfo(){}

    public static BrandsInfo getInstance(){
        if(instance == null){
            instance =  new  BrandsInfo();
        }
        return instance;
    }


}
