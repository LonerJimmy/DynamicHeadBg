package com.example.params;

public class ViewParams {
	public float R;
	public float x,y;
	public int[] rgb;
	public boolean isRote;	
	public int startAlph,endAlph;
	public int startR,endR;
	public float time;
	
	public ViewParams(){
		R=0;
		x=0;
		y=0;
		rgb=new int[3];
		isRote=false;		
		startAlph=0;
		endAlph=0;
		startR=0;
		endR=0;
		time=0;
	}
}
