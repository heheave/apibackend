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
        <script type="text/javascript" src="/js/page/userscenedetail.js"></script>
	</head>
	<script>
   　    var user = "<%=((com.sjtu.pojo.User)(session.getAttribute("id"))).getUid()%>"
         var sid = getQueryString("sid")
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
                                    <a href = "/page/forward?page=user">个人主页</a>
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
                        	    场景详情
                        	</li>
                        	<li class="active">
                        	    <a href="#"><i class="icon-list-alt"></i>场景详情</a>
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
				<div class="span9" id = "detail">
                    <h2 id = 'title'>场景列表</h2>
                    <table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									设备名称
								</th>
								<th>
									监测值
								</th>
								<th>
                                    检测时间
                                </th>
                                <th>
                                    是否合法
                                </th>
							</tr>
						</thead>
						<tbody id = "devListTbl"></tbody>
					</table>
                    <div class="thumbnails" id = "appdetail-pane">
                        <div class = "mytip" id = "mytip" style = "display:none">
                        <ul class="messages well">
    					    <li class="my-well1">
                                <span class="message" id= "detail-pname"><b>设备名:&ensp;&ensp;</b>xiaoke</span>
                            </li>
                            <li class="my-well1">
                                <span class="message" id= "detail-pvalue"><b>监测值:&ensp;&ensp;</b>xiaoke</span>
                        	</li>
                        	<li class="my-well1">
                                <span class="message" id= "detail-ptime"><b>检测时间:&ensp;&ensp;</b>xiaoke</span>
                            </li>
                            <li class="my-well1">
                                <span class="message" id= "detail-pvalie"><b>是否合法:&ensp;&ensp;</b>xiaoke</span>
                            </li>
                        </ul>
                        </div>
       			    </div>
                    <div class="form-actions" style="text-align:center">
                        <button type="submit" class="btn" onclick = "back()">Back</button>
                    </div>
				</div>

                <div class="span9" id = "detail1" style = "display:none">
                    <h2 id = 'title1'>设备详情</h2>
                    <table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									项目
								</th>
								<th>
									详情
								</th>
							</tr>
						</thead>
						<tbody>
						    <tr><th>设备名</th><th id = "pname"></th></tr>
						    <tr><th>输出描述</th><th id = "poutdesc"></th></tr>
						    <tr><th>输出类型</th><th id = "pouttype"></th></tr>
						    <tr><th>输出单位</th><th id = "poutunit"></th></tr>
						    <tr><th>设备描述</th><th id = "pdesc"></th></tr>
						    <tr><th>均值类型</th><th id = "pavg"></th></tr>
						    <tr><th>均值时间</th><th id = "pavgtime"></th></tr>
						    <tr><th>均值</th><th id = "pavgvalue"></th></tr>
						    <tr><th>实时值</th><th id = "prealvalue"></th></tr>
						</tbody>
					</table>
                    <div class="thumbnails" id = "realvaluehis"></div>
                    <div class="form-actions" style="text-align:center">
                        <button type="submit" class="btn" onclick = "back1()">Back</button>
                    </div>
				</div>
			</div>
		</div>
	</body>
</html>