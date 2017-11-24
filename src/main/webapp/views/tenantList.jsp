<%@ page language="java" import="java.util.*" import="com.sjtu.pojo.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<title>Tenant List</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/bs/css/bootstrap.min.css" rel="stylesheet">
		<link href="/bs/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="/css/site.css" rel="stylesheet">

		<script type="text/javascript" src="/js/jquery.min.js"></script>
        <script type="text/javascript" src="/js/format.js"></script>
        <script type="text/javascript" src="/bs/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/js/url.js"></script>
        <script type="text/javascript" src="/js/site.js"></script>
        <script type="text/javascript" src="/js/page/tenantList.js"></script>
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
                                		    <a href="#">租户列表</a>
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
                                		    <a onclick="/page/forward?page=devicelist">设备列表</a>
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
                        	<li>
                        	    <a href="/page/forward?page=admin"><i class="icon-list-alt"></i>系统概况</a>
                        	</li>
							<li class="nav-header">
								租户管理
							</li>
							<li class="active">
								<a href="#"><i class="icon-th-list"></i>租户列表</a>
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
				<div class="span9" id = "tenantList">
					<ul class="files zebra-list">
					    <c:forEach items="${ulist}" var="u">
                        <li>
                    	    <i class="icon-user"></i>
                    	    <a class="title" onclick="showDetail('${u.uid}')">
                    	        账号: ${u.unick}
                    	    </a> <span class="meta">姓名: <em>${u.uname}</em>, 公司: <em>${u.ucom}</em>, 创建于: <em>${u.utime}</em>,  </span>
                        </li>
                        </c:forEach>
                    </ul>

				</div>

                <div class="span9" id = "tenantDetail" style = "display:none">
					<h1 id = "user-nick"></h1>
					<h2>
					</h2>

					<ul class="messages well">
					    <li class="my-well">
                            <span class="message" id= "detail-uid"><b>用户Id:&ensp;&ensp;</b>xiaoke</span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-uname"><b>姓名:&ensp;&ensp;</b>xiaoke</span>
                    	</li>
                    	<li class="my-well">
                            <span class="message" id= "detail-uphone"><b>电话:&ensp;&ensp;</b>xiaoke</span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-ucom"><b>公司:&ensp;&ensp;</b>xiaoke</span>
                        </li>
                    	<li class="my-well">
                            <span class="message" id= "detail-uemail"><b>Email:&ensp;&ensp;</b></span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-ulevel"><b>用户级别:&ensp;&ensp;</b></span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-udesc"><b>用户级别:&ensp;&ensp;</b></span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-utime"><b>创建时间:&ensp;&ensp;</b></span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-udevicecode"><b>申请设备码数:&ensp;&ensp;</b></span>
                        </li>
                        <li class="my-well">
                            <span class="message" id= "detail-appnum"><b>创建App数:&ensp;&ensp;</b></span>
                        </li>
                    </ul>
                    <div class="form-actions" style="text-align:center; background-color:white">
                        <button class="btn" onclick = "back()">Back</button>
                    </div>

				</div>

			</div>
		</div>
	</body>
</html>