<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KingIM</title>
    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
            <h6 class="logo-name">KingIM</h6>
            </div>
            <h3>基于WebSocket+layim的即时通讯系统</h3>
			       <form action="/index/loginCheck" method="post">
			              <div class="form-group">
			                  <input type="username" class="form-control" placeholder="用户名" name="userName" required/>
			              </div>
			              <div class="form-group">
			                  <input type="password" class="form-control" placeholder="密码" name="password" required/>
			              </div>
			              <button type="submit" id="sbBtn" class="btn btn-primary block full-width m-b">登录</button>
			               <p class="text-muted text-center" style="font-size:14px;color:red"> ${msg} </p>
					 	   <p class="text-muted text-center"><a href="javascript:;">忘记密码</a> | <a href="javascript:;">注册新账号</a></p>
			       </form>
        </div>
    </div>
</body>
</html>

