package com.bluelight.Daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bluelight.dao.SNSUserInfoDao;
import com.bluelight.entry.BoundInfo;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.tools.BaseDao;

public class SNSUserInfodaoimpl implements SNSUserInfoDao {
	BaseDao bd=new BaseDao();
	@Override
	public Boolean add(SNSUserInfo UserInfo) {
		String sql="insert into users(openId,uHeadImgUrl,uname,usex,country,province,city) values(?,?,?,?,?,?,?)";
        return  bd.executeUpdate(sql,new Object[]{UserInfo.getOpenId(),UserInfo.getHeadImgUrl(),UserInfo.getNickname(),UserInfo.getSex(),UserInfo.getCountry(),UserInfo.getProvince(),UserInfo.getCity()});	
	}
	public Boolean addByopenid(String openId) {
		String sql="insert into users(openId) values(?)";
        return  bd.executeUpdate(sql,new Object[]{openId});	
	}
	@Override
	public SNSUserInfo findByopenid(String openid) {
		// TODO Auto-generated method stub
		SNSUserInfo UserInfo=null;	
        String sql="select openId,uHeadImgUrl,uname,usex,usmoke,la,ln,ctarget,uheight,uweight,uage,familyopenid from users where openid='"+openid+"'";
        System.out.println(sql);
        ResultSet rs=bd.executeQuery(sql);
        try {
			while(rs.next()){
UserInfo=new SNSUserInfo(
rs.getString("OpenID"),rs.getString("uname"),
rs.getInt("usex"),rs.getInt("uage"),
rs.getString("uHeadImgUrl"),rs.getDouble("uheight"),
rs.getDouble("uweight"),rs.getInt("usmoke"),
rs.getDouble("ln"),rs.getDouble("la"),
rs.getInt("ctarget"),rs.getString("familyopenid"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return UserInfo;
	}

	@Override
	public Boolean update(SNSUserInfo UserInfo) {
		 String sql="update users set uage=?,uheight=?,uweight=?,usmoke=? where openid=?";
	    return bd.executeUpdate(sql,new Object[]{UserInfo.getAge(),UserInfo.getUheight(),UserInfo.getUweight(),UserInfo.getUsmoke(),UserInfo.getOpenId()});
	}
	public Boolean wechatupdate(SNSUserInfo UserInfo) {
		 String sql="update users set uHeadImgUrl=?,uname=?,usex=?,country=?,province=?,city=? where openid=?";
		 System.out.println(sql);
	    return bd.executeUpdate(sql,new Object[]{UserInfo.getHeadImgUrl(),UserInfo.getNickname(),UserInfo.getSex(),UserInfo.getCountry(),UserInfo.getProvince(),UserInfo.getCity(),UserInfo.getOpenId()});
	}
	@Override
	public Boolean updateLocation(SNSUserInfo UserInfo) {
		 String sql="update users set la=?,ln=? where openid=?";
		    return bd.executeUpdate(sql,new Object[]{UserInfo.getLa(),UserInfo.getLn(),UserInfo.getOpenId()});	
	}

	@Override
	public int findtarget(String openid) {
		int target = 0;
	        String sql="select ctarget from users where openid='"+openid+"'";
	        System.out.println(sql);
	        ResultSet rs=bd.executeQuery(sql);
	        try {
	            while(rs.next())
	            {
	               target=rs.getInt("ctarget");
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return target;
	}
	public Boolean updateUserFamily(SNSUserInfo UserInfo) {
		 String sql="update users set  familyopenid=? where openid=?";
	    return bd.executeUpdate(sql,new Object[]{UserInfo.getFamily(),UserInfo.getOpenId()});
	}
	public SNSUserInfo family(String openid) 
	{
		SNSUserInfo UserInfo=null;	
        String sql="select openId,uHeadImgUrl,uname,usex,usmoke,la,ln,ctarget,uheight,uweight,uage,familyopenid from users where familyopenid='"+openid+"'";
        System.out.println(sql);
        ResultSet rs=bd.executeQuery(sql);
        try {
			while(rs.next()){
UserInfo=new SNSUserInfo(
rs.getString("OpenID"),rs.getString("uname"),
rs.getInt("usex"),rs.getInt("uage"),
rs.getString("uHeadImgUrl"),rs.getDouble("uheight"),
rs.getDouble("uweight"),rs.getInt("usmoke"),
rs.getDouble("ln"),rs.getDouble("la"),
rs.getInt("ctarget"),rs.getString("familyopenid"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return UserInfo;
		
	}
}
