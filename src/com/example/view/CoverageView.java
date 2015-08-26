package com.example.view;

import lenovo.jni.ImageUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CoverageView extends View{
	
	private View vv;

	public CoverageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}	

	public CoverageView(Context context, AttributeSet set){
		super(context, set);
	}	
	
	public CoverageView(Context context,View v){		
		super(context);
		this.vv=v;
	}
	
	public void onDraw(Canvas c){
		
	}

	public void onLayout (boolean changed, int left, int top, int right, int bottom)
	{
		
	}
}
