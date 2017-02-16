package com.bluelight.demo.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bluelight.Daoimpl.BoundInfoDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.entry.BoundInfo;
import com.bluelight.entry.SNSUserInfo;

/**
 * 模拟 DB 保存用户和设备绑定信息
 * <p>
 * 实际中需要使用数据库持久化
 */
public class DBMock {

	private static Map<String, Map<String, String>> bound = new ConcurrentHashMap<String, Map<String, String>>();
	static BoundInfoDaoimpl boundInfoimpl=new BoundInfoDaoimpl();
	
	/**
	 * 根据用户id获取绑定的设备id和设备类型
	 * fromUserName,deviceID,openID,deviceType
	 */
	public static BoundInfo queryBoundInfo(String DeviceID) {
//		Map<String, String> boundInfo = bound.get(fromUserName);
//		System.out.println("queryBoundInfoBy=" + boundInfo);
		BoundInfo boundInfo=boundInfoimpl.findbyFromUserName(DeviceID);
		return boundInfo;
	}

	/**
	 * 保存绑定关系
	 */
	public static void saveBoundInfo(Map<String, String> reqMap) {
		System.out.println("正在尝试绑定关系saveBoundInfo=" + reqMap);
		SNSUserInfodaoimpl SNSUserdao=new SNSUserInfodaoimpl();
		/**
		 * 官方提供的绑定方法
		 */
//		Map<String, String> boundInfo = new HashMap<String, String>();
//		boundInfo.put("fromUserName", reqMap.get("FromUserName"));
//		boundInfo.put("deviceID", reqMap.get("DeviceID"));
//		boundInfo.put("openID", reqMap.get("OpenID"));
//		boundInfo.put("deviceType", reqMap.get("DeviceType"));
//		bound.put(reqMap.get("FromUserName"), boundInfo);
		
		/**
		 * 绑定信息到DB
		 */	
		BoundInfo boundInfo=new BoundInfo();
		boundInfo.setFromUserName(reqMap.get("FromUserName"));
		boundInfo.setDeviceID(reqMap.get("DeviceID"));
		boundInfo.setDeviceType(reqMap.get("DeviceType"));
		boundInfo.setOpenID(reqMap.get("OpenID"));
		SNSUserInfodaoimpl Userdaoimpl=new SNSUserInfodaoimpl();
		SNSUserInfo snsUserInfo=Userdaoimpl.findByopenid(reqMap.get("OpenID"));
		if(null==snsUserInfo)
		{
		SNSUserdao.addByopenid(reqMap.get("OpenID"));
		}
		boolean staue= boundInfoimpl.add(boundInfo);
		if(staue)
			System.out.println("成功绑定信息");
		
	}
	public static void updateBoundInfo(Map<String, String> reqMap) {
		BoundInfo boundInfo=new BoundInfo();
		boundInfo.setFromUserName(reqMap.get("FromUserName"));
		boundInfo.setDeviceID(reqMap.get("DeviceID"));
		boundInfo.setDeviceType(reqMap.get("DeviceType"));
		boundInfo.setOpenID(reqMap.get("OpenID"));
		boolean staue=boundInfoimpl.update(boundInfo);
		if(staue)
		{
			System.out.println("绑定信息已更新");
		}
	}
	/**
	 * 删除绑定关系
	 */
	public static void removeBoundInfo(String fromUserName) {
		System.out.println("正在删除绑定关系：" + fromUserName);
		boundInfoimpl.dete(fromUserName);
//		bound.remove(fromUserName);
	}

}
