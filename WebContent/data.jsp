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
    <title>数据分析</title>
    <link rel="stylesheet" type="text/css" href="dhj/css/style.css" media="all"> 
    <link rel="stylesheet" type="text/css" href="dhj/css/slide.css" media="all">
    <script type="text/javascript" src="dhj/js/jquery.js"></script>
    <script type="text/javascript" src="dhj/js/smoke.js"></script>
    <script type="text/javascript" src="dhj/js/jy.js"></script>
    <script type="text/javascript" src="dhj/js/hx.js"></script>
   <script type="text/javascript" src="dhj/js/data.js"></script>
<script type="text/javascript" src="dhj/js/jquery.json.js"></script>
   <script type="text/javascript">
    var jiaoyou1=${mouthsum*Cigarette.ctar};
    var jiaoyou2=${target*30*Cigarette.ctar}
    var qujian= new Array(); 
    var mouthcount = new Array(); 
    var riqicount=new Array(); 
    var kongyan = new Array();
    var riqikongyan=new Array();
     var showdata = new Array();
     var riqidata=new Array();
      var riqi= new Array(); 
    var yanse = new Array("#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9","#fd74b5");
      var j=0;var t=0;
  
   <c:forEach items="${requestScope.mouthmap}" var="mouthabc" >
   var thing ={y: parseInt("${mouthabc.value*Cigarette.cprice}"), color:yanse[j]};  
   showdata[j]=thing;
   qujian[j]="${mouthabc.key}";
   mouthcount[j]=parseInt("${mouthabc.value}");
   kongyan[j]=parseInt("${target*7}");
  // showdata[j]= jQuery.toJSON(thing);
   j=j+1;
      </c:forEach>  
   
       <c:forEach items="${requestScope.weekmap}" var="weekabc" >
      
        var thing1 ={y:parseInt("${mouthabc.value*Cigarette.cprice}"), color:yanse[t]}; 
        riqidata[t]=thing;
        riqi[t]="${weekabc.key}";
        riqicount[t]=parseInt("${weekabc.value}");     
        riqikongyan[t]=parseInt("${target}");
        t=t+1;
        riqidata[t]=jQuery.toJSON(thing1);
        </c:forEach>   
        
   </script>
  </head> 
  <body>
  <div class="scroll guide"> 
	<!--  本月吸烟数 -->
    <div class="Menubox">
        <ul>
            <li id="one1" onclick="setTab('one',1,2)"  class="hover">日</li>
            <li id="one2" onclick="setTab('one',2,2)" >月</li>
        </ul>
    </div>
    <div class="Contentbox">
    <div id="con_one_1" class="hover">
    
    	<div class="smoke">
            <div class="smoketit" >本周吸烟数量<span style="width:10%; color:#FFF; float:right; margin-right:10%;">支</span></div>
            <div id="container" style="min-width:100%;height:400px;margin-top:10px;"></div>
            <div class="smokesetting">
                <div class="smokesettingfont">本周控烟目标：<span>${target*7}</span>支</div>
                <div class="smokesettingfont">本周吸烟数：<span>${weeksum}</span>支</div>
            </div>
        </div>
    
    <!--  焦油含量 -->
        <div class="smoke">
            <div class="smoketit jytit" >焦油含量</div>
            <div class="jthlcon">
            <c:forEach items="${requestScope.weekmap}" var="weekabc" >
             <li>
                    <div class="jthlconimg"><img src="dhj/images/sjicon.png" /></div>
                    <div class="jthlconsj">${weekabc.value*Cigarette.ctar}mg</div>
                    <div class="jthlconsj">${weekabc.key} </div>
                </li>
            </c:forEach>                           
            </div>
    	</div>
    
    </div>
    
    <div id="con_one_2" style="display:none">
    
    	<div class="smoke">
            <div class="smoketit" >本月吸烟数量 <span style="width:10%; color:#FFF; float:right; margin-right:10%;">支</span></div>
            <div id="container1" style="min-width:100%;height:400px;margin-top:10px; "></div>
            <div class="smokesetting">
                <div class="smokesettingfont">本月控烟目标：<span>${target*30}</span>支</div>
                <div class="smokesettingfont">本月吸烟数：<span>${mouthsum}</span>支</div>
            </div>
        </div>
    
    <!--  焦油含量 -->
        <div class="smoke">
            <div class="smoketit jytit" >焦油含量</div>
            <div class="jthlcon jthlconmouth">
              <c:forEach items="${requestScope.mouthmap}" var="mouthabc" >
               <li>
                    <div class="jthlconimg"><img src="dhj/images/sjicon.png" /></div>
                    <div class="jthlconsj">${mouthabc.value}mg</div>
                    <div class="jthlconsj">${mouthabc.key}</div>
                </li>
              </c:forEach>
               
              
              
            </div>
    </div>
    </div>
    
    
    
    
    
    
    
    
    
   
        
     <!--  本月花销 -->
    <div class="smoke">
    	<div class="smoketit hxtit" >本月花销</div>
    	<div id="containe_hx" style="min-width:320px;height:350px;margin-top:10px;"></div>
        <div class="smokesettingfont">本月吸烟花销：<span>${mouthsum*Cigarette.cprice}</span>元</div>
    </div>
    
</div>
<script>
$('#day-mouth a').click(function(){
  $(this).addClass('djbg');
  $(this).siblings('a').removeClass('djbg');
});
</script> 
<script>
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}
</script>
</body>
</html>