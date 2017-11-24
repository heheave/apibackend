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
        <script type="text/javascript" src="/js/page/dcodeList.js"></script>

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
						<a class="brand" href="#">Manager</a>
						<div class="nav-collapse">
							<ul class="nav">

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
                                		    <a href="/user/getUsers">应用列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=userAdd">添加应用</a>
                                	    </li>
                                    </ul>
                                </li>

                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown">设备管理<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                	    <li>
                                		    <a href="#">设备码列表</a>
                                	    </li>
                                	    <li>
                                		    <a href="/page/forward?page=devicecode">设备配置</a>
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
                        	    <a href="#"><i class="icon-list-alt"></i>主页显示</a>
                        	</li>
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
								<a href="/user/getUsers"><i class="icon-th"></i>应用列表</a>
							</li>
							<li>
								<a href="/page/forward?page=userAdd"><i class="icon-plus"></i>添加应用</a>
							</li>

                            <li class="nav-header">
							    设备管理
							</li>
							<li class="active">
								<a href="#"><i class="icon-barcode"></i>设备码列表</a>
							</li>
							<li>
								<a href="/page/forward?page=devicecode"><i class="icon-wrench"></i>设备配置</a>
							</li>
						</ul>
					</div>
				</div>

                <div class="span9">
					<h2>
                        租户信息填写
                    </h2>
                                        <div class="form-horizontal">
                    					<form id="addTenantForm" action="/user/addTenant">
                    						<fieldset>
                    							<legend>信息登记</legend>
                    							<div class="control-group">
                    								<label class="control-label" for="input01">昵称</label>
                    								<div class="controls">
                    									<input type="text" class="input-xlarge" name="unick"/>
                    								</div>
                    							</div>
                    							<div class="control-group">
                                                    <label class="control-label" for="input01">密码</label>
                                                    <div class="controls">
                                                        <input type="password" class="input-xlarge" name="upasswd"/>
                                                    </div>
                                                </div>
                    							<div class="control-group">
                    								<label class="control-label" for="input01">姓名</label>
                    								<div class="controls">
                    									<input type="text" class="input-xlarge" name="uname"/>
                    								</div>
                    							</div>

                                                <div class="control-group">
                    								<label class="control-label" for="input01">电话</label>
                    								<div class="controls">
                    									<input type="text" class="input-xlarge" name="uphone"/>
                    								</div>
                    							</div>
                                                <div class="control-group">
                    								<label class="control-label" for="input01">公司</label>
                    								<div class="controls">
                    									<input type="text" class="input-xlarge" name="ucom"/>
                    								</div>
                    							</div>
                                                <div class="control-group">
                    								<label class="control-label" for="input01">Email</label>
                    								<div class="controls">
                    									<input type="text" class="input-xlarge" name="uemail"/>
                    								</div>
                    							</div>

                    							<div class="control-group">
                    								<label class="control-label" for="textarea">描述</label>
                    								<div class="controls">
                    									<textarea class="input-xlarge" id="textarea" rows="4" name="udesc">
                    									</textarea>
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