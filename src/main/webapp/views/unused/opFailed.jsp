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

<link href="/css/board.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body onload="closeWindow();">

	<script type="text/javascript">
		var time = 10;
		function closeWindow() {
			var tid=window.setTimeout('closeWindow()', 1000);
			if (time > 0) {
				document.getElementById("show").innerHTML = "<font color=red>"
						+ time + "</font>秒后自动转回";
				time--;
			} else {
				window.clearTimeout(tid);
				history.go(-1);
			}
		}
	</script>

	<div class="container">
		<div class="hint">
			访问出错！<br />请检查您的网络或输入信息！ <a id="show"
				href="javascript:history.go(-1)"></a>
		</div>
	</div>
</body>


</html>