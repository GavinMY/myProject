package com.bluelight.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;


import org.json.JSONException;
import org.json.JSONObject;

import com.bluelight.demo.api.util.HttpUtil;

public class DownloadImage 
{

	public Boolean down(String openid, String url,String filepath)
	{
		Boolean status=false;
//	  String str="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//	  str=str.replaceAll("OPENID", openid);
//	  String info=HttpUtil.doGet(str);
//	  String url=null;
//	  try {
//		JSONObject obj = new JSONObject(info);
//		   url=obj.getString("headimgurl");
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	  System.out.println(url);
			
//			 url = "http://wx.qlogo.cn/mmopen/ZvUYvDZIrcTVQy2tg4SibUiaFHmPVGH1Y2LNBaNb7Z5ndMYliaYeiaTWydMKq1HpvRcVS0Opbfdk4ZuywJew2HnJuPrAqibQEYgvs";  
		        byte[] btImg = getImageFromNetByUrl(url);  
		        if(null != btImg && btImg.length > 0){  
		            System.out.println("读取到：" + btImg.length + " 字节");  
		            String fileName = openid+".gif";  
		            writeImageToDisk(btImg, fileName,filepath+"/"+fileName); 
		            status=true;
		        }else{  
		            System.out.println("没有从该连接获得内容");  
		        }  
		        return status;
	}
	
	 /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
    public static void writeImageToDisk(byte[] img, String fileName,String filepath){  
        try {  
        	System.out.println("filepath:"+filepath);
            File file = new File(filepath);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
            System.out.println("图片已经写入");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
}
