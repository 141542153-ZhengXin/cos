<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生界面</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<jsp:include page="common.jsp"></jsp:include>
<script type="text/javascript" src="${PATH }/static/js/studentResume.js"></script>
<script type="text/javascript" src="${PATH }/static/js/bootstrapValidator.min.js"></script>
<link href="${PATH }/static/css/bootstrapValidator.min.css" rel="stylesheet">
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
					<li>
						<a href="../student/studentIndex?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;个人首页
						</a>
					</li>
					<li class="active">
						<a>
							<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;简历投递
						</a>
					</li>
					<li>
						<a href="../student/studentQA?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
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
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—简历投递</h2>
					<a href="#div1" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
			<a class="left carousel-control" href="../student/studentIndex?userName=${user.userName }&&userId=${user.userId }" role="button" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="../student/studentQA?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
			</a>
		</div>
		</nav>

		<div class="col-md-2"></div>
		<div id="div1" class="col-md-8">
			<ul class="nav nav-tabs">
				<li id="resume_0" class="active">
					<a href="javascript:void(0);" onclick="ResumeProcess(0)">填写简历</a>
				</li>
				<li id="resume_1">
					<a href="javascript:void(0);" onclick="ResumeProcess(1)">修改简历</a>
				</li>
			</ul>
			<div class="panel panel-default">
				<div class="panel-body">
					<form id="sendForm" class="form-horizontal">
						<label for="input1" class="col-sm-3 control-label">学号</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input1" disabled>
						</div>
						<label for="input2" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input2" disabled>
						</div>
						<label for="input3" class="col-sm-3 control-label">性别</label>
						<div class="col-sm-3 form-group">
							<select class="form-control" id="input3" disabled>
								<option value="0">男</option>
								<option value="1">女</option>
							</select>
						</div>
						<label for="input4" class="col-sm-2 control-label">地区</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input4" disabled>
						</div>
						<label for="input5" class="col-sm-3 control-label">院系</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input5" disabled>
						</div>
						<label for="input6" class="col-sm-2 control-label">专业</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input6" disabled>
						</div>
						<label for="input7" class="col-sm-3 control-label">手机</label>
						<div class="col-sm-3 form-group">
							<input type="text" class="form-control" id="input7" name="telephone">
						</div>
						<label for="input8" class="col-sm-2 control-label">投递组织</label>
						<div class="col-sm-3 form-group">
							<select class="form-control" id="input8" name="organization">
								<c:forEach var="it" items="${organization}">
									<option value="${it}">${it }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-12"></div>
						<label for="input6" class="col-sm-3 control-label">爱好</label>
						<div class="col-sm-8 form-group">
							<textarea class="form-control" id="input9" name="hobby"></textarea>
						</div>
						<label for="input7" class="col-sm-3 control-label">自我评价</label>
						<div class="col-sm-8 form-group">
							<textarea class="form-control" id="input10" name="evaluation"></textarea>
						</div>

						<div class="col-sm-offset-9 col-sm-2">
							<button type="button" class="btn btn-default" onclick="SendResume()">投递</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
</body>
</html>