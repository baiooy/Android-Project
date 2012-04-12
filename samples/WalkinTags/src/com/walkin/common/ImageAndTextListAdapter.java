package com.walkin.common;




import java.util.List;

import com.walkin.bean.ImageAndText;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 图片异步下载类
 * (如该图片已存在即显示图片,否则通过URL下载图片并显示)
 * 
 * @author 		Tim   http://doinone.iteye.com/blog/
 * @created		2011-6-22
 * @since		2011-6-22
 */

public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {

	    private ListView listView;
	    private AsyncImageLoader asyncImageLoader;
	    private LayoutInflater inflater ;
	    private Context context;

	    public ImageAndTextListAdapter(Activity activity, List<ImageAndText> imageAndTexts, ListView listView) {
	        super(activity, 0, imageAndTexts);
	        this.listView = listView;
	        asyncImageLoader = new AsyncImageLoader();
	        inflater = activity.getLayoutInflater();
	        this.context = activity;
	    }

	  /*  public View getView(int position, View convertView, ViewGroup parent) {
//	        Activity activity = (Activity) getContext();

	        Holder holder ;
	        
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.image_and_text_row, null);
	            holder = new Holder(); 
	            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
	    		holder.textView = (TextView)convertView.findViewById(R.id.text);
	           
	            convertView.setTag(holder);
	        } else {
	        	holder = (Holder) convertView.getTag();
	        }
	        
	        // 获取item对应第几条（position）的Model实体
	        ImageAndText imageAndText = getItem(position);

	        // Load the image and set it on the ImageView
	        String imageUrl = imageAndText.getImageUrl();	        
	        holder.imageView.setTag(imageUrl);
	        
	       
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(context, imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
	               // Log.i("Rock", imageViewByTag+"Drawable cachedImage = asyncImageLoader.loadDrawable( imageViewByTag)-->");
	               // Log.i("Rock", imageDrawable+"Drawable cachedImage = asyncImageLoader.loadDrawable( imageDrawable)-->");
	                
	                if (imageViewByTag != null && imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	                    imageViewByTag.setImageDrawable(imageDrawable);
	                }
	            }
	        });
			if (cachedImage == null) {
				holder.imageView.setImageResource(R.drawable.default_image);
			}else{
				holder.imageView.setImageDrawable(cachedImage);
			}
			
			
	        // Set the text on the TextView	        
	        holder.textView.setText(imageAndText.getText());
	        
	        Log.i("test","position = " + position);
	        
	        return convertView;
	    }*/
	    
	    
	    static class Holder{
	    	ImageView imageView;
		    TextView textView;
	    }
	    

}
