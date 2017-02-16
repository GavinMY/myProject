import java.awt.*;
import java.io.*;

import javax.imageio.*;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;

import java.awt.image.BufferedImage;
import javax.swing.*;
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedImage b1;
		try {
			b1 = ImageIO.read(new File("D:\\image\\1.1.jpg"));
			BufferedImage b2 = ImageIO.read(new File("D:\\image\\2.gif"));
			Graphics2D g = b1.createGraphics();
			Font f = new Font("宋体",Font.BOLD,60);
			g.setFont(f);
			g.setColor(Color.BLACK);
			g.drawString("我在城里人部落",100,80);	
	        g.drawImage(b2, 60,50,60,60,null);
	        
			ImageIO.write(b1,"jpg",new File("D:\\image\\a.jpg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  //1.jpg是你的 主图片的路径
        InputStream is;
		try {
			is = new FileInputStream("D:\\image\\1.1.jpg");
			  //通过JPEG图象流创建JPEG数据流解码器
	        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
	        //解码当前JPEG数据流，返回BufferedImage对象
	        BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
	        //得到画笔对象
	        Graphics g = buffImg.getGraphics();
	        
	        //创建你要附加的图象。
	        //2.jpg是你的小图片的路径
	        ImageIcon imgIcon = new ImageIcon("D:\\image\\2.gif"); 	        
	        //得到Image对象。
	        Image img = imgIcon.getImage();	        
	        //将小图片绘到大图片上。
	        //5,300 .表示你的小图片在大图片上的位置。
	        g.drawImage(img,250,400,100,100,null);
	        //设置颜色。
	        g.setColor(Color.BLACK);
	        
	        
	        //最后一个参数用来设置字体的大小
	        Font f = new Font("宋体",Font.BOLD,30);	        
	        g.setFont(f);	        
	        //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
	        g.drawString("我在城里人部落",30,50);	        
	        g.dispose();	        
	        OutputStream os;
			try {
				os = new FileOutputStream("D:\\image\\union.jpg");
				  //创键编码器，用于编码内存中的图象数据。
		        
		        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
		        en.encode(buffImg);		        
		        is.close();
		        os.close();		        
		    System.out.println ("合成结束。。。。。。。。");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
//        
//      
//
//        
        
        
 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      
		
		
	}

}
