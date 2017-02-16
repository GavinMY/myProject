package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.SNSUserInfo;

public class YcjkServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public YcjkServlet() {
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
		 HttpSession session=request.getSession();
		 String openId=session.getAttribute("openId").toString();//亲人的openid
		 request.setCharacterEncoding("utf-8");
		 SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
		 FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
		 SNSUserInfo user=Userdaoimpl.family(openId);//根据自己的openid查找亲人的账号
		if(null!=user)
		{
		 //查询他今日抽烟信息
		 SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//24小时制 
		  Date now=new Date();
		  request.setAttribute("date",sdformat.format(now));
         Date moring = getTimesmorning();
         Timestamp  end = new Timestamp(now.getTime());
         Timestamp begin = new Timestamp(moring.getTime());
         List<Fireinfo> list=Fireinfo.findByopenid(user.getOpenId(),begin,end);	         
		 request.setAttribute("uname", user.getNickname());
		 request.setAttribute("list", list);
		 System.out.println(list.size());
		 request.getRequestDispatcher("/ycjk.jsp").forward(request, response);
		}
		else
		{
		request.getRequestDispatcher("/ycjkerror.jsp").forward(request, response);
		}
		
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
