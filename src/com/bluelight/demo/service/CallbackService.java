package com.bluelight.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;



import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.Daoimpl.Todayinfo;
import com.bluelight.demo.api.DeviceApi;
import com.bluelight.demo.api.MpApi;
import com.bluelight.demo.api.util.HttpUtil;
import com.bluelight.demo.consts.MsgType;
import com.bluelight.demo.consts.XmlResp;
import com.bluelight.demo.mock.DBMock;
import com.bluelight.demo.protocol.BlueLight;
import com.bluelight.demo.protocol.BlueLight.CmdId;
import com.bluelight.entry.BoundInfo;
import com.bluelight.entry.SNSUserInfo;
import com.bluelight.entry.WxTemplate;


/**
 * 回调业务处理
 */
public class CallbackService 
{
	// 自定义菜单中的key值
	public static final String V1001_LIGHT_ON = "V1001_LIGHT_ON";//点灯
	public static final String V1002_LIGHT_OFF = "V1002_LIGHT_OFF";//灭灯
	
	public String handle(Map<String, String> reqMap) throws Exception {
		String msgType = reqMap.get("MsgType");
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		
System.out.println("msgType:"+msgType);
		// 针对不同类型的消息和事件进行处理

		// 文本消息
		if (MsgType.TEXT.equals(msgType)) {
			// 可以在此处进行关键字自动回复
			//String content = "收到文本消息：" + reqMap.get("Content");
			String content=reqMap.get("Content");
			 if (content.startsWith("限制点火"))
			 {
				 String keyWord = content.replaceAll("^限制点火", "").trim(); 
				 System.out.println(keyWord);
				 if ("".equals(keyWord)) {  
					 content="您没有输入限制点火次数";
			        }
				 else
				 {
					 BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						
						lightResp="(DTM:LMT-"+keyWord+",ACN-3)";
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content1 = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content1);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息
							
							String debugText = "已发送限制点火："+keyWord+"次指令，请等待服务器响应";
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                    
				 }
			 }
			 content += "收到文本消息：";
			return XmlResp.buildText(fromUser, toUser, content);
		}
		
		// 基础事件推送
		if (MsgType.EVENT.equals(msgType)) {
			String event = reqMap.get("Event");
			// 关注公众号
			if(MsgType.Event.SUBSCRIBE.equals(event)) {
				// 回复欢迎语           
		       return XmlResp.buildNewsMessage(fromUser,toUser,"您好,欢迎关注lightir乐驼智能打火机", 
						"请先完善个人资料,开始一段控烟、戒烟之旅.在这里,您将注定不平凡",
						"http://www.wsren.cn/demo/dhj/images/guanzhu.jpg",
						"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464d8754fb3521b3&redirect_uri=http%3A%2F%2Fwww.wsren.cn%2Fdemo%2Fservlet%2FGetopenServlet&response_type=code&scope=snsapi_userinfo&state=users#wechat_redirect");
			}
			if (MsgType.Event.UNSUBSCRIBE.equals(event)) {
				//取消关注			
			}
			if (MsgType.Event.LOCATION.equals(event)) {
				// 上报地理位置
				System.out.println("更新坐标");
				Double la =Double.valueOf(reqMap.get("Latitude"));
				Double ln =Double.valueOf(reqMap.get("Longitude"));
				SNSUserInfodaoimpl snsUserInfo=new SNSUserInfodaoimpl();
				SNSUserInfo UserInfo=snsUserInfo.findByopenid(fromUser);
				if(UserInfo!=null)
				{
				UserInfo.setLa(la);
				UserInfo.setLn(ln);
				snsUserInfo.updateLocation(UserInfo);
				}
			}
			// 菜单点击事件
			if (MsgType.Event.CLICK.equals(event)) {
				// 根据key值判断点击的哪个菜单
				String eventKey = reqMap.get("EventKey");				
				  if (eventKey.equals("11")) {  
						// 根据 fromUserName 获取绑定的信息
					  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);
						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();						
						// 构造设备消息						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						lightResp="(DTM:CLK-"+df.format(new Date()).toString()+")";  
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备

						DeviceApi.transMsg(deviceType, deviceID, openID, content);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息
							
							String debugText = "设备：" + deviceID +"已发送同步系统时间指令，等待响应中！" ;
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                  } else if (eventKey.equals("12")) { 
              		// 根据 fromUserName 获取绑定的信息
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						lightResp="(DTM:INF)";
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息
							
							String debugText = "已发送获取系统信息指令，请等待响应：";
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                      
                  } else if (eventKey.equals("21")) {  
                		// 根据 fromUserName 获取绑定的信息
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						lightResp="(DTM:OFF-D)";						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content);						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息							
							String debugText =   "设备" + deviceID + "已发送获取离线点火信息指令，请等待响应";
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}                      
                  } else if (eventKey.equals("22")) {  
                	// 根据 fromUserName 获取绑定的信息
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						lightResp="(DTM:CLN-ALL)";
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息
							
							String debugText = "已发清空离线数据指令,等待响应信息" ;
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                  }
                  else if (eventKey.equals("23")) {  
                	  StringBuffer buffer = new StringBuffer();  
                	  buffer.append("限制点火使用指南").append("\n"); 
                	  buffer.append("请按照以下格式在文本框中输入指令").append("\n");   
                	  buffer.append("限制点火 6").append("\n");						
					return XmlResp.buildText(fromUser, toUser,  buffer.toString());                  
                  }
                  else if (eventKey.equals("24")) {
                  	// 根据 fromUserName 获取绑定的信息
                  	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

  						// 未绑定
  						if (boundInfo == null) {
  							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
  						}

  						String deviceType = boundInfo.getDeviceType();
  						String deviceID = boundInfo.getDeviceID();
  						String openID = boundInfo.getOpenID();  						
  						// 构造设备消息  						
  						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
  						
  						String lightResp;
  						lightResp="(DTM:FEN-0)";		
  						byte[] respRaw = lightResp.getBytes();
  						// Base64编码
  						final String content = Base64.encodeBase64String(respRaw);  						
  						// 推送消息给设备
  						DeviceApi.transMsg(deviceType, deviceID, openID, content);  						
  						// 回复
  						boolean debug = true;
  						if(debug){
  							// 返回调试信息
  							
  							String debugText = "已发禁止点火指令,等待响应信息" ;
  							return XmlResp.buildText(fromUser, toUser, debugText);
  						}else{
  							return "";
  						}
                    }
                  else if (eventKey.equals("25")) {  
                    	// 根据 fromUserName 获取绑定的信息
                    	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

    						// 未绑定
    						if (boundInfo == null) {
    							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
    						}
    						String deviceType = boundInfo.getDeviceType();
    						String deviceID = boundInfo.getDeviceID();
    						String openID = boundInfo.getOpenID();
    						
    						// 构造设备消息
    						
    						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
    						
    						String lightResp;
    						lightResp="(DTM:FEN-1)";
    						byte[] respRaw = lightResp.getBytes();
    						// Base64编码
    						final String content = Base64.encodeBase64String(respRaw);
    						
    						// 推送消息给设备
    						DeviceApi.transMsg(deviceType, deviceID, openID, content);
    						
    						// 回复
    						boolean debug = true;
    						if(debug){
    							// 返回调试信息
    							
    							String debugText = "已发允许点火指令,等待响应信息" ;
    							return XmlResp.buildText(fromUser, toUser, debugText);
    						}else{
    							return "";
    						}
                      }
                  else if (eventKey.equals("31")) {  
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);
						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						
						
						String lightResp;
						lightResp="(DTM:ALS-1)";
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息							
							String debugText = "已发送允许发声指令,等待响应中";
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                      
                  }
				  
                  else if (eventKey.equals("32")) {  
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
						
						// 构造设备消息
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
						
						String lightResp;
						lightResp="(DTM:ALS-0)";
								
						
						byte[] respRaw = lightResp.getBytes();
						// Base64编码
						final String content = Base64.encodeBase64String(respRaw);
						
						// 推送消息给设备
						DeviceApi.transMsg(deviceType, deviceID, openID, content);
						
						// 回复
						boolean debug = true;
						if(debug){
							// 返回调试信息
							
							String debugText = "已经发送禁止发声指令,等待响应中";
							return XmlResp.buildText(fromUser, toUser, debugText);
						}else{
							return "";
						}
                    
                }
                  else if (eventKey.equals("33")) {  
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);

						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
					
					// 构造设备消息
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
					
					String lightResp;
					lightResp="(DTM:RST)";
							
					
					byte[] respRaw = lightResp.getBytes();
					// Base64编码
					final String content = Base64.encodeBase64String(respRaw);
					
					// 推送消息给设备
					DeviceApi.transMsg(deviceType, deviceID, openID, content);
					
					// 回复
					boolean debug = true;
					if(debug){
						// 返回调试信息
						
						String debugText = "已发送恢复出厂设置指令，等待响应中";
						return XmlResp.buildText(fromUser, toUser, debugText);
					}else{
						return "";
					}
                  }
                  else if (eventKey.equals("34")) {  
                	  BoundInfo boundInfo = DBMock.queryBoundInfo(fromUser);
						// 未绑定
						if (boundInfo == null) {
							return XmlResp.buildText(fromUser, toUser, "蓝牙连接异常！未能获取绑定信息");
						}

						String deviceType = boundInfo.getDeviceType();
						String deviceID = boundInfo.getDeviceID();
						String openID = boundInfo.getOpenID();
					
					// 构造设备消息
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式					
					String lightResp;
					lightResp="(DTM:OFF-W)";				
					byte[] respRaw = lightResp.getBytes();
					// Base64编码
					final String content = Base64.encodeBase64String(respRaw);
					
					// 推送消息给设备
					DeviceApi.transMsg(deviceType, deviceID, openID, content);
					
					// 回复
					boolean debug = true;
					if(debug){
						// 返回调试信息						
						String debugText = "已发送获取周离线数据指令，等待响应中";
						return XmlResp.buildText(fromUser, toUser, debugText);
					}else{
						return "";
					}
                  }
                  else if (eventKey.equals("Sb")) {
                	  
                	  Todayinfo todayinfo=new Todayinfo();
                	 String url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
              		JSONObject json = new JSONObject();
              		json.put("template_id_short", "OPENTM207350032");
              		String a=HttpUtil.doPost(url,json.toString());
              		JSONObject obj=JSONObject.fromObject(a);
              		System.out.println("a:"+a);
              	    String template_id=obj.getString("template_id");
              		WxTemplate t = new WxTemplate();
              	//	t.setUrl("http://www.wsren.cn/demo/servlet/FireinfoSelvet?openId="+fromUser);
 String sb1="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx464d8754fb3521b3&redirect_uri=http%3A%2F%2Fwww.wsren.cn%2Fdemo%2Fservlet%2FGetopenServlet&response_type=code&scope=snsapi_userinfo&state=fire#wechat_redirect";             		
                    t.setUrl(sb1);
 					t.setTouser(fromUser);
              		t.setTopcolor("#44B549");
              		t.setTemplate_id(template_id);
              		t.setData(todayinfo.dayinfo(fromUser));
              		String url11="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
            		System.out.println(JSONObject.fromObject(t).toString());
            		HttpUtil.doPost(url11, JSONObject.fromObject(t).toString());                	             	                  	  
                  }
						
			}
		}	
		// 设备消息或事件
		if (MsgType.DEVICE_EVENT.equals(msgType)
				|| MsgType.DEVICE_TEXT.equals(msgType)) {
			String reqContent = reqMap.get("Content");
			String deviceType = reqMap.get("DeviceType");
			String deviceID = reqMap.get("DeviceID");
			String sessionID = reqMap.get("SessionID");
			final String openID = reqMap.get("OpenID");
			System.out.println(reqContent);
			// 设备事件推送
			if (MsgType.DEVICE_EVENT.equals(msgType)) {
				String event = reqMap.get("Event");
				System.out.println("event:"+event);
				// 绑定/解绑事件
				if (MsgType.DeviceEvent.BIND.equals(event)
						|| MsgType.DeviceEvent.UNBIND.equals(event)) {
					// 存储用户和设备的绑定关系
					if(MsgType.DeviceEvent.BIND.equals(event)){
						BoundInfo boundInfo = DBMock.queryBoundInfo(reqMap.get("FromUserName"));
								if(boundInfo==null)
										DBMock.saveBoundInfo(reqMap);
								else{
									   DBMock.updateBoundInfo(reqMap);
								}
					}else{
						DBMock.removeBoundInfo(reqMap.get("FromUserName"));
					}
					// 设备绑定/解绑事件可以回复空包体
					return "";
				}
			}
//			// 收到设备消息
			if (MsgType.DEVICE_TEXT.equals(msgType)) {
				System.out.println("reqContent:    "+reqContent);				
			//byte[] reqRaw = Base64.decodeBase64(reqContent.getBytes());	
		     byte[] reqRaw=com.bluelight.demo.protocol.Base64.decode(reqContent);
			 String x1 = new String(reqRaw);					
					String transText = "";					
					byte[] reqTextRaw = transText.getBytes("UTF-8");
					if (reqTextRaw.length > 0 && reqTextRaw[reqTextRaw.length - 1] == 0) {
						// 推送给微信用户的内容去掉末尾的反斜杠零'\0'
						transText = transText + new String(reqTextRaw, 0, reqTextRaw.length -  1, "UTF-8");
					} else{
//						transText = transText + reqText;
					}				
					// 推送文本消息给微信
					
//					
					String lightResp="";
					if(x1.equals("MTD:Connected"))
					{
						 String lightResp1="(DTM:INF)";
						 byte[] respRaw = lightResp1.getBytes();// Base64编码
						 final String content1 = Base64.encodeBase64String(respRaw);						
						 DeviceApi.transMsg(deviceType, deviceID, openID, content1);// 推送消息给设备						
						transText=x1;	
						MpApi.customSendText(openID, transText);
					}
					else
					{	
						System.out.println(x1);
						if(x1.substring(0, x1.indexOf("-")).equals("(MTD:CLK"))
						{
							transText="当前系统信息为\n"+BlueLight.parsesys(x1,openID);
						}
						else if(x1.substring(0, x1.indexOf("-")).equals("(MTD:OCD"))
						{
							System.err.println("得到的点火信息为："+x1);
							transText=BlueLight.savefire(x1,openID);
						}
						else if(x1.substring(0, x1.indexOf("-")).equals("(MTD:OCN"))
						{							
							transText="查询到："+x1.substring(x1.length()-2, x1.length()-1)+"条离线点火信息";
						}
						else if(x1.indexOf("(MTD:OWD")>-1)
						{							
							transText=x1;
						}
						else if(x1.indexOf("(MTD:ORD")>-1)
						{							
							transText=x1;
						}
						else if(x1.equals("(MTD:RTN-OK)"))
						{
						 transText ="设置成功";
						}
						else if(x1.equals("((MTD:RTN-ERROR))"))
						{
						transText ="下发指令失败，请检查打火机与手机的连接";
						}
						MpApi.customSendText(openID, transText);
						// demo中 回复 收到的内容给设备
						lightResp="";						
					}
					// 序列化
					byte[] respRaw = lightResp.getBytes();
					// Base64编码
					String respCon = Base64.encodeBase64String(respRaw);					
					// 设备消息接口必须回复符合协议的xml
					return XmlResp.buildDeviceText(toUser, fromUser, deviceType, deviceID, respCon, sessionID);
				}						
		}		
		// 未处理的情况返回空字符串
		return "";				
	}
}
