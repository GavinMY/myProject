package com.bluelight.entry;

import java.sql.Timestamp;
import java.util.Date;

public class Fireinfo 
{
	private int fid;
	private String openid;
	private Timestamp ftime;
	private String fromuser;
	private String address;
	
	public Fireinfo(int fid, String openid, Timestamp ftime, String fromuser,
			String address) {
		super();
		this.fid = fid;
		this.openid = openid;
		this.ftime = ftime;
		this.fromuser = fromuser;
		this.address = address;
	}
	
	public Fireinfo(String openid, Timestamp ftime, String fromuser,
			String address) {
		super();
		this.openid = openid;
		this.ftime = ftime;
		this.fromuser = fromuser;
		this.address = address;
	}

	public Fireinfo(String openid, Timestamp ftime,
			String address) {
		this.openid = openid;
		this.ftime = ftime;
		this.address = address;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Timestamp getFtime() {
		return ftime;
	}
	public void setFtime(Timestamp ftime) {
		this.ftime = ftime;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
