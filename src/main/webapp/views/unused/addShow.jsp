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
<script type="text/javascript" src="/js/addShow.js"></script>

<link href="/css/btn.css" rel="stylesheet" type="text/css" />
<link href="/css/board.css" rel="stylesheet" type="text/css" />
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/addShow.css" rel="stylesheet" type="text/css" />

<!--[if IE 8]>
		<link href="css/common-ie8.css" rel="stylesheet" type="text/css" />
		<link href="css/calendar-ie8.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<script type="text/javascript">
    var appName = getQueryString("appName");
</script>

<body>
    <div id = "app" class = "container">
        <div id="gray"></div>
        <div class = "canvas">
            <div class = "title">Add Comparison To APP</div>

                        <div class="input_div">
                            Type：<select id="type" name="stype">
                                    <option value ="CMP">CMP</option>
                                    <option value ="ADD">ADD</option>
                                    <option value="SUB">SUB</option>
                                    <option value="MUL">MUL</option>
                                    <option value="DIV">DIV</option>
                                  </select>
                        </div>

                        <div class="input_div">
                            Desc：<input id="desc" type="text" name="sdesc"
                            onclick="textareaOnclick(this, 'input device type')"
                            onblur="textareaOnblur(this, 'input device type')"
                            value = "input device type"/>
                        </div>

                        <div class="input_div">
                            Number：<input id="cnum" type="text" name="scnum"
                            onclick="textareaOnclick(this, 'input comparison number')"
                            onblur="textareaOnblur(this, 'input comparison number')"
                            value = "input comparison number"/>
                        </div>

            <div class="popup" id="popup">
				<div class="top_nav" id='top_nav'>
					<div align="center">
						<span>Set Port Information</span> <a class="guanbi"></a>
					</div>
				</div>

                <div id = "portcell">
				    <div class="input_div">
					    <div class="myinput_cell_long">
						    Port 1： <input id="1" />
					    </div>
                    </div>
                    <div class="input_div">
					    <div class="myinput_cell_long">
						    Port 2： <input id="2" />
					    </div>
                    </div>
                </div>

                <div class="bottom_btn">
                    <button class="mybutton" style = "disabled:true" onclick="mysubmit()">提交</button>
                </div>
			</div>


            <div class="bottom_btn">
                <button class="mybutton" onclick="back()">返回</button>
                <button class="mybutton" onclick="next()">确认</button>
            </div>

        </div>
    </div>
</body>


</html>