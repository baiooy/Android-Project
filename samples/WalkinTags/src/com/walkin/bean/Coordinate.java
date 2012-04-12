package com.walkin.bean;


public class Coordinate  {
private double x,y;
private String VName;
private String detail;

	public Coordinate( double x, double y, String name,
			String detail ) 
	{
		super();
		this.x = x;
		this.y = y;
		VName = name;
		this.detail=detail;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
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
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}



}
