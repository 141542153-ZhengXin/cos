<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生界面</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<jsp:include page="common.jsp"></jsp:include>
<script type="text/javascript" src="${PATH }/static/js/studentIndex.js"></script>
</head>
<body>
	<div>
		<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a id="msg" tabindex="0" class="navbar-brand" href="#" data-toggle="popover" data-trigger="hover" data-placement="bottom" title="面试消息"
					data-content="">学&nbsp;生：${sessionScope.userName}</a>
				<input type="hidden" id="userId" value="${sessionScope.userId}">
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active">
						<a>
							<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;个人首页
						</a>
					</li>
					<li>
						<a href="../student/studentResume?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;简历投递
						</a>
					</li>
					<li>
						<a href="../student/studentQA?userName=${user.userName }&&userId=${user.userId }">
							<span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;问答环节
						</a>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							student <span class="caret"></span>
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
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—个人首页</h2>
					<a href="#div1" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
			<a class="left carousel-control" href="../student/studentQA?userName=${user.userName }&&userId=${user.userId }" role="button"
				data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="../student/studentResume?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
			</a>
		</div>
		</nav>

		<div id="div1" class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<span> 文章阅读 </span>
				</div>
				<div class="panel-body">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">

						<!-- Wrapper for slides -->
						<div class="carousel-inner" role="listbox"></div>

						<!-- Controls -->
						<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
							<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
						</a>
						<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
							<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>