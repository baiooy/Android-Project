package com.walkin.bean;


/**
 * Copyright (C) 2010,Under the supervision of China Telecom Corporation
 * Limited Guangdong Research Institute
 * The New Vphone Project
 * @Author fonter.yang
 * @Create date��2010-10-11
 * 
 */
public class GridInfo {

	private String name;
	private String strImg;
	
	public GridInfo(String name,String strImg) {
		super();
		this.name = name;
		this.strImg = strImg;
	}





	/**
	 * @return the strImg
	 */
	public String getStrImg() {
		return strImg;
	}








	/**
	 * @param strImg the strImg to set
	 */
	public void setStrImg(String strImg) {
		this.strImg = strImg;
	}








	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
