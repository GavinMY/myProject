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
    <title>当日数据</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
    <link rel="stylesheet" type="text/css" href="dhj/css/swiper.min.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/swiper.min.js"></script>
    <script type="text/javascript" src="dhj/js/iscroll.js"></script>
<style>
.swiper-container {width: 96%;height: 70px; margin-left:2%; background:url(dhj/images/borderop50.png) repeat-x left bottom;}
.swiper-slide {text-align: center; font-size: 18px;display: -webkit-box;display: -ms-flexbox;display: -webkit-flex;display: flex;-webkit-box-pack: center;-ms-flex-pack: center;-webkit-justify-content: center;justify-content: center;-webkit-box-align: center;-ms-flex-align: center;-webkit-align-items: center;align-items: center; height:70px; line-height:70px; float:left; }
.swiper-slide img{ width:100%; }
.swiper-button-next{ background-size:auto 40%;}
.swiper-button-prev{ background-size:auto 40%;}
</style>
<script type="text/javascript">
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;
/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		el = document.getElementById('thelist');
<!--  新获得的数据 -->
	//	for (i=0; i<3; i++) {
	//		li = document.createElement('li');
	//		li.innerText = 'Generated row' + (++generatedCount);
	//		el.insertBefore(li, el.childNodes[0]);
	//	}
		 document.forms[0].submit();
		myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		el = document.getElementById('thelist');

		for (i=0; i<3; i++) {
			li = document.createElement('li');
			li.innerText = 'Generated row ' + (++generatedCount);
			el.appendChild(li, el.childNodes[0]);
		}
		
		myScroll.refresh();		// 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}
/**
 * 初始化iScroll控件
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
	
	myScroll = new iScroll('wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
				this.minScrollY = 0;
			}else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
				pullDownAction();	// Execute custom function (ajax call?)
			} 
		}
	});	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}
//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 
</script>
  </head> 
  <body>
  <form action="servlet/dayFire" method="post">
 <div class="scroll guide" > 
	<div class="drsj">
    	 <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide" >
                 <!--  时间 -->
                 
<input type="text" name="date"  style="background:none;text-align:center; border:none;color:#FFF;font-size:16px " id="date" value="${requestScope.date}" />  
                </div>               
            </div>
            <!-- Add Pagination -->
             <div class="swiper-button-next" onclick="page_next()"></div> 
            <div class="swiper-button-prev" onclick="page_pre()"></div>
        </div>
        <!--  数据列表 -->
        <div id="wrapper">
	<div id="scroller">
		<div id="pullDown">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
		</div>
		
		<ul id="thelist" style=" margin-top:30px;"  >
		<c:forEach var="Fireinfo" items="${requestScope.morning}" varStatus="status">
		<li>
            	<div class="drsj-gs"></div>
                <div class="drsj-sw">
                	<div class="drsj-time"><fmt:formatDate value="${Fireinfo.ftime}" pattern="HH:mm" /></div>
                    <div class="drsj-time">${Fireinfo.address}</div>
                </div>
            </li>
		</c:forEach>
			 <c:forEach var="Fireinfo" items="${requestScope.afternoon}" varStatus="status">
			  <li>
            	<div class="drsj-gs drsj-gs2"></div>
                <div class="drsj-sw">
                	<div class="drsj-time"><fmt:formatDate value="${Fireinfo.ftime}" pattern="HH:mm" /></div>
                    <div class="drsj-time">${Fireinfo.address}</div>
                </div>
            </li>
			 </c:forEach>
            <c:forEach var="Fireinfo" items="${requestScope.night}" varStatus="status">
            <li>
            	<div class="drsj-gs drsj-gs3"></div>
                <div class="drsj-sw">
                	<div class="drsj-time"><fmt:formatDate value="${Fireinfo.ftime}" pattern="HH:mm" /></div>
                    <div class="drsj-time">${Fireinfo.address}</div>
                </div>
            </li>
            </c:forEach>                      
		</ul>
		
		<div id="pullUp">
			
		</div>
		
	</div>
</div>
        
        <div class="drsj-xy" style=" height:500px;">
        	
        </div>
        
    </div>
</div>
<input type="hidden" name="openid" value="${param.openid}">
</form>

</body>
<script>
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: '.swiper-pagination',
   //   nextButton: '.swiper-button-next',
   //     prevButton: '.swiper-button-prev',
        spaceBetween: 30
    });
	var aa = $(window).height();
$('.guide').css('min-height',aa)
function FormatDate (strTime) {
    var date = new Date(strTime);
    var year=date.getFullYear();
     var month=date.getMonth();
     var dates= date.getDate();
     if(month==0)
     {
     year=year-1;
     month=12;
     }
    return year+"-"+month+"-"+dates;
}
function addDate(date,days){ 
var d=new Date(date); 
d.setDate(d.getDate()+days); 
var month=d.getMonth()+1; 
var day = d.getDate(); 
if(month<10){ 
month = "0"+month; 
} 
if(day<10){ 
day = "0"+day; 
} 
var val = d.getFullYear()+""+month+""+day; 
return val; 
}
function page_pre()
{
  var dd=$("#date").attr("value");
 var  arr = dd.split("-");
 var now =new Date();
 now.setFullYear(arr[0],arr[1],arr[2]);
 var yesterday=new Date(now.getTime()-1000*60*60*24);
 $("#date").attr("value",FormatDate(yesterday));//填充内容 
 document.forms[0].submit();
}
function page_next()
{
  var dd=$("#date").attr("value");
 var  arr = dd.split("-");
 var now =new Date();
 var today=new Date();
 now.setFullYear(arr[0],arr[1],arr[2]);
 if(now.getDate()-today.getDate()==0&&now.getMonth()-(today.getMonth()+1)==0)
 {
  alert("对不起，你无法查看明天的数据 ")
 }
 else
 {
  var yesterday=new Date(now.getTime()+1000*60*60*24);
  $("#date").attr("value",FormatDate(yesterday));//填充内容 
 document.forms[0].submit();
 }
}
</script>
</html>
