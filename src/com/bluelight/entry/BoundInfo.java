package com.bluelight.entry;

public class BoundInfo
{
private String FromUserName;
private String DeviceID;
private String DeviceType;
private String OpenID;

public BoundInfo()
{}

public BoundInfo(String FromUserName,String DeviceID,String DeviceType,String OpenID)
{
	this.FromUserName=FromUserName;
	this.DeviceID=DeviceID;
	this.DeviceType=DeviceType;
	this.OpenID=OpenID;
}

public String getFromUserName() {
	return FromUserName;
}
public void setFromUserName(String fromUserName) {
	FromUserName = fromUserName;
}
public String getDeviceID() {
	return DeviceID;
}
public void setDeviceID(String deviceID) {
	DeviceID = deviceID;
}
public String getDeviceType() {
	return DeviceType;
}
public void setDeviceType(String deviceType) {
	DeviceType = deviceType;
}
public String getOpenID() {
	return OpenID;
}
public void setOpenID(String openID) {
	OpenID = openID;
}
}
