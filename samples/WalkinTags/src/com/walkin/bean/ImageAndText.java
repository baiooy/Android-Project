package com.walkin.bean;

/**
 * 保存ListView的item的实体
 *
 * 
 * @author 		Tim   http://doinone.iteye.com/blog/
 * @created		2011-6-22
 * @since		2011-6-22
 */

public class ImageAndText {
	    private String imageUrl;
	    private String text;

	    public ImageAndText(String imageUrl, String text) {
	        this.imageUrl = imageUrl;
	        this.text = text;
	    }
	    public String getImageUrl() {
	        return imageUrl;
	    }
	    public String getText() {
	        return text;
	    }
}
