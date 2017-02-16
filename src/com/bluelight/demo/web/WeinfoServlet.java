package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.entry.SNSUserInfo;

public class WeinfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WeinfoServlet() {
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
		 SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
         if(null==request.getParameter("uage"))
         {
        	 SNSUserInfo snsUserInfo=Userdaoimpl.findByopenid(openid);
        	 System.out.println(snsUserInfo.getUheight());
        	 System.out.println(snsUserInfo.getUweight());
        	 request.setAttribute("snsUserInfo", snsUserInfo);
        	 request.setAttribute("openid", openid);
        	// response.sendRedirect("/usercenter.jsp"); 
        	request.getRequestDispatcher("/usercenter.jsp").forward(request, response);
        	 return;
         }
         else
         {
       Double uheight=Double.valueOf(request.getParameter("uheight")) ;
       Double uweight= Double.valueOf(request.getParameter("uweight")) ;
       int usmoke=Integer.valueOf(request.getParameter("usmoke"));
       int uage=Integer.valueOf(request.getParameter("uage"));    
       SNSUserInfo snsUserInfo=Userdaoimpl.findByopenid(openid);
       snsUserInfo.setUheight(uheight);
       snsUserInfo.setUweight(uweight);
       snsUserInfo.setUsmoke(usmoke);
       snsUserInfo.setAge(uage);
       Userdaoimpl.update(snsUserInfo);
      // response.sendRedirect("/servlet/oauthServlet"); 
       System.out.println("准备跳转到设备页面");
     request.getRequestDispatcher("/servlet/EquipmentServlet?openid="+openid).forward(request, response);
       return;
        }
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
