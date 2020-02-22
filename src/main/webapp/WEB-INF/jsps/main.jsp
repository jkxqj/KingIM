<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<title>KingIM</title>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>"></base>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="layim/css/layui.css" media="all"/>
</head>
<body style="background-color:#C8C8C8; height:100%">
<input type="hidden" id="userId" value="${user.id}"/>

<a class="layui-btn" href="index/logout">退出登录</a>
<p style="font-size:50px; text-align:center;margin-top:20px">Hello KingIM!</p>
<p style="font-size:20px; text-align:center;margin:20px">WebSocket、netty、layim、Spring MVC、MyBatis</p>
<script src="js/jquery.min.js"></script>
<script src="layim/layui.js"></script>
<script src="layim/im.js"></script>
</body>
</html>

