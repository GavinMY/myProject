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

import com.bluelight.Daoimpl.CigaretteDaoimpl;
import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.consts.WxConfig;
import com.bluelight.demo.util.AdvancedUtil;
import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.entry.WeixinOauth2Token;
import com.bluelight.entry.cigarette;

public class YaoqingServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public YaoqingServlet() {
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
		  String filepath=request.getSession().getServletContext().getRealPath("/dhj/images"); 
		  FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
		  String state=request.getParameter("state");
		  SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
		  CigaretteDaoimpl cigaretteDao=new CigaretteDaoimpl();
		  String openId =null;
		  SNSUserInfo snsUserInfo=null;
		  SNSUserInfo user=null;
		  Boolean flg=false;
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
									 snsUserInfo=Userdaoimpl.findByopenid(openId);
									 System.out.println("state:"+state);
									 user=Userdaoimpl.findByopenid(state);//有设备的用户										
										 if(null==snsUserInfo)//没有查询到，代表未注册用户,立即获取信息，完成注册
										 {
											 System.out.println("null==snsUserInfo");
											 // 获取用户信息
											 snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,openId,filepath);
											 Userdaoimpl.add(snsUserInfo);	 
											 cigarette mycigarette=new cigarette(10, 0.5, 0.5,20,20,openId);//默认此时创建香烟信息								
											 cigaretteDao.add(mycigarette);	
										 }	
										
											 if(null==user.getFamily())//判断要添加的用户是否已经被其他人绑定，为null，没添加则立即添加
											 {
											 user.setFamily(openId);
											 flg= Userdaoimpl.updateUserFamily(user);
											 }
											 else
											 {
											request.getRequestDispatcher("/alreadyaoqing.jsp").forward(request, response);
											return;
											 }
										 
								}
								else//不同意授权
								{
									// 跳转到index.jsp
									request.getRequestDispatcher("/notallow.jsp").forward(request, response);	
									return;
								}
							}
			else
			{
			 openId=session.getAttribute("openId").toString();
			 user=Userdaoimpl.findByopenid(state);//有设备的用户			
			 if(null==user.getFamily())
			 {
			 user.setFamily(openId);
			 flg= Userdaoimpl.updateUserFamily(user);
			 }
			 else
			 {
				 flg=false; 
			 }
			}
		  if(flg)//绑定信息成功，则查询数据
		  {
			  SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//24小时制 
			  Date now=new Date();
			  request.setAttribute("date",sdformat.format(now));
	          Date moring = getTimesmorning();
	          Timestamp end = new Timestamp(now.getTime());
	          Timestamp begin = new Timestamp(moring.getTime());
	          List<Fireinfo> list=Fireinfo.findByopenid(state,begin,end);
	          request.setAttribute("ycjk", list);
	          request.setAttribute("uname", user.getNickname());
	      	request.getRequestDispatcher("/yaoqingSccuess.jsp").forward(request, response);	
	      	return;
		  }
		  else
		  {
			  request.getRequestDispatcher("/alreadyaoqing.jsp").forward(request, response);
			  return;
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
