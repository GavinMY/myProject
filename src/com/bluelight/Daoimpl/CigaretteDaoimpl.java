package com.bluelight.Daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.bluelight.dao.CigaretteDao;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.entry.cigarette;
import com.bluelight.tools.BaseDao;

public class CigaretteDaoimpl implements CigaretteDao {
	BaseDao bd=new BaseDao();
	@Override
	public cigarette findByopenid(String openid) {
		cigarette c=null; 
        String sql="select cprice,ctar,soda,ctarget,count,openid from cigarette where openid='"+openid+"'";
        System.out.println(sql);
        ResultSet rs=bd.executeQuery(sql);
        try {
			while(rs.next()){
				
              c=new cigarette(rs.getDouble("cprice"),rs.getDouble("ctar"),rs.getDouble("soda"),rs.getInt("ctarget"),rs.getInt("count"),openid);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return c;
	}

	@Override
	public Boolean add(cigarette Cigarette) {
		String sql="insert into cigarette(cprice,ctar,soda,ctarget,count,openid) values(?,?,?,?,?,?)";
        return  bd.executeUpdate(sql,new Object[]{Cigarette.getCprice(),Cigarette.getCtar(),Cigarette.getSoda(),Cigarette.getCtarget(),Cigarette.getCount(),Cigarette.getOpenid()});				
	}


	public Map selectEquipment(String openid)
	{
		String sql="select c.ctarget,c.cprice,c.ctar,c.soda,c.count from cigarette c  where c.openid='"+openid+"'";
		Map map=new HashMap();
		ResultSet rs=bd.executeQuery(sql);	
	try {
			while(rs.next())
			{
				map.put("ctarget", rs.getInt("ctarget"));
				map.put("cprice", rs.getDouble("cprice"));
				map.put("ctar", rs.getDouble("ctar"));
				map.put("soda", rs.getDouble("soda"));
				map.put("count", rs.getDouble("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	public Boolean updatecigarette(cigarette cigarete) {
		String sql="update cigarette set cprice=?,ctar=? where openid=?";
		return   bd.executeUpdate(sql,new Object[]{cigarete.getCprice(),cigarete.getCtar(),cigarete.getOpenid()});
	}

}
