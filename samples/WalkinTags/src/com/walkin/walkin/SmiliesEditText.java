package com.walkin.walkin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class SmiliesEditText extends EditText {

	public SmiliesEditText(Context context) {
		super(context);
	}
	public SmiliesEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
	public void insertIcon(Drawable drawable,String str){
		SpannableString spannable = new SpannableString(getText().toString()+str);   
	//	Drawable drawable =  new BitmapDrawable(myBitmap);  
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());  
	    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);   
	    spannable.setSpan(span, getText().length(),getText().length()+str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);     
        setText(spannable);
	}
}
