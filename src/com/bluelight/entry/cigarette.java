package com.bluelight.entry;

public class cigarette {
//香烟编号
private int cid;
//香烟价格·
private double cprice;
//焦油含量
private double ctar;
private double  soda;//烟碱量
private int ctarget;//控烟目标
private int count;//每盒烟的数量
//所属用户
private  String openid;
public cigarette()
{
}
public cigarette(int cid, double cprice, double ctar, String openid) {
	super();
	this.cid = cid;
	this.cprice = cprice;
	this.ctar = ctar;
	this.openid = openid;
}
public cigarette(double cprice, double ctar, double soda, int ctarget,
		int count, String openid) {
	super();
	this.cprice = cprice;
	this.ctar = ctar;
	this.soda = soda;
	this.ctarget = ctarget;
	this.count = count;
	this.openid = openid;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public double getCprice() {
	return cprice;
}
public void setCprice(double cprice) {
	this.cprice = cprice;
}
public double getCtar() {
	return ctar;
}
public void setCtar(double ctar) {
	this.ctar = ctar;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
public double getSoda() {
	return soda;
}
public void setSoda(double soda) {
	this.soda = soda;
}
public int getCtarget() {
	return ctarget;
}
public void setCtarget(int ctarget) {
	this.ctarget = ctarget;
}

}
