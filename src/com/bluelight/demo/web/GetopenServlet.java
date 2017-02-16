package com.bluelight.demo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelight.Daoimpl.CigaretteDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.consts.WxConfig;
import com.bluelight.demo.util.AdvancedUtil;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.entry.WeixinOauth2Token;
import com.bluelight.entry.cigarette;

public class GetopenServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetopenServlet() {
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
	 * This method is called when a form has its tag value method equals to post.
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  HttpSession session=request.getSession();	
		  String filepath=request.getSession().getServletContext().getRealPath("/dhj/images"); 
		  String state=request.getParameter("state");
		  SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
		  CigaretteDaoimpl cigaretteDao=new CigaretteDaoimpl();
		  String openId =null;
		  if(null==session.getAttribute("openId"))//如果session里没有openId则去授权获取
		  {
				 // 用户同意授权后，能获取到code
				String code = request.getParameter("code");
				if (!"authdeny".equals(code)) 
				{	
					
					// 获取网页授权access_token
					WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WxConfig.APPID, WxConfig.APPSECRET, code);
					// 网页授权接口访问凭证
					String accessToken = weixinOauth2Token.getAccessToken();
					// 用户标识
					 openId = weixinOauth2Token.getOpenId();
					 session.setAttribute("openId", openId);					  
					SNSUserInfo snsUserInfo=Userdaoimpl.findByopenid(openId);
					
						 if(null==snsUserInfo)//如果用户无设备
						 {
//							 System.out.println("null==snsUserInfo");
//							 // 获取用户信息
//							 snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,openId,filepath);
//							 Userdaoimpl.add(snsUserInfo);	 
							 session.removeAttribute("openId");
							 request.getRequestDispatcher("/tip.html").forward(request, response);	
							 return;
						 }					 
						 else if(null==snsUserInfo.getNickname())
							{
								System.out.println("null==snsUserInfo.getNickname");
								// 获取用户信息
								snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,openId,filepath);
								Userdaoimpl.wechatupdate(snsUserInfo);
								cigarette mycigarette=new cigarette(15, 8, 0.8,20,20,openId);//默认此时创建香烟信息		
								
								cigaretteDao.add(mycigarette);								
						}					 							 
				}
				else
				{
					// 跳转到index.jsp
					request.getRequestDispatcher("/notallow.jsp").forward(request, response);	
					return;
				}
			}
			else
			{
			 openId=session.getAttribute("openId").toString();
			}
			if(state.equals("data"))
			{
				request.getRequestDispatcher("/servlet/DataServlet").forward(request, response);
				return;
			}
			else if(state.equals("equipment"))
			{
				request.getRequestDispatcher("/servlet/EquipmentServlet").forward(request, response);	
				return;
			}
			else if(state.equals("fire"))
			{
				request.getRequestDispatcher("/servlet/FireinfoSelvet").forward(request, response);	
				return;
			}
			else if(state.equals("users"))
			{			
			SNSUserInfo user=Userdaoimpl.findByopenid(openId);
			//设置要传递的参数
			request.setAttribute("snsUserInfo",user);
			request.getRequestDispatcher("/home.jsp").forward(request,response);	
			return;
			}
			else if(state.equals("ycjk"))
			{
				request.getRequestDispatcher("/servlet/YcjkServlet").forward(request,response);
				
				
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
