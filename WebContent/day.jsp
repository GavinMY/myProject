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
    <title>吸烟数据</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all">
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
	<link rel="stylesheet" type="text/css" href="dhj/css/swiper.min.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/zhou.js"></script>  
    <script type="text/javascript" src="dhj/js/data.js"></script>
    <script type="text/javascript" src="dhj/js/index.js"></script>
    <script type="text/javascript" src="dhj/js/index1.js"></script>
    <script type="text/javascript" src="dhj/js/swiper.min.js"></script>
   <style>
.swiper-container {width: 100%;height: auto; margin-top:1px; height:350px;  }
.swiper-slide {text-align: center;font-size: 18px;display: -webkit-box;display: -ms-flexbox;display: -webkit-flex;display: flex;-webkit-box-pack: center;-ms-flex-pack: center;-webkit-justify-content: center;justify-content: center;-webkit-box-align: center;-ms-flex-align: center;-webkit-align-items: center;align-items: center;}
.swiper-slide img{ width:100%; }
</style>
<script type="text/javascript">
 var shuju=new Array();
 var i=0;
<c:forEach items="${requestScope.daysinfo}" var="daysinfo" >
shuju[i]=${daysinfo};
i=i+1;
</c:forEach>
</script>
  </head>  
  <body>
 <div class="scroll  guide"> 
<div class="days" style="width:100%; display:inline-block;">
	<!-- <div class="loading"><img src="dhj/images/loading.gif" /></div> -->
    <div class="swiper-container">
        <div class="swiper-wrapper">
        	<!-- 日 -->
         	<c:forEach var="item" items="${date}" varStatus="status">         	
            <div class="swiper-slide">
            	<a href="servlet/EquipmentServlet?openid=${openid}" class="userset">&nbsp; </a>
            
            	<div class="daycon" onclick="window.open('servlet/dayFire?openid=${openid}')">
                	<div class="dayset">控烟目标：${Cigarette.ctarget}支</div>
                	<div class="daytab">
                    	<li style="background:#FFF; color:#000;">${item}</li>    
                    </div>
                    <div class="daydata"> <div id="${status.index}" class="chart"></div> </div>
                </div>            
            </div>
            </c:forEach>                   
        </div>
        
        <!-- Add Pagination -->
        
    </div>
    <div class="daylist">
    	<div class="daynum">
              <a href="javascript:;">
                  <div class="dayfont">今日吸烟量</div>
                   <div class="dayfont">${todaycount}</div>
               </a>
               <a href="javascript:;">
                    <div class="dayfont">本周吸烟量</div>
                    <div class="dayfont">${weekcount}</div>
                </a>
                <a href="javascript:;"  style=" border-right:none;">
                    <div class="dayfont">本月吸烟量</div>
                     <div class="dayfont">${mouthsweek}</div>
                 </a>
                 <a href="javascript:;">
                     <div class="dayfont">烟气烟碱（mg）</div>
                     <div class="dayfont">${Cigarette.soda*todaycount}</div>
                  </a>
                   <a href="javascript:;">
                       <div class="dayfont">焦油（mg）</div>
                       <div class="dayfont">${Cigarette.ctar*todaycount}</div>
                    </a>
                    <a href="javascript:;"  style=" border-right:none;" >
                        <div class="dayfont" >吸烟花销（元）</div>
                        <div class="dayfont">${Cigarette.cprice*todaycount}</div>
                    </a>
           </div>
    </div>
    
 </div>   
</div>
</body>
<script>
 var num = $('.swiper-wrapper').children('.swiper-slide').length;
var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
		initialSlide:num-1, //设置起始页面的索引
    });
</script>
<script>
$(".daylmorning").find("li:last").css('border','none');
$(".daunum").find("li:last").css('border','none');
</script>
<script>
var aa = $(window).height();
$('.days').css('min-height',aa)
</script>
</html>
 