package com.bluelight.demo.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bluelight.demo.api.DeviceApi;
import com.bluelight.demo.api.json.DeviceAuth;
import com.bluelight.demo.api.util.HttpUtil;
import com.bluelight.tools.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class DeviceAuths extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeviceAuths() {
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
	static String imagePath = "D://image";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("octets/stream"); 
		 String excelName = "二维码地址";  
		/*
		 * 处理提交过来的数据
		 * */
		String mac=request.getParameter("mac");
		 Pattern p = Pattern.compile("\\s*|\t|\r|\n");
         Matcher m = p.matcher(mac);
         mac = m.replaceAll("");
         String[] arr= mac.split(",");
         List<String> sList = Arrays.asList(arr);
       //转码防止乱码  
	     response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("utf-8"), "ISO8859-1" )+".xls"); 
	     String[] headers = new String[]{"type=url","网络地址","短地址","文件名称"};  
	     OutputStream out = response.getOutputStream(); 
	     exportExcel(excelName,headers,findTicket(sList), out,"yyyy-MM-dd"); 
	     out.close();  
         System.out.println("excel导出成功！");  
//         findTicket(sList);
         
	}
	
	/*
	 * 
	 * */
	public static List findTicket(List<String> listmac)
	{
		List list =new ArrayList();
		for(int i=0;i<listmac.size();i++)
		{
			Map map=new HashMap<String, String>();
			String mac=listmac.get(i);
			map.put("mac", listmac.get(i));
			
		// 设备id 由厂商指定，建议由字母、数字、下划线组成，以免json解析失败。D03972C87C2B
			String url="https://api.weixin.qq.com/device/getqrcode?access_token=ACCESS_TOKEN";
			String code=HttpUtil.doGet(url);
			JSONObject codeJson = JSONObject.fromObject(code);
			String ticket = codeJson.getString("qrticket");
			String deviceId=codeJson.getString("deviceid");
			System.out.println("qrticket:"+ticket);
			//-----------设备授权 
			// 设备授权后才能进行扫码绑定
			// 开发调试时，可以先用假的信息授权，测试服务端设备绑定事件的处理。
//		//	 设备参数确定后，更新为正确的设备属性，再和设备联调。
	    	String authKey = "";		//"1234567890ABCDEF1234567890ABCD11";
		boolean isCreate = false;//是否首次授权： true 首次授权； false 更新设备属性
		try {
			//设备授权
			device_Auth(authKey, deviceId, mac, isCreate);
			//-----------根据设备id生成二维码，授权后才能进行扫描绑定
		//String ticket=createQrByDeviceId(deviceId);
		map.put("ticket", ticket);
		list.add(map);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
		return list;
	}
	

	/**
	 * 设备授权
	 * @param authKey 加密key
	 * @param deviceId 设备id
	 * @param mac 设备的mac地址
	 * @param 是否首次授权： true 首次授权； false 更新设备属性
	 */
	public static void device_Auth(String authKey, String deviceId, String mac,
			boolean isCreate) throws Exception {		
		DeviceAuth device = new DeviceAuth();		
		device.setId(deviceId);		//设备id
		device.setMac(mac);			//设备的mac地址 采用16进制串的方式（长度为12字节），不需要0X前缀，如： 1234567890AB
		device.setConnect_protocol("2");//设备类型 android classic bluetooth – 1 ios classic bluetooth – 2 ble – 3 wifi -- 4
		
		// 不加密时 authKey 为空字符串，crypt_method、auth_ver都为0
		// 加密时 authKey 需为符合格式的值，crypt_method、auth_ver都为1
		device.setAuth_key(authKey);	//加密key 1234567890ABCDEF1234567890ABCDEF
		device.setCrypt_method("0");    //auth加密方法  0：不加密 1：AES加密
		device.setAuth_ver("0");        //0：不加密的version 1：version 1
		
		/**
		 * 连接策略，32位整型，按bit位置位，目前仅第1bit和第3bit位有效（bit置0为无效，1为有效；第2bit已被废弃），且bit位可以按或置位
		 * （如1|4=5），各bit置位含义说明如下：<br/>
		 * 1：（第1bit置位）在公众号对话页面，不停的尝试连接设备<br/>
		 * 4：（第3bit置位）处于非公众号页面（如主界面等），微信自动连接。当用户切换微信到前台时，可能尝试去连接设备，连上后一定时间会断开<br/>
		 * 8：（第4bit置位），进入微信后即刻开始连接。只要微信进程在运行就不会主动断开
		 */
		device.setConn_strategy("1");   //连接策略
		device.setClose_strategy("1");  //1：退出公众号页面时断开 2：退出公众号之后保持连接不断开 3：一直保持连接（设备主动断开连接后，微信尝试重连）

		// 低功耗蓝牙必须为-1
		device.setManu_mac_pos("-1");   //表示mac地址在厂商广播manufature data里含有mac地址的偏移，取值如下： -1：在尾部、 -2：表示不包含mac地址
		// 
		device.setSer_mac_pos("-2");    //表示mac地址在厂商serial number里含有mac地址的偏移，取值如下： -1：表示在尾部 -2：表示不包含mac地址 其他：非法偏移
		
		// 调用授权
		List<DeviceAuth> auths = new ArrayList<DeviceAuth>();
		auths.add(device);
		System.out.println(DeviceApi.authorize(auths,isCreate));
		// {"resp":[{"base_info":{"device_type":"gh_1bafe245c2cb","device_id":
		// "gh_1bafe245c2cb_9e081608d6d62b984edf52d5d3a50aba"},"errcode":0,"errmsg":"ok"}]}
	}
	/**
	 * 根据deviceId 调用createQrcode生成二维码生成串
	 */
	public static String createQrByDeviceId(String deviceId) throws Exception{
		List<String> list = new ArrayList<String>();
		list.add(deviceId);
		String code = DeviceApi.createQrcode(list);
		// 格式 result={"errcode":0,"errmsg":"ok","device_num":1,"code_list":[{"device_id":"gh_1bafe245c2cb_bluelight_demo_000002","ticket":"http:\/\/we.qq.com\/d\/AQC5v5iz1PEfQTzuo5Ow7U24-pzJwIcfYvZ-y8yS"}]}
		JSONObject codeJson = JSONObject.fromObject(code);
		String ticket = ((JSONArray) codeJson.get("code_list"))
				.getJSONObject(0).getString("ticket");
		
//		String ticket="vkJINI5Zf7-4-sg3Abd4JkUVm3uMo9m9Q-ZjkuuKlMf8Ht4_QwZFs8EHv-6yuGjk5bbb6C-woiOA1IH60rZvh_L30SQstrcze2BVVC34RcU";
		System.out.println("ticket=" + ticket);
		
//		createQrImage(imagePath , deviceId , ticket );
		return ticket;
	}
	/**
	 * 使用zxing库生成二维码图片
	 */
	private static void createQrImage(String path, String deviceId,
			String ticket) {
		path = path.endsWith("/") ? path : path + "/";
		int width = 430;
		int height = 430;
		// 二维码的图片格式
		String format = "jpg";
		String fileName = path + deviceId + "."+ format;
		// 设置二维码的参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);//容错率
		try {
			// 生成二维码
			BitMatrix bitMatrix = new MultiFormatWriter().encode(ticket,
					BarcodeFormat.QR_CODE, width, height, hints);
			// 输出图片
			File outputFile = new File(fileName);
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

			System.out.println("设备id：" + deviceId + "，ticket："+  ticket + "，生成二维码图片：" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
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
	 /** 
     *  
     * @Description: 生成excel并导出到客户端（本地） 
     * @Auther: lujinyong 
     * @Date: 2013-8-22 下午3:05:49 
     */  
    protected void exportExcel(String title,String[] headers,List mapList,OutputStream out,String pattern){  
        //声明一个工作簿  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        //设置表格默认列宽度为15个字符  
        sheet.setDefaultColumnWidth(20);  
        //生成一个样式，用来设置标题样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式,用于设置内容样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        //产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for(int i = 0; i<headers.length;i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        for (int i=0;i<mapList.size();i++) {  
            Map<String,Object> map = (Map<String, Object>) mapList.get(i);  
            row = sheet.createRow(i+1);  
            int j = 0;  
            Object value = null;           
            row.createCell(j++).setCellValue("");  
            row.createCell(j++).setCellValue(map.get("ticket").toString()); 
            row.createCell(j++).setCellValue("");  
            row.createCell(j++).setCellValue(map.get("mac").toString());  
//            value=map.get("age");  
//            if(value instanceof Integer){  
//                row.createCell(j++).setCellValue(String.valueOf(value));  
//            }  
//            row.createCell(j++).setCellValue("0".equals(map.get("sex").toString())?"女":"男");  
        }  
        try {  
            workbook.write(out);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 

}
