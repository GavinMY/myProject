package com.bluelight.dao;

import com.bluelight.demo.consts.Equipment;

public interface EquipmentDao 
{
	public Boolean add(Equipment equipment); 
	public Equipment findByopenid(String opendid);
	public Boolean update(Equipment equipment);
}
