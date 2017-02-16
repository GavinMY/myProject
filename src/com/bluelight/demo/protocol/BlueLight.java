package com.bluelight.demo.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.bluelight.Daoimpl.EquipmentDaoimpl;
import com.bluelight.Daoimpl.FireinfoDaoimpl;
import com.bluelight.Daoimpl.SNSUserInfodaoimpl;
import com.bluelight.demo.api.util.Addressconvert;
import com.bluelight.demo.consts.Equipment;
import com.bluelight.demo.consts.XmlResp;
import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.SNSUserInfo;

/**
 * 蓝牙灯泡自定义协议结构解析
 */
public class BlueLight {

	/**
	 * <pre>
	 * 	包 由包头+包体组成，包头如：
	 * 	struct BlueDemoHead
	 * 	{
	 * 	    unsigned char  m_magicCode[2];
	 * 	    unsigned short m_version;
	 * 	    unsigned short m_totalLength;    
	 * 	    unsigned short m_cmdId;  
	 * 	    unsigned short m_seq;  
	 * 	    unsigned short m_errorCode;
	 * 	};
	 *  包体为字符串 utf-8编码
	 * </pre>
	 */
	  
	public static class Head {
		public char magic;// 固定为 0xFECF
		public short version;
		public short length;// 包头+包体总长度
		public short cmdId;// 命令字
		public short seq;// 序列号 resp时为参数 push时为0
		public short errorCode;// 错误代码 0表示成功
		 

		@Override
		public String toString() {
			return "Head [magic=" + magic + ", version=" + version
					+ ", length=" + length + ", cmdId=" + cmdId + ", seq="
					+ seq + ", errorCode=" + errorCode + "]";
		}
	}

	public Head head;
	public String body;

	/**
	 * 构造类型
	 * 
	 * @param cmdId
	 *            命令
	 * @param respText
	 *            包体内容
	 * @param seq
	 *            序列号 响应包同传入参数值；push包为0
	 */
	public static BlueLight build(CmdId cmdId, String respText, short seq) {
		BlueLight light = new BlueLight();
		light.body = respText;
		light.head = new Head();

		byte[] b = respText == null ? new byte[0] : respText.getBytes(CHARSET);

		light.head.magic = MAGIC;
		light.head.version = 1;
		light.head.length = (short) (HEAD_LENGTH + b.length);
		light.head.cmdId = cmdId.value();
		light.head.seq = seq;
		light.head.errorCode = 0;
		return light;
	}

	/**
	 * 转为二进制
	 */
	public byte[] toBytes() {
		byte[] b = body == null ? new byte[0] : body.getBytes(CHARSET);

		ByteBuffer buf = ByteBuffer.allocate(head.length);
		buf.putChar(head.magic);
		buf.putShort(head.version);
		buf.putShort(head.length);
		buf.putShort(head.cmdId);
		buf.putShort(head.seq);
		buf.putShort(head.errorCode);
		buf.put(b);

		buf.flip();
		return buf.array();
	}

	/**
	 * 二进制转对象
	 */
	public static BlueLight parse(byte[] reqBytes) {
		ByteBuffer buf = ByteBuffer.wrap(reqBytes);
		System.out.println(buf.getChar(0));
		char magic = buf.getChar();
		
		
		System.out.println(magic);
		// magic校验
		if (magic != MAGIC) {
			System.err.println("magic not valid " + magic);
		}

		Head h = new Head();
		h.magic = MAGIC;
		h.version = buf.getShort();
		h.length = buf.getShort();
		h.cmdId = buf.getShort();
		h.seq = buf.getShort();
		h.errorCode = buf.getShort();

		int bodyLen = h.length - HEAD_LENGTH;
		byte[] bodyBytes = new byte[bodyLen];
		buf.get(bodyBytes);
		String b = new String(bodyBytes, CHARSET);

		BlueLight light = new BlueLight();
		light.head = h;
		light.body = b;
		return light;
	}

	// magic code
	private static final char MAGIC = 0xFECF;
	// 包头长度
	private static final short HEAD_LENGTH = 12;
	private static Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * 命令
	 */
	public static enum CmdId {
		/**
		 * 请求
		 */
		SEND_TEXT_REQ(0x01),
		/**
		 * 响应
		 */
		SEND_TEXT_RESP(0x1001),
		/**
		 * 点灯
		 */
		OPEN_LIGHT_PUSH(0xC3),
		/**
		 * 灭灯
		 */
		CLOSE_LIGHT_PUSH(0xC1);

		private short value;

		private CmdId(int v) {
			this.value = (short) v;
		}

		public short value() {
			return value;
		}
	}

	@Override
	public String toString() {
		return "BlueLight [body=" + body + ", head=" + head + "]";
	}
	
	static char[] bytesToChars(byte[] bytes) {
		char[] cs = new char[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			cs[i] = (char) bytes[i];
		}
		return cs;
	}
	
	static byte[] charsToBytes(char[] chars) {
		byte[] bs = new byte[chars.length];
		for (int i = 0; i < chars.length; i++) {
			bs[i] = (byte) chars[i];
		}
		return bs;
	}
	
	protected static String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	public static String  parsesys(String a,String openId)
	{	
	EquipmentDaoimpl EquipmentDao=new EquipmentDaoimpl();
	System.out.println("开始解析："+a);
	String b=a.substring(a.indexOf("-")+1,a.length()-1);
	String[] c=b.split(",");
	String d=null;
	d="\n当前系统时间为:"+c[0];
	d+="\n总点火次数:"+c[1].substring(c[1].indexOf("-")+1, c[1].length());
	int BAT=Integer.valueOf(c[2].substring(c[2].indexOf("-")+1, c[2].length()));	
	d+="\n电池电量:"+BAT;
	int fen=Integer.valueOf(c[3].substring(c[2].indexOf("-")+1, c[3].length()));
	d+="\n点火使能:"+fen;
	
	d+="\n限制次数:"+c[4].substring(c[4].indexOf("-")+1, c[4].length());
	//d+="\n提醒次数:"+c[5].substring(c[5].indexOf("-")+1, c[5].length());
	d+="\n当日点火次数:"+c[6].substring(c[6].indexOf("-")+1, c[6].length());
	int  ALS=Integer.valueOf(c[7].substring(c[7].indexOf("-")+1, c[7].length()));	
	d+="\n蜂鸣状态："+c[7].substring(c[7].indexOf("-")+1, c[7].length());
	Equipment equipment=EquipmentDao.findByopenid(openId);
	//没有查询到用户系统信息，就新增一个
	if(null==equipment)
	{
		equipment=new Equipment(openId,fen, ALS, BAT);
		EquipmentDao.add(equipment);
	}
	else//已有设备信息，则更新当前设备信息
	{
		Equipment newequipment=new Equipment(openId,fen, ALS, BAT);
		EquipmentDao.update(newequipment);	
	}
	return d;
	}	
	public static String  savefire(String a,String openId)
	{
		SNSUserInfodaoimpl userdao=new SNSUserInfodaoimpl();
		FireinfoDaoimpl  firedao=new FireinfoDaoimpl();
		SNSUserInfo UserInfo=userdao.findByopenid(openId);
		String address="未知";
		String firetime="";
		try {
			
			if(null!=UserInfo&&null!=UserInfo.getLa())
			{
			 address=Addressconvert.Address(UserInfo.getLn(), UserInfo.getLa());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("获取到openId："+openId);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/mm/dd/hh/mm/ss");
	 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String d="";
		 a=a.replace("(",""); 
		 a=a.replace(")",""); 
		 System.out.println("数据"+a);
		 Date date=new Date();
		 if(a.length()==29)
	//	 if(a.indexOf("MTD:OCD")>-1)
		 {
//			 String b=a.substring(a.indexOf("-")+1,a.length()); 
//			 String[] c=b.split("/");
//			 b=b.replaceAll("/", "\n");
//			 System.out.println(c[0]+","+c[1]);
//			 d="收到"+(c.length-1)+"条离线点火信息,点火时间分别为"+b;		 
//			 System.out.println(b);
			 String b=a.substring(a.indexOf("-")+1,a.length()-1);
			 String fireno=b.substring(0, 1);
			  firetime=b.substring(2,b.length()-1);		
			 d="\n第"+fireno+"次离线点火时间："+firetime;						
		 }
		 else
		 {
			 try {
				String b=a.substring(a.indexOf("-")+1,a.length());
				 String[] c=b.split(",");
				 firetime=c[0];
				 String CNT=c[1].substring(c[1].indexOf("-")+1, c[1].length());						
				 String TCN=c[2].substring(c[2].indexOf("-")+1, c[2].length());							  		  
				d="今日第"+TCN+"次点火,点火时间"+firetime+",今日点火总次数"+TCN;
			} catch (Exception e) {	
				e.printStackTrace();
				 return "数据格式出现问题"+a;
			}	
		 }	
		
		   Timestamp time = new Timestamp(date.getTime());
				 if(firedao.findfireinfo(time, openId))
				 {
			     System.out.println("插入的时间为："+time);
				 Fireinfo fireinfo=new Fireinfo(openId,time, openId, address);	
				 firedao.addFireinfo(fireinfo);
				 }  			
		 return d;
	}
	
	public static String lixian(String a,String openId)
	{
	String info="";
	return info;
	}
	
	public static void main(String[] args) {		
//		String x="(MTD:OCD-2015/11/30/02/35/12,CNT-2,TCN-1)";
//		String x1="(MTD:CLK-2015/11/30/15/34/24,CNT-38,BAT-25,FEN-1,LMT-20,ACN-0,TCN-10,ALS-1)";
//		String xx="(MTD:OCD-/21:35/21:36/21:37/21:38/21:39)";		
//		System.out.println(savefire(xx,"o3yS0uEWZabZWhhpJPLzya5QPKyI"));
		String yy="(MTD:OWD1-20/18/30/60/35/62/33)";
		System.out.println(lixian(yy,"o3yS0uEWZabZWhhpJPLzya5QPKyI"));
  //	System.out.println(parsesys(x1));
 //		System.out.println(CmdId.OPEN_LIGHT_PUSH.value);
//		BlueLight light = BlueLight.build(CmdId.OPEN_LIGHT_PUSH,
//				"123456", (short) 0);		
		//System.out.println(light);
		//System.out.println(Base64.encodeBase64String(light.toBytes()));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式
//		System.out.println("DTM:CLK-"+df.format(new Date()).toString());// new Date()为获取当前系统时间
//		BlueLight lightResp = BlueLight.build(CmdId.OPEN_LIGHT_PUSH,
//				"DTM:CLK-"+df.format(new Date()).toString(), (short) 0);
		
//		String lightResp1=null;
////		SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");//设置日期格式		
////		lightResp1="(DTM:CLK-"+df1.format(new Date()).toString()+")";
//		String lightResp2="(DTM:INF)";
//		String lightResp3="(DTM:OFF)";
//		String lightResp4="(DTM:CLN-ALL)";
//		String lightResp5="(DTM:FEN-0,LMT-20,ACN-3)";
//		String lightResp6="(DTM:ALS-1)";
//		String lightResp7="(DTM:RST)";
//		lightResp1=lightResp5;
//		// 序列化
//		byte[] respRaw = lightResp1.getBytes();
//		// Base64编码
//		String respCon = Base64.encodeBase64String(respRaw);
//		// 设备消息接口必须回复符合协议的xml
//		 XmlResp.buildDeviceText("gh_4614a9169b8e", "o3yS0uB0uXYbzHQNHY3oaWdwA4cY", "gh_4614a9169b8e", "D03972C85FFB", respCon, "350790");
	}
	
}
