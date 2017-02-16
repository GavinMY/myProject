<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>mac地址授权</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
            function myCheck()
            {
               for(var i=0;i<document.form1.elements.length-1;i++)
               {
                  if(document.form1.elements[i].value=="")
                  {
                     alert("请不要提交空表单 ");
                     document.form1.elements[i].focus();
                     return false;
                  }
               }
               return true; 
            }
        </script>
  </head>
  
  <body>
   <div style="margin:0,auto;">
<form action="servlet/DeviceAuths"  method="post" onsubmit="return  myCheck()">
	<textarea rows="20%" cols="100%" name="mac" id="mac" placeholder="请依次输入mac地址，多个mac之间用逗号隔开，设备的mac地址 格式采用16进制串的方式（长度为12字节），
不需要0X前缀，如：AAAAAAAAAAAA,BBBBBBBBBBBB,CCCCCCCCCCCC" style="width:100%;ime-mode:disabled">
</textarea><br>
	<input style="text-align:center; width:50px; margin:0 auto; display:block" type="submit" value="导出" >
</form>
</div>
  </body>
</html>
