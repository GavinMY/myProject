<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
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
    <title>设 置</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
   <link rel="stylesheet" type="text/css" href="dhj/css/swiper.min.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/swiper.min.js"></script>
    <script type='text/javascript' src='/demo/dwr/interface/switchs.js'></script>
    <script type='text/javascript' src='/demo/dwr/engine.js'></script>
    <script type='text/javascript' src='/demo/dwr/util.js'></script>
  </head>   
<body>
<div class="scroll guide"> 
<form action="servlet/EquipmentServlet" method="post" name="form1" onsubmit="return checkThat()">
	<div class="setting"> 	 
        <!--  设备设置 -->
        <div class="setting-sb">
        	<h3>设备设置</h3>
            <div class="setting-sblist" id="border1">
            	<li>
                	<span class="setting-sblisticon"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">点火开关</div>                      
							 <c:choose>
								    <c:when test="${equipment.FEN==1}">
								      <div class="setting-sblisttb" id="fire"><img src="dhj/images/open.png" /></div>
								    </c:when>   
								    <c:otherwise>
								         <div class="setting-sblisttb" id="fire"><img src="dhj/images/close.png" /></div>
								    </c:otherwise>
							</c:choose> 
                    </span>
                </li>
                <li>
                	<span class="setting-sblisticon setting-sblistsy"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">声音开关</div>                       
                         <c:choose>
						    <c:when test="${equipment.ALS==1}">
						     <div class="setting-sblisttb" id="fengming"><img src="dhj/images/open.png" /></div>						    
						    </c:when>   
						    <c:otherwise>
						     <div class="setting-sblisttb" id="fengming"><img src="dhj/images/close.png" /></div>
						      	
						    </c:otherwise>
						</c:choose>
                        <input type="hidden" name="openid" value="${openid}">
                    </span>
                </li>
              
                <li>
                	<span class="setting-sblisticon setting-sblistsxtx"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">每日控烟目标</div>
                        <div class="setting-sblisttb"><input type="text" name="ctarget" value="${map['ctarget']}" oninput="value=value.replace(/[^\d]/g,'') " class="setting-sxtx" />支</div>
                    </span>
                </li>
                 
                 <li>
                	<span class="setting-sblisticon setting-sblistdl"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">电 量</div>
                        <div class="setting-sblisttb">${equipment.BAT}%</div>
                    </span>
                </li>
            </div>
        </div>
        
        <!--  香烟设置 -->
        <div class="setting-sb setting-xy">
        	<h3>香烟设置</h3>
            <div class="setting-sblist" id="border2">
            	<li>
                	<span class="setting-sblisticon setting-sblistjg"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">价 格</div>
                        <div class="setting-sblisttb"><input type="text" name="cprice"  value="${map['cprice']}" class="setting-sxtx" oninput="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" /> &nbsp;元</div>
                    </span>
                </li>
                <li>
                	<span class="setting-sblisticon setting-sblistyh"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">一 盒</div>
                        <div class="setting-sblisttb"><input type="text" name="ccount" value="20" oninput="value=value.replace(/[^\d]/g,'') " class="setting-sxtx" /> &nbsp;支</div>
                    </span>
                </li>
                <li>
                	<span class="setting-sblisticon setting-sblistjyl"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">焦油量</div>
                        <div class="setting-sblisttb"><input type="text" name="ctar" value="${map['ctar']}" class="setting-sxtx" oninput="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />  mg</div>
                    </span>
                </li>
                 <li>
                	<span class="setting-sblisticon setting-sblistyjl"></span>
                    <span class="setting-sblistcon">
                    	<div class="setting-sblistfont">烟气烟碱量</div>
                        <div class="setting-sblisttb"><input type="text" name="soda" value="${map['soda']}" oninput="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" class="setting-sxtx" />  mg</div>
                    </span>
                </li>
            </div>
        </div>        
        <!--  个人资料 -->
        <div class="setting-sb setting-grzl">
        	<h3><a href="servlet/oauthServlet">个人资料 <img src="dhj/images/swperjtr.png" /></a></h3>
        </div>
          <div class="setting-sb setting-grzl">
        	<h3><a href="servlet/InvitationServlet">邀请好友 <img src="dhj/images/swperjtr.png" /></a></h3>
        </div>
        <!--  个人资料 -->
        <div class="setting-sb setting-hfcc">
        	<h3><a href="javascript:;">恢复出厂设置 <img src="dhj/images/swperjtr.png" /></a></h3>
        </div>        
    </div>
    
    <div class="settingcctc" >
    		<div class="settingtccon">
            	<p>请先同步数据，恢复出厂设置后，打火机中所有数据将清零</p>
                <div class="settingtcqd">确 定</div>
            </div>
    </div>
    <!-- <div class="tjbtn">确 定</div> -->
     <button type="submit" class="tjbtn">确 定</button>
</form>
</div>
<script>

$('#border1 .setting-sblistcon:last').css('border','none');
$('#border2 .setting-sblistcon:last').css('border','none');
$('.setting-sblisttb').click(function(){
    var  state= $(this).attr("id"); 
    var src =  $(this).find('img').attr('src');
    //默认图片
    var  oldsrc = 'dhj/images/close.png';
    //新图片
    var  newsrc = 'dhj/images/open.png';
    if(state=="fire")
    {
	    if( src == oldsrc )
	    {
	       $("#FEN").attr("value",'0');
	       $(this).find('img').attr('src',newsrc);
	       switchs.fireonoff("${openid}",1,function(data){
	       if(data==true)
	       {
	       alert("已允许点火 ");	       
	       }
	       })
	    }else 
	    {
	       $("#FEN").attr("value",'0');
	       $(this).find('img').attr('src',oldsrc);
	        switchs.fireonoff("${openid}",1,function(data){
	         if(data==true)
	       {
	       alert("已禁止点火")
	       }
	        });
	    }
    }
    else if(state=="fengming")
    {
        if( src == oldsrc )
	    {
	       $(this).find('img').attr('src',newsrc);
	          switchs.fengming("${openid}",1,function(data){
	         if(data==true)
	       {
	        alert("已允许蜂鸣 ");	      
	       }
	        });
	       
	    }else
	    {
	       $(this).find('img').attr('src',oldsrc);
	        switchs.fengming("${openid}",0,function(data){
	         if(data==true)
	       {
	         alert("已禁止蜂鸣 "); 
	       }
	        });
	    }
    }
 });
 
$('.setting-hfcc').click(function(){
	$('.settingcctc').css('display','block');
	
 });
 $('.settingtcqd').click(function(){
	$('.settingcctc').css('display','none');
 });
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
</body>
</html>
