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
<script type="text/javascript" src="/js/app.js"></script>

<link href="/css/simplecss.css" rel="stylesheet" type="text/css" />
<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/app.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<script type="text/javascript">
    var appId = getQueryString("appId");
</script>

<body>
    <div class = "container">
        <div id="gray"></div>
        <div class = "canvas">
            <div class = "title" id = "appName"></div>
            <div class = "title1">Basic Info</div>
            <div class="myinput_div">
                <div class="myinput_cell">
            		所属：<div id="acompany">123</div>
            	</div>

            	<div class="myinput_cell">
            	    描述：<div id="adesc">213</div>
            	</div>
            </div>

            <div class="myinput_div">
                <div class="myinput_cell_long">
                    创建时间：<div id="acrttime">21321</div>
                </div>

            </div>

            <div class = "title1">
                Devices List
                <button class="mybutton1" onclick="addDevice()">Add Device</button>
            </div>
            <table cellspacing = 0 cell-padding = 0 id = "app-table" class = "scss_table">
                        <tr class = "scss_title_tr">
                            <td class = "scss_td">Index</td>
                            <td class = "scss_td">Id</td>
                            <td class = "scss_td">Type</td>
                            <td class = "scss_td">Company</td>
                            <td class = "scss_td">Location</td>
                            <td class = "scss_td">Desc</td>
                        </tr>
            </table>

            <div class = "title1">
                Comparison Charts
                <button class="mybutton1" onclick="popunshow()">Del Device</button>
                <button class="mybutton1" style="margin-right:30px" onclick="addShow()">Add</button>
            </div>
            <div id = "shows"></div>

            <div class="popup" id="popup">
				<div class="top_nav" id='top_nav'>
					<div align="center">
						<span>Set Port Information</span> <a class="guanbi"></a>
					</div>
				</div>

                <div id = "portcell">
				    <div class="input_div">
					    <div class="myinput_cell_long">
						    Show Name： <select id="show-name"></select>
					    </div>
                    </div>
                </div>

                <div class="bottom_btn">
                    <button class="mybutton" style = "disabled:true" onclick="delShow()">提交</button>
                </div>
			</div>

            <div class="bottom_btn">
                <button class="mybutton" onclick="back()">返回</button>
                <button class="mybutton" onclick="del()">删除</button>
            </div>
        </div>

    </div>
</body>


</html>