package com.bluelight.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class BaseDao
{
	public final String DRIVER="com.mysql.jdbc.Driver"; //驱动字符串
   public final String URL="jdbc:mysql://182.92.177.50:7777/lighter?useUnicode=true&characterEncoding=utf8"; //连接字符串
	//public final String URL="jdbc:mysql://127.0.0.1:3306/lighter?useUnicode=true&characterEncoding=utf8"; //连接字符串
	public final String USER="root"; //用户名
	public final String PWD="wslc"; //密码	
	Connection con=null; //连接对象
	PreparedStatement pstmt=null; //执行对象
	ResultSet rs=null; //结果集对象

	/**
	 * 获得数据库连接对象
	 * @return 连接对象
	 */
	public Connection getConnection(){
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PWD);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载异常！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("获得连接异常！");
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 执行DML（INSERT,DELETE,UPDATE） 操作
	 * @param sql 要执行的DML语句 
	 * @param values SQL语句占位符对应的值数组
	 * @return boolean类型执行结果
	 */
	public boolean executeUpdate(String sql,Object[] values){
		boolean flag=false;
		getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			setValues(values);
			
			pstmt.executeUpdate();
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.closeAll();
		}
		return flag;
	}
	/**
	 * 执行DML（INSERT,DELETE,UPDATE） 操作
	 * @param sql 要执行的DML语句 
	 * @return boolean类型执行结果
	 */
	public boolean executeUpdate(String sql){
		boolean flag=false;
		getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.closeAll();
		}
		return flag;
	}
	
	
	
	/**
	 * 执行DQL（SELECT） 语句，并返回结果集合
	 * @param sql 要执行的SELETE语句
	 * @param values SQL语句对应的占位符参数值数组
	 * @return 结果集ResultSet
	 */
	public ResultSet executeQuery(String sql,Object[] values){
		getConnection();
		CachedRowSet crs=null;
		try {
			pstmt=con.prepareStatement(sql);
			this.setValues(values);
			
			rs=pstmt.executeQuery();
			
			//将连接对象ResultSet转换为非连接的本地缓存对象CachedRowSet
			crs=new CachedRowSetImpl();
			crs.populate(rs);//rs==>crs
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			this.closeAll();
		}
		return crs;
	}
	/**
	 * 执行DQL（SELECT） 语句，并返回结果集合
	 * @param sql 要执行的SELETE语句
	 * @return 结果集ResultSet
	 */
	public ResultSet executeQuery(String sql){
		getConnection();
		CachedRowSet crs=null;
		try {
			System.out.println(con+"......");
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			crs=new CachedRowSetImpl();
			crs.populate(rs);//rs==>crs
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally{
			this.closeAll();
		}
		return crs;
	}
	
	/**
	 * 为指定执行对象设置占位符的参数值
	 * @param pstmt 执行对象
	 * @param values 占位符的参数值数组
	 */
	public void setValues(Object[] values){
		if(values!=null&&values.length>0){
			for(int i=0;i<values.length;i++){
				try {
					pstmt.setObject((i+1), values[i]);
				} catch (SQLException e) {
					System.out.println("占位符参数设置有误！");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 关闭数据访问的各个对象
	 * @param con 连接对象
	 * @param pstmt 执行对象
	 * @param rs 结果集对象
	 */
	public void closeAll(){
		//关闭结果集通道
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("rs关闭异常！");
				e.printStackTrace();
			}
		}
		//关闭执行通道
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("pstmt关闭异常！");
				e.printStackTrace();
			}
		}
		
		//关闭连接通道
		try {
			if(con!=null&&(!con.isClosed())){
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("con关闭异常！");
			e.printStackTrace();
		}
	}
}