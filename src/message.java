import java.util.HashMap;
import java.util.Map;

import com.bluelight.demo.api.util.HttpUtil;
import com.bluelight.entry.TemplateData;
import com.bluelight.entry.WxTemplate;

import net.sf.json.JSONObject;


public class message {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
		JSONObject json = new JSONObject();
		json.put("template_id_short", "OPENTM207350032");
		String a=HttpUtil.doPost(url,json.toString());
		JSONObject obj=JSONObject.fromObject(a);
		System.out.println("a:"+a);
	    String template_id=obj.getString("template_id");
		WxTemplate t = new WxTemplate();
		t.setUrl("https://www.baidu.com/");
		t.setTouser("o3yS0uEWZabZWhhpJPLzya5QPKyI");
		t.setTopcolor("#44B549");
		t.setTemplate_id(template_id);
		Map<String,TemplateData> m = new HashMap<String,TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#44B549");
		first.setValue("今日吸烟数据");
		TemplateData first1 = new TemplateData();
		first1.setColor("##00FF00");
		first1.setValue("80支");
		TemplateData first2 = new TemplateData();
		first2.setColor("##00FF00");
		first2.setValue("50%");
		m.put("first", first);
		m.put("keyword1", first1);
		m.put("keyword2", first2);
		
//		TemplateData remark = new TemplateData();
//		remark.setColor("blue");
//		remark.setValue("***备注说明***");
//		m.put("Remark", remark);
		t.setData(m);
		//JSONObject jsonobj = httpRequest(“https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN”, "POST",JSONObject.fromObject(t).toString())  
		String url11="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		System.out.println(JSONObject.fromObject(t).toString());
		HttpUtil.doPost(url11, JSONObject.fromObject(t).toString());
	}

}
