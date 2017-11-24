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
        <script type="text/javascript" src="/js/url.js"></script>
        <script type="text/javascript" src="/bs/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/js/site.js"></script>
        <script type="text/javascript" src="/js/page/devicecode.js"></script>
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
                                		    <a href="#">设备码生成</a>
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
							<li>
								<a href="/page/forward?page=devicelist"><i class="icon-th-list"></i>设备列表</a>
							</li>
							<li class="active">
								<a href="#"><i class="icon-qrcode"></i>设备码生成</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="span9">
                    <h1>
                        设备码生成
                    </h1>
                    <div class="well blank-slate">
                        <p>为设备生成接入系统的唯一凭证.</p>
                        <a id = "toggle-a" href="#new-device-code" class="btn btn-primary"><i class="icon-plus icon-white"></i>为租户创建设备码</a>
                    </div>
                    <div id="new-device-code" class="form-horizontal hidden">
                    <form id = "code-gen-form" action="/device/gen-dcode">
						<fieldset>
							<legend>New Project</legend>
							<div class="control-group">
								<label class="control-label" for="input01">数量</label>
								<div class="controls">
									<input type="text" class="input-xlarge" id="input01" name = "num"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="select01">租户</label>
								<div class="controls">
									<select id="select01" style="width:285px" name= "uid"></select>
								</div>
							</div>
						</fieldset>
					</form>
					<div class="form-actions">
                        <button type="submit" class="btn btn-primary" onclick = "addSubmit()">Save</button><div class="myinterval">&ensp;</div><button class="btn" onclick="addCancel()">Cancel</button>
                    </div>
                    </div>

				</div>

			</div>
		</div>
	</body>
</html>