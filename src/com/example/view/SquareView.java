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

@SuppressLint("DrawAllocation") public class SquareView extends View{

	private Paint paint=new Paint();		
	private int alph;
	private float alphFloat;
	private ViewParams params;
	private float sizeScale;
	private float alphScale;
	private float x,y,R;
	private boolean change;
	private float startR,endR;
	private int count=0;
	private boolean isEnable = true;
	
	public SquareView(Context context) {
		super(context);			
	}
	
	public SquareView(Context context, AttributeSet set){
		super(context, set);
	}	
	
	public SquareView(Context context, ViewParams params){
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
		if(params.isRote){			
		}else{
			paint.setColor(Color.argb(alph,params.rgb[0],params.rgb[1],params.rgb[2]));	
		}
	}	
	
	public void onDraw(Canvas canvas)
	{	
		if(params.isRote)//圆圈进行自转
		{
			paint.setColor(Color.argb(alph,params.rgb[0],params.rgb[1],params.rgb[2]));			
			canvas.rotate(-45);	
			canvas.drawRect(-R/2+x,R/2+y,R/2+x,R*3/2+y,paint);
			if(change && isEnable){				
				if(R>startR){
					change=false;
				}else{
					R+=sizeScale;					
					alphFloat=((alph+alphScale));	
					alph=Math.round(alphFloat);				
				}
			}else if (isEnable) {				
				if(R<endR){
					change=true;
				}else{
					R-=sizeScale;					
					alphFloat=((alph-alphScale));	
					alph=Math.round(alphFloat);
					System.out.println("alph="+alph);
				}
			}
			invalidate();
		}else{			
			paint.setAlpha(alph);
			canvas.rotate(-45);	
			canvas.drawRect(-R/2+x,R/2+y,R/2+x,R*3/2+y,paint);
			if(change && isEnable){				
				if(R>startR){
					change=false;
				}else{
					R+=sizeScale;
					alphFloat=((alph-alphScale));	
					alph=Math.round(alphFloat);
				}
			}else if (isEnable){				
				if(R<endR){
					change=true;
				}else{
					R-=sizeScale;
					alphFloat=((alph+alphScale));	
					alph=Math.round(alphFloat);
				}
			}
			invalidate();
		}
	}
	
	public void setDrawChangeEnable(boolean is) {
        isEnable = is;
    }
	/*private void drawRect(Canvas canvas) {
		// TODO Auto-generated method stub
		rectX=(float) (rectX+0.1*Math.cos(rectAngle/180*Math.PI));
		rectY=(float) (rectY+0.1*Math.sin(rectAngle/180*Math.PI));
		rectAngle+=1;		
		if(rectAngle>=360)
			rectAngle=0;
	}

	private void drawRandomCircle(Canvas canvas)//移动一段距离随机改变一次方向
	{		 
		 System.out.println("random="+r.nextInt(360));		 
		 randomRoteX=(float) (randomRoteX+0.5*Math.cos(randomAngle2*Math.PI/180));
		 randomRoteY=(float) (randomRoteY+0.5*Math.sin(randomAngle2*Math.PI/180));
		 if(distance(randomRoteX,randomRoteY)>=50){
			 for(int i=0;i<9000000;i++){				 
			 }			 
			 randomAngle2=randomAngle2+90+r.nextInt(180);}else{			 
			 }
		 System.out.println("randomRoteX="+randomRoteX);
		 System.out.println("randomRoteY="+randomRoteY);
	}
	
	private float distance(float x,float y){
		float d=(float) Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		return d;		
	}
	
	private void drawRandomCircle2(Canvas canvas)//走到某位置之后弹回的方向随机
	{
		randomRoteX=(float) (randomRoteX+moveLength*Math.cos(initAngle*Math.PI/180));
		randomRoteY=(float) (randomRoteY+moveLength*Math.sin(initAngle*Math.PI/180));	
		if(moveLength>=5){
			canvas.translate(randomRoteX, randomRoteY);
			moveLength=0;
			initAngle=r.nextInt(180)+90+initAngle;			
		 }else{
			 moveLength+=0.1;
		 }		
	}

	private void drawBlueCircle(Canvas canvas) {
		// TODO Auto-generated method stub						
		switch(mRote)
		{
		case 1:			
			if(mRoteX>=40)
				mRote=2;
			else
				mRoteX+=0.5;
			break;
		case 2:
			if(mRoteY>=60)
				mRote=3;
			else				
				mRoteY+=0.5;	
			break;
		case 3:
			if(mRoteX<=0 && mRoteY<=0)
			{
				mRote=1;
			}			
			else
			{
				mRoteX-=0.4;
				mRoteY-=0.6;
			}
			break;
		}		
	}
	
	private void drawLastCircle(Canvas canvas) {
		// TODO Auto-generated method stub						
		if(isLastRote)
		{
			if(isLastAdd)
			{				
				if(lastRote>50)
				{
					isLastAdd=false;
				}
				else
					lastRote+=0.5;
			}			
			else
			{				
				if(lastRote<0)
				{
					isLastAdd=true;
					isLastRote=false;
				}
				else
					lastRote-=0.5;
			}
		}			
		else
		{
			if(isLastAdd)
			{				
				if(lastRote>100)
				{
					isLastAdd=false;
				}
				else
					lastRote+=0.5;
			}			
			else
			{				
				if(lastRote<0)
				{
					isLastAdd=true;
					isLastRote=true;
				}
				else
					lastRote-=0.5;
			}
		}
		
	}
	
	public void onLayout (boolean changed, int left, int top, int right, int bottom)
	{
		
	}
	*/
}
