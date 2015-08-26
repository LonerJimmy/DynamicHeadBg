package com.example.params;

public class ViewIncreament {
	private float startR,endR;
	private float sizeScale;
	private float startAlph,endAlph;
	private float alphScale;
	
	public void ViewIncreament(){
		startR=0;
		endR=0;
		sizeScale=0;
		startAlph=0;
		endAlph=0;
		alphScale=0;
	}
	
	public void ViewIncreament(float starR,float endR,float startAlph,float endAlph){
		this.startR=startR;
		this.endR=endR;
		this.startAlph=startAlph;
		this.endAlph=endAlph;
	}
	
	public void setR(float startR,float endR){
		this.startR=startR;
		this.endR=endR;
	}
	
	public void setAlph(float startAlph,float endAlph){
		this.startAlph=startAlph;
		this.endAlph=endAlph;
	}
	
	public float[] getR(){
		float[] R=new float[2];
		R[0]=this.startR;
		R[1]=this.endR;
		return R;		
	}
	
	public float[] getAlph(){
		float[] Alph=new float[2];
		Alph[0]=this.startAlph;
		Alph[1]=this.endAlph;
		return Alph;
	}
	
	public float getSizeScale(){
		return 0;
	}
}
