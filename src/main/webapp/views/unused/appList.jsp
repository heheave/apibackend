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
<script type="text/javascript" src="/js/appList.js"></script>

<link href="/css/simplecss.css" rel="stylesheet" type="text/css" />
<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/appList.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body>

    <div class = "container">
        <div class = "canvas">
            <div class = "title">App List</div>
            <table cellspacing = 0 cell-padding = 0 id = "app-table" class = "scss_table">
                        <tr class = "scss_title_tr">
                            <td colspan = "5" class = "scss_td">Devices</td>
                        </tr>

                        <tr class = "scss_title_tr">
                            <td class = "scss_td">Index</td>
                            <td class = "scss_td">App Name</td>
                            <td class = "scss_td">Company</td>
                            <td class = "scss_td">Desc</td>
                            <td class = "scss_td">Create Time</td>
                        </tr>
                    </table>


            <div class="bottom_btn">
                <button class="mybutton" onclick="update()">刷新</button>
                <button class="mybutton" onclick="create()">创建</button>
            </div>
        </div>
    </div>
</body>


</html>