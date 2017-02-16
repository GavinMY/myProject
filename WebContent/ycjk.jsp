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
    <title>远程监控</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
   <link rel="stylesheet" type="text/css" href="dhj/css/swiper.min.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/swiper.min.js"></script>
  </head>
  
  <body>
  <div class="scroll  guide">
	<div class="ycjk"> 	 
        <!--  远程监控 -->
		<h3>您关注的 <strong>${requestScope.uname}</strong> 今日吸烟数据如下：</h3>
        <div class="ycjk-list">
        
      <c:forEach var="Fireinfo" items="${requestScope.list}" varStatus="status">
        <li>
            	<div class="ycjk-listicon"></div>
                <div class="ycjk-listxyl">
                	<span>1根</span>
                    <span>吸烟量</span>
                </div>
                 <div class="ycjk-listtime">
                	<span><fmt:formatDate value="${Fireinfo.ftime}" pattern="HH:mm" /></span>
                    <span>时 间</span>
                </div>
            </li>
        </c:forEach>
        	
        </div>
    </div>
</div>
<script>
var aa = $(window).height();
$('.ycjk').css('min-height',aa)
</script>
</body>
</html>