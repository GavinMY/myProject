package com.bluelight.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bluelight.entry.Fireinfo;

public interface FireinfoDao
{
	public List findByopenid(String openid,Timestamp moring,Timestamp now);
	public Boolean add(Fireinfo fireinfo);
	public Map findByzhouinfo(String openid,Timestamp moring,Timestamp now);
	public Map findBymouthinfo(String openid,Timestamp moring,Timestamp now);
}
