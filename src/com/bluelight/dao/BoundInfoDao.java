package com.bluelight.dao;

import com.bluelight.entry.BoundInfo;

public interface BoundInfoDao 
{
	 public boolean add(BoundInfo boundInfo);
	 public boolean dete(String FromUserName);
	 public BoundInfo findbyFromUserName(String FromUserName);
	 public boolean update(BoundInfo boundInfo);
}
