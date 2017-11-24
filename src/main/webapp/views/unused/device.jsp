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
<script type="text/javascript" src="/js/device.js"></script>

<link href="/css/simplecss.css" rel="stylesheet" type="text/css" />
<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/device.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<script type="text/javascript">
    var appName = getQueryString("appName");
    var dmark = getQueryString("dmark");
</script>

<body>
    <div class = "container">
        <div class = "canvas">
            <div class = "title" id = "dmark"></div>
            <div class = "title1">Basic Info</div>
            <div class="myinput_div">
                <div class="myinput_cell">
            		Type：<div id="dtype"></div>
            	</div>

            	<div class="myinput_cell">
            	    Company：<div id="dcompany"></div>
            	</div>
            </div>

            <div class="myinput_div">
                <div class="myinput_cell">
                    Location：<div id="dlocation"></div>
                </div>

                <div class="myinput_cell">
            	    Desc：<div id="ddes"></div>
            	</div>
            </div>

            <div class="myinput_div">
                <div class="myinput_cell">
                    Last DTime：<div id="lst_dtime"></div>
                </div>

                <div class="myinput_cell">
            	    Last Time：<div id="lst_ptime"></div>
            	</div>
            </div>

            <div id = "port-list-title" class = "title1"></div>
            <table cellspacing = 0 cell-padding = 0 id = "port-table" class = "scss_table">
                        <tr class = "scss_title_tr">
                            <td class = "scss_td">Desc</td>
                            <td class = "scss_td">Value</td>
                            <td class = "scss_td">Valid</td>
                            <td class = "scss_td">Unit</td>
                            <td class = "scss_td">Avg Time</td>
                            <td class = "scss_td">Avg Value</td>
                        </tr>
            </table>

            <div class = "title1">Realtime Monitoring</div>
            <div id = "shows">
                    <div id = "his-chart" class = 'div_chart'></div>
                </div>

            <div class="bottom_btn">
                <button class="mybutton" onclick="back()">返回</button>
                <button class="mybutton" onclick="del()">删除</button>
            </div>
        </div>

    </div>

</body>


</html>