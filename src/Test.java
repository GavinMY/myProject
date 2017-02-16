import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;


import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.demo.api.util.HttpUtil;
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//  String str="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=o3yS0uJ6dIlFj0nHq8-n99H4qA8A&lang=zh_CN";
//  
//  String info=HttpUtil.doGet(str);
//  System.out.println("info:"+info);
//  String url=null;
//  try {
//	JSONObject obj = new JSONObject(info);
//	   url=obj.getString("headimgurl");
//} catch (JSONException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//  System.out.println(url);
		
////		 url = "http://wx.qlogo.cn/mmopen/ZvUYvDZIrcTVQy2tg4SibUiaFHmPVGH1Y2LNBaNb7Z5ndMYliaYeiaTWydMKq1HpvRcVS0Opbfdk4ZuywJew2HnJuPrAqibQEYgvs";  
//	        byte[] btImg = getImageFromNetByUrl(url);  
//	        if(null != btImg && btImg.length > 0){  
//	            System.out.println("读取到：" + btImg.length + " 字节");  
//	            String fileName = "百度.gif";  
//	            writeImageToDisk(btImg, fileName);  
//	        }else{  
//	            System.out.println("没有从该连接获得内容");  
//	        }  		
//	String ourl="http://www.wslc.com.cn/demo/servlet/authServlet";
	//String ourl="http://yujuan1314.oicp.net/demo/servlet/DataServlet";
//  String ourl="http://yujuan1314.oicp.net/demo/servlet/FireinfoSelvet";  
// // String ourl="http://yujuan1314.oicp.net/demo/servlet/oauthServlet";
////String ourl="http://www.wsxian.com/weChat_longhui/FoodDetail.action?mid=1";	
//System.out.println(URLEncodeUTF8(ourl));
//	
//	String url="https://api.weixin.qq.com/device/getqrcode?access_token=ACCESS_TOKEN";
//	String code=HttpUtil.doGet(url);
//	JSONObject codeJson = JSONObject.fromObject(code);
//	System.out.println("codeJson"+codeJson);
//	String ticket = codeJson.getString("qrticket");
//	String deviceid=codeJson.getString("deviceid");
//	System.out.println("deviceid: "+deviceid);
//	System.out.println("ticket: "+ticket);
		
	//	String ourl="http://www.wslc.com.cn/demo/servlet/authServlet";
		String ourl1="http://yujuan1314.oicp.net/demo/servlet/DataServlet";
	  String ourl2="http://yujuan1314.oicp.net/demo/servlet/FireinfoSelvet";  
	 String ourl3="http://yujuan1314.oicp.net/demo/servlet/YaoqingServlet";
	 String url14="http://www.wsren.cn/demo/servlet/GetopenServlet";
//	////String ourl="http://www.wsxian.com/weChat_longhui/FoodDetail.action?mid=1";	
	System.out.println(URLEncodeUTF8(ourl1));	
	System.out.println(URLEncodeUTF8(ourl2));	
	System.out.println(URLEncodeUTF8(ourl3));
	System.out.println(URLEncodeUTF8(url14));	
//	System.out.println(ourl3.indexOf("oauthServlet"));
//		  FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
//		  //查询28天每日总的抽烟量
//		   Date today1=new Date();
//		   Timestamp today = new Timestamp(today1.getTime());		
////		   long t=getTimesmorning().getTime()-24*24*60*60*1000;
//		   Timestamp tt = new Timestamp(t);
//		   Timestamp before28days=  new Timestamp(getTimesmorning().getTime()-24*24*60*60*1000);
//		   for(int i=0;i<24;i++)
//		   {
//			   Timestamp ceshi=  new Timestamp(getTimesmorning().getTime()-i*24*60*60*1000); 
//			   System.out.println(ceshi);
//		   }
//		   System.out.println(before28days);
//		   Fireinfo.findmorefireinfo(before28days, today, "o3yS0uLDTYHDcN0BQIEqiRGYnDaQ");		 
//		   System.out.println(Monday(-1));	
//		   System.out.println(Sunday(0));
		 // getWeek("2015","12");
//		   int year=2016;
//	        int month=1;
//	        Calendar c = Calendar.getInstance();
//	        c.set(year, month-1, 1);
//	        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//	        int dayinweek = c.get(Calendar.DAY_OF_WEEK);
//	        dayinweek = dayinweek==1?7:dayinweek-1;
//	        int weekcount = 1;	      
//	        String a="";
//	        for (int i=1; i<=days; i++) {
//	            if (i==1 || (i-1+dayinweek)%7==1)
//	             //   System.out.print("第" + weekcount + "周 " + month + "." + i );	            	
//	            if(i<10) 
//	            {
//	            	a= month + ".0" + i;
//	            }
//	            else
//	            {
//	            	a= month + "." + i;
//	            }
//	           
//	            if (i==days || (i-1+dayinweek)%7==0) {
//	             //   System.out.print(" - " + month + "." + i);
//	            	if(i<10)
//	            	{
//	                a+=" - " + month + ".0" + i;
//	            	}
//	            	else
//	            	{
//	            		  a+=" - " + month + "." + i;
//	            	}
//	                System.out.println(a);
//	                a="";
//	                weekcount++;	            
//	            }
//	        }	        
//	        getWeeks(2016,1);
//	        // 定义输出日期格式
//	        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//	         
//	        Date currentDate = new Date();
//         currentDate.setDate(currentDate.getDate()-1);
////	        // 比如今天是2012-12-25
//	        List<Date> days = dateToWeek(currentDate);
//	        System.out.println(days.size());
//	        
//	        System.out.println("今天的日期: " + sdf.format(currentDate));
//	       
//	        for (Date date : days) {	        	
//	            System.out.println(sdf.format(date));	        	
//	        }
		   System.out.println(getTimesWeekmorning());
		   System.out.println(getTimesWeeknight());
		   
		   System.out.println(getStartDayOfWeekNo(2016,02));
		   System.out.println(getEndDayOfWeekNo(2016,02));
	}
	
    public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return (cal.get(Calendar.MONTH) + 1) + "." +
               cal.get(Calendar.DAY_OF_MONTH);    
        
    }
    
    /**
     * get the end day of given week no of a year.
     * @param year
     * @param weekNo
     * @return
     */
    public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return (cal.get(Calendar.MONTH) + 1) + "." +
               cal.get(Calendar.DAY_OF_MONTH);    
    }
    /**
     * get Calendar of given year
     * @param year
     * @return
     */
    private static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return  cal.getTime();
	}

	// 获得本周日24点时间
	public  static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}
	 public static List<Date> dateToWeek(Date mdate) {
	        int b = mdate.getDay();
	        Date fdate;
	        List<Date> list = new ArrayList<Date>();
	        Long fTime = mdate.getTime() - b * 24 * 3600000;
	        for (int a = 1; a <= 7; a++) {
	            fdate = new Date();
	            fdate.setTime(fTime + (a * 24 * 3600000));
	            System.out.println(fdate);
	            if(!fdate.after(mdate))
	            list.add(a-1, fdate);
	        }
	        return list;
	    }
	public static List getWeeks(int year,int month )
	{
		List list=new ArrayList();
		   Calendar c = Calendar.getInstance();
	        c.set(year, month-1, 1);
	        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int dayinweek = c.get(Calendar.DAY_OF_WEEK);
	        dayinweek = dayinweek==1?7:dayinweek-1;
	        int weekcount = 1;	      
	        String a="";
	        for (int i=1; i<=days; i++) {
	            if (i==1 || (i-1+dayinweek)%7==1)
	             //   System.out.print("第" + weekcount + "周 " + month + "." + i );	            	
	            if(i<10) 
	            {
	            	a= month + ".0" + i;
	            }
	            else
	            {
	            	a= month + "." + i;
	            }
	           
	            if (i==days || (i-1+dayinweek)%7==0) {
	             //   System.out.print(" - " + month + "." + i);
	            	if(i<10)
	            	{
	                a+=" - " + month + ".0" + i;
	            	}
	            	else
	            	{
	            		  a+=" - " + month + "." + i;
	            	}
	                list.add(a);
	                a="";
	                weekcount++;	            
	            }
	        }
		return list;
	}
	
	
	public static void getWeek(String year,String month){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        int index = 1;
      
        String start = year+"-"+month+"-"+1;
        String end=null;
        for(int i = 1;i<(cal.getActualMaximum(Calendar.DAY_OF_MONTH));i++){
        	
            cal.set(Calendar.DAY_OF_MONTH, i);
            int week=cal.get(Calendar.DAY_OF_WEEK);   
    
            end = year+"-"+month+"-"+(i+1);
         
            if(week-1==6){
                System.out.println("第"+index+"周: "+start+"  结束   "+end);
                   start = year+"-"+month+"-"+(i+2);                   
                    index ++;
                }
         
        }
      
     //   System.out.println("第"+index+"周: "+start+"至"+end);
    
   
    }
	
    //查询前几周的日期
	public static  String Monday(int n)
	{
		 Calendar cal = Calendar.getInstance();
		   //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推		
		   String monday;
		   cal.add(Calendar.DATE, n*7);
		   //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		   cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		   monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		   return monday+" 00:00:00";
	}
	//查询前几周周日的时间
	public static  String Sunday(int n)
	{
		 Calendar cal = Calendar.getInstance();
		   //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推		
		   String monday;
		   cal.add(Calendar.DATE, n*7);
		   //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		   cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		   monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		   return monday+" 23:59:59";
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
	/*
	 * 生成授权网址
	 * */
	public static String URLEncodeUTF8(String source)
	{
		String result=source;
		
		try {
			result=java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}

	
}
