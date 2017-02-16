package com.bluelight.Daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bluelight.dao.EquipmentDao;
import com.bluelight.demo.consts.Equipment;
import com.bluelight.tools.BaseDao;

public class EquipmentDaoimpl implements EquipmentDao {
	 BaseDao bd=new BaseDao();
	@Override
	public Boolean add(Equipment equipment) {
		String sql="insert into Equipment(openid,FEN,ALS,BAT) values(?,?,?,?)";
        return  bd.executeUpdate(sql,new Object[]{equipment.getOpenid(),equipment.getFEN(),equipment.getALS(),equipment.getBAT()});			
	}
	@Override
	public Equipment findByopenid(String opendid) {
		Equipment equipment=null;
		String sql="select openid,FEN,ALS,BAT from  Equipment where openid='"+opendid+"'";
		ResultSet rs=bd.executeQuery(sql);
		try {
			while(rs.next())
			{
				equipment=new Equipment(rs.getString("openid"),rs.getInt("FEN"),rs.getInt("ALS"),rs.getInt("BAT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return equipment;
	}
	@Override
	public Boolean update(Equipment equipment) {
		String sql="update Equipment set FEN=?,ALS=?,BAT=? where openid=?";
		return   bd.executeUpdate(sql,new Object[]{equipment.getFEN(),equipment.getALS(),equipment.getBAT(),equipment.getOpenid()});
	}
	

}
