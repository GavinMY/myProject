package com.bluelight.demo.api.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class Addressconvert {
    public static String Address(Double ln, Double la) throws IOException {
    	JSONObject jsonObject = null;
        String address = "";
        try {
            String urlStr=  "http://apis.map.qq.com/ws/geocoder/v1/?location="
            	+la+","+ln
            	+"&key=SWFBZ-JQDKV-X42PC-ULABB-KKN2F-MJFRU&get_poi=0";
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
        
         // 从输入流读取返回内容
			InputStream inputStream = con.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
  
        // 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			
			jsonObject = new JSONObject(buffer.toString());
			JSONObject result=jsonObject.getJSONObject("result");
			 address=result.getString("address");
	         
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return address;
    }
}
