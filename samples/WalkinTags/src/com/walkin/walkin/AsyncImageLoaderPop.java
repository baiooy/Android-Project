package com.walkin.walkin;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoaderPop {
	private static HashMap<String, SoftReference<Drawable>> imageCache;

	static {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public AsyncImageLoaderPop() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public static Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageView,
						imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String imageUrl);
	}

	public static void setImage(String url, ImageView view, int resId) {
		Drawable cachedImage;
		cachedImage = loadDrawable(url, view, new ImageCallback() {
			public void imageLoaded(Drawable imageDrawable,
					ImageView imageView, String imageUrl) {
				imageView.setImageDrawable(imageDrawable);
			}
		});
		if (cachedImage == null) {
			view.setImageResource(resId);
		} else {
//			Bitmap bitmap = drawableToBitmap(cachedImage);
//			view.setImageBitmap(getRoundedCornerBitmap(bitmap));
			view.setImageDrawable(cachedImage);
		}
	}
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 12;

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
	}
}
