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
    <title>邀请好友</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
   <link rel="stylesheet" type="text/css" href="dhj/css/swiper.min.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/swiper.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> <!--微信js -->
    <script type="text/javascript">
         wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:'${appId}', // 必填，公众号的唯一标识
	    timestamp:parseInt('${timestamp}'), // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList:[
			// 所有要调用的 API 都要加到这个列表中
			'onMenuShareTimeline',
			'onMenuShareAppMessage'
		  ] 
});
	wx.ready(function () {
		wx.onMenuShareAppMessage({
		  title: '生命诚可贵，爱情价更高 ',
		  desc: '亲爱的，快来帮我戒烟吧 。我需要你！ ',
		  link:'${requestScope.redict}',
		  imgUrl: 'http://yujuan1314.oicp.net/demo/dhj/images/yaoicon.jpg',
		  
		  
		});

		wx.onMenuShareTimeline({
		  title: '生命诚可贵，爱情价更高',
		   desc: '亲爱的，快来帮我戒烟吧 。我需要你！',
          link:'${requestScope.redict}',
          imgUrl:'http://yujuan1314.oicp.net/demo/dhj/images/yaoicon.jpg'
		});
	});
    </script>
    </head>
  <body>
  <img src="dhj/images/yaoicon.jpg" width="0" height="0" />
 <div class="scroll  guide">
	<div class="yaoqing"> 	 
        <div class="yqimg"><img src="dhj/images/yqicon.png" /></div>
        <p>点击右上角的微信菜单，选择发送的朋友，邀请您的好友关注您的健康数据</p>
        <div class="jiechu">想解除和好友的绑定关系，请删除绑定</div>
        <div class="haoyou">
        <c:if test="${requestScope.uname!=null}">
        	<span class="haoyouname">好友：${requestScope.uname}</span>
            <span  class="haoyoudel"><a href="servlet/DeleFamilyServlet?openid=${sessionScope.openId}">删 除</a></span>        
            </c:if>
        </div>
    </div>
</div>
<script>
var aa = $(window).height();
$('.yaoqing').css('min-height',aa)
</script>
</body>
</html>