package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.api.util.CommonUtil;
import com.bluelight.demo.api.util.PastUtil;
import com.bluelight.demo.consts.WxConfig;
import com.bluelight.entry.SNSUserInfo;

public class InvitationServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InvitationServlet() {
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
		    SNSUserInfo qinqing=null;
		    SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();	
		    String openId=session.getAttribute("openId").toString();
		    SNSUserInfo user=Userdaoimpl.findByopenid(openId);
		  
		    if(null!=user.getFamily())
		    {
		    	 qinqing=Userdaoimpl.findByopenid(user.getFamily());
		    	  System.out.println(qinqing.getNickname());
		    	 request.setAttribute("uname", qinqing.getNickname());
		    }
		    else
		    {
		    	request.setAttribute("uname",null);
		    }
		  //  String redict="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464d8754fb3521b3&redirect_uri=http%3A%2F%2Fyujuan1314.oicp.net%2Fdemo%2F%2Fservlet%2FYaoqingServlet&response_type=code&scope=snsapi_userinfo&state="+openId+"#wechat_redirect"	;			 
		    String redict="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464d8754fb3521b3&redirect_uri=http%3A%2F%2Fwww.wsren.cn%2Fdemo%2Fservlet%2FGetopenServlet&response_type=code&scope=snsapi_userinfo&state="+openId+"#wechat_redirect";			 
			   
		    Map signature=PastUtil.getParam(WxConfig.APPID, WxConfig.APPSECRET,request);
		    request.setAttribute("nonceStr", signature.get("nonceStr"));
			request.setAttribute("timestamp", signature.get("timestamp"));
			request.setAttribute("signature", signature.get("signature"));
			request.setAttribute("appId", signature.get("appid"));	
			request.setAttribute("redict", redict);	
			
			request.getRequestDispatcher("/yaoqing.jsp").forward(request, response);			
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
