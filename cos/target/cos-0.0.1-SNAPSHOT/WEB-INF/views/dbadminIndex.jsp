<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据库管理员界面</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<jsp:include page="common.jsp"></jsp:include>
<link href="${PATH }/static/css/admin.css" rel="stylesheet">
<script type="text/javascript" src="${PATH }/static/js/dbadminIndex.js"></script>
</head>
<body>
	<div>
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">数据库管理员：${sessionScope.userName}</a>
				<input type="hidden" id="userId" value="${sessionScope.userId}">
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active">
						<a href="javascript:void(0);">
							<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;数据监控
						</a>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">
							dbadmin <span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="javascript:void(0);" data-toggle="modal" href="common.jsp" data-target="#passWordModal" onclick="openModal()">
									<span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;修改密码
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="window.location.href='../login.jsp'">
							<span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;退出
						</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div>
			<div class="item">
				<img style="width: 100%" src="${PATH }/static/images/banner.jpg">
				<div class="carousel-caption">
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—数据监控</h2>
					<a href="#div1" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
		</div>
		</nav>
		
		<div id="div1" class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">student表数据</div>
				<div class="panel-body pre-scrollable">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>id</th>
								<th>name</th>
								<th>sex</th>
								<th>area</th>
								<th>departments</th>
								<th>major</th>
								<th>telephone</th>
								<th>organization</th>
								<th>hobby</th>
								<th>evaluation</th>
								<th>isPass</th>
								<th>isDelivery</th>
							</tr>
						</thead>
						<tbody id="stbody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">article表数据</div>
				<div class="panel-body pre-scrollable">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>id</th>
								<th>title</th>
								<th>content</th>
								<th>adminId</th>
								<th>adminName</th>
								<th>department</th>
								<th>attention</th>
							</tr>
						</thead>
						<tbody id="atbody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>