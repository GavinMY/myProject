package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.bluelight.entry.WeixinOauth2Token;
import com.bluelight.entry.cigarette;
import com.bluelight.tools.FindDate;

public class DataServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DataServlet() {
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
		   request.setCharacterEncoding("utf-8");
		   FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
		   FindDate Findate=new FindDate();
		   CigaretteDaoimpl  cigaretteDaoimpl=new CigaretteDaoimpl();
		   SNSUserInfodaoimpl UserInfodao=new SNSUserInfodaoimpl();
//		   //用户同意授权后，能获取到code
//			String code = request.getParameter("code");
//	    	// 获取网页授权access_token
//			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WxConfig.APPID, WxConfig.APPSECRET, code);
//			// 用户标识
//			String openid = weixinOauth2Token.getOpenId();   	
			
			String openid=session.getAttribute("openId").toString();
		    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制 
		  //查询用户控烟目标
		//	int target=UserInfodao.findtarget(openid);
			//查询香烟信息
			cigarette Cigarette=cigaretteDaoimpl.findByopenid(openid);
		      //查询本月信息			
	         Timestamp endmouths=new Timestamp(Findate.getTimesMonthnight().getTime());//本月结束时间
	         Timestamp startmouths=new Timestamp(Findate. getTimesMonthmorning().getTime());//本月开始时间      
			Map map2=Fireinfo.findBymouthinfo(openid, startmouths, endmouths);
			
			int mouthsum=Integer.valueOf(map2.get("mouthsum").toString());
			map2.remove("mouthsum");
			Map mouthmap=new  LinkedHashMap<String,Integer>();
			java.util.Iterator it = map2.entrySet().iterator();
			int i=0;
			while(it.hasNext())
			{
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			String[] a=entry.getKey().toString().split(",");
//			if(i==0)
//			{
//	           mouthmap.put((Findate. getTimesMonthmorning().getMonth()+1)+"."+Findate. getTimesMonthmorning().getDate()+"-"+Findate.getEndDayOfWeekNo(Integer.valueOf(a[0]),Integer.valueOf(a[1])), entry.getValue());
//			}
//			else
	         mouthmap.put(Findate.getStartDayOfWeekNo(Integer.valueOf(a[0]),(Integer.valueOf(a[1])+1))+"-"+Findate.getEndDayOfWeekNo(Integer.valueOf(a[0]),(Integer.valueOf(a[1])+1)),entry.getValue());
			 i++;
			}
			
			//查询本周信息
			
			Timestamp beginweek=new Timestamp(Findate.getTimesWeekmorning().getTime());
			Timestamp endweek=new Timestamp(Findate.getTimesWeeknight().getTime());
			Map weekmap=Fireinfo.findByzhouinfo(openid, beginweek, endweek);	        
	        int  sum=Integer.valueOf(weekmap.get("sum").toString());
	        weekmap.remove("sum");
	        
	       
	       //为没有数据的日信息加入0
	         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		     Date currentDate = new Date();
	         List<Date> days = dateToWeek(currentDate);
	        if(days.size()==0)
	        {
	        	 currentDate.setDate(currentDate.getDate()-1);
	        	 days = dateToWeek(currentDate);
	        	 currentDate.setDate(currentDate.getDate()+1);
	        	 days.add(currentDate);
	        }
	         Map week=new LinkedHashMap();
	         for (Date date : days)
	         {
	        	 
	        	 if(null==weekmap.get(sdf.format(date)))
	        	 {
	        		 week.put(sdf.format(date), 0) ;
	        	 }
	        	 else
	        	 {
	        		 week.put(sdf.format(date),weekmap.get(sdf.format(date))) ;
	        	 }
	         }
	       
			//为没有数据的周信息加入0
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH)+1;//获取月份
			List list=getWeeks(year,month);
			Map map1=new  LinkedHashMap<String,Integer>();
			for(int j=0;j<list.size();j++)
			{
					if(null==mouthmap.get(list.get(j)))	
					{
					map1.put(list.get(j), 0);
					}
					else
					{
					map1.put(list.get(j),mouthmap.get(list.get(j)));
					}
			}
			
			
			if(null==Cigarette)
			{
				request.setAttribute("Cigarette", null);
				request.setAttribute("target",0);//用户控烟目标
			}
			else
			{				
				request.setAttribute("Cigarette", Cigarette);
				request.setAttribute("target",Cigarette.getCtarget());//用户控烟目标
			}			
			request.setAttribute("mouthmap", map1);//本月吸烟信息
			request.setAttribute("weekmap", week);//本月吸烟信息
			request.setAttribute("mouthsum", mouthsum);//本月吸烟信息
			request.setAttribute("weeksum", sum);//本日吸烟信息			
			request.getRequestDispatcher("/data.jsp").forward(request, response);
	}	
	//本月的所有周
	public static List getWeeks(int year,int month )
	{
		List list=new ArrayList();
		   Calendar c = Calendar.getInstance();
	        c.set(year, month-1, 1);
	        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int dayinweek = c.get(Calendar.DAY_OF_WEEK);
	        dayinweek = dayinweek==1?7:dayinweek-1;
	        int weekcount = 1;	      
	        String a="";
	        for (int i=1; i<=days; i++) {
	            if (i==1 || (i-1+dayinweek)%7==1)
	            	a= month + "." + i;      
	            if (i==days || (i-1+dayinweek)%7==0) {	   	
	              a+="-" + month + "." + i;          	
	              list.add(a);
	              a="";
	              weekcount++;	            
	            }
	        }
		return list;
	}
	//本周今日之前的日期
	 public static List<Date> dateToWeek(Date mdate) {
	        int b = mdate.getDay();
	        Date fdate;
	        List<Date> list = new ArrayList<Date>();
	        Long fTime = mdate.getTime() - b * 24 * 3600000;
	        for (int a = 1; a <= 7; a++) {
	            fdate = new Date();
	            fdate.setTime(fTime + (a * 24 * 3600000));
	            if(!fdate.after(mdate))
	            list.add(a-1, fdate);
	        }
	        return list;
	    }
    //查询前几周的日期
	public static  String Monday(int n)
	{
		 Calendar cal = Calendar.getInstance();
		   //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推		
		   String monday;
		   cal.add(Calendar.DATE, n*7);
		   //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		   cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		   monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		   return monday+" 00:00:00";
	}
	//查询前几周周日的时间
	public static  String Sunday(int n)
	{
		 Calendar cal = Calendar.getInstance();
		   //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推		
		   String monday;
		   cal.add(Calendar.DATE, n*7);
		   //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		   cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		   monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		   return monday+" 23:59:59";
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
