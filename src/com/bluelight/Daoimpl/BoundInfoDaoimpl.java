package com.bluelight.Daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bluelight.dao.BoundInfoDao;
import com.bluelight.entry.BoundInfo;
import com.bluelight.tools.BaseDao;

public class BoundInfoDaoimpl implements BoundInfoDao {
	 BaseDao bd=new BaseDao();
	@Override
	public boolean add(BoundInfo boundInfo) {
		// TODO Auto-generated method stub
		String sql="insert into BoundInfo(fromUserName,DeviceID,DeviceType,OpenID) values(?,?,?,?)";
        return  bd.executeUpdate(sql,new Object[]{boundInfo.getFromUserName(),boundInfo.getDeviceID(),boundInfo.getDeviceType(),boundInfo.getOpenID()});
	}
	@Override
	public boolean dete(String FromUserName) {
		 return bd.executeUpdate("delete  from BoundInfo where FromUserName='"+FromUserName+"'");
	}
	@Override
	public BoundInfo findbyFromUserName(String fromUserName) {
		    BoundInfo boundInfo=null;         
	        String sql="select * from boundinfo where fromUserName='"+fromUserName+"'";
	        ResultSet rs=bd.executeQuery(sql);
	        try {
	            while(rs.next()){
	            	boundInfo=new BoundInfo(rs.getString("FromUserName"), rs.getString("DeviceID"), rs.getString("DeviceType"), rs.getString("OpenID"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }	         
	        return boundInfo;
	}
	@Override
	public boolean update(BoundInfo boundInfo) {
		 String sql="update BoundInfo set deviceID=?,deviceType=? where openID=?";
		 return bd.executeUpdate(sql,new Object[]{boundInfo.getDeviceID(),boundInfo.getDeviceType(),boundInfo.getOpenID()});
	}

}
