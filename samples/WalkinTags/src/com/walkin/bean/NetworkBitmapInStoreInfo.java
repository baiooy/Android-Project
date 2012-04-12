package com.walkin.bean;






import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public class NetworkBitmapInStoreInfo {
    private static NetworkBitmapInStoreInfo instance = null;
   
	private String [] strIMG;            
	private Bitmap[] bitmap;    

	
	
	
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
    
    private NetworkBitmapInStoreInfo(){}

    public static NetworkBitmapInStoreInfo getInstance(){
        if(instance == null){
            instance =  new  NetworkBitmapInStoreInfo();
        }
        return instance;
    }


}
