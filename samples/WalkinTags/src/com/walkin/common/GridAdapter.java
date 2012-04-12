package com.walkin.common;

import java.util.List;

import com.walkin.bean.GridInfo;
import com.walkin.common.AsyncImageLoader.ImageCallback;
import com.walkin.spp.SppaConstant;
import com.walkin.walkin.BrandsActivity;
import com.walkin.walkin.MeActivity;
import com.walkin.walkin.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright (C) 2010,Under the supervision of China Telecom Corporation Limited
 * Guangdong Research Institute The New Vphone Project
 * 
 * @Author fonter.yang
 * @Create date��2010-10-11
 * 
 */
public class GridAdapter extends BaseAdapter {

	private class GridHolder {
		ImageView appImage;
		TextView appName;
	}

	private Context context;

	private List<GridInfo> list;
	private LayoutInflater mInflater;

	public GridAdapter(Context c) {
		super();
		this.context = c;
	}

	public void setList(List<GridInfo> list) {
		this.list = list;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int index) {

		return list.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		GridHolder holder;
		if (convertView == null) {   
			convertView = mInflater.inflate(R.layout.badgesgrid_detail_item, null);   
			holder = new GridHolder();
		//	holder.appImage = (ImageView)convertView.findViewById(R.id.ImageView_itemImage);
			holder.appName = (TextView)convertView.findViewById(R.id.TextView_itemText);
			convertView.setTag(holder);   

		}else{
			 holder = (GridHolder) convertView.getTag();   

		}
		GridInfo info = list.get(index);
		if (info != null) {   
			holder.appName.setText(info.getName());

			loadImage4(SppaConstant.WALKIN_URL_BASE+"badges/ios/"+info.getStrImg()+"_full.png", (ImageView)convertView.findViewById(R.id.ImageView_itemImage));
			
//			holder.appImage.setBackgroundResource(info.getResid());

		}
		Log.d("Rock", info.getStrImg()+":info.getStrImg()");
		return convertView;
	}
	
	 private AsyncImageLoader3 asyncImageLoader3 = new AsyncImageLoader3();
	    //采用Handler+Thread+封装外部接口
	    private void loadImage5(final String url, final ImageView imageView) {
	          //如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
	         Drawable cacheImage = asyncImageLoader3.loadDrawable(url,new AsyncImageLoader3.ImageCallback() {
	             //请参见实现：如果第一次加载url时下面方法会执行
	             public void imageLoaded(Drawable imageDrawable) {
	            	 
	            	 if (imageDrawable != null ) { 
	            	 imageView.setImageDrawable(imageDrawable);
	            	 }else{
         		 imageView.setImageResource(R.drawable.badge_back_full);
	            	 }
	             }
	         });
	        if(cacheImage!=null){
	        	imageView.setImageDrawable(cacheImage);
	        }else{
    		 imageView.setImageResource(R.drawable.badge_back_full);
    	 }
	    }
	
	    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	    private void loadImage4(final String url, final ImageView imageView) {
	        // 延遲加載圖片 ： imageUrl 是 圖片的http鏈接地址，後面是回调函數
	        Drawable cachedImage = asyncImageLoader.loadDrawable(this.context, url, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                
	               if (imageDrawable != null ) { // 防止图片url获取不到图片是，占位图片不见了的情况
	            	   imageView.setImageDrawable(imageDrawable);
	               }else{
	            		 imageView.setImageResource(R.drawable.badge_back_full);
	            	 }
	            }
	        });
	        if(cachedImage!=null){
	        	imageView.setImageDrawable(cachedImage);
	        }else{
    		 imageView.setImageResource(R.drawable.badge_back_full);
    	 }
	    	}

}
