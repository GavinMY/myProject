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
    
    <title>完善个人信息</title>
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
  </head>
  		<script type="text/javascript">
            function myCheck()
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
  <body>
 
   <div class="scroll"> 
       <form name="form1" action="servlet/UpdateUserServlet" method="post" onSubmit="return myCheck()">
    <div class="users">
    	<div class="user_head"><img src="${requestScope.snsUserInfo.headImgUrl}" /></div>
        <div class="user_name">${requestScope.snsUserInfo.nickname}</div>
    </div> 
    <div class="userinfo">
    	<div class="userinfolist">
        	<div class="userinfotit">性别</div>
            <div class="userinfocon">
		 <div class="userinfocon centerR">${requestScope.snsUserInfo.sex eq 1 ?"男":"女"}</div>
	    </div>
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon2">年龄</div>
            <div class="userinfocon"><input  type="text" name="uage"  placeholder="请填写年龄" class="userinfoinput" /> 岁</div>
        </div>
        <div class="userinfolist">
        	<div class="userinfotit icon3">身高</div>
            <div class="userinfocon"><input  type="text" name="uheight" placeholder="请填写身高" class="userinfoinput"  oninput="value=value.replace(/[^\d]/g,'') "/> cm</div>
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon4">体重</div>
            <div class="userinfocon"><input  type="text" name="uweight"  placeholder="请填写体重" class="userinfoinput"  oninput="value=value.replace(/[^\d]/g,'') "/> KG</div>
        </div>
         <div class="userinfolist">
        	<div class="userinfotit icon5">烟龄</div>
            <div class="userinfocon"><input  type="text" name="usmoke" placeholder="请填写烟龄" class="userinfoinput"  oninput="value=value.replace(/[^\d]/g,'') "/> 年</div>
            <div class="userinfocon"><input  type="hidden" name="openid"  class="userinfoinput" value="${requestScope.snsUserInfo.openId}" /> </div>
        </div>
    </div>
    <button type="submit" class="submitbtn">提 交</button>
</form>
</div>
  </body>
</html>
