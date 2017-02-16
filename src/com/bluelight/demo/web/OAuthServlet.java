package com.bluelight.demo.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.consts.WxConfig;
import com.bluelight.demo.util.AdvancedUtil;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.entry.WeixinOauth2Token;

/**
 * 授权后的回调请求处理
 * 
 * @author meiyu
 * @date 2013-11-12
 */
public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = -1847238807216447030L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session=request.getSession();
		 String openId=null;
		 if(null!=session.getAttribute("openId"))
		 {
			 openId=session.getAttribute("openId").toString();
		 }
		 else
		 {
			 openId=request.getParameter("openId") ;
		 }
		  
		 request.setCharacterEncoding("utf-8");
		 SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
		 SNSUserInfo user=Userdaoimpl.findByopenid(openId);
			//设置要传递的参数
		request.setAttribute("snsUserInfo",user);
		request.getRequestDispatcher("/home.jsp").forward(request,response);	
		
	}
}
