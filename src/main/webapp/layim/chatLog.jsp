<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>聊天记录</title>
	<link rel="stylesheet" type="text/css" href="js/plugins/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="js/plugins/layui/css/pc/layim/layim.css" />
	<link rel="stylesheet" type="text/css" href="js/plugins/layui/css/chatting.css" />
  </head>
  <body>


  	  <p class="showMore"><a href="javascript:;" id="moreLog">查看更多消息</a></p>
  	  
	  <div class="layim-chat-main">
		  <ul id="messageList">
		  
		<!--  <li>
				  <div class="layim-chat-user">
					  <img src="http://tva1.sinaimg.cn/crop.7.0.736.736.50/bd986d61jw8f5x8bqtp00j20ku0kgabx.jpg">
					  <cite>Hi<i>2016-8-19 10:15:26</i></cite>
				  </div>
			  	  <div class="layim-chat-text">嗨，你好！该模块尚在开发中</div>
			  </li>
			  
			  <li class="layim-chat-mine">
				  <div class="layim-chat-user">
					  <img src="http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg">
					  <cite><i>2016-8-19 10:47:42</i>纸飞机</cite>
				  </div>
				  <div class="layim-chat-text">您好</div>
			  </li>   -->
			  
		  </ul>
	  </div>
	  
	 <input id="message" type="hidden" value='${jsonStr}'>
	 <input id="toId" type="hidden" value='${toId}'>
	 <input id="type" type="hidden" value='${type}'>
	 <input id="userId" type="hidden" value='${sessionScope.userInfo.id}'>
	 		
	  
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>	  
<script type="text/javascript" src="js/plugins/layui/chatLog.js"></script>
</body>
</html>
