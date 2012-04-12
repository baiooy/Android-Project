package com.walkin.bean;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;

public class Vidicon  {
private GeoPoint longitude;
private int x,y;
private String VName;
private String url;
private Drawable bubbleImage;

	public Vidicon(GeoPoint geoPoint, int x, int y, String name,
			String url ) 
	{
		super();
		longitude = geoPoint;
		this.x = x;
		this.y = y;
		VName = name;
		this.url=url;
		this.bubbleImage=bubbleImage;
	}



	/**
	 * @return the bubbleImage
	 */
	public Drawable getBubbleImage() {
		return bubbleImage;
	}



	/**
	 * @param bubbleImage the bubbleImage to set
	 */
	public void setBubbleImage(Drawable bubbleImage) {
		this.bubbleImage = bubbleImage;
	}



	/**
	 * @return the longitude
	 */
	public GeoPoint getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(GeoPoint longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the vName
	 */
	public String getVName() {
		return VName;
	}

	/**
	 * @param name the vName to set
	 */
	public void setVName(String name) {
		VName = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
