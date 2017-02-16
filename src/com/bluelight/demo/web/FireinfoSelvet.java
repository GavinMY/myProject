package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.Date;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelight.Daoimpl.CigaretteDaoimpl;
import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.consts.WxConfig;
import com.bluelight.demo.util.AdvancedUtil;
import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.WeixinOauth2Token;
import com.bluelight.entry.cigarette;
import com.bluelight.tools.FindDate;

public class FireinfoSelvet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FireinfoSelvet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   request.setCharacterEncoding("utf-8");
		   HttpSession session=request.getSession();
		  String openid=session.getAttribute("openId").toString();
		 //  String openid=request.getParameter("openId");
		   session.setAttribute("openId", openid);
		   FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
		   CigaretteDaoimpl  cigaretteDaoimpl=new CigaretteDaoimpl();
		   SNSUserInfodaoimpl UserInfodao=new SNSUserInfodaoimpl();
//		   if(null==openid)//request里是否有openid
//		   { 		  
//			   if(null==session.getAttribute("openid"))
//			   {
//				 //用户同意授权后，能获取到code
//					String code = request.getParameter("code");
//			    	// 获取网页授权access_token
//					WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WxConfig.APPID,WxConfig.APPSECRET, code);			
//					// 用户标识
//				   openid = weixinOauth2Token.getOpenId();  
//				   session.setAttribute("openid", openid);
//			   }
//			   else
//			   {
//				   openid=session.getAttribute("openid").toString();
//			   }
//			   
//		   }
//		   openid="o3yS0uLDTYHDcN0BQIEqiRGYnDaQ";
		   //查询28天每日总的抽烟量
		   Date today1=new Date();
		   Timestamp todayzero = new Timestamp(getTimesmorning().getTime());//今天0点时间	
		   Timestamp today = new Timestamp(today1.getTime());//现在时间	
		   //本周起始时间
			FindDate Findate=new FindDate();
			Timestamp beginweek=new Timestamp(Findate.getTimesWeekmorning().getTime());
			Timestamp endweek=new Timestamp(Findate.getTimesWeeknight().getTime()); 
			 Timestamp endmouths=new Timestamp(Findate.getTimesMonthnight().getTime());//本月结束时间
	         Timestamp startmouths=new Timestamp(Findate. getTimesMonthmorning().getTime());//本月开始时间
		   Timestamp before28days=  new Timestamp(getTimesmorning().getTime()-24*24*60*60*100);//24天前的时间
		   int todaycount=Fireinfo.countFireinfo(todayzero, today, openid); //当日总抽烟量		
		   Map map=Fireinfo.findmorefireinfo(before28days,today,openid);
		  List date=new ArrayList();
		  List daysinfo=new ArrayList();
		  
		   for(int i=24;i>=0;i--)
		   {
			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
			   Timestamp ceshi=  new Timestamp(getTimesmorning().getTime()-i*24*60*60*1000); 
			   String str = df.format(ceshi);//时间
			  
			   date.add(str);
			   if(null==map.get(str))
			   {
				   daysinfo.add(0);
			   }
			   else
			   {
				   daysinfo.add(map.get(str)) ;
			   }
		   }		   
	//	 int target=UserInfodao.findtarget(openid); //查询用户控烟目标
		 
		 int weekcount=Fireinfo.countFireinfo(beginweek, endweek, openid); //本周总抽烟量		  
	     int mouthsweek=Fireinfo.countFireinfo(startmouths,endmouths, openid); //查询本月信息
	   //查询香烟信息
		cigarette Cigarette=cigaretteDaoimpl.findByopenid(openid);	
		
		
		request.setAttribute("openid", openid);
		request.setAttribute("date", date);//28天的日期
		request.setAttribute("todaycount", todaycount);//当日总抽烟量		
		request.setAttribute("daysinfo",daysinfo);
		request.setAttribute("weekcount", weekcount);//本周吸烟数目
		request.setAttribute("mouthsweek", mouthsweek);//本月吸烟数目
		request.setAttribute("Cigarette", Cigarette);//香烟价格焦油信息
		
		request.getRequestDispatcher("/day.jsp").forward(request, response);
	}
	// 获得当天0点时间
	public static Date getTimesmorning(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
