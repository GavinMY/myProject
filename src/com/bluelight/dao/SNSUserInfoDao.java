package com.bluelight.dao;

import com.bluelight.entry.SNSUserInfo;

public interface SNSUserInfoDao 
{
public Boolean add(SNSUserInfo UserInfo);
public SNSUserInfo findByopenid(String openid);
public Boolean update(SNSUserInfo UserInfo);
public Boolean updateLocation(SNSUserInfo UserInfo);
public int findtarget(String openid);
}
