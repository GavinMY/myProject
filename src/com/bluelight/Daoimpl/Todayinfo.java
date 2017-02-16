package com.bluelight.Daoimpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluelight.demo.consts.Equipment;
import com.bluelight.entry.Fireinfo;
import com.bluelight.entry.TemplateData;

public class Todayinfo 
{
	public Map dayinfo(String openid)
	{
		Map<String,TemplateData> m = new HashMap<String,TemplateData>();
		 EquipmentDaoimpl EquipmentDao=new EquipmentDaoimpl();
		 FireinfoDaoimpl Fireinfo=new FireinfoDaoimpl();
		TemplateData first = new TemplateData();
		first.setColor("#44B549");
		first.setValue("今日吸烟数据");
		
		TemplateData first1 = new TemplateData();
		first1.setColor("##00FF00");
		first1.setValue("80支");		
		
		//今日抽烟信息
		//查询他今日抽烟信息
		 SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//24小时制 
		  Date now=new Date();
        Date moring = getTimesmorning();
        Timestamp  end = new Timestamp(now.getTime());
        Timestamp begin = new Timestamp(moring.getTime());
        List<Fireinfo> list=Fireinfo.findByopenid(openid,begin,end);	 
        first1.setValue(list.size()+"支");
		
		TemplateData first2 = new TemplateData();
		first2.setColor("##00FF00");
		Equipment equipment=EquipmentDao.findByopenid(openid);
		if(null==equipment)
		{
			first2.setValue("0");
		}
		else
		{
			first2.setValue(equipment.getBAT()+"%");
		}
		m.put("first", first);
		m.put("keyword1", first1);
		m.put("keyword2", first2);
		return m;
		
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
}
