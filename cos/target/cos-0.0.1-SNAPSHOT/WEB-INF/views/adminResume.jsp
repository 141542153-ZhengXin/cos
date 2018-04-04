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
<link href="${PATH }/static/css/admin.css" rel="stylesheet">
<script type="text/javascript" src="${PATH }/static/js/bootstrap-paginator.min.js"></script>
<script type="text/javascript" src="${PATH }/static/js/Chart.js"></script>
<script type="text/javascript" src="${PATH }/static/js/adminResume.js"></script>
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
					<li>
						<a href="../admin/adminContent?userName=${sessionScope.userName}&&userId=${sessionScope.userId}">
							<span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;内容管理
						</a>
					</li>
					<li class="active">
						<a>
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
				<img style="width: 100%" src="${PATH }/static/images/banner2.jpg">
				<div class="carousel-caption">
					<h2 style="font-family: '楷体'">欢迎来到校园组织信息管理系统—简历管理</h2>
					<a href="#div1" style="text-decoration: none;" class="glyphicon glyphicon-chevron-down">&nbsp;&nbsp;</a>
				</div>
			</div>
			<a class="left carousel-control" href="../admin/adminContent?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="../admin/adminIndex?userName=${sessionScope.userName}&&userId=${sessionScope.userId}" role="button"
				data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
			</a>
		</div>

		<div id="div5" class="col-md-7">
			<div class="panel panel-default">
				<div class="panel-heading">地区统计</div>
				<div class="panel-body">
					<canvas id="barChart" class="col-md-12"></canvas>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">院系统计</div>
				<div class="panel-body">
					<canvas id="pieChart" class="col-md-12"></canvas>
				</div>
			</div>
		</div>
		<div id="div6" class="col-md-5">
			<ul class="nav nav-tabs">
				<li id="resume_0" class="active">
					<a href="javascript:void(0);" onclick="ResumeProcessTable(0)">未阅简历列表</a>
				</li>
				<li id="resume_1">
					<a href="javascript:void(0);" onclick="ResumeProcessTable(1)">通过简历列表</a>
				</li>
				<div class="col-lg-5 pull-right">
					<div class="input-group">
						<input id="search" type="text" class="form-control" placeholder="学号搜索">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="ResumeProcessTableById()">简历搜索</button>
						</span>
					</div>
				</div>
			</ul>
			<table class="table">
				<thead>
					<tr>
						<th>学号</th>
						<th>姓名</th>
						<th>投递部门</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<nav class="pull-right">
			<ul class="pagination" id="page"></ul>
			</nav>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">简历查看</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset disabled>
							<div class="form-group check-form">
								<label class="col-sm-2 control-label">学号</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">姓名</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">性别</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">院系-专业</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">电话</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">投递部门</label>
								<div class="col-sm-10">
									<p class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">爱好</label>
								<div class="col-sm-10">
									<p style="word-break: break-all;" class="form-control-static"></p>
								</div>
								<label class="col-sm-2 control-label">自我评价</label>
								<div class="col-sm-10">
									<p style="word-break: break-all;" class="form-control-static"></p>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="workModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">简历处理</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">审阅:</label>
							<div class="col-sm-10 radio">
								<label class="col-sm-2">
									<input type="radio" name="optionsRadios" id="optionsRadios1" value="1" onclick="RadioEvent()" checked>
									通过
								</label>
								<label class="col-sm-2">
									<input type="radio" name="optionsRadios" id="optionsRadios2" value="2" onclick="RadioEvent()">
									不通过
								</label>
							</div>
							<label class="col-sm-2 control-label">短信:</label>
							<div class="col-sm-10 work-form">
								<textarea id="msgArea" class="form-control" rows="3"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button id="msgButton" type="button" class="btn btn-info" onclick="MsgModelProcess()" value="">生成短信模板</button>
					<button type="button" class="btn btn-primary" onclick="ResumeProcess()">确认</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>