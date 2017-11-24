<%@ page language="java" import="java.util.*" import="com.sjtu.pojo.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<!--[if lt IE 7 ]><html lang="en" class="ie6 ielt7 ielt8 ielt9"><![endif]--><!--[if IE 7 ]><html lang="en" class="ie7 ielt8 ielt9"><![endif]--><!--[if IE 8 ]><html lang="en" class="ie8 ielt9"><![endif]--><!--[if IE 9 ]><html lang="en" class="ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> 
<html lang="en"><!--<![endif]--> 
	<head>
		<meta charset="utf-8">
		<title>Admin Mainpage</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/bs/css/bootstrap.min.css" rel="stylesheet">
		<link href="/bs/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="/css/site.css" rel="stylesheet">

		<script type="text/javascript" src="/js/jquery.min.js"></script>
        <script type="text/javascript" src="/js/highcharts.js"></script>
        <script type="text/javascript" src="/js/format.js"></script>
        <script type="text/javascript" src="/js/url.js"></script>
        <script type="text/javascript" src="/bs/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/js/site.js"></script>
        <script type="text/javascript" src="/js/page/admin.js"></script>
	</head>
	<script>
   　    var user = "<%=((com.sjtu.pojo.User)(session.getAttribute("id"))).getUid()%>"
    </script>
	<body>
		<div class="container">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						    <span class="icon-bar"></span>
						    <span class="icon-bar"></span>
						    <span class="icon-bar"></span>
						</a>
						<a class="brand" href="#">Admin</a>
						<div class="nav-collapse">
							<ul class="nav">

								<li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">租户管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="/user/getUsers">租户列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=tenantAdd">添加租户</a>
                                	    </li>
                                    </ul>
                                </li>

                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">设备管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="/page/forward?page=devicelist">设备列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=devicecode">设备码生成</a>
                                	    </li>
                                    </ul>
                                </li>
							</ul>

							<form class="navbar-search pull-left" action="">
								<input type="text" class="search-query span2" placeholder="搜索" />
							</form>

							<ul class="nav pull-right">
								<li>
									<a href="#" style = "color:blue"><%=((com.sjtu.pojo.User)(session.getAttribute("id"))).getUnick()%></a>
								</li>

								<li>
									<a href="/login/logout">Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="span3">
					<div class="well" style="padding: 8px 0;">
						<ul class="nav nav-list">
						    <li class="nav-header">
                        	    主页显示
                        	</li>
                        	<li class="active">
                        	    <a href="#"><i class="icon-list-alt"></i>系统概况</a>
                        	</li>
							<li class="nav-header">
								租户管理
							</li>
							<li>
								<a href="/user/getUsers"><i class="icon-th-list"></i>租户列表</a>
							</li>
							<li>
								<a href="/page/forward?page=tenantAdd"><i class="icon-plus"></i>添加租户</a>
							</li>

                            <li class="nav-header">
							    设备管理
							</li>
							<li>
								<a href="/page/forward?page=devicelist"><i class="icon-th-list"></i>设备列表</a>
							</li>
							<li>
								<a href="/page/forward?page=devicecode"><i class="icon-qrcode"></i>设备码生成</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<h2>
						机器监控
					</h2>
					<div>
					    <ul id="myTab" class="nav nav-tabs">
                    	    <li class="active"><a href="#cpu" data-toggle="tab">CPU</a></li>
                    	    <li><a href="#mem" data-toggle="tab">MEM</a></li>
                    	    <li><a href="#io" data-toggle="tab">IO</a></li>
                    	    <li><a href="#net" data-toggle="tab">NET</a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <div class="tab-pane fade hero-unit in active" id="cpu" style="padding:10px"></div>
                            <div class="tab-pane fade hero-unit" id="mem" style="padding:10px"></div>
                            <div class="tab-pane fade hero-unit" id="io" style="padding:10px"></div>
                            <div class="tab-pane fade hero-unit" id="net" style="padding:10px"></div>
                        </div>
					</div>
					<div class="well summary">
						<ul>
							<li>
								<a href="#"><span class="count" id = "msgNum"></span> 消息总数</a>
							</li>
							<li>
								<a href="#"><span class="count" id = "msgBytes"></span> 消息总量</a>
							</li>
							<li>
								<a href="#"><span class="count" id = "msgNumToday"></span> 日消息总数</a>
							</li>
							<li class="last">
								<a href="#"><span class="count" id = "msgBytesToday"></span> 日消息总量</a>
							</li>
						</ul>
					</div>
					<div class = "well summary">
					    <div id = "pip1" class = "my-para-left"></div>
					    <div id = "pip2" class = "my-para-right"></div>
					</div>
					<div style = "clear:both"></div>
					<h2>
						日志信息
					</h2>
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									姓名
								</th>
								<th>
									时间
								</th>
								<th>
									动作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									小明
								</td>
								<td>
									2071-11-11 19:20:00
								</td>
								<td>
									查看了设备信息
								</td>
							</tr>
						</tbody>
					</table>
                    <ul class="pager">
						<li class="next">
							<a href="activity.htm">More &rarr;</a>
						</li>
					</ul>

				</div>
			</div>
		</div>
	</body>
</html>