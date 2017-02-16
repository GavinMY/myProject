package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.entry.Fireinfo;

public class dayFire extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public dayFire() {
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
			String openid=request.getParameter("openid");
			FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
			List<Fireinfo> morning=new ArrayList<Fireinfo>();
			List<Fireinfo> afternoon=new ArrayList<Fireinfo>();
			List<Fireinfo> night=new ArrayList<Fireinfo>();
			 Timestamp begin=null;
			 Timestamp end=null;
			if(null==request.getParameter("date"))
			{     SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//24小时制 
				  Date now=new Date();
				  request.setAttribute("date",sdformat.format(now));
		          Date moring = getTimesmorning();
		          end = new Timestamp(now.getTime());
		          begin = new Timestamp(moring.getTime());
		         
			}
			else
			{
				String date=request.getParameter("date");
				request.setAttribute("date",date);
				SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制 			
				try {
					begin=new Timestamp( sdformat.parse(date+" 00:00:00").getTime());
					end=new Timestamp( sdformat.parse(date+" 23:59:59").getTime());	
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					  				
			}
			List<Fireinfo> list=Fireinfo.findByopenid(openid,begin,end);			
			for(int i=0;i<list.size();i++)
			{
				Fireinfo fireinfo=list.get(i);
				int hours=fireinfo.getFtime().getHours();
				if(hours>=6&&hours<=12)
				{
					morning.add(fireinfo);
				}
				else if (hours>12&&hours<18) {
					afternoon.add(fireinfo);
				}
				else
				{
					night.add(fireinfo);
				}
			}
			request.setAttribute("morning",morning);//早晨吸烟量
			request.setAttribute("afternoon",afternoon);//中午吸烟量
			request.setAttribute("night", night);//下午吸烟量
			request.getRequestDispatcher("/fireinfo.jsp").forward(request, response);				
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
