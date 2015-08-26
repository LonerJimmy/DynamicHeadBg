package com.example.view;

import java.util.Random;

import com.example.params.ViewParams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

@SuppressLint("DrawAllocation") public class CircleView extends View{

	private Paint paint=new Paint();		
	private int alph;
	private float alphFloat;
	private ViewParams params;
	private float sizeScale;
	private float alphScale;
	private float x,y,R;
	private boolean change;
	private float startR,endR;	
	private boolean isEnable = true;
		
	public CircleView(Context context) {
		super(context);				
	}
	
	public CircleView(Context context, AttributeSet set){
		super(context, set);
	}	
	
	public CircleView(Context context, ViewParams params){
		super(context);
		this.params=params;	
		this.x=params.x;
		this.y=params.y;
		this.R=params.R;
		this.alphFloat=params.startAlph*255/100;
		this.alph=Math.round(alphFloat);
		this.alphScale=(Math.abs(params.startAlph-params.endAlph))/(params.time/2*1000/16)*255/100;
		this.sizeScale=(Math.abs(params.startR-params.endR))/(params.time/2*1000/16)*R/100;
		this.startR=params.startR*params.R/100;
		this.endR=params.endR*params.R/100;
		this.change=false;
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG); 	
		paint.setAntiAlias(true);
	}	
	
	public void onDraw(Canvas canvas)
	{
		if(params.isRote)//圆圈进行自转
		{
			paint.setColor(Color.argb(alph,params.rgb[0],params.rgb[1],params.rgb[2]));
			//paint.setAlpha(alph);			
			if(change && isEnable){				
				if(R>startR){
					change=false;
				}else{
					R+=sizeScale;
					/*
					alphFloat=((alphFloat+alphScale));
					alph=Math.round(alphFloat);*/
				}
			}else if (isEnable){				
				if(R<endR){
					change=true;
				}else{
					R-=sizeScale;
					/*
					alphFloat=((alphFloat-alphScale));
					alph=Math.round(alphFloat);*/					
				}
			}
			canvas.drawCircle(x,y,R,paint);
			invalidate();
		}
	}
	
	
	public void onLayout (boolean changed, int left, int top, int right, int bottom)
	{
		
	}
	
	public void setDrawChangeEnable(boolean is) {
	    isEnable = is;
	}
}
