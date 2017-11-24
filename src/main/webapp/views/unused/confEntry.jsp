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
<script type="text/javascript" src="/js/confEntry.js"></script>

<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/confEntry.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<script type="text/javascript">
    var appName = getQueryString("caname");
    var dmark = getQueryString("cdmark");
    var port = getQueryString("cpidx");
</script>

<body>
    <div id = "app" class = "container">
        <div class = "canvas">
            <div class = "title">Modify Port Config</div>
            <!-- <form id = "addForm" action = "/app/addApp" method = "post"> -->
                        <div class="input_div">
                            Cmd：<input id="cmd" type="text" name="ccmd"
                            onclick="textareaOnclick(this, 'input conf cmd')"
                            onblur="textareaOnblur(this, 'input conf cmd')"
                            value = "input conf cmd" />
                        </div>

                        <div class="input_div">
                            Avg：<input id="avg" type="text" name="cavg"
                            onclick="textareaOnclick(this, 'input conf avg')"
                            onblur="textareaOnblur(this, 'input conf avg')"
                            value = "input conf avg"/>
                        </div>

            <!-- </form> -->

            <div class="bottom_btn">
                <button class="mybutton" onclick="back()">返回</button>
                <button class="mybutton" onclick="del()">删除</button>
                <button class="mybutton" onclick="modify()">修改</button>
            </div>

        </div>
    </div>
</body>


</html>