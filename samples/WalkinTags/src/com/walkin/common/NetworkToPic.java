package com.walkin.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class NetworkToPic {
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream baops = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baops);
		return baops.toByteArray();
	}

	public static Drawable resizeImage(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap resizedBitmap = null;
		try {
			Bitmap BitmapOrg = bitmap;

			int width = BitmapOrg.getWidth();
			int height = BitmapOrg.getHeight();
			int newWidth = w;
			int newHeight = h;

			// calculate the scale
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;

			// create a matrix for the manipulation
			Matrix matrix = new Matrix();
			// resize the Bitmap
			matrix.postScale(scaleWidth, scaleHeight);
			// if you want to rotate the Bitmap
			// matrix.postRotate(45);

			// recreate the new Bitmap
			resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height,
					matrix, true);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);

	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(

		drawable.getIntrinsicWidth(),

		drawable.getIntrinsicHeight(),

		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
				.getIntrinsicHeight());

		drawable.draw(canvas);

		return bitmap;

	}
	  
    /* @param 将图片内容解析成字节数组  
    * @param inStream  
    * @return byte[]  
    * @throws Exception  
    */    
   public static byte[] readStream(InputStream inStream) throws Exception {    
       byte[] buffer = new byte[1024];    
       int len = -1;    
       ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
       while ((len = inStream.read(buffer)) != -1) {    
           outStream.write(buffer, 0, len);    
       }    
       byte[] data = outStream.toByteArray();    
       outStream.close();    
       inStream.close();    
       return data;    
   
   }   
	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 根据图片网络地址获取图片的byte[]类型数据
	 * 
	 * @param urlPath
	 *            图片网络地址
	 * @return 图片数据
	 */

	public static byte[] getImageFromURL(String urlPath) {
		byte[] data = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			// conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6000);
			is = conn.getInputStream();
			if (conn.getResponseCode() == 200) {
				data = readInputStream(is);
			} else
				System.out.println("发生异常！");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 读取InputStream数据，转为byte[]数据类型
	 * 
	 * @param is
	 *            InputStream数据
	 * @return 返回byte[]数据
	 */
	public static byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 根据网络图片地址集批量获取网络图片
	 * 
	 * @param urlPath
	 *            网络图片地址数组
	 * @return 返回Bitmap数据类型的数组
	 */
	public static Bitmap[] getBitmapArray(String[] urlPath) {
		int length = urlPath.length;
		if (urlPath == null || length < 1) {
			return null;
		} else {
			Bitmap[] bitmaps = new Bitmap[length];
			for (int i = 0; i < length; i++) {

				try {
					byte[] imageByte = getImageFromURL(urlPath[i].trim());
					// 以下是把图片转化为缩略图再加载
					BitmapFactory.Options options = new BitmapFactory.Options();
					// options.inJustDecodeBounds = true;
					// Bitmap bitmap=BitmapFactory.decodeByteArray(imageByte, 0
					// , imageByte.length, options);
					options.inJustDecodeBounds = false;
					int be = (int) (options.outHeight / (float) 200);
					if (be <= 0)
						be = 1;
					options.inSampleSize = be;
					bitmaps[i] = BitmapFactory.decodeByteArray(imageByte, 0,
							imageByte.length, options);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			return bitmaps;
		}

	}

	private class MyTaskMeInbiBalance extends
			AsyncTask<String, Integer, String> {

		protected String doInBackground(String... params) {

			// String urlcons =
			// SppaConstant.WALKIN_URL_USER+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"?userId="
			// +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)+"&"
			// +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
			// Log.d("Rock", urlcons);
			// user_mservice.setRetrieveUrl(urlcons);
			// user_mservice.retrieveUserQueryInfo();
			return null;
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {
			// Button_ME.setText(user_mservice.getInbiBalance());
		}

	}
}
