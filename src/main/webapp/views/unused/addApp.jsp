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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<base href="<%=basePath%>">
<title>App page</title>

<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/js/highcharts.js"></script>
<script type="text/javascript" src="/js/format.js"></script>
<script type="text/javascript" src="/js/url.js"></script>
<script type="text/javascript" src="/js/textinput.js"></script>
<script type="text/javascript" src="/js/addApp.js"></script>

<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/addApp.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body>
    <div id = "app" class = "container">
        <div class = "canvas">
            <div class = "title">创建APP</div>
            <form id = "addForm" action = "/app/addApp" method = "post">
                        <div class="input_div">
                            AppName：<input id="appName" type="text" name="appName"
                            onclick="textareaOnclick(this, 'input app name')"
                            onblur="textareaOnblur(this, 'input app name')"
                            value = "input app name" />
                        </div>

                        <div class="input_div">
                            Company：<input id="company" type="text" name="acompany"
                            onclick="textareaOnclick(this, 'input company name')"
                            onblur="textareaOnblur(this, 'input company name')"
                            value = "input company name"/>
                        </div>

                        <div class="input_div">
                            Desc：<input id="desc" type="text" name="adesc"
                            onclick="textareaOnclick(this, 'input app desc')"
                            onblur="textareaOnblur(this, 'input app desc')"
                            value = "input app desc"/>
                        </div>

            </form>

            <div class="bottom_btn">
                <button class="mybutton" onclick="back()">返回</button>
                <button class="mybutton" onclick="submit()">提交</button>
            </div>

        </div>
    </div>
</body>


</html>