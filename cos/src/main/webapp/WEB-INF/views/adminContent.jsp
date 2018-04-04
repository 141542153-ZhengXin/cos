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
<script type="text/javascript" src="${PATH }/static/js/adminContent.js"></script>
<link href="${PATH }/static/css/toastr.css" rel="stylesheet" />
<script src="${PATH }/static/js/toastr.min.js"></script>
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
					<li>
						<a href="../admin/adminIndex?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;后台首页
						</a>
					</li>
					<li class="active">
						<a>
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
				<img style="width: 100%" src="${PATH }/static/images/banner3.jpg">
				<div class="carousel-caption">
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—内容管理</h2>
					<a href="#div4" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
			<a class="left carousel-control" href="../admin/adminIndex?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="../admin/adminResume?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
			</a>
		</div>

		<div id="div4" class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							文章编辑 <span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="javascript:void(0);" id="add_a" data-toggle="modal" data-target="#addModal">添加 </a>
							</li>
						</ul>
					</div>
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

	<!-- Modal -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">文章添加</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="adminId1" name="adminId" value="${user.userId }">
							<input type="hidden" id="adminName" name="adminName" value="${user.userName }">
							<label class="col-sm-2 control-label">标题</label>
							<div class="col-sm-9">
								<input id="addInput" type="text" name="title" class="form-control" maxlength="50"></input>
							</div>
							<label class="col-sm-2 control-label">内容</label>
							<div class="col-sm-9">
								<textarea id="addTextarea" name="content" class="form-control" rows="3"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="addEvent()">添加</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">文章修改</h4>
				</div>
				<div class="modal-body">
					<form id="updateForm" class="form-horizontal">
						<div class="form-group">
							<input type="hidden" id="adminId2" name="adminId" value="${user.userId }">
							<input type="hidden" id="id" name="id">
							<label class="col-sm-2 control-label">标题</label>
							<div class="col-sm-9">
								<input id="updateInput" type="text" name="title" class="form-control"></input>
							</div>
							<label class="col-sm-2 control-label">内容</label>
							<div class="col-sm-9">
								<textarea id="updateTextarea" name="content" class="form-control" rows="3"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="updateEvent()">修改</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>