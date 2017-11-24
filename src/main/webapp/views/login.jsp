<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>登录</title>
	<link href="/bs/css/bootstrap.min.css" rel="stylesheet">
    <link href="/bs/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/css/site.css" rel="stylesheet">


    <script src="/js/jquery.min.js"></script>
    <script src="/bs/js/bootstrap.min.js"></script>
    <script src="/js/site.js"></script>


<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body>
   <div id="login-page" class="container">
   			<h1 style="text-align:center">用户登录</h1>
   			<form id="loginForm" class="well" action="/login/postLogin" method="post">
   			<div class="control-group">
                <label class="control-label" for="input01">用户名:</label>
            	<div class="controls">
            	    <input type="text" class="input-xlarge" name = "unick"  placeholder="User Nick"/>
            	</div>
            </div>

            <div class="control-group">
			    <label class="control-label" for="input01">密&ensp;&ensp;码:</label>
			    <div class="controls">
				    <input type="password" class="input-xlarge" name = "upasswd" placeholder="Password"/>
			    </div>
			</div>
   			<label class="checkbox"> <input type="checkbox" /> Remember me </label>
   			<button type="submit" class="btn btn-primary">Sign in</button>
   			<button type="submit" class="btn">Forgot Password</button>
   		</form>
   </div>
</body>


</html>