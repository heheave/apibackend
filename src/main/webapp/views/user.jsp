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
		<title>User Mainpage</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/bs/css/bootstrap.min.css" rel="stylesheet">
		<link href="/bs/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="/css/site.css" rel="stylesheet">
		<link href="/css/page.css" rel="stylesheet">

		<script type="text/javascript" src="/js/jquery.min.js"></script>
        <script type="text/javascript" src="/js/highcharts.js"></script>
        <script type="text/javascript" src="/js/format.js"></script>
        <script type="text/javascript" src="/js/url.js"></script>
        <script type="text/javascript" src="/js/paging.js"></script>
        <script type="text/javascript" src="/bs/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/js/site.js"></script>
        <script type="text/javascript" src="/js/page/user.js"></script>
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
						<a class="brand" href="#">User</a>
						<div class="nav-collapse">

							<ul class="nav">
							<!--
								<li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">用户管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="/user/getUsers">用户列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=userAdd">添加用户</a>
                                	    </li>
                                    </ul>
                                </li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">应用管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="/app/list">应用列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=appAdd">添加应用</a>
                                	    </li>
                                    </ul>
                                </li>

                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">设备管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="/page/forward?page=dcodeList">设备码列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=pointerList">设备配置</a>
                                	    </li>
                                    </ul>
                                </li>
                                -->
                                <li>
                                    <a href = "#">个人主页</a>
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
                        	    场景列表
                        	</li>
                        	<li class="active">
                        	    <a href="#"><i class="icon-list-alt"></i>场景列表</a>
                        	</li>
                        	<!--
							<li class="nav-header">
								用户管理
							</li>
							<li>
								<a href="/user/getUsers"><i class="icon-user"></i>用户列表</a>
							</li>
							<li>
								<a href="/page/forward?page=userAdd"><i class="icon-plus"></i>添加用户</a>
							</li>

							<li class="nav-header">
								应用管理
							</li>
							<li>
								<a href="/app/list"><i class="icon-th"></i>应用列表</a>
							</li>
							<li>
								<a href="/page/forward?page=appAdd"><i class="icon-plus"></i>添加应用</a>
							</li>

                            <li class="nav-header">
							    设备管理
							</li>
							<li>
								<a href="/page/forward?page=dcodeList"><i class="icon-barcode"></i>设备码列表</a>
							</li>
							<li>
								<a href="/page/forward?page=pointerList"><i class="icon-th-list"></i>设备列表</a>
							</li>
							-->
						</ul>
					</div>
				</div>
				<div class="span9">
					<h2>
						设备列表
					</h2>
                    <table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									场景名
								</th>
								<th>
									应用名
								</th>
								<th>
                                    接入设备总数量
                                </th>
                                <th>
                                    在线设备数量
                                </th>
							</tr>
						</thead>
						<tbody id = "ainfoListTbl"></tbody>
					</table>
					<div class = "pagination">
                        <div class="box" id = "box"></div>
                    </div>
                    <script>
					    var pageNum = default_get("/app/summary-scene-cnt")
                        $('#box').paging({
                                initPageNo: 1,
                                totalPages: pageNum,
                                totalCount: "",
                                slideSpeed: 600,
                                jump: false,
                                callback: function(page) {
                                    skipToPage(page)
                                }
                        })
                    </script>
					<!--
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
								<td>/login/logout
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
                    -->
				</div>
			</div>
		</div>
	</body>
</html>