package com.bluelight.dao;

import java.util.List;
import java.util.Map;

import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.cigarette;

public interface CigaretteDao 
{
	public cigarette findByopenid(String openid);
	public Boolean add(cigarette Cigarette);
	public Map selectEquipment(String openid);
	
}
