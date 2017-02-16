
package com.bluelight.demo.protocol;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;


import org.apache.commons.codec.binary.Hex;



public class Base64 {
    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
            -1, -1, -1 };

    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    public static byte[] decode(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {

            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("utf-8");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("utf-8");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("utf-8");
    }
 
    
    public static char[] getChar (byte[] bytes) {//将字节转为字符(解码)
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        
         CharBuffer cb = cs.decode (bb);
        
         return cb.array();
    }
    public static String[] bytesToHexStrings(byte[] src){       
    	           if (src == null || src.length <= 0) {       
    	               return null;       
    	            }
    	           String[] str = new String[src.length];
                
    	           for (int i = 0; i < src.length; i++) {       
    	               int v = src[i] & 0xFF;       
    	               System.out.print(Integer.toHexString(v));
    	               String hv = Integer.toHexString(v);   
    	               
    	               if (hv.length() < 2) {       
    	                   str[i] = "0";       
    	                }       
    	               str[i] = hv;        
    	           }       
    	           return str;       
    	      }
    	
    public static String hexString2binaryString(String hexString)  
    {  
        if (hexString == null || hexString.length() % 2 != 0)  
            return null;  
        String bString = "", tmp;  
        for (int i = 0; i < hexString.length(); i++)  
        {  
            tmp = "0000"  
                    + Integer.toBinaryString(Integer.parseInt(hexString  
                            .substring(i, i + 1), 16));  
            bString += tmp.substring(tmp.length() - 4);  
        }  
        return bString;  
    }  
    
    
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "abcd";
        System.out.println("加密前：" + s);
        String x = encode(s.getBytes());
        System.out.println("加密后：" + x);
    //   String x1= bytesToHexString(decode("/s8AAQAXAMgAAAAARFRNOlRlc3RDTUQ=="));
        byte[] reqRaw1=org.apache.commons.codec.binary.Base64.decodeBase64("TVREOkNvbm5lY3RlZA==");
        String a1= new String (reqRaw1);
        System.out.println("a1"+a1);
        byte[] reqRaw=decode(" KE1URDpDTEstMjAxNS8wOS8wMi8xMC81Mi80NixDTlQtMCxCQVQtMCxGRU4tMSxMTVQtMjAsQUNOLTAsVENOLTMsQUxTLTAp");        
      String a= new String (reqRaw);
     System.out.println(a);
     System.out.println("头信息"+a.substring(0, a.indexOf("-")));
     String b=a.substring(a.indexOf("-")+1,a.length()-1);
     System.out.println("b"+b);
     String[] c=b.split(",");
	 System.out.println("系统时间:"+c[0]);
	 System.out.println("点火次数:"+c[1].substring(c[1].indexOf("-")+1, c[1].length()));
	 System.out.println("电池电量:"+c[2].substring(c[2].indexOf("-")+1, c[2].length()));
	 System.out.println("点火使能:"+c[3].substring(c[2].indexOf("-")+1, c[3].length()));
	 System.out.println("限制次数:"+c[4].substring(c[2].indexOf("-")+1, c[4].length()));
	 System.out.println("提醒次数:"+c[5].substring(c[2].indexOf("-")+1, c[5].length()));
	 System.out.println("当日点火次数:"+c[6].substring(c[2].indexOf("-")+1, c[6].length()));
	 System.out.println("状态:"+c[7].substring(c[7].indexOf("-")+1, c[7].length()));
	 
	 
//	 System.out.println(BlueLight.parsesys(a));
	
/*
 * 转对象
 * */

	 ByteBuffer buf = ByteBuffer.wrap(reqRaw);   
	 System.out.println(buf.getChar());
	 System.out.println("buf.getShort()"+buf.getShort());
        
      
	      
    }
}
