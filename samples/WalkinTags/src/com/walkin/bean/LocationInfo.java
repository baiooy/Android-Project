package com.walkin.bean;






import android.app.Activity;
import android.content.Context;

public class LocationInfo {
    private static LocationInfo instance = null;
   
	private double latitude=0;               
	private double longitude=0;            
	private float accuracy=0;    

	/**
	 * @return the accuracy
	 */
	public float getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	private Context context;
    public void setActivity(Activity activity) {
    	context = activity;
    }
    
    private LocationInfo(){}

    public static LocationInfo getInstance(){
        if(instance == null){
            instance =  new  LocationInfo();
        }
        return instance;
    }


}
