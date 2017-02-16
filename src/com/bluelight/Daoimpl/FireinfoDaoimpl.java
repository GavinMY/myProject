package com.bluelight.Daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.bluelight.dao.FireinfoDao;
import com.bluelight.entry.BoundInfo;
import com.bluelight.entry.Fireinfo;
import com.bluelight.tools.BaseDao;
public class FireinfoDaoimpl implements FireinfoDao{
	 BaseDao bd=new BaseDao();
	@Override
	public Boolean add(Fireinfo fireinfo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List findByopenid(String openid,Timestamp moring,Timestamp now) {
		List<Fireinfo> list=new ArrayList<Fireinfo>();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        String sql="select openid,ftime,address from fireinfo where openid='"+openid+"'"+" and ftime>'"+sdformat.format(moring)+"' and ftime<'"+sdformat.format(now)+"' order by ftime asc";
        System.out.println(sql);
        ResultSet rs=bd.executeQuery(sql);
        try {
            while(rs.next()){
            	Fireinfo fireinfo=new Fireinfo(rs.getString("openid"),rs.getTimestamp("ftime"), rs.getString("address")==null?"未知":rs.getString("address"));
            //	System.out.println(sdformat.format(fireinfo.getFtime()));
            	list.add(fireinfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }	         
        return list;
	}
	@Override
	public Map findByzhouinfo(String openid, Timestamp moring, Timestamp now) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
		String  sql="select DATE_FORMAT(ftime,'%m-%d') time, count(*) count from fireinfo where openid='"+openid+"' and ftime>'"+sdformat.format(moring)+"' and ftime<'"+sdformat.format(now)+"'  group by  day(ftime) order by ftime asc";		
			System.out.println("周:"+sql);
			Map map=new  LinkedHashMap<String,Integer>();
			int size=0;
			ResultSet rs=bd.executeQuery(sql);
		  try {
	            while(rs.next()){
	            	map.put(rs.getString("time"), rs.getInt("count")); 
	            	size+=rs.getInt("count");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }	
	        map.put("sum", size);
	        return map;
	}

	@Override
	public Map findBymouthinfo(String openid, Timestamp moring, Timestamp now) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
		// TODO Auto-generated method stub
		String  sql="select DATE_FORMAT(ftime,'%Y,%u') weeks,count(*) as count from fireinfo  where openid='"+openid+"' and ftime>'"+sdformat.format(moring)+"' and ftime<'"+sdformat.format(now)+"'  group by  weeks order by ftime asc";
		System.out.println("月："+sql);
		Map map=new  LinkedHashMap<String,Integer>();
		int size=0;
		ResultSet rs=bd.executeQuery(sql);
		try {
			while(rs.next()){
				map.put(rs.getString("weeks"), rs.getInt("count")); 
				size+=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 map.put("mouthsum", size);
        return map;
	}
	public Boolean addFireinfo(Fireinfo fireinfo) 
	{
		String sql="insert into fireinfo(openid,ftime,fromuser,address) values(?,?,?,?)";
        return  bd.executeUpdate(sql,new Object[]{fireinfo.getOpenid(),fireinfo.getFtime(),fireinfo.getFromuser(),fireinfo.getAddress()});	
	}
	public Boolean findfireinfo(Timestamp time,String openid)
	{
		String sql="select openid,ftime,fromuser from fireinfo where ftime='"+time+"' and openid='"+openid+"'";
		System.out.println(sql);
		
		ResultSet rs=bd.executeQuery(sql);
		try {
			while(rs.next())
			{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	//查询28天点火信息
	public Map findmorefireinfo(Timestamp before,Timestamp now,String openid)
	{
		String sql="select DATE_FORMAT(ftime,'%Y-%m-%d') days,count(fid) count from fireinfo where openid='"+openid+"' and ftime>'" +before+"' and ftime<'"+now+"'  group by days order by ftime desc ";
		System.out.println(sql);
		Map map=new LinkedHashMap();
		ResultSet rs=bd.executeQuery(sql);
		try {
			while(rs.next())
			{
				String str=rs.getString("days");
				map.put(str, rs.getInt("count"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	//查询吸烟量
	public int countFireinfo(Timestamp before,Timestamp now,String openid)
	{
		int count=0;
		String sql="select count(*) count from fireinfo where openid='"+openid+"' and ftime>'" +before+"' and ftime<'"+now+"'";
		System.out.println(sql);
		ResultSet rs=bd.executeQuery(sql);
		try {
			while(rs.next())
			{
				 count=rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
