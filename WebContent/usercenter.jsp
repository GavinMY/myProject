<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
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
    <title>填写个人信息</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript">
            function checkThat()
            {        
               for(var i=0;i<document.form1.elements.length-1;i++)
               {
             
                  if(document.form1.elements[i].value=="")
                  {
                     alert("当前表单填写不完整");
                     document.form1.elements[i].focus();
                     return false;
                  }
               }
               return true; 
            }
        </script>
  </head>
  
  <body>
  <div class="scroll guide"> 
<form action="servlet/WeinfoServlet" name="form1"  onsubmit="return checkThat()" method="post">
    <div class="users">
    	<div class="user_head"><img src="${requestScope.snsUserInfo.headImgUrl}" /></div>
        <div class="user_name">${requestScope.snsUserInfo.nickname}</div>
    </div>
    
    <div class="userinfo">
    	<div class="userinfolist">
        	<div class="userinfotit">性 别</div>
            <div class="userinfocon centerR">${requestScope.snsUserInfo.sex eq 1 ?"男":"女"}</div>
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon2">年 龄</div>
            <div class="userinfocon centerR"><input type="text" name="uage" value="${requestScope.snsUserInfo.age}"  class="txxx" oninput="value=value.replace(/[^\d]/g,'')"/> &nbsp;岁</div>
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon3">身 高</div>
            <div class="userinfocon centerR"><input type="text" name="uheight"  value="<fmt:formatNumber type="number" value="${requestScope.snsUserInfo.uheight}" maxFractionDigits="0"/>" class="txxx" oninput="value=value.replace(/[^\d]/g,'') " /> cm</div>
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon4">体 重</div>
            <div class="userinfocon centerR"><input type="text" name="uweight" value="<fmt:formatNumber type="number" value="${requestScope.snsUserInfo.uweight}" maxFractionDigits="0"/>" class="txxx" oninput="value=value.replace(/[^\d]/g,'') " /> &nbsp;kg</div>
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon5">烟 龄</div>
            <div class="userinfocon centerR"><input type="text" name="usmoke" value="${requestScope.snsUserInfo.usmoke}"  class="txxx" oninput="value=value.replace(/[^\d]/g,'') "/>&nbsp; 年</div>
        <input type="hidden" name="openid" value="${requestScope.openid}">
        </div>
    </div>
    <input type="submit" value="确 定" class="tjbtn">
</form>
</div>
</body>
</html>