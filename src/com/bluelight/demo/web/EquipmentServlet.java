package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.bluelight.Daoimpl.CigaretteDaoimpl;
import com.bluelight.Daoimpl.EquipmentDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.api.DeviceApi;
import com.bluelight.demo.consts.Equipment;
import com.bluelight.demo.consts.XmlResp;
import com.bluelight.demo.mock.DBMock;
import com.bluelight.entry.BoundInfo;
import com.bluelight.entry.cigarette;

public class EquipmentServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public EquipmentServlet() {
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
		
		if(null==request.getParameter("ctarget"))
		{
		 String fromUser=request.getParameter("openid");
		 if(null==fromUser)
		 {
			 fromUser=session.getAttribute("openId").toString();
		 }
		/*
		 * 发送获取系统信息请求，更新设置
		 * */
		 System.out.println(fromUser);
		 BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);
		 if(null!=boundInfo)
		 {
			 //System.out.println("没有查询到你与设备的绑定信息");
			 String deviceType = boundInfo.getDeviceType();
			 String deviceID = boundInfo.getDeviceID();
			 String openID = boundInfo.getOpenID();
			 String lightResp="(DTM:INF)";
			 byte[] respRaw = lightResp.getBytes();// Base64编码			
			 final String content1 = Base64.encodeBase64String(respRaw);						
			 DeviceApi.transMsg(deviceType, deviceID, openID, content1);// 推送消息给设备
		 }		 		 
			/*
			 * 获取系统信息
			 * */
		 EquipmentDaoimpl EquipmentDao=new EquipmentDaoimpl();
		 Equipment equipment=EquipmentDao.findByopenid(fromUser);
		 if(null==equipment)
		 {
			 request.getRequestDispatcher("/tip.html").forward(request, response); 
			 return;
		 }
		 CigaretteDaoimpl CigaretteDao=new CigaretteDaoimpl();
		 Map map=CigaretteDao.selectEquipment(fromUser);		
		 request.setAttribute("equipment", equipment);
		 request.setAttribute("map",map);
		 System.out.println("map:"+map.get("count"));
		 request.setAttribute("openid",fromUser);
		 request.getRequestDispatcher("/sbxx.jsp").forward(request, response);
		 return;
		}
		else
		{
			String openid=request.getParameter("openid");
			 if(null==openid)
			 {
				 openid=session.getAttribute("openId").toString();
			 }
			Double cprice=Double.valueOf(request.getParameter("cprice"));
			Double ctar=Double.valueOf(request.getParameter("ctar"));
			
			int count=Integer.valueOf(request.getParameter("ccount"));
			Double soda=Double.valueOf(request.getParameter("soda"));
			int ctarget=Integer.valueOf(request.getParameter("ctarget"));
//			
			cigarette newcigarete=new cigarette(cprice,ctar,soda,ctarget,count,openid);
			CigaretteDaoimpl CigaretteDao=new CigaretteDaoimpl();
			SNSUserInfodaoimpl userdao=new SNSUserInfodaoimpl();
			cigarette cigarete=CigaretteDao.findByopenid(openid);
				if(null==cigarete)
				{
					CigaretteDao.add(newcigarete);
				}
				else
				{	
				CigaretteDao.updatecigarette(newcigarete);
				}				
				request.getRequestDispatcher("FireinfoSelvet?openid="+openid).forward(request, response);
				 return;
		}
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
