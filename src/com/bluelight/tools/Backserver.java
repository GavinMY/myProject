package com.bluelight.tools;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.bluelight.demo.api.DeviceApi;
import com.bluelight.demo.mock.DBMock;
import com.bluelight.entry.BoundInfo;

public class Backserver
{
	public Boolean recover(String openid)
	{
	 String	 lightResp="(DTM:RST)"; 
	 BoundInfo boundInfo = DBMock.queryBoundInfo(openid);
	 String deviceType = boundInfo.getDeviceType();
	 String deviceID = boundInfo.getDeviceID();
	 String openID = boundInfo.getOpenID();
	 byte[] respRaw = lightResp.getBytes();// Base64编码			
	 final String content1 = Base64.encodeBase64String(respRaw);						
	String debug=DeviceApi.transMsg(deviceType, deviceID, openID, content1);// 推送消息给设备 
	try {
		JSONObject json=new JSONObject(debug);
		String date=json.getString("ret_info");
		if(date.equals("ok"))
		{
			return true;
		}
	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
	}

}
