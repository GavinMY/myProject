package com.bluelight.demo.consts;

public  class Equipment
{
	private  String  openid;//电量
	private  int  FEN;//点火使能	
	private  int  ALS;//蜂鸣
	private  int BAT;//电量
	
	

	public Equipment() {
		super();
	}
	public Equipment(String openid,int fEN, int aLS, int bAT) {
		super();
		FEN = fEN;
		ALS = aLS;
		BAT = bAT;
		this.openid = openid;
	}
	public int getFEN() {
		return FEN;
	}
	public void setFEN(int fEN) {
		FEN = fEN;
	}
	public int getALS() {
		return ALS;
	}
	public void setALS(int aLS) {
		ALS = aLS;
	}
	public int getBAT() {
		return BAT;
	}
	public void setBAT(int bAT) {
		BAT = bAT;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
