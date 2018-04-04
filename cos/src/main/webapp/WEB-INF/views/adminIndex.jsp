<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理员界面</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<jsp:include page="common.jsp"></jsp:include>
<script type="text/javascript" src="${PATH }/static/js/Chart.js"></script>
<script type="text/javascript" src="${PATH }/static/js/adminIndex.js"></script>
</head>
<body>
	<div>
		<nav class="navbar navbar-transparent" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">管理员：${sessionScope.userName}</a>
				<input type="hidden" id="userId" value="${sessionScope.userId}">
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active">
						<a href="#div1">
							<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;后台首页
						</a>
					</li>
					<li>
						<a href="../admin/adminContent?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;内容管理
						</a>
					</li>
					<li>
						<a href="../admin/adminResume?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;简历管理
						</a>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							admin <span class="caret"></span>
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
		</nav>

		<div id="CarouselDIV">
			<div class="item">
				<img style="width: 100%; height: 100%" src="${PATH }/static/images/banner1.jpg">
				<div class="carousel-caption">
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—后台首页</h2>
					<a href="#div1" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
			<a class="left carousel-control" href="../admin/adminResume?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="../admin/adminContent?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
			</a>
		</div>

		<div id="div1" class="row col-md-5">
			<div class="thumbnail">
				<img src="${PATH }/static/images/attention.jpg">
				<div class="caption">
					<h4>网站热帖</h4>
					<ul id="article" class="list-group">
					</ul>
				</div>
			</div>
		</div>

		<div id="div2" class="row col-md-3">
			<div class="thumbnail">
				<img src="${PATH }/static/images/data.jpg">
				<div class="caption">
					<h4>网站数据统计</h4>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>简历投递统计</th>
								<th>去年</th>
								<th>今年</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row">投递总人数</th>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<th scope="row">投递本部门总人数</th>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<th scope="row">简历投递男女比例</th>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div id="div3" class="row col-md-4">
			<div class="thumbnail">
				<img src="${PATH }/static/images/chat.jpg">
				<div class="caption pre-scrollable">
					<h4>学生聊天室</h4>
					<ul id="adminmsg" class="list-group"></ul>
				</div>
			</div>
		</div>

		<div class="modal fade" id="adminModal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">聊天记录</h4>
					</div>
					<div class="modal-body">
						<div id="msgDiv1" class="modal-body pre-scrollable"></div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="col-md-12">
									<form action="#">
										<div class="form-group">
											<label for="admintext">回复留言内容</label>
											<textarea class="form-control" id="admintext" rows="5" cols="10" placeholder="请输入回复内容"></textarea>
											<button type="button" class="btn btn-default" id="sendMsg1">发送</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
</body>
</html>