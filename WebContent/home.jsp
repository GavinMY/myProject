<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>"> 
     <meta charset="utf-8">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta content="no-cache,must-revalidate" http-equiv="Cache-Control">
    <meta content="no-cache" http-equiv="pragma">
    <meta content="0" http-equiv="expires">
    <meta content="telephone=no, address=no" name="format-detection">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
  </head>
  
  <body>
   <div class="scroll guide"> 
<form>
    <div class="users">
    	<div class="user_head"><img src="${requestScope.snsUserInfo.headImgUrl}" /></div>
        <div class="user_name">${requestScope.snsUserInfo.nickname}</div>
        <div class="edits"><a href="servlet/WeinfoServlet?openid=${sessionScope.openId}" style="color:#FFF">编辑</a></div>
    </div>
    
    <div class="userinfo">
    	<div class="userinfolist">
        	<div class="userinfotit">性 别</div>
            <div class="userinfocon centerR">${requestScope.snsUserInfo.sex eq 1 ?"男":"女"}</div>
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon2">年 龄</div>
            
            <c:choose>
        	<c:when test="#requestScope.snsUserInfo.age==0"><div class="userinfocon centerR">未知</div></c:when>
        	<c:otherwise><div class="userinfocon centerR">${requestScope.snsUserInfo.age}岁</div></c:otherwise>
        	</c:choose> 
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon3">身 高</div>
            <c:choose>
        	<c:when test="#requestScope.snsUserInfo.uheight==0"><div class="userinfocon centerR">未知</div></c:when>
        	<c:otherwise><div class="userinfocon centerR"><fmt:formatNumber type="number" value="${requestScope.snsUserInfo.uheight}" maxFractionDigits="0"/>cm</div></c:otherwise>
        	</c:choose>   
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon4">体 重</div>
            <div class="userinfocon centerR"><fmt:formatNumber type="number" value="${requestScope.snsUserInfo.uweight}" maxFractionDigits="0"/>KG</div>
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon5">烟 龄</div>
            <div class="userinfocon centerR">${requestScope.snsUserInfo.usmoke}年</div>
        </div>
    </div>
</form>
</div>
</body>
</html>
   