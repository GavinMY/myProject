package com.bluelight.entry;

import java.util.List;

/**
 * 
 * 
 * @authormeiyu
 * @date 2013-11-09
 */
public class SNSUserInfo {
	
	//用户id
	private Integer uid;
	// 用户标识
	private String openId;
	// 用户昵称
	private String nickname;
	// 性别（1是男性，2是女性，0是未知）
	
	private int sex;
	//年龄
	private int age;
	// 国家
	private String country;
	// 省份
	private String province;
	// 城市
	private String city;
	// 用户头像链接
	private String headImgUrl;
    //身高
	private Double 	uheight;
	//体重
	private Double uweight;
	//烟龄
	private Integer  usmoke;
	//经度
	private Double  ln;
	//纬度
	private Double  la;
	//控烟目标
	private Integer target;	
	// 用户特权信息
	private List<String> privilegeList;
	private String  family;
	
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getOpenId() {
		return openId;
	}
	public SNSUserInfo()
	{	
	}
	public SNSUserInfo(Integer uid,String openId,String headImgUrl,String nickname,int sex)
	{
		this.uid=uid;
		this.openId=openId;
		this.headImgUrl=headImgUrl;
		this.nickname=nickname;
		this.sex=sex;
	}

	public SNSUserInfo(String openId,String headImgUrl, String nickname, int sex,
			  Integer usmoke,  Double la,Double ln, Integer target) {
		super();
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.headImgUrl = headImgUrl;
		this.usmoke = usmoke;
		this.ln = ln;
		this.la = la;
		this.target = target;
	}

	public SNSUserInfo(String openId, String nickname, int sex, int age,
			String headImgUrl, Double uheight, Double uweight, Integer usmoke,
			Double ln, Double la, Integer target,String  family) {
		super();
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.age = age;
		this.headImgUrl = headImgUrl;
		this.uheight = uheight;
		this.uweight = uweight;
		this.usmoke = usmoke;
		this.ln = ln;
		this.la = la;
		this.target = target;
		this.family=family;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public List<String> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Double getUheight() {
		return uheight;
	}

	public void setUheight(Double uheight) {
		this.uheight = uheight;
	}

	public Double getUweight() {
		return uweight;
	}

	public void setUweight(Double uweight) {
		this.uweight = uweight;
	}

	public Integer getUsmoke() {
		return usmoke;
	}

	public void setUsmoke(Integer usmoke) {
		this.usmoke = usmoke;
	}

	public Double getLn() {
		return ln;
	}

	public void setLn(Double ln) {
		this.ln = ln;
	}

	public Double getLa() {
		return la;
	}

	public void setLa(Double la) {
		this.la = la;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

}
