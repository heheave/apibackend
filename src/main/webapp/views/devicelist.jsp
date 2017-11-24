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
        <link href="/css/page.css" rel="stylesheet">

		<script type="text/javascript" src="/js/jquery.min.js"></script>
        <script type="text/javascript" src="/js/url.js"></script>
        <script type="text/javascript" src="/js/format.js"></script>
        <script type="text/javascript" src="/js/paging.js"></script>
        <script type="text/javascript" src="/bs/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/js/site.js"></script>
        <script type="text/javascript" src="/js/page/devicelist.js"></script>

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
                                		    <a href="#">设备列表</a>
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
									<a href="#" style = "color:blue"><%=((com.sjtu.pojo.User)(session.getAttribute("id"))).getUname()%></a>
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
                        	<li>
                        	    <a href="/page/forward?page=admin"><i class="icon-list-alt"></i>系统概况</a>
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
							<li class="active">
								<a href="#"><i class="icon-th-list"></i>设备列表</a>
							</li>
							<li>
								<a href="/page/forward?page=devicecode"><i class="icon-qrcode"></i>设备码生成</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="span9" id = "devList">
                    <h1>
						设备情况
					</h1>
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									设备码
								</th>
								<th>
                                    所属租户
                                </th>
								<th>
									协议
								</th>
								<th>
									所连机器
								</th>
                                <th>
                                    是否在线
                                </th>
							</tr>
						</thead>
						<tbody id = "devListTbl"></tbody>
					</table>
					<div class = "pagination">
                    <div class="box" id = "box">

					</div>
					</div>
					<script>
					    var pageNum = default_get("/device/cnt-dcode")
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
				</div>
                <div class="span9" style = "display:none" id = "devDetail">
                    <h1>
						设备详情
					</h1>
					<table class="table table-bordered table-striped">
						<thead>
							<tr><th>名称</th><th>详情</th></tr>
						</thead>
						<tbody>
							<tr><td>设备码</td><td id = "dcode"></td></tr>
							<tr><td>所属租户</td><td id = "dcnick"></td></tr>
							<tr><td>协议</td><td id = "pro"></td></tr>
							<tr><td>所连接器IP</td><td id = "mip"></td></tr>
							<tr><td>初次连接时间</td><td id = "ftime"></td></tr>
							<tr><td>最后连接时间</td><td id = "ltime"></td></tr>
							<tr><td>平均发包时间</td><td id = "pavg"></td></tr>
							<tr><td>已发包数量</td><td id = "dmn"></td></tr>
							<tr><td>已发包字节</td><td id = "dmb"></td></tr>
							<tr><td>数据包字段数</td><td id = "dma"></td></tr>
						</tbody>
					</table>
					<div class="form-actions" style="text-align:center; background-color:white">
                        <button class="btn" onclick = "back()">Back</button>
                    </div>
				</div>

			</div>
		</div>
	</body>
</html>