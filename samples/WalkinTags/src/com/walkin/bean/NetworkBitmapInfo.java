package com.walkin.bean;






import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public class NetworkBitmapInfo {
    private static NetworkBitmapInfo instance = null;
   
	private String [] strIMG;            
	private Bitmap[] bitmap;    
	protected List<Map<String, Object>> mList;
	private int isRemoveNum=0;
	
	
	/**
	 * @return the isRemoveNum
	 */
	public int getIsRemoveNum() {
		return isRemoveNum;
	}

	/**
	 * @param isRemoveNum the isRemoveNum to set
	 */
	public void setIsRemoveNum(int isRemoveNum) {
		this.isRemoveNum = isRemoveNum;
	}

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

	/**
	 * @return the bitmap
	 */
	public Bitmap[] getBitmap() {
		return bitmap;
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(Bitmap[] bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the strIMG
	 */
	public String[] getStrIMG() {
		return strIMG;
	}

	/**
	 * @param strIMG the strIMG to set
	 */
	public void setStrIMG(String[] strIMG) {
		this.strIMG = strIMG;
	}

	private Context context;
    public void setActivity(Activity activity) {
    	context = activity;
    }
    
    private NetworkBitmapInfo(){}

    public static NetworkBitmapInfo getInstance(){
        if(instance == null){
            instance =  new  NetworkBitmapInfo();
        }
        return instance;
    }


}
